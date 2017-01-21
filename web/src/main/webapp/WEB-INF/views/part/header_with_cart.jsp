<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-default">
  <div class="container">
    <a class="navbar-brand" href="<c:url value="/index.jsp"/>"><span class="glyphicon glyphicon-phone"></span>Phonify</a>
    <a href="<c:url value="/cart"/>">
      <button id="cart_info" class="btn btn-default pnf pull-right">
      <c:choose>
        <c:when test="${empty sessionScope.cartCurriculum}">
          <spring:message code="button.clientCart" htmlEscape="false" arguments="0 0"/>
        </c:when>
        <c:otherwise>
          <fmt:formatNumber var="sub" value="${sessionScope.cartCurriculum.cartSubtotal}" type="currency" currencySymbol=""/><spring:message
                code="button.clientCart" htmlEscape="false"
                arguments="${sessionScope.cartCurriculum.cartSize} ${sub}"/>
        </c:otherwise>
      </c:choose></button>
     </a>
    <div class="clearfix"></div>
  </div>
</nav>

<%----%>
<%--My cart:<strong><span data-out="size"><c:out--%>
<%--value="${sessionScope.cartCurriculum.cartSize}"/></span></strong>items--%>
<%--<strong><span data-out="subtotal"><fmt:formatNumber--%>
<%--value="${sessionScope.cartCurriculum.cartSubtotal}" type="currency" currencySymbol="$"/></span> </strong>--%>