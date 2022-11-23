<%@include file="/common/taglib.jsp"%>
<c:url var="listURL" value="/admin/contact/list" />
<c:url var="deleteURL" value="/admin/contact/delete" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Danh sách liên hệ</title>
</head>

<body>

	<div class="main-content">
	
		<form:form action="${listURL}" id="formSubmit"
			method="get">
			
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="<c:url value="/admin/home"/>">Trang
								chủ</a></li>
					</ul>
					<!-- /.breadcrumb -->
				</div>
				<div class="page-content">
				<div class="page-header"> <h2 class="tm-block-title d-inline-block">Danh sách liên hệ</h2></div>
				<!-- /.page-header -->
				
				<div class="row">
					<div class="row">
						<div class="col-xs-12">
						<c:if test="${not empty message}">
							<div class="alert alert-${alert}">${message}</div>
						</c:if> 
					
							
							<div class="row">
								<div class="col-xs-12">
									
									<section class="mb-4" >

    <!--Section heading-->
    <h2 class="h1-responsive font-weight-bold text-center my-4">Liên hệ</h2>
    
   

    <div class="row" style="padding: 133px;" >

        <!--Grid column-->
        <div class="col-md-12">
        <c:url var="createUrl" value="/home/contact" />
            <form:form modelAttribute="model" id="contact-form" name="contact-form" action="${createUrl}" method="POST">

                <!--Grid row-->
                <div class="row">

                    <!--Grid column-->
                    <div class="col-md-6">
                        <div class="md-form mb-0">
                            <form:input type="text" path="name" cssClass="form-control"/>
                            <label for="name" class="">Họ và tên</label>
                        </div>
                    </div>
                    <!--Grid column-->

                    <!--Grid column-->
                    <div class="col-md-6">
                        <div class="md-form mb-0">
                        <form:input type="text" path="email" cssClass="form-control"/>
                           
                            <label for="email" class="">Email</label>
                        </div>
                    </div>
                    <!--Grid column-->

                </div>
                <!--Grid row-->

                <!--Grid row-->
                <div class="row">
                    <div class="col-md-12">
                        <div class="md-form mb-0">
                        <form:input type="text" path="title" cssClass="form-control"/>
                           
                            <label for="subject" class="">Tiêu đề</label>
                        </div>
                    </div>
                </div>
                <!--Grid row-->

                <!--Grid row-->
                <div class="row">

                    <!--Grid column-->
                    <div class="col-md-12">

                        <div class="md-form">
                        <form:textarea rows="2" type="text" path="content" cssClass="form-control md-textarea"/>
                            
                            <label for="message">Nội dung</label>
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
						</div>
					</div>
				</div>
			</div>
			</div>
		</form:form>
	</div>
	
	<!-- /.main-content -->

</body>

</html>