package com.sat.sisat.persona.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATParameterFactory;
//import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.GnCondicionEspecial;
import com.sat.sisat.persistence.entity.GnCondicionEspecialPK;
import com.sat.sisat.persistence.entity.GnDepartamento;
import com.sat.sisat.persistence.entity.GnDistrito;
import com.sat.sisat.persistence.entity.GnDistritoPK;
import com.sat.sisat.persistence.entity.GnProvincia;
import com.sat.sisat.persistence.entity.GnProvinciaPK;
import com.sat.sisat.persistence.entity.GnTipoDenoUrbana;
import com.sat.sisat.persistence.entity.GnTipoEdificacion;
import com.sat.sisat.persistence.entity.GnTipoIngreso;
import com.sat.sisat.persistence.entity.GnTipoInterior;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.GnVia;
import com.sat.sisat.persistence.entity.MpDireccion;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.MpPersonaDomicilio;
import com.sat.sisat.persistence.entity.MpRelacionado;
import com.sat.sisat.persistence.entity.MpRelacionadoPK;
import com.sat.sisat.persistence.entity.MpSituacionEmpresarial;
import com.sat.sisat.persistence.entity.MpSubtipoPersona;
import com.sat.sisat.persistence.entity.MpTipoCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.MpTipoDocumentoCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoPersona;
import com.sat.sisat.persistence.entity.MpTipoPersonaTipoDocIden;
import com.sat.sisat.persistence.entity.MpTipoRelacion;
import com.sat.sisat.persistence.entity.TgPersona;
import com.sat.sisat.persona.dto.ParamBusquedaPersonaDTO;
import com.sat.sisat.persona.dto.PersonaEditTelEmailDTO;
import com.sat.sisat.persona.dto.PersonaEstadoCuentaResumido;
import com.sat.sisat.persona.dto.ValidaUbicacionDTO;
import com.sat.sisat.predial.dto.FindDireccion;
import com.sat.sisat.predial.dto.FindMpDireccion;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.predial.dto.FindMpRelacionado;
import com.sat.sisat.predial.dto.FindPersona;
import com.sat.sisat.tramitedocumentario.dto.RelacionadosDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

public class PersonaBusinessDao extends GeneralDao {

