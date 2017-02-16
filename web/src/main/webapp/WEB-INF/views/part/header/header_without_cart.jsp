<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="indexUrl" value="/index.jsp"/>
<nav class="navbar navbar-default">
  <div class="container">
    <a class="navbar-brand" href="${indexUrl}">
      <span class="glyphicon glyphicon-phone"></span>Phonify</a>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
      <div class="pull-right">
        <%@ include file="/WEB-INF/views/part/button/adminBtn.jsp" %>
        <%@ include file="/WEB-INF/views/part/button/logoutBtn.jsp"%>
      </div>
    </sec:authorize>
  </div>
</nav>