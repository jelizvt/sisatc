package com.sat.sisat.reportes.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.reportes.dto.AnalisisCuentaDTO;
import com.sat.sisat.reportes.dto.ConSubConDTO;
import com.sat.sisat.reportes.dto.ReporDeudaDTO;
import com.sat.sisat.reportes.dto.ReporRecaudacionDTO;

public class RecaudacionesBusinessDao extends GeneralDao{
	
	public ArrayList<ReporRecaudacionDTO> getRecaudaciones(int anioInicio, int anioFin, int diaInicio, int diaFin, int mesInicio, int mesFin) {
		ArrayList<ReporRecaudacionDTO> Listareca= new ArrayList<ReporRecaudacionDTO>();

		try {
			
			StringBuilder SQL= new StringBuilder();
			//SQL.append("select DC.CONCEPTO_ID,DC.DESCRIPCION CONCEPTO, DSC.DESCRIPCION SUBCONCEPTO");
			SQL.append("select DC.CONCEPTO_ID,DC.DESCRIPCION CONCEPTO ");
			SQL.append(",FD.DEUDA_ID,FD.TOTAL_DEUDA,FD.TOTAL_CANCELADO,FD.ANNO_DEUDA ");
			SQL.append(",DP.PERSONA_ID,DP.TIPO_DOCUMENTO_ID,DP.NRO_DOC_IDENTIDAD,DP.RAZON_SOCIAL ");
			SQL.append(",DP.APELLIDOS_NOMBRES ");
			SQL.append(",DT.TIEMPO_ID,DT.FECHA,DT.ANIO,DT.MES,DT.SEMANA,DT.DIA,DT.DIASEMANA ");
			SQL.append(",FR.MONTO_PAGO PAGO_SOLES ");
			SQL.append(",FR.MONTO_PAGO/1000 MILES_SOLES ");
			SQL.append(",FR.MONTO_PAGO/1000000 MILLONES_SOLES ");
			//SQL.append(",(FR.MONTO_PAGO*100)/FD.TOTAL_DEUDA PORCENTAJE ");
			SQL.append(",(select SUM(fr1.monto_pago) from fac_recaudaciones fr1 ");
			SQL.append("inner join DIM_TIEMPO dt1 on dt1.TIEMPO_ID=fr1.tiempo_id ");
			SQL.append("where dt1.TIEMPO_ID=dt1.TIEMPO_ID) RECAUDACION_ANIO ");
			
			SQL.append(",(select SUM(fr2.monto_pago) from "+SATParameterFactory.getDBDWNameScheme()+".fac_recaudaciones fr2 ");
			SQL.append("inner join "+SATParameterFactory.getDBDWNameScheme()+".fac_deuda d2 on d2.deuda_id=fr2.deuda_id ");
			SQL.append("where d2.anno_deuda=fd.anno_deuda ) RECAUDACION_ANIO_DEUDA ");
			
			SQL.append(",(select SUM(fr2.monto_pago) from "+SATParameterFactory.getDBDWNameScheme()+".fac_recaudaciones fr2 ");
			SQL.append("inner join "+SATParameterFactory.getDBDWNameScheme()+".fac_deuda d2 on d2.deuda_id=fr2.deuda_id ");
			SQL.append("where d2.anno_deuda=fd.anno_deuda ");
			SQL.append(")*100/(select SUM(FD3.total_deuda) ");
			SQL.append("from "+SATParameterFactory.getDBDWNameScheme()+".fac_deuda FD3  ");
			SQL.append("where FD3.anno_deuda=fd.anno_deuda ");
			SQL.append(") PORCENTAJE ");
			
			SQL.append(",(select SUM(fr3.monto_pago) from "+SATParameterFactory.getDBDWNameScheme()+".fac_recaudaciones fr3 ");
			SQL.append(" inner join "+SATParameterFactory.getDBDWNameScheme()+".fac_deuda d3 on d3.deuda_id=fr3.deuda_id");
			SQL.append(" where d3.concepto_id=dc.concepto_id ");
			SQL.append(" )*100/(select SUM(FD4.total_deuda) ");
			SQL.append(" from "+SATParameterFactory.getDBDWNameScheme()+".fac_deuda FD4 ");
			SQL.append(" where FD4.concepto_id=dc.concepto_id ");
			SQL.append(" )  PORC_RECA_CONCEPTO ");
			
			SQL.append("from  "+SATParameterFactory.getDBDWNameScheme()+".FAC_RECAUDACIONES FR ");
			SQL.append("INNER JOIN "+SATParameterFactory.getDBDWNameScheme()+".DIM_CONCEPTO DC ON DC.CONCEPTO_ID=FR.CONCEPTO_ID ");
			SQL.append("INNER JOIN "+SATParameterFactory.getDBDWNameScheme()+".DIM_PERSONA DP ON DP.PERSONA_ID= FR.PERSONA_ID ");
			SQL.append("INNER JOIN "+SATParameterFactory.getDBDWNameScheme()+".DIM_DEUDA DD ON DD.DEUDA_ID=FR.DEUDA_ID ");
			SQL.append("INNER JOIN "+SATParameterFactory.getDBDWNameScheme()+".FAC_DEUDA FD ON FD.DEUDA_ID=DD.DEUDA_ID ");
			SQL.append("INNER JOIN "+SATParameterFactory.getDBDWNameScheme()+".DIM_TIEMPO DT ON DT.TIEMPO_ID=FR.TIEMPO_ID ");
			//SQL.append("INNER JOIN "+SATParameterFactory.getDBDWNameScheme()+".DIM_SUBCONCEPTO DSC ON DSC.CONCEPTO_ID=DC.CONCEPTO_ID ");
			
			if(anioInicio>0 && anioFin==0){
				SQL.append(" where convert(int,DT.ANIO)>="+anioInicio);	
			}
			else if(anioInicio==0 && anioFin>0){
				SQL.append(" where convert(int,DT.ANIO)<= "+anioFin);	
			}
			else if(anioInicio>0 && anioFin>0){
				SQL.append(" where convert(int,DT.ANIO)>="+anioInicio);	
				SQL.append(" and convert(int,DT.ANIO)<= "+anioFin);
			}


			if(mesInicio>0){
				SQL.append(" and str(month(DT.FECHA))>="+mesInicio);
			}
			if(mesFin>0){
				SQL.append(" and str(month(DT.FECHA))<="+mesFin);
			}
			if(diaInicio>0){
				SQL.append(" and str(DAY(DT.FECHA))>="+diaInicio);
			}
			if(diaFin>0){
				SQL.append(" and str(DAY(DT.FECHA))<="+diaFin);
			}
			
			SQL.append(" order by fd.anno_deuda asc");

			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				ReporRecaudacionDTO obj= new ReporRecaudacionDTO();
				obj.setConcepto(rs.getString("CONCEPTO"));
				//obj.setSubConcepto(rs.getString("SUBCONCEPTO"));
				obj.setTotalDeuda(rs.getBigDecimal("TOTAL_DEUDA"));
				obj.setTotalCancelado(rs.getBigDecimal("TOTAL_CANCELADO"));
				obj.setAnioDeuda(rs.getInt("ANNO_DEUDA"));
				obj.setPersonaId(rs.getInt("PERSONA_ID"));
				obj.setTiempoId(rs.getInt("TIEMPO_ID"));
				obj.setFecha(rs.getTimestamp("FECHA"));
				obj.setAnio(rs.getInt("ANIO"));
				obj.setMontoPago(rs.getBigDecimal("PAGO_SOLES").setScale(4,BigDecimal.ROUND_UP));
				obj.setMilesSoles(rs.getBigDecimal("MILES_SOLES").setScale(4,BigDecimal.ROUND_UP));
				obj.setMillonesSoles(rs.getBigDecimal("MILLONES_SOLES").setScale(6,BigDecimal.ROUND_UP));
				obj.setPorcentajeDeuda(rs.getBigDecimal("PORCENTAJE").setScale(4,BigDecimal.ROUND_UP));
				obj.setRecaudacionAnio(rs.getBigDecimal("RECAUDACION_ANIO"));
				obj.setRecaudacionAnioDeuda(rs.getBigDecimal("RECAUDACION_ANIO_DEUDA"));
				obj.setRecaConceptoPorcen(rs.getBigDecimal("PORC_RECA_CONCEPTO"));
				Listareca.add(obj);
			}
			
		} catch (Exception e) {
			System.out.println("ERROR: "+e);
			e.printStackTrace();
		}
		
