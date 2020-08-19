package com.sat.sisat.alfresco;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;

import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentFault;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.QueryResult;
import org.alfresco.webservice.repository.RepositoryFault;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLAddAspect;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Query;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.ResultSet;
import org.alfresco.webservice.types.ResultSetRow;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ContentUtils;
import org.alfresco.webservice.util.WebServiceFactory;

import com.sat.sisat.alfresco.content.Contenido;
import com.sat.sisat.alfresco.exception.AlfrescoRepositoryException;
import com.sat.sisat.alfresco.util.AlfrescoRepoFactory;
import com.sat.sisat.alfresco.util.Constantes;

public class RepositoryManager {

	public static void guardarContenido(Contenido contenido) throws AlfrescoRepositoryException {

		try {
			AuthenticationUtils.startSession(AlfrescoRepoFactory.getUser(), AlfrescoRepoFactory.getPassword());

			Store storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

			ParentReference companyHomeParent = new ParentReference(storeRef,
					null,
					contenido.getNombreRepositorio(),
					Constants.ASSOC_CONTAINS,
					null);

			companyHomeParent.setChildName("cm:".concat(contenido.getName()));

			CMLCreate create = new CMLCreate("1",
					companyHomeParent,
					null,
					null,
					null,
					contenido.getTipoContenido(),
					contenido.getNamedValueContent());

			CMLAddAspect addAspect = new CMLAddAspect(Constants.ASPECT_TITLED,
					contenido.getNamedValueAspectAll(),
					null,
					"1");

			CML cml = new CML();
			cml.setCreate(new CMLCreate[] { create });

			cml.setAddAspect(new CMLAddAspect[] { addAspect });

			UpdateResult[] result = WebServiceFactory.getRepositoryService().update(cml);
			Reference content = result[0].getDestination();

			ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();

			contentService.write(content,
					Constants.PROP_CONTENT,
					contenido.getContentBytes(),
					contenido.getContentFormat());

		} catch (ContentFault e) {

			e.printStackTrace();

		} catch (RemoteException e) {
			e.printStackTrace();
			throw new AlfrescoRepositoryException("Error al guardar el contenido.");
		} finally {

			AuthenticationUtils.endSession();
		}
	}

