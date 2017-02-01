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

  <title><spring:message code="products.title"/></title>
  <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
  <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
</head>
<body>
<c:import url="/WEB-INF/views/part/header_with_cart.jsp"/>
<div class="container">
  <div class="row">
    <div class="col-lg-12 pnf">
      <h1><spring:message code="products.header"/></h1>
    </div>
      <table class="table table-striped">
        <%@ include file="part/table/cart_thead.jsp" %>
        <tbody>
        <c:forEach var="phone" items="${phones}">
          <tr>
            <td>
              <a href="<c:url value="/products/${phone.key}"/>">
                <span data-save="${phone.key}">${phone.model}</span></a>
            </td>
            <td>${phone.color}</td>
            <td><fmt:formatNumber value="${phone.displaySize/10}" type="number"
                                  maxFractionDigits="1"/>''</td>
            <td><fmt:formatNumber value="${phone.price}" type="currency"
                                  currencySymbol="$"/></td>
            <td>
              <form id="addToCartForm<c:out value="${phone.key}"/>" name="doAddToCartForm">
                <input name="quantity" class="input-sm" value="1" title="quantity">
                <input name="phoneId" type="hidden" value="${phone.key}">
              </form>
            </td>
            <td>
              <button form="addToCartForm<c:out value="${phone.key}"/>"
                      class="btn btn-default btn-sm pnf" type="submit">
                <spring:message code="button.addToCart"/></button>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    <output id="msg"><spring:message code="${requestScope.msgCode}" text=" "/></output>
  </div>
</div>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/main.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>
</body>
</html>
