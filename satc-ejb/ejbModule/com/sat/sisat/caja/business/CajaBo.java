package com.sat.sisat.caja.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.jboss.security.auth.spi.Util;

import com.sat.sisat.caja.dao.CajaBusinessDao;
import com.sat.sisat.caja.dao.CjBenefBonoDao;
import com.sat.sisat.caja.dto.AgenciaOperacionDTO;
import com.sat.sisat.caja.dto.AgenciaUsuarioDTO;
import com.sat.sisat.caja.dto.CajaAperturaDTO;
import com.sat.sisat.caja.dto.CajeroDTO;
import com.sat.sisat.caja.dto.CajeroRecaudacionDTO;
import com.sat.sisat.caja.dto.CjBenefBono;
import com.sat.sisat.caja.dto.CjCajaCuadreEntity;
import com.sat.sisat.caja.dto.CjCobranza;
import com.sat.sisat.caja.dto.CjGenerico;
import com.sat.sisat.caja.dto.CjMotivos;
import com.sat.sisat.caja.dto.CjPapeletaDTO;
import com.sat.sisat.caja.dto.CjPartidaEntity;
import com.sat.sisat.caja.dto.CjPersona;
import com.sat.sisat.caja.dto.CjReciboDetalleEntity;
import com.sat.sisat.caja.dto.CjReciboEntity;
import com.sat.sisat.caja.dto.CjReciboPagoEntity;
import com.sat.sisat.caja.dto.CjTipoDocumento;
import com.sat.sisat.caja.dto.CjTupaDTO;
import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.caja.dto.PagoTupaReferenciaDTO;
import com.sat.sisat.caja.dto.ReciboPagoDTO;
import com.sat.sisat.caja.dto.ReciboPagoDescuentoDetalleDTO;
import com.sat.sisat.caja.dto.ReciboPagoDetalleDTO;
import com.sat.sisat.caja.dto.ReciboPagoDetallePieDTO;
import com.sat.sisat.caja.dto.ReciboPagoFormaPagoDTO;
import com.sat.sisat.caja.dto.ReporteCuentaDTO;
import com.sat.sisat.caja.dto.ResumenConceptosDTO;
import com.sat.sisat.caja.dto.TramoSaldoDTO;
import com.sat.sisat.caja.dto.consultaRecibojava;
import com.sat.sisat.cobranzacoactiva.dto.ResumenDeudasCobranzaCoactivaDTO;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.estadocuenta.dto.CrConstanciaNoAdeudo;
import com.sat.sisat.estadocuenta.dto.CrGeneralDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.obligacion.dto.ResumenObligacionDTO;
import com.sat.sisat.persistence.entity.CjAgencia;
import com.sat.sisat.persistence.entity.CjAgenciaOperacion;
import com.sat.sisat.persistence.entity.CjAgenciaUsuario;
import com.sat.sisat.persistence.entity.CjCajaApertura;
import com.sat.sisat.persistence.entity.CjPago;
import com.sat.sisat.persistence.entity.CjPagoTupa;
import com.sat.sisat.persistence.entity.CjReciboDetalle;
import com.sat.sisat.persistence.entity.CjReciboPago;
import com.sat.sisat.persistence.entity.GnTipoCambio;
import com.sat.sisat.predial.business.BaseBusiness;


