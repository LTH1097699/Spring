<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang chủ</title>
</head>
<body>
<div class="mt-4"></div>
	<div class="row">
		
		<div class="col-lg-3">

			<div class="list-group">
					<a href="<c:url value='/home/account'/>" class="list-group-item">Thông tin người dùng</a>
					<a href="<c:url value='/home/account/order'/>" class="list-group-item">Đơn hàng</a>				
			</div>
			<div class="list-group">
					<a href="<c:url value='/home/account/details'/>" class="list-group-item">Thông tin tài khoản</a>
					<a href="<c:url value ="/home/account/address"/>" class="list-group-item">Thông tin địa chỉ</a>	
			</div>


		</div>
		<!-- /.col-lg-3 -->
		<div class="col-lg-9">
		<h1 class="my-4 font-weight-normal">Thông tin tài khoản</h1>
		
		
			<c:if test="${not empty message}">
									<div class="alert alert-${alert}">${message}</div>
								</c:if>
		<c:url var="updateURL" value="/home/account/details" />
		<form:form action="${updateURL}" id="formSubmit" method="post" modelAttribute="model">
		<form:hidden path="id"/>
		<form:hidden path="roleCode"/>
		
				<h4 class="my-5 font-weight-normal border-bottom" style="margin-bottom: 1rem !important;">Thông tin tài khoản</h4>
				<div class="row">
						<div class="col-md-6">
						
						
						<h6 class="font-weight-normal">Họ và tên</h6>
						
						<form:input autocomplete="false" path="fullNameValidator"/>
						<form:errors path="fullNameValidator" style="color:red;" cssClass="error" />
						</div>
						<div class="col-md-6">
						<h6 class="font-weight-normal">Email</h6>
						<h6 class="font-weight-normal">${model.userName}</h6>
						<form:hidden path="userName"/>
						</div>
				</div>
				<div class="row">
				<div class="col-md-3">
				<button value="100" name="action" class="btn btn-primary" type="submit">Cập nhật</button>
				</div>
						
				</div>
				<br>
				
				<h4 class="my-5 font-weight-normal border-bottom" style="margin-bottom: 1rem !important;">Đổi mật khẩu</h4>
				<div class="row">
						<div class="col-md-6">
						
						
						<h6 class="font-weight-normal">Nhập mật khẩu mới </h6>
						<form:password path="password"/>
						<form:errors path="password" style="color:red;" cssClass="error" />
						
						</div>
						<div class="col-md-6">
						<h6 class="font-weight-normal">Nhập lại mật khẩu</h6>
						<form:password path="confirmPassword"/>
						<form:errors path="confirmPassword" style="color:red;" cssClass="error" />
						</div>
				</div>
				<!-- /.row -->
				<div class="row">
				<div class="col-md-3">
				<button value="200" name="action" class="btn btn-primary" type="submit">Cập nhật</button>
				</div>
						
				</div>
			
		</form:form>
		</div>
		<!-- /.col-lg-9 -->

	</div>
	

	
</body>
</html>