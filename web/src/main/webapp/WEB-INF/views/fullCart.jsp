<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<c:set var="sessCartItems" value="${sessionScope.cart.orderItems}" />

<span id="restore_msg" class="holder"><spring:message code="button.restore"/></span>
<span id="delete_msg" class="holder"><spring:message code="button.delete"/></span>

<style>
  div.form-group-sm div{
    display: none;
  }
  div.form-group-sm.has-error div{
    display: block;
  }
  div.form-group-sm.has-error div .help-block{
    font-size:12px;
    margin-bottom: 0;
  }
</style>

<c:url var="orderUrl" value="/order"/>

<div class="container">
  <div class="row">
    <div class="col-lg-12 pnf">
      <h1>Cart</h1>
      <c:if test="${not empty requestScope.msg}">
        <h5>${requestScope.msg}</h5>
      </c:if>
    </div>
    <p>
      <%@ include file="part/button/backMainBtn.jsp" %>
      <a href="${orderUrl}">
        <button class="btn btn-default pnf" type="submit" value="order">
          <spring:message code="button.order"/>
        </button>
      </a>
    </p>
    <spring:url value="/update_cart" var="userActionUrl" />
    <form:form class="form-inline" modelAttribute="cartItems"
               method="post" action="${userActionUrl}">
      <table class="table table-striped">
        <%@ include file="part/table/cart_thead.jsp" %>
        <tbody>
        <c:forEach var="item" items="${sessCartItems}" varStatus="iterStatus">
          <fmt:formatNumber var="currentDisplay" value="${(item.phone.displaySize)/10}"
                            maxFractionDigits="1"/>
          <fmt:formatNumber var="currentPrice" value="${item.phone.price}" type="currency"
                            currencySymbol="$"/>
          <tr>
            <td><c:out value="${item.phone.model}"/></td>
            <td><c:out value="${item.phone.color}"/> </td>
            <td><c:out value="${currentDisplay}''"/></td>
            <td><c:out value="${currentPrice}"/></td>
            <td>
              <c:set var="ind" value="${iterStatus.index}"/>
              <spring:bind path="items[${ind}].quantity">
                <c:set var = "currentValue" value="${status.error ?  status.value : item.quantity}"/>
                <div class="form-group-sm ${status.error ? 'has-error' : ''}">
                  <form:input path="items[${ind}].phone.key" cssClass="hidden"
                              value="${item.phone.key}"/>
                  <form:input path="items[${ind}].quantity" class="form-control"
                              value="${currentValue}" title="quantity"/>
                  <div><form:errors path="items[${ind}].quantity" cssClass="help-block"
                                    class="control-label" /></div>
                </div>
              </spring:bind>
            </td>
            <td>
              <label class="btn btn-default btn-sm pnf" data-action = "delete">
                <input type="checkbox" name="deleteId" value="${item.phone.key}" autocomplete="off"
                       hidden>
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
    <output id="msg"><c:out value="${tempMsg}"/></output>
  </div>
</div>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>
<script src="<c:url value="/resources/js/cart.js"/>"></script>
</body>
</html>
