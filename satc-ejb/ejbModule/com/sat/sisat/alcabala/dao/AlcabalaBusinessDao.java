package com.sat.sisat.alcabala.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.sat.sisat.alcabala.BuscarAlcabalaDTO;
import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.determinacion.vehicular.dto.DatosInafecDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.GnNotaria;
import com.sat.sisat.persistence.entity.GnTipoMoneda;
import com.sat.sisat.persistence.entity.RaAlcabalaSustento;
import com.sat.sisat.persistence.entity.RaDireccionAlcabala;
import com.sat.sisat.persistence.entity.RaDjalcabala;
import com.sat.sisat.persistence.entity.RaTipoTransferencia;
import com.sat.sisat.persistence.entity.RpCondicionPropiedad;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

public class AlcabalaBusinessDao extends GeneralDao {

	public List<GnNotaria> getAllGnNotaria() throws Exception {
		List<GnNotaria> lista = new ArrayList<GnNotaria>();
		try {
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT notaria_id, nombre_notaria FROM ").append(Constante.schemadb).append(".gn_notaria ORDER BY nombre_notaria");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnNotaria obj = new GnNotaria();
				obj.setNotariaId(rs.getInt("notaria_id"));
				obj.setNombreNotaria(rs.getString("nombre_notaria"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<RaTipoTransferencia> getAllRaTipoTransferencia()
			throws Exception {
		List<RaTipoTransferencia> lista = new ArrayList<RaTipoTransferencia>();
		try {
			StringBuffer SQL=new StringBuffer();
			SQL.append("  SELECT tipo_transferencia_id, descripcion, desc_corta from ").append(Constante.schemadb).append(".ra_tipo_transferencia ");
			SQL.append("where flag_afectacion ='1'and estado = '1'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RaTipoTransferencia obj = new RaTipoTransferencia();
				obj.setTipoTransferenciaId(rs.getInt("tipo_transferencia_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescCorta(rs.getString("desc_corta"));
				lista.add(obj);
			}

		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}


	public List<GnTipoMoneda> getAllGnTipoMoneda() throws Exception {
		List<GnTipoMoneda> lista = new ArrayList<GnTipoMoneda>();
		try {
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT tipo_moneda_id, descripcion FROM ").append(Constante.schemadb).append(".gn_tipo_moneda where tipo_moneda_id < 3");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnTipoMoneda obj = new GnTipoMoneda();
				obj.setTipoMonedaId(rs.getInt("tipo_moneda_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}


	public BigDecimal getIPM(int anio, int mes) throws Exception {

		BigDecimal ipm = new BigDecimal("0");
		StringBuffer sentenciaSQL=new StringBuffer();
		sentenciaSQL.append("SELECT ipm_orignal FROM ").append(Constante.schemadb).append(".gn_ipm where anno_ipm=? and mes_ipm=?");

		PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
		pst.setInt(1, anio);
		pst.setInt(2, mes);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {

			ipm = rs.getBigDecimal(1);

		}

		return ipm;
	}


	public ArrayList<BuscarAlcabalaDTO> getAllRaDjalcabala(Integer personaId, Integer djAlcabala, Date fechaInicio, Date fechaFin)
			throws Exception {
		
		ArrayList<BuscarAlcabalaDTO> lista = new ArrayList<BuscarAlcabalaDTO>();
		StringBuffer sentenciaSQL=new StringBuffer();
		sentenciaSQL.append("select dja.djalcabala_id, dja.dj_id, dja.persona_id, dja.fecha_declaracion, dja.fecha_transferencia, dja.porc_propiedad, ");
		sentenciaSQL.append("dja.tipo_predio, dja.ubicacion_predio, dal.direccion_completa, dis.descripcion, dja.distrito_id , dja.estado, dja.notaria_id, ");
		sentenciaSQL.append("dja.tipo_transferencia_id, dja.condicion_propiedad_id, dja.flag_primera_venta, dja.tipo_moneda_id, dja.autovaluo_total, dja.valor_transferencia,");
		sentenciaSQL.append("dal.direccion_alcabala_id, o.nombre_usuario ");
		sentenciaSQL.append("from ").append(Constante.schemadb).append(".ra_djalcabala dja ");
		sentenciaSQL.append("left join ").append(Constante.schemadb).append(".sg_usuario o on(o.usuario_id=dja.usuario_id) ");
		sentenciaSQL.append("inner join ").append(Constante.schemadb).append(".ra_direccion_alcabala dal on(dja.djalcabala_id=dal.djalcabala_id) ");
		sentenciaSQL.append("inner join ").append(Constante.schemadb).append(".gn_distrito dis on (dis.distrito_id=dal.distrito_id) and ( dis.dpto_id=dja.departamento_id) and (dja.provincia_id=dis.provincia_id) ");		
		sentenciaSQL.append("and dal.estado_id=1 ");
		
		if(personaId!=null){
			sentenciaSQL.append("and dja.persona_id=? " );
		}
		if(djAlcabala!=null){
			sentenciaSQL.append("and dja.djalcabala_id=? " );
		}
		if(fechaInicio!=null){
			sentenciaSQL.append("and  dja.fecha_declaracion >=? " );
		}
		if(fechaFin!=null){
			
			sentenciaSQL.append("and  dja.fecha_declaracion <=? " );
		}
		sentenciaSQL.append(" where dja.estado!='9' " );
		sentenciaSQL.append("order by dja.djalcabala_id desc " );
		
		PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
		
		int cont=1;
		if(personaId!=null){
			pst.setInt(cont, personaId);
		}
		if(djAlcabala!=null){
			pst.setInt(++cont,  djAlcabala);
		}
		if(fechaInicio!=null){
			pst.setTimestamp(++cont, new Timestamp(fechaInicio.getTime()));
		}
		if(fechaFin!=null){

			String fechaF = new SimpleDateFormat("dd/MM/yyyy").format(fechaFin);
			String [] dataTemp = fechaF.split("/");
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(dataTemp[2]), Integer.parseInt(dataTemp[1])- 1, Integer.parseInt(dataTemp[0]));
			c.add(Calendar.DAY_OF_MONTH, 1);
			fechaFin=c.getTime();
			pst.setTimestamp(++cont, new Timestamp((fechaFin).getTime()));

		}
		
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			 
			 BuscarAlcabalaDTO obj= new BuscarAlcabalaDTO();
			 
			 obj.setDjAlcabalaId(rs.getInt("djalcabala_id"));
			 obj.setDjId(rs.getInt("dj_id"));
			 obj.setPersonaId(personaId);
			 obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
			 obj.setFechaTransferencia(rs.getString("fecha_transferencia"));
			 obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
			 obj.setTipoPredio(rs.getString("tipo_predio"));
			 obj.setUbicPredio(rs.getString("ubicacion_predio"));
			 obj.setDirecCompleta(rs.getString("direccion_completa"));
			 obj.setDistriDesc(rs.getString("descripcion"));
			 obj.setDistriId(rs.getInt("distrito_id"));
			 obj.setEstado(rs.getString("estado"));
			 
			 obj.setNotariaId(rs.getInt("notaria_id"));
			 obj.setTipoTransferenciaId(rs.getInt("tipo_transferencia_id"));
			 obj.setCondicionPropiedadId(rs.getInt("condicion_propiedad_id"));
			 obj.setFlagPrimeraVenta(rs.getString("flag_primera_venta"));
			 obj.setTipoMonedaId(rs.getInt("tipo_moneda_id"));
			 obj.setAutovaluoTotal(rs.getBigDecimal("autovaluo_total"));
			 obj.setValorTransferencia(rs.getBigDecimal("valor_transferencia"));
			 obj.setDireccionAlcabalaId(rs.getInt("direccion_alcabala_id"));
			
			 obj.setUsuario(rs.getString("nombre_usuario"));
			 
			 lista.add(obj);
			
		}
		return lista;
	}


	public void guardaDjAlcabala(RaDjalcabala raDjalcabala) throws Exception {

	}


	public void guardarDocAnexos(RaAlcabalaSustento raAlcabalaSustento)
			throws Exception {
	}
	
	public int actualizaRaDireccionAlcabala(RaDireccionAlcabala raDirecAlca)throws Exception{
		int result = 0;
		try {
			StringBuffer sentenciaSQL = new StringBuffer();
			sentenciaSQL.append("UPDATE ").append(Constante.schemadb).append(".ra_direccion_alcabala ");
			sentenciaSQL.append("SET estado_id= ?  ");
			sentenciaSQL.append("Where djalcabala_id= ?  ");
			sentenciaSQL.append("and direccion_alcabala_id= ?  ");

			PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
			
			int cont=1;
			pst.setString(cont, "2");
			pst.setInt(++cont, raDirecAlca.getDjalcabalaId());
			pst.setInt(++cont, raDirecAlca.getDireccionAlcabalaId());
			result = pst.executeUpdate();

		} catch (Exception e) {
			result = 1;
			e.printStackTrace();		}
		
		return result;
		
	}

	
	public int actualizaRaTransferente(int djaAlcabalaId)throws Exception{
		int result = 0;
		try {
			StringBuffer sentenciaSQL = new StringBuffer();
			sentenciaSQL.append("UPDATE ").append(Constante.schemadb).append(".ra_transferente_alcabala ");
			sentenciaSQL.append("SET estado= 2 ");
			sentenciaSQL.append("WHERE djalcabala_id = ? ");
			
			PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
			int cont=1;
			pst.setInt(cont, djaAlcabalaId);

			result = pst.executeUpdate();
		} catch (Exception e) {
			result = 1;
			e.printStackTrace();		}
		
		return result;
	}
	
	public int actualizaDjAlcabala(RaDjalcabala raDjalcabala) throws Exception {
	int result = 0;
	try {
		StringBuffer sentenciaSQL = new StringBuffer();
		sentenciaSQL.append("UPDATE ").append(Constante.schemadb).append(".ra_djalcabala ");
		sentenciaSQL.append("SET estado = ? , ");
		sentenciaSQL.append("predio_alcabala_id= ? , ");
		sentenciaSQL.append("motivo_actualizacion_id= ? , ");
		sentenciaSQL.append("anno_declaracion= ? , ");
		sentenciaSQL.append("fecha_declaracion= ? , ");
		sentenciaSQL.append("dj_id= ? , ");
		sentenciaSQL.append("ubicacion_predio= ? , ");
		sentenciaSQL.append("condicion_propiedad_id= ? , ");
		sentenciaSQL.append("porc_propiedad= ? , ");
		sentenciaSQL.append("codigo_postal_distrito= ? , ");
		sentenciaSQL.append("tipo_predio= ? , ");
		sentenciaSQL.append("tipo_transferencia_id= ? , ");
		sentenciaSQL.append("fecha_transferencia= ? , ");
		sentenciaSQL.append("flag_primera_venta= ? , ");
		sentenciaSQL.append("notaria_id= ? , ");
		sentenciaSQL.append("valor_transferencia= ? , ");
		sentenciaSQL.append("autovaluo_total= ? , ");
		sentenciaSQL.append("autovaluo_afecta= ? , ");
		sentenciaSQL.append("ajuste= ? , ");
		sentenciaSQL.append("autovaluo_ajustado= ? , ");
		sentenciaSQL.append("tasa= ? , ");
		sentenciaSQL.append("base_imponible= ? , ");
		sentenciaSQL.append("base_exonerada= ? , ");
		sentenciaSQL.append("base_afecta= ? , ");
		sentenciaSQL.append("valor_impuesto= ? , ");
		sentenciaSQL.append("interes= ? , ");
		sentenciaSQL.append("total_deuda= ? , ");
		sentenciaSQL.append("glosa= ? , ");
		sentenciaSQL.append("tiene_deuda= ? , ");
		sentenciaSQL.append("tipo_moneda_id= ? , ");
		sentenciaSQL.append("valor_transferencia_soles= ? , ");
		sentenciaSQL.append("tasa_ajuste= ? , ");
		sentenciaSQL.append("usuario_id= ? , ");
		sentenciaSQL.append("fecha_registro= ? , ");
		sentenciaSQL.append("terminal= ? ");

		
		sentenciaSQL.append("WHERE djalcabala_id = ? ");
		
		PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
		int cont=1;
		pst.setString(cont, "2");
		pst.setInt(++cont, raDjalcabala.getPredioAlcabalaId());
		
		pst.setInt(++cont, 2);
		pst.setInt(++cont, raDjalcabala.getAnnoDeclaracion());

		pst.setTimestamp(++cont, raDjalcabala.getFechaDeclaracion());
		pst.setInt(++cont, raDjalcabala.getDjId());
		pst.setString(++cont, raDjalcabala.getUbicacionPredio());
		pst.setInt(++cont, raDjalcabala.getCondicionPropiedadId());
		pst.setBigDecimal(++cont, raDjalcabala.getPorcPropiedad());
		pst.setString(++cont, raDjalcabala.getCodigoPostalDistrito());
		pst.setString(++cont, raDjalcabala.getTipoPredio());
		pst.setInt(++cont, raDjalcabala.getTipoTransferenciaId());
		pst.setString(++cont, raDjalcabala.getFechaTransferencia());
		pst.setString(++cont, raDjalcabala.getFlagPrimeraVenta());
		pst.setInt(++cont, raDjalcabala.getNotariaId());
		pst.setBigDecimal(++cont, raDjalcabala.getValorTransferencia());
		pst.setBigDecimal(++cont, raDjalcabala.getAutovaluoTotal());
		pst.setBigDecimal(++cont, raDjalcabala.getAutovaluoAfecta());
		pst.setBigDecimal(++cont, raDjalcabala.getAjuste());
		pst.setBigDecimal(++cont, raDjalcabala.getAutovaluoAjustado());
		pst.setBigDecimal(++cont, raDjalcabala.getTasa());
		pst.setBigDecimal(++cont, raDjalcabala.getBaseImponible());
		pst.setBigDecimal(++cont, raDjalcabala.getBaseExonerada());
		pst.setBigDecimal(++cont, raDjalcabala.getBaseAfecta());
		pst.setBigDecimal(++cont, raDjalcabala.getValorImpuesto());
		pst.setBigDecimal(++cont, raDjalcabala.getInteres());
		pst.setBigDecimal(++cont, raDjalcabala.getTotalDeuda());
		pst.setString(++cont, raDjalcabala.getGlosa());
		pst.setString(++cont, raDjalcabala.getTieneDeuda());
		pst.setInt(++cont, raDjalcabala.getTipoMonedaId());
		pst.setBigDecimal(++cont, raDjalcabala.getValorTransferenciaSoles());
		pst.setBigDecimal(++cont, raDjalcabala.getTasaAjuste());
		pst.setInt(++cont, raDjalcabala.getUsuarioId());
		pst.setTimestamp(++cont, raDjalcabala.getFechaRegistro());
		pst.setString(++cont, raDjalcabala.getTerminal());
		pst.setInt(++cont, raDjalcabala.getDjalcabalaId());

		result = pst.executeUpdate();
		//System.out.println("actualizo djAlcabala");

		
	} catch (Exception e) {
		result = 1;
		e.printStackTrace();

	}
	return result;
	
}
	
	
	public ArrayList<BuscarPersonaDTO> getRaTransferenteAlcabalaByDJAlcabajaId(
			int djAlcabalaId) throws Exception {
		

		ArrayList<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		StringBuffer sentenciaSQL=new StringBuffer();
		sentenciaSQL.append("select dja.djalcabala_id ,dja.transferente_id, dja.tipo_persona_id, dja.subtipo_persona_id, dja.PERSONA_ID ,dja.tipo_doc_identidad_id, dja.nro_docu_identidad ,dja.ape_paterno, ");
		sentenciaSQL.append("dja.ape_materno, dja.nombres, dja.razon_social, dja.nombre_completo, dja.direccion_completa, dja.telefono, dja.referencia  , mp.descrpcion_corta  ");
		sentenciaSQL.append("FROM ").append(Constante.schemadb).append(".ra_transferente_alcabala dja, ").append(Constante.schemadb).append(".mp_tipo_docu_identidad mp ");
		sentenciaSQL.append("where dja.estado=1 and dja.tipo_doc_identidad_id= mp.tipo_doc_identidad_id and dja.djalcabala_id= ? " );

		PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
		
		int cont=1;

		pst.setInt(cont, djAlcabalaId);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			 
			BuscarPersonaDTO obj= new BuscarPersonaDTO();
			
				
			obj.setDireccionCompleta(rs.getString("direccion_completa"));
			obj.setApellidosNombres(rs.getString("nombre_completo"));
			obj.setTipoDocIdentidad(rs.getString("descrpcion_corta"));
			obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
			obj.setRazonSocial(rs.getString("razon_social"));
			obj.setTelefono(rs.getString("telefono"));
			obj.setTipoPersonaId(rs.getInt("tipo_persona_id"));
			obj.setSubtipoPersonaId(rs.getInt("subtipo_persona_id"));
			obj.setPersonaId(rs.getInt("PERSONA_ID"));
			obj.setTipodocumentoIdentidadId(rs.getInt("tipo_doc_identidad_id"));
			obj.setApellidoPaterno(rs.getString("ape_paterno"));
			obj.setApellidoMaterno(rs.getString("ape_materno"));
			obj.setPrimerNombre(rs.getString("nombres"));
			obj.setNombresCompletos(rs.getString("nombre_completo"));
			obj.setReferencia(rs.getString("referencia"));

			 
			 lista.add(obj);
			
		}
		return lista;

	}
	
	public ArrayList<AnexosDeclaVehicDTO> getRaAlcabalaSustentoByDjAlcabalaId(
			int djAlcabalaId) throws Exception {
		
		ArrayList<AnexosDeclaVehicDTO> lista = new ArrayList<AnexosDeclaVehicDTO>();
		StringBuffer sentenciaSQL=new StringBuffer();
		sentenciaSQL.append("select asu.ra_alcabala_sustento_id ,asu.djalcabala_id ,asu.doc_sustentatorio_id , asu.numero_documento,asu.referencia ,asu.content_id ,asu.usuario_id ,asu.fecha_registro ,asu.terminal, rv.descripcion ");
		sentenciaSQL.append("FROM ").append(Constante.schemadb).append(".ra_alcabala_sustento asu, ").append(Constante.schemadb).append(".rv_documento_sustentatorio rv ");
		sentenciaSQL.append("where asu.doc_sustentatorio_id= rv.doc_sustentatorio_id and asu.djalcabala_id= ? and asu.estado= 1 " );

		PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());

		int cont=1;

		pst.setInt(cont, djAlcabalaId);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			 
			AnexosDeclaVehicDTO obj= new AnexosDeclaVehicDTO();
				
			obj.setDescripcionDoc(rs.getString("descripcion"));
			obj.setDocSustentatorioId(rs.getInt("doc_sustentatorio_id"));
			obj.setGlosaDoc(rs.getString("numero_documento"));
			obj.setNomDocAdjunto(rs.getString("referencia"));
			obj.setContentId(rs.getInt("content_id"));
			 
			 lista.add(obj);
			
		}
		return lista;
	}
	
	/**
	 * 
	 * @param raAlcaSus
	 * @return
	 * @throws Exception
	 */
	public int actualizaAlcabalaSustento(int djAlcabalaId){
	int result = 0;
	try {
		StringBuffer sentenciaSQL = new StringBuffer();
		sentenciaSQL.append("UPDATE ").append(Constante.schemadb).append(".ra_alcabala_sustento ");
		sentenciaSQL.append("SET estado= 2 ");
		sentenciaSQL.append("WHERE djalcabala_id = ? ");
		
		PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
		int cont=1;
		pst.setInt(cont, djAlcabalaId);
	

		result = pst.executeUpdate();
	} catch (Exception e) {
		result = 1;
		e.printStackTrace();		
	}
	return result;
	}
	
	/**
	 * 
	 * @param idPersona
	 * @return
	 * @throws Exception
	 */
	public DatosInafecDTO getInafecAlcabala(int idPersona)throws Exception{
	try {
		StringBuffer sentenciaSQL= new StringBuffer();
		
		sentenciaSQL.append("select ce.fecha_inicio, ce.fecha_fin, cc.tipo, cc.valor  from ").append(Constante.schemadb).append(".gn_condicion_especial ce ");
		sentenciaSQL.append("inner join ").append(Constante.schemadb).append(".dt_condi_especial_contri cc on (cc.tipo_cond_especial_id=ce.tipo_cond_especial_id) ");
		sentenciaSQL.append("where cc.concepto_id=7 and cc.subconcepto_id=70 and ce.persona_id=? ");
		
		PreparedStatement pst=connect().prepareStatement(sentenciaSQL.toString());
		int cont=1;
		pst.setInt(cont, idPersona);
		ResultSet rs = pst.executeQuery();
		DatosInafecDTO datos = null;
		while (rs.next()) {
			datos = new DatosInafecDTO();
			datos.setFechaInicio(rs.getDate("fecha_inicio"));
			datos.setFechaFin(rs.getDate("fecha_fin"));
			datos.setTipo(rs.getString("tipo"));
			datos.setValor(rs.getBigDecimal("valor"));
		}
		return datos;
		
	} catch (Exception e) {
		e.printStackTrace();		
	}
		return null;
	}
	
	
	/**
	 * Obtiene el autovaluo de un predio segun el año y el codigo de predio ingresado
	 * @param predioId
	 * @param annoTransfer
	 * @return
	 */
	public BigDecimal obtenerAutovaluoPredio(int predioId, int annoTransfer)throws Exception{
		try {
			
			BigDecimal autoVal= new BigDecimal(0);
						
			StringBuffer SQL= new StringBuffer();
			//SQL.append("select p.base_afecta from ").append(Constante.schemadb).append(".dt_determinacion_predio p inner join ").append(Constante.schemadb).append(".dt_determinacion d on (d.determinacion_id=p.determinacion_id ) ");
			SQL.append("select p.valor_autovaluo_alcabala from ").append(Constante.schemadb).append(".dt_determinacion_predio p inner join ").append(Constante.schemadb).append(".dt_determinacion d on (d.determinacion_id=p.determinacion_id ) ");
			SQL.append("where  p.estado='1' and d.estado='1' and p.predio_id=? and d.anno_determinacion=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			int cont=1;
			pst.setInt(cont, predioId);
			pst.setInt(++cont, annoTransfer);
			ResultSet rs= pst.executeQuery();

			while (rs.next()) {
			//autoVal=rs.getBigDecimal("base_afecta");	
			autoVal=rs.getBigDecimal("valor_autovaluo_alcabala");
			
			}
			
			return autoVal;
  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param dtDeter
	 * @return
	 * @throws Exception
	 */
	public int actualizaDtDeterminacionALcabala(DtDeterminacion dtDeter)throws Exception{
		int result = 0;
		try {
			StringBuffer sentenciaSQL = new StringBuffer();
			sentenciaSQL.append("UPDATE ").append(Constante.schemadb).append(".dt_determinacion ");
			sentenciaSQL.append("SET estado= 0 ");
			sentenciaSQL.append("WHERE determinacion_id = ? ");
			
			PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
			int cont=1;
			pst.setInt(cont, dtDeter.getDeterminacionId());
		

			result = pst.executeUpdate();
		} catch (Exception e) {
			result = 1;
			e.printStackTrace();		
		}
		return result;
		}
	
	
	public int actualizaDeudaAlcabala(int deterId)throws Exception{
		int result = 0;
		try {
			StringBuffer sentenciaSQL = new StringBuffer();
			sentenciaSQL.append("UPDATE ").append(Constante.schemadb).append(".cd_deuda ");
			sentenciaSQL.append("SET estado= 0 ");
			sentenciaSQL.append("WHERE determinacion_id = ? ");
			
			PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
			int cont=1;
			pst.setInt(cont, deterId);
		

			result = pst.executeUpdate();
		} catch (Exception e) {
			result = 1;
			e.printStackTrace();		
		}
		return result;
		}
	
	public List<RpCondicionPropiedad> getAllRpCondicionPropiedad()throws Exception{
		List<RpCondicionPropiedad> lista=new LinkedList<RpCondicionPropiedad>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT descripcion,condicion_propiedad_id FROM ").append(Constante.schemadb).append(".rp_condicion_propiedad order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpCondicionPropiedad obj=new RpCondicionPropiedad(); 
				obj.setCondicionPropiedadId(rs.getInt("condicion_propiedad_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}

	public void eliminarDJAlcabala(BuscarAlcabalaDTO currentItem) throws SisatException {
		try{
			//ra_djalcabala
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".ra_djalcabala ");
			SQL.append(" SET estado = '").append(Constante.ESTADO_ELIMINADO).append("' ,");
			SQL.append(" glosa = '").append(currentItem.getGlosa()).append("' ,");
			SQL.append(" fecha_declaracion = ? ,");
			SQL.append(" usuario_id = ").append(currentItem.getUsuarioId()).append(" ,");			
			SQL.append(" terminal = '").append(currentItem.getTerminal()).append("' ");
			SQL.append(" WHERE djalcabala_id=? and dj_id=? ");

			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setTimestamp(1,currentItem.getFechaDeclaracion());
			pst.setInt(2,currentItem.getDjAlcabalaId());
			pst.setInt(3,currentItem.getDjId());
			
			pst.executeUpdate();
			
			System.out.println("ra_djalcabala ");	
			
			//ra_direccion_alcabala
			StringBuffer SQL2=new StringBuffer();
			SQL2.append(" UPDATE ").append(Constante.schemadb).append(".ra_direccion_alcabala ");
			SQL2.append(" SET estado_id = '").append(Constante.ESTADO_ELIMINADO).append("' ,");
			SQL2.append(" usuario_id = ").append(currentItem.getUsuarioId()).append(" ,");
			SQL2.append(" terminal = '").append(currentItem.getTerminal()).append("' ");
			SQL2.append(" WHERE djalcabala_id=? ");

			PreparedStatement pst2=connect().prepareStatement(SQL2.toString());
			pst2.setInt(1,currentItem.getDjAlcabalaId());			
			
			pst2.executeUpdate();
			
			System.out.println("ra_direccion_alcabala ");	
			
			//ra_transferente_alcabala
			StringBuffer SQL3=new StringBuffer();
			SQL3.append(" UPDATE ").append(Constante.schemadb).append(".ra_transferente_alcabala ");
			SQL3.append(" SET estado = '").append(Constante.ESTADO_ELIMINADO).append("' ,");
			SQL3.append(" usuario_id = ").append(currentItem.getUsuarioId()).append(" ,");
			SQL3.append(" terminal = '").append(currentItem.getTerminal()).append("' ");
			SQL3.append(" WHERE djalcabala_id=? ");

			PreparedStatement pst3=connect().prepareStatement(SQL3.toString());
			pst3.setInt(1,currentItem.getDjAlcabalaId());			
			
			pst3.executeUpdate();
			
			System.out.println("ra_transferente_alcabala ");	
			
			//ra_alcabala_sustento
			StringBuffer SQLSust=new StringBuffer("SELECT ra_alcabala_sustento_id FROM ").append(Constante.schemadb).append(".ra_alcabala_sustento");
			SQLSust.append(" WHERE djalcabala_id = ?");
			
			PreparedStatement pstSust=connect().prepareStatement(SQLSust.toString());
			pstSust.setInt(1,currentItem.getDjAlcabalaId());	
			
			
			ResultSet rsSust = pstSust.executeQuery();
			Integer raAlcabalaSustentoId = 0; 
			
			while(rsSust.next()){				
				raAlcabalaSustentoId = rsSust.getInt("ra_alcabala_sustento_id");				
			}
			
			if(raAlcabalaSustentoId > 0){
				StringBuffer SQL4=new StringBuffer();
				SQL4.append(" UPDATE ").append(Constante.schemadb).append(".ra_alcabala_sustento ");
				SQL4.append(" SET estado = '").append(Constante.ESTADO_ELIMINADO).append("' ,");
				SQL4.append(" usuario_id = ").append(currentItem.getUsuarioId()).append(" ,");
				SQL4.append(" terminal = '").append(currentItem.getTerminal()).append("' ");
				SQL4.append(" WHERE raAlcabalaSustentoId=? ");

				PreparedStatement pst4=connect().prepareStatement(SQL4.toString());
				pst4.setInt(1,raAlcabalaSustentoId);
							
				pst4.executeUpdate();
				
				System.out.println("ra_alcabala_sustento ");	
			}
			
			
			//dt_determinacion
			StringBuffer SQLDet=new StringBuffer("SELECT determinacion_id FROM ").append(Constante.schemadb).append(".dt_determinacion");
			SQLDet.append(" WHERE persona_id=? and concepto_id=").append(Constante.CONCEPTO_ALCABALA).append(" and djreferencia_id=? and  estado = '").append(Constante.ESTADO_ACTIVO).append("' ");
			
			PreparedStatement pstDet=connect().prepareStatement(SQLDet.toString());
			pstDet.setInt(1,currentItem.getPersonaId());
			pstDet.setInt(2,currentItem.getDjId());
			
			ResultSet rs = pstDet.executeQuery();
			Integer determinacionId = 0; 
			
			while(rs.next()){
				
				determinacionId = rs.getInt("determinacion_id");				
			}
			
			if(determinacionId > 0){
				
				StringBuffer SQL5=new StringBuffer();
				SQL5.append(" UPDATE ").append(Constante.schemadb).append(".dt_determinacion ");
				SQL5.append(" SET estado = '").append(Constante.ESTADO_ELIMINADO).append("' ,");
				SQL5.append(" usuario_id = ").append(currentItem.getUsuarioId()).append(" ,");
				SQL5.append(" terminal = '").append(currentItem.getTerminal()).append("' ");
				SQL5.append(" WHERE determinacion_id=? ");

				PreparedStatement pst5=connect().prepareStatement(SQL5.toString());
				pst5.setInt(1,determinacionId);
				pst5.executeUpdate();
				
				System.out.println("dt_determinacion ");
				
				//cd_deuda
				StringBuffer SQL6=new StringBuffer();
				SQL6.append(" UPDATE ").append(Constante.schemadb).append(".cd_deuda ");
				SQL6.append(" SET estado = '").append(Constante.ESTADO_ELIMINADO).append("' ,");
				SQL6.append(" usuario_id = ").append(currentItem.getUsuarioId()).append(" ,");
				SQL6.append(" terminal = '").append(currentItem.getTerminal()).append("' ");
				SQL6.append(" WHERE determinacion_id=? ");

				PreparedStatement pst6=connect().prepareStatement(SQL6.toString());
				pst6.setInt(1,determinacionId);
				pst6.executeUpdate();
				
				System.out.println("cd_deuda ");
			}else{
				throw new SisatException("No tiene una determinacion asociada");
			}
			
		}catch(Exception e){
			throw new SisatException(e.getMessage());			
		}
	
	}

}
