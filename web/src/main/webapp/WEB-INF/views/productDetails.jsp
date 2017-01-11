<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Phonify product details</title>
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
    <button class="btn btn-default pnf" type="button">Back to product list
    </button>
    <div class="col-lg-12 pnf">
      <h1>This phone model</h1>
    </div>

    <table class="table table-striped fit">
      <tbody>
      <tr>
        <td>Display</td>
        <td>4'</td>
      </tr>
      <tr>
        <td>Length</td>
        <td>val</td>
      </tr>
      <tr>
        <td>Width</td>
        <td>val</td>
      </tr>
      <tr>
        <td>Color</td>
        <td>val</td>
      </tr>
      <tr>
        <td>Price</td>
        <td>val</td>
      </tr>

      <tr>
        <td>Camera</td>
        <td>val</td>
      </tr>
      </tbody>
    </table>
    <form>
      <input type="text" class="input-sm" value="1">
      <button class="btn btn-default btn-sm pnf" type="button">Add to cart
      </button>
    </form>
  </div>
</div>

<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>

</body>

</html>

