
package com.sat.sisat.common.util;

import java.util.ArrayList;
import java.util.List;


public class Constante {

	// cchaucca:ini
	public static String RECORD_STATUS_NEW = "0";
	public static String RECORD_STATUS_UPDATE = "1";
	public static String RECORD_STATUS_CLONE = "2";
	public static String RECORD_STATUS_VIEW = "3";
	public static String RECORD_STATUS_NEW_FISCA = "4";
	public static String RECORD_STATUS_CLONE_FISCA = "5";
	public static String RECORD_STATUS_UPDATE_FISCA = "6";
	public static String RECORD_STATUS_CLONE_FISCA_ACEPTADA = "7";
	// cchaucca:fin
	
	// INACTIVO 0
	// ACTIVO 1
	// PENDIENTE 2
	// PENDIENTE 3
	// ..... 4..8
	// ELIMINADO 9
	
	public static String ESTADO_INACTIVO = "0";
	public static String ESTADO_ACTIVO = "1"; // definitivo registro DJ
	public static String ESTADO_PENDIENTE = "2";// pendiente de APROBACION POR USUARIO
	public static String ESTADO_PENDIENTE_DESCARGO = "3";
	public static String ESTADO_PENDIENTE_ACTUALIZACION = "4";
	
	public static String ESTADO_TRANSFERENCIA = "4";
	public static String ESTADO_RECTIFICADO = "5";
	public static String ESTADO_DESCARGADO = "6";
	public static String ESTADO_PRESCRITO = "7";
	public static String ESTADO_COMPESADO = "8";
	public static String ESTADO_ELIMINADO = "9";// ELIMINADO

	public static String FLAG_DJ_ANIO_ACTIVO = "1"; // definitivo registro DJ
	public static String FLAG_DJ_ANIO_INACTIVO = "0"; // definitivo registro DJ

	public static String FLAG_ACTIVO = "1";
	public static String FLAG_INACTIVO = "0";

	public static String TIPO_PREDIO_URBANO = "U";
	public static String TIPO_PREDIO_RUSTICO = "R";

	public static Integer TIPO_MENSAJE_ERROR = 1;
	public static Integer TIPO_MENSAJE_ADVERTENCIA = 2;
	public static Integer TIPO_MENSAJE_MENSAJE = 3;

	public static String DEPARTAMENTO = "CAJAMARCA";
	public static String PROVINCIA = "CAJAMARCA";
	public static String DISTRITO = "CAJAMARCA";

	public static Integer DEPARTAMENTO_ID_DEFECTO = 6;
	public static Integer PROVINCIA_ID_DEFECTO = 1;
	public static Integer DISTRITO_ID_DEFECTO = 1;

	public static Integer DEPART_PROVIN_DISTR_DEFECTO = 1;
	public static Integer DEPART_PROVIN_DISTR_DISTINTO = 0;

	public static String ESTADO_ACTIVO_DESCRIPCION = "ACTIVO";
	public static String ESTADO_INACTIVO_DESCRIPCION = "INACTIVO";
	public static String ESTADO_ELIMINADO_DESCRIPCION = "ELIMINADO";
	public static String ESTADO_PENDIENTE_DESCRIPCION = "PENDIENTE";

	public static String DIRECCION_FISCAL = "FISCAL";
	
	public static Integer REGISTRAR_DIRECCION_FISCAL = 0;
	
	public static String SITUACION_EMPRESARIAL_DEFECTO = "Ninguno";
	public static String SITUACION_EMPRESARIAL_DEF_PERJURID_PATRIAUTO = "Normal";
	public static String CONDICION_ESPECIAL_DEFECTO = "Ninguno";

	public static Integer CONDICION_ESPECIAL_DEFECTO_ID = 1;
	
	public static Integer SITUACION_EMPRESARIAL_ID_DEFECTO = 6;
	public static Integer SITUACION_EMPRESARIAL_ID_DEFECTO_PERJURID_PATRIAUTO = 1;

	public static String TIPO_PERSONA_JURIDICA = "Persona Juridica";
	public static String TIPO_PERSONA_PATRIMONIO = "Patrimonio Autónomo";

	public static String TIPO_PERSONA_NATURAL = "Persona Natural";
	public static String TIPO_PERSONA_SOC_CONYUGAL = "Sociedad Conyugal";
	public static String TIPO_PERSONA_COPROPIEDAD = "Copropiedad";

	public static Integer TIPO_PERSONA_JURIDICA_ID = 2;
	public static Integer TIPO_PERSONA_NATURAL_ID = 1;
	public static Integer TIPO_PERSONA_SOCIEDADCONYUGAL_ID = 3;
	public static Integer TIPO_PERSONA_PATRIMONIAUTONOMO_ID = 4;
	public static Integer TIPO_PERSONA_COPROPIEDAD_ID = 5;
	public static Integer SUB_TIPO_PERSONA_INDIVIDUAL_ID = 1;
	public static Integer SUB_TIPO_PERSONA_SUC_INDIVISA_ID = 5;
	public static Integer SUB_TIPO_PERSONA_COPROPIETARIO_ID = 8;
	
