<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="logoutURL" value="/logout"/>
<form class="pnf_inline" action="${logoutURL}" method="post" id="logoutForm">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  <button class="btn btn-default pnf" type="submit">
    <spring:message code="common.logout"/>
  </button>
</form>
