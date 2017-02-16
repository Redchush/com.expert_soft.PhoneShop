<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message var="buttonMsg" code="${param.button_code}"/>
<c:url var="buttonUrl" value="${param.button_url}"/>
<nav class="navbar navbar-default">
  <div class="container">
    <a class="navbar-brand" href="<c:url value="/index.jsp"/>">
      <span class="glyphicon glyphicon-phone"></span>Phonify
    </a>
    <div class="pull-right">
      <a class="inline" href="${buttonUrl}">
        <button class="btn btn-default pnf" type="button">${buttonMsg}</button>
      </a>
    </div>
    <div class="clearfix"></div>
  </div>
</nav>