	public static String SUB_TIPO_PERSONA_SUC_INDIVISA = "Sucesión Indivisa";
	public static String SUB_TIPO_PERSONA_SOCIEDAD_IRREGULAR = "Sociedad Irregular";
	public static String SUB_TIPO_PERSONA_SOCIEDAD_REGULAR = "Sociedad Regular";
	public static String SUB_TIPO_PERSONA_PERSONA_INDIVIDUAL = "Persona Individual";
	public static String SUB_TIPO_PERSONA_MENOR_EDAD = "Menor de Edad";
	public static String SUB_TIPO_PERSONA_COPROPIETARIO = "Copropietario";
	
	public static Integer SUB_TIPO_PERSONA_MENOR_EDAD_ID = 2;
	
	public static Integer TIPO_ACCION_NUEVO = 1;
	public static Integer TIPO_ACCION_EDITAR = 2;
	public static Integer TIPO_ACCION_VISTA_PREVIA=3;
	public static Integer TIPO_ACCION_VER=4;
	
	public static Boolean GENERADO_DJ = Boolean.TRUE;
	public static Boolean NO_GENERADO_DJ = Boolean.FALSE;

	public static String TIPO_RELACIONADO_CONYUGE = "Conyuge";
	public static Integer TIPO_RELACIONADO_CONYUGE_ID = 3;

	public static Integer TIPO_RELACIONADO_REPRESENTANTE_LEGAL_ID = 1;
	public static Integer TIPO_RELACIONADO_ADMINISTRADOR_ID = 2;
	//public static String TIPO_RELACIONADO_PADREOTUTOR = "Padre o Tutor";
	public static Integer TIPO_RELACIONADO_PADREOTUTOR_ID = 4;
	
	public static Integer TIPO_CONDICION_ESPECIAL_PENSIONISTA_ID=12;
	public static int SUB_CONCEPTO_PREDIAL = 10;
	public static int SUB_CONCEPTO_PAPELETA = 40;
	public static int SUB_CONCEPTO_PAPELETA_SOAT = 41;
	public static int SUB_CONCEPTO_VEHICULAR = 20;
	public static int SUB_CONCEPTO_ARBITRIOS_BARRIDO = 30;
	public static int SUB_CONCEPTO_ARBITRIOS_RECOJO = 31;
	public static int SUB_CONCEPTO_ARBITRIOS_LIMPIEZA_PUBLICA = 39;
	public static int SUB_CONCEPTO_ARBITRIOS_PARQUES = 32;
	public static int SUB_CONCEPTO_ARBITRIOS_SEGURIDAD = 33;

	public static String TIPO_DOCUMENTO_DNI = "DNI";
	public static int CANTIDAD_DIGITOS_DNI = 8;
	public static String TIPO_DOCUMENTO_RUC = "RUC";
	public static int CANTIDAD_DIGITOS_RUC = 11;
	public static int TIPO_DOCUMENTO_DNI_ID = 1;
	public static int TIPO_DOCUMENTO_RUC_ID = 2;
	
	public static String[] LISTA_DNI_INCORRECTOS = {"00000000"};
	public static String[] LISTA_RUC_INCORRECTOS = {"10000000000"};
	public static int LISTA_DNI_INCORRECTOS_LENGHT=1;
	public static int LISTA_RUC_INCORRECTOS_LENGHT=1;
	
	public static int FORMA_PAGO_EFECTIVO = 1;
	public static int FORMA_PAGO_TARCRED = 2;
	public static int FORMA_PAGO_TARDEB = 3;
	public static int FORMA_PAGO_CHEQUE = 4;
	public static int FORMA_PAGO_BONO_CAJAM = 5;
	public static int TIPO_MONEDA = 1;
	public static int NOMBRE_AGENCIA=1;

	//Motivo de declaracion predial 
	public static Integer MOTIVO_DECLARACION_PRIMERA_INSCRIPCION=1;
	public static Integer MOTIVO_DECLARACION_INSCRIPCION=2;
	public static Integer MOTIVO_DECLARACION_ACTUALIZA=3;
	public static Integer MOTIVO_DECLARACION_DESCARGO=4;
	public static Integer MOTIVO_DECLARACION_MIGRACION=7;
	public static Integer MOTIVO_DECLARACION_MASIVA=10;//79;//Generacion masiva de determinacion 
	
	//Condicion de propiedad
	public static Integer CONDICION_PROPIEDAD_PROPIETARIO_UNICO=1;
	public static Integer CONDICION_PROPIEDAD_CONDOMINO_SIMPLE=2;
	
