<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--use phf class to change default bootstrap css-->
<!DOCTYPE html>
<html lang="en">

<c:set var="this_title" value="Phonify ${phone.model} details"/>
<jsp:include page="/WEB-INF/views/part/head_template.jsp">
  <jsp:param name="head_title" value="${this_title}"/>
</jsp:include>

<body>
<style>
  tfoot > tr> td.frame {
    padding: 15px 0;
  }
  .form-group-sm{
    margin-bottom:15px;
  }
</style>

<c:import url="/WEB-INF/views/part/header/header_with_cart.jsp"/>
<div class="container productDetails_page">
  <div class="row">
    <%@ include file="part/button/backMainBtn.jsp" %>
    <div class="col-lg-12 pnf">
      <h1>
        <span data-save="${phone.key}"><c:out value="${phone.model}"/></span>
      </h1>
    </div>
    <table class="table table-striped fit">
      <tbody>
      <tr>
        <td><spring:message code="product.displaySize"/></td>
        <td>
          <fmt:formatNumber value="${phone.displaySize/10}" maxFractionDigits="1"/>
          <spring:message code="product.displaySize.dimension"/>
        </td>
      </tr>
      <tr>
        <td><spring:message code="product.length"/></td>
        <td>
          <c:out value="${phone.length}" default="unknown" escapeXml="true"/>
          <c:if test="${not empty phone.length}">
            <spring:message code="product.dimension.mm"/>
          </c:if>
        </td>
      </tr>
      <tr>
        <td><spring:message code="product.width"/></td>
        <td>
          <c:out value="${requestScope.phone.width}" default="unknown" escapeXml="true"/>
          <c:if test="${not empty requestScope.phone.length}"><spring:message
                  code="product.dimension.mm"/></c:if>
        </td>
      </tr>
      <tr>
        <td><spring:message code="product.color"/></td>
        <td>
          <c:out value="${requestScope.phone.color}" escapeXml="true"/>
        </td>
      </tr>
      <tr>
        <td><spring:message code="product.price"/></td>
        <td>
          <fmt:formatNumber value="${requestScope.phone.price}"
                            type="currency" currencySymbol="$"/>
        </td>
      </tr>
      <tr>
        <td><spring:message code="product.camera"/></td>
        <td>
          <c:out value="${requestScope.phone.camera}" default="unknown"
                 escapeXml="true"/>
          <c:if test="${not empty requestScope.phone.length}">
            <spring:message code="product.camera.dimension"/>
          </c:if>
        </td>
      </tr>
      </tbody>
      <tfoot class="emptyCelled">
      <tr>
        <td colspan="2" class="frame">
          <c:url var="formUrl" value="/add_to_cart" />
          <c:set var="formId" value="addToCartForm${phone.key}"/>
          <form action="${formUrl}" id="${formId}" name="doAddToCartForm">
            <div class="form-group-sm">
              <input name="quantity" class="form-control" value="1" title="quantity">
            </div>
            <input name="phoneId" type="hidden" value="${phone.key}">
            <button class="btn btn-default btn-sm pnf" type="submit">
              <spring:message code="button.addToCart"/>
            </button>
          </form>
        </td>
      </tr>
      </tfoot>
    </table>
    <output id="msg"></output>
  </div>
</div>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/main.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>

</body>
</html>

