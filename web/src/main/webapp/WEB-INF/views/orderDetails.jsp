<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Phonify order confirm</title>
  <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
  <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
</head>
<body>
<!--use phf class to change default bootstrap css-->
<c:import url="/WEB-INF/views/part/header_without_cart.jsp"/>
<div class="container">
  <div class="row">
    <div class="col-lg-12 pnf">
      <h1><spring:message code="order.confirm.thanks"/></h1>
    </div>
    <table class="table table-responsive">
      <%@ include file="part/table/order_thead.jsp" %>
      <tbody>
      <c:forEach var="item" items="${order.orderItems}">
        <tr>
          <td>${item.phone.model}</td>
          <td>${item.phone.color}</td>
          <td>${item.phone.displaySize}</td>
          <td>${item.quantity}</td>
          <td>${item.phone.price}</td>
        </tr>
      </c:forEach>
      </tbody>
      <tfoot class="emptyCelled">
      <tr>
        <td colspan="3"></td>
        <td><spring:message code="order.subtotal"/></td>
        <td><fmt:formatNumber value="${order.subtotal}"
                                  type="currency" currencySymbol="$"/></td>
      </tr>
      <tr>
        <td colspan="3"></td>
        <td><spring:message code="order.deliveryPrice"/></td>
        <td><fmt:formatNumber value="${order.deliveryPrice}"
                              type="currency" currencySymbol="$"/></td>
      </tr>
      <tr>
        <td colspan="3"></td>
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
          <c:out value="${order.userInfo.firstName}" escapeXml="true"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-2"><spring:message code="order.userInfo.lastName"/></label>
        <div class="col-sm-5"><c:out value="${order.userInfo.lastName}"
                                     escapeXml="true"/></div>
      </div>
      <div class="form-group">
        <label class="col-sm-2" ><spring:message code="order.userInfo.address"/></label>
        <div class="col-sm-5"><c:out value="${order.userInfo.deliveryAddress}"
                                     escapeXml="true"/></div>
      </div>
      <div class="form-group">
        <label class="col-sm-2" >Phone</label>
        <div class="col-sm-5"><c:out value="${order.userInfo.contactPhoneNo}"
                                     escapeXml="true"/></div>
      </div>
      <div class="form-group">
        <div class="col-sm-7"><c:out value="${order.userInfo.additionalInfo}"
                                     escapeXml="true"/></div>
      </div>
      <div class="form-group">
        <div class="col-sm-3">
          <a href="<c:url value="/products"/> ">
             <button type="submit" class="btn btn-default btn-block pnf"><spring:message
                     code="button.backToShopping"/></button>
          </a>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
