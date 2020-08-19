package com.sat.sisat.estadocuenta.managed;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.CjGenerico;
import com.sat.sisat.caja.dto.CjPersona;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcActo;
import com.sat.sisat.estadocuenta.business.EstadoCuentaBoRemote;
import com.sat.sisat.persistence.CrudServiceBean;

@ManagedBean
@ViewScoped
public class EstadoCuentaManaged extends BaseManaged {

	
	private static final long serialVersionUID = 1L;

	@EJB
	CajaBoRemote cajaBo;
	
	@EJB
	EstadoCuentaBoRemote estadocuentaBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private ArrayList<CjGenerico> SubConcepto;
	private ArrayList<CjGenerico> Conceptos; // lista de subconceptos
	
	private ArrayList<CjGenerico> nroCuotas;
	private ArrayList<CjGenerico> nroAnyo;
	private ArrayList<CjGenerico> deuda;
	private ArrayList<CjGenerico> ListaSubConceptoSel;
	//private ArrayList<CjGenerico> Usos;

	// select all
	private boolean todosConceptos;
	private boolean todosAnios;
	private boolean todosCuotas;
	private boolean todosUsos;
	private boolean detallado;
	
	private boolean todosdeuda;
	private boolean todoscuenta;
	
	private int codContribuyente;
	private String nroDocuIdentidad;
	private String nombreContribuyente;
	private String direccionPersona;
    
	private CjGenerico deudaItem;
	private FindCcActo actoItem;
	private ArrayList<CjGenerico> SubConceptoSelec;
	private CjPersona cjPersona;
	private List<CjGenerico> lstDjVehicular = new ArrayList<CjGenerico>();
	private List<CjGenerico> lstPapeletas = new ArrayList<CjGenerico>();
	private List<CjGenerico> lstPredio = new ArrayList<CjGenerico>();  
	// check
	private boolean todospapeletas;
	private boolean todosvehiculos;
	private boolean todospredios;

	private int personaId;
    // Control tablas
	private boolean renderedPapeletas = false;
	private boolean renderedPredial = false;
	private boolean renderedVehicular = false;
	
	private String tituloVehiculo;
	private String tituloPapeleta;
	private String tituloPredio;
	
	private String strConcepto = "";
	private String strSubConcepto = "";
	private String strPredio = "";
	private String strVehiculo = "";
	private String strCuotas = "";
	private String strPeriodo = "";
	private String strPapeletas = "";
	//private String strUsos = "";
	
	
	
	public EstadoCuentaManaged() {
		getSessionManaged().setLinkRegresar("/sisat/persona/buscarpersona.xhtml");
	}
	
	private int resultado;
	private Boolean esBuenContribuyente;

	@PostConstruct
	public void init() {

		personaId=getSessionManaged().getContribuyente().getPersonaId();
		cargarSubConceptoTodos();
		cargarNroCuotasTodos();
		cargarNroAnyoTodos();
		cargarListaPrediosTodos(personaId);
		cargarListaPapeletasTodos(personaId);
		cargarVehiculoTodos(personaId);
		//cargarUsos();
		//buscar();
		buscaDeudas();
		
	}
	
	public void limpiar(){
		todosdeuda=false;
		detallado=false;
		todosConceptos=false;
		todosAnios=false;
		todosCuotas=false;
		todosUsos=false;
		todospapeletas=false;
		todosvehiculos=false;
		todospredios=false;
		for (CjGenerico cj : SubConcepto) {
			cj.setSelect(false);
		}
		for (CjGenerico cj : nroAnyo) {
			cj.setSelect(false);
		}
		for (CjGenerico cj : nroCuotas) {
			cj.setSelect(false);
		}
	/*	for (CjGenerico cj : Usos) {
			cj.setSelect(false);
		}*/
		for (CjGenerico cj : lstDjVehicular) {
			cj.setSelect(false);
		}
		for (CjGenerico cj : lstPapeletas) {
			cj.setSelect(false);
		}
		for (CjGenerico cj : lstPredio) {
			cj.setSelect(false);
		}
		 strConcepto = "";
		 strPredio = "";
		 strVehiculo = "";
		 strCuotas = "";
		 strPeriodo = "";
		 strPapeletas = "";
		 //strUsos = "";
		 strSubConcepto = "";
	
	}

