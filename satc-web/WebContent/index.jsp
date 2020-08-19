<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%request.getSession().invalidate();%>  
<%response.sendRedirect("sisat/inicio.jsf");%>  