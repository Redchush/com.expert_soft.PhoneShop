<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="/WEB-INF/views/part/head_template.jsp"/>
<body>
<div class="container order_page">
  <div class="row">
    <c:choose>
      <c:when test="${pageContext.request.userPrincipal.name != null}">
        <h2><spring:message code="admin.greeting"
                            arguments="${pageContext.request.userPrincipal.name}"/>
        </h2>
        <p>
          <%@ include file="WEB-INF/views/part/button/logoutBtn.jsp"%>
          <jsp:include page="WEB-INF/views/part/button/defaultBtn.jsp">
            <jsp:param name="button_url" value="/admin"/>
            <jsp:param name="button_code" value="button.goAdmin"/>
          </jsp:include>
        </p>
      </c:when>

      <c:otherwise>
        <h2>
          <spring:message code="admin.greeting" arguments="Client"/>
        </h2>
        <%@ include file="WEB-INF/views/part/button/loginBtn.jsp"%>
        <jsp:include page="WEB-INF/views/part/button/defaultBtn.jsp">
          <jsp:param name="button_url" value="/products"/>
          <jsp:param name="button_code" value="button.goShopping"/>
        </jsp:include>
      </c:otherwise>
    </c:choose>
  </div>
</div>

</body>
</html>
