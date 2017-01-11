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

  <title>Phonify customer order</title>
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
      <h1>Order</h1>
    </div>
    <p>
      <button class="btn btn-default pnf" type="button">Back to product list</button>
    </p>

    <table class="table table-responsive">
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
      <tr>
        <td>Model</td>
        <td>Color</td>
        <td>Display size</td>
        <td>Price</td>
        <td>1</td>
        <td>200</td>
      </tr>
      <tr>
        <td>Model</td>
        <td>Color</td>
        <td>Display size</td>
        <td>Price</td>
        <td>1</td>
        <td>200</td>
      </tr>
      <tr>
        <td>Model</td>
        <td>Color</td>
        <td>Display size</td>
        <td>Price</td>
        <td>1</td>
        <td>200</td>
      </tr>
      </tbody>
      <tfoot class="emptyCelled">
      <tr>
        <td colspan="4"></td>
        <td>Subtotal</td>
        <td>750</td>
      </tr>
      <tr>
        <td colspan="4"></td>
        <td>Delivery</td>
        <td>750</td>
      </tr>
      <tr>
        <td colspan="4"></td>
        <td>TOTAL</td>
        <td>750</td>
      </tr>


      </tfoot>
    </table>

  </div>
  <div class="row">
    <form class="form-horizontal pnf">
      <div class="form-group">
        <label class="control-label col-sm-2" for="f_name">First name</label>
        <div class="col-sm-5">
          <input class="form-control" id="f_name" placeholder="First name">
        </div>
      </div>
      <div class="form-group">
        <label class="control-label col-xs-2" for="l_name">Last name</label>
        <div class="col-sm-5">
          <input class="form-control" id="l_name" placeholder="Last name">
        </div>
      </div>

      <div class="form-group">
        <label class="control-label col-sm-2" for="address">Address</label>
        <div class="col-sm-5">
          <input  class="form-control" id="address" placeholder="Address">
        </div>
      </div>
      <div class="form-group">
        <label class="control-label col-sm-2" for="phone">Phone</label>
        <div class="col-sm-5">
          <input class="form-control" id="phone" placeholder="Phone">
        </div>
      </div>

      <div class="form-group">
        <div class="col-sm-7">
          <textarea class="form-control" placeholder="Additional information" id="add_info"></textarea>
        </div>
      </div>

      <div class="form-group">
        <div class="col-sm-2">
          <button type="submit" class="btn btn-default btn-block pnf">Order</button>
        </div>
      </div>
    </form>
  </div>

</div>

<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script>

</body>
</html>
