<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="buttonUrl" value="${param.button_url}"/>
<a class="inline" href="${buttonUrl}">
  <button class="btn btn-default pnf" type="button">
    <spring:message code="${param.button_code}"/>
  </button>
</a>
