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

  <title>Phonify customer order</title>
  <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
  <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
</head>
<body>
<c:import url="/WEB-INF/views/part/header_without_cart.jsp"/>

<div class="container">
  <div class="row">
    <div class="col-lg-12 pnf">
      <h1>Order</h1>
    </div>
    <p>
      <a href="<c:url value="/products"/> ">
        <button class="btn btn-default pnf" type="button"><spring:message
                code="button.backToMain"/> </button>
      </a>
    </p>
    <table class="table table-responsive">
      <%@ include file="part/product/product_thead.jsp" %>
      <tbody>
      <c:forEach  var="item" items="${order.orderItems}">
        <tr>
          <td>${item.phone.model}</td>
          <td>${item.phone.color}</td>
          <td>${item.phone.displaySize}</td>
          <td>${item.phone.price}</td>
          <td>${item.quantity}</td>
          <td>action</td>
        </tr>
      </c:forEach>
      </tbody>
      <tfoot class="emptyCelled">
      <tr>
        <td colspan="4"></td>
        <td><spring:message code="order.subtotal"/></td>
        <td>${order.subtotal}</td>
      </tr>
      <tr>
        <td colspan="4"></td>
        <td><spring:message code="order.deliveryPrice"/></td>
        <td>${order.deliveryPrice}</td>
      </tr>
      <tr>
        <td colspan="4"></td>
        <td><spring:message code="order.total"/></td>
        <td>${order.totalPrice}</td>
      </tr>
      </tfoot>
    </table>

  </div>
  <div class="row">
    <spring:url value="/doOrder" var="userActionUrl" />

    <form:form class="form-horizontal pnf" modelAttribute="userInfo"
               method="POST" action="${userActionUrl}" htmlEscape="true">
      <div class="form-group">
        <spring:bind path="firstName">
          <label class="control-label col-sm-2" for="f_name">First name</label>
          <div class="col-sm-5">
          <form:input path="firstName" cssErrorClass="has-error" cssClass="form-control"
                        id="f_name"
                        placeholder="First name"/>
          </div>
          <div class="form-control-feedback has-error">
            <form:errors path="firstName" cssClass="has-error"/>
          </div>
        </spring:bind>
      </div>
      <div class="form-group">
        <spring:bind path="lastName">
          <label class="control-label col-xs-2" for="l_name">Last name</label>
          <div class="col-sm-5">
            <form:input path="lastName" cssErrorClass="has-error" cssClass="form-control"
                        id="l_name"
                        placeholder="Last name"/>
          </div>
          <div class="form-control-feedback has-error"><form:errors
                  path="lastName"/></div>
        </spring:bind>
      </div>

      <div class="form-group">
        <spring:bind path="deliveryAddress">
          <label class="control-label col-sm-2" for="address">Address</label>
          <div class="col-sm-5">
            <form:input path="deliveryAddress" cssErrorClass="has-error" cssClass="form-control"
                        id="address" placeholder="Address"/>
          </div>
          <div class="form-control-feedback has-error">
            <form:errors path="deliveryAddress" cssClass="control-label has-error"/>
          </div>
        </spring:bind>
      </div>
      <div class="form-group">
        <spring:bind path="contactPhoneNo">
          <label class="control-label col-sm-2" for="phone">Phone</label>
          <div class="col-sm-5">
            <form:input path="contactPhoneNo" cssErrorClass="has-error" cssClass="form-control"
                        id="phone" placeholder="Phone"/>
          </div>
          <div class="form-control-feedback has-error">
            <form:errors path="contactPhoneNo" cssClass="control-label has-error"/>
          </div>
        </spring:bind>
      </div>

      <div class="form-group">
        <spring:bind path="additionalInfo">
          <div class="col-sm-7">
            <form:textarea path="additionalInfo" cssErrorClass="has-error" cssClass="form-control"
                           id="add_info" placeholder="Additional information"/>
          </div>
          <div class="form-control-feedback has-error">
            <form:errors path="additionalInfo" cssClass="control-label has-error"/>
          </div>
        </spring:bind>
      </div>

      <div class="form-group">
        <div class="col-sm-2">
          <button type="submit" class="btn btn-default btn-block pnf"><spring:message
                  code="button.order"/> </button>
        </div>
      </div>
    </form:form>
  </div>
</div>

<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>

</body>
</html>
