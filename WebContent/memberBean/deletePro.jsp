
<%@page import="ch11.logon.LogonDBBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%request.setCharacterEncoding("utf-8"); %>

<%
	String id = (String)session.getAttribute("id");
	String passwd = request.getParameter("passwd");
	
	LogonDBBean manager = LogonDBBean.getInstance();
	
	int check = manager.deleteMember(id, passwd);
	
	if(check == 1){
		session.invalidate();
	}
	out.println(check);
			
%>

