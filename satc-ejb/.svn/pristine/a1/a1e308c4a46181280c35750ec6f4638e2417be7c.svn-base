package com.sat.sisat.common.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import com.sat.sisat.administracion.parameter.ParameterLoader;

public class Util {

	private static Pattern emailPattern = Pattern
			.compile("\\w[\\w\\._]*\\w@[\\w_]+\\.(\\w{2,3})(\\.\\w{2,3})?");

	public static boolean isEmail(String value) {
		return emailPattern.matcher(value).matches();
	}
	
	public static Integer toInteger(String value){
		Integer result=0;
		String valor=value;
		try{
			result=Integer.valueOf(valor);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static Long toLong(String value){
		Long result=Long.parseLong("0"); 
		try{
			result=Long.valueOf(value);
		}catch(Exception e){
			;
		}
		return result;
	}

	public static BigDecimal toBigDecimal(String value){
		BigDecimal  result=new BigDecimal(0); 
		try{
			result=BigDecimal.valueOf(Double.valueOf(value));
		}catch(Exception e){
			;
		}
		return result;
	}
	public static Double toDouble(String value){
		Double  result=new Double(0); 
		try{
			result=Double.valueOf(Double.valueOf(value));
		}catch(Exception e){
			;
		}
		return result;
	}
    public static String getRutaImagen() {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            return ParameterLoader.getParameter("rutaimagen");
        } else if (os.contains("Linux")) {
            return "/usr/share/sisat/report/image/";
        } else {
            return "";
        }
    }
    public static String getRutaReportesGenerales() {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            return ParameterLoader.getParameter("rutareportes");
        } else if (os.contains("Linux")) {
            return "/usr/share/sisat/report/";
        } else {
            return "";
        }
    }
    
    
    /**
     * Método utilitario para el parseo de una cadena de texto a una cadena usada en la consulta de texto indexado
     * metodo necesario para la transformacion del string que recibe el store procedure [stp_cj_buscar_recibo_por_referencia]
     * o cualquier store procedure que use consultas indexadas basadas en texto
     * */
	public static String parseToContainsSentence(String cadenaTexto) {

		cadenaTexto = cadenaTexto.trim();

		String[] cadena = cadenaTexto.split("\\s+");

		StringBuffer sb = new StringBuffer();

		sb.append("\"");
		sb.append(cadena[0]);
		sb.append("\"");
		

		for (int i = 1; i < cadena.length; i++) {
			sb.append(" AND ");
			sb.append("\"");
			sb.append(cadena[i]);
			sb.append("\"");
		}

		return sb.toString();
	}
	

	/**
	 * fox
	 * @param str
	 * @return
	 */
	public static boolean isNull(Integer str) {
        return str == null ? true : false;
    }
	public static boolean isNull(String str) {
        return str == null ? true : false;
    }

    public static boolean isEmpty(String param) {
        if (isNull(param) || param.trim().length() == 0) {
            return true;
        }
        return false;
    }
    public static String getString(String param){
    	if(isEmpty(param)){
    		return "";
    	}
    	return param.trim();
    }
    
    public static String nvl(String value,String replace) {
        return value != null ? value : replace;
    }
    
    public static Integer nvl(Integer value,Integer replace) {
        return value != null ? value : replace;
    }
    
    public static Double nvl(Double value,Double replace) {
        return value != null ? value : replace;
    }
}