	public static int CONCEPTO_PREDIAL = 1;
	public static int CONCEPTO_VEHICULAR = 2;
	public static int CONCEPTO_PAPELETA = 4;
	public static int CONCEPTO_ARBITRIO = 3;
	public static int CONCEPTO_ALCABALA = 7;
	public static int CONCEPTO_MULTAS = 12;
	
	public static int SUBCONCEPTO_PREDIAL = 10;
	public static int SUBCONCEPTO_VEHICULAR = 20;
	public static int SUBCONCEPTO_ALCABALA = 70;
	
	public static String TIPO_TRANSFERENCIA_ADQUIRIENTE = "A";
	public static String TIPO_TRANSFERENCIA_TRANSFERENTE = "T";
	
	//Estado de papeletas inicio
	public static String ESTADO_PAPELETA_PENDIENTE = "0";//En Verificacion // PENDIENTE DE VERIFICACION PROVENIENTES DE LOS MASIVOS
	public static String ESTADO_PAPELETA_REGISTRADO = "1"; //Registrado
	public static String ESTADO_PAPELETA_NO_COINCIDE = "2";//No Coincide
	public static String ESTADO_PAPELETA_DEFINITIVO = "3";//Definitivo //Pendiente de Pago
	public static String ESTADO_PAPELETA_DEFINITIVO_CANCELADO = "4";//Definitivo //Papeleta Cancelada

	public static String ESTADO_PAPELETA_CANCELADO = "4";//Pagado
	public static String ESTADO_PAPELETA_DESCARGADO = "5";//Pagado
	public static String ESTADO_PAPELETA_ANULADO = "6";//Pagado
	public static String ESTADO_PAPELETA_COMPENSADO = "7";//Pagado
	public static String ESTADO_PAPELETA_PRESCRITO = "8";//Pagado
	
	public static String ESTADO_PAPELETA_ELIMINADA="9";	//Eliminacion Logica
	//Estado de papeletas fin
	
	public static String ESTADO_ELIMINADO_ADJUNTO = "E";
	public static String ESTADO_TEMPORAL_ADJUNTO = "T";
	public static String ESTADO_DEFINITIVO_ADJUNTO = "D";
	
	public static int  REGISTRO_PAPELETA_ACTIVO = 1;
	public static int REGISTRO_PAPELETA_INACTIVO = 0;
	
	public static int ID_TABLA_CARGA = 21;
	public static String NOMBRE_CLAVE_DELIMITADOR = "delimitador";
	public static String NOMBRE_CLAVE_EXTENSION = "extension";
	public static String NOMBRE_CLAVE_DIRECTORIO = "directorio";

	public static String ORIGEN_PAPELETA_MASIVO = "M";//DESDE SCRIPT DE MIGRACION MASIVA
	public static String ORIGEN_PAPELETA_INDIVIDUAL = "I";
	
	public static String ACCION_NUEVO = "nuevo";
	public static String ACCION_AGREGAR = "agregar";
	public static String ACCION_VERIFICAR = "verificar";
	public static String ACCION_ACTUALIZAR = "actualizar";
	
	//flags RS para papeletas
	public static int FLAG_RS_GENERADA = 1;
	public static int FLAG_RS_NO_GENERADA = 0;
	public static int FLAG_RS_RECLAMO = 1;
	public static int FLAG_RS_NO_RECLAMO = 0;
	
	public static int CUOTA_1 = 1;  
	
	//CONTROL Y COBRANZA
	public static Integer TIPO_ACTO_RECOR_DEU_POR_VENCER_ID=1;
	public static Integer TIPO_ACTO_RECOR_DEUDAS_ID=2;
	public static Integer TIPO_ACTO_RESOLUCION_DETERMINACION_ID=4;
	public static Integer TIPO_ACTO_ORDEN_PAGO_ID=3;
	public static Integer TIPO_ACTO_CONSTANCIA_EXIGIBILIDAD=10;
	public static Integer TIPO_ACTO_RESOLUCION_SANCION_ID=8;
	public static Integer TIPO_ACTO_RESOLUCION_SANCION_NP_ID=9;
	public static Integer TIPO_ACTO_REC_ADMINISTRATIVA = 6;
	public static Integer TIPO_ACTO_REC_TRIBUTARIA= 7;
	
	public static String TIPO_LOTE_ORDINARIO="1";
	public static Integer TIPO_LOTE_INFORMATIVO_ID=1;
	public static Integer TIPO_LOTE_COBRANZA_ID=2;
	public static Integer TIPO_LOTE_COACTIVO_ID=3;
	public static Integer TIPO_LOTE_VEHICULAR_ID=4;
	public static Integer TIPO_COBRANZA_ORDINARIO=1;
	public static Integer TIPO_COBRANZA_COACTIVO=2;
	public static String ESTADO_LOTE_PRELIMINAR="Preliminar";
	public static String ESTADO_LOTE_FINAL="Final";
	public static String ESTADO_LOTE_PRELIMINAR_VALOR="1";
	public static String ESTADO_LOTE_FINAL_VALOR="2";
	public static Integer NUMERO_FIRMAS_REC1=2;
	public static Integer NUMERO_FIRMAS_OP=1;
	
