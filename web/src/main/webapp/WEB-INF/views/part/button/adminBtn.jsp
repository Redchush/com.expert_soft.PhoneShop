<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="admin_btn" value="/admin"/>
<a class="inline" href="${admin_btn}">
  <button class="btn btn-default pnf" type="button">
    <spring:message code="button.goAdmin"/>
  </button>
</a>