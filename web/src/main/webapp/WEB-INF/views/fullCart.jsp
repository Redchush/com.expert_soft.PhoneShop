<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
  <title><spring:message code="cart.title"/></title>

  <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
  <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
</head>

<body>
<c:import url="/WEB-INF/views/part/header_with_cart.jsp"/>
<c:set var="cartItems" value="${reqCart.itemsMap}" />

<span id="restore_msg" class="holder"><spring:message code="button.restore"/></span>
<span id="delete_msg" class="holder"><spring:message code="button.delete"/></span>
<span id="delete_msg" class="holder"><spring:message code="button.delete"/></span>


<div class="container">
  <div class="row">
    <div class="col-lg-12 pnf">
      <h1>Cart</h1>
      <c:if test="${not empty requestScope.msg}">
        <h5>${requestScope.msg}</h5>
      </c:if>
    </div>
    <p>
      <a href="<c:url value="/products"/> ">
        <button class="btn btn-default pnf" type="button"><spring:message
                code="button.backToMain"/></button>
      </a>
      <a href="<c:url value="/order"/> ">
        <button class="btn btn-default pnf" type="submit" value="order"><spring:message
                code="button.order"/></button>
      </a>
    </p>

    <spring:url value="/update" var="userActionUrl" />

    <form:form class="form-inline"
               modelAttribute="cartItems" method="post" action="${userActionUrl}">
      <table class="table table-striped">
        <%@ include file="part/product/product_thead.jsp" %>
        <tbody>
        <c:forEach var="itemEntry" items="${cartItems}" varStatus="index">

          <c:set var="phone" value="${itemEntry.value.phone}" scope="page"/>
          <fmt:formatNumber var="currentDisplay" value="${(phone.displaySize)/10}"
                            maxFractionDigits="1"/>
          <fmt:formatNumber var="currentPrice" value="${phone.price}" type="currency"
                            currencySymbol="$"/>
          <tr>
            <td><c:out value="${phone.model}"/></td>
            <td><c:out value="${phone.color}"/> </td>
            <td><c:out value="${currentDisplay}''"/></td>
            <td><c:out value="${currentPrice}"/></td>
          <td>
            <spring:bind path="[${index}].quantity">
              <form:input path="[${index}]" class="input-sm"
                          value="${itemEntry.value.quantity}" title="quantity"/>
              <form:errors path="[${index}].quantity" class="control-label" />

            </spring:bind>
          </td>
            <td>
              <label class="btn btn-default btn-sm pnf" data-action = "delete">
                <input type="checkbox" name="deleteId" value="${phone.key}" autocomplete="off"
                       checked="false" hidden>
                <span><spring:message code="button.delete"/></span>
              </label>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <p class="pull-right">
        <a href="<c:url value="/products"/> ">
          <button class="btn btn-default pnf" type="submit"><spring:message
                  code="button.update"/> </button>
        </a>
      </p>
    </form:form>

  </div>
</div>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>
<script src="<c:url value="/resources/js/cart.js"/>"></script>
</body>
</html>