	public static String TIPO_ARCHIVO_JPG=".jpg";	
	public static Integer TIPO_NIVEL_PISO=1;
	public static Integer TIPO_NIVEL_AREA_COMUN=6;
	public static Integer NIVEL_INCREMENTO=5;//a partir del 5to piso se aplica un porcentaje
	//no modificar = 0.05
	public static Double cdblPorcentajeIncremento=0.05;//Si la cantida de niveles es >5 (incremento el 5%)
	
	public static String TIPO_TASA_IMPUESTO_PREDIAL_BASE="3";//MONTO MINIMO
	public static String TIPO_TASA_IMPUESTO_PREDIAL_SIN_RANGO_FIN="2";
	public static String TIPO_TASA_IMPUESTO_PREDIAL_RANGO_INICIO_FIN="1";
	
	public static Integer RESULT_FAILED=-1;
	public static Integer RESULT_PENDING=0;
	public static Integer RESULT_SUCCESS=1;
	
	public static Integer CATEGORIA_COMPONENTE_MUROS=1;
	public static Integer CATEGORIA_COMPONENTE_TECHO=2;
	public static Integer CATEGORIA_COMPONENTE_PISOS=3;
	public static Integer CATEGORIA_COMPONENTE_PUERTAS=4;
	public static Integer CATEGORIA_COMPONENTE_REVESTIMIENTO=5;
	public static Integer CATEGORIA_COMPONENTE_BANNOS=6;
	public static Integer CATEGORIA_COMPONENTE_ELECTRICO=7;
	
	public static String TIPO_LOTE_GENERACION_PRELIMINAR="1";
	public static String TIPO_LOTE_GENERACION_FINAL="2";
	public static String TIPO_PROGRAMACION_FINAL_VALOR="1";
	public static String TIPO_PROGRAMACION_PRELIMINAR_VALOR="2";
	public static String TIPO_AGRUPACION_AGRUPADO="Agrupado";
	public static String TIPO_AGRUPACION_INDIVIDUAL="Individual";
	public static String TIPO_AGRUPACION_AGRUPADO_VALOR="1";
	public static String TIPO_AGRUPACION_INDIVIDUAL_VALOR="2";
	public static String FLAG_GENERACION_PROGRAMADA="1";
	public static String FLAG_GENERACION_FINALIZADA="2";
	public static String TIPO_PROGRAMACION_PRELIMINAR="Preliminar";
	public static String TIPO_PROGRAMACION_FINAL="Final";
	
	public static String schemadb=SATParameterFactory.getDBNameScheme();
	public static String  schemaper=SATParameterFactory.getDBPersonaNameScheme();
	public static String  schemadw=SATParameterFactory.getDBDWNameScheme();
	// Tipos de movimiento de hitorico deuda
	public static int TIPO_MOV_GENERADO = 1;
	public static int TIPO_MOV_RECALCULO = 2;
	public static int TIPO_MOV_PAGO = 3;
	
	public static int CONDICION_ESPECIAL_CONTRIBUYENTE_PENSIONISTA=12;
	public static int USO_PREDIO_CASA_HABITACION= 6;
	public static int USO_PREDIO_CASA_HABITACION_DIPLOMATICA= 7;
	public static int USO_PREDIO_COMERCIO= 10;
	public static int USO_PREDIO_TIENDA_BODEGA= 45;
	
	public static String TIPO_INFECTACION_PORCENTAJE="1";
	public static String TIPO_INFECTACION_PROPORCION_UIT="2";
	
	
	public static String OPERACION_LOTE_PAPELETA_DIGITAR="1";
	public static String OPERACION_LOTE_PAPELETA_VERFICAR="2";
	public static String OPERACION_LOTE_PAPELETA_AGREGAR="3";
	
	public static Integer TIPO_DEUDA_AUTOGENERADO=1;
	public static Integer TIPO_DEUDA_GENERADO_MANUALMENTE=9;
	
	//caltamirano:ini
	public static String FISCALIZADO_SI = "1";
	public static String FISCALIZADO_NO = "0";
	public static String FISCA_ACEPTADA_SI = "1";
	public static String FISCA_ACEPTADA_NO = "0";
	public static String FISCA_CERRADA_SI = "1";
	public static String FISCA_CERRADA_NO = "0";
	public static String FISCA_ACEPTADA_CONTRIB_NO = "NO ACEPTADO POR CONTRIBUYENTE";
	public static String FISCA_ACEPTADA_CONTRIB_SI = "ACEPTADO POR CONTRIBUYENTE";
	public static String FISCA_NOACEPTADA_CERRADA = "NO ACEPTADO POR CONTRIBUYENTE Y CERRAR FISCALIZACION";
	//caltamirano:fin
	
