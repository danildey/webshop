<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="info" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="URL" value="${pageContext.request.requestURL }"/>
<c:set var="URI" value="${pageContext.request.requestURI }"/>
<c:set var="baseURL" value="${fn:replace(URL, URI, contextPath)}"/>

<%@ attribute name="user" required="true" %>
<%@ attribute name="avatar" required="true" %>

 <c:choose>

    <c:when test="${sessionScope.userBean eq null}">
       <ul class="login">
          <li><a href="${baseURL}/login"><span> </span>LOGIN</a></li>
             |
          <li><a href="${baseURL}/register">SIGNUP</a></li>
       </ul>
    </c:when>

    <c:otherwise>
       <ul class="login">
          <li><a href="${baseURL}/account"><span> </span>YOUR ACCOUNT</a></li>
           |
          <li><a href="${baseURL}/logout">LOGOUT</a></li>
       </ul>
       <img src="avatar" height="35" >
    </c:otherwise>

 </c:choose>
