package com.sat.sisat.cuponera.dao;

import java.sql.CallableStatement;

import com.sat.sisat.common.dao.GeneralDao;

public class CuponeraDao extends GeneralDao {
	public int registraPersonaCuponera(Integer personaId) {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall("{call dbo.sp_create_cuponera_individual(?)}");
			cs.setInt(1, personaId);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

}
