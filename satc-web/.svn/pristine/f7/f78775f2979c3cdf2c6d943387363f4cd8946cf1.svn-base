package com.sat.sisat.tramitedocumentario.managed;

import java.io.Serializable;

import com.sat.sisat.common.util.FileUpload;

public class AnexoDTO implements Serializable {
	
	private static final long serialVersionUID = -3601869208611097350L;
	
	private String documento;
	private String descripcion;	
	private FileUpload file;
	
	public enum TipoEstadoAnexo{EnEdicion(0), Finalizado(1), SoloLectura(2);
		private int value;
	
		private TipoEstadoAnexo(int value){
			this.value = value;
		}

		public int getValue() {
			return value;
		}	
	};
	
	/** Define el estado del anexo en la grilla
	 * 0 = EnEdicion
	 * 1 = Finalizado	 
	 * 2 = SoloLectura
	 * */
	private TipoEstadoAnexo estado = TipoEstadoAnexo.EnEdicion;
	
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public FileUpload getFile() {
		return file;
	}

	public void setFile(FileUpload file) {
		this.file = file;
	}

	public TipoEstadoAnexo getEstado() {
		return estado;
	}

	public void setEstado(TipoEstadoAnexo estado) {
		this.estado = estado;
	}

	

}
