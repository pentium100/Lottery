<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Pragma","public");
 response.setHeader("Expires","0");
 response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
 response.setHeader("Content-Type","application/force-download");
 response.setHeader("Content-Type","application/vnd.ms-excel");
 response.setCharacterEncoding("utf-8"); 
 response.setHeader("Charset","utf-8"); 
 response.setHeader("Content-Disposition","attachment;filename=match.xls");
 
 out.print(request.getParameter("exportContent"));
 %>