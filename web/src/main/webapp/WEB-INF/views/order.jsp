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
      <%@ include file="part/table/order_thead.jsp" %>
      <tbody>
      <c:forEach  var="item" items="${order.orderItems}">
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
        <td>${order.subtotal}</td>
      </tr>
      <tr>
        <td colspan="3"></td>
        <td><spring:message code="order.deliveryPrice"/></td>
        <td>${order.deliveryPrice}</td>
      </tr>
      <tr>
        <td colspan="3"></td>
        <td><spring:message code="order.total"/></td>
        <td>${order.totalPrice}</td>
      </tr>
      </tfoot>
    </table>

  </div>
  <div class="row">
    <style>
      .form-group span{
        display: none;
      }
      .form-group.has-feedback.has-error span,
      .form-group.has-feedback.has-success span.glyphicon-ok{
         display: block;
      }
    </style>
    <spring:url value="/doOrder" var="userActionUrl" />
    <spring:hasBindErrors name="userInfo">

      <c:set var="group_error_class" value="has-feedback has-error"/>
      <c:set var="group_success_class" value="has-feedback has-success"/>

      <c:set var="valid_icon">
        <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
      </c:set>
      <c:set var="invalid_icon">
        <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
      </c:set>

      <c:set var="firstName_icon" value="${valid_icon}"/>
      <c:set var="lastName_icon" value="${valid_icon}"/>
      <c:set var="delivery_icon" value="${valid_icon}"/>
      <c:set var="phone_icon" value="${valid_icon}"/>
      <c:set var="addInfo_icon" value="${valid_icon}"/>

      <c:set var="firstName_errorClass" value="${group_success_class}"/>
      <c:set var="lastName_errorClass" value="${group_success_class}"/>
      <c:set var="deliveryAddr_errorClass" value="${group_success_class}"/>
      <c:set var="phoneNo_errorClass" value="${group_success_class}"/>
      <c:set var="addInfo_errorClass" value="${group_success_class}"/>

      <c:if var="isFirstNameError" test="${errors.hasFieldErrors('firstName')}">
        <c:set var="firstName_errorClass" value="${group_error_class}" />
        <c:set var="firstName_icon" value="${invalid_icon}"/>
      </c:if>
      <c:if var="isLastNameError" test="${errors.hasFieldErrors('lastName')}">
        <c:set var="lastName_errorClass" value="${group_error_class}" />
        <c:set var="lastName_icon" value="${invalid_icon}"/>
      </c:if>
      <c:if var="isDeliveryError" test="${errors.hasFieldErrors('deliveryAddress')}">
        <c:set var="deliveryAddr_errorClass" value="${group_error_class}" />
        <c:set var="delivery_icon" value="${invalid_icon}"/>
      </c:if>
      <c:if var="isPhoneNoError" test="${errors.hasFieldErrors('contactPhoneNo')}">
        <c:set var="phoneNo_errorClass" value="${group_error_class}" />
        <c:set var="phone_icon" value="${invalid_icon}"/>
      </c:if>
      <c:if var="isAddInfoErro" test="${errors.hasFieldErrors('additionalInfo')}">
        <c:set var="addInfo_errorClass" value="has-error" />
        <c:set var="addInfo_icon" value="${invalid_icon}"/>
      </c:if>
    </spring:hasBindErrors>
    <form:form class="form-horizontal pnf" modelAttribute="userInfo"
               method="POST" action="${userActionUrl}" htmlEscape="true">

      <spring:bind path="firstName">
        <spring:message var="firstNameMsg" code="order.userInfo.firstName"/>
        <div class="form-group <c:out value="${firstName_errorClass}"/> ">
          <label class="control-label col-sm-2" for="f_name">${firstNameMsg}</label>
          <div class="col-sm-5">
            <form:input path="firstName" cssClass="form-control"
                        id="f_name"
                        placeholder="${firstNameMsg}"/>
            <c:out value="${firstName_icon}" escapeXml="false"/>
            <span id="usernameStatus" class="help-block"><form:errors
                    path="firstName" cssClass="has-error"/>!</span>
          </div>
        </div>
      </spring:bind>

      <spring:bind path="lastName">
        <spring:message var="lastNameMsg" code="order.userInfo.lastName"/>
        <div class="form-group <c:out value="${lastName_errorClass}"/>">
          <label class="control-label col-xs-2" for="l_name">${lastNameMsg}</label>
          <div class="col-sm-5">
            <form:input path="lastName" cssClass="form-control"
                        id="l_name"
                        placeholder="${lastNameMsg}"/>
            <c:out value="${lastName_icon}" escapeXml="false"/>
            <span id="usernameStatus" class="help-block"><form:errors
                    path="lastName" htmlEscape="false"/>
            </span>
          </div>
        </div>
      </spring:bind>
      <spring:bind path="deliveryAddress">
        <spring:message var="addrMsg" code="order.userInfo.address"/>
        <div class="form-group <c:out value="${deliveryAddr_errorClass}"/>">
          <label class="control-label col-sm-2" for="address">${addrMsg}</label>
          <div class="col-sm-5">
            <form:input path="deliveryAddress" cssClass="form-control"
                        id="address" placeholder="${addrMsg}"/>
            <c:out value="${delivery_icon}" escapeXml="false"/>
            <span id="usernameStatus" class="help-block"><form:errors
                    path="deliveryAddress"/></span>
          </div>
        </div>
      </spring:bind>
      <spring:bind path="contactPhoneNo">
        <spring:message var="phone_msg" code="order.userInfo.contactPhoneNo"/>
        <div class="form-group <c:out value="${phoneNo_errorClass}"/>">
          <label class="control-label col-sm-2" for="phone">${phone_msg}</label>
          <div class="col-sm-5">
            <form:input path="contactPhoneNo" cssClass="form-control"
                        id="phone" placeholder="${phone_msg}"/>
            <c:out value="${phone_icon}" escapeXml="false"/>
            <span id="usernameStatus" class="help-block"><form:errors
                    path="contactPhoneNo"/></span>
          </div>

        </div>
      </spring:bind>
      <spring:bind path="additionalInfo">
        <spring:message var="addInfo_msg" code="order.userInfo.addInfo"/>
        <div class="form-group <c:out value="${addInfo_errorClass}"/>">
          <div class="col-sm-7">
            <form:textarea path="additionalInfo" cssClass="form-control"
                           id="add_info" placeholder="${addInfo_msg}"/>
            <c:out value="${addInfo_icon}" escapeXml="false"/>
            <span id="usernameStatus" class="help-block"><form:errors
                    path="additionalInfo"/></span>
          </div>
        </div>
      </spring:bind>
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
