<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><dec:title default="Trang chủ" /></title>

<!-- css -->
<link
	href="<c:url value='/template/web/bootstrap/css/bootstrap.min.css' />"
	rel="stylesheet" type="text/css" media="all" />


<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script type="text/javascript"
	src="<c:url value='/template/web/jquery/jquery.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/template/web/bootstrap/js/bootstrap.bundle.min.js' />"></script>

<!-- page specific plugin scripts -->

<script
	src="<c:url value='/template/admin/assets/js/jquery-ui.min.js'/>"></script>

<script rel="stylesheet"
	src="<c:url value='/template/web/jquery.twbsPagination.js' />"></script>
	
<!-- sweet-alert 2 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.4.8/sweetalert2.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.4.8/sweetalert2.css">

<link href="<c:url value='/template/web/css/style.css' />"
	rel="stylesheet" type="text/css" media="all" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style type="text/css">
.product {
	position: inherit;
	width: 100%;
	height: 100%;
	border-radius: 4px;
	overflow: hidden
}

.product .imgbox {
	width: 100%;
	height: 100%;
	box-sizing: border-box
}

.specifies {
	position: absolute;
	width: 100%;
	bottom: -160px;
	/* background: #fff; */
	padding: 10px;
	box-sizing: border-box;
	transition: .5s
}

.product:hover .specifies {
	bottom: 0
}

.btn {
	display: block;
	padding: 5px;
	color: #fff;
	margin: 10px 0 0;
	width: 100%;
	font-size: 13px;
	border-radius: 2px
}
a.nav-link{
	padding: 0;
}


/* --------------------------------- */

.active {
  border-color: black !important;
  background-color: #f2f2f2;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.18), 0 3px 6px rgba(0, 0, 0, 0.23);
}

.card {
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15), 0 2px 5px rgba(0, 0, 0, 0.2);
  -webkit-transition: all 0.5s ease;
  -moz-transition: all 0.5s ease;
  -o-transition: all 0.5s ease;
  transition: all 0.5s ease;
} 

/* ------comment------------- */
.be-comment-block {
    margin-bottom: 50px !important;
    border: 1px solid #edeff2;
    border-radius: 2px;
    padding: 50px 70px;
    border:1px solid #ffffff;
}

.comments-title {
    font-size: 16px;
    color: #262626;
    margin-bottom: 15px;
    font-family: 'Conv_helveticaneuecyr-bold';
}

.be-img-comment {
    width: 60px;
    height: 60px;
    float: left;
    margin-bottom: 15px;
}

.be-ava-comment {
    width: 60px;
    height: 60px;
    border-radius: 50%;
}

.be-comment-content {
    margin-left: 80px;
}

.be-comment-content span {
    display: inline-block;
    width: 49%;
    margin-bottom: 15px;
}

.be-comment-name {
    font-size: 13px;
    font-family: 'Conv_helveticaneuecyr-bold';
}

.be-comment-content a {
    color: #383b43;
}

.be-comment-content span {
    display: inline-block;
    width: 49%;
    margin-bottom: 15px;
}

.be-comment-time {
    text-align: right;
}

.be-comment-time {
    font-size: 11px;
    color: #b4b7c1;
}

.be-comment-text {
    font-size: 13px;
    line-height: 18px;
    color: #7a8192;
    display: block;
    background: #f6f6f7;
    border: 1px solid #edeff2;
    padding: 15px 20px 20px 20px;
}

.form-group.fl_icon .icon {
    position: absolute;
    top: 1px;
    left: 16px;
    width: 48px;
    height: 48px;
    background: #f6f6f7;
    color: #b5b8c2;
    text-align: center;
    line-height: 50px;
    -webkit-border-top-left-radius: 2px;
    -webkit-border-bottom-left-radius: 2px;
    -moz-border-radius-topleft: 2px;
    -moz-border-radius-bottomleft: 2px;
    border-top-left-radius: 2px;
    border-bottom-left-radius: 2px;
}

.form-group .form-input {
    font-size: 13px;
    line-height: 50px;
    font-weight: 400;
    color: #b4b7c1;
    width: 100%;
    height: 50px;
    padding-left: 20px;
    padding-right: 20px;
    border: 1px solid #edeff2;
    border-radius: 3px;
}

.form-group.fl_icon .form-input {
    padding-left: 70px;
}

.form-group textarea.form-input {
    height: 150px;
}



.container .collapse-category,.collapse-author,.collapse-publisher{
    display: none;
    padding : 5px;
}



</style>



</head>
<body class="bg-light d-flex flex-column min-vh-100" style="padding-top: 0">
<div id="reload">
	<!-- header -->
	<%@ include file="/common/web/header.jsp"%>
	<!-- header -->

	<div class="container bg-white " style=" max-width: 1280px;">
		<dec:body />
	</div>

	<!-- footer -->
	<%@ include file="/common/web/footer.jsp"%>
	<!-- footer -->


	<dec:getProperty property="page.script">
	</dec:getProperty>




</div>
</body>
</html>