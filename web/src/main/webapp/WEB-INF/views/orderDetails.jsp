<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--use phf class to change default bootstrap css-->
<!DOCTYPE html>
<html lang="en">
<jsp:include page="/WEB-INF/views/part/head_template.jsp">
  <jsp:param name="head_title" value="Phonify order confirm"/>
</jsp:include>
<body>

<c:import url="/WEB-INF/views/part/header/header_without_cart.jsp"/>

<spring:message var="header_msg" code="order.confirm.thanks"/>
<sec:authorize access="hasRole('ROLE_ADMIN')">
  <spring:message var="header_msg" code="order.details" arguments="${order.key}"/>
</sec:authorize>

<div class="container">
  <div class="row">
    <div class="col-lg-12 pnf">
      <h1>${header_msg}</h1>
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
        <td>
          <fmt:formatNumber value="${order.subtotal}" type="currency" currencySymbol="$"/>
        </td>
      </tr>
      <tr>
        <td colspan="3"></td>
        <td><spring:message code="order.deliveryPrice"/></td>
        <td>
          <fmt:formatNumber value="${order.deliveryPrice}" type="currency" currencySymbol="$"/>
        </td>
      </tr>
      <tr>
        <td colspan="3"></td>
        <td><spring:message code="order.total"/> </td>
        <td>
          <fmt:formatNumber value="${requestScope.order.totalPrice}" type="currency" currencySymbol="$"/>
        </td>
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
        <div class="col-sm-5">
          <c:out value="${order.userInfo.lastName}" escapeXml="true"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2"><spring:message code="order.userInfo.address"/></label>
        <div class="col-sm-5">
          <c:out value="${order.userInfo.deliveryAddress}" escapeXml="true"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2"><spring:message code="order.userInfo.contactPhoneNo"/></label>
        <div class="col-sm-5">
          <c:out value="${order.userInfo.contactPhoneNo}" escapeXml="true"/>
        </div>
      </div>
      <div class="form-group">
        <div class="col-sm-7">
          <c:out value="${order.userInfo.additionalInfo}" escapeXml="true"/>
        </div>
      </div>
      <div class="form-group">
        <div class="col-sm-3">
          <a href="<c:url value="/products"/>">
            <button type="submit" class="btn btn-default btn-block pnf">
              <spring:message code="button.backToShopping"/>
            </button>
          </a>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
