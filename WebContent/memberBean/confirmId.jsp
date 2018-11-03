
<%@page import="ch11.logon.LogonDBBean"%>
<%@page import="ch11.logon.LogonDataBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%request.setCharacterEncoding("utf-8"); %>

<%
	String id = request.getParameter("id");

LogonDBBean manager = LogonDBBean.getInstance();

int check = manager.confirmId(id);
out.println(check);
%>
