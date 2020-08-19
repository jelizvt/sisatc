package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReciboPagoDetallePieDTO implements Serializable{

	private static final long serialVersionUID = -1944982044719183563L;

	private int determinacionId;
	private int conceptoId;
	private String subconceptosId;
	private String referenciaLabel;
	private String referenciaId;
	private String referencia;
	private List<ReciboPagoDetalleDTO> listReciboPagoDetalle = new ArrayList<ReciboPagoDetalleDTO>();

	public ReciboPagoDetallePieDTO(){
	}

	public ReciboPagoDetallePieDTO(int conceptoId, String subconceptosId,
			String referenciaLabel, String referenciaId, String referencia,
			List<ReciboPagoDetalleDTO> listReciboPagoDetalle) {
		super();
		this.conceptoId = conceptoId;
		this.subconceptosId = subconceptosId;
		this.referenciaLabel = referenciaLabel;
		this.referenciaId = referenciaId;
		this.referencia = referencia;
		this.listReciboPagoDetalle = listReciboPagoDetalle;
	}

	public ReciboPagoDetallePieDTO(int determinacionId,
			int conceptoId,
			String subconceptosId,
			String referenciaLabel,
			String referenciaId,
			String referencia,
			List<ReciboPagoDetalleDTO> listReciboPagoDetalle) {
		super();
		this.determinacionId = determinacionId;
		this.conceptoId = conceptoId;
		this.subconceptosId = subconceptosId;
		this.referenciaLabel = referenciaLabel;
		this.referenciaId = referenciaId;
		this.referencia = referencia;
		this.listReciboPagoDetalle = listReciboPagoDetalle;
	}

	public int getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(int conceptoId) {
		this.conceptoId = conceptoId;
	}

	public String getSubconceptosId() {
		return subconceptosId;
	}

	public void setSubconceptosId(String subconceptosId) {
		this.subconceptosId = subconceptosId;
	}

	public String getReferenciaLabel() {
		return referenciaLabel;
	}

	public void setReferenciaLabel(String referenciaLabel) {
		this.referenciaLabel = referenciaLabel;
	}

	public String getReferenciaId() {
		return referenciaId;
	}

	public void setReferenciaId(String referenciaId) {
		this.referenciaId = referenciaId;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public List<ReciboPagoDetalleDTO> getListReciboPagoDetalle() {
		return listReciboPagoDetalle;
	}

	public void setListReciboPagoDetalle(
			List<ReciboPagoDetalleDTO> listReciboPagoDetalle) {
		this.listReciboPagoDetalle = listReciboPagoDetalle;
	}

	public int getDeterminacionId() {
		return determinacionId;
	}

	public void setDeterminacionId(int determinacionId) {
		this.determinacionId = determinacionId;
	}
}