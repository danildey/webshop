<!DOCTYPE html>
<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Error page</title>
    <meta charset="utf-8">
</head>
<body>
<button onclick="history.back()">Back to Previous Page</button>
<h1>404 Page Not Found.</h1>
<tr>
    <td class="content">
        <%-- CONTENT --%>

        <h2 class="error">
            The following error occurred
        </h2>

        <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
        <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>
        <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

        <c:if test="${not empty code}">
            <h3>Error code: ${code}</h3>
        </c:if>

        <c:if test="${not empty message}">
            <h3>${message}</h3>
        </c:if>

        <c:if test="${not empty exception}">
            <% exception.printStackTrace(new PrintWriter(out)); %>
        </c:if>

        <%-- if we get this page using forward --%>
        <c:if test="${not empty requestScope.errorMessage}">
            <h3>${requestScope.errorMessage}</h3>
        </c:if>

        <%-- CONTENT --%>
    </td>
</tr>
</body>
</html>