package com.sat.sisat.expediente.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.expediente.dto.FindExpedienteByPersona;

public class ExpedienteBusinessDao extends GeneralDao {
		public List<FindExpedienteByPersona> getAllExpedientesByPersona(Integer personaId) throws Exception {
			List<FindExpedienteByPersona> list = new ArrayList<FindExpedienteByPersona>();

			try {
				String SQL = new String("stp_getExpedienteByPersona ?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, personaId);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					FindExpedienteByPersona obj = new FindExpedienteByPersona();
					
					obj.setId(rs.getInt("id"));
					obj.setActoId(rs.getInt("acto_id"));
					obj.setExpediente(rs.getString("expediente"));
					obj.setAnioPaquete(rs.getInt("anno_paquete"));
					obj.setPaquete(rs.getInt("paquete"));
					obj.setTipoActo(rs.getInt("tipo_acto"));
					obj.setTipoRec(rs.getString("tipo_rec"));
					obj.setTipoValor(rs.getString("tipo_valor"));
					obj.setPeriodo(rs.getInt("periodo"));
					obj.setFechaEmision(rs.getDate("fecha_emision"));
					obj.setFechaNotificacion(rs.getDate("fecha_notificacion"));
					obj.setMonto(rs.getBigDecimal("monto"));					
					obj.setRutaCompleta(rs.getString("ruta_completa"));
					list.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return list;			
		}

}
