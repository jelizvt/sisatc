package com.sat.sisat.common.security.filter;

import java.io.IOException;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sat.sisat.common.security.UserSession;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.usuario.business.UsuarioBoRemote;
import com.sat.sisat.usuario.dto.UsuarioDTO;

/**
 * Filters all requests for application resources and uses an
 * AuthorizationManager to authorize access.
 * 
 * @author César Altamirano
 */

public class SecurityFilter extends BaseManaged implements Filter {

	private static final long serialVersionUID = 8079780068977928453L;

	private FilterConfig config;

	@EJB
	private UsuarioBoRemote usuarioBo;
	
	@Inject
	UserSession user;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	public void doFilter(ServletRequest sreq, ServletResponse sres, FilterChain chain) throws IOException,
			ServletException {
		HttpServletResponse response = (HttpServletResponse) sres;
		HttpServletRequest request = (HttpServletRequest) sreq;

		if (user.getUsuarioId() == 1) {

			String userName = request.getUserPrincipal().getName();
			try {

				String terminal = request.getRemoteAddr() != null ? request.getRemoteAddr() : request.getLocalAddr();

				UsuarioDTO us = usuarioBo.getUsuarioLoginData(userName);
				user.setUsuario(us.getUsuario().getNombreUsuario());
				user.setUsuarioId(us.getUsuario().getUsuarioId());
				user.setTerminal(terminal);

				System.out
						.println("************************************************* Call Data user filter *************************************************");
				System.out.println(user);

			} catch (Exception ex) {
				System.out.println("ERROR en la obtencion de credenciales de usuarios: " + ex.getMessage());
				throw new IOException("ERROR en la obtencion de credenciales de usuarios: " + ex.getMessage());
			}
		}

		chain.doFilter(sreq, sres);
	}

	@PreDestroy
	public void destroy() {
		config = null;
	}
}