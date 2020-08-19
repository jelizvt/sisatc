package com.sat.sisat.controlycobranza.dto;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.sat.sisat.persistence.entity.CcRec;

public class CcRecAcumulada  extends CcRec implements TreeNode {  
	
//	private Integer personaId;
	
	private Integer cantidad;
	
	private String descripcion;
	
	private Boolean seleccionado;
	
	private Long contenId;
	
	private String tipoRec;
	
	private List<CcRecAcumulada> listRec;	
	 
	public CcRecAcumulada(){
		this.setType("rec");
	}
	public TreeNode getChildAt(int childIndex) {
        return null;
    }
 
    public int getChildCount() {
        return 0;
    }
 
    public TreeNode getParent() {
        return null;
    }
 
    public int getIndex(TreeNode node) {
        return 0;
    }
 
    public boolean getAllowsChildren() {
        return false;
    }
 
    public boolean isLeaf() {
        return true;
    }
 
    public Enumeration<TreeNode> children() {
        return new Enumeration<TreeNode>() {
            public boolean hasMoreElements() {
                return false;
            }
 
            public TreeNode nextElement() {
                return null;
            }
        };
    }
	/*public CcRecAcumulada(){
		super();
	}*/
/*	public Integer getPersonaId() {
		return personaId;
	}


	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
*/

	public Integer getCantidad() {
		return cantidad;
	}


	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Boolean getSeleccionado() {
		return seleccionado;
	}


	public void setSeleccionado(Boolean seleccionado) {
		this.seleccionado = seleccionado;
	}


	public Long getContenId() {
		return contenId;
	}


	public void setContenId(Long contenId) {
		this.contenId = contenId;
	}

	public List<CcRecAcumulada> getListRec() {
		return listRec;
	}

	public void setListRec(List<CcRecAcumulada> listRec) {
		this.listRec = listRec;
	}

	public String getTipoRec() {
		return tipoRec;
	}

	public void setTipoRec(String tipoRec) {
		this.tipoRec = tipoRec;
	}




}
