package com.sat.sisat.controlycobranza.managed;

/**
 * @author Cristobal Ramires -=CRAMIREZ=-
 * @version 0.1
 * @since 28/02/2019 La clase ValidarDescargoDeudaManaged.java se encargara de la busqueda y administracion de los descargos validados y pendientes por validar 
 */

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

//import org.primefaces.context.RequestContext;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindPersonaDescargo;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindDetalleLoteDescargo;
import com.sat.sisat.controlycobranza.dto.FindLoteDescargo;

@ManagedBean
@ViewScoped
public class ValidarDescargoDeudaManaged  extends BaseManaged{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private List<FindPersonaDescargo> findPersonaDescargo;
	private List<FindLoteDescargo> FindLoteDescargo;
	private List<FindDetalleLoteDescargo> findDetalleLoteDescargo;
	private FindDetalleLoteDescargo item;
	
	
	private Date fecha_Inicio= Calendar.getInstance().getTime() ;
	private Date fecha_Fin= Calendar.getInstance().getTime();
	
	private Integer persona_id;
	private Integer findLoteDescargoId;
	 
	private Integer codigo;
	private Integer usuarioID;
	private Date Fecha;
	
	private boolean selectedAllDeu = false;
	
	private String deudas_id="";
	private String observacionesDescargo="";
	
