<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/WEB-INF/views/part/head_template.jsp"/>

<body>
<jsp:include page="/WEB-INF/views/part/header/header_with_button.jsp">
  <jsp:param name="button_url" value="/products"/>
  <jsp:param name="button_code" value="button.backToMain"/>
</jsp:include>

<spring:message var="name" code="common.username"/>
<spring:message var="pass" code="common.password"/>
<c:set var="login_param" value="login"/>
<c:set var="password_param" value="password"/>

<div class="container">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
      <h1><spring:message code="common.login"/> </h1>
      <c:if test="${not empty errorMsg}">
        <c:set var="add_class" value="has-error"/>
        <div class="alert alert-danger fade in">
           ${errorMsg}
        </div>
      </c:if>
      <c:if test="${not empty successMsg}">
        <div class="alert alert-success fade in">
          ${successMsg}
        </div>
      </c:if>

      <c:url var="formUrl" value="/login"/>
      <form action="${formUrl}" method="post">
        <div class="form-group ${add_class}">
          <label for="${login_param}">${name}</label>
          <input type="text" class="form-control" id="${login_param}" name="${login_param}" placeholder="${name}">
        </div>
        <div class="form-group ${add_class}">
          <label for="${password_param}">${pass}</label>
          <input type="text" class="form-control" id="${password_param}" name="${password_param}" placeholder="${pass}">
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Submit</button>
      </form>

    </div>
  </div>

</body>

</html>