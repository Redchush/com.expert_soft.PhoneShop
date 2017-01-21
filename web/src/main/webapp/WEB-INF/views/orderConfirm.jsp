<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Phonify product list</title>

  <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
  <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">

</head>

<body>
<!--use phf class to change default bootstrap css-->
<c:import url="/WEB-INF/views/part/header_without_cart.jsp"/>
<div class="container">
  <div class="row">
    <div class="col-lg-12 pnf">
      <h1>Thank for your order</h1>
    </div>
    <table class="table table-responsive">
      <%@ include file="part/product/product_thead.jsp" %>
      <tbody>
      <c:forEach var="phone" items="${requestScope.phones}">
        <tr>
          <td>Model</td>
          <td>Color</td>
          <td>Display size</td>
          <td>Price</td>
          <td>1</td>
          <td>200</td>
        </tr>
      </c:forEach>
      </tbody>
      <tfoot class="emptyCelled">
      <tr>
        <td colspan="4"></td>
        <td>Subtotal</td>
        <td><td><fmt:formatNumber value="${requestScope.order.subtotal}"
                                  type="currency" currencySymbol="$"/></td>
      </tr>
      <tr>
        <td colspan="4"></td>
        <td>Delivery</td>
        <td><fmt:formatNumber value="${requestScope.order.deliveryPrice}"
                              type="currency" currencySymbol="$"/></td>
      </tr>
      <tr>
        <td colspan="4"></td>
        <td><spring:message code="order.total"/> </td>
        <td><fmt:formatNumber value="${requestScope.order.totalPrice}"
                              type="currency" currencySymbol="$"/></td>
      </tr>
      </tfoot>
    </table>

  </div>
  <div class="row">
    <div class="form-horizontal pnf">
      <div class="form-group">
        <label class="col-sm-2"><spring:message code="order.userInfo.firstName"/></label>
        <div class="col-sm-5">
          <c:out value="${requestScope.order.userInfo.firstName}" escapeXml="true"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-2"><spring:message code="order.userInfo.lastName"/></label>
        <div class="col-sm-5">
        <c:out value="${requestScope.order.userInfo.lastName}"  escapeXml="true"/>
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-2" ><spring:message code="order.userInfo.address"/></label>
        <div class="col-sm-5">
        <c:out value="${requestScope.order.userInfo.deliveryAddress}" escapeXml="true"/>
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-2" >Phone</label>
        <div class="col-sm-5">
          <c:out value="${requestScope.order.userInfo.contactPhoneNo}" escapeXml="true"/>
        </div>
      </div>

      <div class="form-group">
        <div class="col-sm-7">
          <c:out value="${requestScope.order.userInfo.additionalInfo}" escapeXml="true"/>
        </div>
      </div>

      <div class="form-group">
        <div class="col-sm-3">
          <button type="submit" class="btn btn-default btn-block pnf">Back to Shopping</button>
        </div>
      </div>
    </div>
  </div>

</div>

<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>

</body>
</html>
