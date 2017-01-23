<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--use phf class to change default bootstrap css-->
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Phonify <c:out value="${requestScope.phone.model}"/> details</title>
  <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
  <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
</head>
<body>
<c:import url="/WEB-INF/views/part/header_with_cart.jsp"/>
<div class="container">
  <div class="row">
    <a href="<c:url value="/products"/> ">
      <button class="btn btn-default pnf" type="button"><spring:message
              code="button.backToMain"/></button>
    </a>
    <div class="col-lg-12 pnf">
      <h1><spring:message code="error.internal"/></h1>
    </div>
    <div class="col-lg-12 pnf">
      <spring:message code="error.internal" arguments="${requestScope.url}"/>
      <c:out value="${requestScope.description}"/>
    </div>
  </div>
</div>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/main.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>

</body>
</html>

