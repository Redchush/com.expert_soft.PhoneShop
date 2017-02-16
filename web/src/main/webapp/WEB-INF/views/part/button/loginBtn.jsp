<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="loginURl" value="/login"/>
<a class="inline" href="${loginURl}">
  <button class="btn btn-default pnf" type="button">
    <spring:message code="common.login"/>
  </button>
</a>