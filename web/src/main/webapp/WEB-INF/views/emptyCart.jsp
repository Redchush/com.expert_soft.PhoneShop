<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<spring:message var="this_title" code="cart.title"/>
<jsp:include page="/WEB-INF/views/part/head_template.jsp">
  <jsp:param name="head_title" value="${this_title}"/>
</jsp:include>
<body>
<c:import url="/WEB-INF/views/part/header/header_with_cart.jsp"/>

<div class="container">
  <div class="row">
    <div class="col-lg-12 pnf">
      <h1><spring:message code="cart.header"/></h1>
      <p><spring:message code="cart.empty"/></p>
    </div>
    <%@ include file="part/button/backMainBtn.jsp" %>
    <p>
      <table class="table table-striped">
        <%@ include file="part/table/cart_thead.jsp" %>
      </table>
    </p>
  </div>
</div>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>
</body>
</html>