	/**
	 * Metodo que realiza la busqueda de contenido en base a un id ingresado.
	 * 
	 * @param id
	 *            Identificador del documento a buscar
	 * @param f
	 *            Variable que almacenara el archivo del contenido encontrado
	 * @return 1 Si encuentra un contenido relacionado con el id ingresado y -1 si no lo encuentra
	 * @throws AlfrescoRepositoryException
	 */
	public static int buscarContenido(String id, ByteArrayOutputStream outputStream) throws AlfrescoRepositoryException {

		int resp = -1;

		try {
			AuthenticationUtils.startSession(AlfrescoRepoFactory.getUser(), AlfrescoRepoFactory.getPassword());
			Store storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

			/**
			 * Para buscar un tipo de contenido especifico +TYPE:"satcm:resDeterminacion" Para
			 * buscar un tipo de documento mediante tu id +TYPE:"satcm:resDeterminacion"
			 * +@satcm\:id:"0" Para la obtension del documento directamente +@satcm\:id:"0"
			 */

			StringBuilder sb = new StringBuilder();

			sb.append("+ @satcm\\:id:\"");
			sb.append(id);
			sb.append("\"");

			String queryString = sb.toString();

			Query query = new Query(Constants.QUERY_LANG_LUCENE, queryString);

			QueryResult queryResult = WebServiceFactory.getRepositoryService().query(storeRef, query, false);

			ResultSet resultSet = queryResult.getResultSet();

			ResultSetRow[] rows = resultSet.getRows();

			if (rows != null) {

				ResultSetRow row = rows[0];

				Reference reference = new Reference(storeRef, row.getNode().getId(), null);
				Content[] contents = WebServiceFactory.getContentService().read(convertToPredicate(reference),
						Constants.PROP_CONTENT);

				InputStream inputStream = ContentUtils.getContentAsInputStream(contents[0]);

				try {

					byte[] buffer = new byte[4096];
					int bytesRead = -1;
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);

					}
					resp = 1;

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						inputStream.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		} catch (AuthenticationFault e) {
			e.printStackTrace();
		} catch (RepositoryFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * Metodo que realiza la busqueda de contenido en base a un id ingresado, ademas obtiene la
	 * metadata de ese contenido contendra datos sobre su nombre en el repositorio, el id y el path
	 * de acceso en el servidor ftp
	 * 
	 * @param id
	 * @param outputStream
	 *            Variable que almacenara el archivo del contenido encontrado
	 * @return Contenido
	 * @throws AlfrescoRepositoryException
	 */
	public static Contenido buscarContenidoYMetaData(String id, ByteArrayOutputStream outputStream)
			throws AlfrescoRepositoryException {

		Contenido contenido = new Contenido();

		try {
			AuthenticationUtils.startSession(AlfrescoRepoFactory.getUser(), AlfrescoRepoFactory.getPassword());
			Store storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

			/**
			 * Para buscar un tipo de contenido especifico +TYPE:"satcm:resDeterminacion" Para
			 * buscar un tipo de documento mediante tu id +TYPE:"satcm:resDeterminacion"
			 * +@satcm\:id:"0" Para la obtension del documento directamente +@satcm\:id:"0"
			 */

			StringBuilder sb = new StringBuilder();

			sb.append("+ @satcm\\:id:\"");
			sb.append(id);
			sb.append("\"");

			String queryString = sb.toString();

			Query query = new Query(Constants.QUERY_LANG_LUCENE, queryString);

			QueryResult queryResult = WebServiceFactory.getRepositoryService().query(storeRef, query, false);

			ResultSet resultSet = queryResult.getResultSet();

			ResultSetRow[] rows = resultSet.getRows();

			if (rows != null) {

				ResultSetRow row = rows[0];

				Reference reference = new Reference(storeRef, row.getNode().getId(), null);
				Content[] contents = WebServiceFactory.getContentService().read(convertToPredicate(reference),
						Constants.PROP_CONTENT);

				NamedValue[] columns = row.getColumns();
				for (NamedValue namedValue : columns) {
					if (namedValue.getName().equals(Constants.PROP_NAME)) {
						contenido.setName(namedValue.getValue());
						contenido.setPathFTP(getPathFtp(contents[0].getNode().getPath()));

					}
					if (namedValue.getName().equals(Constantes.PROP_ID)) {
						contenido.setId(namedValue.getValue());
					}
				}

				InputStream inputStream = ContentUtils.getContentAsInputStream(contents[0]);

				try {

					byte[] buffer = new byte[4096];
					int bytesRead = -1;
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);

					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						inputStream.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		} catch (AuthenticationFault e) {
			e.printStackTrace();
		} catch (RepositoryFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return contenido;
	}

	/**
	 * Metodo que realiza la busqueda de contenido en base a un id ingresado.
	 * 
	 * @param id
	 *            Identificador del documento a buscar
	 * @param f
	 *            Variable que almacenara el archivo del contenido encontrado
	 * @return 1 Si encuentra un contenido relacionado con el id ingresado y -1 si no lo encuentra
	 * @throws AlfrescoRepositoryException
	 */
	public static int buscarContenido(String id, File f) throws AlfrescoRepositoryException {

		int resp = -1;

		try {
			AuthenticationUtils.startSession(AlfrescoRepoFactory.getUser(), AlfrescoRepoFactory.getPassword());

			Store storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

			/**
			 * Para buscar un tipo de contenido especifico +TYPE:"satcm:resDeterminacion"
			 * 
			 * Para buscar un tipo de documento mediante tu id
			 * 
			 * +TYPE:"satcm:resDeterminacion" +@satcm\:id:"0"
			 * 
			 * Para la obtension del documento directamente +@satcm\:id:"0"
			 * 
			 */

			StringBuilder sb = new StringBuilder();

			sb.append("+ @satcm\\:id:\"");
			sb.append(id);
			sb.append("\"");

			String queryString = sb.toString();

			Query query = new Query(Constants.QUERY_LANG_LUCENE, queryString);

			QueryResult queryResult = WebServiceFactory.getRepositoryService().query(storeRef, query, false);

			ResultSet resultSet = queryResult.getResultSet();

			ResultSetRow[] rows = resultSet.getRows();

			if (rows != null) {

				ResultSetRow row = rows[0];

				Reference reference = new Reference(storeRef, row.getNode().getId(), null);
				Content[] contents = WebServiceFactory.getContentService().read(convertToPredicate(reference),
						Constants.PROP_CONTENT);

				InputStream inputStream = ContentUtils.getContentAsInputStream(contents[0]);

				OutputStream outputStream = null;

				try {

					outputStream = new FileOutputStream(f);
					// int byteCount = 0;
					byte[] buffer = new byte[4096];
					int bytesRead = -1;
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);
						// byteCount += bytesRead;
					}
					outputStream.flush();
					resp = 1;

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						inputStream.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					try {
						outputStream.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		} catch (AuthenticationFault e) {
			e.printStackTrace();
		} catch (RepositoryFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * Metodo que realiza la busqueda de contenido en base a un id ingresado, ademas obtiene la
	 * metadata de ese contenido contendra datos sobre su nombre en el repositorio, el id y el path
	 * de acceso en el servidor ftp
	 * 
	 * @param id
	 * @param outputStream
	 *            Variable que almacenara el archivo del contenido encontrado
	 * @return Contenido
	 * @throws AlfrescoRepositoryException
	 */
	public static Contenido buscarContenidoYMetaData(String id, File f) throws AlfrescoRepositoryException {

		Contenido contenido = new Contenido();

		try {
			AuthenticationUtils.startSession(AlfrescoRepoFactory.getUser(), AlfrescoRepoFactory.getPassword());

			Store storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

			/**
			 * Para buscar un tipo de contenido especifico +TYPE:"satcm:resDeterminacion"
			 * 
			 * Para buscar un tipo de documento mediante tu id
			 * 
			 * +TYPE:"satcm:resDeterminacion" +@satcm\:id:"0"
			 * 
			 * Para la obtension del documento directamente +@satcm\:id:"0"
			 * 
			 */

			StringBuilder sb = new StringBuilder();

			sb.append("+ @satcm\\:id:\"");
			sb.append(id);
			sb.append("\"");

			String queryString = sb.toString();

			Query query = new Query(Constants.QUERY_LANG_LUCENE, queryString);

			QueryResult queryResult = WebServiceFactory.getRepositoryService().query(storeRef, query, false);

			ResultSet resultSet = queryResult.getResultSet();

			ResultSetRow[] rows = resultSet.getRows();

			if (rows != null) {

				ResultSetRow row = rows[0];

				Reference reference = new Reference(storeRef, row.getNode().getId(), null);
				Content[] contents = WebServiceFactory.getContentService().read(convertToPredicate(reference),
						Constants.PROP_CONTENT);

				InputStream inputStream = ContentUtils.getContentAsInputStream(contents[0]);

				NamedValue[] columns = row.getColumns();
				for (NamedValue namedValue : columns) {
					if (namedValue.getName().equals(Constants.PROP_NAME)) {
						contenido.setName(namedValue.getValue());
						contenido.setPathFTP(getPathFtp(contents[0].getNode().getPath()));

					}
					if (namedValue.getName().equals(Constantes.PROP_ID)) {
						contenido.setId(namedValue.getValue());
					}
				}

				OutputStream outputStream = null;

				try {

					outputStream = new FileOutputStream(f);
					// int byteCount = 0;
					byte[] buffer = new byte[4096];
					int bytesRead = -1;
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);
						// byteCount += bytesRead;
					}
					outputStream.flush();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						inputStream.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					try {
						outputStream.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		} catch (AuthenticationFault e) {
			e.printStackTrace();
		} catch (RepositoryFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return contenido;
	}

	/**
	 * Metodo que obtiene la metada de un contenido, es contenido tendra datos sobre su nombre en el
	 * repositorio, el id y el path de acceso en el servidor ftp
	 * 
	 * @param id
	 * @return
	 * @throws AlfrescoRepositoryException
	 */
	public static Contenido buscarContenido(String id) throws AlfrescoRepositoryException {

		Contenido contenido = new Contenido();

		try {
			AuthenticationUtils.startSession(AlfrescoRepoFactory.getUser(), AlfrescoRepoFactory.getPassword());

			Store storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

			/**
			 * Para buscar un tipo de contenido especifico +TYPE:"satcm:resDeterminacion"
			 * 
			 * Para buscar un tipo de documento mediante tu id
			 * 
			 * +TYPE:"satcm:resDeterminacion" +@satcm\:id:"0"
			 * 
			 * Para la obtension del documento directamente +@satcm\:id:"0"
			 * 
			 */

			StringBuilder sb = new StringBuilder();

			sb.append("+ @satcm\\:id:\"");
			sb.append(id);
			sb.append("\"");

			String queryString = sb.toString();

			Query query = new Query(Constants.QUERY_LANG_LUCENE, queryString);

			QueryResult queryResult = WebServiceFactory.getRepositoryService().query(storeRef, query, false);

			ResultSet resultSet = queryResult.getResultSet();

			ResultSetRow[] rows = resultSet.getRows();

			if (rows != null) {

				ResultSetRow row = rows[0];

				Reference reference = new Reference(storeRef, row.getNode().getId(), null);

				Content[] contents = WebServiceFactory.getContentService().read(convertToPredicate(reference),
						Constants.PROP_CONTENT);

				NamedValue[] columns = row.getColumns();
				for (NamedValue namedValue : columns) {
					if (namedValue.getName().equals(Constants.PROP_NAME)) {
						contenido.setName(namedValue.getValue());
						contenido.setPathFTP(getPathFtp(contents[0].getNode().getPath()));

					}
					if (namedValue.getName().equals(Constantes.PROP_ID)) {
						contenido.setId(namedValue.getValue());
					}
				}
			}

		} catch (AuthenticationFault e) {
			e.printStackTrace();
		} catch (RepositoryFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return contenido;
	}

	/**
	 * 
	 * @param reference
	 * @return
	 */
	public static Predicate convertToPredicate(Reference reference) {
		Predicate predicate = new Predicate();
		predicate.setNodes(new Reference[] { reference });
		return predicate;
	}

	/**
	 * Metodo usado para la obtencion del path en el servidor ftp de alfresco para un acceso directo
	 * a los contenidos, usado para procesos masivos
	 * 
	 * @param pathFile
	 *            Contiene el path propocionado por alfresco, este contiene definiciones de espacio
	 *            usadas por alfresco
	 * @return
	 */
	private static String getPathFtp(String pathFile) {

		String pathFtp = null;

		/** Obtenemos la url del servidor */
		String url = AlfrescoRepoFactory.getUrl();

		/** Obtenemos el ip del servidor de alfresco */
		String[] ts = url.split("//");
		ts = ts[1].split(":");
		url = ts[0];

		/** Armando path hacia el direcctorio raiz de alfresco */
		pathFtp = "ftp://".concat(AlfrescoRepoFactory.getUser()).concat(":").concat(AlfrescoRepoFactory.getPassword())
				.concat("@").concat(url).concat("/Alfresco");

		/**
		 * Limpiamos el path proporcionado por alfreso para poder trabajarlo como path para el
		 * servidor ftp
		 */
		String path = pathFile.replaceAll("cm:", "");

		path = path.replaceAll("/app:company_home", "");
		path = path.replaceAll("cm_x003a_", "");

		/** Reemplazando espacios en blanco */
		path = path.replaceAll("_x0020_", "%20");

		pathFtp = pathFtp.concat(path);

		return pathFtp;
	}

}