	public void volvercargar(){
		//cargarSubConceptoTodos();
		//cargarNroCuotasTodos();
		//cargarNroAnyoTodos();
		//cargarListaPrediosTodos(personaId);
		//cargarListaPapeletasTodos(personaId);
		//cargarVehiculoTodos(personaId);
		//cargarUsos();
		//buscar();
	}
	
	public void imprimirEstadoCuenta()throws SQLException {
		java.sql.Connection connection=null;	
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			 connection=connj.connectJasper();
			 
		     HashMap<String,Object> parameters = new HashMap<String,Object>();
		     parameters.put("persona_id", personaId);
		     parameters.put("ruta_image",SATWEBParameterFactory.getPathReporteImagenes()) ;
		     parameters.put("SUBREPORT_DIR",SATWEBParameterFactory.getPathReporte()) ;
		     parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
		     parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
		     JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"ec_estado_cuenta.jasper"), parameters,connection);
		     ByteArrayOutputStream output = new ByteArrayOutputStream();
		     JasperExportManager.exportReportToPdfStream(jasperPrint, output);	                           
		     HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();		         		          
		     response.setContentType("application/pdf");	         
		     response.addHeader("Content-Disposition","attachment;filename=ec_estado_cuenta"+personaId+".pdf");
		     response.setContentLength(output.size());
		     ServletOutputStream servletOutputStream = response.getOutputStream();
		     servletOutputStream.write(output.toByteArray(), 0, output.size());
		     servletOutputStream.flush();
		     servletOutputStream.close();
		     FacesContext.getCurrentInstance().responseComplete();
		     
		 } catch (Exception jre) {
		      jre.printStackTrace();
		      WebMessages.messageFatal(jre);
		 }finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	}
	
	public void buscar() {
		try {
			 strConcepto = "";
			 strSubConcepto = "";
			 strPredio = "";
			 strVehiculo = "";
			 strCuotas = "";
			 strPeriodo = "";
			 strPapeletas = "";
			// strUsos = "";
			
			 
			ArrayList<CjGenerico> listaConcepto = new ArrayList<CjGenerico>();
			// exampl
			try {

				listaConcepto = getSubConcepto();

				for (CjGenerico oConcepto : listaConcepto) {
					
					if (oConcepto.isSelect()) {
					
						if(oConcepto.getConceptoId()<=4){
								if (strSubConcepto == "")
									strSubConcepto = String.valueOf(oConcepto
											.getSubconceptoId());
								else
									strSubConcepto = strSubConcepto
											+ ","
											+ String.valueOf(oConcepto
													.getSubconceptoId());
						}
						if(oConcepto.getConceptoId()>4){
							if (strConcepto == "")
								strConcepto = String.valueOf(oConcepto.getConceptoId());
							else
								strConcepto = strConcepto
										+ ","
										+ String.valueOf(oConcepto
												.getConceptoId());
						     }
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();

			}

			ArrayList<CjGenerico> ListaCuota = getNroCuotas();

			try {
				if (ListaCuota != null && ListaCuota.size() > 0) {// Aumente para el null
																	
																	
					for (CjGenerico oCuota : ListaCuota) {
						if (oCuota.isSelect()) {
							if (strCuotas == "")
								strCuotas = String.valueOf(oCuota.getCodigo());
							else
								strCuotas = strCuotas + "," + String.valueOf(oCuota.getCodigo());
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();

			}

			ArrayList<CjGenerico> ListaPeriodo = getNroAnyo();

			try {
				if (ListaPeriodo != null && ListaPeriodo.size() > 0) {
					for (CjGenerico oPeriodo : ListaPeriodo) {
						if (oPeriodo.isSelect()) {
							if (strPeriodo == "")
								strPeriodo = String.valueOf(oPeriodo.getCodigo());
							else
								strPeriodo = strPeriodo + "," + String.valueOf(oPeriodo.getCodigo());
						}
					}
				}
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();

			}

			List<CjGenerico> ListaPredio = getLstPredio();

			try {
				if (ListaPredio != null && ListaPredio.size() > 0) {
					for (CjGenerico oPredio : ListaPredio) {
						if (oPredio.isSelect()) {
							if (strPredio == "")
								strPredio = String.valueOf(oPredio.getPredioId());
							else
								strPredio = strPredio + "," + String.valueOf(oPredio.getPredioId());
						}
					}
				}
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}

			List<CjGenerico> ListaVehiculo = getLstDjVehicular();

			try {
				if (ListaVehiculo != null && ListaVehiculo.size() > 0) {
					for (CjGenerico oVehiculo : ListaVehiculo) {
						if (oVehiculo.isSelect()) {
							if (strVehiculo == "")
								strVehiculo = String.valueOf(oVehiculo.getVehiculoId());
							else
								strVehiculo = strVehiculo + "," + String.valueOf(oVehiculo.getVehiculoId());
						}
					}
				}
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}

			List<CjGenerico> ListaPapeletas = getLstPapeletas();//*****

			try {
				if (ListaPapeletas != null && ListaPapeletas.size() > 0) {
					for (CjGenerico oPapeleta : ListaPapeletas) {
						if (oPapeleta.isSelect()) {
							if (strPapeletas == "")
								strPapeletas = String.valueOf(oPapeleta.getPapeletaId());
							else
								strPapeletas = strPapeletas + "," + String.valueOf(oPapeleta.getPapeletaId());
						}
					}
				}
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();  
			}
			
//			ArrayList<CjGenerico> ListaUsos = getUsos();
//
//			try {
//				if (ListaUsos != null && ListaUsos.size() > 0) {// Aumente para el null
//																	
//																	
//					for (CjGenerico oUso : ListaUsos) {
//						if (oUso.isSelect()) {
//							if (strUsos == "")
//								strUsos = String.valueOf(oUso.getCodigo());
//							else
//								strUsos = strUsos + "," + String.valueOf(oUso.getCodigo());
//						}
//					}
//				}
//			} catch (Exception ex) {
//				ex.printStackTrace();
//
//			}
  
			java.sql.Timestamp FechaInteres = DateUtil.getCurrentDate();
			deuda = estadocuentaBo.obtenerDeuda(personaId, strConcepto,strSubConcepto,  strPredio, strVehiculo, strPapeletas, strCuotas,strPeriodo, FechaInteres, todosdeuda,detallado);

		} catch (Exception e) {
			e.printStackTrace();
			addFatalMessage(e.getMessage());
		}
	}
	
	public void buscarActoAdministrativo() throws Exception{
		try {
			actoItem=controlycobranzaBo.findCcActo(deudaItem.getFlagControlyCobranza(),deudaItem.getNroDocumentoId(),personaId);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public String salir() {
		return sendRedirectPrincipal();
	}
	
	public void cargarSubConceptoTodos() {
		try {
			if(todosdeuda==true)
			SubConcepto = estadocuentaBo.obtenerSubConceptoTodos(personaId,1);
			else
				SubConcepto = estadocuentaBo.obtenerSubConceptoTodos(personaId,0);
			
		} catch (Exception e) {
		}
	}
	
	public void cargarNroCuotasTodos() {
		try {
			if(todosdeuda==true)
				nroCuotas = estadocuentaBo.obtenerNroCuotasTodos(personaId,1);
			else
				nroCuotas = estadocuentaBo.obtenerNroCuotasTodos(personaId,0);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public void cargarNroAnyoTodos() {
		try {
			if(todosdeuda==true)
				nroAnyo = estadocuentaBo.obtenerAnyoTodos(personaId,1);
			else
				nroAnyo = estadocuentaBo.obtenerAnyoTodos(personaId,0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*public void cargarUsos() {
		try {
			if(todosdeuda==true)
				Usos = estadocuentaBo.obtenerTipoUsos(personaId,1);
			else
				Usos = estadocuentaBo.obtenerTipoUsos(personaId,0);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/

	public void cargarVehiculoTodos(Integer personaId) {
		try {
			ArrayList<CjGenerico> lstListaVehiculo=null;
			if(todosdeuda==true)
			 lstListaVehiculo = estadocuentaBo.obtenerVehiculoTodos(personaId,1);  //LstDjVehicular
			if(todosdeuda==false)
				 lstListaVehiculo = estadocuentaBo.obtenerVehiculoTodos(personaId,0);  //LstDjVehicular
			if (!lstListaVehiculo.isEmpty()) renderedVehicular = true;
			setLstDjVehicular(lstListaVehiculo);
			setTituloVehiculo("Vehiculos con Impuesto Vehicular (" + lstListaVehiculo.size() +  ")");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarVehiculo(Integer personaId) {
		try {
			ArrayList<CjGenerico> lstListaVehiculo = cajaBo.obtenerVehiculo(personaId);  //LstDjVehicular
			if (!lstListaVehiculo.isEmpty()) renderedVehicular = true;
			setLstDjVehicular(lstListaVehiculo);
			setTituloVehiculo("Vehiculos con Impuesto Vehicular (" + lstListaVehiculo.size() +  ")");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Es para papeleta
	public void cargarListaPapeletasTodos(Integer personaId) {
		try {
			ArrayList<CjGenerico> lstListaPapeleta=null;
			if(todosdeuda==true)
				lstListaPapeleta = estadocuentaBo.obtenerPapeletaResumen(personaId,"",1);
			if(todosdeuda==false)
				lstListaPapeleta = estadocuentaBo.obtenerPapeletaResumen(personaId,"",0);
			if (!lstListaPapeleta.isEmpty()) renderedPapeletas = true;
			setLstPapeletas(lstListaPapeleta);
			setTituloPapeleta("Papeletas del Contribuyente (" + lstListaPapeleta.size() + ")");
			
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
		
	public void cargarListaPrediosTodos(Integer personaId) {
		try {			
			ArrayList<CjGenerico> lstListaPredio=null;
			if(todosdeuda==true)
			 lstListaPredio = estadocuentaBo.obtenerPredioTodos(personaId,1);
			if(todosdeuda==false)
				 lstListaPredio = estadocuentaBo.obtenerPredioTodos(personaId,0);
			
			if (!lstListaPredio.isEmpty()) renderedPredial = true;
			setLstPredio(lstListaPredio);
			setTituloPredio("Lista  de predios (" + lstListaPredio.size() + ")");
  
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void seleccionarTodosConceptos(ActionEvent ev) {
		try {
			if (todosConceptos) {
				for (CjGenerico cj : SubConcepto) {
					cj.setSelect(true);
				}
			} else {
				for (CjGenerico cj : SubConcepto) {
					cj.setSelect(false);
				}
			}
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("ERROR" + ex);
		}
	}

	public void seleccionarTodosAnios(ActionEvent ev) {
		
		try {
			if (todosAnios) {
				for (CjGenerico cj : nroAnyo) {
					cj.setSelect(true);
				}
			} else {
				for (CjGenerico cj : nroAnyo) {
					cj.setSelect(false);
				}

			}
		} catch (Exception ex) {
			// TODO: Controller exception
			System.out.println("ERROR" + ex);
		}
	}

	/*public void seleccionarTodosUsos(ActionEvent ev) {
		
		try {
			if (todosUsos) {
				for (CjGenerico cj : Usos) {
					cj.setSelect(true);
				}
			} else {
				for (CjGenerico cj : Usos) {
					cj.setSelect(false);
				}

			}
		} catch (Exception ex) {
			// TODO: Controller exception
			System.out.println("ERROR" + ex);
		}
	}*/
	
	public void seleccionarTodosCuotas(ActionEvent ev) {
		
		try {
			if (todosCuotas) {
				for (CjGenerico cj : nroCuotas) {
					cj.setSelect(true);
				}
			} else {
				for (CjGenerico cj : nroCuotas) {
					cj.setSelect(false);
				}
			}
		} catch (Exception ex) {
			// TODO: Controller exception
			System.out.println("ERROR" + ex);
		}
	}

	public void seleccionarTodopapeletas(ActionEvent ev) {
		try {
			if (todospapeletas) {
				for (CjGenerico cj : lstPapeletas) {
					cj.setSelect(true);
				}
			} else {
				for (CjGenerico cj : lstPapeletas) {
					cj.setSelect(false);
				}
			}

		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println("ERROR" + ex);
		}
	}

	public void seleccionarTodoPredios(ActionEvent ev) {
		
		try {
			if (todospredios) {
				for (CjGenerico cj : lstPredio) {
					cj.setSelect(true);
				}
			} else {
				for (CjGenerico cj : lstPredio) {
					cj.setSelect(false);
				}
			}

		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println("ERROR" + ex);
		}
	}

	public void seleccionarTodoVehiculos(ActionEvent ev) {
		try {
			if (todosvehiculos) {
				for (CjGenerico cj : lstDjVehicular) {
					cj.setSelect(true);
				}
			} else {
				for (CjGenerico cj : lstDjVehicular) { 
					cj.setSelect(false);
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println("ERROR" + ex);
		}

	}
	
	public void cobroCorrecto(ActionEvent ev){
		getSessionManaged().setPage("/sisat/caja/cajaUbicaPersona.xhtml");
		getSessionManaged().sendRedirectPrincipalListener();
	}
	
	public void imprimirConstancia()throws SQLException {
		java.sql.Connection connection=null;	
		try {
			 CrudServiceBean connj=CrudServiceBean.getInstance();
			 connection=connj.connectJasper();
			 
		     HashMap<String,Object> parameters = new HashMap<String,Object>();
		     parameters.put("persona_id", personaId);
		     parameters.put("anio", resultado);
		     parameters.put("ruta_image",SATWEBParameterFactory.getPathReporteImagenes()) ;
		     parameters.put("SUBREPORT_DIR",SATWEBParameterFactory.getPathReporte()) ;
		     parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
		     parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
		     JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"ec_constancia_buen_contribuyente.jasper"), parameters,connection);
		     ByteArrayOutputStream output = new ByteArrayOutputStream();
		     JasperExportManager.exportReportToPdfStream(jasperPrint, output);	                           
		     HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();		         		          
		     response.setContentType("application/pdf");	         
		     response.addHeader("Content-Disposition","attachment;filename=constancia_bc"+personaId+".pdf");
		     response.setContentLength(output.size());
		     ServletOutputStream servletOutputStream = response.getOutputStream();
		     servletOutputStream.write(output.toByteArray(), 0, output.size());
		     servletOutputStream.flush();
		     servletOutputStream.close();
		     FacesContext.getCurrentInstance().responseComplete();
		     
		 } catch (Exception jre) {
		      jre.printStackTrace();
		      WebMessages.messageFatal(jre);
		 }finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	}

	public void buscaDeudas() {
		try {
				resultado=estadocuentaBo.obtenerDeudas(personaId);
				if (resultado==1){
					esBuenContribuyente=Boolean.FALSE;
				}else if(resultado==2||resultado==3 || resultado==4){
					esBuenContribuyente=Boolean.TRUE;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public ArrayList<CjGenerico> getNroCuotas() {
		return nroCuotas;
	}

	public void setNroCuotas(ArrayList<CjGenerico> nroCuotas) {
		this.nroCuotas = nroCuotas;
	}

	public ArrayList<CjGenerico> getNroAnyo() {
		return nroAnyo;
	}

	public void setNroAnyo(ArrayList<CjGenerico> nroAnyo) {
		this.nroAnyo = nroAnyo;
	}

	public ArrayList<CjGenerico> getDeuda() {
		return deuda;
	}

	public void setDeuda(ArrayList<CjGenerico> deuda) {
		this.deuda = deuda;
	}

	public ArrayList<CjGenerico> getListaSubConceptoSel() {
		return ListaSubConceptoSel;
	}

	public void setListaSubConceptoSel(ArrayList<CjGenerico> listaSubConceptoSel) {
		ListaSubConceptoSel = listaSubConceptoSel;
	}

	public ArrayList<CjGenerico> getConceptos() {
		return Conceptos;
	}

	public void setConceptos(ArrayList<CjGenerico> conceptos) {
		Conceptos = conceptos;
	}

	public ArrayList<CjGenerico> getSubConcepto() {
		return SubConcepto;
	}

	public void setSubConcepto(ArrayList<CjGenerico> subConcepto) {
		SubConcepto = subConcepto;
	}

	public ArrayList<CjGenerico> getSubConceptoSelec() {
		return SubConceptoSelec;
	}

	public void setSubConceptoSelec(ArrayList<CjGenerico> subConceptoSelec) {
		SubConceptoSelec = subConceptoSelec;
	}

	public CjPersona getCjPersona() {
		return cjPersona;
	}

	public void setCjPersona(CjPersona cjPersona) {
		this.cjPersona = cjPersona;
	}

	public List<CjGenerico> getLstDjVehicular() {
		return lstDjVehicular;
	}

	public void setLstDjVehicular(List<CjGenerico> lstDjVehicular) {
		this.lstDjVehicular = lstDjVehicular;
	}

	public boolean isTodosConceptos() {
		return todosConceptos;
	}

	public void setTodosConceptos(boolean todosConceptos) {
		this.todosConceptos = todosConceptos;
	}

	public boolean isTodosAnios() {
		return todosAnios;
	}

	public void setTodosAnios(boolean todosAnios) {
		this.todosAnios = todosAnios;
	}

	public boolean isTodosCuotas() {
		return todosCuotas;
	}

	public void setTodosCuotas(boolean todosCuotas) {
		this.todosCuotas = todosCuotas;
	}

	public int getCodContribuyente() {
		return codContribuyente;
	}

	public void setCodContribuyente(int codContribuyente) {
		this.codContribuyente = codContribuyente;
	}

	public String getNroDocuIdentidad() {
		return nroDocuIdentidad;
	}

	public void setNroDocuIdentidad(String nroDocuIdentidad) {
		this.nroDocuIdentidad = nroDocuIdentidad;
	}

	public String getNombreContribuyente() {
		return nombreContribuyente;
	}

	public void setNombreContribuyente(String nombreContribuyente) {
		this.nombreContribuyente = nombreContribuyente;
	}

	public boolean isTodospapeletas() {
		return todospapeletas;
	}

	public void setTodospapeletas(boolean todospapeletas) {
		this.todospapeletas = todospapeletas;
	}



	public boolean isTodosvehiculos() {
		return todosvehiculos;
	}

	public void setTodosvehiculos(boolean todosvehiculos) {
		this.todosvehiculos = todosvehiculos;
	}

	public boolean isTodospredios() {
		return todospredios;
	}

	public void setTodospredios(boolean todospredios) {
		this.todospredios = todospredios;
	}

	public List<CjGenerico> getLstPapeletas() {
		return lstPapeletas;
	}

	public void setLstPapeletas(List<CjGenerico> lstPapeletas) {
		this.lstPapeletas = lstPapeletas;
	}

	public boolean isRenderedPapeletas() {
		return renderedPapeletas;
	}

	public boolean isRenderedPredial() {
		return renderedPredial;
	}

	public boolean isRenderedVehicular() {
		return renderedVehicular;
	}

	public List<CjGenerico> getLstPredio() {
		return lstPredio;
	}

	public void setLstPredio(List<CjGenerico> lstPredio) {
		this.lstPredio = lstPredio;
	}

	public String getTituloVehiculo() {
		return tituloVehiculo;
	}

	public void setTituloVehiculo(String tituloVehiculo) {
		this.tituloVehiculo = tituloVehiculo;
	}

	public String getTituloPapeleta() {
		return tituloPapeleta;
	}

	public void setTituloPapeleta(String tituloPapeleta) {
		this.tituloPapeleta = tituloPapeleta;
	}
	public String getTituloPredio() {
		return tituloPredio;
	}

	public void setTituloPredio(String tituloPredio) {
		this.tituloPredio = tituloPredio;
	}

	public String getDireccionPersona() {
		return direccionPersona;
	}

	public void setDireccionPersona(String direccionPersona) {
		this.direccionPersona = direccionPersona;
	}


	public boolean isTodosUsos() {
		return todosUsos;
	}


	public void setTodosUsos(boolean todosUsos) {
		this.todosUsos = todosUsos;
	}

/*
	public ArrayList<CjGenerico> getUsos() {
		return Usos;
	}


	public void setUsos(ArrayList<CjGenerico> usos) {
		Usos = usos;
	}
*/

	public boolean isTodosdeuda() {
		return todosdeuda;
	}


	public void setTodosdeuda(boolean todosdeuda) {
		this.todosdeuda = todosdeuda;
	}


	public boolean isTodoscuenta() {
		return todoscuenta;
	}


	public void setTodoscuenta(boolean todoscuenta) {
		this.todoscuenta = todoscuenta;
	}



	public boolean isDetallado() {
		return detallado;
	}


	public void setDetallado(boolean detallado) {
		this.detallado = detallado;
	}


	public String getStrConcepto() {
		return strConcepto;
	}


	public void setStrConcepto(String strConcepto) {
		this.strConcepto = strConcepto;
	}


	public String getStrPredio() {
		return strPredio;
	}


	public void setStrPredio(String strPredio) {
		this.strPredio = strPredio;
	}


	public String getStrVehiculo() {
		return strVehiculo;
	}


	public void setStrVehiculo(String strVehiculo) {
		this.strVehiculo = strVehiculo;
	}


	public String getStrCuotas() {
		return strCuotas;
	}


	public void setStrCuotas(String strCuotas) {
		this.strCuotas = strCuotas;
	}


	public String getStrPeriodo() {
		return strPeriodo;
	}


	public void setStrPeriodo(String strPeriodo) {
		this.strPeriodo = strPeriodo;
	}


	public String getStrPapeletas() {
		return strPapeletas;
	}


	public void setStrPapeletas(String strPapeletas) {
		this.strPapeletas = strPapeletas;
	}

/*
	public String getStrUsos() {
		return strUsos;
	}


	public void setStrUsos(String strUsos) {
		this.strUsos = strUsos;
	}
*/

	public String getStrSubConcepto() {
		return strSubConcepto;
	}


	public void setStrSubConcepto(String strSubConcepto) {
		this.strSubConcepto = strSubConcepto;
	}


	public CjGenerico getDeudaItem() {
		return deudaItem;
	}


	public void setDeudaItem(CjGenerico deudaItem) {
		this.deudaItem = deudaItem;
	}


	public FindCcActo getActoItem() {
		return actoItem;
	}


	public void setActoItem(FindCcActo actoItem) {
		this.actoItem = actoItem;
	}

	public Boolean getEsBuenContribuyente() {
		return esBuenContribuyente;
	}

	public void setEsBuenContribuyente(Boolean esBuenContribuyente) {
		this.esBuenContribuyente = esBuenContribuyente;
	}

	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}

}