	//cchaucca:ini
	
	//formatos para operar con fechas
		public static String FORMATO_FECHA = "dd/MM/yyyy";
	
	// Caja
	public final static String FORMAS_PAGO_EFECTIVO = "EF";
	public final static String FORMAS_PAGO_VARIAS = "VF";
	public final static String TIPO_MONEDA_SOLES = "Soles";
	
	//Estado de deuda
	
	public static Integer ESTADO_DEUDA_DETERMINADO=1;

	//Motivo de descargo
	public static Integer MOTIVO_DESCARGO_OTROS=99;
	public static Integer MOTIVO_DESCARGO_VENTA=1;
	public static Integer MOTIVO_DESCARGO_FUSION_MIGRACION=98;
	
	
	//Tipo de Acto Orden de Pago, Los tipos de acto superiores a 3 son valores generados
	public static Integer TIPO_ACTO_ORDEN_PAGO=3;
	
	public final static int ROL_SUPERVISOR_ID = 7;
	public final static int ROL_CAJERO_ID = 8;
	public final static int ROL_ADMIN_ID = 9;
	public final static String TIPO_ROL_SUPERVISOR = "S";
	public final static String TIPO_ROL_CAJERO = "C";
	public final static String OPERACION_APERTURA = "A";
	public final static String OPERACION_CIERRE = "C";
	
	public final static int TIPO_TIERRA_RUSTICO_ERIAZA_ID = 4;
	
	public final static int TIERRAS_APTAS_PARA_CULTIVO_EN_LIMPIO_CON_RIEGO = 1;
	public final static int TIERRAS_APTAS_PARA_CULTIVO_PARA_PASTOREO_CON_RIEGO = 2; 
	public final static int TIERRAS_APTAS_PARA_CULTIVO_PERMANENTE_CON_RIEGO = 3;
	
	public final static int TIPO_CUADRE_SISTEMA = 1;
	public final static int TIPO_CUADRE_CAJA = 2;
	public final static int TIPO_CUADRE_SOBRANTE = 3;
	public final static int TIPO_CUADRE_FALTANTE = 4;
	
	public final static int TIPO_USO_COCHERA_DOMICILIO = 9;
	public final static int TIPO_USO_SERVICIO_DE_ESTACIONAMIENTO = 23;
	public final static int TIPO_USO_TERRENO_SIN_CONSTRUIR = 44;
	public final static int TIPO_USO_ALMACEN_DEPOSITO = 4;
	
	public final static int TIPO_USO_COCHERA_DOMICILIO_NUEVO = 124;
	public final static int TIPO_USO_SERVICIO_DE_ESTACIONAMIENTO_NUEVO = 143;
	public final static int TIPO_USO_TERRENO_SIN_CONSTRUIR_NUEVO = 195;
	public final static int TIPO_USO_ALMACEN_DEPOSITO_NUEVO = 107;
	public final static int TIPO_USO_VIVIENDA = 201;
	
	/*arbitrios 2015:INICIO*/
	/**
	 *   Entidades prestados de servicios públicos de agua potable, electricidad y telefonía
		 Servicios Públicos prestados por Gob. Regional, Municipaliad Provincial y distrital
		 Servicios Públicos prestados por ministerios
	/**  --14,39,40,41,42 -- publicas
		 --17,31,32,33,34,47 -- educativas**/

	public final static int TIPO_USO_INSTITUCIONES_PUBLICOS_AGUAELECTEL = 14;
	public final static int TIPO_USO_INSTITUCIONES_PUBLICOS_GOBREGMUNI = 39;
	public final static int TIPO_USO_INSTITUCIONES_PUBLICOS_MINISTERIOS = 40;
	public final static int TIPO_USO_INSTITUCIONES_PUBLICOS_OPD = 41;
	public final static int TIPO_USO_INSTITUCIONES_PUBLICOS_OTRAS_ENT = 42;
	
	public final static int TIPO_USO_INSTITUCIONES_EDUCATIVAS_NOLUCRA = 17;
	public final static int TIPO_USO_INSTITUCIONES_EDUCATIVAS_MENESTA = 31;
	public final static int TIPO_USO_INSTITUCIONES_EDUCATIVAS_MENPART = 32;
	public final static int TIPO_USO_INSTITUCIONES_EDUCATIVAS_UNIESTA = 33;
	public final static int TIPO_USO_INSTITUCIONES_EDUCATIVAS_UNIPART = 34;
	public final static int TIPO_USO_INSTITUCIONES_EDUCATIVAS_OTRSER = 47;
	
	/*arbitrios 2015:FIN*/
	
	
	public final static int CATEGORIA_USO_DOMESTICO = 11;
	public final static int CATEGORIA_USO_DOMESTICO_2016 = 47;
	
	public final static int TIPO_USO_PREDIO_RUSTICO_AGRICOLA=1;
	
