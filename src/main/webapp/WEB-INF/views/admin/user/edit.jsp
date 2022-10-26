<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglib.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>

<head>
<title>Thông tin người dùng</title>
</head>

<body>
	<fmt:setLocale value="vi_VN" />
	<div class="main-content">
		<div class="main-content-inner">
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
                                try {
                                    ace.settings.check('breadcrumbs', 'fixed')
                                } catch (e) { }
                            </script>
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a
						href="<c:url value="/admin/home" />">Trang
							chủ</a></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<div class="page-header">Thông tin người dùng</div>
				<!-- /.page-header -->
				<div class="row">
					<div class="col-xs-12">
						<c:url var="updateURL" value="/admin/user/edit/${model.id}" />
						
						
						<form:form action="${updateURL}" class="form-horizontal"
							role="form" id="formSubmit" modelAttribute="model" method="POST">
							
							<input  id="getTab" name="getTab" value="${model.getTab}" type="hidden">
							<form:hidden path="id"/>
							<div class="col-sm-12">

								<!-- 	BUTTON -->
								<div class="clearfix form-actions"
									style="margin-top: 0px; padding: 8px 20px 8px 20px; background-color: white; border-bottom: 1px solid #E5E5E5; border-top: 0 solid #E5E5E5;">

									<div class="row pull-right">
										<a class="btn btn-danger text-white "
											href="<c:url value="/admin/user/list" />">
											<i class="ace-icon fa fa-arrow-left icon-on-left"></i>Quay
											lại
										</a> &nbsp; &nbsp; &nbsp;

									</div>
									
								</div>
								<c:if test="${not empty message}">
									<div class="alert alert-${alert}">${message}</div>
								</c:if>
								<div class="tabbable tabs-left">
									<ul class="nav nav-tabs" id="myTab3">
										<li id="#home3" class="active"><a data-toggle="tab" href="#home3"
											style="line-height: 45px;" aria-expanded="false"> <i
												class="pink ace-icon fa fa-tachometer bigger-110"></i> Thông
												tin người dùng
										</a></li>
										<li id="#profile3" class=""><a data-toggle="tab" href="#profile3"
											style="line-height: 45px;" aria-expanded="false"> <i
												class="blue ace-icon fa fa-user bigger-110"></i> Đơn hàng
										</a></li>
										<li id="#dropdown13" class=""><a data-toggle="tab" href="#dropdown13"
											style="line-height: 45px;" aria-expanded="true"> <i
												class="ace-icon fa fa-rocket"></i> Thông tin tài khoản
										</a></li>
									</ul>
									<div id="tabs" class="tab-content">
										<div id="home3" class="tab-pane active">
											<h4 class="my-5 font-weight-normal border-bottom"
												style="margin-bottom: 1rem !important;">Thông tin người
												dùng</h4>
											<div class="row">
												<div class="col-md-6">

													<h5 class="font-weight-bold">Thông tin người dùng</h5>
													<div class="row">
														<div class="col-md-6 text-left">Tên khách hàng</div>
														<div class="col-md-6 text-right">${model.fullName}</div>
														<form:hidden path="fullName" />
													</div>
													<div class="row">
														<div class="col-md-6 text-left">Email</div>
														<div class="col-md-6 text-right">${model.userName}</div>
													</div>
												</div>
												<div class="col-md-6">
													<h5 class="font-weight-bold">Vai trò</h5>
													<div class="row">
														<div class="col-md-6 text-left">Vai trò</div>
														<div class="col-md-6 text-right">
															${model.role.codeName}</div>
														<form:hidden path="role.codeName"/>
													</div>
												</div>
											</div>

											<div class="space"></div>
											<div class="row" style="margin-left: 5px; margin-right: 5px;">

												<h5 class="my-5 font-weight-normal border-bottom"
													style="margin-bottom: 1rem !important;"
													style="font-weight: 600;">Thông tin địa chỉ</h5>
												<div class="dt-buttons btn-overlap btn-group pull-right">
													<!-- THEM DIA CHI -->
													<c:url var="createAddressURL"
														value='/admin/user/${model.id}/address/create' />
													<a flag="info"
														class="dt-button buttons-colvis btn btn-white btn-primary btn-bold "
														data-toggle="tooltip" title='Thêm địa chỉ'
														href='${createAddressURL}'> <span> <i
															class="fa fa-plus-circle bigger-110 purple"> Thêm địa
																chỉ</i>
													</span>
													</a>
												</div>
												<div>

													<table class="table table-striped table-bordered ">

														<thead>
															<tr>

																<th style="background-color: white;" class="center">SĐT</th>
																<th style="background-color: white;">Số nhà</th>
																<th style="background-color: white;" class="hidden-xs">Phường/Xã</th>
																<th style="background-color: white;" class="hidden-480">Quận/Huyện</th>
																<th style="background-color: white;">Tỉnh/thành</th>
																<th style="background-color: white;">Thao tác</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="item" items="${model.shipAddress}"
																varStatus="i">
																<form:hidden path="shipAddress[${i.index}].id"
																	value="${item.id}" />
																<tr>

																	<td>
																		<div class="product-title">${item.number}</div>

																	</td>
																	<td class="hidden-xs">${item.address}</td>
																	<td class="hidden-480">${item.ward}</td>
																	<td class="hidden-480">${item.district}</td>
																	<td class="hidden-480">${item.province}</td>
																	<td><c:url var="UpdateURL"
																			value="/admin/user/${model.id}/address/edit/${item.id}" />

																		<a class="btn btn-sm btn-primary btn-edit"
																		data-toggle="tooltip" title="Cập nhật"
																		href='${UpdateURL}'> <i
																			class="fa fa-pencil-square-o" aria-hidden="true"></i>
																	</a> <c:url var="deleteURL"
																			value="/admin/user/${model.id}/address/delete/" /> <a
																		class="btn btn-sm btn-danger btn-edit delete-address"
																		data-id="${item.id}" data-toggle="tooltip" title="Xóa">
																			<i class="fa fa-trash-o" aria-hidden="true"> </i>
																	</a></td>
																</tr>
															</c:forEach>

														</tbody>
													</table>
												</div>
											</div>


											<div class="space"></div>

											<!-- /.row -->

										</div>
										<div id="profile3" class="tab-pane">
											<!-- ORDER -->
											
											<%@ include file="/WEB-INF/views/admin/user/subViewOrder.jsp"%>
											
										</div>

										<div id="dropdown13" class="tab-pane ">

											<div class="row">
												<div class="col-md-6">

													<h5 class="font-weight-bold" style="font-weight: 600;">Thông
														tin người dùng</h5>
													<div class="row">
														<div class="col-md-6 text-left">Email</div>
														<div class="col-md-6 text-right">${model.userName}</div>
														<form:hidden path="userName" />
													</div>
													<div class="row">
														<div class="col-md-6 text-left">
															<p>Tên khách hàng :</p>
															<form:input path="fullNameValidator" />
															<form:errors path="fullNameValidator" cssClass="error" cssStyle="color:red;"/>
														</div>
														<div class="col-md-6 text-right"></div>
													</div>
												</div>
												<div class="col-md-6">
													<h5 class="font-weight-bold" style="font-weight: 600;">Vai
														trò</h5>
													<div class="row">
														<div class="col-md-6 text-left">Vai trò</div>
														<div class="col-md-6 text-right">
															<form:select path="roleCode">

																<form:options items="${role}" />
															</form:select>

														</div>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-6"></div>
												<div class="col-md-6">
													<div class="row">
														<div class="col-md-6 pull-right ">
															<button name="action" value="100" style="margin-left: 250px;" class="btn btn-info "
																type="submit">Lưu tên</button>
														</div>
													</div>

												</div>
											</div>

											<div class="row">
												<div class="col-md-6">

													<h5 class="font-weight-bold" style="font-weight: 600;">Đổi
														mật khẩu</h5>
													<div class="row">
														<div class="col-md-6 text-left">
															<p>Nhập mật khẩu :</p>
															<form:password path="password" />
															<form:errors  path="password"  style="color:red;"  cssClass="error" />
														</div>
														<div class="col-md-6 text-right"></div>
													</div>
													<div class="row">
														<div class="col-md-6 text-left">
															<p>Nhập lại mật khẩu : </p>
															
															<form:password path="confirmPassword" />
															<form:errors path="confirmPassword"  style="color:red;"  cssClass="error" />
														</div>

														<div class="col-md-6 text-right"></div>
													</div>
												</div>
												<div class="col-md-6">

													<div class="row"></div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-6"></div>
												<div class="col-md-6">
													<div class="row">
														<div class="col-md-6 pull-right ">
															<button name="action" value="200" style="margin-left: 250px;" class="btn btn-info "
																type="submit">Lưu mật khẩu</button>
														</div>
													</div>

												</div>
											</div>

										</div>
									</div>
								</div>
							</div>
						</form:form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
			<script >
			var tab = $("#getTab").attr("value")
			
			if(tab != null){
				str = tab.substring(1);
				$('li.active').not($("li[id="+tab+"]")).removeClass('active');			       
				$('div.active').not($("div[id="+str+"]")).removeClass('active');
				console.log(str)
				$("li[id="+tab+"]").addClass("active");
				$("div[id="+str+"]").addClass("active");
			} 
			</script>
			
			<script type="text/javascript">
                        $(".delete-address").on("click", function() {
                        	Swal.fire({
                        			title: "Xác nhận xóa",
                        			text: "Bạn có muốn xóa",
                        			icon: 'warning',
                        			showCancelButton: true,
                        			confirmButtonColor: '#d33',
                        			cancelButtonColor: '#3085d6',
                        			confirmButtonText: "Có, hãy xóa",
                        			cancelButtonText: "Không, đừng xóa",
                        		}).then((result) => {
                          		  if (result.isConfirmed) {	     			  
                          				var id=$(this).attr("data-id")
                          				console.log(id);
                        				window.location.href = "${deleteURL}"+id;
                        	  }
                        	})
                    	}); 
        </script>
        <script type="text/javascript">
        $('.tab-pane').each(function() {
			if($(this).hasClass('active')){
				var id = $(this).attr('id');
				console.log(id);
			}
		})
        
		$(document).ready(function () {
       $("#myTab3").on("click", function (e) {
        //debugger
        //var ref_this = $("ul.tabs li a.active");

        var target = $(e.target).attr("href");
        console.log(target);
        $("#getTab").attr("value",target)

       });
});

        </script>
</body>

</html>