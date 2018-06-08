<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="info" %>

<%@ attribute name="captchaId" required="false" %>
<%@ attribute name="image" required="true" %>

<div>
    <c:if test="${initParam.captchaHandler == 'appCaptchaHandler'}">
        <input type="hidden" name="CaptchaId" value="${CaptchaId}" >
    </c:if>
    <img src="data:image/jpg;base64,${captchaImage}" width="200" height="100"><br>
</div>
