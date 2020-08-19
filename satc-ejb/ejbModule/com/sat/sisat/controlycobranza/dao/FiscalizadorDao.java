package com.sat.sisat.controlycobranza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.controlycobranza.dto.MpFiscalizador;
import com.sat.sisat.controlycobranza.dto.MpFiscalizadorArea;

public class FiscalizadorDao extends GeneralDao{

	
	private EntityManager em;
	private DataSource ds;
	

	public FiscalizadorDao(EntityManager em, DataSource ds) {
		super();
		this.em = em;
		this.ds = ds;
	}
	
	public <T> T create(T t) {
		this.em.persist(t);
		this.em.flush(); 		
		return t;
	}
	
	public void update(MpFiscalizador f) {
		//return (T) this.em.merge(t);
		try {
			StringBuilder SQL = new StringBuilder("UPDATE mp_fiscalizador SET nombresApellidos = '"+f.getNombresApellidos()+"', dni = '"+f.getDni()+"', direccion = '"+f.getDireccion()+"', fini = '"+f.getFini()+"', ffin = '"+f.getFfin()+"' WHERE idfiscalizador = "+f.getIdfiscalizador());
			//StringBuilder SQL = new StringBuilder("UPDATE fiscalizador SET nombresApellidos = 'adasdas', dni = "+f.getDni()+", direccion = "+f.getDireccion()+", fini = '2013-12-11', ffin = '2013-12-11' WHERE idfiscalizador = 7");
			
			Connection c=ds.getConnection();
			
			PreparedStatement pst = c.prepareStatement(SQL.toString());
			pst.executeUpdate();
			
			//Se desarrollo as� debido a que con el otro m�todo no reconoc�a los INT al momento de enviar la consulta
			//Por esta raz�n se hizo con JDBC
			
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("ERROR : " + ex);
		}
	}
	
	public void delete(int t) {
		//Object ref = this.em.getReference(t.getClass(), t);
		//this.em.remove(ref); 
		
		//Con los metodos de arriba sale la excepcion de que los INT no son aceptados o desconocidos.
		
		try {
			StringBuilder SQL = new StringBuilder("UPDATE mp_fiscalizador SET estado = 9 WHERE codigo ="+t);
			
			Connection c=ds.getConnection();
			
			PreparedStatement pst = c.prepareStatement(SQL.toString());
			pst.executeUpdate();
			
			//Se desarrollo as� debido a que con el otro m�todo no reconoc�a los INT al momento de enviar la consulta
			//Por esta raz�n se hizo con JDBC
			
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("ERROR : " + ex);
		}
	}
	
	public <T> T find(Object id, Class<T> type) {
		return (T) this.em.find(type, id);
	}
	
	public List<MpFiscalizadorArea> listarEmpleos(){
		//int resultado=0;
		
		List<MpFiscalizadorArea> listaEmpleo = new ArrayList<MpFiscalizadorArea>();
		try {
			ArrayList<String[]> matResultado=new ArrayList<String[]>();
			StringBuilder SQL = new StringBuilder("select u.unidad_id,u.descripcion from gn_unidad u where u.estado=1");
			
			Connection c=ds.getConnection();
			
			PreparedStatement pst = c.prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			

			while(rs.next()){
				MpFiscalizadorArea p = new MpFiscalizadorArea();
				p.setUnidad_id(rs.getInt("unidad_id"));
				p.setDescripcion(rs.getString("descripcion"));
				
				listaEmpleo.add(p);
			}
            
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("ERROR : " + ex);
		}
		return listaEmpleo;
	}	
	


	public List<MpFiscalizador> listarPersonas(){
		//int resultado=0;
		
		List<MpFiscalizador> listaPersonas = new ArrayList<MpFiscalizador>();
		try {
			ArrayList<String[]> matResultado=new ArrayList<String[]>();
			StringBuilder SQL = new StringBuilder("SELECT * FROM mp_fiscalizador");
			
			Connection c=ds.getConnection();
			
			PreparedStatement pst = c.prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			

			while(rs.next()){
				MpFiscalizador p = new MpFiscalizador();
				p.setCodigo(rs.getString("codigo"));
				p.setNombresApellidos(rs.getString("nombresApellidos"));
				
				listaPersonas.add(p);
			}
            
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("ERROR : " + ex);
		}
		return listaPersonas;
	}	
	
		
	
}
