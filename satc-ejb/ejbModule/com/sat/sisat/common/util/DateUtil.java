package com.sat.sisat.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class DateUtil {

	
	public static int obtenerAnioSegunFecha(Date fecha){
		int anio;
		Calendar calen = Calendar.getInstance();
		calen.setTime(fecha);
		//calen.add(Calendar.MONTH, 1);le suma un mes a la fecha - error para mes 12
		anio = calen.get(Calendar.YEAR);
		return anio;
	}
	
	/**
	 * Obtiene el año del sistema
	 * 
	 * @return
	 */
	public static String obtenerAnioActual() {
		Calendar cal = GregorianCalendar.getInstance();
		int anio = cal.get(Calendar.YEAR);
		return anio + "";
	}

	private static final java.sql.Timestamp DateToSqlTimestamp(java.util.Date utilDate) {
		return new java.sql.Timestamp(utilDate.getTime());
	}
	/**
	 * Obtiene la fecha actual en formato sql
	 * 
	 * @return java.sql.Date
	 */
	public static java.sql.Date getCurrentDateOnly() {
		Calendar cal = GregorianCalendar.getInstance();
		return new java.sql.Date(cal.getTimeInMillis());
	}

	/**
	 * Obtiene la fecha actual en formato sql, considerando horas, minutos y segundos.
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getCurrentDate() {
		Calendar cal = GregorianCalendar.getInstance();
		return new Timestamp(cal.getTimeInMillis());
	}

	public static Integer getAnioActual() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	public static Integer getAnhoFecha(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		return calendar.get(Calendar.YEAR);
	}

	public static Integer getMesActual() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static boolean esFechaValida(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.AM_PM, Calendar.AM);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Calendar fec = Calendar.getInstance();
		fec.setTime(fecha);
		fec.set(Calendar.AM_PM, Calendar.AM);
		fec.set(Calendar.HOUR, 0);
		fec.set(Calendar.MINUTE, 0);
		fec.set(Calendar.SECOND, 0);
		fec.set(Calendar.MILLISECOND, 0);

		if (!fec.after(cal))
			return true;
		return false;
	}

	private static Pattern emailPattern = Pattern
			.compile("\\w[\\w\\._]*\\w@[\\w_]+\\.(\\w{2,3})(\\.\\w{2,3})?");

	public static boolean isEmail(String value) {
		return emailPattern.matcher(value).matches();
	}

	public static String convertDateToString(Date fecha) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"dd/MM/yyyy");
		String str = sdf.format(fecha);
		return str;
	}

	public static Date convertStringToDate(String fecha) {
		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = (Date) formatter.parse(fecha);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static final java.sql.Date dateToSqlDate(java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}

	public static final java.sql.Time dateToSqlTime(java.util.Date utilDate) {
		return new java.sql.Time(utilDate.getTime());
	}

	public static final java.sql.Timestamp dateToSqlTimestamp(
			java.util.Date utilDate) {
		if(utilDate!=null)
			return new java.sql.Timestamp(utilDate.getTime());
		else
			return null;
	}
	
	public static final String getHoraActual(){
		Calendar calendario = Calendar.getInstance();
		Integer hora =calendario.get(Calendar.HOUR);
		Integer minutos = calendario.get(Calendar.MINUTE);
		int ampm = calendario.get(Calendar.AM_PM);
		return String.valueOf(hora)+":"+String.valueOf(minutos) +" "+(ampm==Calendar.AM?"am":"pm");
	}
	
	public static int diferenciaFechas(Calendar fechaInicio, Calendar fechaFin, int valor) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		int retorno = 0;
		java.util.Date date1 = null;
		java.util.Date date2 = null;
		try {
			
			Calendar cal1 = null;
			date1 = fechaInicio.getTime();
			cal1 = Calendar.getInstance();

			Calendar cal2 = null;
			date2 = fechaFin.getTime();
			cal2 = Calendar.getInstance();
			
			// different date might have different offset
			cal1.setTime(date1);
			long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET)
					+ cal1.get(Calendar.DST_OFFSET);

			cal2.setTime(date2);
			long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET)
					+ cal2.get(Calendar.DST_OFFSET);

			// Use integer calculation, truncate the decimals
			int hr1 = (int) (ldate1 / 3600000); // 60*60*1000
			int hr2 = (int) (ldate2 / 3600000);

			int days1 = (int) hr1 / 24;
			int days2 = (int) hr2 / 24;

			int dateDiff = days2 - days1;
			int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
			int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH)- cal1.get(Calendar.MONTH);

			if (valor == 1) {
				retorno = dateDiff;
			} else if (valor == 2) {
				retorno = monthDiff;
			} else if (valor == 3) {
				retorno = yearDiff;
			}
		} catch (Exception pe) {
			pe.printStackTrace();
		}
		return retorno;
	}
	
	public static int diferenciasDeFechas(Date fechaInicial, Date fechaFinal) {

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String fechaInicioString = df.format(fechaInicial);
        try {
            fechaInicial = df.parse(fechaInicioString);
        } catch (Exception ex) {
        }

        String fechaFinalString = df.format(fechaFinal);
        try {
            fechaFinal = df.parse(fechaFinalString);
        } catch (Exception ex) {
        }

        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaFinal.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ((int) dias);
    }
	
	/**
	 * Método que mueve las horas al final del dia, usado para la obtencion de datos en base a rango de horas
	 * @param fecha
	 * @return
	 */
	public static Date moverHoraAlFinalDelDia(Date fecha) {
		Calendar cal = null;
		try{
			cal = Calendar.getInstance();
			cal.setTime(fecha);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
		}catch (Exception e) {
			return null;
		}
		
		return cal.getTime();
	}
	
	/**
	 * Método que mueve las horas al final del dia, usado para la obtencion de datos en base a rango de horas
	 * @param fecha
	 * @return
	 */
	public static Date moverHoraAlInicioDelDia(Date fecha) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);

		return cal.getTime();
	}
	
	public static String getTimestampToString(Timestamp date){
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-5"));
		return sdf.format(date);
	}
}
