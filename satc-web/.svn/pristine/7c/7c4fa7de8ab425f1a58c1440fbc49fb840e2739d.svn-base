package com.sat.sisat.common.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sat.sisat.common.managed.SessionManaged;
import com.sat.sisat.common.security.TracingInterceptor;
import com.sat.sisat.common.security.UserSession;

@Interceptors(TracingInterceptor.class)
public class BaseManaged implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2787193526357748657L;
	
	private int subMenuId;
	
	@Inject
	UserSession user; 
	
	public UserSession getUser() {
		return user;
	}

	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public ServletContext getServletContext() {
		return (ServletContext) getExternalContext().getContext();
	}

	public HttpServletRequest getServletRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public HttpServletResponse getServletResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	/**
	 * Obtiene el valor del parametro solicitado
	 * 
	 * @param name
	 *            Identificador del parámetro a buscar en el contexto.
	 * @return String
	 */
	public String getRequestParameter(String name) {
		return (String) getExternalContext().getRequestParameterMap().get(name);
	}
	
	public Map<String, Object> getSessionMap() {
		return getExternalContext().getSessionMap();
	}
	
	/**
	 * Obtiene un mensaje asociado a un identificador o llave ingresada
	 * 
	 * @param key
	 *            Llave para buscar la cadena en el archivos de mensajes de
	 *            internacionalización
	 * @return String
	 */
	public String getMsg(String key) {
		return MessagesLoader.getMessage(key);
	}

	/**
	 * Envia mensaje de información al cliente o usuario final del sistema
	 * 
	 * @param msg
	 *            Mensaje a enviar
	 */
	public void addInfoMessage(String msg) {
		addMessage(FacesMessage.SEVERITY_INFO, msg);
	}

	/**
	 * Envia mensaje de advertencia al cliente o usuario final del sistema
	 * 
	 * @param msg
	 *            Mensaje a enviar
	 */
	public void addWarnMessage(String msg) {
		addMessage(FacesMessage.SEVERITY_WARN, msg);
	}

	/**
	 * Envia mensaje de error al cliente o usuario final del sistema
	 * 
	 * @param msg
	 *            Mensaje a enviar
	 */
	public void addErrorMessage(String msg) {
		addMessage(FacesMessage.SEVERITY_ERROR, msg);
	}

	/**
	 * Envia mensaje de error grabe al cliente o usuario final del sistema
	 * 
	 * @param msg
	 *            Mensaje a enviar
	 */
	public void addFatalMessage(String msg) {
		addMessage(FacesMessage.SEVERITY_FATAL, msg);
	}

	/**
	 * Envia un mensajes al cliente o usuario final del sistema
	 * 
	 * @param severity
	 *            Severidad del mensaje(Informacion, Advertencia, Error, Error
	 *            grabe)
	 * @param message
	 *            Mensaje a enviar al cliente.
	 */
	private void addMessage(FacesMessage.Severity severity, String message) {
		FacesMessage facesMessage = new FacesMessage(severity, message, message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	/**
	 * Obtiene un Managed Bean
	 * 
	 * @param beanName
	 *            Nombre del Managed Bean que se desea buscar
	 * @return Managed Bean como objeto
	 */
	public Object getManaged(String beanName) {
		FacesContext fc = FacesContext.getCurrentInstance();
		Application app = fc.getApplication();
		ValueExpression ve = app.getExpressionFactory().createValueExpression(
				fc.getELContext(), String.format("#{%s}", beanName),
				Object.class);
		return ve.getValue(fc.getELContext());
	}

	/**
	 * Obtiene o crea y obtiene el bean de session principal
	 * 
	 * @return
	 */
	public SessionManaged getSessionManaged() {
		return (SessionManaged) getManaged("sessionManaged");
	}

	/**
	 * Elimina Managed Bean que tiene alcance de Sessión
	 * 
	 * @param beanName
	 *            Nombre del Bean a eliminar
	 */
	public void removeSessionBean(String beanName) {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			if (fc.getExternalContext().getSessionMap().containsKey(beanName)) {
				fc.getExternalContext().getSessionMap().remove(beanName);
			}
		} catch (RuntimeException ex) {
			// logger.error(ex); TODO : Corregir
		}
	}

	public static Object getSessionMapValue(FacesContext context, String key) {
		return context.getExternalContext().getSessionMap().get(key);
	}

	public static void setSessionMapValue(FacesContext context, String key,
			Object value) {
		context.getExternalContext().getSessionMap().put(key, value);
	}

	public static Object removeSessionMapValue(FacesContext context, String key) {
		return context.getExternalContext().getSessionMap().remove(key);
	}

	public static void closeSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Object session = externalContext.getSession(false);
		HttpSession httpSession = (HttpSession) session;
		httpSession.invalidate();
	}

	public static void closeSession(String namebean) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Object session = externalContext.getSession(false);
		HttpSession httpSession = (HttpSession) session;
		httpSession.removeAttribute(namebean);
	}

	public String sendRedirectPrincipal() {
		return "/sisat/principal.xhtml?faces-redirect=true";
	}
	public String sendRedirectPrincipalSuMenu(int submenuId) {
		System.out.println("sendRedirectPrincipalSuMenu");
		this.subMenuId = submenuId;
		return "/sisat/principal.xhtml?faces-redirect=true";
	}

	public void sendRedirectPrincipalListener() {
		try {
			getExternalContext().redirect("principal.jsf");
		} catch (IOException ex) {
			// TODO : Controller exception
			System.out.println("Pagina no encontrada");
		}
	}
	
	/**
	 * Método para hacer operaciones de loggers 
	 * @param s Cadena de texto q contiene lo que se imprimira en el log
	 */
	public void debug(String s){
		//System.out.println(s);
	}
	
	public void validateStateUser(UserSession _userSession) throws Exception{
		
		if(_userSession.getUsuarioId() == 1){
			throw new Exception("Error en la obtencion de datos de usuario");
		}
		
		
	}

	public int getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(int subMenuId) {
		this.subMenuId = subMenuId;
	}
	
}
