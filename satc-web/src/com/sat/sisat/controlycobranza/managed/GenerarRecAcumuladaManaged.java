package com.sat.sisat.controlycobranza.managed;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.component.UITree;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

import com.sat.sisat.alfresco.RepositoryManager;
import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.CcRecAcumulada;
import com.sat.sisat.persistence.entity.CcRec;

@ManagedBean
@ViewScoped
public class GenerarRecAcumuladaManaged extends BaseManaged {
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private List<CcRecAcumulada> listRecAdmulables;
	private List<CcRecAcumulada> listRecAdmulablesPorPersona;
	private CcRecAcumulada itemRecAcumulada;
	
	public GenerarRecAcumuladaManaged(){
		
	}
	@PostConstruct
	public void init()throws Exception {
		listRecAdmulables= new ArrayList<CcRecAcumulada>();
		listRecAdmulables= listaAcumulables();
		initTree();
	}
	
	public  List<CcRecAcumulada> listaAcumulables() throws Exception{
		try{
	
		return controlycobranzaBo.findRecAcumulables();
	}catch(Exception e){
		e.printStackTrace();
	}
		return null;
   }

	public void verActo(){
		String nombreArchivo="";
			try{
				nombreArchivo=itemRecAcumulada.getText();
				ByteArrayOutputStream output = new ByteArrayOutputStream();
		        RepositoryManager.buscarContenido(String.valueOf(itemRecAcumulada.getContenId()), output);
		        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			    response.setContentType(Constantes.CONTENT_MIMETYPE_JPEG);
			    response.addHeader("Content-Disposition","attachment;filename="+nombreArchivo);
			    response.setContentLength(output.size());
			    ServletOutputStream servletOutputStream = response.getOutputStream();
			    servletOutputStream.write(output.toByteArray(), 0, output.size());
			    servletOutputStream.flush();
			    servletOutputStream.close();
			    FacesContext.getCurrentInstance().responseComplete();
			}catch(Exception e){
				e.printStackTrace();
			}
	}	
	
	private TreeNode root = null;
	//private static final String DATA_PATH = "/pages/simple-tree-data.properties";

	private void addNodes(String path, TreeNode node)throws Exception {
		boolean end = false;
		// Boolean flag that becomes true if no more properties in this level
		int counter = 1;
		// counter for the current tree node children 1.1, 1.2 etc..
		TreeNodeImpl nodeImpl = new TreeNodeImpl();
		TreeNodeImpl nodeImplSubnivel = new TreeNodeImpl();
		CcRecAcumulada data = new CcRecAcumulada();
		CcRecAcumulada dataSub = new CcRecAcumulada();
		for(int i=0;i<listRecAdmulables.size();i++){
			nodeImpl = new TreeNodeImpl();
			data = new CcRecAcumulada();
			data.setPersonaId(listRecAdmulables.get(i).getPersonaId());
			data.setText(listRecAdmulables.get(i).getDescripcion());
			data.setType("recacum");
			data.setSeleccionado(Boolean.FALSE);
			listRecAdmulablesPorPersona = controlycobranzaBo.findRecAcumulables(listRecAdmulables.get(i).getPersonaId());
			listRecAdmulables.get(i).setListRec(listRecAdmulablesPorPersona);
			data.setListRec(listRecAdmulables.get(i).getListRec());
			nodeImpl.setData(data);		
			node.addChild(i+1, nodeImpl);
			for(int j=0;j<listRecAdmulablesPorPersona.size();j++){
				nodeImplSubnivel = new TreeNodeImpl();
				dataSub = new CcRecAcumulada();
				dataSub.setRecId(listRecAdmulablesPorPersona.get(j).getRecId());
				dataSub.setText(listRecAdmulablesPorPersona.get(j).getDescripcion());
				dataSub.setType("rec");
				dataSub.setSeleccionado(Boolean.FALSE);
				dataSub.setContenId(listRecAdmulablesPorPersona.get(j).getContenId());
				nodeImplSubnivel.setData(dataSub);		
				nodeImpl.addChild(j+1, nodeImplSubnivel);
			}
		}
		
	
	}
  
