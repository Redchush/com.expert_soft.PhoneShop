<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="indexUrl" value="/index.jsp"/>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="/WEB-INF/views/part/head_template.jsp"/>
<body>

<c:url value=""/>
<nav class="navbar navbar-default">
  <div class="container">
    <a class="navbar-brand" href="${indexUrl}">
      <span class="glyphicon glyphicon-phone"></span>Phonify
    </a>
    <div class="pull-right">
      <%@ include file="part/button/backMainBtn.jsp" %>
      <%@ include file="part/button/logoutBtn.jsp"%>
    </div>
    <div class="clearfix"></div>
  </div>
</nav>

<div class="container admin_page">
  <div class="row">
    <div class="col-lg-12 pnf">
      <h1><spring:message code="admin.header"/></h1>
      <h5><spring:message code="admin.greeting"
                          arguments="${pageContext.request.userPrincipal.name}"/>
      </h5>
    </div>
  </div>

  <div class="row">
    <table class="table table-striped">
      <thead class="pnf">
      <tr>
        <th><spring:message code="common.key"/></th>
        <th><spring:message code="order.userInfo.firstName"/></th>
        <th><spring:message code="order.userInfo.lastName"/></th>
        <th><spring:message code="order.userInfo.address"/></th>
        <th><spring:message code="order.userInfo.contactPhoneNo"/></th>
        <th><spring:message code="order.deliveryPrice"/></th>
        <th><spring:message code="order.total"/></th>
        <th><spring:message code="order.status"/></th>
        <th><spring:message code="common.action"/></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="order" items="${orders}">
        <tr>
          <td>${order.key}</td>
          <td>${order.userInfo.firstName}</td>
          <td>${order.userInfo.lastName}</td>
          <td>${order.userInfo.deliveryAddress}</td>
          <td>${order.userInfo.contactPhoneNo}</td>
          <td>${order.deliveryPrice}</td>
          <td>${order.totalPrice}</td>
          <td>
            <c:set var="formId" value="update_${order.key}"/>
            <c:url var="formUrl" value="/update_order"/>

            <c:set var="isNew" value=""/>
            <c:set var="isDelivered" value=""/>

            <c:if test="${order.status == 'NEW'}">
              <c:set var="isNew" value="selected"/>
            </c:if>
            <c:if test="${order.status == 'DELIVERED'}">
              <c:set var="isDelivered" value="selected"/>
            </c:if>

            <form id="${formId}" action="${formUrl}" method="post">
              <input type="hidden" name="${_csrf.parameterName}"
                     value="${_csrf.token}" />
              <input type="hidden" name="orderKey" value="${order.key}">
              <div class="form-group">
                <select class="form-control xs" id="sel1" name="status">
                  <option ${isNew}><spring:message code="order.status.new"/></option>
                  <option ${isDelivered}><spring:message code="order.status.delivered"/></option>
                </select>
              </div>
            </form>
          </td>
          <td>
            <c:url var="seeOrderUrl" value="/order/${order.key}"/>
            <a href="${seeOrderUrl}">
              <button class="btn btn-info btn-xs" type="button">
                <spring:message code="common.see"/>
              </button>
            </a>
            <br>
            <button form="${formId}" class="btn btn-primary btn-xs pnf" type="submit">
              <spring:message code="order.action.update_st"/>
            </button>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <c:if test="${not empty tempMsg}">
      <div class="alert alert-success fade in">
          ${tempMsg}
      </div>
    </c:if>
  </div>
</div>
</body>
</html>