	public List<MpTipoDocuIdentidad> getAllMpTipoDocumento() throws Exception {
		List<MpTipoDocuIdentidad> list = new ArrayList<MpTipoDocuIdentidad>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select tipo_doc_identidad_id, descripcion, descrpcion_corta from  ");
			sql.append(Constante.schemadb).append(".mp_tipo_docu_identidad");
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				MpTipoDocuIdentidad obj = new MpTipoDocuIdentidad();
				obj.setTipoDocIdentidadId(rs.getInt(1));
				obj.setDescripcion(rs.getString(2));
				obj.setDescrpcionCorta(rs.getString(3));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	// GnTipoEdificacion
	public List<GnTipoEdificacion> getAllGnTipoEdificacion() throws Exception {
		List<GnTipoEdificacion> lista = new LinkedList<GnTipoEdificacion>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select tipo_edificacion_id,descripcion,descripcion_corta  from ");
			sql.append(Constante.schemadb).append(
					".gn_tipo_edificacion order by descripcion asc");

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnTipoEdificacion obj = new GnTipoEdificacion();
				obj.setTipoEdificacionId(rs.getInt("tipo_edificacion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));

				lista.add(obj);
			}

		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	// GnTipoIngreso
	public List<GnTipoIngreso> getAllGnTipoIngreso() throws Exception {
		List<GnTipoIngreso> lista = new LinkedList<GnTipoIngreso>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select tipo_ingreso_id,descripcion,descripcion_corta from ");
			sql.append(Constante.schemadb).append(
					".gn_tipo_ingreso order by descripcion asc");

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnTipoIngreso obj = new GnTipoIngreso();
				obj.setTipoIngresoId(rs.getInt("tipo_ingreso_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));

				lista.add(obj);
			}

		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	// GnTipoInterior
	public List<GnTipoInterior> getAllGnTipoInterior() throws Exception {
		List<GnTipoInterior> lista = new LinkedList<GnTipoInterior>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select tipo_interior_id,descripcion,descripcion_corta from ");
			sql.append(Constante.schemadb).append(
					".gn_tipo_interior order by descripcion asc");

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnTipoInterior obj = new GnTipoInterior();
				obj.setTipoInteriorId(rs.getInt("tipo_interior_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));

				lista.add(obj);
			}

		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<GnTipoDenoUrbana> getAllGnTipoDenoUrbana() throws Exception {
		List<GnTipoDenoUrbana> lista = new LinkedList<GnTipoDenoUrbana>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT tipo_deno_id,descripcion,descripcion_corta FROM ");
			sql.append(Constante.schemadb).append(
					".gn_tipo_deno_urbana order by descripcion asc");

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnTipoDenoUrbana obj = new GnTipoDenoUrbana();
				obj.setTipoDenoId(rs.getInt("tipo_deno_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public GnVia getGnVia(Integer ViaId) throws Exception {
		GnVia gnVia = new GnVia();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT t.tipo_via_id,t.descripcion,t.via_id, tv.descripcion descripciontipovia ");
			SQL.append("FROM ");
			SQL.append(Constante.schemadb).append(".gn_via t inner join ");
			SQL.append(Constante.schemadb).append(
					".gn_tipo_via tv on (tv.tipo_via_id=t.tipo_via_id) ");
			SQL.append("WHERE t.via_id=? ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, ViaId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				gnVia = new GnVia();
				gnVia.setViaId(rs.getInt("via_id"));
				gnVia.setDescripcion(rs.getString("descripcion"));
				gnVia.setTipoViaId(rs.getInt("tipo_via_id"));
				gnVia.setDescripcionTipoVia(rs.getString("descripciontipovia"));
			}
		} catch (Exception e) {
			throw (e);
		}
		return gnVia;
	}

	public List<MpPersona> listarPersonasxDni(String numeroDocumento)
			throws Exception {
		List<MpPersona> list = new ArrayList<MpPersona>();
		try {
			StringBuffer stB = new StringBuffer(200);
			stB.append("select tipo_persona_id, tipo_doc_identidad_id ,")
					.append("ISNULL(ape_paterno,'') apePaterno,")
					.append("ISNULL(ape_materno,'') apeMaterno,")
					.append("ISNULL(primer_nombre,'') primerNombre,")
					.append("ISNULL(segundo_nombre,'') segundoNombre,")
					.append("ISNULL(razon_social,'') razonSocial,")
					.append("nro_docu_identidad ").append("from ")
					.append(Constante.schemadb).append(".dbo.mp_persona ")
					.append("where nro_docu_identidad like '%")
					.append(numeroDocumento).append("%'");

			PreparedStatement pstmt = connect()
					.prepareStatement(stB.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MpPersona per = new MpPersona();
				per.setTipoPersonaId(rs.getInt(1));
				per.setTipoDocIdentidadId(rs.getInt(2));
				per.setApePaterno(rs.getString(3));
				per.setApeMaterno(rs.getString(4));
				per.setPrimerNombre(rs.getString(5));
				per.setSegundoNombre(rs.getString(6));
				per.setRazonSocial(rs.getString(7));
				per.setNroDocuIdentidad(rs.getString(8));
				per.setApellidosNombres(per.getApePaterno()
						+ per.getApeMaterno() + per.getPrimerNombre()
						+ per.getSegundoNombre());
				// per.setConcatenadoNombres(per.getApePaterno() +
				// per.getApeMaterno() + per.getPrimerNombre() +
				// per.getSegundoNombre());
				list.add(per);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
		}

		return list;

	}

	public List<GnTipoVia> getAllGnTipoVia() throws Exception {
		List<GnTipoVia> lista = new LinkedList<GnTipoVia>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT descripcion,descripcion_corta,tipo_via_id FROM ");
			sql.append(Constante.schemadb).append(
					".gn_tipo_via order by descripcion asc");
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnTipoVia obj = new GnTipoVia();
				obj.setTipoViaId(rs.getInt("tipo_via_id"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}

		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public ArrayList<FindPersona> getFindPersona() throws Exception {
		ArrayList<FindPersona> lista = new ArrayList<FindPersona>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT persona_id,tipo_persona_id,tipo_doc_identidad_id,nro_docu_identidad,ape_paterno,ape_materno,primer_nombre,razon_social,apellidos_nombres FROM ");
			SQL.append(Constante.schemadb).append(".mp_persona ");
			SQL.append("where tipo_persona_id=1 ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindPersona obj = new FindPersona();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				obj.setTipoDocIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApePaterno(rs.getString("ape_paterno"));
				obj.setApeMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public ArrayList<FindPersona> getFindEmpresas() throws Exception {
		ArrayList<FindPersona> lista = new ArrayList<FindPersona>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT persona_id,tipo_persona_id,tipo_doc_identidad_id,nro_docu_identidad,ape_paterno,ape_materno,primer_nombre,razon_social,apellidos_nombres FROM ");
			SQL.append(Constante.schemadb).append(".mp_persona ");
			SQL.append("where tipo_persona_id=2 ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindPersona obj = new FindPersona();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				obj.setTipoDocIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApePaterno(rs.getString("ape_paterno"));
				obj.setApeMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public Integer getPersonaId(Integer tipDoc, String numeroDoc)
			throws Exception {
		Integer personaId = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT persona_id FROM ");
			SQL.append(Constante.schemadb).append(".mp_persona ");
			SQL.append("where tipo_doc_identidad_id=? and nro_docu_identidad=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipDoc);
			pst.setString(2, numeroDoc);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				personaId = rs.getInt("persona_id");
			}
		} catch (Exception e) {
			throw (e);
		}
		return personaId;
	}

	public GnCondicionEspecial findCondicionEspecial(MpPersona mpersona)
			throws Exception {
		GnCondicionEspecial gnCondicionEspecial = null;
		try {

			StringBuffer SQL = new StringBuffer();
			SQL.append("select top 1 persona_id,condicion_especial_id,tipo_documento,tipo_cond_especial_id,nro_documento,fecha_inicio,fecha_fin,fecha_registro,fecha_documento,usuario_id,terminal, porcentaje from ");
			SQL.append(Constante.schemadb)
					.append(".gn_condicion_especial where persona_id=? and estado=? and tipo_cond_especial_id!=12 order by condicion_especial_id desc  ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, mpersona.getPersonaId());
			pst.setString(2, Constante.ESTADO_ACTIVO);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				gnCondicionEspecial = new GnCondicionEspecial();
				GnCondicionEspecialPK id = new GnCondicionEspecialPK();
				id.setPersonaId(rs.getInt("persona_id"));
				id.setCondicionEspecialId(rs.getInt("condicion_especial_id"));
				gnCondicionEspecial.setId(id);
				gnCondicionEspecial.setTipoDocumento(rs
						.getInt("tipo_documento"));
				gnCondicionEspecial.setTipoCondEspecialId(rs
						.getInt("tipo_cond_especial_id"));
				gnCondicionEspecial.setNroDocumento(rs
						.getString("nro_documento"));
				gnCondicionEspecial.setFechaInicio(rs
						.getTimestamp("fecha_inicio"));
				gnCondicionEspecial.setFechaFin(rs.getTimestamp("fecha_fin"));
				gnCondicionEspecial.setFechaRegistro(rs
						.getTimestamp("fecha_registro"));
				gnCondicionEspecial.setFechaDocumento(rs
						.getTimestamp("fecha_documento"));
				gnCondicionEspecial.setUsuarioId(rs.getInt("usuario_id"));
				gnCondicionEspecial.setTerminal(rs.getString("terminal"));
				gnCondicionEspecial.setPorcentaje(rs.getDouble("porcentaje"));
			}
		} catch (Exception e) {
			throw (e);
		}
		return gnCondicionEspecial;
	}

	public FindDireccion verificaDireccion(Integer djId, Integer idVia,
			String numero1, String letra1, String numero2, String letra2)
			throws Exception {
		FindDireccion direccion = null;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("select via_id,d.numero,d.letra,d.numero2,d.letra2,j.codigo_predio,j.desc_domicilio,j.fecha_declaracion ");
			SQL.append("from ").append(Constante.schemadb)
					.append(".rp_djpredial j ");
			SQL.append("inner join ")
					.append(Constante.schemadb)
					.append(".rp_djdireccion d on (d.dj_id=j.dj_id and d.estado='A') ");
			SQL.append("where j.estado='A' and j.flag_dj_anno='A' ");
			SQL.append("and d.via_id=? ");
			if (numero1 != null && numero1.trim().length() > 0) {
				SQL.append("and UPPER(ltrim(rtrim(d.numero)))=UPPER(ltrim(rtrim(?))) ");
			}

			if (letra1 != null && letra1.trim().length() > 0) {
				SQL.append("and UPPER(ltrim(rtrim(d.letra)))=UPPER(ltrim(rtrim(?)))  ");
			}

			if (numero2 != null && numero2.trim().length() > 0) {
				SQL.append("and UPPER(ltrim(rtrim(d.numero2)))=UPPER(ltrim(rtrim(?)))  ");
			}

			if (letra2 != null && letra2.trim().length() > 0) {
				SQL.append("and UPPER(ltrim(rtrim(d.letra2)))=UPPER(ltrim(rtrim(?)))  ");
			}

			int p = 1;
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(p, idVia);
			if (numero1 != null && numero1.trim().length() > 0) {
				pst.setString(++p, numero1);
			}

			if (letra1 != null && letra1.trim().length() > 0) {
				pst.setString(++p, letra1);
			}

			if (numero2 != null && numero2.trim().length() > 0) {
				pst.setString(++p, numero2);
			}

			if (letra2 != null && letra2.trim().length() > 0) {
				pst.setString(++p, letra2);
			}

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				direccion = new FindDireccion();
				direccion.setViaId(rs.getInt("via_id"));
				direccion.setNumero(rs.getString("numero"));
				direccion.setLetra(rs.getString("letra"));
				direccion.setNumero2(rs.getString("numero2"));
				direccion.setLetra2(rs.getString("letra2"));
				direccion.setCodigoPredio(rs.getString("codigo_predio"));
				direccion.setDescDomicilio(rs.getString("desc_domicilio"));
				direccion.setFechaDeclaracion(rs
						.getTimestamp("fecha_declaracion"));
			}
		} catch (Exception e) {
			throw (e);
		}
		return direccion;
	}

	public List<MpTipoDocumentoCondicionEspecial> findMpTipoDocumentoCondicionEspecial(
			Integer tipoDocumentoCondicionEspecial_id) throws Exception {
		List<MpTipoDocumentoCondicionEspecial> lista = new LinkedList<MpTipoDocumentoCondicionEspecial>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select tipo_documento_condicion_especial_id,descripcion,descripcion_corta,codigo_documento,estado from ");
			sql.append(Constante.schemadb)
					.append(".mp_tipo_documento_condicion_especial where estado=? and  tipo_documento=? ");

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setString(1, Constante.ESTADO_ACTIVO);
			pst.setInt(2, tipoDocumentoCondicionEspecial_id);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				MpTipoDocumentoCondicionEspecial obj = new MpTipoDocumentoCondicionEspecial();
				obj.setTipoDocumentoCondicionEspecialId(rs
						.getInt("tipo_documento_condicion_especial_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				obj.setCodigoDocumento(rs.getString("codigo_documento"));
				obj.setEstado(rs.getString("estado"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public int guardarMpRelacionado(MpRelacionado mpRelacionado)
			throws Exception {
		int result = 0;
		try {
			mpRelacionado.setApellidosNombres(mpRelacionado.getApePaterno()
					+ ' ' + mpRelacionado.getApeMaterno() + ' '
					+ mpRelacionado.getPrimerNombre() + ' '
					+ mpRelacionado.getSegundoNombre());
			StringBuffer SQL = new StringBuffer();
			SQL.append("INSERT INTO ")
					.append(Constante.schemadb)
					.append(".mp_relacionado(relacionado_id,persona_id,tipo_relacion_id,nro_docu_identidad ");
			SQL.append(" ,primer_nombre,segundo_nombre,ape_paterno,ape_materno,email,facebook,telefono ");
			SQL.append(" ,twitter,usuario_id,estado,terminal,fecha_registro,tipo_doc_identidad_id,apellidos_nombres)");
			SQL.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,GETDATE(),?,?) ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, mpRelacionado.getId().getRelacionadoId());// a
			pst.setInt(2, mpRelacionado.getId().getPersonaId());// a
			pst.setInt(3, mpRelacionado.getTipoRelacionId());// a
			pst.setString(4, mpRelacionado.getNroDocuIdentidad());// a
			pst.setString(5, mpRelacionado.getPrimerNombre());// a
			pst.setString(6, mpRelacionado.getSegundoNombre());// a
			pst.setString(7, mpRelacionado.getApePaterno());// a
			pst.setString(8, mpRelacionado.getApeMaterno());// a
			pst.setString(9, mpRelacionado.getEmail());// a
			pst.setString(10, mpRelacionado.getFacebook());// a
			pst.setString(11, mpRelacionado.getTelefono());// a
			pst.setString(12, mpRelacionado.getTwitter());// a
			pst.setInt(13, mpRelacionado.getUsuarioId());// a
			pst.setString(14, mpRelacionado.getEstado());// a
			pst.setString(15, mpRelacionado.getTerminal());// a
			pst.setInt(16, mpRelacionado.getMpTipoDocIdentidadId());// a
			pst.setString(17, mpRelacionado.getApellidosNombres());// a
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public int GenerarDjMpPersona(MpPersona mpPersona) throws Exception {
		int result = 0;
		try {

			StringBuffer SQL = new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb)
					.append(".mp_persona ");
			SQL.append(" SET nro_dj = ?  ");
			SQL.append(" ,estado = ? ");
			SQL.append(" ,nro_dj_formato = ? ");
			SQL.append(" WHERE persona_id = ? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,
					mpPersona.getNroDj() == null ? 0 : mpPersona.getNroDj());
			pst.setString(2, mpPersona.getEstado());
			pst.setString(3, mpPersona.getNroDjFormato());
			pst.setInt(4, mpPersona.getPersonaId());
			result = pst.executeUpdate();

		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public int ActualizarMpRelacionado(MpRelacionado mpRelacionado)
			throws Exception {
		int result = 0;
		try {
			mpRelacionado.setApellidosNombres(mpRelacionado.getApePaterno()
					+ ' ' + mpRelacionado.getApeMaterno() + ' '
					+ mpRelacionado.getPrimerNombre() + ' '
					+ mpRelacionado.getSegundoNombre());
			StringBuffer SQL = new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb)
					.append(".mp_relacionado ");
			SQL.append("SET tipo_relacion_id = ? ");
			SQL.append(",nro_docu_identidad = ? ");
			SQL.append(",primer_nombre = ? ");
			SQL.append(",segundo_nombre = ? ");
			SQL.append(",ape_paterno = ? ");
			SQL.append(",ape_materno = ? ");
			SQL.append(",email = ? ");
			SQL.append(",facebook = ? ");
			SQL.append(",telefono = ? ");
			SQL.append(",twitter = ? ");
			SQL.append(",usuario_id = ? ");
			SQL.append(",estado = ? ");
			SQL.append(",fecha_registro = ? ");
			SQL.append(",terminal = ? ");
			SQL.append(",tipo_doc_identidad_id = ?  ");
			SQL.append(",apellidos_nombres = ? ");
			SQL.append(" where persona_id=? and relacionado_id=?  ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, mpRelacionado.getTipoRelacionId());
			pst.setString(2, mpRelacionado.getNroDocuIdentidad());
			pst.setString(3, mpRelacionado.getPrimerNombre());
			pst.setString(4, mpRelacionado.getSegundoNombre());
			pst.setString(5, mpRelacionado.getApePaterno());
			pst.setString(6, mpRelacionado.getApeMaterno());
			pst.setString(7, mpRelacionado.getEmail());
			pst.setString(8, mpRelacionado.getFacebook());
			pst.setString(9, mpRelacionado.getTelefono());
			pst.setString(10, mpRelacionado.getTwitter());
			pst.setInt(11, mpRelacionado.getUsuarioId());
			pst.setString(12, mpRelacionado.getEstado());
			pst.setTimestamp(13, DateUtil.getCurrentDate());
			pst.setString(14, mpRelacionado.getTerminal());
			pst.setInt(15, mpRelacionado.getMpTipoDocIdentidadId());
			pst.setString(16, mpRelacionado.getApellidosNombres());
			pst.setInt(17, mpRelacionado.getId().getPersonaId());
			pst.setInt(18, mpRelacionado.getId().getRelacionadoId());
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public int ActualizarMpPersona(MpPersona mpPersona) throws Exception {
		int result = 0;
		try {

			StringBuffer SQL = new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb)
					.append(".mp_persona ");
			SQL.append(" SET estado = ? ");
			SQL.append(" WHERE persona_id = ? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Constante.ESTADO_ACTIVO);
			pst.setInt(2, mpPersona.getPersonaId());
			result = pst.executeUpdate();

		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public int darBajaCondicionEspecial(GnCondicionEspecial condicionEspecial)
			throws Exception {
		int result = 0;
		try {

			StringBuffer SQL = new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb)
					.append(".gn_condicion_especial ");
			SQL.append(" SET estado = ?  ");
			SQL.append(" WHERE condicion_especial_id = ? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Constante.ESTADO_INACTIVO);
			pst.setInt(2, condicionEspecial.getId().getCondicionEspecialId());
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public int desactiveCondicionEspecial(Integer personaId) throws Exception {
		int result = 0;
		try {

			StringBuffer SQL = new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb)
					.append(".gn_condicion_especial ");
			SQL.append(" SET estado = ?  ");
			SQL.append(" WHERE persona_id = ? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Constante.ESTADO_INACTIVO);
			pst.setInt(2, personaId);
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public int ActualizarCondicionEspecial(GnCondicionEspecial condicionEspecial)
			throws Exception {
		int result = 0;
		try {

			StringBuffer SQL = new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb)
					.append(".gn_condicion_especial ");
			SQL.append(" SET tipo_documento = ?  ");
			SQL.append(" , tipo_cond_especial_id = ?  ");
			SQL.append(" , nro_documento = ?  ");
			SQL.append(" , fecha_registro = ?  ");
			SQL.append(" , fecha_documento = ?  ");
			SQL.append(" , fecha_inicio = ?  ");
			SQL.append(" , fecha_fin = ?  ");
			SQL.append(" , usuario_id = ?  ");
			SQL.append(" , terminal = ?  ");
			SQL.append(" WHERE condicion_especial_id = ? and persona_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, condicionEspecial.getTipoDocumento());
			pst.setInt(2, condicionEspecial.getTipoCondEspecialId());
			pst.setString(3, condicionEspecial.getNroDocumento());
			pst.setTimestamp(4, condicionEspecial.getFechaRegistro());
			pst.setTimestamp(5, condicionEspecial.getFechaDocumento());
			pst.setTimestamp(6, condicionEspecial.getFechaInicio());
			pst.setTimestamp(7, condicionEspecial.getFechaFin());
			pst.setInt(8, condicionEspecial.getUsuarioId());
			pst.setString(9, obtenerTerminalCliente());
			pst.setInt(10, condicionEspecial.getId().getCondicionEspecialId());
			pst.setInt(11, condicionEspecial.getId().getPersonaId());

			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public int verificarTipoDocumentoTipoPersona(
			MpTipoPersonaTipoDocIden tipoperTipoDoc) throws Exception {
		int result = 0;
		try {

			StringBuffer SQL = new StringBuffer();
			SQL.append(" SELECT count(*) cantidad ");
			SQL.append(" FROM ").append(Constante.schemadb)
					.append(".mp_tipo_persona_tipo_doc_iden  ");
			SQL.append(" WHERE tipo_persona_id = ? and subtipo_persona_id=? and tipo_doc_identidad_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoperTipoDoc.getTipoPersonaId());
			pst.setInt(2, tipoperTipoDoc.getSubtipoPersonaId());
			pst.setInt(3, tipoperTipoDoc.getTipoDocIdentidadId());
			ResultSet rs = pst.executeQuery();
			rs.next();
			result = rs.getInt("cantidad");
			return result;
		} catch (Exception e) {
			throw (e);
		}
	}

	public ArrayList<Integer> getIdRelacionado(String apePaterno,
			String apeMaterno, String primerNombre, String apellidos_nombres,
			String documentoidentidad, Integer tipoDocumentoIdentidad)
			throws Exception {

		ArrayList<Integer> aIdRelacionado = new ArrayList<Integer>();

		if ((apePaterno != null && apePaterno.trim().length() > 2)
				|| (apeMaterno != null && apeMaterno.trim().length() > 2)
				|| (primerNombre != null && primerNombre.trim().length() > 2)
				|| (apellidos_nombres != null && apellidos_nombres.trim()
						.length() > 2)
				|| ((documentoidentidad != null && documentoidentidad.trim()
						.length() > 8) && (tipoDocumentoIdentidad != null && tipoDocumentoIdentidad > 0))) {

			StringBuffer SQL = new StringBuffer();
			SQL.append(" SELECT DISTINCT TOP 20 persona_id FROM dbo.mp_relacionado p ");
			SQL.append(" WHERE estado='1'  ");
			if (apellidos_nombres != null
					&& apellidos_nombres.trim().length() > 0)
				SQL.append(" AND p.apellidos_nombres like UPPER('")
						.append(apellidos_nombres).append("%')");
			if (apePaterno != null && apePaterno.trim().length() > 0)
				SQL.append(" AND p.ape_paterno like UPPER('")
						.append(apePaterno).append("%')");
			if (apeMaterno != null && apeMaterno.trim().length() > 0)
				SQL.append(" AND p.ape_materno like UPPER('")
						.append(apeMaterno).append("%')");
			if (primerNombre != null && primerNombre.trim().length() > 0)
				SQL.append(" AND p.primer_nombre like UPPER('")
						.append(primerNombre).append("%')");
			if (tipoDocumentoIdentidad != null && tipoDocumentoIdentidad > 0)
				SQL.append(" AND p.tipo_doc_identidad_id=")
						.append(tipoDocumentoIdentidad).append("");
			if (documentoidentidad != null
					&& documentoidentidad.trim().length() > 0)
				SQL.append(" AND p.nro_docu_identidad='")
						.append(documentoidentidad).append("'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				aIdRelacionado.add(rs.getInt("persona_id"));
			}
		}
		return aIdRelacionado;
	}

	public ArrayList<FindMpPersona> getmpPersona(Integer personaId,
			String apePaterno, String apeMaterno, String primerNombre,
			String apellidos_nombres, String documentoidentidad, Integer nroDj,
			Integer tipoDocumentoIdentidad, String razonSocial,
			String codigoAnterior, String placa) throws Exception {
		ArrayList<FindMpPersona> lista = new ArrayList<FindMpPersona>();

		ArrayList<Integer> aIdRelacionado = getIdRelacionado(apePaterno,
				apeMaterno, primerNombre, apellidos_nombres,
				documentoidentidad, tipoDocumentoIdentidad);

		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" select distinct TOP 50 p.persona_id,tp.descripcion tipo_persona,tp.tipo_persona_id ,tdi.descripcion tipo_documentoent,tdi.tipo_doc_identidad_id,p.ape_paterno,p.ape_materno,");
			SQL.append(" p.primer_nombre,p.segundo_nombre,p.razon_social,p.nro_docu_identidad, p.flag_notifica_email, ");
			SQL.append(" p.apellidos_nombres,");
			SQL.append(" (select COUNT(distinct djp.predio_id) from ")
					.append(Constante.schemadb)
					.append(".rp_djpredial djp where djp.persona_id=p.persona_id and djp.estado='")
					.append(Constante.ESTADO_ACTIVO)
					.append("' and djp.flag_dj_anno='")
					.append(Constante.FLAG_DJ_ANIO_ACTIVO)
					.append("' and motivo_declaracion_id != 4 and (motivo_descargo_id != 98 or motivo_descargo_id is null)) as predios,");
			SQL.append(" (select COUNT(distinct djv.vehiculo_id) from  ")
					.append(Constante.schemadb)
					.append(".rv_djvehicular djv where djv.persona_id=p.persona_id and djv.estado='")
					.append(Constante.ESTADO_ACTIVO)
					.append("') as vehiculos,d.direccion_completa,p.estado,p.nro_dj,p.nro_dj_formato");
			SQL.append(" ,stp.descripcion subtipopersona,p.subtipo_persona_id,p.fecha_declaracion,p.nro_docu_identidad_adi,p.telefono,p.fax,p.email,p.twitter,p.facebook,ce.condicion_especial_id,tce.descripcion tipocondicionespecial, td.tipo_documento_condicion_especial_id,td.descripcion tipodocumento,te.descripcion situacionempresarial  ");
			SQL.append(" ,p.fecha_situacion_empre ,p.fecha_fin_situacion_empresarial,p.fecha_inscripcion,p.tipo_doc_sust_situacion_empresarial ");
			SQL.append(" ,p.nro_doc_sust_situacion_empresarial,p.fecha_emision_situacion_empresarial,p.situacion_empresarial_id,p.nro_partida_defuncion,p.fecha_defuncion,ce.tipo_cond_especial_id,p.fecha_registro,ce.nro_documento cond_espe_nro_documento,ce.fecha_inicio cond_espe_fecha_inicio  ,ce.fecha_fin cond_espe_fecha_fin,ce.fecha_documento cond_espe_fecha_documento");
			SQL.append(" ,d.ubicacion_id, flag_estatal, p.glosa, ce.porcentaje, p.is_ubicable_control ");
			// Agrega el buscador de personas relacionados
			if (aIdRelacionado != null && aIdRelacionado.size() > 0) {
				SQL.append(" ,(rel.tipo_relacion +' '+rel.tipo_documento+' '+rel.nro_docu_identidad+' '+rel.apellidos_nombres) relacionado ");
			} else {
				SQL.append(" ,'' relacionado ");
			}
			SQL.append(" from ").append(Constante.schemadb)
					.append(".mp_persona p");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_persona tp on (p.tipo_persona_id=tp.tipo_persona_id)");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi on (p.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id)");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_subtipo_persona stp on tp.tipo_persona_id = stp.tipo_persona_id  and p.subtipo_persona_id=stp.subtipo_persona_id");
			SQL.append(" left join ")
					.append(Constante.schemadb)
					.append(".gn_condicion_especial ce on p.persona_id=ce.persona_id and ce.estado='1' ");

			SQL.append(" left join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_documento_condicion_especial td on td.tipo_documento_condicion_especial_id=ce.tipo_documento ");
			SQL.append(" left join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_condicion_especial tce on ce.tipo_cond_especial_id=tce.tipo_cond_especial_id ");
			SQL.append(" left join ")
					.append(Constante.schemadb)
					.append(".mp_situacion_empresarial te on p.situacion_empresarial_id=te.situacion_empresarial_id ");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_persona_domicilio pd on (p.persona_id=pd.persona_id and pd.flag_fiscal='1' and pd.domicilio_persona_id is not null and pd.estado IN ('1','2')) ");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_direccion d on (p.persona_id=d.persona_id and pd.direccion_id=d.direccion_id and d.estado IN ('1','2')) ");

			// Correccion de consulta inicio 12/02/2014 : inicio
			/*
			 * SQL.append(" left join ") .append(Constante.schemadb) .append(
			 * ".rv_vehiculo v on ( v.vehiculo_id in (select djv.vehiculo_id from rv_djvehicular djv where djv.persona_id = p.persona_id and djv.estado in ('1','2') )) "
			 * );
			 */
			SQL.append(" LEFT JOIN ")
					.append(Constante.schemadb)
					.append(".rv_djvehicular djv on (djv.persona_id=p.persona_id and djv.estado IN ('1','2')) ");
			SQL.append(" LEFT JOIN ")
					.append(Constante.schemadb)
					.append(".rv_vehiculo v on (v.vehiculo_id=djv.vehiculo_id) ");
			// Correccion de consulta inicio 12/02/2014 : fin

			// Agrega el buscador de personas relacionados
			if (aIdRelacionado != null && aIdRelacionado.size() > 0) {
				SQL.append(" LEFT JOIN  ( SELECT p.persona_id,p.nro_docu_identidad,t.descrpcion_corta tipo_documento,p.apellidos_nombres,r.descripcion tipo_relacion ");
				SQL.append(" FROM dbo.mp_relacionado p ");
				SQL.append(" INNER JOIN dbo.mp_tipo_docu_identidad t on (p.tipo_doc_identidad_id=t.tipo_doc_identidad_id) ");
				SQL.append(" INNER JOIN dbo.mp_tipo_relacion r on (r.tipo_relacion_id=p.tipo_relacion_id) ");
				SQL.append(" WHERE p.estado='1' AND p.persona_id IN (")
						.append(aIdRelacionado.toString().replace("[", "")
								.replace("]", ""))
						.append(") ) rel ON (rel.persona_id=p.persona_id) ");
			}
			SQL.append(" where p.estado IN ('1','2') ");
			if (personaId != null && personaId > 0)
				SQL.append(" and p.persona_id = ").append(personaId);
			if (nroDj != null && nroDj > 0)
				SQL.append(" and p.nro_dj = ").append(nroDj);
			if (apePaterno != null && apePaterno.trim().length() > 0)
				SQL.append(" and p.ape_paterno like UPPER('%")
						.append(apePaterno).append("%')");
			if (apeMaterno != null && apeMaterno.trim().length() > 0)
				SQL.append(" and p.ape_materno like UPPER('%")
						.append(apeMaterno).append("%')");
			if (primerNombre != null && primerNombre.trim().length() > 0)
				SQL.append(" and p.primer_nombre like UPPER('")
						.append(primerNombre).append("%')");
			if (apellidos_nombres != null
					&& apellidos_nombres.trim().length() > 0)
				SQL.append(" and p.apellidos_nombres like UPPER('%")
						.append(apellidos_nombres).append("%')");
			if (documentoidentidad != null
					&& documentoidentidad.trim().length() > 0)
				SQL.append(" and p.nro_docu_identidad ='")
						.append(documentoidentidad).append("'");
			if (tipoDocumentoIdentidad != null && tipoDocumentoIdentidad > 0)
				SQL.append(" and tdi.tipo_doc_identidad_id = ")
						.append(tipoDocumentoIdentidad).append("");
			if (razonSocial != null && razonSocial.trim().length() > 0)
				SQL.append(" and p.razon_social like UPPER('%")
						.append(razonSocial).append("%')");
			if (codigoAnterior != null && codigoAnterior.trim().length() > 0)
				SQL.append(" and RTRIM(LTRIM(p.cod_ant_satcaj)) = '")
						.append(codigoAnterior).append("'");
			if (placa != null && placa.trim().length() > 0)
				SQL.append(" and v.placa = UPPER('").append(placa.trim())
						.append("')");
			// .append(" or v.placa_anterior like UPPER('").append(placa).append("')");

			// Agrega el buscador de personas relacionados
			if (aIdRelacionado != null && aIdRelacionado.size() > 0) {
				SQL.append(" OR p.persona_id IN (")
						.append(aIdRelacionado.toString().replace("[", "")
								.replace("]", "")).append(") ");
			}

			SQL.append(" order by p.nro_dj ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindMpPersona obj = new FindMpPersona();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoPersona(rs.getString("tipo_persona"));
				obj.setTipoDocumentoIdentidad(rs.getString("tipo_documentoent"));
				obj.setApePaterno(rs.getString("ape_paterno"));
				obj.setApeMaterno(rs.getString("ape_Materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setPredios(rs.getInt("predios"));
				obj.setVehiculos(rs.getInt("vehiculos"));
				obj.setDomicilioPersona(rs.getString("direccion_completa"));
				obj.setEstado(rs.getString("estado"));
				if (obj.getEstado().compareTo(Constante.ESTADO_ACTIVO) == 0) {
					obj.setEstadoDescripcion(Constante.ESTADO_ACTIVO_DESCRIPCION);
					obj.setEstadoRegPersona(true);
				}
				if (obj.getEstado().compareTo(Constante.ESTADO_PENDIENTE) == 0) {
					obj.setEstadoDescripcion(Constante.ESTADO_PENDIENTE_DESCRIPCION);
					obj.setEstadoRegPersona(false);
				}
				obj.setNroDj(rs.getInt("nro_dj"));
				obj.setNroDjFormato(rs.getString("nro_dj_formato"));
				obj.setSubtipopersona(rs.getString("subtipopersona"));
				obj.setSubtipoPersonaId(rs.getInt("subtipo_persona_id"));
				obj.setFechaSituacionEmpresarial(rs
						.getTimestamp("fecha_situacion_empre"));
				obj.setFechaFinSituacionEmpresarial(rs
						.getTimestamp("fecha_fin_situacion_empresarial"));
				obj.setTipoDocSustSituacionEmpresarial(rs
						.getString("tipo_doc_sust_situacion_empresarial"));
				obj.setNroDocSustSituacionEmpresarial(rs
						.getString("nro_doc_sust_situacion_empresarial"));
				obj.setFechaEmisionSituacionEmpresarial(rs
						.getTimestamp("fecha_emision_situacion_empresarial"));
				obj.setSituacionEmpresarialId(rs
						.getInt("situacion_empresarial_id"));
				obj.setGncondicionEspecialId(rs.getInt("condicion_especial_id"));
				obj.setTipoCondicionEspecial(rs
						.getString("tipocondicionespecial"));
				obj.setTipodocumentoId(rs
						.getInt("tipo_documento_condicion_especial_id"));
				obj.setTipodocumento(rs.getString("tipodocumento"));
				obj.setSituacionEmpresarial(rs
						.getString("situacionempresarial"));
				obj.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				obj.setTipoDocIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setTelefono(rs.getString("telefono"));
				obj.setFax(rs.getString("fax"));
				obj.setEmail(rs.getString("email"));
				obj.setTwitter(rs.getString("twitter"));
				obj.setFacebook(rs.getString("facebook"));
				obj.setFlagNotificaEmail(rs.getString("flag_notifica_email"));
				obj.setNroPartidaDefuncion(rs
						.getString("nro_partida_defuncion"));
				obj.setFechaDefuncion(rs.getTimestamp("fecha_defuncion"));
				obj.setTipoCondicionEspecialId(rs
						.getInt("tipo_cond_especial_id"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setNroDocumentoCondContri(rs
						.getString("cond_espe_nro_documento"));
				obj.setFechaInicioCondContri(rs
						.getTimestamp("cond_espe_fecha_inicio"));
				obj.setFechafinCondContri(rs
						.getTimestamp("cond_espe_fecha_fin"));
				obj.setFechaDocumentoCondContri(rs
						.getTimestamp("cond_espe_fecha_documento"));
				obj.setFechaInscripcion(rs.getTimestamp("fecha_inscripcion"));
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				// Agrega el buscador de personas relacionados
				obj.setRelacionado(rs.getString("relacionado"));

				obj.setFlagEstatal(rs.getString("flag_estatal"));

				obj.setPorcentaje(rs.getDouble("porcentaje"));
				obj.setGlosa(rs.getString("glosa"));
				obj.setFlagUbicableControl(rs.getBoolean("is_ubicable_control"));

				MpPersona mpPersona = obtenerMpPersonaDeFindmpPersonavo(obj);
				obj.setMpPersona(mpPersona);
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public MpPersona obtenerMpPersonaDeFindmpPersonavo(FindMpPersona obj) {
		MpPersona mpPersona = new MpPersona();
		mpPersona.setPersonaId(obj.getPersonaId());
		mpPersona.setTipoPersonaId(obj.getTipoPersonaId());
		mpPersona.setTipoDocIdentidadId(obj.getTipoDocIdentidadId());
		mpPersona.setApellidosNombres(obj.getApellidosNombres());
		mpPersona.setApeMaterno(obj.getApeMaterno());
		mpPersona.setApePaterno(obj.getApePaterno());
		mpPersona.setPrimerNombre(obj.getPrimerNombre());
		mpPersona.setSegundoNombre(obj.getSegundoNombre());
		mpPersona.setNroDocuIdentidad(obj.getNroDocuIdentidad());
		mpPersona.setFechaDeclaracion(obj.getFechaDeclaracion());
		mpPersona.setSubtipoPersonaId(obj.getSubtipoPersonaId());
		mpPersona.setTelefono(obj.getTelefono());
		mpPersona.setFax(obj.getFax());
		mpPersona.setEmail(obj.getEmail());
		mpPersona.setTwitter(obj.getTwitter());
		mpPersona.setFacebook(obj.getFacebook());
		mpPersona.setRazonSocial(obj.getRazonSocial());
		mpPersona.setNroDocSustSituacionEmpresarial(obj
				.getNroDocSustSituacionEmpresarial());
		mpPersona.setTipoDocSustSituacionEmpresarial(obj
				.getTipoDocSustSituacionEmpresarial());
		mpPersona.setFlagNotificaEmail(obj.getFlagNotificaEmail());
		mpPersona.setNroDj(obj.getNroDj());
		mpPersona.setNroDjFormato(obj.getNroDjFormato());
		mpPersona.setSituacionEmpresarialId(obj.getSituacionEmpresarialId());
		mpPersona.setNroPartidaDefuncion(obj.getNroPartidaDefuncion());
		mpPersona.setFechaDefuncion(obj.getFechaDefuncion());
		mpPersona.setFechaRegistro(obj.getFechaRegistro());

		if (mpPersona.getFlagNotificaEmail() == Constante.FLAG_ACTIVO)
			mpPersona.setNotificaEmail(true);
		else
			mpPersona.setNotificaEmail(false);
		return mpPersona;
	}

	public FindMpPersona findmpPersona(Integer personaId) throws Exception {
		FindMpPersona obj = null;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("  select p.persona_id,p.tipo_persona_id,p.tipo_doc_identidad_id ");
			SQL.append(" ,p.ape_paterno,p.ape_materno,p.primer_nombre,p.segundo_nombre,p.razon_social ");
			SQL.append(" ,p.fecha_inscripcion,p.nro_docu_identidad,nro_docu_identidad_adi,p.telefono,p.fax,p.email,p.twitter,p.facebook ");
			SQL.append(" ,p.flag_notifica_email,p.fecha_declaracion,p.nro_dj,p.fecha_baja,p.nro_partida_defuncion,p.fecha_situacion_empre ");
			SQL.append(" ,p.fecha_fin_situacion_empresarial,p.apellidos_nombres,p.tipo_doc_sust_situacion_empresarial ");
			SQL.append(" ,p.nro_doc_sust_situacion_empresarial,p.fecha_emision_situacion_empresarial,p.situacion_empresarial_id ");
			SQL.append(" ,tp.descripcion tipo_persona,tdi.descripcion tipodocumentoidentidad,stp.descripcion subtipopersona,ce.condicion_especial_id,tce.descripcion tipocondicionespecial, td.tipo_documento_condicion_especial_id,td.descripcion tipodocumento,te.descripcion situacionempresarial  ");
			SQL.append(" ,ce.nro_documento,ce.fecha_registro,ce.fecha_inicio,ce.fecha_fin,ce.fecha_documento,p.estado,p.fecha_registro ");
			SQL.append(" ,p.subtipo_persona_id ");
			SQL.append(" from ").append(Constante.schemadb)
					.append(".mp_persona p ");
			SQL.append(" left join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_persona tp on p.tipo_persona_id=tp.tipo_persona_id ");
			SQL.append(" left join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi on p.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id ");
			SQL.append(" left join ")
					.append(Constante.schemadb)
					.append(".mp_subtipo_persona stp on p.subtipo_persona_id=stp.subtipo_persona_id ");
			SQL.append(" left join ")
					.append(Constante.schemadb)
					.append(".gn_condicion_especial ce on p.persona_id=ce.persona_id and ce.estado='1' ");
			SQL.append(" left join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_documento_condicion_especial td on td.tipo_documento_condicion_especial_id=ce.tipo_documento ");
			SQL.append(" left join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_condicion_especial tce on ce.tipo_cond_especial_id=tce.tipo_cond_especial_id ");
			SQL.append(" left join ")
					.append(Constante.schemadb)
					.append(".mp_situacion_empresarial te on p.situacion_empresarial_id=te.situacion_empresarial_id and te.estado='1' ");
			if (personaId > 0)
				SQL.append(" where p.persona_id = ").append(personaId)
						.append("");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				obj = new FindMpPersona();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				obj.setTipoDocumentoIdentidad(rs
						.getString("tipodocumentoidentidad"));
				obj.setApePaterno(rs.getString("ape_paterno"));
				obj.setApeMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setFechaInscripcion(rs.getTimestamp("fecha_inscripcion"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setNroDocIdentidadAdi(rs
						.getString("nro_docu_identidad_adi"));
				obj.setTelefono(rs.getString("telefono"));
				obj.setFax(rs.getString("fax"));
				obj.setEmail(rs.getString("email"));
				obj.setTwitter(rs.getString("twitter"));
				obj.setFacebook(rs.getString("facebook"));
				obj.setFlagNotificaEmail(rs.getString("flag_notifica_email"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setNroDj(rs.getInt("nro_dj"));
				obj.setFechaBaja(rs.getTimestamp("fecha_baja"));
				obj.setNroPartidaDefuncion(rs
						.getString("nro_partida_defuncion"));
				obj.setFechaSituacionEmpresarial(rs
						.getTimestamp("fecha_situacion_empre"));
				obj.setFechaFinSituacionEmpresarial(rs
						.getTimestamp("fecha_fin_situacion_empresarial"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setTipoDocSustSituacionEmpresarial(rs
						.getString("tipo_doc_sust_situacion_empresarial"));
				obj.setNroDocSustSituacionEmpresarial(rs
						.getString("nro_doc_sust_situacion_empresarial"));
				obj.setFechaEmisionSituacionEmpresarial(rs
						.getTimestamp("fecha_emision_situacion_empresarial"));
				obj.setSituacionEmpresarialId(rs
						.getInt("situacion_empresarial_id"));
				obj.setTipoPersona(rs.getString("tipo_persona"));
				obj.setSubtipopersona(rs.getString("subtipopersona"));
				obj.setGncondicionEspecialId(rs.getInt("condicion_especial_id"));
				obj.setTipoCondicionEspecial(rs
						.getString("tipocondicionespecial"));
				obj.setTipodocumentoId(rs
						.getInt("tipo_documento_condicion_especial_id"));
				obj.setTipodocumento(rs.getString("tipodocumento"));
				obj.setSituacionEmpresarial(rs
						.getString("situacionempresarial"));
				obj.setNroDocumentoCondContri(rs.getString("nro_documento"));
				obj.setFechaRegistroCondContri(rs
						.getTimestamp("fecha_registro"));
				obj.setFechaInicioCondContri(rs.getTimestamp("fecha_inicio"));
				obj.setFechafinCondContri(rs.getTimestamp("fecha_fin"));
				obj.setFechaDocumentoCondContri(rs
						.getTimestamp("fecha_documento"));
				obj.setEstado(rs.getString("estado"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setSubtipoPersonaId(rs.getInt("subtipo_persona_id"));
				obj.setTipoDocIdentidadId(rs.getInt("tipo_doc_identidad_id"));
			}
		} catch (Exception e) {
			throw (e);
		}
		return obj;
	}

	public FindMpPersona findmpPersona(String nroDocumento) throws Exception {
		FindMpPersona obj = null;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("  select p.persona_id,p.tipo_persona_id,p.tipo_doc_identidad_id ");
			SQL.append(" ,p.ape_paterno,p.ape_materno,p.primer_nombre,p.segundo_nombre,p.razon_social ");
			SQL.append(" ,p.fecha_inscripcion,p.nro_docu_identidad,nro_docu_identidad_adi,p.telefono,p.fax,p.email,p.twitter,p.facebook ");
			SQL.append("  ,p.flag_notifica_email,p.fecha_declaracion,p.nro_dj,p.fecha_baja,p.nro_partida_defuncion,p.fecha_situacion_empre ");
			SQL.append("  ,p.fecha_fin_situacion_empresarial,p.apellidos_nombres,p.tipo_doc_sust_situacion_empresarial ");
			SQL.append(" ,p.nro_doc_sust_situacion_empresarial,p.fecha_emision_situacion_empresarial,p.situacion_empresarial_id ");
			SQL.append(" ,tp.descripcion tipo_persona,tdi.descripcion tipodocumentoidentidad,stp.descripcion subtipopersona,ce.condicion_especial_id,tce.descripcion tipocondicionespecial, td.tipo_documento_condicion_especial_id,td.descripcion tipodocumento,te.descripcion situacionempresarial  ");
			SQL.append(" ,ce.nro_documento,ce.fecha_registro,ce.fecha_inicio,ce.fecha_fin,p.fecha_registro ");
			SQL.append(" from ").append(Constante.schemadb)
					.append(".mp_persona p ");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_persona tp on p.tipo_persona_id=tp.tipo_persona_id ");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi on p.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id ");
			SQL.append(" left join  ")
					.append(Constante.schemadb)
					.append(".mp_subtipo_persona stp on p.subtipo_persona_id=stp.subtipo_persona_id ");
			SQL.append(" left join  ")
					.append(Constante.schemadb)
					.append(".gn_condicion_especial ce on p.persona_id=ce.persona_id ");
			SQL.append(" left join  ")
					.append(Constante.schemadb)
					.append(".mp_tipo_documento_condicion_especial td on td.tipo_documento_condicion_especial_id=ce.tipo_documento ");
			SQL.append(" left join  ")
					.append(Constante.schemadb)
					.append(".mp_tipo_condicion_especial tce on ce.condicion_especial_id=tce.tipo_cond_especial_id ");
			SQL.append(" left join  ")
					.append(Constante.schemadb)
					.append(".mp_situacion_empresarial te on p.situacion_empresarial_id=te.situacion_empresarial_id ");
			SQL.append(" where p.nro_docu_identidad=? and (p.estado=? or p.estado=?)");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, nroDocumento);
			pst.setString(2, Constante.ESTADO_ACTIVO);
			pst.setString(3, Constante.ESTADO_PENDIENTE);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				obj = new FindMpPersona();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoPersona(rs.getString("tipo_persona_id"));
				obj.setTipoDocumentoIdentidad(rs
						.getString("tipo_doc_identidad_id"));
				obj.setApePaterno(rs.getString("ape_paterno"));
				obj.setApeMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setFechaInscripcion(rs.getTimestamp("fecha_inscripcion"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setNroDocIdentidadAdi(rs
						.getString("nro_docu_identidad_adi"));
				obj.setTelefono(rs.getString("telefono"));
				obj.setFax(rs.getString("fax"));
				obj.setEmail(rs.getString("email"));
				obj.setTwitter(rs.getString("twitter"));
				obj.setFacebook(rs.getString("facebook"));
				obj.setFlagNotificaEmail(rs.getString("flag_notifica_email"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setNroDj(rs.getInt("nro_dj"));
				obj.setFechaBaja(rs.getTimestamp("fecha_baja"));
				obj.setNroPartidaDefuncion(rs
						.getString("nro_partida_defuncion"));
				obj.setFechaSituacionEmpresarial(rs
						.getTimestamp("fecha_situacion_empre"));
				obj.setFechaFinSituacionEmpresarial(rs
						.getTimestamp("fecha_fin_situacion_empresarial"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setTipoDocSustSituacionEmpresarial(rs
						.getString("tipo_doc_sust_situacion_empresarial"));
				obj.setNroDocSustSituacionEmpresarial(rs
						.getString("nro_doc_sust_situacion_empresarial"));
				obj.setFechaEmisionSituacionEmpresarial(rs
						.getTimestamp("fecha_emision_situacion_empresarial"));
				obj.setSituacionEmpresarialId(rs
						.getInt("situacion_empresarial_id"));
				obj.setTipoPersona(rs.getString("tipo_persona"));
				obj.setTipoDocumentoIdentidad(rs
						.getString("tipodocumentoidentidad"));
				obj.setSubtipopersona(rs.getString("subtipopersona"));
				obj.setGncondicionEspecialId(rs.getInt("condicion_especial_id"));
				obj.setTipoCondicionEspecial(rs
						.getString("tipocondicionespecial"));
				obj.setTipodocumentoId(rs
						.getInt("tipo_documento_condicion_especial_id"));
				obj.setTipodocumento(rs.getString("tipodocumento"));
				obj.setSituacionEmpresarial(rs
						.getString("situacionempresarial"));
				obj.setNroDocumentoCondContri(rs.getString("nro_documento"));
				obj.setFechaRegistroCondContri(rs
						.getTimestamp("fecha_registro"));
				obj.setFechaInicioCondContri(rs.getTimestamp("fecha_inicio"));
				obj.setFechafinCondContri(rs.getTimestamp("fecha_fin"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				MpPersona mpPersona = new MpPersona(obj.getPersonaId());
				mpPersona.setNroDocuIdentidad(obj.getNroDocuIdentidad());
				obj.setMpPersona(mpPersona);
			}
		} catch (Exception e) {
			throw (e);
		}
		return obj;
	}

	public FindMpPersona findmpPersona(Integer tipodocumentoid,
			String nroDocumento) throws Exception {
		FindMpPersona obj = null;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("  select p.persona_id,p.tipo_persona_id,p.tipo_doc_identidad_id ");
			SQL.append(" ,p.ape_paterno,p.ape_materno,p.primer_nombre,p.segundo_nombre,p.razon_social ");
			SQL.append(" ,p.fecha_inscripcion,p.nro_docu_identidad,nro_docu_identidad_adi,p.telefono,p.fax,p.email,p.twitter,p.facebook ");
			SQL.append("  ,p.flag_notifica_email,p.fecha_declaracion,p.nro_dj,p.fecha_baja,p.nro_partida_defuncion,p.fecha_situacion_empre ");
			SQL.append("  ,p.fecha_fin_situacion_empresarial,p.apellidos_nombres,p.tipo_doc_sust_situacion_empresarial ");
			SQL.append(" ,p.nro_doc_sust_situacion_empresarial,p.fecha_emision_situacion_empresarial,p.situacion_empresarial_id ");
			SQL.append(" ,tp.descripcion tipo_persona,tdi.descripcion tipodocumentoidentidad,stp.descripcion subtipopersona,ce.condicion_especial_id,tce.descripcion tipocondicionespecial, td.tipo_documento_condicion_especial_id,td.descripcion tipodocumento,te.descripcion situacionempresarial  ");
			SQL.append(" ,ce.nro_documento,ce.fecha_registro,ce.fecha_inicio,ce.fecha_fin,p.fecha_registro ");
			SQL.append(" from ").append(Constante.schemadb)
					.append(".mp_persona p ");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_persona tp on p.tipo_persona_id=tp.tipo_persona_id ");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi on p.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id ");
			SQL.append(" left join  ")
					.append(Constante.schemadb)
					.append(".mp_subtipo_persona stp on p.subtipo_persona_id=stp.subtipo_persona_id ");
			SQL.append(" left join  ")
					.append(Constante.schemadb)
					.append(".gn_condicion_especial ce on p.persona_id=ce.persona_id ");
			SQL.append(" left join  ")
					.append(Constante.schemadb)
					.append(".mp_tipo_documento_condicion_especial td on td.tipo_documento_condicion_especial_id=ce.tipo_documento ");
			SQL.append(" left join  ")
					.append(Constante.schemadb)
					.append(".mp_tipo_condicion_especial tce on ce.condicion_especial_id=tce.tipo_cond_especial_id ");
			SQL.append(" left join  ")
					.append(Constante.schemadb)
					.append(".mp_situacion_empresarial te on p.situacion_empresarial_id=te.situacion_empresarial_id ");
			SQL.append(" where p.nro_docu_identidad=? and (p.estado=? or p.estado=?) and p.tipo_doc_identidad_id=?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, nroDocumento);
			pst.setString(2, Constante.ESTADO_ACTIVO);
			pst.setString(3, Constante.ESTADO_PENDIENTE);
			pst.setInt(4, tipodocumentoid);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				obj = new FindMpPersona();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoPersona(rs.getString("tipo_persona_id"));
				obj.setTipoDocumentoIdentidad(rs
						.getString("tipo_doc_identidad_id"));
				obj.setApePaterno(rs.getString("ape_paterno"));
				obj.setApeMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setFechaInscripcion(rs.getTimestamp("fecha_inscripcion"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setNroDocIdentidadAdi(rs
						.getString("nro_docu_identidad_adi"));
				obj.setTelefono(rs.getString("telefono"));
				obj.setFax(rs.getString("fax"));
				obj.setEmail(rs.getString("email"));
				obj.setTwitter(rs.getString("twitter"));
				obj.setFacebook(rs.getString("facebook"));
				obj.setFlagNotificaEmail(rs.getString("flag_notifica_email"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setNroDj(rs.getInt("nro_dj"));
				obj.setFechaBaja(rs.getTimestamp("fecha_baja"));
				obj.setNroPartidaDefuncion(rs
						.getString("nro_partida_defuncion"));
				obj.setFechaSituacionEmpresarial(rs
						.getTimestamp("fecha_situacion_empre"));
				obj.setFechaFinSituacionEmpresarial(rs
						.getTimestamp("fecha_fin_situacion_empresarial"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setTipoDocSustSituacionEmpresarial(rs
						.getString("tipo_doc_sust_situacion_empresarial"));
				obj.setNroDocSustSituacionEmpresarial(rs
						.getString("nro_doc_sust_situacion_empresarial"));
				obj.setFechaEmisionSituacionEmpresarial(rs
						.getTimestamp("fecha_emision_situacion_empresarial"));
				obj.setSituacionEmpresarialId(rs
						.getInt("situacion_empresarial_id"));
				obj.setTipoPersona(rs.getString("tipo_persona"));
				obj.setTipoDocumentoIdentidad(rs
						.getString("tipodocumentoidentidad"));
				obj.setSubtipopersona(rs.getString("subtipopersona"));
				obj.setGncondicionEspecialId(rs.getInt("condicion_especial_id"));
				obj.setTipoCondicionEspecial(rs
						.getString("tipocondicionespecial"));
				obj.setTipodocumentoId(rs
						.getInt("tipo_documento_condicion_especial_id"));
				obj.setTipodocumento(rs.getString("tipodocumento"));
				obj.setSituacionEmpresarial(rs
						.getString("situacionempresarial"));
				obj.setNroDocumentoCondContri(rs.getString("nro_documento"));
				obj.setFechaRegistroCondContri(rs
						.getTimestamp("fecha_registro"));
				obj.setFechaInicioCondContri(rs.getTimestamp("fecha_inicio"));
				obj.setFechafinCondContri(rs.getTimestamp("fecha_fin"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				MpPersona mpPersona = new MpPersona(obj.getPersonaId());
				mpPersona.setNroDocuIdentidad(obj.getNroDocuIdentidad());
				obj.setMpPersona(mpPersona);
			}
		} catch (Exception e) {
			throw (e);
		}
		return obj;
	}

	public FindMpPersona existeContribuyente(Integer tipoContribuyente,
			Integer subTipoContribuyente, Integer tipodocumentoid,
			String nroDocumento) throws Exception {
		FindMpPersona obj = null;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" select p.persona_id,tdi.tipo_doc_identidad_id,tdi.descripcion tipo_doc_identidad,p.nro_docu_identidad,p.apellidos_nombres,p.razon_social,tp.descripcion tipo_persona,stp.descripcion subtipopersona ");
			SQL.append(" from " + SATParameterFactory.getDBNameScheme()
					+ ".mp_persona p  ");
			SQL.append(" inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_tipo_docu_identidad tdi on p.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id ");
			SQL.append(" inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_tipo_persona tp on p.tipo_persona_id=tp.tipo_persona_id  ");
			SQL.append(" inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_subtipo_persona stp on p.subtipo_persona_id=stp.subtipo_persona_id ");
			SQL.append(" where p.tipo_doc_identidad_id=? and p.nro_docu_identidad=? and p.tipo_persona_id=? and p.subtipo_persona_id=? and p.estado='1' ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipodocumentoid);
			pst.setString(2, nroDocumento);
			pst.setInt(3, tipoContribuyente);
			pst.setInt(4, subTipoContribuyente);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				obj = new FindMpPersona();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipodocumentoId(rs.getInt("tipo_doc_identidad_id"));
				obj.setTipodocumento(rs.getString("tipo_doc_identidad"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setTipoPersona(rs.getString("tipo_persona"));
				obj.setSubtipopersona(rs.getString("subtipopersona"));
			}
		} catch (Exception e) {
			throw (e);
		}
		return obj;
	}

	public ArrayList<MpTipoPersona> findMpTipoPersona(Integer tipoPersonaId)
			throws Exception {
		ArrayList<MpTipoPersona> lMpTipoPersona = new ArrayList<MpTipoPersona>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"select tp.tipo_persona_id,tp.descripcion,tp.descripcion_corta from ")
					.append(Constante.schemadb)
					.append(".mp_tipo_persona tp WHERE tp.tipo_persona_id =? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoPersonaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				MpTipoPersona mpTipoPersona = new MpTipoPersona();
				mpTipoPersona.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				mpTipoPersona.setDescripcion(rs.getString("descripcion"));
				mpTipoPersona.setDescripcionCorta(rs
						.getString("descripcion_corta"));
				lMpTipoPersona.add(mpTipoPersona);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lMpTipoPersona;
	}

	public ArrayList<MpRelacionado> findMpRelacionado(Integer Persona_id)
			throws Exception {
		ArrayList<MpRelacionado> lMpRelacionado = new ArrayList<MpRelacionado>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT persona_id,relacionado_id,tipo_relacion_id,tipo_doc_identidad_id,nro_docu_identidad,primer_nombre,segundo_nombre,ape_paterno,ape_materno,email,facebook ");
			SQL.append(
					" ,telefono,twitter,usuario_id,estado,fecha_registro,terminal,apellidos_nombres  FROM ")
					.append(Constante.schemadb)
					.append(".mp_relacionado WHERE persona_id=? and estado='1' ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Persona_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				MpRelacionado mpRelacionado = new MpRelacionado();
				MpRelacionadoPK id = new MpRelacionadoPK(
						rs.getInt("persona_id"), rs.getInt("relacionado_id"));
				mpRelacionado.setId(id);
				mpRelacionado.setTipoRelacionId(rs.getInt("tipo_relacion_id"));
				mpRelacionado.setMpTipoDocIdentidadId(rs
						.getInt("tipo_doc_identidad_id"));
				mpRelacionado.setNroDocuIdentidad(rs
						.getString("nro_docu_identidad"));
				mpRelacionado.setPrimerNombre(rs.getString("primer_nombre"));
				mpRelacionado.setSegundoNombre(rs.getString("segundo_nombre"));
				mpRelacionado.setApePaterno(rs.getString("ape_paterno"));
				mpRelacionado.setApeMaterno(rs.getString("ape_materno"));
				mpRelacionado.setEmail(rs.getString("email"));
				mpRelacionado.setFacebook(rs.getString("facebook"));
				mpRelacionado.setTelefono(rs.getString("telefono"));
				mpRelacionado.setUsuarioId(rs.getInt("usuario_id"));
				mpRelacionado.setEstado(rs.getString("estado"));
				mpRelacionado.setFechaRegistro(rs
						.getTimestamp("fecha_registro"));
				mpRelacionado.setTerminal(rs.getString("terminal"));
				mpRelacionado.setApellidosNombres(rs
						.getString("apellidos_nombres"));
				lMpRelacionado.add(mpRelacionado);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lMpRelacionado;
	}

	public ArrayList<FindMpRelacionado> findMpRelacionadoPersona(
			Integer Persona_id) throws Exception {
		ArrayList<FindMpRelacionado> lMpRelacionado = new ArrayList<FindMpRelacionado>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT r.persona_id,r.relacionado_id,r.tipo_relacion_id,r.tipo_doc_identidad_id,r.nro_docu_identidad,r.primer_nombre,r.segundo_nombre,r.ape_paterno,r.ape_materno,r.email,r.facebook ");
			SQL.append(" ,r.telefono,r.twitter,r.usuario_id,r.estado,r.fecha_registro,r.terminal,r.apellidos_nombres ");
			SQL.append(" ,tr.descripcion as descripciontiporelacion,tdi.descripcion as descripciontipodocumento");
			SQL.append(" FROM ").append(Constante.schemadb)
					.append(".mp_relacionado r");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_relacion tr on r.tipo_relacion_id=tr.tipo_relacion_id");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi on r.tipo_doc_identidad_id = tdi.tipo_doc_identidad_id");
			SQL.append(" WHERE persona_id=? and (r.estado=? or r.estado=? )");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Persona_id);
			pst.setString(2, Constante.ESTADO_ACTIVO);
			pst.setString(3, Constante.ESTADO_PENDIENTE);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindMpRelacionado mpRelacionado = new FindMpRelacionado();
				mpRelacionado.setPersonaId(rs.getInt("persona_id"));
				mpRelacionado.setRelacionadoId(rs.getInt("relacionado_id"));
				mpRelacionado.setTipoRelacionId(rs.getInt("tipo_relacion_id"));
				mpRelacionado.setMpTipoDocIdentidadId(rs
						.getInt("tipo_doc_identidad_id"));
				mpRelacionado.setNroDocuIdentidad3(rs
						.getString("nro_docu_identidad"));
				mpRelacionado.setPrimerNombre(rs.getString("primer_nombre"));
				mpRelacionado.setSegundoNombre(rs.getString("segundo_nombre"));
				mpRelacionado.setApePaterno(rs.getString("ape_paterno"));
				mpRelacionado.setApeMaterno(rs.getString("ape_materno"));
				mpRelacionado.setEmail(rs.getString("email"));
				mpRelacionado.setTwitter(rs.getString("twitter"));
				mpRelacionado.setFacebook(rs.getString("facebook"));
				mpRelacionado.setTelefono(rs.getString("telefono"));
				mpRelacionado.setUsuarioId(rs.getInt("usuario_id"));
				mpRelacionado.setEstado(rs.getString("estado"));
				mpRelacionado.setFechaRegistro(rs
						.getTimestamp("fecha_registro"));
				mpRelacionado.setTerminal(rs.getString("terminal"));
				mpRelacionado.setApellidosNombres(rs
						.getString("apellidos_nombres"));
				mpRelacionado.setRelacionadoDescripcion(rs
						.getString("descripciontiporelacion"));
				mpRelacionado.setMpTipoDocIdentidadDescripcion(rs
						.getString("descripciontipodocumento"));
				mpRelacionado
						.setMpRelacionado(obtenerMpRelacionadoPersonaDeFindMpRelacionadoPersona(mpRelacionado));
				lMpRelacionado.add(mpRelacionado);

			}
		} catch (Exception e) {
			throw (e);
		}
		return lMpRelacionado;
	}

	public FindMpRelacionado findMpRelacionadoPersona(Integer Persona_id,
			Integer relacionado_id) throws Exception {
		FindMpRelacionado mpRelacionado = new FindMpRelacionado();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT r.persona_id,r.relacionado_id,r.tipo_relacion_id,r.tipo_doc_identidad_id,r.nro_docu_identidad,r.primer_nombre,r.segundo_nombre,r.ape_paterno,r.ape_materno,r.email,r.facebook ");
			SQL.append(" ,r.telefono,r.twitter,r.usuario_id,r.estado,r.fecha_registro,r.terminal,r.apellidos_nombres ");
			SQL.append(" ,tr.descripcion as descripciontiporelacion,tdi.descripcion as descripciontipodocumento");
			SQL.append(" FROM ").append(Constante.schemadb)
					.append(".mp_relacionado r");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_relacion tr on r.tipo_relacion_id=tr.tipo_relacion_id");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi on r.tipo_doc_identidad_id = tdi.tipo_doc_identidad_id");
			SQL.append(" WHERE r.persona_id=? and r.relacionado_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Persona_id);
			pst.setInt(2, relacionado_id);
			ResultSet rs = pst.executeQuery();
			rs.next();
			mpRelacionado = new FindMpRelacionado();
			mpRelacionado.setPersonaId(rs.getInt("persona_id"));
			mpRelacionado.setRelacionadoId(rs.getInt("relacionado_id"));
			mpRelacionado.setTipoRelacionId(rs.getInt("tipo_relacion_id"));
			mpRelacionado.setMpTipoDocIdentidadId(rs
					.getInt("tipo_doc_identidad_id"));
			mpRelacionado.setNroDocuIdentidad3(rs
					.getString("nro_docu_identidad"));
			mpRelacionado.setPrimerNombre(rs.getString("primer_nombre"));
			mpRelacionado.setSegundoNombre(rs.getString("segundo_nombre"));
			mpRelacionado.setApePaterno(rs.getString("ape_paterno"));
			mpRelacionado.setApeMaterno(rs.getString("ape_materno"));
			mpRelacionado.setEmail(rs.getString("email"));
			mpRelacionado.setFacebook(rs.getString("facebook"));
			mpRelacionado.setTwitter(rs.getString("twitter"));
			mpRelacionado.setTelefono(rs.getString("telefono"));
			mpRelacionado.setUsuarioId(rs.getInt("usuario_id"));
			mpRelacionado.setEstado(rs.getString("estado"));
			mpRelacionado.setFechaRegistro(rs.getTimestamp("fecha_registro"));
			mpRelacionado.setTerminal(rs.getString("terminal"));
			mpRelacionado
					.setApellidosNombres(rs.getString("apellidos_nombres"));
			mpRelacionado.setRelacionadoDescripcion(rs
					.getString("descripciontiporelacion"));
			mpRelacionado.setMpTipoDocIdentidadDescripcion(rs
					.getString("descripciontipodocumento"));
			mpRelacionado
					.setMpRelacionado(obtenerMpRelacionadoPersonaDeFindMpRelacionadoPersona(mpRelacionado));
			return mpRelacionado;

		} catch (Exception e) {
			throw (e);
		}
		// return mpRelacionado;
	}

	public MpRelacionado obtenerMpRelacionadoPersonaDeFindMpRelacionadoPersona(
			FindMpRelacionado mpRelacionado) {
		MpRelacionado mpRelacionadoTemp = new MpRelacionado();
		MpRelacionadoPK mpRelacionadoPKTemp = new MpRelacionadoPK();
		mpRelacionadoPKTemp.setPersonaId(mpRelacionado.getPersonaId());
		mpRelacionadoPKTemp.setRelacionadoId(mpRelacionado.getRelacionadoId());
		mpRelacionadoTemp.setId(mpRelacionadoPKTemp);
		mpRelacionadoTemp.setApePaterno(mpRelacionado.getApePaterno());
		mpRelacionadoTemp.setApeMaterno(mpRelacionado.getApeMaterno());
		mpRelacionadoTemp.setApellidosNombres(mpRelacionado
				.getApellidosNombres());
		mpRelacionadoTemp.setPrimerNombre(mpRelacionado.getPrimerNombre());
		mpRelacionadoTemp.setSegundoNombre(mpRelacionado.getSegundoNombre());
		mpRelacionadoTemp.setEmail(mpRelacionado.getEmail());
		mpRelacionadoTemp.setEstado(mpRelacionado.getEstado());
		mpRelacionadoTemp.setFacebook(mpRelacionado.getFacebook());
		mpRelacionadoTemp.setTelefono(mpRelacionado.getTelefono());
		mpRelacionadoTemp.setTwitter(mpRelacionado.getTwitter());
		mpRelacionadoTemp.setTipoRelacionId(mpRelacionado.getTipoRelacionId());
		mpRelacionadoTemp.setMpTipoDocIdentidadId(mpRelacionado
				.getMpTipoDocIdentidadId());
		mpRelacionadoTemp.setNroDocuIdentidad(mpRelacionado
				.getNroDocuIdentidad3());
		return mpRelacionadoTemp;

	}
	
	public ArrayList<RelacionadosDTO> GetRelacionadosNew(int persona_id) {

		ArrayList<RelacionadosDTO> listaRelacionados = new ArrayList<RelacionadosDTO>();

		try {
			String sql = "stp_dt_HR_subreporte_relacionados ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setInt(1, persona_id);	
			
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {// ***
				RelacionadosDTO item = new RelacionadosDTO();
				
				item.setItem(rs.getInt("item"));
				item.setRelacionadoId(rs.getInt("relacionado_id"));
				item.setTiporelacion(rs.getString("relacionado"));
				item.setPorcParticipacion(rs.getBigDecimal("porc_participacion"));
				item.setApellidosNombres(rs.getString("apellidos_nombres"));
				item.setTipoDocIdentidad(rs.getString("descripcion"));
				item.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				listaRelacionados.add(item);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listaRelacionados;
	}
	

	public List<MpTipoPersona> getAllMpTipoPersona() throws Exception {
		List<MpTipoPersona> lista = new LinkedList<MpTipoPersona>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"select tp.tipo_persona_id,tp.descripcion,tp.descripcion_corta from ")
					.append(Constante.schemadb)
					.append(".mp_tipo_persona tp where tp.estado=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Constante.ESTADO_ACTIVO);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				MpTipoPersona obj = new MpTipoPersona();
				obj.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				lista.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<MpTipoRelacion> getAllMpTipoRelacion() throws Exception {
		List<MpTipoRelacion> lista = new LinkedList<MpTipoRelacion>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"SELECT tipo_relacion_id,descripcion,descripcion_alterna,usuario_id,estado,fecha_registro,terminal FROM ")
					.append(Constante.schemadb).append(".mp_tipo_relacion");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				MpTipoRelacion obj = new MpTipoRelacion();
				obj.setTipoRelacionId(rs.getInt("tipo_relacion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionAlterna(rs.getString("descripcion_alterna"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setEstado(rs.getString("estado"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setTerminal(rs.getString("terminal"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<MpTipoRelacion> findMpTipoRelacion(Integer tipoRelacionId)
			throws Exception {
		List<MpTipoRelacion> lista = new LinkedList<MpTipoRelacion>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"SELECT tipo_relacion_id,descripcion,descripcion_alterna,usuario_id,estado,fecha_registro,terminal FROM ")
					.append(Constante.schemadb)
					.append(".mp_tipo_relacion where tipo_relacion_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoRelacionId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				MpTipoRelacion obj = new MpTipoRelacion();
				obj.setTipoRelacionId(rs.getInt("tipo_relacion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionAlterna(rs.getString("descripcion_alterna"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setEstado(rs.getString("estado"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setTerminal(rs.getString("terminal"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public ArrayList<MpTipoDocuIdentidad> findMpTipoDocuIdentidad(
			Integer tipoPersonaId) throws Exception {
		ArrayList<MpTipoDocuIdentidad> lMpTipoDocuIdentidad = new ArrayList<MpTipoDocuIdentidad>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"select tdi.tipo_doc_identidad_id,tdi.descripcion,tdi.descrpcion_corta from ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi WHERE tdi.tipo_doc_identidad_id=? ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoPersonaId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				MpTipoDocuIdentidad mpTipoDocuIdentidad = new MpTipoDocuIdentidad();
				mpTipoDocuIdentidad.setTipoDocIdentidadId(rs
						.getInt("tipo_doc_identidad_id"));
				mpTipoDocuIdentidad.setDescripcion(rs.getString("descripcion"));
				mpTipoDocuIdentidad.setDescrpcionCorta(rs
						.getString("descrpcion_corta"));
				lMpTipoDocuIdentidad.add(mpTipoDocuIdentidad);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lMpTipoDocuIdentidad;
	}

	public ArrayList<MpTipoDocuIdentidad> findMpTipoDocuIdentidad(
			Integer tipoPersonaId, Integer subtipoPersonaId) throws Exception {
		ArrayList<MpTipoDocuIdentidad> lMpTipoDocuIdentidad = new ArrayList<MpTipoDocuIdentidad>();
		try {
			StringBuffer SQL = new StringBuffer();
			// if (subtipoPersonaId != null) {
			SQL.append("  select tdi.tipo_doc_identidad_id,tdi.descripcion,tdi.descrpcion_corta ");
			SQL.append("  from ").append(Constante.schemadb)
					.append(".mp_tipo_persona_tipo_doc_iden tptd   ");
			SQL.append("  inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi on tptd.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id ");
			SQL.append("  inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_persona tp on tptd.tipo_persona_id=tp.tipo_persona_id ");
			SQL.append("  inner join ")
					.append(Constante.schemadb)
					.append(".mp_subtipo_persona stp on tptd.subtipo_persona_id=stp.subtipo_persona_id ");
			SQL.append("  where tptd.tipo_persona_id=? and tptd.subtipo_persona_id=? ");
			// }
			// else {
			// SQL.append("  select tdi.tipo_doc_identidad_id,tdi.descripcion,tdi.descrpcion_corta ");
			// SQL.append("  from ").append(Constante.schemadb).append(".mp_tipo_persona_tipo_doc_iden tptd   ");
			// SQL.append("  inner join ").append(Constante.schemadb)
			// .append(".mp_tipo_docu_identidad tdi on tptd.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id ");
			// SQL.append("  inner join ").append(Constante.schemadb)
			// .append(".mp_tipo_persona tp on tptd.tipo_persona_id=tp.tipo_persona_id ");
			// SQL.append("  inner join ").append(Constante.schemadb)
			// .append(".mp_subtipo_persona stp on tptd.subtipo_persona_id=stp.subtipo_persona_id ");
			// SQL.append("  where tptd.tipo_persona_id=? and tptd.subtipo_persona_id=? ");
			// }
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			if (subtipoPersonaId == null
					|| subtipoPersonaId == Constante.SUB_TIPO_PERSONA_COPROPIETARIO_ID
					|| subtipoPersonaId == Constante.SUB_TIPO_PERSONA_SUC_INDIVISA_ID) {
				pst.setInt(1, Constante.TIPO_PERSONA_NATURAL_ID);
				pst.setInt(2, Constante.SUB_TIPO_PERSONA_INDIVIDUAL_ID);
			} else {
				pst.setInt(1, tipoPersonaId);
				pst.setInt(2, subtipoPersonaId);

			}
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				MpTipoDocuIdentidad mpTipoDocuIdentidad = new MpTipoDocuIdentidad();
				mpTipoDocuIdentidad.setTipoDocIdentidadId(rs
						.getInt("tipo_doc_identidad_id"));
				mpTipoDocuIdentidad.setDescripcion(rs.getString("descripcion"));
				mpTipoDocuIdentidad.setDescrpcionCorta(rs
						.getString("descrpcion_corta"));
				lMpTipoDocuIdentidad.add(mpTipoDocuIdentidad);

			}
		} catch (Exception e) {
			throw (e);
		}
		return lMpTipoDocuIdentidad;
	}

	public List<MpTipoDocuIdentidad> getAllMpTipoDocumentoIdentidad()
			throws Exception {
		List<MpTipoDocuIdentidad> lista = new LinkedList<MpTipoDocuIdentidad>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"select tdi.tipo_doc_identidad_id,tdi.descripcion,tdi.descrpcion_corta from ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				MpTipoDocuIdentidad obj = new MpTipoDocuIdentidad();
				obj.setTipoDocIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescrpcionCorta(rs.getString("descrpcion_corta"));

				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<MpTipoCondicionEspecial> getAllMpTipoCondicionEspecial(
			Integer tipoPersonaId, Integer subTipoPersonaId) throws Exception {
		List<MpTipoCondicionEspecial> lista = new LinkedList<MpTipoCondicionEspecial>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("select tce.tipo_cond_especial_id,tce.descripcion,tce.estado,tce.usuario_id,tce.fecha_registro,tce.terminal ");
			SQL.append("from  ").append(Constante.schemadb)
					.append(".mp_tipo_per_cond_especial tpce  ");
			SQL.append("inner join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_condicion_especial tce on tpce.tipo_cond_especial_id = tce.tipo_cond_especial_id ");
			SQL.append("where tpce.tipo_persona_id = ? and tpce.estado = ? and tpce.subtipo_persona_id = ? and tpce.flag_situacion!=1");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoPersonaId);
			pst.setString(2, Constante.ESTADO_ACTIVO);
			pst.setInt(3, subTipoPersonaId);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				MpTipoCondicionEspecial obj = new MpTipoCondicionEspecial();
				obj.setTipoCondEspecialId(rs.getInt("tipo_cond_especial_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setEstado(rs.getString("estado"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setTerminal(rs.getString("terminal"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<MpTipoDocumentoCondicionEspecial> getAllMpTipoDocumentoCondicionEspecial()
			throws Exception {
		List<MpTipoDocumentoCondicionEspecial> lista = new LinkedList<MpTipoDocumentoCondicionEspecial>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"select tipo_documento_condicion_especial_id,descripcion,descripcion_corta,codigo_documento,estado from ")
					.append(Constante.schemadb)
					.append(".mp_tipo_documento_condicion_especial where estado=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Constante.ESTADO_ACTIVO);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				MpTipoDocumentoCondicionEspecial obj = new MpTipoDocumentoCondicionEspecial();
				obj.setTipoDocumentoCondicionEspecialId(rs
						.getInt("tipo_documento_condicion_especial_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				obj.setCodigoDocumento(rs.getString("codigo_documento"));
				obj.setEstado(rs.getString("estado"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<MpSituacionEmpresarial> getAllMpSituacionEmpresarial(
			Integer tipoPersonaId) throws Exception {
		List<MpSituacionEmpresarial> lista = new LinkedList<MpSituacionEmpresarial>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("  select tce.situacion_empresarial_id,tce.descripcion,tce.estado,tce.usuario_id ");
			SQL.append("from ").append(Constante.schemadb)
					.append(".mp_tipo_per_sit_empresarial tpse  ");
			SQL.append("inner join ")
					.append(Constante.schemadb)
					.append(".mp_situacion_empresarial tce on tpse.situacion_empresarial_id = tce.situacion_empresarial_id ");
			SQL.append("where tpse.tipo_persona_id = ? and tpse.estado = ? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoPersonaId);
			pst.setString(2, Constante.ESTADO_ACTIVO);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				MpSituacionEmpresarial obj = new MpSituacionEmpresarial();
				obj.setSituacionEmpresarialId(rs
						.getInt("situacion_empresarial_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setEstado(rs.getString("estado"));// validacion a la bd
														// relacionada
														// 31/07/2012
				obj.setUsuarioId(rs.getInt("usuario_id"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<MpSituacionEmpresarial> findMpSituacionEmpresarial(
			Integer situacion_empresarial_id) throws Exception {
		List<MpSituacionEmpresarial> lista = new LinkedList<MpSituacionEmpresarial>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"select situacion_empresarial_id,descripcion,estado,usuario_id from ")
					.append(Constante.schemadb)
					.append(".mp_situacion_empresarial where situacion_empresarial_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, situacion_empresarial_id);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				MpSituacionEmpresarial obj = new MpSituacionEmpresarial();
				obj.setSituacionEmpresarialId(rs
						.getInt("situacion_empresarial_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setEstado(rs.getString("estado"));// validacion a la bd
														// relacionada
														// 31/07/2012
				obj.setUsuarioId(rs.getInt("usuario_id"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<MpTipoCondicionEspecial> findMpTipoCondicionEspecial(
			Integer tipo_cond_especial_id) throws Exception {
		List<MpTipoCondicionEspecial> lista = new LinkedList<MpTipoCondicionEspecial>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"select tce.tipo_cond_especial_id,tce.descripcion,tce.estado,tce.usuario_id,tce.fecha_registro,tce.terminal from ")
					.append(Constante.schemadb)
					.append(".mp_tipo_condicion_especial tce where tce.tipo_cond_especial_id=? ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipo_cond_especial_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				MpTipoCondicionEspecial obj = new MpTipoCondicionEspecial();
				obj.setTipoCondEspecialId(rs.getInt("tipo_cond_especial_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setEstado(rs.getString("estado"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setTerminal(rs.getString("terminal"));
				lista.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<MpSubtipoPersona> finMpSubtipoPersona(Integer tipoPersonaId)
			throws Exception {
		List<MpSubtipoPersona> lista = new LinkedList<MpSubtipoPersona>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"select stp.tipo_persona_id,stp.subtipo_persona_id,stp.descripcion,stp.descripcion_corta from ")
					.append(Constante.schemadb)
					.append(".mp_subtipo_persona stp where stp.tipo_persona_id=? and estado=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoPersonaId);
			pst.setString(2, Constante.ESTADO_ACTIVO);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				MpSubtipoPersona obj = new MpSubtipoPersona();
				obj.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				obj.setSubtipoPersonaId(rs.getInt("subtipo_persona_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<MpSubtipoPersona> finMpSubtipoPersona(String tipoPersona)
			throws Exception {
		List<MpSubtipoPersona> lista = new LinkedList<MpSubtipoPersona>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"  select stp.tipo_persona_id,stp.subtipo_persona_id,stp.descripcion,stp.descripcion_corta from ")
					.append(Constante.schemadb)
					.append(".mp_subtipo_persona stp ");
			SQL.append(" inner join  ")
					.append(Constante.schemadb)
					.append(".mp_tipo_persona tp on stp.tipo_persona_id=tp.tipo_persona_id ");
			SQL.append(" where tp.descripcion=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, tipoPersona);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				MpSubtipoPersona obj = new MpSubtipoPersona();
				obj.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				obj.setSubtipoPersonaId(rs.getInt("subtipo_persona_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	private String DescripcionAbreviada(HashMap<Integer, String> map, int id) {
		return map.get(id);
	}

	private String getDenominacionTipoVia(HashMap<Integer, String> map,
			int TipoViaId) {
		return map.get(TipoViaId);
	}

	private String getDenominacionDenUrbana(HashMap<Integer, String> map,
			int TipoDenUrbanaId) {
		return map.get(TipoDenUrbanaId);
	}

	public String ConcatenarMpDireccion(MpDireccion direccion,
			HashMap<Integer, String> mapIGnTipoEdificacion,
			HashMap<Integer, String> mapIGnTipoInterior,
			HashMap<Integer, String> mapIGnTipoIngreso,
			HashMap<Integer, String> mapIGnTipoVia, Integer nroCuadra,
			String sector) {
		StringBuffer sbStrDireccion = new StringBuffer();
		StringBuffer sbStrDesDom = new StringBuffer();

		try {
			// Numero 1
			if (direccion.getNumero() != null
					&& direccion.getNumero().trim().length() > 0) {
				sbStrDireccion.append("Nro ").append(direccion.getNumero());
				// Letra 1
				if (direccion.getLetra() != null
						&& direccion.getLetra().compareTo("") != 0
						&& direccion.getLetra().trim().length() > 0) {
					sbStrDireccion.append("-").append(direccion.getLetra());
				}
				// Numero 2
				if (direccion.getNumero2() != null
						&& direccion.getNumero2().trim().length() > 0) {
					sbStrDireccion.append("/").append(direccion.getNumero2());
				}
				// Letra 2
				if (direccion.getLetra2() != null
						&& direccion.getLetra2().compareTo("") != 0
						&& direccion.getLetra2().trim().length() > 0) {
					sbStrDireccion.append("-").append(direccion.getLetra2());
				}
			}

			// Tipo Edificio
			if (direccion.getTipoEdificacionId() != null
					&& direccion.getTipoEdificacionId() > 0) {
				sbStrDireccion.append(" ").append(
						DescripcionAbreviada(mapIGnTipoEdificacion,
								direccion.getTipoEdificacionId()));
				// Nombre del Edificio
				if (direccion.getNombreEdificiacion() != null
						&& direccion.getNombreEdificiacion().trim().length() > 0) {
					sbStrDireccion.append(" ").append(
							direccion.getNombreEdificiacion());
				}
			}

			// Piso
			if (direccion.getPiso() != null
					&& direccion.getPiso().trim().length() > 0) {
				sbStrDireccion.append(" ").append("Piso ")
						.append(direccion.getPiso());
			}

			// Tipo Interior
			if (direccion.getTipoInteriorId() != null
					&& direccion.getTipoInteriorId() > 0) {
				sbStrDireccion.append(" ").append(
						DescripcionAbreviada(mapIGnTipoInterior,
								direccion.getTipoInteriorId()));
				// 'Numero Interior
				if (direccion.getNumeroInterior() != null
						&& direccion.getNumeroInterior().trim().length() > 0)
					sbStrDireccion.append(" ").append(
							direccion.getNumeroInterior());
				// 'Letra Interior
				if (direccion.getLetraInterior() != null
						&& direccion.getLetraInterior().trim().length() > 0)
					sbStrDireccion.append("-").append(
							direccion.getLetraInterior().trim());
			}

			// 'Tipo Ingreso
			if (direccion.getTipoIngresoId() != null
					&& direccion.getTipoIngresoId() > 0) {
				sbStrDireccion.append(" ").append(
						DescripcionAbreviada(mapIGnTipoIngreso,
								direccion.getTipoIngresoId()));
				// 'Nombre Ingreso
				if (direccion.getNombreIngreso() != null
						&& direccion.getNombreIngreso().trim().length() > 0)
					sbStrDireccion.append(" ").append(
							direccion.getNombreIngreso().trim());
			}

			// 'Manzana
			if (direccion.getManzana() != null
					&& direccion.getManzana().trim().length() > 0) {
				sbStrDireccion.append(" ").append("Mz: ")
						.append(direccion.getManzana().trim());
			}

			// 'Lote
			if (direccion.getLote() != null
					&& direccion.getLote().trim().length() > 0) {
				sbStrDireccion.append(" ").append("Lt: ")
						.append(direccion.getLote().trim());
			}

			// Sector
			/*
			 * cchaucca 10/01/2014 : se retira la denominacion del sector
			 * tributario de la direccion contenada
			 */
			/*
			 * if (direccion.getSector() != null &&
			 * direccion.getSector().trim().length() > 0) {
			 * sbStrDireccion.append
			 * (" ").append("Sector: ").append(direccion.getSector().trim()); }
			 */

			// Lugar
			if (direccion.getLugar() != null
					&& direccion.getLugar().trim().length() > 0) {
				sbStrDireccion.append(" ").append("Lugar: ")
						.append(direccion.getLugar().trim());
			}

			// 'Concatenacion de Domicilio
			// 'Tipo Via
			if (direccion.getVia().getTipoViaId() != null
					&& direccion.getVia().getTipoViaId() > 0) {
				sbStrDesDom
						.append(getDenominacionTipoVia(mapIGnTipoVia, direccion
								.getVia().getTipoViaId())).append(" ")
						.append(direccion.getVia().getDescripcion());
			}
			// tipo via real
			if (direccion.getTipoVia() != null
					&& direccion.getTipoVia().trim().length() > 0) {
				sbStrDesDom.append(direccion.getTipoVia()).append(" ")
						.append(direccion.getNombreVia());
			}

			if (sbStrDireccion.length() > 0) {
				sbStrDesDom.append(" ").append(sbStrDireccion.toString());
			}

			// 'Referencia
			if (direccion.getReferencia() != null
					&& direccion.getReferencia().trim().length() > 0) {
				if (sbStrDesDom.length() > 0) {
					if (direccion.getUbigeo().getDistrito().trim().length() > 0) {
						if (sbStrDesDom.length() > 0) {
							sbStrDesDom.append(", ").append(
									direccion.getUbigeo().getDistrito());
						} else {
							sbStrDesDom.append(direccion.getUbigeo()
									.getDistrito());
						}
					}
					sbStrDesDom.append(" - Ref: ").append(
							direccion.getReferencia());
				} else {
					// 'Distrito
					sbStrDesDom.append(direccion.getReferencia());
					if (direccion.getUbigeo().getDistrito().trim().length() > 0) {
						if (sbStrDesDom.length() > 0) {
							/*
							 * cchaucca 10/01/2014 : se retira la denominacion
							 * del sector tributario de la direccion contenada
							 */
							// sbStrDesDom.append(", Cdra: ").append(nroCuadra).append(", Sector: ").append(sector).append(", ").append(direccion.getUbigeo().getDistrito());
							sbStrDesDom
									.append(", Cdra: ")
									.append(nroCuadra)
									.append(", ")
									.append(direccion.getUbigeo().getDistrito());
						} else {
							/*
							 * cchaucca 10/01/2014 : se retira la denominacion
							 * del sector tributario de la direccion contenada
							 */
							// sbStrDesDom.append(", Cdra: ").append(nroCuadra).append(", Sector: ").append(sector).append(", ").append(direccion.getUbigeo().getDistrito());
							sbStrDesDom
									.append(", Cdra: ")
									.append(nroCuadra)
									.append(", ")
									.append(direccion.getUbigeo().getDistrito());
						}
					}
				}
			} else {
				// distrito
				if (direccion.getUbigeo().getDistrito().trim().length() > 0) {
					if (sbStrDesDom.length() > 0) {
						/*
						 * cchaucca 10/01/2014 : se retira la denominacion del
						 * sector tributario de la direccion contenada
						 */
						// sbStrDesDom.append(", Cdra: ").append(nroCuadra).append(", Sector: ").append(sector).append(", ").append(direccion.getUbigeo().getDistrito());
						sbStrDesDom.append(", Cdra: ").append(nroCuadra)
								.append(", ")
								.append(direccion.getUbigeo().getDistrito());
					} else {
						/*
						 * cchaucca 10/01/2014 : se retira la denominacion del
						 * sector tributario de la direccion contenada
						 */
						// sbStrDesDom.append(", Cdra: ").append(nroCuadra).append(", Sector: ").append(sector).append(", ").append(direccion.getUbigeo().getDistrito());
						sbStrDesDom.append(", Cdra: ").append(nroCuadra)
								.append(", ")
								.append(direccion.getUbigeo().getDistrito());
					}
				}
			}
			// strDesDom = strDesDom + ", Cdra: "+nroCuadra+", Sector: "+sector;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: " + e);
		}
		return sbStrDesDom.toString();
	}

	public int guardarMpPersonadireccion(MpPersonaDomicilio mpPersonaDomicilio)
			throws Exception {
		int result = 0;
		try {

			StringBuffer SQL = new StringBuffer();
			SQL.append("INSERT INTO ").append(Constante.schemadb)
					.append(".mp_persona_domicilio ");
			SQL.append("(domicilio_persona_id,tipo_predio ");// a 2
			SQL.append(",flag_fiscal,flag_procesal,flag_real,flag_fiscal_notificado ");// b
																						// 4
			SQL.append(",domicilio_completo,usuario_id,terminal,fecha_registro ");// c
																					// 4
			SQL.append(",fecha_actualizacion,terminal_actualizacion,usuario_id_actualizacion,direccion_id,persona_id,tipo_domicilio,estado) ");// d
																																				// 5
			SQL.append("VALUES(?,?, ");// a 2
			SQL.append("?,?,?,?, ");// b 4
			SQL.append("?,?,?,?, ");// c 4
			SQL.append("?,?,?,?,?,?,?) ");// d 5

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, mpPersonaDomicilio.getDomicilio_persona_id());// a
			pst.setString(2, mpPersonaDomicilio.getTipoPredio());
			pst.setString(3, mpPersonaDomicilio.getFlagFiscal());// b
			pst.setString(4, mpPersonaDomicilio.getFlagProcesal());
			pst.setString(5, mpPersonaDomicilio.getFlagReal());
			pst.setString(6, mpPersonaDomicilio.getFlagFiscalNotificado());
			pst.setString(7, mpPersonaDomicilio.getDomicilioCompleto());// c
			pst.setInt(8, mpPersonaDomicilio.getUsuarioId());
			pst.setString(9, mpPersonaDomicilio.getTerminal());
			pst.setTimestamp(10, mpPersonaDomicilio.getFechaRegistro());// c
			pst.setTimestamp(11, mpPersonaDomicilio.getFechaActualizacion());
			pst.setString(12, mpPersonaDomicilio.getTerminalActualizacion());
			pst.setInt(13, mpPersonaDomicilio.getUsuarioIdActualizacion());
			pst.setInt(14, mpPersonaDomicilio.getDireccionId());
			pst.setInt(15, mpPersonaDomicilio.getPersonaId());// d
			pst.setString(16, mpPersonaDomicilio.getTipoDomicilio());
			pst.setString(17, mpPersonaDomicilio.getEstado());

			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public ArrayList<FindMpDireccion> finMpDireccionPersona(int persona_id,
			String estado) throws Exception {
		ArrayList<FindMpDireccion> lista = new ArrayList<FindMpDireccion>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" select ROW_NUMBER() OVER(order by d.direccion_id  desc) item,pd.domicilio_persona_id,pd.domicilio_completo,pd.tipo_domicilio,d.persona_id,d.direccion_id ");
			SQL.append(" ,tv.tipo_via_id,tv.descripcion tipo_via,v.via_id,v.descripcion via ");
			SQL.append(" ,te.tipo_edificacion_id,te.descripcion tipo_edificacion ");
			SQL.append(" ,ti.tipo_interior_id,ti.descripcion tipo_interior,ting.tipo_ingreso_id,ting.descripcion tipo_ingreso");
			SQL.append(" ,dpto.dpto_id,dpto.descripcion departamento,prov.provincia_id,prov.descripcion provincia,dis.distrito_id, dis.descripcion distrito,u.numero_cuadra,s.descripcion sector,u.lado_cuadra");
			SQL.append("  FROM ").append(Constante.schemadb)
					.append(".mp_persona_domicilio pd");
			SQL.append("  inner join  ")
					.append(Constante.schemadb)
					.append(".mp_direccion d on pd.direccion_id=d.direccion_id");
			SQL.append("  inner join  ").append(Constante.schemadb)
					.append(".gn_ubicacion u on d.ubicacion_id=u.ubicacion_id");
			SQL.append("  inner join  ")
					.append(Constante.schemadb)
					.append(".gn_sector_lugar sl on u.sector_lugar_id=sl.sector_lugar_id");
			SQL.append("  inner join  ").append(Constante.schemadb)
					.append(".gn_sector s on sl.sector_id=s.sector_id");
			SQL.append("  LEFT join   ").append(Constante.schemadb)
					.append(".gn_departamento dpto on d.dpto_id=dpto.dpto_id");
			SQL.append("  LEFT join   ")
					.append(Constante.schemadb)
					.append(".gn_provincia prov on d.provincia_id=prov.provincia_id and  dpto.dpto_id=prov.dpto_id");
			SQL.append("  LEFT join   ")
					.append(Constante.schemadb)
					.append(".gn_distrito dis on dpto.dpto_id=dis.dpto_id and   d.distrito_id=dis.distrito_id and prov.provincia_id=dis.provincia_id");
			SQL.append("  left join   ").append(Constante.schemadb)
					.append(".gn_via v on u.via_id=v.via_id");
			SQL.append("  left join   ").append(Constante.schemadb)
					.append(".gn_tipo_via tv on v.tipo_via_id=tv.tipo_via_id");
			SQL.append("  left join   ")
					.append(Constante.schemadb)
					.append(".gn_tipo_edificacion te on d.tipo_edificacion_id=te.tipo_edificacion_id");
			SQL.append("  left join   ")
					.append(Constante.schemadb)
					.append(".gn_tipo_interior ti on d.tipo_interior_id=ti.tipo_interior_id");
			SQL.append("  left join   ")
					.append(Constante.schemadb)
					.append(".gn_tipo_ingreso ting on d.tipo_ingreso_id=ting.tipo_ingreso_id");
			SQL.append("   where pd.persona_id=? and d.estado=? and  pd.estado=?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, persona_id);
			pst.setString(2, estado);
			pst.setString(3, estado);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindMpDireccion obj = new FindMpDireccion();
				obj.setItem(rs.getInt("item"));
				obj.setPersonaDomicilio(rs.getInt("domicilio_persona_id"));
				obj.setDomicilioCompleto(rs.getString("domicilio_completo"));
				obj.setTipoDomicilio(rs.getString("tipo_domicilio"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDireccionId(rs.getInt("direccion_id"));
				obj.setTipoViaId(rs.getInt("tipo_via_id"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setViaId(rs.getInt("via_id"));
				obj.setVia(rs.getString("via"));
				obj.setTipoEdificacionId(rs.getInt("tipo_edificacion_id"));
				obj.setTipoEdificacion(rs.getString("tipo_edificacion"));
				obj.setTipoInteriorId(rs.getInt("tipo_interior_id"));
				obj.setTipoInterior(rs.getString("tipo_interior"));
				obj.setTipoIngresoId(rs.getInt("tipo_ingreso_id"));
				obj.setTipoIngreso(rs.getString("tipo_ingreso"));
				obj.setDepartamentoId(rs.getInt("dpto_id"));
				obj.setDepartamento(rs.getString("departamento"));
				obj.setProvinciaId(rs.getInt("provincia_id"));
				obj.setProvincia(rs.getString("provincia"));
				obj.setDistritoId(rs.getInt("distrito_id"));
				obj.setDistrito(rs.getString("distrito"));
				obj.setNroCuadra(rs.getInt("numero_cuadra"));
				obj.setSector(rs.getString("sector"));
				obj.setLadoCuadra(rs.getInt("lado_cuadra"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public ArrayList<FindMpDireccion> finMpDireccionPersona(int persona_id)
			throws Exception {
		ArrayList<FindMpDireccion> lista = new ArrayList<FindMpDireccion>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" select ROW_NUMBER() OVER(order by d.direccion_id  desc) item,pd.domicilio_persona_id,pd.domicilio_completo,pd.tipo_domicilio,d.persona_id,d.direccion_id ");
			SQL.append(" ,tv.tipo_via_id,tv.descripcion tipo_via,v.via_id,v.descripcion via ");
			SQL.append(" ,te.tipo_edificacion_id,te.descripcion tipo_edificacion ");
			SQL.append(" ,ti.tipo_interior_id,ti.descripcion tipo_interior,ting.tipo_ingreso_id,ting.descripcion tipo_ingreso");
			SQL.append(" ,dpto.dpto_id,dpto.descripcion departamento,prov.provincia_id,prov.descripcion provincia,dis.distrito_id, dis.descripcion distrito,u.numero_cuadra,s.descripcion sector,u.lado_cuadra,pd.flag_fiscal");
			SQL.append("  FROM ").append(Constante.schemadb)
					.append(".mp_persona_domicilio pd");
			SQL.append("  inner join ")
					.append(Constante.schemadb)
					.append(".mp_direccion d on pd.direccion_id=d.direccion_id");
			SQL.append("    inner join ").append(Constante.schemadb)
					.append(".gn_ubicacion u on d.ubicacion_id=u.ubicacion_id");
			SQL.append("    inner join ")
					.append(Constante.schemadb)
					.append(".gn_sector_lugar sl on u.sector_lugar_id=sl.sector_lugar_id");
			SQL.append("    inner join ").append(Constante.schemadb)
					.append(".gn_sector s on sl.sector_id=s.sector_id");
			SQL.append("  LEFT join ").append(Constante.schemadb)
					.append(".gn_departamento dpto on d.dpto_id=dpto.dpto_id");
			SQL.append("    LEFT join ")
					.append(Constante.schemadb)
					.append(".gn_provincia prov on d.provincia_id=prov.provincia_id and  dpto.dpto_id=prov.dpto_id");
			SQL.append("    LEFT join ")
					.append(Constante.schemadb)
					.append(".gn_distrito dis on dpto.dpto_id=dis.dpto_id and   d.distrito_id=dis.distrito_id and prov.provincia_id=dis.provincia_id");
			SQL.append("  left join ").append(Constante.schemadb)
					.append(".gn_via v on u.via_id=v.via_id");
			SQL.append("  left join ").append(Constante.schemadb)
					.append(".gn_tipo_via tv on v.tipo_via_id=tv.tipo_via_id");
			SQL.append("  left join ")
					.append(Constante.schemadb)
					.append(".gn_tipo_edificacion te on d.tipo_edificacion_id=te.tipo_edificacion_id");
			SQL.append("  left join ")
					.append(Constante.schemadb)
					.append(".gn_tipo_interior ti on d.tipo_interior_id=ti.tipo_interior_id");
			SQL.append("  left join ")
					.append(Constante.schemadb)
					.append(".gn_tipo_ingreso ting on d.tipo_ingreso_id=ting.tipo_ingreso_id");
			SQL.append("   where pd.persona_id=? and (d.estado=? or d.estado=?) and (pd.estado=? or pd.estado=?)");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, persona_id);
			pst.setString(2, Constante.ESTADO_ACTIVO);
			pst.setString(3, Constante.ESTADO_PENDIENTE);
			pst.setString(4, Constante.ESTADO_ACTIVO);
			pst.setString(5, Constante.ESTADO_PENDIENTE);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindMpDireccion obj = new FindMpDireccion();
				obj.setItem(rs.getInt("item"));
				obj.setPersonaDomicilio(rs.getInt("domicilio_persona_id"));
				obj.setDomicilioCompleto(rs.getString("domicilio_completo"));
				obj.setTipoDomicilio(rs.getString("tipo_domicilio"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDireccionId(rs.getInt("direccion_id"));
				obj.setTipoViaId(rs.getInt("tipo_via_id"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setViaId(rs.getInt("via_id"));
				obj.setVia(rs.getString("via"));
				obj.setTipoEdificacionId(rs.getInt("tipo_edificacion_id"));
				obj.setTipoEdificacion(rs.getString("tipo_edificacion"));
				obj.setTipoInteriorId(rs.getInt("tipo_interior_id"));
				obj.setTipoInterior(rs.getString("tipo_interior"));
				obj.setTipoIngresoId(rs.getInt("tipo_ingreso_id"));
				obj.setTipoIngreso(rs.getString("tipo_ingreso"));
				obj.setDepartamentoId(rs.getInt("dpto_id"));
				obj.setDepartamento(rs.getString("departamento"));
				obj.setProvinciaId(rs.getInt("provincia_id"));
				obj.setProvincia(rs.getString("provincia"));
				obj.setDistritoId(rs.getInt("distrito_id"));
				obj.setDistrito(rs.getString("distrito"));
				obj.setNroCuadra(rs.getInt("numero_cuadra"));
				obj.setSector(rs.getString("sector"));
				obj.setLadoCuadra(rs.getInt("lado_cuadra"));
				obj.setFlagFiscal(rs.getString("flag_fiscal"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public int guardarGnCondicionEspecial(
			GnCondicionEspecial gnCondicionEspecial) throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("INSERT INTO ").append(Constante.schemadb)
					.append(".gn_condicion_especial ");
			SQL.append("(persona_id,condicion_especial_id ");// a 2
			SQL.append(",tipo_documento,tipo_cond_especial_id,nro_documento,fecha_inicio ");// b
																							// 4
			SQL.append(",fecha_fin,fecha_documento,estado,usuario_id,fecha_registro,terminal) ");// c
																									// 5
			SQL.append("VALUES(?,?, ");// a 2
			SQL.append("?,?,?,?, ");// b 4
			SQL.append("?,?,?,?,?,?) ");// c 5
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, gnCondicionEspecial.getId().getPersonaId());// a
			pst.setInt(2, gnCondicionEspecial.getId().getCondicionEspecialId());
			pst.setInt(3, gnCondicionEspecial.getTipoDocumento());
			pst.setInt(4, gnCondicionEspecial.getTipoCondEspecialId());
			pst.setString(5, gnCondicionEspecial.getNroDocumento());
			pst.setTimestamp(6, gnCondicionEspecial.getFechaInicio());
			pst.setTimestamp(7, gnCondicionEspecial.getFechaFin());
			pst.setTimestamp(8, gnCondicionEspecial.getFechaDocumento());
			pst.setString(9, gnCondicionEspecial.getEstado());
			pst.setInt(10, gnCondicionEspecial.getUsuarioId());
			pst.setTimestamp(11, gnCondicionEspecial.getFechaRegistro());
			pst.setString(12, gnCondicionEspecial.getTerminal());
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public List<GnDepartamento> getAllGnDepartamento() throws Exception {
		List<GnDepartamento> lista = new LinkedList<GnDepartamento>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT dpto_id,descripcion FROM ")
					.append(Constante.schemadb)
					.append(".gn_departamento order by dpto_id asc");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnDepartamento obj = new GnDepartamento();
				obj.setDptoId(rs.getInt("dpto_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<GnProvincia> getAllGnProvincia(Integer departamentoId)
			throws Exception {
		List<GnProvincia> lista = new LinkedList<GnProvincia>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" SELECT dpto_id,provincia_id,descripcion FROM ")
					.append(Constante.schemadb)
					.append(".gn_provincia where dpto_id=? order by dpto_id asc");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, departamentoId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnProvincia obj = new GnProvincia();
				GnProvinciaPK id = new GnProvinciaPK();
				id.setDptoId(rs.getInt("dpto_id"));
				id.setProvinciaId(rs.getInt("provincia_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setId(id);
				lista.add(obj);
			}

		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<GnDistrito> getAllGnDistrito(Integer departamentoId,
			Integer provinciaId) throws Exception {
		List<GnDistrito> lista = new LinkedList<GnDistrito>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"SELECT dpto_id,provincia_id,distrito_id,descripcion,codigo_postal FROM ")
					.append(Constante.schemadb)
					.append(".gn_distrito where dpto_id=? and provincia_id=? order by dpto_id asc");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, departamentoId);
			pst.setInt(2, provinciaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnDistrito obj = new GnDistrito();
				GnDistritoPK id = new GnDistritoPK();
				id.setDptoId(rs.getInt("dpto_id"));
				id.setProvinciaId(rs.getInt("provincia_id"));
				id.setDistritoId(rs.getInt("distrito_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setCodigoPostal(rs.getString("codigo_postal"));
				obj.setId(id);
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	// cchaucca:ini 05/04/213 busqueda de personas para papeletas
	public List<BuscarPersonaDTO> findPersonaPapeletas(Integer persId,
			String apeNom) throws Exception {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			// select TOP 100 p.persona_id,
			StringBuilder SQL = new StringBuilder(
					"SELECT TOP 100 per.persona_id,tdi.descrpcion_corta,per.nro_doc_identidad,per.apellidos_nombres,per.razon_social,per.ape_paterno, ");
			SQL.append(" per.ape_materno,per.primer_nombre,per.segundo_nombre,per.tipo_documento_id ,");
			SQL.append(" (SELECT COUNT(*) AS cantidad FROM dbo.pa_papeleta where persona_infractor_id=per.persona_id and estado<>9) as nropapeleta");
			SQL.append(" ,CASE WHEN (paper.persona_id IN (SELECT persona_id FROM mp_persona))THEN 'SI' ELSE 'NO' END contribuyente_infractor");
			SQL.append(" FROM dbo.gn_persona per  ");
			SQL.append(" INNER JOIN pa_persona paper on paper.persona_id=per.persona_id ");
			SQL.append(" LEFT JOIN dbo.mp_tipo_docu_identidad tdi ON tdi.tipo_doc_identidad_id=per.tipo_documento_id ");
			SQL.append(" WHERE per.estado='1' and paper.estado=1 ");
			if (persId != null) {
				SQL.append("AND per.persona_id =").append(persId);
			} else {
				if (apeNom != null) {
					SQL.append(" AND per.apellidos_nombres LIKE '%")
							.append(apeNom).append("%'");
				}
			}
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarPersonaDTO obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoDocIdentidad(rs.getString("descrpcion_corta"));
				obj.setNroDocuIdentidad(rs.getString("nro_doc_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				// obj.setDireccionCompleta(rs.getString("domicilio_completo"));
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				// obj.setEsContribuyente(rs.getInt("es_contribuyente")==0?Boolean.FALSE:Boolean.TRUE);
				obj.setTipodocumentoIdentidadId(rs.getInt("tipo_documento_id"));
				obj.setNroPapeletas(rs.getInt("nropapeleta"));
				obj.setContribuyenteInfractor(rs
						.getString("contribuyente_infractor"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<BuscarPersonaDTO> findPersonaPapeletas(int tipoDocuIdentidadId,
			String nroDocuIdentidad) throws Exception {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT TOP 50 per.persona_id,tdi.descrpcion_corta,per.nro_doc_identidad,per.apellidos_nombres,per.razon_social,per.ape_paterno,");
			SQL.append(" per.ape_materno,per.primer_nombre,per.segundo_nombre,per.tipo_documento_id, ");
			SQL.append(" (SELECT COUNT(1) AS cantidad FROM dbo.pa_papeleta where persona_infractor_id=per.persona_id and estado<>9) as nropapeleta");
			SQL.append(" ,CASE WHEN (paper.persona_id IN (SELECT persona_id FROM mp_persona))THEN 'SI' ELSE 'NO' END contribuyente_infractor");
			SQL.append(" FROM dbo.gn_persona per ");
			SQL.append(" INNER JOIN dbo.mp_tipo_docu_identidad tdi ON tdi.tipo_doc_identidad_id=per.tipo_documento_id");
			SQL.append(" INNER JOIN pa_persona paper on paper.persona_id=per.persona_id ");
			SQL.append(" WHERE per.estado='1' and paper.estado=1");
			if (tipoDocuIdentidadId > 0 && nroDocuIdentidad != null
					&& nroDocuIdentidad.trim().length() > 0) {
				// SQL.append(" AND per.tipo_documento_id=").append(tipoDocuIdentidadId).append(" and per.nro_doc_identidad='").append(nroDocuIdentidad).append("'");
				SQL.append(" AND per.tipo_documento_id=? and per.nro_doc_identidad=?");
			}

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			if (tipoDocuIdentidadId > 0 && nroDocuIdentidad != null
					&& nroDocuIdentidad.trim().length() > 0) {
				pst.setInt(1, tipoDocuIdentidadId);
				pst.setString(2, nroDocuIdentidad);
			}

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarPersonaDTO obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoDocIdentidad(rs.getString("descrpcion_corta"));
				obj.setNroDocuIdentidad(rs.getString("nro_doc_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				// obj.setDireccionCompleta(rs.getString("domicilio_completo"));
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				// obj.setEsContribuyente(rs.getInt("es_contribuyente")==0?Boolean.FALSE:Boolean.TRUE);
				obj.setTipodocumentoIdentidadId(rs.getInt("tipo_documento_id"));
				obj.setNroPapeletas(rs.getInt("nropapeleta"));
				obj.setContribuyenteInfractor(rs
						.getString("contribuyente_infractor"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	// cchaucca:fin 05/05/2013

	// cchaucca:ini 28/05/2012
	public List<BuscarPersonaDTO> findPersona(Integer persId, String apePat,
			String apeMat, String nombres) throws Exception {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			// select TOP 100 p.persona_id,
			StringBuilder SQL = new StringBuilder(
					"SELECT TOP 100 per.persona_id,tdi.descrpcion_corta,per.nro_docu_identidad,per.apellidos_nombres,per.razon_social,per.ape_paterno,per.ape_materno,per.primer_nombre,per.segundo_nombre,per.tipo_doc_identidad_id,stp.descripcion subtipopersona ");
			SQL.append(" FROM ").append(Constante.schemadb)
					.append(".mp_persona per  ");
			SQL.append(" INNER JOIN ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi ON tdi.tipo_doc_identidad_id=per.tipo_doc_identidad_id ");
			SQL.append(" INNER JOIN ")
					.append(Constante.schemadb)
					.append(".mp_subtipo_persona stp on per.subtipo_persona_id=stp.subtipo_persona_id ");
			SQL.append(" WHERE per.estado='1' ");
			if (persId != null) {
				SQL.append("AND per.persona_id =").append(persId);
			} else {
				if (apePat != null) {
					SQL.append(" AND per.ape_paterno LIKE '%").append(apePat)
							.append("%'");
				}
				if (apeMat != null) {
					SQL.append(" AND per.ape_materno LIKE '%").append(apeMat)
							.append("%'");
				}
				if (nombres != null) {
					SQL.append(" AND per.primer_nombre LIKE '%")
							.append(nombres).append("%'");
				}
			}
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarPersonaDTO obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoDocIdentidad(rs.getString("descrpcion_corta"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				obj.setTipodocumentoIdentidadId(rs
						.getInt("tipo_doc_identidad_id"));
				obj.setSubtipoPersona(rs.getString("subtipopersona"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<BuscarPersonaDTO> findPersona(String razonSocial)
			throws Exception {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT TOP 100  per.persona_id,tdi.descrpcion_corta,per.nro_docu_identidad,per.apellidos_nombres,per.razon_social,per.ape_paterno,per.ape_materno,per.primer_nombre,per.segundo_nombre,per.tipo_doc_identidad_id,stp.descripcion subtipopersona ");
			SQL.append(" FROM ").append(Constante.schemadb)
					.append(".mp_persona per  ");
			SQL.append(" INNER JOIN ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi ON tdi.tipo_doc_identidad_id=per.tipo_doc_identidad_id ");
			SQL.append(" INNER JOIN ")
					.append(Constante.schemadb)
					.append(".mp_subtipo_persona stp on per.subtipo_persona_id=stp.subtipo_persona_id ");
			SQL.append(
					" WHERE per.estado='1' AND per.tipo_doc_identidad_id=2 AND per.razon_social LIKE '%")
					.append(razonSocial).append("%' ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarPersonaDTO obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoDocIdentidad(rs.getString("descrpcion_corta"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				// obj.setDireccionCompleta(rs.getString("domicilio_completo"));
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				// obj.setEsContribuyente(rs.getInt("es_contribuyente")==0?Boolean.FALSE:Boolean.TRUE);
				obj.setTipodocumentoIdentidadId(rs
						.getInt("tipo_doc_identidad_id"));
				obj.setSubtipoPersona(rs.getString("subtipopersona"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<BuscarPersonaDTO> findPersona(int tipoDocuIdentidadId,
			String nroDocuIdentidad) throws Exception {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT TOP 100 per.persona_id,tdi.descrpcion_corta,per.nro_docu_identidad,per.apellidos_nombres,per.razon_social,per.ape_paterno,per.ape_materno,per.primer_nombre,per.segundo_nombre,per.tipo_doc_identidad_id,stp.descripcion subtipopersona ");
			SQL.append(" FROM ").append(Constante.schemadb)
					.append(".mp_persona per  ");
			SQL.append(" INNER JOIN ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi ON tdi.tipo_doc_identidad_id=per.tipo_doc_identidad_id ");
			SQL.append(" INNER JOIN ")
					.append(Constante.schemadb)
					.append(".mp_subtipo_persona stp on per.subtipo_persona_id=stp.subtipo_persona_id ");
			SQL.append(" WHERE per.estado='1' ");
			if (tipoDocuIdentidadId > 0 && nroDocuIdentidad != null
					&& nroDocuIdentidad.trim().length() > 0)
				SQL.append(" AND per.tipo_doc_identidad_id=")
						.append(tipoDocuIdentidadId)
						.append(" and per.nro_docu_identidad='")
						.append(nroDocuIdentidad).append("'");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarPersonaDTO obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoDocIdentidad(rs.getString("descrpcion_corta"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				// obj.setDireccionCompleta(rs.getString("domicilio_completo"));
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				// obj.setEsContribuyente(rs.getInt("es_contribuyente")==0?Boolean.FALSE:Boolean.TRUE);
				obj.setTipodocumentoIdentidadId(rs
						.getInt("tipo_doc_identidad_id"));
				obj.setSubtipoPersona(rs.getString("subtipopersona"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public TgPersona findTgPersona(String le) throws Exception {
		TgPersona tgPersona = null;
		return tgPersona;
	
		
		/*
		
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"SELECT cmasteri,le,libro,sp_dep,sp_prv,sp_dst,ape_pat,ape_mat,nombre,fec_nac = CONVERT(datetime,[fec_nac],103),sexo,sp_gri,sp_sec,sp_doc FROM ")
					.append(Constante.schemaper)
					.append(".tg_persona WHERE le=?");
			PreparedStatement pst = connectPersona().prepareStatement(
					SQL.toString());
			pst.setString(1, le);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				tgPersona = new TgPersona();
				tgPersona.setCmasteri(rs.getString("cmasteri") != null ? rs
						.getString("cmasteri").trim() : "");
				tgPersona.setLe(rs.getString("le") != null ? rs.getString("le")
						.trim() : "");
				tgPersona.setLibro(rs.getString("libro") != null ? rs
						.getString("libro").trim() : "");
				tgPersona.setSp_dep(rs.getString("sp_dep") != null ? rs
						.getString("sp_dep").trim() : "");
				tgPersona.setSp_prv(rs.getString("sp_prv") != null ? rs
						.getString("sp_prv").trim() : "");
				tgPersona.setSp_dst(rs.getString("sp_dst") != null ? rs
						.getString("sp_dst").trim() : "");
				tgPersona.setApe_pat(rs.getString("ape_pat") != null ? rs
						.getString("ape_pat").trim() : "");
				tgPersona.setApe_mat(rs.getString("ape_mat") != null ? rs
						.getString("ape_mat").trim() : "");
				tgPersona.setNombre(rs.getString("nombre") != null ? rs
						.getString("nombre").trim() : "");
				// tgPersona.setFec_nac(rs.getString("fec_nac")!=null?rs.getString("fec_nac").trim():"");
				tgPersona
						.setFechaNacimiento(rs.getTimestamp("fec_nac") != null ? rs
								.getTimestamp("fec_nac") : null);
				tgPersona.setSexo(rs.getString("sexo") != null ? rs.getString(
						"sexo").trim() : "");
				tgPersona.setSp_gri(rs.getString("sp_gri") != null ? rs
						.getString("sp_gri").trim() : "");
				tgPersona.setSp_sec(rs.getString("sp_sec") != null ? rs
						.getString("sp_sec").trim() : "");
				tgPersona.setSp_doc(rs.getString("sp_doc") != null ? rs
						.getString("sp_doc").trim() : "");
			}
		} catch (Exception e) {
			throw (e);
		}
		return tgPersona;*/
	}

	// cchaucca fin : 28/05/12

	public int grabarTablaMpPersonaHistorico(MpPersona persona)
			throws Exception {
		int result = 0;
		try {
			Integer id = ObtenerCorrelativoTabla("mp_persona_historico", 1);
			StringBuffer SQL = new StringBuffer();
			SQL.append(" insert into ").append(Constante.schemadb)
					.append(".mp_persona_historico ");
			SQL.append(" SELECT ? ");
			SQL.append(" ,persona_id,ape_paterno,ape_materno,primer_nombre,segundo_nombre,razon_social ");
			SQL.append(" ,fecha_inscripcion,'',nro_docu_identidad_adi,telefono ,fax,email,twitter ");
			SQL.append(" ,facebook,flag_notifica_email,fecha_declaracion,nro_dj,fecha_baja,nro_partida_defuncion ");
			SQL.append(" ,fecha_defuncion,fecha_situacion_empre,fecha_fin_situacion_empresarial,login ");
			SQL.append(" ,usuario_id,terminal,null,fecha_registro ");
			SQL.append(" FROM ").append(Constante.schemadb)
					.append(".mp_persona where persona_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, id);
			pst.setInt(2, persona.getPersonaId());
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public int grabarTablaGnCondicionEspecialHistorico(
			GnCondicionEspecial gnCondicionEspecial) throws Exception {
		int result = 0;
		try {
			Integer id = ObtenerCorrelativoTabla(
					"gn_condicion_especial_historico", 1);
			StringBuffer SQL = new StringBuffer();
			SQL.append(" insert into ").append(Constante.schemadb)
					.append(".gn_condicion_especial_historico ");
			SQL.append(" SELECT ? ");
			SQL.append(" ,persona_id ");
			SQL.append(" ,condicion_especial_id ");
			SQL.append(" ,tipo_documento ");
			SQL.append(" ,tipo_cond_especial_id ");
			SQL.append(" ,nro_documento ");
			SQL.append(" ,fecha_inicio ");
			SQL.append(" ,fecha_fin ");
			SQL.append(" ,estado ");
			SQL.append(" ,usuario_id ");
			SQL.append(" ,fecha_registro ");
			SQL.append(" ,terminal ");
			SQL.append(" ,fecha_documento ");
			SQL.append(" ,porcentaje ");
			SQL.append(" FROM ")
					.append(Constante.schemadb)
					.append(".gn_condicion_especial where persona_id=? and condicion_especial_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, id);
			pst.setInt(2, gnCondicionEspecial.getId().getPersonaId());
			pst.setInt(3, gnCondicionEspecial.getId().getCondicionEspecialId());

			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public int actualizarEstadoMpDireccion(String estadoCambiar,
			String estadoCondicion, Integer persona_id, Integer direccion_id)
			throws Exception {
		int result = 0;
		try {

			StringBuffer SQL = new StringBuffer();
			SQL.append(" update ").append(Constante.schemadb)
					.append(".mp_direccion set estado=? where estado=? and  ");
			SQL.append(" persona_id = ? ");
			if (direccion_id > 0)
				SQL.append("  and direccion_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estadoCambiar);
			pst.setString(2, estadoCondicion);
			pst.setInt(3, persona_id);// 1
			if (direccion_id > 0)
				pst.setInt(4, direccion_id);
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public int actualizarEstadoMpDireccion(Integer direccion_id, String estado)
			throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("UPDATE ")
					.append(Constante.schemadb)
					.append(".mp_direccion set estado=? where direccion_id = ? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, direccion_id);// 1
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public int actualizarEstadoMpPersonaDomicilio(Integer personaDomicilio_id,
			String estado) throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" update ").append(Constante.schemadb)
					.append(".mp_persona_domicilio set estado = ? where ");
			SQL.append(" domicilio_persona_id = ? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, personaDomicilio_id);// 1
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public int actualizarEstadoMpPersonaDomicilio(String estadoCambiar,
			String estadoCondicion, Integer persona_id,
			Integer domicilio_persona_id) throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" update ")
					.append(Constante.schemadb)
					.append(".mp_persona_domicilio set estado=? where estado=? and  ");
			SQL.append(" persona_id = ? ");
			if (domicilio_persona_id > 0)
				SQL.append(" and domicilio_persona_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estadoCambiar);
			pst.setString(2, estadoCondicion);
			pst.setInt(3, persona_id);// 1
			if (domicilio_persona_id > 0)
				pst.setInt(4, domicilio_persona_id);
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public int actualizarEstadoMpRelacionado(String estadoCambiar,
			String estadoCondicion, Integer persona_id, Integer relacionado_id)
			throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" update ")
					.append(Constante.schemadb)
					.append(".mp_relacionado set estado=? where estado=? and  ");
			SQL.append(" persona_id = ?  ");
			if (relacionado_id > 0)
				SQL.append(" and relacionado_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estadoCambiar);
			pst.setString(2, estadoCondicion);
			pst.setInt(3, persona_id);
			if (relacionado_id > 0)
				pst.setInt(4, relacionado_id);
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public int actualizarEstadoMpRelacionado(String estado, Integer persona_id,
			Integer relacionado_id) throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" update ").append(Constante.schemadb)
					.append(".mp_relacionado set estado=? where ");
			SQL.append(" persona_id = ? and relacionado_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, persona_id);
			pst.setInt(3, relacionado_id);
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public int verificarRelacionadoTipoPersonaJuridica(Integer persona_id)
			throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" SELECT COUNT(*) cantidad FROM ")
					.append(Constante.schemadb)
					.append(".mp_relacionado where persona_id=? and tipo_relacion_id in(1,2) and estado <>9");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, persona_id);
			ResultSet rs = pst.executeQuery();
			rs.next();
			result = rs.getInt("cantidad");
		} catch (Exception e) {
			throw (e);
		}
		return result;

	}

	public int grabarTablaMpRelacionadoHistorico(MpRelacionado mpRelacionado)
			throws Exception {
		int result = 0;
		try {
			Integer id = ObtenerCorrelativoTabla("mp_relacionado_historico", 1);
			StringBuffer SQL = new StringBuffer();
			SQL.append(" insert into ").append(Constante.schemadb)
					.append(".mp_relacionado_historico ");
			SQL.append(" SELECT ? ");
			SQL.append(" ,persona_id,relacionado_id,tipo_relacion_id,nro_docu_identidad,primer_nombre ");
			SQL.append(" ,segundo_nombre,ape_paterno,ape_materno,email,facebook,telefono,twitter ");
			SQL.append(" ,usuario_id,estado,fecha_registro,terminal,tipo_doc_identidad_id,apellidos_nombres ");
			SQL.append(" FROM ").append(Constante.schemadb)
					.append(".mp_relacionado where relacionado_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, id);
			pst.setInt(2, mpRelacionado.getId().getRelacionadoId());
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	/*
	 * Metodo que nos pemite consultar los DNI segun los parametros apellido
	 * paterno, materno, nombres (este último es opcional)
	 * 
	 * @param: ape_mat - Apellido Materno
	 * 
	 * @param: ape_pat - Apellido Paterno
	 * 
	 * @param: primerNombre - Primer nombre de la persona
	 * 
	 * @param: segundoNombre - segundo nombre de la persona
	 */
	public List<TgPersona> findDNIPersona(String ape_pat, String ape_mat,
			String primerNombre, String segundoNombre) throws Exception {
		TgPersona tgPersona = null;
		List<TgPersona> personas = new ArrayList<TgPersona>();

		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(
					"SELECT cmasteri,le,libro,sp_dep,sp_prv,sp_dst,ape_pat,ape_mat,nombre,fec_nac = CONVERT(datetime,[fec_nac],103),sexo,sp_gri,sp_sec,sp_doc FROM ")
					.append(Constante.schemaper)
					.append(".tg_persona WHERE ape_pat=? and ape_mat=? and nombre like ?");
			PreparedStatement pst = connectPersona().prepareStatement(
					SQL.toString());
			pst.setString(1, ape_pat);
			pst.setString(2, ape_mat);
			String nombresCompletos = (primerNombre.trim().length() == 0) ? ""
					: primerNombre
							+ " "
							+ ((segundoNombre.trim().length() == 0) ? ""
									: segundoNombre);
			nombresCompletos = "%" + nombresCompletos + "%";
			pst.setString(3, nombresCompletos);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				tgPersona = new TgPersona();
				tgPersona.setCmasteri(rs.getString("cmasteri") != null ? rs
						.getString("cmasteri").trim() : "");
				tgPersona.setLe(rs.getString("le") != null ? rs.getString("le")
						.trim() : "");
				tgPersona.setLibro(rs.getString("libro") != null ? rs
						.getString("libro").trim() : "");
				tgPersona.setSp_dep(rs.getString("sp_dep") != null ? rs
						.getString("sp_dep").trim() : "");
				tgPersona.setSp_prv(rs.getString("sp_prv") != null ? rs
						.getString("sp_prv").trim() : "");
				tgPersona.setSp_dst(rs.getString("sp_dst") != null ? rs
						.getString("sp_dst").trim() : "");
				tgPersona.setApe_pat(rs.getString("ape_pat") != null ? rs
						.getString("ape_pat").trim() : "");
				tgPersona.setApe_mat(rs.getString("ape_mat") != null ? rs
						.getString("ape_mat").trim() : "");
				tgPersona.setNombre(rs.getString("nombre") != null ? rs
						.getString("nombre").trim() : "");
				// tgPersona.setFec_nac(rs.getString("fec_nac")!=null?rs.getString("fec_nac").trim():"");
				tgPersona
						.setFechaNacimiento(rs.getTimestamp("fec_nac") != null ? rs
								.getTimestamp("fec_nac") : null);
				tgPersona.setSexo(rs.getString("sexo") != null ? rs.getString(
						"sexo").trim() : "");
				tgPersona.setSp_gri(rs.getString("sp_gri") != null ? rs
						.getString("sp_gri").trim() : "");
				tgPersona.setSp_sec(rs.getString("sp_sec") != null ? rs
						.getString("sp_sec").trim() : "");
				tgPersona.setSp_doc(rs.getString("sp_doc") != null ? rs
						.getString("sp_doc").trim() : "");
				personas.add(tgPersona);
			}
		} catch (Exception e) {
			throw (e);
		}
		return personas;
	}

	public FindMpPersona existeContribuyente2(Integer tipodocumentoid,
			String nroDocumento) throws Exception {
		FindMpPersona obj = null;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" select p.persona_id,tdi.tipo_doc_identidad_id,tdi.descripcion tipo_doc_identidad,p.nro_docu_identidad,p.apellidos_nombres,p.razon_social,tp.descripcion tipo_persona,stp.descripcion subtipopersona ");
			SQL.append(" from " + SATParameterFactory.getDBNameScheme()
					+ ".mp_persona p  ");
			SQL.append(" inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_tipo_docu_identidad tdi on p.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id ");
			SQL.append(" inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_tipo_persona tp on p.tipo_persona_id=tp.tipo_persona_id  ");
			SQL.append(" inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_subtipo_persona stp on p.subtipo_persona_id=stp.subtipo_persona_id ");
			SQL.append(" where p.tipo_doc_identidad_id=? and p.nro_docu_identidad=? and p.estado='1' ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipodocumentoid);
			pst.setString(2, nroDocumento);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				obj = new FindMpPersona();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipodocumentoId(rs.getInt("tipo_doc_identidad_id"));
				obj.setTipodocumento(rs.getString("tipo_doc_identidad"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setTipoPersona(rs.getString("tipo_persona"));
				obj.setSubtipopersona(rs.getString("subtipopersona"));
			}
		} catch (Exception e) {
			throw (e);
		}
		return obj;
	}

	public BuscarPersonaDTO findPaPersona(Integer personaId) throws Exception {
		BuscarPersonaDTO persona = new BuscarPersonaDTO();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" select  pa.estado,pa.usuario_id,pa.fecha_registro,pa.terminal,gn.persona_id, isnull(gn.tipo_documento_id,1) tipo_documento_id, isnull(gn.nro_doc_identidad,'') nro_doc_identidad, ");
			SQL.append(" isnull(gn.primer_nombre,'') primer_nombre, isnull(gn.segundo_nombre,'') segundo_nombre,   ");
			SQL.append(" isnull(gn.ape_paterno,'')ape_paterno, isnull(gn.ape_materno,'')ape_materno, isnull(gn.tipo_documento_id,1)tipo_documento_id, isnull(pa.num_licencia,'') num_licencia, ");
			SQL.append(" isnull(pa.clase_licencia_id,0) clase_licencia_id, isnull(gn.razon_social,'') razon_social, isnull(gn.domicilio_completo,'') domicilio_completo, isnull(gn.apellidos_nombres,'') apellidos_nombres ");
			SQL.append(" from dbo.gn_persona gn  ");
			SQL.append(" inner join dbo.pa_persona pa  on (gn.persona_id = pa.persona_id) ");
			SQL.append(" where gn.persona_id=? and gn.estado='1'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				persona.setPersonaId(rs.getInt("persona_id"));
				persona.setTipoDocIdentidadId(rs.getInt("tipo_documento_id"));
				persona.setNroDocuIdentidad(rs.getString("nro_doc_identidad"));
				persona.setPrimerNombre(rs.getString("primer_nombre"));
				persona.setSegundoNombre(rs.getString("segundo_nombre"));
				persona.setApellidoPaterno(rs.getString("ape_paterno"));
				persona.setApellidoMaterno(rs.getString("ape_materno"));
				persona.setTipodocumentoIdentidadId(rs.getInt("tipo_documento_id"));
				persona.setNumLicencia(rs.getString("num_licencia"));
				persona.setClaseLicenciaId(rs.getInt("clase_licencia_id"));
				persona.setRazonSocial(rs.getString("razon_social"));
				persona.setDireccionCompleta(rs.getString("domicilio_completo"));
				persona.setApellidosNombres(rs.getString("apellidos_nombres"));
				
				persona.setTerminal(rs.getString("terminal"));
				persona.setFechaRegistro(rs.getTimestamp("fecha_registro"));				
				persona.setUsuarioId(rs.getInt("usuario_id"));
				persona.setEstado(rs.getString("estado"));
				
				
				
				
			}
		} catch (Exception e) {
			throw (e);
		}
		return persona;
	}

	public List<BuscarPersonaDTO> busquedaPersona(
			ParamBusquedaPersonaDTO paramBusquedaPersonaDTO)
			throws SisatException {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {

			StringBuilder SQL = new StringBuilder(
					" dbo.stp_gn_obtener_datos_persona ?,?,?,?,?,?,?,?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());

			if (paramBusquedaPersonaDTO.getPersonaId() != null
					&& paramBusquedaPersonaDTO.getPersonaId().intValue() > 0) {
				pst.setInt(1, paramBusquedaPersonaDTO.getPersonaId().intValue());
			} else {
				pst.setNull(1, Types.INTEGER);
			}

			if (paramBusquedaPersonaDTO.getApellidoPaterno() != null
					&& !paramBusquedaPersonaDTO.getApellidoPaterno().trim()
							.isEmpty()) {
				pst.setString(2, paramBusquedaPersonaDTO.getApellidoPaterno());
			} else {
				pst.setNull(2, Types.VARCHAR);
			}

			if (paramBusquedaPersonaDTO.getApellidoMaterno() != null
					&& !paramBusquedaPersonaDTO.getApellidoMaterno().trim()
							.isEmpty()) {
				pst.setString(3, paramBusquedaPersonaDTO.getApellidoMaterno());
			} else {
				pst.setNull(3, Types.VARCHAR);
			}

			if (paramBusquedaPersonaDTO.getPrimerNombre() != null
					&& !paramBusquedaPersonaDTO.getPrimerNombre().trim()
							.isEmpty()) {
				pst.setString(4, paramBusquedaPersonaDTO.getPrimerNombre());
			} else {
				pst.setNull(4, Types.VARCHAR);
			}

			if (paramBusquedaPersonaDTO.getApellidosNombres() != null
					&& !paramBusquedaPersonaDTO.getApellidosNombres().trim()
							.isEmpty()) {
				pst.setString(5, paramBusquedaPersonaDTO.getApellidosNombres()
						.concat("%"));
			} else {
				pst.setNull(5, Types.VARCHAR);
			}

			if (paramBusquedaPersonaDTO.getRazonSocial() != null
					&& !paramBusquedaPersonaDTO.getRazonSocial().trim()
							.isEmpty()) {
				pst.setString(6, paramBusquedaPersonaDTO.getRazonSocial());
			} else {
				pst.setNull(6, Types.VARCHAR);
			}

			if (paramBusquedaPersonaDTO.getTipoDocIdentidadId() != null) {
				pst.setInt(7, paramBusquedaPersonaDTO.getTipoDocIdentidadId()
						.intValue());
			} else {
				pst.setNull(7, Types.INTEGER);
			}

			if (paramBusquedaPersonaDTO.getNroDocumento() != null
					&& !paramBusquedaPersonaDTO.getNroDocumento().trim()
							.isEmpty()) {
				pst.setString(8, paramBusquedaPersonaDTO.getNroDocumento());
			} else {
				pst.setNull(8, Types.VARCHAR);
			}

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarPersonaDTO obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipodocumentoIdentidadId(rs.getInt("tipo_documento_id"));
				obj.setTipoDocIdentidad(rs.getString("tipo_doc_as_string"));
				obj.setNroDocuIdentidad(rs.getString("nro_doc_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setDireccionCompleta(rs.getString("domicilio_completo"));
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				obj.setEsContribuyente(rs.getInt("es_contribuyente") == 0 ? Boolean.FALSE
						: Boolean.TRUE);
				lista.add(obj);
			}
		} catch (Exception e) {
			throw new SisatException(e);
		}
		return lista;
	}

	public List<BuscarPersonaDTO> findTraDocPersona(Integer personaId,
			String apePaterno, String apeMaterno, String primerNombre,
			String apellidos_nombres, String documentoidentidad, Integer nroDj,
			Integer tipoDocumentoIdentidad, String razonSocial,
			String codigoAnterior, String placa) throws SisatException {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();

		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" select distinct TOP 50 gp.persona_id,tp.descripcion tipo_persona,tp.tipo_persona_id ,tdi.descripcion tipo_documentoent,tdi.tipo_doc_identidad_id,gp.ape_paterno,gp.ape_materno,");
			SQL.append(" gp.primer_nombre,gp.segundo_nombre,gp.razon_social,gp.nro_doc_identidad, p.flag_notifica_email, ");
			SQL.append(" gp.apellidos_nombres,");			
			SQL.append(" (select COUNT(distinct djp.predio_id) from ")
					.append(Constante.schemadb)
					.append(".rp_djpredial djp where djp.persona_id=gp.persona_id and djp.estado='")
					.append(Constante.ESTADO_ACTIVO)
					.append("' and djp.flag_dj_anno='")
					.append(Constante.FLAG_DJ_ANIO_ACTIVO)
					.append("' and motivo_declaracion_id != 4 and (motivo_descargo_id != 98 or motivo_descargo_id is null)) as predios,");
			SQL.append(" (select COUNT(distinct djv.vehiculo_id) from  ")
					.append(Constante.schemadb)
					.append(".rv_djvehicular djv where djv.persona_id=gp.persona_id and djv.estado='")
					.append(Constante.ESTADO_ACTIVO)
					.append("') as vehiculos,COALESCE(d.direccion_completa,gp.domicilio_completo) AS direccion_completa,p.estado,p.nro_dj,p.nro_dj_formato");
			SQL.append(" ,stp.descripcion subtipopersona,p.subtipo_persona_id,p.fecha_declaracion,p.nro_docu_identidad_adi,p.telefono,p.fax,p.email,p.twitter,p.facebook,ce.condicion_especial_id,tce.descripcion tipocondicionespecial, td.tipo_documento_condicion_especial_id,td.descripcion tipodocumento,te.descripcion situacionempresarial  ");
			SQL.append(" ,p.fecha_situacion_empre ,p.fecha_fin_situacion_empresarial,p.fecha_inscripcion,p.tipo_doc_sust_situacion_empresarial ");
			SQL.append(" ,p.nro_doc_sust_situacion_empresarial,p.fecha_emision_situacion_empresarial,p.situacion_empresarial_id,p.nro_partida_defuncion,p.fecha_defuncion,ce.tipo_cond_especial_id,p.fecha_registro,ce.nro_documento cond_espe_nro_documento,ce.fecha_inicio cond_espe_fecha_inicio  ,ce.fecha_fin cond_espe_fecha_fin,ce.fecha_documento cond_espe_fecha_documento");
			SQL.append(" ,d.ubicacion_id, flag_estatal, p.glosa, ce.porcentaje ");		
			SQL.append(" FROM ").append(Constante.schemadb)
					.append(".gn_persona gp");
			SQL.append(" LEFT join ")
					.append(Constante.schemadb)
					.append(".mp_persona p on (gp.persona_id=p.persona_id)");
			SQL.append(" LEFT join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_persona tp on (p.tipo_persona_id=tp.tipo_persona_id)");
			SQL.append(" LEFT join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi on (p.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id)");
			SQL.append(" LEFT join ")
					.append(Constante.schemadb)
					.append(".mp_subtipo_persona stp on tp.tipo_persona_id = stp.tipo_persona_id  and p.subtipo_persona_id=stp.subtipo_persona_id");
			SQL.append(" LEFT join ")
					.append(Constante.schemadb)
					.append(".gn_condicion_especial ce on p.persona_id=ce.persona_id and ce.estado='1' ");

			SQL.append(" LEFT join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_documento_condicion_especial td on td.tipo_documento_condicion_especial_id=ce.tipo_documento ");
			SQL.append(" LEFT join ")
					.append(Constante.schemadb)
					.append(".mp_tipo_condicion_especial tce on ce.tipo_cond_especial_id=tce.tipo_cond_especial_id ");
			SQL.append(" LEFT join ")
					.append(Constante.schemadb)
					.append(".mp_situacion_empresarial te on p.situacion_empresarial_id=te.situacion_empresarial_id ");
			SQL.append(" LEFT join ")
					.append(Constante.schemadb)
					.append(".mp_persona_domicilio pd on (p.persona_id=pd.persona_id and pd.flag_fiscal='1' and pd.domicilio_persona_id is not null and pd.estado IN ('1','2')) ");
			SQL.append(" LEFT join ")
					.append(Constante.schemadb)
					.append(".mp_direccion d on (p.persona_id=d.persona_id and pd.direccion_id=d.direccion_id and d.estado IN ('1','2')) ");

			// Correccion de consulta inicio 12/02/2014 : inicio
			/*
			 * SQL.append(" left join ") .append(Constante.schemadb) .append(
			 * ".rv_vehiculo v on ( v.vehiculo_id in (select djv.vehiculo_id from rv_djvehicular djv where djv.persona_id = p.persona_id and djv.estado in ('1','2') )) "
			 * );
			 */
			SQL.append(" LEFT JOIN ")
					.append(Constante.schemadb)
					.append(".rv_djvehicular djv on (djv.persona_id=p.persona_id and djv.estado IN ('1','2')) ");
			SQL.append(" LEFT JOIN ")
					.append(Constante.schemadb)
					.append(".rv_vehiculo v on (v.vehiculo_id=djv.vehiculo_id) ");
			// Correccion de consulta inicio 12/02/2014 : fin

			SQL.append(" WHERE gp.estado IN ('1','2') ");
			if (personaId != null && personaId > 0)
				SQL.append(" and gp.persona_id = ").append(personaId);
			if (nroDj != null && nroDj > 0)
				SQL.append(" and p.nro_dj = ").append(nroDj);
			if (apePaterno != null && apePaterno.trim().length() > 0)
				SQL.append(" and gp.ape_paterno like UPPER('%")
						.append(apePaterno).append("%')");
			if (apeMaterno != null && apeMaterno.trim().length() > 0)
				SQL.append(" and gp.ape_materno like UPPER('%")
						.append(apeMaterno).append("%')");
			if (primerNombre != null && primerNombre.trim().length() > 0)
				SQL.append(" and gp.primer_nombre like UPPER('")
						.append(primerNombre).append("%')");
			if (apellidos_nombres != null
					&& apellidos_nombres.trim().length() > 0)
				SQL.append(" and gp.apellidos_nombres like UPPER('%")
						.append(apellidos_nombres).append("%')");
			if (documentoidentidad != null
					&& documentoidentidad.trim().length() > 0)
				SQL.append(" and gp.nro_doc_identidad ='")
						.append(documentoidentidad).append("'");
			/*
			if (tipoDocumentoIdentidad != null && tipoDocumentoIdentidad > 0)
				SQL.append(" and tdi.tipo_doc_identidad_id = ")
						.append(tipoDocumentoIdentidad).append("");
			*/
			if (razonSocial != null && razonSocial.trim().length() > 0)
				SQL.append(" and gp.razon_social like UPPER('%")
						.append(razonSocial).append("%')");
			if (codigoAnterior != null && codigoAnterior.trim().length() > 0)
				SQL.append(" and RTRIM(LTRIM(gp.cod_ant_satcaj)) = '")
						.append(codigoAnterior).append("'");
			if (placa != null && placa.trim().length() > 0)
				SQL.append(" and v.placa = UPPER('").append(placa.trim())
						.append("')");
			// .append(" or v.placa_anterior like UPPER('").append(placa).append("')");

			SQL.append(" order by p.nro_dj ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				BuscarPersonaDTO obj = new BuscarPersonaDTO();

				obj.setPersonaId(rs.getInt("persona_id"));

				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_Materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setNroDocuIdentidad(rs.getString("nro_doc_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setTipoDocumentoIdentidadAsString(rs
						.getString("tipo_documentoent"));
				obj.setTipoPersonaAsString(rs.getString("tipo_persona"));
				obj.setDireccionCompleta(rs.getString("direccion_completa"));

				obj.setSubtipoPersonaId(rs.getInt("subtipo_persona_id"));

				obj.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				obj.setTipoDocIdentidadId(rs.getInt("tipo_doc_identidad_id"));

				obj.setFechaDefuncion(rs.getTimestamp("fecha_defuncion"));

				lista.add(obj);
			}
		} catch (Exception e) {
			throw new SisatException(e.getMessage());
		}
		return lista;
	}	
	
	public int editarCorreoTelefono(PersonaEditTelEmailDTO persona,Integer usuarioId,String terminal ) throws SisatException {
		int result = 0;
		
		try {
			String SQL = new String("dbo.stp_edit_persona_telefono_correo ?, ?, ?, ?, ?, ?, ?, ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, persona.getPersona_id());
			pst.setString(2, persona.getTelefono());
			pst.setString(3, persona.getFax());
			pst.setString(4, persona.getEmail());
			pst.setString(5, persona.getTwitter());
			pst.setString(6, persona.getFacebook());
			pst.setInt(7, usuarioId);
			pst.setString(8, terminal);
				
			result = pst.executeUpdate();
			
			//result = 1;
			
		} catch (Exception ex) {
			String msg = "Ocurrio un error al actualizar : " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return result;
		
	}
	
	
	public List<PersonaEstadoCuentaResumido> getEstadoCuentaResumido(Integer personaId) {
		
				List<PersonaEstadoCuentaResumido> estado = new ArrayList<PersonaEstadoCuentaResumido>();
				
				try {
					String SQL = new String("dbo.stp_cr_estado_cuenta_resumido ? ");
				
					PreparedStatement pst = connect().prepareStatement(SQL.toString());
					pst.setInt(1, personaId);
					
					ResultSet rs = pst.executeQuery();
		
					while (rs.next()) {	
						PersonaEstadoCuentaResumido obj = new PersonaEstadoCuentaResumido();
						
						obj.setPersonaId(rs.getInt("persona_id"));
						obj.setAnnoDeuda(rs.getString("anno_deuda"));
						obj.setConceptoId(rs.getInt("concepto_id"));
						obj.setConceptoDescripcion(rs.getString("concepto_descripcion"));
						obj.setSubconceptoId(rs.getInt("subconcepto_id"));
						obj.setSubconceptoDescripcion(rs.getString("subconcepto_descripcion"));
						obj.setCuotas(rs.getString("cuotas"));
						
						estado.add(obj);
					}
					
				} catch (Exception ex) {
					String msg = "No se ha podido recuperar informacion: " ;
					System.out.println(msg + ": " + ex);
				}
		
		return estado;
	}
	
	

	
	public PersonaEditTelEmailDTO finPersonaEditTelEmail(Integer personaId) {
		PersonaEditTelEmailDTO persona = new PersonaEditTelEmailDTO();
				
				try {
					String SQL = new String("dbo.stp_find_persona_edit_telefono_correo ? ");
				
					PreparedStatement pst = connect().prepareStatement(SQL.toString());
					pst.setInt(1, personaId);
					
					ResultSet rs = pst.executeQuery();
		
					while (rs.next()) {	
						PersonaEditTelEmailDTO obj = new PersonaEditTelEmailDTO();
						
						obj.setPersona_id(rs.getInt("persona_id"));
						obj.setApellidos_nombres(rs.getString("apellidos_nombres"));
						obj.setTipo_persona(rs.getString("tipo_persona"));
						obj.setTipo_documento(rs.getString("tipo_documento"));
						obj.setNro_docu_identidad(rs.getString("nro_docu_identidad"));
						obj.setTelefono(rs.getString("telefono"));
						obj.setFax(rs.getString("fax"));
						obj.setEmail(rs.getString("email"));
						obj.setTwitter(rs.getString("twitter"));
						obj.setFacebook(rs.getString("facebook"));
						
						persona = obj;
					}
					
				} catch (Exception ex) {
					String msg = "No se ha podido recuperar informacion: " ;
					System.out.println(msg + ": " + ex);
				}
		
		return persona;
	}
	
	
	
	public int changeFlagUbicableControl(Integer personaId, Integer flagUbicable, Integer usuarioId, String terminal) {
		
		int result = 0;
				try {
					String SQL = new String("dbo.usp_cambio_persona_ubicable ?, ?, ?, ? ");
				
					PreparedStatement pst = connect().prepareStatement(SQL.toString());
					pst.setInt(1, personaId);
					pst.setInt(2, flagUbicable);
					pst.setInt(3, usuarioId);
					pst.setString(4, terminal);
					
					result = pst.executeUpdate();
					
				} catch (Exception ex) {
					String msg = "No se ha podido actualizar el Flag: " ;
					System.out.println(msg + ": " + ex);
				}
		
		return result;
	}
	
	
	public List<ValidaUbicacionDTO> getValidaPersonaUbicacion(Integer personaId) {
			
		List<ValidaUbicacionDTO> personas = new ArrayList<ValidaUbicacionDTO>();
			
		try {
				String SQL = new String("dbo.usp_get_persona_ubicable ? ");
			
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, personaId);
				
				ResultSet rs = pst.executeQuery();
				
				while (rs.next()) {	
					ValidaUbicacionDTO obj = new ValidaUbicacionDTO();
					
					obj.setPersona_id(rs.getInt("persona_id"));
					obj.setEstado(rs.getInt("estado"));
					obj.setIs_ubicable_control(rs.getInt("is_ubicable_control"));
					obj.setUsuario_registro_id(rs.getInt("usuario_registro_id"));
					obj.setTerminal_registro(rs.getString("terminal_registro"));
					obj.setFecha_registro(rs.getTimestamp("fecha_registro"));
					obj.setNombre_usuario_registro(rs.getString("nombre_usuario_registro"));
					obj.setUsuario_valida_id(rs.getInt("usuario_valida_id"));
					obj.setTerminal_valida(rs.getString("terminal_valida"));
					obj.setFecha_valida(rs.getTimestamp("fecha_valida"));
					obj.setNombre_usuario_valido(rs.getString("nombre_usuario_valido"));
					obj.setApellidos_nombres(rs.getString("apellidos_nombres"));
					obj.setGlosa(rs.getString("glosa"));
					obj.setDias(rs.getInt("dias"));
					
					personas.add(obj);
				}
				
			} catch (Exception ex) {
				String msg = "No se ha podido actualizar el Flag: " ;
				System.out.println(msg + ": " + ex);
			}
			
			return personas;
		}
	
	
	public int changeFlagUbicableVerifica(Integer personaId,Integer estado,String glosa ,Integer flagUbicable, Integer usuarioId, String terminal) {
		
		int result = 0;
				try {
					String SQL = new String("dbo.usp_change_persona_ubicable ?, ?, ?, ?,?,? ");
					
					System.out.println(personaId);
					System.out.println(estado);
					System.out.println(glosa);
					System.out.println(flagUbicable);
					System.out.println(usuarioId);
					System.out.println(terminal);
					
					PreparedStatement pst = connect().prepareStatement(SQL.toString());
					pst.setInt(1, personaId);
					pst.setInt(2, estado);
					pst.setString(3, glosa);
					pst.setInt(4, flagUbicable);
					pst.setInt(5, usuarioId);
					pst.setString(6, terminal);
					
					result = pst.executeUpdate();
					
				} catch (Exception ex) {
					String msg = "No se ha podido actualizar el Flag: " ;
					System.out.println(msg + ": " + ex);
				}
		
		return result;
	}
	
	



}