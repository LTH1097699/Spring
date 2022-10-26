<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Đăng Kí</title>
</head>
<body>
	<div class="container">
	<c:if test="${not empty message}">
					<div class ="alert alert-danger">
						${message}
					</div>
					</c:if>
	<c:url var="saveURL" value="/sign-in"/>
	<form:form action="${saveURL}" id="formLogin" modelAttribute="model" method="post">
		<section class="vh-100 gradient-custom">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-md-9 col-lg-5 col-xl-5">
        <div class="card bg-dark text-white" style="border-radius: 1rem;">
          <div class="card-body p-5 text-center">
          
			<div class="row">
			<div class="col-md-12">
			       
				<h2 class="fw-bold mb-2 text-uppercase">Đăng kí</h2>
              
              

              <div class="form-outline form-white mb-4">
               <label class="form-label" for="typeEmailX">Email</label>
              	<form:input  type="text"  cssClass="form-control form-control-lg" path="userName" placeholder="Email"/>
               <form:errors path="userName" cssClass="error" cssStyle="color:red;" />
             <!--   oninvalid="this.setCustomValidity('Tên tài khoản thiếu @')" onchange="this.setCustomValidity('')" -->
              </div>
				
			<div class="form-outline form-white mb-4">
               <label class="form-label" for="typeEmailX">Email</label>
              	<form:input  cssClass="form-control form-control-lg" path="fullName" placeholder="Họ và tên"/>
               <form:errors path="fullName" cssClass="error" cssStyle="color:red;" />
               
              </div>
				
              <div class="form-outline form-white mb-4">
              <label class="form-label" for="typePasswordX">Mật khẩu</label>
              <form:password  cssClass="form-control form-control-lg" path="password" placeholder="Mật khẩu"/>
               <form:errors path="password" cssClass="error" cssStyle="color:red;" />
                
               
              </div>
                <div class="form-outline form-white mb-4">
                <label class="form-label" for="typePasswordX">Nhập lại mật khẩu</label>
              <form:password  cssClass="form-control form-control-lg" path="confirmPassword" placeholder="Nhập lại mật khẩu"/>
               <form:errors path="confirmPassword" cssClass="text-red" cssStyle="color:red;" />
                
               
              </div>

              <button name="action" value="350" class="btn btn-outline-light btn-lg px-5" type="submit">Đăng kí</button>

              <div class="d-flex justify-content-center text-center mt-4 pt-1">
                <a href="#!" class="text-white"><i class="fab fa-facebook-f fa-lg"></i></a>
                <a href="#!" class="text-white"><i class="fab fa-twitter fa-lg mx-4 px-2"></i></a>
               
              </div>
            <div>
              <p class="mb-0">Có tài khoản <a href="<c:url value='/login' />" class="text-white-50 fw-bold">Đăng nhập</a>
              </p>
            </div>
			</div>
			</div>
    

          </div>
        </div>
      </div>
    </div>
  </div>
</section>
	</form:form>
	</div>
</body>
</html>