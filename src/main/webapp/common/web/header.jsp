<%@ page import="com.springbook.utils.SecurityUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<div class="container">
		<ul class="navbar-nav ml-auto">

			<security:authorize access="isAnonymous()">
				<li class="nav-item"><a class="nav-link"
					href='<c:url value="/login"/>'>Đăng nhập</a></li>
				<li class="nav-item"><a class="nav-link"
					href='<c:url value="/sign-in"/>'>Đăng Ký</a></li>
			</security:authorize>

			<security:authorize access="isAuthenticated()">
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>

				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav mr-auto">


						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> Xin chào, <%=SecurityUtils.getPrincipal().getFullName()%>
						</a>
							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
								<a class="dropdown-item" href="<c:url value="/home/account"/>">Tài
									khoản</a> <a class="dropdown-item" href="<c:url value="/home/cart/list" />"><span
									class="bi bi-cart"></span>Giỏ hàng</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href='<c:url value="/logout"/>'>Đăng xuất</a>
							</div></li>
					</ul>
				</div>
			</security:authorize>
		</ul>
	</div>
</nav>

<nav class="navbar navbar-expand-lg navbar-light"
	style="background-color: #e3f2fd;">
	<!-- Navbar content -->

	<div class="container">
		<div class="navbar-collapse collapse" id="collapsingNavbar">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link"
					style="padding: 0;" href="<c:url value="/home/book"/>"><h5
							style="font-size: 1.1rem;color: black;" class="font-weight-bold">Trang
							chủ</h5>  </a></li>
				<li class="nav-item"><a class="nav-link"
					style="padding: 0;" href="<c:url value="/home/contact"/>"><h5
							style="font-size: 1.1rem;color: black;" class="font-weight-bold">Liên hệ</h5>  </a></li>
			</ul>
			
			
			<ul class="navbar-nav ml-auto">
			
				<li class="nav-item"><a class="nav-link"
					style="padding-top: 7px;" href="<c:url value="/home/cart/list"/>"><h5 
							style="font-size: 1.1rem; color: black;" >
							
							<i class="fa fa-shopping-cart"></i>
							Giỏ hàng</h5>
						 </a></li>
				
			</ul>
			
			
		</div>

	</div>


</nav>
<nav class="navbar navbar-expand-lg navbar-light bg-light"></nav>