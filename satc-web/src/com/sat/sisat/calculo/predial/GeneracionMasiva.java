package com.sat.sisat.calculo.predial;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sat.sisat.administracion.dto.CampoDTO;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.RjDocuAnexo;
import com.sat.sisat.persistence.entity.RpDjarbitrio;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpDjusoDetalle;
import com.sat.sisat.persistence.entity.RpInstalacionDj;
import com.sat.sisat.persistence.entity.RpOtrosFrente;
import com.sat.sisat.persistence.entity.RpTipoObra;
import com.sat.sisat.persistence.entity.RpTipoObraPeriodo;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.managed.calculo.DeterminacionArbitrios;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

public class GeneracionMasiva {
	
	private CalculoPredialBoRemote calculoPredialBo;
	private GeneralBoRemote generalBo;
	private RegistroPrediosBoRemote registroPrediosBo;
	private Integer usuarioId;
	private String terminal;
	private Integer annioDj=2019;
	
	public GeneracionMasiva(RegistroPrediosBoRemote registroPrediosBo,CalculoPredialBoRemote calculoPredialBo,GeneralBoRemote generalBo){
		setGeneralBo(generalBo);
		setCalculoPredialBo(calculoPredialBo);
		setRegistroPrediosBo(registroPrediosBo);
		init();
	}
	public void determinacionPredialMasiva(Integer personaInicioDjId,Integer personaFinDjId)throws Exception{
		try{
			ArrayList<Integer> lContribuyente=getCalculoPredialBo().getAllPersonaId(getAnnioDj(),personaInicioDjId,personaFinDjId);
						
			Iterator<Integer> it=lContribuyente.iterator();
			while(it.hasNext()){
				Integer personaId=it.next();
				try{
					//System.out.println(" **(a) determinacion predial persona "+personaId);
					//Calculo del impuesto predial
					DeterminacionPredialMasiva predial=new DeterminacionPredialMasiva(getCalculoPredialBo(),getGeneralBo());
					predial.generarDeterminacionPredialComun(personaId,getAnnioDj());
				}catch(Exception e){
					System.out.println("ERROR EN DETERMINACION PREDIAL Y ARBITRIOS DE CONTRIBUYENTE :: "+personaId+" :: "+e.getMessage());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public void init(){
		try{
			InetAddress owner=InetAddress.getLocalHost();
			setTerminal(owner.getHostAddress());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void registroDjMasiva(Integer personaInicioDjId,Integer personaFinDjId)throws Exception{
		try{
			//Integer annioDjAnt=getAnnioDj()-1;
			Integer annioDjAnt=2020;
			
			ArrayList<Integer> lDjPredial=getCalculoPredialBo().getAllRpDJpredialId(annioDjAnt,personaInicioDjId,personaFinDjId);
			Iterator<Integer> it=lDjPredial.iterator();
			while(it.hasNext()){
				Integer DjId=it.next();
				try{
					duplicaDjPredial(DjId,getAnnioDj(),Constante.MOTIVO_DECLARACION_MASIVA,getUsuarioId(),getTerminal());
				}catch(Exception e){
					System.out.println("ERROR DECLARACION JURADA :: "+DjId+" :: "+e.getMessage());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//managed individual
	public void determinacionMasivaCorrigeArbitrios(Integer personaInicioDjId,Integer personaFinDjId)throws Exception{
		try{
			ArrayList<CampoDTO> lContribuyente=getCalculoPredialBo().getAllParchePersonaId(getAnnioDj(),personaInicioDjId,personaFinDjId);
			
			Iterator<CampoDTO> it=lContribuyente.iterator();
			while(it.hasNext()){
				Integer personaId=it.next().getCampoId();
				Integer DjId=it.next().getTablaId();
				try{
					DeterminacionArbitrios arbitrios=new DeterminacionArbitrios(getCalculoPredialBo(),getGeneralBo());
					MpPersona contribuyente=arbitrios.getContribuyente(personaId);
					arbitrios.generarDeterminacionArbitriosDjPredial(contribuyente,2019,DjId);
				}catch(Exception e){
					System.out.println("ERROR EN DETERMINACION PREDIAL Y ARBITRIOS DE CONTRIBUYENTE :: "+personaId+" :: "+e.getMessage());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * AÑO 2016
	 * @param personaInicioDjId
	 * @param personaFinDjId
	 * @throws Exception
	 */
	
	public void determinacionArbitriosMasiva(Integer personaInicioDjId,Integer personaFinDjId)throws Exception{
		try{
			ArrayList<Integer> lContribuyente=getCalculoPredialBo().getAllPersonaId(getAnnioDj(),personaInicioDjId,personaFinDjId);
						
			Iterator<Integer> it=lContribuyente.iterator();
			while(it.hasNext()){
				Integer personaId=it.next();
				try{
					//System.out.println("Hola");
					//Calculo de arbitrios
					System.out.println(" **(b) determinacion arbitrios persona "+personaId);
					DeterminacionArbitriosMasivaOrigen arbitrios=new DeterminacionArbitriosMasivaOrigen(getCalculoPredialBo(),getGeneralBo(),getAnnioDj());
					arbitrios.generarDeterminacionArbitrios2016(personaId,getAnnioDj());
				}catch(Exception e){
					System.out.println("ERROR EN DETERMINACION PREDIAL Y ARBITRIOS DE CONTRIBUYENTE :: "+personaId+" :: "+e.getMessage());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
		AÑO 2016
	 */
	
	
	public void determinacionMasiva(Integer personaInicioDjId,Integer personaFinDjId)throws Exception{
		try{
			//Solo trae contribuyentes con predios en el 2016 //sql orroroso
			ArrayList<Integer> lContribuyente=getCalculoPredialBo().getAllPersonaId(getAnnioDj(),personaInicioDjId,personaFinDjId);
						
			Iterator<Integer> it=lContribuyente.iterator();
			while(it.hasNext()){
				Integer personaId=it.next();
				try{
					System.out.println(" **(a) determinacion predial persona "+personaId);
					//Calculo del impuesto predial
					DeterminacionPredialMasiva predial=new DeterminacionPredialMasiva(getCalculoPredialBo(),getGeneralBo());
					predial.generarDeterminacionPredialComun2014(personaId,getAnnioDj());//esta controlando que no se genere determinacion masiva en el 2013
					//Calculo de arbitrios
					//System.out.println(" **(b) determinacion arbitrios persona "+personaId);
					//DeterminacionArbitriosMasivaOrigen arbitrios=new DeterminacionArbitriosMasivaOrigen(getCalculoPredialBo(),getGeneralBo(),getAnnioDj());
					//arbitrios.generarDeterminacionArbitrios2014(personaId,getAnnioDj());
				}catch(Exception e){
					System.out.println("ERROR EN DETERMINACION PREDIAL Y ARBITRIOS DE CONTRIBUYENTE :: "+personaId+" :: "+e.getMessage());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void duplicaDjPredial(Integer djId,Integer annoDj,Integer motivoDeclaracion,Integer userId,String terminal)throws Exception{
		Integer DjActualizaId=0;
		
			if(djId!=null&&djId>0){
				RpDjpredial djpredio=calculoPredialBo.getRpDJpredial(djId);
				
//				System.out.println(" ## generación declaracion jurada 2013 Persona_id="+djpredio.getPersonaId()+" dj_id="+djId);
				
				RpDjdireccion direccion=getRegistroPrediosBo().getRpDjDireccion(djpredio.getDjId());
				ArrayList<RpDjconstruccion> listaConstruccion=registroPrediosBo.getAllRpDjconstruccion(djpredio.getDjId(),djpredio.getAnnoDj());
				ArrayList<RpInstalacionDj> listaOtrasInsta=registroPrediosBo.getAllRpInstalacionDj(djpredio.getDjId());
				ArrayList<RpOtrosFrente> listaOtrosFrentes=registroPrediosBo.getAllRpOtrosFrente(djpredio.getDjId());
				List<BuscarPersonaDTO> listaTransferente=registroPrediosBo.getTransferentePropiedad(djpredio.getDjId(), Constante.TIPO_TRANSFERENCIA_TRANSFERENTE);
				Integer djArbitrioIdAnterior=registroPrediosBo.getDjArbitrioId(djpredio.getDjId());
				ArrayList<RpDjuso> listaUsosDelPredio=new ArrayList<RpDjuso>();
				if(djArbitrioIdAnterior!=null&&djArbitrioIdAnterior>0)
					listaUsosDelPredio=registroPrediosBo.getAllRpDjuso(djArbitrioIdAnterior);
//					listaUsosDelPredio=registroPrediosBo.getAllRpDjusoNuevo(djArbitrioIdAnterior); /*Comentado el 29.12.2016, se utilizó en 2015 para cambiar djs con usos nuevos para año 2016.*/
				ArrayList<RjDocuAnexo> listaDocAnexos=registroPrediosBo.getAllRjDocuAnexo(djpredio.getDjId());
				
				//--
				Integer djIdAnt=djpredio.getDjId();
				//satc.dbo.rp_djpredial
				djpredio.setDjId(Constante.RESULT_PENDING);
				djpredio.setIdAnterior(String.valueOf(djIdAnt));
				djpredio.setUsuarioId(userId);
				djpredio.setTerminal(terminal);
				djpredio.setTerminalRegistro(terminal);
				djpredio.setFechaActualizacion(DateUtil.getCurrentDate());
				//--
				djpredio.setFlagDjAnno(Constante.FLAG_DJ_ANIO_ACTIVO);
				djpredio.setEstado(Constante.ESTADO_ACTIVO);
				
				djpredio.setMotivoDeclaracionId(motivoDeclaracion);
				djpredio.setAnnoDj(annoDj);
				djpredio.setFechaDeclaracion(DateUtil.getCurrentDate());
				djpredio.setFechaRegistro(DateUtil.getCurrentDate());
				djpredio.setFiscalizado(Constante.FISCALIZADO_NO);
				djpredio.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
				djpredio.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
				//--
				DjActualizaId=registroPrediosBo.guardarRpDjpredial(djpredio);
				
				//satc.dbo.rp_djdireccion
				if(direccion!=null){
					direccion.setDjId(DjActualizaId);
					direccion.setEstado(Constante.ESTADO_ACTIVO);
					direccion.setFechaRegistro(DateUtil.getCurrentDate());
					direccion.setUsuarioId(userId);
					direccion.setTerminal(terminal);
					registroPrediosBo.guardarRpDjdireccion(direccion);
				}
				
				//satc.dbo.rp_djconstruccion
				for(int i=0;i<listaConstruccion.size();i++){
					RpDjconstruccion construccion=listaConstruccion.get(i);
					construccion.setDjId(DjActualizaId);
					construccion.setUsuarioId(userId);
					construccion.setFechaRegistro(DateUtil.getCurrentDate());
					construccion.setTerminal(terminal);
					int rez=registroPrediosBo.guardarRpDjconstruccion(construccion);
					if(rez>0){
						Integer newConstruccionId=registroPrediosBo.getUltimoConstruccionId(DjActualizaId);
						listaConstruccion.get(i).setNewConstruccionId(newConstruccionId);
					}
				}
				
				//satc.dbo.rp_instalacion_dj
				for(int i=0;i<listaOtrasInsta.size();i++){
					RpInstalacionDj instalacion=listaOtrasInsta.get(i);
					instalacion.setDjId(DjActualizaId);
					instalacion.setUsuarioId(userId);
					instalacion.setFechaRegistro(DateUtil.getCurrentDate());
					instalacion.setTerminal(terminal);
					
					//--Correccion del calculo del valor de las otras instalaciones -22/12/2013
					RpTipoObraPeriodo tipoObraPeriodo = new RpTipoObraPeriodo();				
					tipoObraPeriodo = registroPrediosBo.getRpTipoObraPeriodo(instalacion.getTipoObraId(), djpredio.getAnnoDj());
					if(tipoObraPeriodo!=null&&tipoObraPeriodo.getValorUnitario()!=null&&tipoObraPeriodo.getValorUnitario().doubleValue()>0){
						BigDecimal valorInstalacion=instalacion.getAreaTerreno().multiply(tipoObraPeriodo.getValorUnitario());
						if(valorInstalacion!=null){
							instalacion.setValorInstalacion(valorInstalacion);	
						}
					}else {
						RpTipoObra tipoObra=registroPrediosBo.getRpTipoObra(instalacion.getTipoObraId());
						if(tipoObra!=null&&tipoObra.getValorUnitario()!=null&&tipoObra.getValorUnitario().doubleValue()>0){
							BigDecimal valorInstalacion=instalacion.getAreaTerreno().multiply(tipoObra.getValorUnitario());
							if(valorInstalacion!=null){
								instalacion.setValorInstalacion(valorInstalacion);	
							}	
						}
					}
					//--Correccion del calculo del valor de las otras instalaciones -22/12/2013
					
					instalacion.setEstado(Constante.ESTADO_ACTIVO);
					
					registroPrediosBo.guardarRpInstalacionDj(instalacion);
				}
				
				//satc.dbo.rp_otros_frentes
				for(int i=0;i<listaOtrosFrentes.size();i++){
					RpOtrosFrente frente=listaOtrosFrentes.get(i);
					frente.setDjId(DjActualizaId);
					frente.setUsuarioId(userId);
					frente.setFechaRegistro(DateUtil.getCurrentDate());
					frente.setTerminal(terminal);
					frente.setEstado(Constante.ESTADO_ACTIVO);
					int rez=registroPrediosBo.guardarRpOtrosFrente(frente);
				}
				
				RpDjarbitrio rpDjarbitrio=new RpDjarbitrio();  
				rpDjarbitrio.setDjId(DjActualizaId);
				rpDjarbitrio.setEstado(Constante.ESTADO_ACTIVO);
				rpDjarbitrio.setFechaRegistro(DateUtil.getCurrentDate());
				rpDjarbitrio.setTerminal(terminal);
				rpDjarbitrio.setUsuarioId(userId);
				int result=registroPrediosBo.guardarDjArbitrioId(rpDjarbitrio);
				if(result>0){
					//Obtenemos el djArbitrioId del nuevo Dj
					Integer djArbitrioId=registroPrediosBo.getDjArbitrioId(DjActualizaId);
					for(int i=0;i<listaUsosDelPredio.size();i++){
						RpDjuso uso=listaUsosDelPredio.get(i);
						uso.setDjarbitrioId(djArbitrioId);
						uso.setUsuarioId(userId);
						uso.setFechaRegistro(DateUtil.getCurrentDate());
						uso.setTerminal(terminal);
						uso.setEstado(Constante.ESTADO_ACTIVO);
						//Se corrige el anio de afectacion de acuerdo al anio que se duplica la Dj
						uso.setAnnoAfectacion(annoDj);
						int rez=registroPrediosBo.guardarRpDjuso(uso);
						if(rez>0){
						   Integer newdjUsoId=registroPrediosBo.getUltimoDjUsoId(djArbitrioId);
						   ArrayList<RpDjusoDetalle> lista=registroPrediosBo.getAllRpDjusoDetalle(uso.getDjusoId());
						   for(int j=0;j<lista.size();j++){
							   RpDjusoDetalle detalle=lista.get(j);
							   detalle.setDjusoId(newdjUsoId);
							   Integer newConstruccionId=getNewConstruccionId(detalle.getConstruccionId(),listaConstruccion);
							   if(newConstruccionId>0){
								   detalle.setConstruccionId(newConstruccionId);
								   detalle.setDjusoDetalleId(Constante.RESULT_PENDING);
								   registroPrediosBo.guardarRpDjusoDetalle(detalle);
							   }
						   }
						}
					}
				}
				
				//satc.dbo.rp_transferencia_propiedad
				for(int i=0;i<listaTransferente.size();i++){
					registroPrediosBo.registrarAdquirientes(listaTransferente,DjActualizaId,Constante.TIPO_TRANSFERENCIA_TRANSFERENTE);
				}
				
				//satc.dbo.rj_docu_anexo
				for(int i=0;i<listaDocAnexos.size();i++){
					RjDocuAnexo anexo=listaDocAnexos.get(i);
					anexo.setDjId(DjActualizaId);
					anexo.setUsuarioId(userId);
					anexo.setFechaRegistro(DateUtil.getCurrentDate());
					anexo.setTerminal(terminal);
					anexo.setEstado(Constante.ESTADO_ACTIVO);
					registroPrediosBo.guardarRjDocuAnexo(anexo);
				}
			}
		
	}
	
	public Integer getNewConstruccionId(Integer construccionId,ArrayList<RpDjconstruccion> listaConstruccion)throws Exception{
		Integer NewConstruccionId=0;
		for(int i=0;i<listaConstruccion.size();i++){
			if(construccionId.equals(listaConstruccion.get(i).getConstruccionId())){
				NewConstruccionId=listaConstruccion.get(i).getNewConstruccionId();
			}
		}
		return NewConstruccionId;
	}
	
	public GeneralBoRemote getGeneralBo() {
		return generalBo;
	}

	public void setGeneralBo(GeneralBoRemote generalBo) {
		this.generalBo = generalBo;
	}
	
	public CalculoPredialBoRemote getCalculoPredialBo() {
		return calculoPredialBo;
	}

	public void setCalculoPredialBo(CalculoPredialBoRemote calculoPredialBo) {
		this.calculoPredialBo = calculoPredialBo;
	}
	
	public RegistroPrediosBoRemote getRegistroPrediosBo() {
		return registroPrediosBo;
	}

	public void setRegistroPrediosBo(RegistroPrediosBoRemote registroPrediosBo) {
		this.registroPrediosBo = registroPrediosBo;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
	public Integer getAnnioDj() {
		return annioDj;
	}

	public void setAnnioDj(Integer annioDj) {
		this.annioDj = annioDj;
	}
	
	/* =================================== */
	
	public void pruTobal(Integer personaInicioDjId,Integer personaFinDjId)throws Exception{
		System.out.println("Hola 2222222");
		try{
			DeterminacionPredialMasiva predial=new DeterminacionPredialMasiva(getCalculoPredialBo(),getGeneralBo());
			predial.obtenerValorConstruccion_TOBAL(2020);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}
