package com.sat.sisat.common.util;

import javax.faces.context.FacesContext;

public final class SATWEBParameterFactory {

	private static String pathReporte = null;
	
	private static String pathReporteCuponera = null;

	private static String pathReporteImagenes = null;

	private static volatile boolean loadedPaths = false;

	private static void loadPaths() {
		if (loadedPaths) {
			return;
		}

		String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		pathReporte = path_context + "/sisat/reportes/";
		pathReporteCuponera = path_context + "/sisat/reporte_cuponera/";
		pathReporteImagenes = path_context + "/sisat/reportes/imagen/";

		loadedPaths = true;
	}

	public static String getPathReporte() {    
		if (!loadedPaths) {
			loadPaths();
		}

		
		return pathReporte;
	}
	
	public static String getPathReporteCuponera() {    
		if (!loadedPaths) {
			loadPaths();
		}
		return pathReporteCuponera;
	}

	public static String getPathReporteImagenes() {
		if (!loadedPaths) {
			loadPaths();
		}
		return pathReporteImagenes;
	}

}