	public final static int UBICACION_PREDIO_TERRENO_SIN_CONSTRUIR=1;
	public final static int UBICACION_PREDIO_PREDIO_INDEPENDIENTE=2;
	public final static int UBICACION_PREDIO_PREDIO_EN_EDIFICIO=3;
	public final static int UBICACION_PREDIO_PREDIO_EN_QUINTA=4;
	public final static int UBICACION_PREDIO_EDIFICIO=5;
	public final static int UBICACION_PREDIO_PARTE_DEL_PREDIO=6;
	
	public final static int TIPO_DESCARGO_DESCARGO=1;
	public final static int TIPO_DESCARGO_PAGOCUENTA=2;
	public final static int TIPO_DESCARGO_PRESCRIPCION=3;
	public final static int TIPO_DESCARGO_COMPENSACION=4;
	public final static int TIPO_DESCARGO_RECTIFICACION=5;
	public final static int TIPO_DESCARGO_TRANSFERENCIA=6;
	
	public final static String TIPO_RECIBO_IMPUESTOS = "1";
	public final static String TIPO_RECIBO_TUPA = "2";
	
	public final static int PREDIO_AFECTO_IMPUESTO_PREDIAL = 12;
	
	public final static int CONDUCTOR_AUSENTE=137619;
	public final static int CONDUCTOR_SIN_NOMBRE=1823;
	public final static int CONDUCTOR_NEGO_IDENTIFICARSE=4296;
	public final static int CONDUCTOR_FUGA=11269;
	
	public final static int INFRACCION_DOSAJE_ETILICO= 338;
	
	/**Son los frontis considerados en el Plan de Ordenamiento Urbano de Cajamarca
	 * **/
	public static int FRONTIS_MAXIMO = 10;
	public static int FRONTIS_MINIMO = 6;
	
	public final static int ESTADO_EXPEDIENTE_RECEPCIONADO = 1;
	public final static int ESTADO_EXPEDIENTE_PEND_REQUISITOS = 2;
	public final static int ESTADO_EXPEDIENTE_EN_TRAMITE = 3;	
	public final static int ESTADO_EXPEDIENTE_RESUELTO = 4;
	public final static int ESTADO_EXPEDIENTE_ASIGNADO = 6;
	public final static int ESTADO_EXPEDIENTE_PEND_APROBACION = 5;
	public final static int ESTADO_EXPEDIENTE_ATENDIDO = 7;
	
	//Constantes nuevas para tramite documentario: 8: ARCHIVADO, 9: ANULADO	
	public final static int ESTADO_EXPEDIENTE_ARCHIVADO = 8;
	public final static int ESTADO_EXPEDIENTE_ANULADO = 9;
		
	//Constantes nuevas para tramite documentario: 10: OBSERVADO, 11: COMPLETO, 12: INCOMPLETO 
	public final static int ESTADO_EXPEDIENTE_OBSERVADO = 10;
	public final static int ESTADO_EXPEDIENTE_COMPLETO = 11;
	public final static int ESTADO_EXPEDIENTE_INCOMPLETO = 12;
		
	//Constantes para Situacion de Expediente
	public final static int SITUACION_EXPEDIENTE_ANULADO = 5;
	public final static int SITUACION_EXPEDIENTE_ARCHIVADO = 6;
	public final static int SITUACION_EXPEDIENTE_RESUELTO = 7;
	public final static int SITUACION_EXPEDIENTE_DEVUELTO = 8;
	public final static int SITUACION_EXPEDIENTE_REGISTRADO = 9;
	public final static int SITUACION_EXPEDIENTE_DIGITALIZADO = 10;
	public final static int SITUACION_EXPEDIENTE_DERIVADO = 11;
	public final static int SITUACION_EXPEDIENTE_ASIGNADO = 12;
	public final static int SITUACION_EXPEDIENTE_RECEPCIONADO = 13;
		
	//Reclamos
	public static int ESTADO_RECLAMO_RECLAMADO = 1;
	public static int ESTADO_RECLAMO_RESUELTO= 0;	
	
	//Tipos de Documentos en Fiscalización
	public static String TIPO_DOC_CARTA = "Carta";
	public static String TIPO_DOC_REQ = "Requerimiento";
	public static String TIPO_DOC_CARTA_MULT = "Carta Múltiple";
	public static String TIPO_DOC_FIP = "FIP";
	public static String TIPO_DOC_AINR = "AINR";
	public static String TIPO_PROGRAMA = "Sin Programa";
	public static String TIPO_DOC_INFORME = "INF";
	
	public final static int TIPO_DOC_CARTA_ID = 4;
	public final static int TIPO_DOC_REQ_ID = 50;
	public final static int TIPO_DOC_CARTA_MULT_ID = 68;
	public final static int TIPO_DOC_FIP_ID = 65;
	public final static int TIPO_DOC_AINR_ID = 66;
	public final static int TIPO_DOC_EA_ID = 67;
	public final static int TIPO_DOC_AR_ID = 64;
	public final static int TIPO_PROGRAMA_ID =7;
	public final static int TIPO_DOC_INF_ID = 10;
	
