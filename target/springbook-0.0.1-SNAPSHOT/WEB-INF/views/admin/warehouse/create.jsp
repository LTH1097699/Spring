<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:url var="saveURL" value="/admin/warehouse/create" />
<html>
<head>
<title>Thêm mới Kho</title>
</head>
<body>
	<div class="main-content">
		<div class="main-content-inner">
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a
						href="<c:url value="/admin/home"/>">Trang chủ</a></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<div class="page-header">
					<h3>Thêm mới Kho</h3>
				</div>
				<!-- /.page-header -->
				<div class="row">

					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS FORM -->
						<form:form action="${saveURL}" class="form-horizontal"
							modelAttribute="model" method="post">
							<!-- 	BUTTON -->
							<div class="clearfix form-actions"
								style="margin-top: 0px; padding: 8px 20px 8px 20px; background-color: white; border-bottom: 1px solid #E5E5E5; border-top: 0 solid #E5E5E5;">
								<div class="col-md-offset-10 col-md-3 ">
									<a class="btn btn-danger text-white "
										href="<c:url value="/admin/tag-list?name=&page=1&limit=10" />">
										<i class="ace-icon fa fa-arrow-left icon-on-left"></i>Quay lại
									</a> &nbsp; &nbsp; &nbsp;
									<button class="btn btn-info " type="submit"
										id="btnAddOrUpdateBook">
										<i class="ace-icon fa fa-check bigger-110"></i> Thêm
									</button>

								</div>
							</div>

							<!-- Tên -->
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-1"> Mã kho</label>
								<div class="col-sm-9">
									<form:input path="code" cssClass="col-xs-10 col-sm-5" />
									<form:errors path="code" style="color:red;" cssClass="error" />
								</div>
							</div>
							<!-- hinh anh -->
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-1"> Tên kho </label>
								<div class="col-sm-9">
									<form:input path="name" cssClass="col-xs-10 col-sm-5" />
									<form:errors path="name" style="color:red;" cssClass="error" />
								</div>
							</div>

						</form:form>
					</div>
				</div>
				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.page-content -->
</body>
</html>
