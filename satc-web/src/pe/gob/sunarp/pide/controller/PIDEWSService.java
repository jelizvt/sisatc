package pe.gob.sunarp.pide.controller;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;

/**
 * This class was generated by Apache CXF 2.4.6
 * 2018-07-02T11:17:50.482-05:00
 * Generated source version: 2.4.6
 * 
 */
@WebService(targetNamespace = "http://controller.pide.sunarp.gob.pe/", name = "PIDEWSService")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PIDEWSService {

    @WebResult(name = "oficina", targetNamespace = "http://controller.pide.sunarp.gob.pe/", partName = "oficina", header = true)
    @Action(input = "http://controller.pide.sunarp.gob.pe/PIDEWSService/getOficinasRequest", output = "http://controller.pide.sunarp.gob.pe/PIDEWSService/getOficinasResponse")
    @WebMethod(action = "getOficinas")
    public RespuestaOficinaRegistralBean getOficinas();

    @WebResult(name = "vehiculo", targetNamespace = "http://controller.pide.sunarp.gob.pe/", partName = "vehiculo")
    @Action(input = "http://controller.pide.sunarp.gob.pe/PIDEWSService/verDetalleRPVRequest", output = "http://controller.pide.sunarp.gob.pe/PIDEWSService/verDetalleRPVResponse")
    @WebMethod(action = "verDetalleRPV")
    public RespuestaVehiculoBean verDetalleRPV(
        @WebParam(partName = "zona", name = "zona")
        java.lang.String zona,
        @WebParam(partName = "oficina", name = "oficina")
        java.lang.String oficina,
        @WebParam(partName = "placa", name = "placa")
        java.lang.String placa
    );

    @WebResult(name = "personaJuridica", targetNamespace = "http://controller.pide.sunarp.gob.pe/", partName = "personaJuridica")
    @Action(input = "http://controller.pide.sunarp.gob.pe/PIDEWSService/buscarPJRazonSocialRequest", output = "http://controller.pide.sunarp.gob.pe/PIDEWSService/buscarPJRazonSocialResponse")
    @WebMethod(action = "buscarPJRazonSocial")
    public RespuestaBusquedaPJBean buscarPJRazonSocial(
        @WebParam(partName = "razonSocial", name = "razonSocial")
        java.lang.String razonSocial
    );

    @WebResult(name = "img", targetNamespace = "http://controller.pide.sunarp.gob.pe/", partName = "img")
    @Action(input = "http://controller.pide.sunarp.gob.pe/PIDEWSService/verAsientoRequest", output = "http://controller.pide.sunarp.gob.pe/PIDEWSService/verAsientoResponse")
    @WebMethod(action = "verAsiento")
    public byte[] verAsiento(
        @WebParam(partName = "transaccion", name = "transaccion")
        long transaccion,
        @WebParam(partName = "idImg", name = "idImg")
        long idImg,
        @WebParam(partName = "tipo", name = "tipo")
        java.lang.String tipo,
        @WebParam(partName = "nroTotalPag", name = "nroTotalPag")
        java.lang.String nroTotalPag,
        @WebParam(partName = "nroPagRef", name = "nroPagRef")
        java.lang.String nroPagRef,
        @WebParam(partName = "pagina", name = "pagina")
        java.lang.String pagina
    );

    @WebResult(name = "asientos", targetNamespace = "http://controller.pide.sunarp.gob.pe/", partName = "asientos")
    @Action(input = "http://controller.pide.sunarp.gob.pe/PIDEWSService/listarAsientosRequest", output = "http://controller.pide.sunarp.gob.pe/PIDEWSService/listarAsientosResponse")
    @WebMethod(action = "listarAsientos")
    public RespuestaPartidaBean listarAsientos(
        @WebParam(partName = "zona", name = "zona")
        java.lang.String zona,
        @WebParam(partName = "oficina", name = "oficina")
        java.lang.String oficina,
        @WebParam(partName = "partida", name = "partida")
        java.lang.String partida,
        @WebParam(partName = "registro", name = "registro")
        java.lang.String registro
    );

    @WebResult(name = "respuestaNaveAeronave", targetNamespace = "http://controller.pide.sunarp.gob.pe/", partName = "respuestaNaveAeronave")
    @Action(input = "http://controller.pide.sunarp.gob.pe/PIDEWSService/buscarNaveAeronaveRequest", output = "http://controller.pide.sunarp.gob.pe/PIDEWSService/buscarNaveAeronaveResponse")
    @WebMethod(action = "buscarNaveAeronave")
    public RespuestaNaveAeronave buscarNaveAeronave(
        @WebParam(partName = "numeroMatricula", name = "numeroMatricula")
        java.lang.String numeroMatricula
    );

    @WebResult(name = "respuestaTitularidad", targetNamespace = "http://controller.pide.sunarp.gob.pe/", partName = "respuestaTitularidad")
    @Action(input = "http://controller.pide.sunarp.gob.pe/PIDEWSService/buscarTitularidadRequest", output = "http://controller.pide.sunarp.gob.pe/PIDEWSService/buscarTitularidadResponse")
    @WebMethod(action = "buscarTitularidad")
    public RespuestaTitularidad buscarTitularidad(
        @WebParam(partName = "tipoParticipante", name = "tipoParticipante")
        java.lang.String tipoParticipante,
        @WebParam(partName = "apellidoPaterno", name = "apellidoPaterno")
        java.lang.String apellidoPaterno,
        @WebParam(partName = "apellidoMaterno", name = "apellidoMaterno")
        java.lang.String apellidoMaterno,
        @WebParam(partName = "nombres", name = "nombres")
        java.lang.String nombres,
        @WebParam(partName = "razonSocial", name = "razonSocial")
        java.lang.String razonSocial
    );
}
