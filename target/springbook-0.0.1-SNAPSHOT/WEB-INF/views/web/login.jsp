<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="loginURL" value="/user-check" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Đăng nhập</title>
</head>
<body>
	<div class="container">
	<c:if test="${not empty message}">
					<div class ="alert alert-${alert}">
						${message}
					</div>
					</c:if>
	<form action="j_spring_security_check" id="formLogin" method="post">
		<section class="vh-100 gradient-custom">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-md-9 col-lg-5 col-xl-5">
        <div class="card bg-dark text-white" style="border-radius: 1rem;">
          <div class="card-body p-5 text-center">
          
			<div class="row">
			<div class="col-md-12">
			<c:if test="${param.incorrectAccount != null}">
					<div class="alert alert-danger">	
							Email hoặc mật khẩu không đúng
					</div>
				</c:if>
				<c:if test="${param.accessDenied != null}">
					<div class="alert alert-danger">	
							Tài khoản không có quyền truy cập
					</div>
				</c:if>
			       
				<h2 class="fw-bold mb-2 text-uppercase">Đăng nhập</h2>
              
              

              <div class="form-outline form-white mb-4">
              <input type="text" class="form-control form-control-lg" id="userName"
							name="j_username" placeholder="Email">
            
              
                <label class="form-label" for="typeEmailX">Email</label>
              </div>

              <div class="form-outline form-white mb-4">
              <input type="password" class="form-control  form-control-lg" id="password"
							name="j_password" placeholder="Mật khẩu">
             
                <label class="form-label" for="typePasswordX">Mật khẩu</label>
              </div>

              <button class="btn btn-outline-light btn-lg px-5" type="submit">Đăng nhập</button>

              <div class="d-flex justify-content-center text-center mt-4 pt-1">
                <a href="#!" class="text-white"><i class="fab fa-facebook-f fa-lg"></i></a>
                <a href="#!" class="text-white"><i class="fab fa-twitter fa-lg mx-4 px-2"></i></a>
               
              </div>

            

            <div>
              <p class="mb-0">Chưa có tài khoản <a href="<c:url value='/sign-in' />" class="text-white-50 fw-bold">Đăng kí</a>
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
	</form>
	
	
		
				
				
	
	
	</div>
</body>
</html>