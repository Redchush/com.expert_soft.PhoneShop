<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--use phf class to change default bootstrap css-->
<!DOCTYPE html>
<html lang="en">
<spring:message var="this_title" code="products.title"/>
<jsp:include page="/WEB-INF/views/part/head_template.jsp">
  <jsp:param name="head_title" value="${this_title}"/>
</jsp:include>

<body>
<c:import url="/WEB-INF/views/part/header/header_with_cart.jsp"/>
<div class="container">
  <div class="row">
    <div class="col-lg-12 pnf">
      <h1><spring:message code="products.header"/></h1>
    </div>
    <table class="table table-striped">
      <%@ include file="part/table/cart_thead.jsp" %>
      <tbody>
      <c:forEach var="phone" items="${phones}">
        <c:set var="fromId" value="addToCartForm${phone.key}"/>
        <c:url var="phoneDetailsUrl" value="/products/${phone.key}"/>
        <tr>
          <td>
            <a href="${phoneDetailsUrl}">
              <span data-save="${phone.key}">${phone.model}</span>
            </a>
          </td>
          <td>${phone.color}</td>
          <td><fmt:formatNumber value="${phone.displaySize/10}" type="number"
                                maxFractionDigits="1"/>''</td>
          <td><fmt:formatNumber value="${phone.price}" type="currency"
                                currencySymbol="$"/></td>
          <td>
            <form id="${fromId}" name="doAddToCartForm">
              <div class="form-group-sm">
                <input name="quantity" class="form-control" value="1" title="quantity">
              </div>
              <input name="phoneId" type="hidden" value="${phone.key}">
            </form>
          </td>
          <td>
            <button form="${fromId}" class="btn btn-default btn-sm pnf" type="submit">
              <spring:message code="button.addToCart"/>
            </button>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <output id="msg">
      <spring:message code="${requestScope.msgCode}" text=" "/>
    </output>
  </div>
</div>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/main.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>
</body>
</html>