	public static int ESTADO_EMITIDO = 1;
	public static int ESTADO_NOTIFICADO = 2;
	public static int ESTADO_CON_INSPECCION = 3;
	public static int ESTADO_AINR = 4;
	public static int ESTADO_CON_DJ_SIN_PAGO = 5;
	public static int ESTADO_CON_DJ_CON_PAGO  = 6;
	public static int ESTADO_CONFORME = 7;
	public static int ESTADO_CON_SUSTENTO_REQ= 8;
	public static int ESTADO_CON_SUSTENTO_RES = 9;
	public static int ESTADO_CON_RESULTADO_REQ = 10;
	public static int ESTADO_CON_VALORES_MULTA = 11;
	public static int ESTADO_CON_VALORES_FISCA = 12;
	public static int ESTADO_EMITIDO_POR_ANULAR = 13;
	public static int ESTADO_CON_INFORME_FINAL = 14;
	public static int ESTADO_ARCHIVADO = 15;
	public static int ESTADO_INACTIVADO = 0;
	public static int ESTADO_PENDIENTE_VERIFICACION = 4;
	public static int ESTADO_ACTIVOS = 1;
	
	/** Tipos de Operaciones */
	public final static int OPERACION_INSERCION = 1;
	public final static int OPERACION_BUSQUEDA = 2;
	public final static int OPERACION_ACTUALIZACION = 3;
	public final static int OPERACION_ELIMINACION = 4;
	public final static int OPERACION_IMPRESION = 5;
	public final static int OPERACION_INACTIVACION = 6;

	/** Tipo de Documento Sustentatorio */
	public static Integer DOCUMENTO_OTROS=99;
	
	public static int PRIMER_TRAMO = 1;
	public static int SEGUNDO_TRAMO = 2;

	
	/**
	 * Modulo Coactiva V2 ::Inicio
	 */
	public final static int TIPO_CARTERA_EXIGIBILIDAD = 1;
	public final static int TIPO_CARTERA_MEDIDA_CAUTELAR = 2;
	
	public final static int TIPO_DEUDA_MATERIA_TRIBUTARIO = 1;
	public final static int TIPO_DEUDA_MATERIA_NO_TRIBUTARIO = 2;
	/**
	 * Modulo Coactiva V2 ::Fin
	 */

	
	/** Resultado de  Evaluación */
	public static String ESTADO_FUNDADA="Fundada";
	public static int ESTADO_REQ_EN_TRAMITE = 1;
	public static int ESTADO_REQ_CON_REQUERIMIENTO = 2;
	public static int ESTADO_REQ_ATENDIDO = 4;
	
	/** Identificadores de submenus referencia tabla sg_submenu -=CRAMIREZ=- */
		/** TRIBUTACION */
		public static int TRIBUTOS_TRIBUTOS = 1;
		public static int VER_DATOS_GENERALES = 87;
		public static int EDITAR_DATOS_GENERALES = 88;
		public static int IMPRIMIR_DECLARACION = 89;
		public static int BENEFICIO_TRIBUTARIO = 90;
		public static int DECLARACION_JURADA = 91;
		public static int GENERACION_ARBITRIOS = 92;
		public static int ELIMINACION_DDJJ = 93;
		public static int DECLARACION_JURADA_VEHICULAR = 94;
		public static int ELIMINACION_DDJJ_VEHICULAR = 95;
		public static int EMITIR_MULTAS_ADMINISTRATIVAS = 97;
		public static int CONSULTA_DE_PAGOS = 99;
		public static int DESCARGO_DE_DEUDAS =100;
		public static int DECLARACION_JURADA_ACABALA =102;
		/** CONTROL*/
		public static int RESOL_DETERMINACION_PREDIAL = 2;
		public static int RESOL_DETERMINACION_ARBITRIOS = 3;
		public static int RESOLUCIONES_DE_SANCION = 4;
		public static int RS_NO_PECUNIARIAS = 5;
		public static int ORDENES_DE_PAGO = 6; 
		public static int NOTIFICACIONES = 8; 
		public static int RECLAMOS = 9;
		public static int CARTERA_DE_TRABAJO = 10;
		public static int GENERAR_DEUDA_EXIGIBLE = 11;
		/** COACTIVA*/
		public static int TRANSFERENCIAS = 13;
		public static int CARTERA_VALORES_TRIB = 14;
		public static int CARTERA_VALORES_NO_TRIB = 15;
		public static int NOTIFICACION_DE_EXPEDIENTES = 16;
		public static int MANTE_EJECUTOR_COACTIVO = 17;
		public static int MANTE_COSTAS = 18;
		public static int MANTE_MOTIVO_DEVOLUCION = 19;
		public static int GESTION_DE_EXPEDIENTES = 25;
		public static int REMATE_DE_VEHICULOS = 28;
		public static int CONSULTAS_RECS = 30;
		/** FISCALIZACION*/
		public static int CONTROL_DE_REQUERIMIENTOS = 32;
		public static int CARTERA_OMISOS = 34;
		public static int CONTROL_DE_REQUERIMIENTOS_VEHICULAR = 35;
		public static int RESOL_DETERMINACION_VEHIC = 36;
		public static int REGISTRO_DE_FISCALIZADORES = 39;
		public static int REGISTROR_DE_PROGRAMA = 40;
		/** PAPELETAS*/
		public static int REGISTRO_PAPELETA = 41;
		public static int REGISTRO_MASIVO = 43;
		public static int DESCARGAR_PAPELETA = 46;
		public static int REGISTRO_DOSAJE_ETILICO = 47;
		/** CONSULTAS*/
		public static int CONSULTA_DE_VEHICULOS = 62;
		/** TRAMITE*/
		public static int MESA_PARTES = 78;