@Stateless
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class CajaBo extends BaseBusiness implements CajaBoRemote {
	
	private static final long serialVersionUID = 3157204222828169427L;
	
	private CajaBusinessDao service;
	
	@Resource
	private SessionContext context;
	
    public CajaBusinessDao getService() {
    	return this.service;
	}
    
    @PostConstruct
    public void initialize(){
    	this.service=new CajaBusinessDao();
    	setDataManager(this.service);
    	sd = new CjBenefBonoDao(em, ds);
    }
    @Resource(mappedName="java:/SATCDS")
   	public DataSource ds;
       
       private CjBenefBonoDao sd = null;
    
    /**
	 * {@inheritDoc}
     * @throws SisatException 
	 */
       
       
     public Integer temporal() throws SisatException
     {
    	 return getService().temporal();
     }
       
	@Override
    public Integer aperturarCerrarAgencia(AgenciaOperacionDTO agenOper) throws SisatException{
    	return getService().aperturarCerrarAgencia(agenOper);
    }

    /**
	 * {@inheritDoc}
	 */
	@Override
    public AgenciaOperacionDTO obtenerEstadoAgenSupervisor(int agenciaId, int usuarioId, String terminal){
    	return getService().obtenerEstadoAgenSupervisor(agenciaId, usuarioId, terminal);
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int guardarInicio(CjAgencia agencia){
		return getService().guardarInicio(agencia);
	}

	public int guardarFinalizarAgencia(CjAgencia agencia){
		//
		///
		return getService().guardarFinalizarAgencia(agencia);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer aperturarCerrarCaja(CajaAperturaDTO cajAper) {
		return getService().aperturarCerrarCaja(cajAper);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CajaAperturaDTO obtenerEstadoActualCaja(int agenciaId, int agenciaOperacionId, int usuarioId, String terminal) {
		return getService().obtenerEstadoActualCaja(agenciaId, agenciaOperacionId, usuarioId, terminal);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<GenericDTO> obtenerFormaPago(){
		return getService().obtenerFormaPago();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<GenericDTO> obtenerFormaPagoSinBonoCajam(){
		return getService().obtenerFormaPagoSinBonoCajam();
	}

	public List<CjCobranza> obtenerTipoMoneda(){
		return getService().obtenerTipoMoneda();
	}

	//Obtener de la Base de Datos de Tipo de Banco
	public List<GenericDTO> obtenerTipoBanco(){
		return getService().obtenerTipoBanco();
	}

	public List<GenericDTO> obtenerTipoTarjeta(){
		return getService().obtenerTipoTarjeta();
	}
	
	public List<CjTupaDTO> ObtenerTasaTupa(){
		return getService().ObtenerTasaTupa();
	}

	public GnTipoCambio obtenerTipoCambioDia(int cmbValTipoMonedaId){
		return getService().obtenerTipoCambioDia(cmbValTipoMonedaId);
	}

	public CjPersona ObtenerDatosPersona(int persona_id, int cajeroId){
		return getService().ObtenerDatosPersona(persona_id, cajeroId);
	}
	
	/**
	 * CajaPersonaDao-----inicio
	 */											
	public ArrayList<CjPersona> obtenerPersona(int persona_id,
			String apellido_pat, String apellido_mat, String primer_nombre,
			String segundo_nombre, String razon_social, int tipo_docu_iden_id,
			String nro_documento, String nro_papeleta, String nro_placa,
			String apellidosNombres, String codAnterior) {
		// return getService().obtenerPersona(persona_id, apellido_pat ,
		// apellido_mat, primer_nombre, segundo_nombre,razon_social
		// ,tipo_docu_iden_id, nro_documento, nro_papeleta, nro_placa,
		// apellidosNombres, codAnterior);
		return getService().obtenerPersona(persona_id, apellido_pat,
				apellido_mat, primer_nombre, segundo_nombre, razon_social,
				tipo_docu_iden_id, nro_documento, nro_papeleta, nro_placa,
				apellidosNombres, codAnterior);// , apellidosNombres, codAnterior);
	}
    //---------------------fin
	
	// Obtener de la Base de Datos
	public List<CjTipoDocumento> obtenerTipoDocumento(){
		return getService().obtenerTipoDocumento();
	}
	
	
	public List<GenericDTO> obtenerSubconceptoDeuda(int personaId) {
		return getService().obtenerConceptosDeuda(personaId);
	}

	public List<GenericDTO> obtenerCuotas(int personaId){
		return getService().obtenerCuotas(personaId);
	}
	public List<GenericDTO> obtenerPlacas(int personaId){
		return getService().obtenerPlacas(personaId);
	}
	
	public List<GenericDTO> obtenerPredios(int personaId){
		return getService().obtenerPredios(personaId);
	}
	
	public List<GenericDTO> obtenerUso(int personaId){
		return getService().obtenerUso(personaId);
	}
	
	public List<GenericDTO> obtenerAniosDeuda(int personaId) {
		return getService().obtenerAniosDeuda(personaId);
	}

	public ArrayList<CjGenerico> obtenerVehiculo(int personaId){
		return getService().obtenerVehiculo(personaId);
	}

	public ArrayList<CjGenerico> obtenerPredio(int personaId){
		return getService().obtenerPredio(personaId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CjPapeletaDTO> obtenerPapeletaResumen(int personaId,String nroPapeleta){
		return getService().obtenerPapeletaResumen(personaId,nroPapeleta);
	}
	
	@Override
	public List<CjPapeletaDTO> obtenerPapeletaResumenPorDeudasId(String deudasId, Integer flagModulo) throws SisatException{
		return getService().obtenerPapeletaResumenPorDeudasId(deudasId, flagModulo);
	}
	
	@Override
	public List<ResumenObligacionDTO> obtenerResumenObligacionesPorDeudasId(String deudasId, Integer flagModulo) throws SisatException{
		return getService().obtenerResumenObligacionesPorDeudasId(deudasId, flagModulo);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DeudaDTO> obtenerDeuda(int cajeroId, int personaId, String listAnios, String listConceptos, String listSubconc, String listCuotas, Timestamp fechaConsulta) throws SisatException{
		return getService().obtenerDeuda(cajeroId, personaId, listAnios, listConceptos, listSubconc, listCuotas, fechaConsulta);
	}
	
	@Override
	public List<DeudaDTO> obtenerDeudasPorDeudaId(int cajeroId, String listDeudasId,Timestamp fechaConsulta) throws SisatException{
		return getService().obtenerDeudasPorDeudaId(cajeroId, listDeudasId, fechaConsulta);
	}
	
	@Override
	public List<DeudaDTO> obtenerDeudaFiltrada(int cajeroId,
			int personaId,
			String listAnios,
			String listConceptos,
			String listSubconc,
			String listCuotas,
			String listPlacas,
			String listPredios,
			String listUso,
			Timestamp fechaConsulta) throws SisatException {
		return getService().obtenerDeudaFiltrada(cajeroId,
				personaId,
				listAnios,
				listConceptos,
				listSubconc,
				listCuotas,
				listPlacas,
				listPredios,
				listUso,
				fechaConsulta);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DeudaDTO> obetenerDeudaConDsctos(String deudas, Date fechaPago, int cajeroId, Integer flagSimulador){
		return getService().obetenerDeudaConDsctos(deudas, fechaPago, cajeroId,flagSimulador);
	}

	@Override
	public int guardarInicio(CjAgenciaOperacion oAgenciaOperacion,
			String estadoForm){
		return getService().guardarInicio(oAgenciaOperacion, estadoForm);
	}

	//guardarFinalizar
	public int guardarFinalizar(CjAgenciaOperacion oAgenciaOperacion){
		return getService().guardarFinalizar(oAgenciaOperacion);
	}

	//caja cuadre
	public ArrayList<CjCajaCuadreEntity> ObtenerOperacionesCuadre(int cajero_id) throws SisatException{
		return getService().ObtenerOperacionesCuadre(cajero_id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean grabarCuadre(ArrayList<CjCajaCuadreEntity> listaCuadre) {
		return getService().grabarCuadre(listaCuadre);
	}
	  
	//Extorno
	public CjReciboEntity ObtenerDatosRecibo(int recibo_id){
		return getService().ObtenerDatosRecibo(recibo_id);
	}
	//cargar combo Motivo Extorno
	public List<CjMotivos> obtenerMotivoExtorno(){
		return getService().obtenerMotivoExtorno();
	}
	
	//cargar combo Motivo CierreCaja
	public List<CjMotivos> obtenerMotivoCuadre(){
		return getService().obtenerMotivoCuadre();
	} 
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AgenciaUsuarioDTO> getAllAgenciasActivas(){
		return getService().getAllAgenciasActivas();
	}
	
	//cargar combo de nombre de agencia
	
	public List<CjAgencia> obtenerNombreAgencia(){
		return getService().obtenerNombreAgencia();
	} 
	
	
	public ArrayList<CjReciboDetalleEntity> ObtenerDatosReciboDetalle(int recibo_id){
		return getService().ObtenerDatosReciboDetalle(recibo_id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReciboPagoDTO obtenerDatosReciboPago(int reciboId){
		return getService().obtenerDatosReciboPago(reciboId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReciboPagoDTO obtenerDatosReciboPagoTupa(int reciboId){
		return getService().obtenerDatosReciboPagoTupa(reciboId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ReciboPagoFormaPagoDTO> getFormasPagoRecibo(int reciboId){
		return getService().getFormasPagoRecibo(reciboId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void obtenerDeudaPapeleta(int cajeroId, int personaId, int papeletaId, Timestamp fechaConsulta){
		 getService().obtenerDeudaPapeleta(cajeroId, personaId, papeletaId, fechaConsulta);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<GenericDTO> obtenerAgenciasUsuario(int usuarioId, String tipoRol){
		return getService().obtenerAgenciasUsuario(usuarioId, tipoRol);
	}
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AgenciaOperacionDTO obtenerAgenciaOperacion(int usuarioId, String terminal){
		return getService().obtenerAgenciaOperacion(usuarioId, terminal);
	}
	
	//Listado de operacion
	
	public ArrayList<CjReciboPagoEntity> obtenerListadoOperaciones(int cajero_id,  Date fecha_inicio, Date fecha_fin) throws SisatException{
		return getService().obtenerListadoOperaciones(cajero_id,fecha_inicio,fecha_fin);
	}
	

	
	
	public ArrayList<consultaRecibojava> consultarRecibo(String nro_recibo ,int periodo ) throws SisatException
	{
		return getService().consultarRecibo(nro_recibo ,periodo);
	}
	
	
	
	
	
	public String ValidarUsuarioCaja(int cajeroId, String terminal) throws SQLException {
		return getService().ValidarUsuarioCaja(cajeroId, terminal);
	}
	
	//del Extorno
	public ArrayList<CjReciboEntity> ObtenerListaOperacion(String nroRecibo, int personaId, int tipoDocumentoId, String nroDocumento, Timestamp fechaInicio, Timestamp fechaFin) throws SQLException{
		return getService().ObtenerListaOperacion(nroRecibo, personaId, tipoDocumentoId, nroDocumento, fechaInicio, fechaFin);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AgenciaUsuarioDTO getAgenUsuarioCajero(int usuarioId){
		return getService().getAgenUsuarioCajero(usuarioId); 
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAgenciaAperturada(int agenciaId){
		return getService().isAgenciaAperturada(agenciaId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CjCajaApertura getCajaAperturada(int usuarioId, int agenciaId){
		return getService().getCajaAperturada(usuarioId, agenciaId);
	}
	
	public ArrayList<CjReciboPagoEntity> ObtenerDatosReciboPago(int recibo_id){
		return getService().ObtenerDatosReciboPago(recibo_id);
	}
	
	public int ExtornarPago(int recibo_id, int usuario_id,  String obs_extorno, String terminal)  throws SQLException, SisatException{
		return getService().ExtornarPago(recibo_id, usuario_id, obs_extorno, terminal);
	}

	public ArrayList<CjPartidaEntity> ObtenerReportePartidaDiaria(int cajero_id, Date fecha_inicio, Date fecha_fin) throws SQLException{
		
		return getService().ObtenerReportePartidaDiaria(cajero_id,  fecha_inicio,  fecha_fin);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean guardarTmpDeuda(List<DeudaDTO> lstDeudas){
		return getService().guardarTmpDeuda(lstDeudas);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean eliminarTmpDeuda(int cajeroId){
		return getService().eliminarTmpDeuda(cajeroId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Integer cobrarDeuda(String deudasId,
			List<CjReciboDetalle> detalle,
			int cajeroId,
			int personaId,
			BigDecimal montoACobrar,
			BigDecimal montoIngresado,
			BigDecimal montoVuelto,
			String referencia,
			String selectedFormasPago) throws SisatException {
		try {

			
			/** Inciando la creacion del recibo */
			
			CjReciboPago recibo = new CjReciboPago();
			recibo.setPersonaId(personaId);
			recibo.setFechaRecibo(DateUtil.getCurrentDate());
			recibo.setMontoACobrar(montoACobrar);

			// ----recibo.setMontoCobrado(this.montoIngresado.doubleValue() >
			// contrib.getMontoCobrar().doubleValue() ? contrib.getMontoCobrar() :
			// this.montoIngresado);

			recibo.setMontoCobrado(montoIngresado);

			recibo.setMontoDescuento(BigDecimal.ZERO); // TODO : POR REVISAR DESCUENTOS

			recibo.setMontoVuelto(montoVuelto == null || montoVuelto.doubleValue() == 0 ? BigDecimal.ZERO : montoVuelto);
			// recibo.setNroReferencia();
			recibo.setUsuarioId(cajeroId);
			recibo.setEstado(Constante.ESTADO_ACTIVO);
			recibo.setFechaRegistro(DateUtil.getCurrentDate());
			recibo.setTerminal(this.getUser().getTerminal());
			
			recibo.setFlagFuente(Constante.ESTADO_ACTIVO);
			recibo.setReferencia(referencia);
			recibo.setTipoRecibo(Constante.TIPO_RECIBO_IMPUESTOS);
			
			int reciboId = getService().guardarCjReciboPago(recibo);
			
			int reciboDetId = 1;
			for (CjReciboDetalle de : detalle) {
				de.setReciboId(reciboId);
				de.setReciboDetalleId(reciboDetId);
				de.setFechaRegistro(DateUtil.dateToSqlTimestamp(Calendar.getInstance().getTime()));
				getService().guardarCjReciboDetalle(de);
				reciboDetId++;
			}
			
			
			
			getService().cobrarDeuda(deudasId,
					reciboId,
					cajeroId,
					recibo.getPersonaId(),
					//-1,
					recibo.getMontoCobrado(),
					recibo.getTerminal());							
			
			return reciboId;
		} catch (SisatException ex) {
			context.setRollbackOnly();
			throw ex;
		} catch (Exception ex) {
			String msg = "Ha ocurrido un error al efectuar el cobro. Reintente denuevo Por favor. ";
			//System.out.println(msg + ex);
			context.setRollbackOnly();
			throw new SisatException(msg);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Integer cobrarDeudaTupa(CjReciboPago recibo, List<CjReciboDetalle> detalle, List<CjPagoTupa> listPagoTupa, int cajeroId) throws SisatException{
		try{
			int reciboId = getService().guardarCjReciboPago(recibo);
			int reciboDetId = 1;
			for(CjReciboDetalle de : detalle){
				de.setReciboId(reciboId);
				de.setReciboDetalleId(reciboDetId);
				de.setFechaRegistro(DateUtil.dateToSqlTimestamp(Calendar.getInstance().getTime()));
				getService().guardarCjReciboDetalle(de);
				reciboDetId ++;
			}
			for(CjPagoTupa pa: listPagoTupa){
				pa.setReciboId(reciboId);
				getService().cobrarDeudaTupa(pa,cajeroId);
			}
			return reciboId;
		}catch(SisatException ex){
			context.setRollbackOnly();
			throw ex;
		}catch(Exception ex){
			String msg = "Ha ocurrido un error en el servicio cobrarDeuda.";
			//System.out.println(msg + ex);
			context.setRollbackOnly();
			throw new SisatException(msg);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @throws SisatException 
	 */
	@Override
	public List<ReciboPagoDetallePieDTO> searchReciboDetalle(int reciboId) throws SisatException {
		return getService().searchReciboDetalle(reciboId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean cambiarEstadoAgenciaUsuario(int usuarioId, String estado){
		return getService().cambiarEstadoAgenciaUsuario(usuarioId, estado);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ReciboPagoDetalleDTO> searchReciboDetalleTupa(int reciboId){
		return getService().searchReciboDetalleTupa(reciboId);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws SisatException 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void registrarAgenciaUsuario(List<CjAgenciaUsuario> listAgenUsers) throws SisatException{
		if(listAgenUsers.isEmpty()){
			return;
		}
		String str = "";
		for(CjAgenciaUsuario au : listAgenUsers){
			str = str + "," + getService().registrarAgenciaUsuario(au);
		}
		str = str.replaceFirst(",", "");
		int usuarioId = listAgenUsers.get(0).getSgUsuario().getUsuarioId();
		getService().cambiarEstadoAgenciaUsuario(usuarioId, str, Constante.ESTADO_INACTIVO);
	}

	/**
	 * Método usado para la verificacion del pago realizado
	 * */
	public void verificarRecibo(int reciboId) throws SisatException, SQLException {

		/** Verificacion de la realizacion del pago. */
		Query query = em.createQuery("from CjPago p where p.reciboId = :reciboId");
		query.setParameter("reciboId", reciboId);
		query.setMaxResults(1);

		Query queryTupa = em.createQuery("from CjPagoTupa p where p.reciboId = :reciboId");
		queryTupa.setParameter("reciboId", reciboId);
		queryTupa.setMaxResults(1);

		@SuppressWarnings("unchecked")
		List<CjPago> listCjPagos = query.getResultList();

		@SuppressWarnings("unchecked")
		List<CjPagoTupa> listCjPagosTupas = queryTupa.getResultList();

		if (!(listCjPagos.size() != 0 || listCjPagosTupas.size() != 0)) {
			/** Extornando el recibo debido a que no fue generado correctamente */
			anularReciboPorSistema(reciboId, "SISAT: Error en la imputacion del pago");
			throw new SisatException("Ha ocurrido un error al efectuar el cobro. Reintente denuevo Por favor.");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AgenciaUsuarioDTO> getAgenciaUsuario(int usuarioId){
		return getService().getAgenciaUsuario(usuarioId);
	}
	
	public List<CajeroDTO> obtenerCajeros(Date fechaInicio, Date fechaFin) throws SisatException{
		return getService().obtenerCajeros(fechaInicio, fechaFin);
	}

	
	public List<CajeroRecaudacionDTO> obtenerCajerosRecaudacion(Date fechaInicio, Date fechaFin) throws SisatException{
		return getService().obtenerCajerosRecaudacion(fechaInicio, fechaFin);
	}
	
	
	@Override
	public List<ReciboPagoDescuentoDetalleDTO> getDetalleDescuentoRecibo(int reciboId) {
		
		return getService().getDetalleDescuentoRecibo(reciboId);
	}
	
	/**
	 * Valida la aplicacion del bono cajamarquino
	 * @param idDeudasACobrar
	 * @return
	 */
	@Override
	public List<ResumenConceptosDTO> getResumenConcepto(String idDeudasACobrar,Integer personaId,Integer periodo){
		return getService().getResumenConcepto(idDeudasACobrar,personaId,periodo);
	}

	public Integer esBenefBonoCajam(Integer personaId,Integer periodo){
		return getService().esBenefBonoCajam(personaId,periodo);
	}
	
	/**
	 * Extorno de Recibo
	 * Validar Supervisor / extornar Recibo 
	 * @param usuarioSupervisor, passwordSupervisor /  recibo_id, obs_extorno, usuario_id, terminal
	 * @return
	 */
	@Override
	public int buscarSupervisor(String usuarioSupervisor, String passwordSupervisor)  throws SisatException
	{
		
		passwordSupervisor = Util.createPasswordHash("SHA", Util.BASE64_ENCODING, null, null, passwordSupervisor);
		return getService().buscarSupervisor(usuarioSupervisor, passwordSupervisor);		
	}
	
	public int extornarRecibo(int recibo_id,  String obs_extorno, int usuario_id, String terminal)  throws SQLException, SisatException{
		return getService().extornarRecibo(recibo_id, obs_extorno, usuario_id, terminal);
	}

	private int extornarRecibo(int recibo_id,  String obs_extorno)  throws SQLException, SisatException{
		return getService().extornarRecibo(recibo_id, obs_extorno, this.getUser().getUsuarioId(), this.getUser().getTerminal());
	}
	
	private int anularReciboPorSistema(int recibo_id,  String obs_extorno)  throws SQLException, SisatException{
		return getService().anularPorSistemaRecibo(recibo_id, obs_extorno, this.getUser().getUsuarioId(), this.getUser().getTerminal());
	}
	
	@Override
	public List<ReporteCuentaDTO> obtenerReporteCuentas(Date fechaInicio, Date fechaFin) throws SisatException{
	
		return getService().obtenerReporteCuentas(fechaInicio, fechaFin);
	}
	
	@Override
	public List<PagoTupaReferenciaDTO> buscarPersonaContribuyente(String p) throws SisatException{
	
		
		return getService().buscarPersonaContribuyente(p);
	}
	
	public void limpiarCjTmpDeudaCajero(int cajeroId) throws SisatException{
		getService().limpiarCjTmpDeudaCajero(cajeroId);
	}
	
	public CjPersona cargarContribuyentePorPlaca(String placa) throws SisatException{
	
		return getService().cargarContribuyentePorPlaca(placa);
	}

	@Override
	public List<ResumenDeudasCobranzaCoactivaDTO> verificarDeudasEnCobranzaCoactiva(String deudas) throws SisatException {		
		
		return service.verificarDeudasEnCobranzaCoactiva(deudas);
	}
	
	public String busquedaContribEnAtencion(Integer usuarioId, Integer personaId, Integer deudaId)throws SisatException{
		
		return service.busquedaContribEnAtencion(usuarioId, personaId, deudaId);
	}
	
	public List<String> busquedaDeudaEnAtencion(String deudasId)throws SisatException{
		
		return service.busquedaDeudaEnAtencion(deudasId);
	}
	
	public List<CjReciboPagoEntity> busquedaRecibosPorReferencia(String referencia) throws SisatException{
		
		
		return service.busquedaRecibosPorReferencia(com.sat.sisat.common.util.Util.parseToContainsSentence(referencia));
	}

	public List<TramoSaldoDTO> obtenerTramos() {
		return service.obtenerTramos();
	}
	public void guardarBonoCajamarquino(CjBenefBono f) {
		
		try {
			sd.create(f);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	//getPersona_id(),getPeriodo()
	public CjBenefBono getCjBenefBonoById(Integer id,Integer anio)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", id);
		parameters.put("p_periodo",anio);
		List<Object> lPapeleta=getService().findByNamedQuery("findCjBenefBonoById", parameters);
		if(lPapeleta!=null&&lPapeleta.size()>0){
			return (CjBenefBono)lPapeleta.get(0);
		}
		return null;
	}
	
	public Integer registrarConstancia(CrConstanciaNoAdeudo constancia)throws Exception{
		return getService().registrarConstancia(constancia);
	}
	
	public List<CrGeneralDTO> obtenerAnio(Integer personaId,String tipoPredio)throws Exception{
		return getService().obtenerAnio(personaId,tipoPredio);
	}
	
	public List<CrGeneralDTO> obtenerPredios(Integer personaId, Integer anio)throws Exception{
		return getService().obtenerPredios(personaId, anio);
	}
	
	public int registrarRecord(int persona_id,String documento,String anio ,String predios_id,int tipo_record,int recibo_id,int usuario_id,String terminal)throws Exception{
		return getService().registrarRecord(persona_id, documento, anio, predios_id, tipo_record, recibo_id, usuario_id, terminal);
	}
}
	