	public void seleccionarTodo(){
		TreeNodeImpl nodeImpltemp;
		CcRecAcumulada datatemp;
		for(int i=0;i<listRecAdmulables.size();i++){
			nodeImpltemp = new TreeNodeImpl();
			datatemp = new CcRecAcumulada();
			nodeImpltemp = (TreeNodeImpl)root.getChild(i+1);
			datatemp=(CcRecAcumulada)nodeImpltemp.getData();
			datatemp.setSeleccionado(Boolean.TRUE);
			nodeImpltemp.setData(datatemp);
		}
	}
	public void deSeleccionarTodo(){
		TreeNodeImpl nodeImpltemp;
		CcRecAcumulada datatemp;
		for(int i=0;i<listRecAdmulables.size();i++){
			nodeImpltemp = new TreeNodeImpl();
			datatemp = new CcRecAcumulada();
			nodeImpltemp = (TreeNodeImpl)root.getChild(i+1);
			datatemp=(CcRecAcumulada)nodeImpltemp.getData();
			datatemp.setSeleccionado(Boolean.FALSE);
			nodeImpltemp.setData(datatemp);
		}
	}
	
	public void acumularExpedientes()throws Exception{
		try{
			TreeNodeImpl nodeImpltemp;
			CcRecAcumulada datatemp;
			String recIds="";
			String resul="";
			for(int i=0;i<listRecAdmulables.size();i++){
				nodeImpltemp = new TreeNodeImpl();
				datatemp = new CcRecAcumulada();
				nodeImpltemp = (TreeNodeImpl)root.getChild(i+1);
				datatemp=(CcRecAcumulada)nodeImpltemp.getData();
				if(datatemp.getSeleccionado().compareTo(Boolean.TRUE)==0 && (datatemp.getNroRec()==null ||datatemp.getNroRec().compareTo("")==0)){
					for(int j=0;j<datatemp.getListRec().size();j++){
					    recIds=recIds+datatemp.getListRec().get(j).getRecId()+",";
					}
				  resul= controlycobranzaBo.acumularRecs(datatemp.getPersonaId(),recIds.substring(0, recIds.length()-1));
				  datatemp.setNroRec(resul);
				  datatemp.setText(datatemp.getText()+" - "+resul);
				}
				
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void initTree() {
		try {
			root = new TreeNodeImpl();
			addNodes(null, root);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// catching exceptions and stream closure with dataStream.close();
	}

	public TreeNode getRoot() {
		if (root == null) {
			initTree();
		}
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	private String nodeTitle;

	// getter and setter
	public void selectionListener(NodeSelectedEvent event) {
		UITree tree = (UITree) event.getComponent();
		nodeTitle = ((CcRecAcumulada)tree.getRowData()).getText();
	}

	public String getNodeTitle() {
		return nodeTitle;
	}

	public void setNodeTitle(String nodeTitle) {
		this.nodeTitle = nodeTitle;
	}

	public List<CcRecAcumulada> getListRecAdmulables() {
		return listRecAdmulables;
	}

	public void setListRecAdmulables(List<CcRecAcumulada> listRecAdmulables) {
		this.listRecAdmulables = listRecAdmulables;
	}
	public List<CcRecAcumulada> getListRecAdmulablesPorPersona() {
		return listRecAdmulablesPorPersona;
	}
	public void setListRecAdmulablesPorPersona(
			List<CcRecAcumulada> listRecAdmulablesPorPersona) {
		this.listRecAdmulablesPorPersona = listRecAdmulablesPorPersona;
	}
	public CcRecAcumulada getItemRecAcumulada() {
		return itemRecAcumulada;
	}
	public void setItemRecAcumulada(CcRecAcumulada itemRecAcumulada) {
		this.itemRecAcumulada = itemRecAcumulada;
	}
}