	/** Identificadores de permisos referencia tabla sg_nivel_acceso -=CRAMIREZ=- */
	public static int ACCESO_MODULO = 1;
	public static int AGREGAR_REGISTRAR = 2;
	public static int MODIFICAR_ACTUALIZAR = 3;
	public static int ELIMINAR = 4;
	public static int LISTAR_INFORMACION = 5;
	public static int VER_DETALLE = 6;
	public static int BUSCAR = 7;
	public static int ESTADO_CUENTA = 8;
	public static int CALCULAR = 9;
	public static int IMPRIMIR_DJ = 10;
	public static int DESCARGAR_DJ = 11;
	public static int IMPRIMIR_DESCARGO_DJ = 12;
	public static int VER_DJ = 13;
	public static int COPIAR_DJ = 14;
	public static int ACTUALIZAR_DJ = 15;
	public static int DETERMINAR = 16;
	public static int PDF_HR=17;
	public static int PDF_PU = 18;
	public static int PDF_PR = 19;
	public static int PDF_HL = 20;
	public static int PDF_DJ = 21;
	public static int VER_CALCULO = 22 ; 
	public static int VER_COMPARACION_DETER_IMACION = 23 ;
	public static int VER_ARBITRIOS = 24;
	public static int DETERMINAR_ARBITRIOS = 25 ;
	public static int PDF_ARBITRIOS = 26;
	public static int GENENRAR = 27;
	public static int INACTIVAR = 28;
	public static int IMPRIMIR_DETERMINACION  = 29;
	public static int IMPRIMIR_LIQUIDACION = 30;
	public static int NOTIFICAR = 31;
	public static int IMPRIMIR = 32;	
	public static int CARCULAR_DEUDA = 33;
	public static int FILTRAR = 34;
	public static int DESCARGO = 35; 
	public static int TRASFERENCIA = 36; 
	public static int PRESCRIPCIONES = 37;
	public static int COMPENSACIONES = 38;
	public static int RECLAMAR = 39;
	public static int DESCARGAR_EXCEL_TERMINAL= 40;
	public static int DESCARGAR_EXCEL_RESUMIDO = 41;
	public static int RECEPCIONA = 42;
	public static int REASIGNAR = 43;
	public static int VER_EJECUTOR = 44;
	public static int ACUMULAR_TODO = 45;
	public static int DESACUMULAR_TODO = 46;
	public static int ACUMULAR = 47;
	public static int RETIRAR = 48;	
	public static int DUPLICAR = 49;
	public static int DESCARGA_PDF = 50;
	public static int ADJUNTAR = 51;
	public static int BLOQUEAR_DESBLOQUEAR_DEUDA = 52;
	public static int ELIMINAR_REC = 53;
	public static int GENERAR_REC_INICIO = 54;
	public static int GENERAR_MAS_RECS = 55;
	public static int VERIFICAR = 56;
	public static int CAJERO = 57;
	public static int SUPERVISOR_CAJA = 58;
	public static int DIGITALIZAR = 59;
	public static int SEGUIMIENTO = 60;
	public static int HISTORICO_MODIFICACIONES = 61;

	/** Identificadores de tipo de menus  -=CRAMIREZ=- */
	public static int MENU_PRINCIPAL = 1;
	public static int MENU_CONTRIBUYENTE = 2;
	
	/** descargo de deudas -=CRAMIREZ=- */
	public static int ANTIGUEDAD_DEUDA_PRESCRIBIR = 4;
	
	public static String TIPOS_TRAMITES_PRESCRIBIR = "10,13";
	public static String TIPOS_TRAMITES_COMPENZACION = "8,9";
	
	public static String TIPOS_TRAMITES_TRANSFERENCIA = "26,27";
	
	public static int TUPA_CONSTANCIA_NO_ADEUDO = 8;
	public static int TUPA_RECORD_HR_PU_PR = 13;
	
}