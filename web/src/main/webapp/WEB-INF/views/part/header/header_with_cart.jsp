<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:url var="cartUrl" value="/cart"/>
<c:url var="indexUrl" value="/index.jsp"/>
<nav class="navbar navbar-default">
  <div class="container">
    <a class="navbar-brand" href="${indexUrl}">
      <span class="glyphicon glyphicon-phone"></span>
      Phonify
    </a>
    <div class="pull-right">
      <a class="inline" href="${cartUrl}">
        <button id="cart_info" class="btn btn-default pnf">
          <c:choose>
            <c:when test="${empty sessionScope.cart}">
              <spring:message code="button.clientCart" htmlEscape="false"
                              argumentSeparator=" " arguments="0 0"/>
            </c:when>
            <c:otherwise>
              <fmt:formatNumber var="sub" value="${sessionScope.cart.subtotal}"
                                type="currency" currencySymbol=""/>
              <spring:message argumentSeparator=" "
                              code="button.clientCart" htmlEscape="false"
                              arguments="${sessionScope.cart.totalPhonesCount} ${sub}"/>
            </c:otherwise>
          </c:choose></button>
      </a>
      <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="/WEB-INF/views/part/button/adminBtn.jsp" %>
        <%@ include file="/WEB-INF/views/part/button/logoutBtn.jsp"%>
      </sec:authorize>
    </div>
    <div class="clearfix"></div>
  </div>
</nav>