		return Listareca;
		
	}
	
	
	public ArrayList<ReporDeudaDTO> getDeudas(int unidades, int anioFin,  int diaInicio, int diaFin, int mesInicio, int mesFin){
		ArrayList<ReporDeudaDTO> lista= new ArrayList<ReporDeudaDTO>();
		
		try {
			StringBuilder SQL= new StringBuilder();
			
			SQL.append("select FD.ANNO_DEUDA,DC.DESCRIPCION CONCEPTO_DESC,SUM(FD.TOTAL_DEUDA-FD.TOTAL_CANCELADO) TOTAL_DEUDA ");
			if(unidades==4){
			SQL.append(",100-isnull(SUM(FD.TOTAL_CANCELADO)/SUM(FD.TOTAL_DEUDA)*100,0.0) PORCEN_DEUDA ");	
			}
			SQL.append(",DE.DESCRIPCION ESTADO_DEUDA_DESC ");
			
//			SQL.append(",DE.ESTADO_DEUDA_ID,DE.DESCRIPCION ESTADO_DEUDA_DESC ");
//			SQL.append(",FD.NRO_CUOTA,FD.FECHA_EMISION,FD.FECHA_VENCIMIENTO,FD.INSOLUTO ");
//			SQL.append(",ISNULL("+SATParameterFactory.getDBNameScheme()+".fnGN_interesSimple(FD.INSOLUTO+FD.REAJUSTE,null,FD.FECHA_VENCIMIENTO,GETDATE()),'0.00') INTERES_SIMPLE ");
//			SQL.append(",ISNULL("+SATParameterFactory.getDBNameScheme()+".fnGN_interesCapitalizado(FD.INSOLUTO+FD.REAJUSTE,FD.FECHA_INTERES,FD.FECHA_VENCIMIENTO),'0.00') INTERES_CAPITALIZADO ");
//			SQL.append(",ISNULL("+SATParameterFactory.getDBNameScheme()+".fnGN_interesSimple(FD.INSOLUTO+FD.REAJUSTE,null,FD.FECHA_VENCIMIENTO,GETDATE()),'0.00') ");
//			SQL.append("+ISNULL("+SATParameterFactory.getDBNameScheme()+".fnGN_interesCapitalizado(FD.INSOLUTO+FD.REAJUSTE,FD.FECHA_INTERES,FD.FECHA_VENCIMIENTO),'0.00') ");
//			SQL.append("+FD.INSOLUTO-FD.INSOLUTO_CANCELADO+FD.REAJUSTE-FD.REAJUSTE_CANCELADO TOTAL_DEUDA_ACTUAL,FD.FLAG_CC ");
			SQL.append(",case ");
			SQL.append("when fd.flag_cc > 2 then 'CON VALOR EMITIDO' ");
			SQL.append("else 'SIN VALOR EMITIDO' ");
			SQL.append("end CONCEPTO_VALOR ");
			SQL.append("from "+SATParameterFactory.getDBDWNameScheme()+".FAC_DEUDA FD ");
//			SQL.append("INNER JOIN "+SATParameterFactory.getDBDWNameScheme()+".DIM_TIEMPO DT ON DT.TIEMPO_ID=FD.TIEMPO_ID ");
			SQL.append("INNER JOIN "+SATParameterFactory.getDBDWNameScheme()+".DIM_CONCEPTO DC ON DC.CONCEPTO_ID=FD.CONCEPTO_ID ");
			SQL.append("INNER JOIN "+SATParameterFactory.getDBDWNameScheme()+".DIM_ESTADO_DEUDA DE ON DE.ESTADO_DEUDA_ID=FD.ESTADO_DEUDA_ID ");
//			SQL.append("INNER JOIN "+SATParameterFactory.getDBDWNameScheme()+".DIM_PERSONA DP ON DP.PERSONA_ID=FD.PERSONA_ID ");
			
			SQL.append(" where fd.anno_deuda>0 and fd.anno_deuda<= year(getdate()) ");
			
			if(unidades==4){
				SQL.append("AND FD.TOTAL_DEUDA>0 ");
			}
			
//			if(anioInicio>0 && anioFin==0){
//				SQL.append(" and convert(int,FD.ANNO_DEUDA)>="+anioInicio);	
//			}
//			else if(anioInicio==0 && anioFin>0){
//				SQL.append(" and convert(int,FD.ANNO_DEUDA)<= "+anioFin);	
//			}
//			else if(anioInicio>0 && anioFin>0){
//				SQL.append(" and convert(int,FD.ANNO_DEUDA)>="+anioInicio);	
//				SQL.append(" and convert(int,FD.ANNO_DEUDA)<= "+anioFin);
//			}

			SQL.append("group by FD.ANNO_DEUDA,DC.DESCRIPCION,DE.DESCRIPCION,FD.FLAG_CC  ");
			SQL.append(" ORDER BY fd.anno_deuda asc ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				ReporDeudaDTO obj= new ReporDeudaDTO();
				obj.setAnioDeuda(rs.getInt("ANNO_DEUDA"));
				obj.setConcepto(rs.getString("CONCEPTO_DESC"));
				obj.setEstadoDeudaDesc(rs.getString("ESTADO_DEUDA_DESC"));
				//obj.setFechaEmision(rs.getTimestamp("FECHA_EMISION"));
				//obj.setFechaVencimiento(rs.getTimestamp("FECHA_VENCIMIENTO"));
				//obj.setNroCuota(rs.getInt("NRO_CUOTA"));
				obj.setMontoDeuda(rs.getBigDecimal("TOTAL_DEUDA").setScale(4,BigDecimal.ROUND_UP));
				//obj.setInsoluto(rs.getBigDecimal("INSOLUTO"));
				//obj.setInteresSimple(rs.getBigDecimal("INTERES_SIMPLE"));
				//obj.setInteresCapitalizado(rs.getBigDecimal("INTERES_CAPITALIZADO"));
				//obj.setTotalDeudaActual(rs.getBigDecimal("TOTAL_DEUDA_ACTUAL"));
				obj.setConceptoValor(rs.getString("CONCEPTO_VALOR"));
				if(unidades==4){
				obj.setPorcenDeuda(rs.getBigDecimal("PORCEN_DEUDA").setScale(2,BigDecimal.ROUND_UP));	
				}
				
				lista.add(obj);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: "+e);
		}
		return lista;
	}
		
	
	public ArrayList<ConSubConDTO> getConceptoSubconcepto(){
		
			ArrayList<ConSubConDTO> lista= new ArrayList<ConSubConDTO>();
		
		try {
			StringBuilder SQL= new StringBuilder();
			SQL.append("select sc.concepto_id,sc.subconcepto_id,sc.descripcion descSubc,cn.descripcion descCon ");
			SQL.append("from ");
			SQL.append(SATParameterFactory.getDBNameScheme());
			SQL.append("gn_subconcepto sc ");
			SQL.append("inner join ");
			SQL.append(SATParameterFactory.getDBNameScheme());
			SQL.append(".gn_concepto cn on cn.concepto_id=sc.concepto_id ");
			SQL.append("where sc.concepto_id<=4 ");
			SQL.append("and sc.estado=1 ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				ConSubConDTO obj= new ConSubConDTO();
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setSubConceptoId(rs.getInt("subconcepto_id"));
				obj.setDescSubcon(rs.getString("descSubc"));
				obj.setDescCon(rs.getString("descCon"));
				
				lista.add(obj);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: "+e);
		}
		return lista;
	}

	
	public ArrayList<AnalisisCuentaDTO> getAnalisisCuenta(int tipoAna, int tipoConSub, Date FechaIni, Date FechaFin){
		
		ArrayList<AnalisisCuentaDTO> lista= new ArrayList<AnalisisCuentaDTO>();
		
		try {
			
			StringBuilder SQL = new StringBuilder();
			SQL.append("select deu.subconcepto_id,sub.descripcion,deu.anno_deuda ");
			SQL.append(",(select isnull(sum(deu2.insoluto)-sum(deu2.insoluto_cancelado),0.00) ");
			SQL.append("from dt_determinacion det2 ");
			SQL.append("inner join  cd_deuda deu2 on deu2.determinacion_id=det2.determinacion_id ");
			SQL.append("where deu2.fecha_emision<='12-30-2010' ");
			SQL.append("and deu2.anno_deuda=deu.anno_deuda ");
			SQL.append("and deu2.subconcepto_id=deu.subconcepto_id ");
			SQL.append(") saldoInicial ");
			SQL.append(",sum(deu.insoluto) insoluto ");
			SQL.append(",sum(deu.insoluto_cancelado) insolutoCancelado ");
			SQL.append(",sum(isnull(cpa.insoluto,0.00))pagos ");
			SQL.append(",(sum(deu.insoluto)-sum(deu.insoluto_cancelado)) saldoFinal ");
			SQL.append("from dt_determinacion det ");
			SQL.append("inner join  cd_deuda deu on deu.determinacion_id=det.determinacion_id ");
			SQL.append("left join cj_pago cpa on cpa.deuda_id=deu.deuda_id ");
			SQL.append("inner join gn_subconcepto sub on sub.subconcepto_id=deu.subconcepto_id ");
			SQL.append("where deu.fecha_emision<='01-01-2012' ");
			SQL.append("and	deu.subconcepto_id=40 ");
			SQL.append("group by sub.descripcion,deu.anno_deuda,deu.subconcepto_id ");
			SQL.append("order by deu.anno_deuda ");
			
			PreparedStatement pst= connect().prepareStatement(SQL.toString());
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				AnalisisCuentaDTO obj= new AnalisisCuentaDTO();
				obj.setImpuesto(rs.getString("descripcion"));
				obj.setAnio(rs.getInt("anno_deuda"));
				obj.setSaldoInicial(rs.getBigDecimal("saldoInicial"));
				//obj.setGenerado(rs.getBigDecimal("generado"));
				obj.setPagos(rs.getBigDecimal("pagos"));
				obj.setSaldoFinal(rs.getBigDecimal("saldoFinal"));
				
				lista.add(obj);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return lista;
	}
	
}
