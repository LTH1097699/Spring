<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglib.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>

<head>
<title>Hoàn đơn hàng</title>
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
						href="<c:url value="
                                        /admin/home" />">Trang
							chủ</a></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<div class="page-header">Hoàn đơn hàng</div>
				<!-- /.page-header -->
				<div class="row">
					<div class="col-xs-12">
						<c:url var="updateURL" value="/admin/order/edit/${model.id}" />
						<form:form action="${updateURL}" class="form-horizontal"
							role="form" id="formSubmit" modelAttribute="model" method="POST">
							<div class="col-sm-12">

								<!-- 	BUTTON -->
								<div class="clearfix form-actions"
									style="margin-top: 0px; padding: 8px 20px 8px 20px; background-color: white; border-bottom: 1px solid #E5E5E5; border-top: 0 solid #E5E5E5;">

									<div class="row pull-right">
									<c:url var="returnURL" value="/admin/order/edit/${model.id}" />
										<a class="btn btn-danger text-white "
											href="${returnURL}">
											<i class="ace-icon fa fa-arrow-left icon-on-left"></i>Quay
											lại
										</a> &nbsp; &nbsp; &nbsp;
										<button class="btn btn-info " type="submit"
											id="btnAddOrUpdateBook">
											<i class="ace-icon fa fa-check bigger-110"></i> Xác nhận <input
												type="hidden" value="6" name="status" id="status">
										</button>
									</div>

									<!-- <input id="order-action" name="order-action" value=""> -->
								</div>
								<c:if test="${not empty message}">
									<div class="alert alert-${alert}">${message}</div>
								</c:if>
								<div class="tab-content">
									<div id="home3" class="tab-pane active">
										<h4 class="my-5 font-weight-normal border-bottom"
											style="margin-bottom: 1rem !important;">Đơn hàng</h4>
										<div class="row">
											<div class="col-md-6">

												<h5 class="font-weight-bold" style="font-weight: 600;">Thông
													tin khách hàng</h5>
												<div class="row">
													<div class="col-md-6 text-left">Tên khách hàng</div>
													<div class="col-md-6 text-right">${model.username}</div>
												</div>
												<div class="row">
													<div class="col-md-6 text-left">Email</div>
													<div class="col-md-6 text-right">${model.email}</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<h5 class="font-weight-bold" style="font-weight: 600;">Địa
													chỉ giao hàng</h5>
												<div class="row">
													<div class="col-md-6 text-left">
														Số nhà :
														<form:input class="billing_address" path="address"
															readonly="true" />
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 text-left">
														Phường/Xã
														<form:input class="billing_address_3" path="ward"
															readonly="true" />
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 text-left">
														Quận/Huyện
														<form:input class="billing_address_2" path="district"
															readonly="true" />
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 text-left">
														Tỉnh/Thành :
														<form:input class="billing_address_1" path="province"
															readonly="true" />
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 text-left">
														SĐT:
														<form:input class="billing_number" path="number"
															readonly="true" />
													</div>
												</div>
											</div>

										</div>
										<div class="space"></div>
										<div class="row">
											<h4 class="my-5 font-weight-normal border-bottom"
												style="margin-bottom: 1rem !important;">Các sản phẩm</h4>
											<div>
												<table class="table table-striped table-bordered">
													<thead>
														<tr>
															<th style="background-color: white;" class="center">id</th>
															<th style="background-color: white;">Sản phẩm</th>

															<th style="background-color: white;" class="hidden-480">Số
																lượng</th>

															<th style="background-color: white;" class="hidden-480">Kho</th>

														</tr>
													</thead>
													<tbody>
														<c:forEach var="item" items="${model.orderDetails}"
															varStatus="i">
															<tr>
																<td class="center">${item.book.id}<form:hidden
																		path="orderDetails[${i.index}].id" value="${item.id}" />
																</td>
																<td>
																	<div class="product-title">${item.book.title}</div>
																	<div class="product-title">Mã:
																		${item.book.bookCode}</div>
																</td>

																<td class="hidden-480">${item.quantity}</td>

																<td class="hidden-480">
																<%-- <c:forEach var="item2" items="${item.wareHouse}">
																<c:if test="${item.wareHouseId == item2.id}">
																<p>${item2.name}</p>
																</c:if>
																
																</c:forEach> --%>
																
																${item.wareHouseName}
																</td>

															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
										<!-- /.row -->
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
</body>

</html>
