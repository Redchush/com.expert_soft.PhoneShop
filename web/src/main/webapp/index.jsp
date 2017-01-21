<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>Hello World!</h2>
<a href="${pageContext.servletContext.contextPath}/products">Go to phones</a>
<a href="<c:url value="/products"/>">Go to phones</a>
</body>
</html>
