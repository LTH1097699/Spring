<%@ page import="com.springbook.utils.SecurityUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="home" value="/home"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang chủ</title>

</head>
<body>
<div class="row">

	<div class="col-md-12">

		<!--Section: Contact v.2-->
<section class="mb-4" >

    <!--Section heading-->
    <h2 class="h1-responsive font-weight-bold text-center my-4">Liên hệ</h2>
    
   

    <div class="row" style="padding: 133px;" >

        <!--Grid column-->
        <div class="col-md-12">
        <c:if test="${not empty message}">
				<div class="alert alert-${alert}">${message}</div>
		</c:if>
        <c:url var="createUrl" value="/home/contact" />
            <form:form modelAttribute="model" id="contact-form" name="contact-form" action="${createUrl}" method="POST">
            	<!-- ANONYMOUS -->
				<security:authorize access="isAnonymous()">
					       <!--Grid row-->
                <div class="row">

                    <!--Grid column-->
                    <div class="col-md-6">
                        <div class="md-form mb-0">
                         <label for="name" class="">Họ và tên</label>
                        
                        <form:input type="text" path="name" cssClass="form-control"/>
                       <form:errors path="name" cssClass="error" cssStyle="color:red;"/>
                            
                        </div>
                    </div>
                  
                    <div class="col-md-6">
                        <div class="md-form mb-0">
                         <label for="email" class="">Email</label>
                        <form:input path="email" cssClass="form-control"/>
                           <form:errors path="email" cssClass="error" cssStyle="color:red;"/>
                           
                        </div>
                    </div>
                </div>
				</security:authorize>
         		
         		<!-- AUTHENTICATED -->
         		<security:authorize access="isAuthenticated()">
					       <!--Grid row-->
                <div class="row">

                    <!--Grid column-->
                    <div class="col-md-6">
                        <div class="md-form mb-0">
                         <label for="name" class="">Họ và tên</label>                
                        <form:input value="${user.fullName}" path="name" cssClass="form-control" readonly="true"/>  
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="md-form mb-0">
                         <label for="email" class="">Email</label>
                        <form:input  value="${user.userName}" path="email" cssClass="form-control" readonly="true"/>                
                        </div>
                    </div>
                </div>
                <!--Grid row-->
				</security:authorize>

                <!--Grid row-->
                <div class="row">
                    <div class="col-md-12">
                        <div class="md-form mb-0">
                          <label for="subject" class="">Tiêu đề</label>
                       
                        <form:input type="text" path="title" cssClass="form-control"/>
                            <form:errors path="title" cssClass="error" cssStyle="color:red;"/>
                          
                        </div>
                    </div>
                </div>
                <!--Grid row-->

                <!--Grid row-->
                <div class="row">

                    <!--Grid column-->
                    <div class="col-md-12">

                        <div class="md-form">
                            <label for="content">Nội dung</label>
                        <form:textarea rows="2" type="text" path="content" cssClass="form-control md-textarea"/>
                           <form:errors path="content" cssClass="error" cssStyle="color:red;"/>      
                        </div>

                    </div>
                </div>
                <!--Grid row-->

            </form:form>

            <div class=" text-center text-md-right">
                <a class="btn btn-primary text-white col-md-3" onclick="document.getElementById('contact-form').submit();">Gửi</a>
            </div>
            <div class="status"></div>
        </div>
        <!--Grid column-->
    </div>
</section>
		
	</div>
</div>

</body>
</html>