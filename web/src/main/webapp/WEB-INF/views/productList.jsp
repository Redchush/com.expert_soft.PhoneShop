<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Phonify product list</title>
  <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
  <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">

</head>

<body>
<!--use phf class to change default bootstrap css-->
<nav class="navbar navbar-default">
  <div class="container">
    <a class="navbar-brand" href="index.html"><span class="glyphicon glyphicon-phone"></span>Phonify</a>
    <button class="btn btn-default pnf pull-right" type="button">My cart: 0 items 0$</button>
    <div class="clearfix"></div>
  </div>
</nav>
<!-- Page Content -->
<div class="container">
  <div class="row">
    <div class="col-lg-12 pnf">
      <h1>Phones</h1>
    </div>
    <form class="form-inline">
      <table class="table table-striped">
        <thead class="pnf">
        <tr>
          <th>Model</th>
          <th>Color</th>
          <th>Display size</th>
          <th>Price</th>
          <th>Quantity</th>
          <th>Action</th>
        </tr>
       </thead>
        <tbody>
          <c:forEach var="${request}" begin="1" end="5">
             Item <c:out value="${i}"/><p>
          </c:forEach>
        <tr>
          <td>Model</td>
          <td>Color</td>
          <td>Display size</td>
          <td>Price</td>
          <td><input type="text" class="input-sm" value="1" title="quantity"></td>
          <td>
            <button class="btn btn-default btn-sm pnf" type="button">Add to cart</button>
          </td>
        </tr>
        <tr>
          <td>Model</td>
          <td>Color</td>
          <td>Display size</td>
          <td>Price</td>
          <td><input type="text" class="input-sm" value="1" title="quantity"></td>
          <td> <button class="btn btn-default btn-sm pnf" type="button">Add to cart</button></td>
        </tr>
        <tr>
          <td>Model</td>
          <td>Color</td>
          <td>Display size</td>
          <td>Price</td>
          <td><input type="text" class="input-sm" value="1" title="quantity"></td>
          <td> <button class="btn btn-default btn-sm pnf" type="button">Add to cart</button></td>
        </tr>
        </tbody>
      </table>
    </form>
  </div>
</div>

<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>
</body>
</html>