	@PostConstruct
	public void init() {
		
		FindLoteDescargo =(List<FindLoteDescargo>) getSessionMap().get("getLotesDescargo");
		if(FindLoteDescargo != null){
			try {
				persona_id = FindLoteDescargo.get(0).getPersona_id();
			  
				this.setCodigo(FindLoteDescargo.get(0).getPersona_id());
				this.setUsuarioID(FindLoteDescargo.get(0).getUsuario_id());
				this.setFecha(FindLoteDescargo.get(0).getFecha_registro());
				
				FindLoteDescargo = controlycobranzaBo.getAllLoteDescargo(codigo,getUsuarioID(),getFecha());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		descargosPendientes();
		
		
	}
	
	public void descargosPendientes() {
		try {
			System.out.println("Test");
			findPersonaDescargo = controlycobranzaBo.getAllPersonaDescargo(persona_id,fecha_Inicio, fecha_Fin,2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void limpiar() {
	  //this.setNro_lote(null);
	  //this.setTipo_descargo_id(null);
	  this.setPersona_id(null);
	  fecha_Inicio= Calendar.getInstance().getTime();;
	  fecha_Fin= Calendar.getInstance().getTime();
	  
	  findPersonaDescargo.clear();
    }
	
	public void buscar() {
		System.out.println("Test");
	}
	
	public void buscarPersona() {	
		try {
			System.out.println("Test");
			System.out.println(persona_id);
			System.out.println(fecha_Inicio);
			System.out.println(fecha_Fin);
			findPersonaDescargo = controlycobranzaBo.getAllPersonaDescargo(persona_id,fecha_Inicio, fecha_Fin,1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void verDetallePersona(Date fecha_registro, int codigo, int usuario_id){
		try {
		  this.setCodigo(codigo);
		  this.setUsuarioID(usuario_id);
		  this.setFecha(fecha_registro);
		  
		  getSessionMap().remove("getLotesDescargo");
			
		  FindLoteDescargo = controlycobranzaBo.getAllLoteDescargo(codigo,usuario_id,fecha_registro);
		  getSessionMap().put("getLotesDescargo", FindLoteDescargo);  
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	 }
	
	public void  selectValidarPersona (int personaId , int usuarioLote ,Date fecha){
	  this.setCodigo(personaId);
	  this.setUsuarioID(usuarioLote);
	  this.setFecha(fecha);
	}
	
	public void verDetalleLote() {
	
		try {
			findDetalleLoteDescargo = controlycobranzaBo.getAllDetalleLoteDescargo(getFindLoteDescargoId());
			
			for (FindDetalleLoteDescargo de : findDetalleLoteDescargo) {
				this.setItem(de);
				break;
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
	
	public void changeSelectAllDeu(ValueChangeEvent ev) {
		String nv = ev.getNewValue().toString();
		if (nv.equals("true")) {
			for (FindDetalleLoteDescargo de : findDetalleLoteDescargo) {
				de.setIsSelect(true);
			}
		} else {
			for (FindDetalleLoteDescargo de : findDetalleLoteDescargo) {
				de.setIsSelect(false);
			}
		}
	}
	
	public void descargoPersonaAll(){
		  try {
			  System.out.println("descargoPersonaAll");
			  controlycobranzaBo.confirmarDescargo(0,getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getUsuarioLogIn().getTerminal(),"",4,this.getCodigo(),this.Fecha,this.getUsuarioID(), this.observacionesDescargo);
			  findPersonaDescargo = controlycobranzaBo.getAllPersonaDescargo(persona_id,fecha_Inicio, fecha_Fin,1);

			  //RequestContext.getCurrentInstance().execute("PF('dlgValidarDescargo').hide()");	
			  //RequestContext.getCurrentInstance().update("formValidarLoteDescargo:datoslotesordinarios");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	
	 public void anulaPersonaDescargoAll(){
			 if (observacionesDescargo.equals("") && observacionesDescargo != null ) {
				 addErrorMessage("Por favor, ingrese observaciones.");
				return;
			}
		 
		  try {
			  controlycobranzaBo.confirmarDescargo(0,getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getUsuarioLogIn().getTerminal(),"",5,this.getCodigo(),this.Fecha,this.getUsuarioID(), this.observacionesDescargo);
			  findPersonaDescargo = controlycobranzaBo.getAllPersonaDescargo(persona_id,fecha_Inicio, fecha_Fin,1);
			  
			  //RequestContext.getCurrentInstance().execute("PF('dlgAnularDescargo').hide()");	
			  //RequestContext.getCurrentInstance().update("formValidarLoteDescargo:datoslotesordinarios");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	 
	 public void descargoAll(){
		  try {
			  controlycobranzaBo.confirmarDescargo(getFindLoteDescargoId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getUsuarioLogIn().getTerminal(),"",1,0,Calendar.getInstance().getTime(),0, this.observacionesDescargo);
			  FindLoteDescargo = controlycobranzaBo.getAllLoteDescargo(this.getCodigo(),this.getUsuarioID(),this.getFecha());
			  
			  //RequestContext.getCurrentInstance().execute("PF('dlgValidarDescargo').hide()");	
			  //RequestContext.getCurrentInstance().update("formValidarLoteDescargo:datoslotesordinarios");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	 
	 public void anulaDescargoAll(){
		 if (observacionesDescargo.equals("") && observacionesDescargo != null ) {
			 System.out.println("Entre !");
			 addErrorMessage("Por favor, ingrese observaciones.");
			return;
		}
		 
		 try {
			  controlycobranzaBo.confirmarDescargo(getFindLoteDescargoId(),getSessionManaged().getUsuarioLogIn().getUsuarioId()
											  		,getSessionManaged().getUsuarioLogIn().getTerminal(),"",3,0
											  		,Calendar.getInstance().getTime(),0, this.observacionesDescargo); 
			  
			  FindLoteDescargo = controlycobranzaBo.getAllLoteDescargo(this.getCodigo(),this.getUsuarioID(),this.getFecha());
			  
			  //RequestContext.getCurrentInstance().execute("PF('dlgAnularDescargo').hide()");	
			  //RequestContext.getCurrentInstance().update("formValidarLoteDescargo:datoslotesordinarios");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	  }
	 
	 public void revertirLote(){
		 if (observacionesDescargo.equals("") && observacionesDescargo != null ) {
			 addErrorMessage("Por favor, ingrese observaciones.");
			return;
		}
		 
		 try {
			 /*System.out.println(getFindLoteDescargoId());
			 System.out.println(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			 System.out.println(getSessionManaged().getUsuarioLogIn().getTerminal());
			 System.out.println("");
			 System.out.println(6);
			 System.out.println(0);
			 System.out.println(Calendar.getInstance().getTime());
			 System.out.println(0);
			 System.out.println(this.observacionesDescargo);*/
			 
			 
			  controlycobranzaBo.confirmarDescargo(getFindLoteDescargoId(),getSessionManaged().getUsuarioLogIn().getUsuarioId()
											  		,getSessionManaged().getUsuarioLogIn().getTerminal(),"",6,0
											  		,Calendar.getInstance().getTime(),0, this.observacionesDescargo); 
			  
			  FindLoteDescargo = controlycobranzaBo.getAllLoteDescargo(this.getCodigo(),this.getUsuarioID(),this.getFecha());
			  
			  //RequestContext.getCurrentInstance().execute("PF('dlgAnularDescargo').hide()");	
			  //RequestContext.getCurrentInstance().update("formValidarLoteDescargo:datoslotesordinarios");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	  }
	 
	 public void descargoParcial() throws Exception {
			if(getSessionManaged().getUsuarioLogIn().getUsuarioId() > 0){
					
				this.deudas_id = "";
				for (FindDetalleLoteDescargo de : findDetalleLoteDescargo) {
					if (de.getIsSelect()) {
						this.deudas_id = this.deudas_id+de.getDeuda_id()+",";
					}
				}
				
				this.deudas_id = this.deudas_id.substring(0, this.deudas_id.length() - 1);
	
					if(this.deudas_id.equals("")){
						addInfoMessage(getMsg("Error: Seleccione deudas."));
					}else{
						try {
							System.out.println("deudas select :" + this.deudas_id);
							controlycobranzaBo.confirmarDescargo(getFindLoteDescargoId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getUsuarioLogIn().getTerminal(),this.deudas_id,2,0,Calendar.getInstance().getTime(),0, this.observacionesDescargo);
							
							findDetalleLoteDescargo = controlycobranzaBo.getAllDetalleLoteDescargo(getFindLoteDescargoId());
							for (FindDetalleLoteDescargo de : findDetalleLoteDescargo) {
								this.setItem(de);
								break;
							}
							
							//RequestContext.getCurrentInstance().execute("PF('dlgValidarDescargo').hide()");
							//RequestContext.getCurrentInstance().update("formregistroLotesRS:resultadofinal");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			}else{
				addInfoMessage(getMsg("Error: por favor actualizar navegador y volver al login, gracias."));
			}

		}
	
	public Date getFecha_Inicio() {
		return fecha_Inicio;
	}

	public void setFecha_Inicio(Date fecha_Inicio) {
		this.fecha_Inicio = fecha_Inicio;
	}

	public Date getFecha_Fin() {
		return fecha_Fin;
	}

	public void setFecha_Fin(Date fecha_Fin) {
		this.fecha_Fin = fecha_Fin;
	}

	public Integer getPersona_id() {
		return persona_id;
	}

	public void setPersona_id(Integer persona_id) {
		this.persona_id = persona_id;
	}


	public List<FindPersonaDescargo> getFindPersonaDescargo() {
		return findPersonaDescargo;
	}


	public void setFindPersonaDescargo(List<FindPersonaDescargo> findPersonaDescargo) {
		this.findPersonaDescargo = findPersonaDescargo;
	}


	public Integer getCodigo() {
		return codigo;
	}


	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public Integer getUsuarioID() {
		return usuarioID;
	}


	public void setUsuarioID(Integer usuarioID) {
		this.usuarioID = usuarioID;
	}


	public Date getFecha() {
		return Fecha;
	}


	public void setFecha(Date fecha) {
		Fecha = fecha;
	}


	public Integer getFindLoteDescargoId() {
		return findLoteDescargoId;
	}


	public void setFindLoteDescargoId(Integer findLoteDescargoId) {
		this.findLoteDescargoId = findLoteDescargoId;
	}


	public List<FindLoteDescargo> getFindLoteDescargo() {
		return FindLoteDescargo;
	}


	public void setFindLoteDescargo(List<FindLoteDescargo> findLoteDescargo) {
		FindLoteDescargo = findLoteDescargo;
	}


	public FindDetalleLoteDescargo getItem() {
		return item;
	}


	public void setItem(FindDetalleLoteDescargo item) {
		this.item = item;
	}


	public List<FindDetalleLoteDescargo> getFindDetalleLoteDescargo() {
		return findDetalleLoteDescargo;
	}


	public void setFindDetalleLoteDescargo(List<FindDetalleLoteDescargo> findDetalleLoteDescargo) {
		this.findDetalleLoteDescargo = findDetalleLoteDescargo;
	}


	public boolean isSelectedAllDeu() {
		return selectedAllDeu;
	}


	public void setSelectedAllDeu(boolean selectedAllDeu) {
		this.selectedAllDeu = selectedAllDeu;
	}

	public String getObservacionesDescargo() {
		return observacionesDescargo;
	}

	public void setObservacionesDescargo(String observacionesDescargo) {
		this.observacionesDescargo = observacionesDescargo;
	}
	
	
}
