<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="bootstrap_css" value="/resources/css/bootstrap.min.css"/>
<c:url var="custom_css" value="/resources/css/main.css"/>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <title><c:out value="${param.head_title}" default="phonify"/></title>
  <link href="${bootstrap_css}" rel="stylesheet">
  <link href="${custom_css}" rel="stylesheet">
</head>