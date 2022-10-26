<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Cập nhật đơn hàng</title>
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
										/admin/home" />">Trang chủ</a></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<div class="page-header"></div>
				
				<!-- /.page-header -->
				<div class="row">
					<div class="col-xs-12">
						<c:if test="${not empty message}">
									<div class="alert alert-${alert}">${message}</div>
								</c:if>
						<c:url var="updateURL" value="/admin/order/edit/${model.id}" />
						<form:form action="${updateURL}" class="form-horizontal"
							role="form" id="formSubmit" modelAttribute="model" method="POST">
							<div class="col-sm-12">
								
								<!-- 	BUTTON -->
								<div class="clearfix form-actions"
									style="margin-top: 0px; padding: 8px 20px 8px 20px; background-color: white; border-bottom: 1px solid #E5E5E5; border-top: 0 solid #E5E5E5;">
									<div class="row pull-right">
										<a class="btn btn-danger"
											href="<c:url value="/admin/order/list"/>">
											<i class="ace-icon fa fa-arrow-left icon-on-left"></i>Quay
											lại
										</a> &nbsp; &nbsp; &nbsp;
										<c:url var="exportURL" value="/pdf/downloadRecept">
											<c:param name="id" value="${model.id}" />
										</c:url>
										<a href="${exportURL}" class="btn btn-white btn-info"
											id="btnAddOrUpdateBook"> <i
											class="ace-icon fa fa-print bigger-200"></i> In hóa đơn
										</a>
										<c:if test="${model.status ==5 }">
											<c:url var="createRefund"
												value="/admin/order/refund/${model.id}" />
											<a href="${createRefund}" class="btn btn-white btn-info"
												id="btnAddOrUpdateBook"> <i
												class="ace-icon fa fa-undo bigger-200"></i> Hoàn đơn hàng
											</a>
										</c:if>
										<c:if test="${model.status !=5 && model.status != 2 && model.status != 6 }">
											<c:url var="createShip" value="/admin/order/ship/${model.id}" />
											<a href="${createShip}" class="btn btn-white btn-info "
												id="btnAddOrUpdateBook"> <i
												class="ace-icon fa fa-check bigger-200"></i> Vận chuyển
											</a>
										</c:if>
										<c:if test="${model.status != 2 && model.status != 6 }">

											<button class="btn btn-info " type="submit"
												id="btnAddOrUpdateBook">
												<i class="ace-icon fa fa-check bigger-110"></i> Lưu đơn hàng
											</button>
										</c:if>
									</div>									
								</div>

								<div class="tab-content">
									<div id="home3" class="tab-pane active">
										<h4 class="my-5 font-weight-normal border-bottom"
											style="margin-bottom: 1rem !important;">Đơn hàng</h4>
										<div class="row">
											<div class="col-md-6">

												<h5 class="font-weight-bold" style="font-weight: 600;">Thông
													tin đơn hàng</h5>
												<div class="row">
													<div class="col-md-6 text-left"
														style="padding: 5px 4px 6px 12px;">Ngày tạo</div>
													<div class="col-md-6 text-right"
														style="padding: 5px 4px 6px 12px;">
														${model.shipDate}</div>
												</div>
												<div class="row">
													<div class="col-md-6 text-left"
														style="padding: 5px 4px 6px 12px;">Trạng thái</div>
													<c:if test="${model.status == 0 }">
														<div class="col-md-6 text-right">Chưa xử lý</div>
													</c:if>
													<c:if test="${model.status == 1}">

														<div class="col-md-6 text-right">Hoàn thành</div>
													</c:if>
													<c:if test="${model.status == 2 }">
														<div class="col-md-6 text-right">Hủy đơn hàng</div>
													</c:if>
													<c:if test="${model.status == 3}">

														<div class="col-md-6 text-right">Đang xử lý</div>
													</c:if>
													<c:if test="${model.status == 5}">

														<div class="col-md-6 text-right">Đang giao hàng</div>
													</c:if>
													<c:if test="${model.status == 6}">

														<div class="col-md-6 text-right">Hoàn đơn</div>
													</c:if>
												</div>
											</div>
											<div class="col-md-6" style="border-left: solid 1px;">

												<h5 class="font-weight-bold" style="font-weight: 600;">Thông
													tin khách hàng</h5>
												<div class="row">
													<div class="col-md-6 text-left"
														style="padding: 5px 4px 6px 12px;">Tên khách hàng</div>
													<div class="col-md-6 text-right">
														<form:input path="username" readonly="true" />
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 text-left"
														style="padding: 5px 4px 6px 12px;">Email</div>
													<div class="col-md-6 text-right">
														<form:input path="email" readonly="true" />
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<h5 class="font-weight-bold" style="font-weight: 600;">
													Địa chỉ giao hàng 
													<c:if test="${model.status != 5 && model.status != 2 && model.status != 6}">
														<a id="actionModalAddress">Thay đổi</a>
													</c:if>
													
												</h5>
												<div class="row">
													<div class="col-md-6 text-left">
														Số nhà :
														<form:input class="billing_address" path="address"
															readonly="true" />
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 text-left">
														Xã/Thị trấn :
														<form:input class="billing_address_3" path="ward"
															readonly="true" />
													</div>
												</div>
												<div class="row">
													<div class="col-md-6 text-left">
														Quận/Huyện :
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

										<!-- Modal ADDRESS -->
										<div class="modal fade" id="modalAddress" tabindex="-1"
											role="dialog" aria-labelledby="exampleModalCenterTitle"
											aria-hidden="true">
											<div class="modal-dialog modal-dialog-centered"
												role="document">
												<div class="modal-content">
													<div class="modal-header">
														<h5 class="modal-title" id="exampleModalLongTitle">Thông
															tin địa chỉ</h5>
													</div>
													<div class="modal-body">
														<div class="mb-3">
															<label style="font-weight: 600;" for="number">Số điện thoại</label> <input
																type="text" class="form-control" name="so_dien_thoai" />
															<p id="messagesModalNumber" class="error"
												style="display: none; color: red;">Chưa nhập giá trị</p>
														</div>
														<div class="mb-3">
															<label style="font-weight: 600;" for="address">Địa chỉ nhà</label> <input
																type="text" class="form-control"
																name="dia_chi_thuong_tru" />
																<p id="messagesModalAddress" class="error"
												style="display: none; color: red;">Chưa nhập giá trị</p>
														</div>
														<div class="row">
															<div class="col-md-5 mb-3">
															<label style="font-weight: 600;" for="address">Tỉnh/Thành</label>
																<select class="form-control" name="chon_tinh_thanh">
																	<option value="">Tỉnh / Thành phố</option>
																</select>
																<p id="messagesModalAddress1" class="error"
												style="display: none; color: red;">Chưa chọn giá trị</p>
															</div>
														</div>
														<div class="row">
															<div class="col-md-4 mb-3">
															<label style="font-weight: 600;" for="address">Quận/Huyện</label>
																<select class="form-control" name="chon_quan_huyen">
																	<option value="">Quận / Huyện</option>
																</select>
																<p id="messagesModalAddress2" class="error"
												style="display: none; color: red;">Chưa chọn giá trị</p>
															</div>
														</div>
														<div class="row">
															<div class="col-md-4 mb-3">
															<label style="font-weight: 600;" for="address">Xã/Thị trấn</label>
																<select class="form-control" name="chon_xa_thitran">
																	<option value="">Xã / Thị trấn</option>
																</select>
																<p id="messagesModalAddress3" class="error"
												style="display: none; color: red;">Chưa chọn giá trị</p>
															</div>
														</div>
														<div class="modal-footer">
															<button type="button" class="btn btn-secondary"
																data-dismiss="modal">Hủy</button>
															<button id="btnModalAddress" type="button"
																class="btn btn-primary">Xác nhận</button>
														</div>
													</div>
												</div>
											</div>
											<!-- /Modal ADDRESS -->
										</div>
										<div class="row">
											<div class="col-md-6">
												<h5 class="font-weight-bold" style="font-weight: 600;">Phương
													thức thanh toán</h5>

												<div class="row">
													
														<span style="margin-left: 11px;" class="ship-method">Tiền
															mặt</span>
													
													<form:hidden path="shipMethod" readonly="true" />
												</div>
											</div>
											<div class="col-md-6">
												<h5 class="font-weight-bold" style="font-weight: 600;">
													Phương thức vận chuyển
													<c:if test="${model.status != 5 && model.status != 2 && model.status != 6}">
														<a id="actionModalShipMethod">Thay đổi</a>
													</c:if>
												</h5>
												<div class="row">
													<c:if test="${model.shipMethod == 0}">
														<span style="margin-left: 11px;" class="ship-method">Giao
															hàng tận nơi</span>

													</c:if>
													<c:if test="${model.shipMethod == 1}">
														<span style="margin-left: 11px;" class="ship-method">Lấy
															tại cửa hàng</span>

													</c:if>
													<form:hidden class="shipMethodOrder" path="shipMethod"
														readonly="true" />
												</div>

												<!-- Modal SHIP -->
												<div class="modal fade" id="modalShipMethod" tabindex="-1"
													role="dialog" aria-labelledby="exampleModalCenterTitle"
													aria-hidden="true">
													<div class="modal-dialog modal-dialog-centered"
														role="document">
														<div class="modal-content">
															<div class="modal-header">
																<h5 class="modal-title" id="exampleModalLongTitle">Phương
																	thức vận chuyển</h5>
															</div>
															<div class="modal-body" id="modalCheck">
																<div class="form-check">
																	<input class="form-check-input"
																		data-id="Giao hàng tận nơi" type="radio" value="0"
																		name="shipMethodCheck" id="flexCheckDefault">
																	<label class="form-check-label" for="flexCheckDefault">
																		Giao hàng tận nơi </label>
																</div>
																<div class="form-check">
																	<input class="form-check-input"
																		data-id="Lấy tại cửa hàng" type="radio" value="1"
																		name="shipMethodCheck" id="flexCheckChecked">
																	<label class="form-check-label" for="flexCheckChecked">
																		Lấy tại cửa hàng </label>
																</div>
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-secondary"
																	data-dismiss="modal">Hủy</button>
																<button id="btnModalShipMethod" type="button"
																	class="btn btn-primary">Xác nhận</button>
															</div>
														</div>
													</div>
												</div>
												<!-- /Modal SHIP -->
											</div>
										</div>
										<div class="space"></div>
										<div class="row">
											<h5 class="my-5 font-weight-normal border-bottom"
												style="margin-bottom: 1rem !important;margin-left: 10px;font-weight: 600;"
												>Các sản phẩm</h5>
											<div>
												<table class="table table-striped table-bordered">

													<thead>
														<tr>
															<th style="background-color: white;" class="center">id</th>
															<th style="background-color: white;">Sản phẩm</th>
															<th style="background-color: white;" class="hidden-xs">Giá</th>


															<th style="background-color: white;" class="hidden-480">Số
																lượng</th>
															<c:if test="${model.status==5}">
																<th style="background-color: white;" class="hidden-xs">Kho</th>

															</c:if>
															<th style="background-color: white;">Tổng tiền</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="item" items="${model.orderDetails}"
															varStatus="i">
															<form:hidden path="orderDetails[${i.index}].id"
																value="${item.id}" />
															<tr>
																<td class="center">${item.book.id}</td>
																<td>
																	<div class="product-title">${item.book.title}</div>
																	<div class="product-title">Mã:
																		${item.book.bookCode}</div>
																</td>
																<td class="hidden-xs"><fmt:formatNumber
																		type="currency">
																						${item.unitPrice}
																					</fmt:formatNumber></td>
																<!-- QUANTITY -->
																<td class="hidden-480"><c:if
																		test="${model.status == 5 || model.status == 6}">
																		<p>${item.quantity}</p>
																		<form:hidden path="orderDetails[${i.index}].quantity"
																			value="${item.quantity}" readonly="true" />

																	</c:if> <c:if test="${model.status != 5 && model.status != 6}">

																		<form:input path="orderDetails[${i.index}].quantity"
																			value="${item.quantity}" />
																	</c:if></td>
																<!-- WAREHOUSE -->
																<c:if test="${model.status == 5 || model.status == 6}">
																	<td>
																		<p>${item.wareHouseName}</p> <form:hidden
																			path="orderDetails[${i.index}].wareHouseName"
																			value="${item.wareHouseName}" readonly="true" />
																	</td>
																</c:if>


																<td><fmt:formatNumber type="currency">
																						${item.totalPrice}
																					</fmt:formatNumber></td>
															</tr>
														</c:forEach>

													</tbody>
												</table>
											</div>
										</div>
										<!-- /.row -->
										<div class="row">
											<div class="col-md-6">
												<h5 class="font-weight-bold" style="font-weight: 600;">Trạng
													thái đơn hàng</h5>
												<select class="statusOrder" name="status" id="status">
													<c:if test="${ model.status == 5}">
														<option value="5">Đang giao hàng</option>
														<option value="1">Hoàn thành</option>
													</c:if>
													<c:if test="${ model.status == 2}">
														<option value="2">Hủy đơn hàng</option>
													</c:if>
													<c:if test="${ model.status != 5 && model.status != 2 && model.status != 6}">
														<option value="0">Chờ xử lý</option>
														<option value="3">Đang xử lý</option>

														<option value="2">Hủy đơn hàng</option>
													</c:if>
													<c:if test="${ model.status == 6}">
														<option value="6">Hoàn đơn hàng</option>
														
													</c:if>

												</select>

											</div>
											<div class="col-md-6">
												<h5 class="my-5 font-weight-normal border-bottom"
													style="margin-bottom: 1rem !important; font-weight: 600;">Tổng
													đơn hàng</h5>
												<div class="row">
													<div class="col-md-6 text-left">Tổng số lượng</div>
													<div class="col-md-6 text-right">
														${model.totalQuantity}</div>
												</div>
												<div class="row">
													<div class="col-md-6 text-left">Tổng sản phẩm</div>
													<div class="col-md-6 text-right">
														${model.totalProduct}</div>
												</div>
												<div class="row">
													<div class="col-md-6 text-left">Tổng tiền</div>
													<div class="col-md-6 text-right">
														<fmt:formatNumber type="currency">
														${model.totalPrice}</fmt:formatNumber>
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
		</div>
	</div>
	<!-- /.page-content -->

	<!-- <script type="text/javascript">
							$('#id-date-picker-1').datepicker({
								clearBtn: true,
								language: "vi",
								calendarWeeks: true,
								defaultViewDate: { year: 1977, month: 04, day: 25 }
							});
						</script> -->
	<!-- <script type="text/javascript">
							$('input[name=status]').on('change', function () {
								var value = $(this).attr("value");
								if ($(this).is(':checked')) {
									$(this).attr("value", 1);
								} else {
									$(this).attr("value", 0);
								}
							});
						</script> -->
	<script type="text/javascript">
							$('input[name=status]').each(function () {
								var value = $(this).attr("value");
								console.log(value);
								if (value == 1) {
									$(this).prop("checked", true);
								} else {
									$(this).prop("checked", false);

								}
							});
						</script>
	<script type="text/javascript">
						$("#actionModalShipMethod").on("click",function(){
							$("#modalShipMethod").modal("show");
							var checkValue = $("input[name=shipMethod]").attr("value");
							$("input[name=shipMethodCheck]").each(function(){
								if($(this).attr("value") === checkValue){
									$(this).prop('checked', true);
								}
							})
						})
						
						$('#modalCheck > input[type=radio]:even').prop('checked', true);
						$("#btnModalShipMethod").on("click",function(){
							
								var checkBoxValue = $("input[name=shipMethodCheck]:checked").val();
								var checkBoxText = $("input[name=shipMethodCheck]:checked").attr("data-id");
								
								console.log(checkBoxText);
								$("input[name=shipMethod]").attr("value",checkBoxValue) 
								$("#modalShipMethod").modal("hide");
								$("span.ship-method").text(checkBoxText);
							
							
						})
						
						/* modal address */
						$("#actionModalAddress").on("click",function(){
							$('#messagesModalNumber').css("display","none")
							$('#messagesModalAddress').css("display","none")
							$('#messagesModalAddress1').css("display","none")
							$('#messagesModalAddress2').css("display","none")
							$('#messagesModalAddress3').css("display","none")
							
							var sdt = $("input.billing_number").attr("value");
							$("input[name=so_dien_thoai]").attr("value",sdt);
							
							var diachi = $("input.billing_address").attr("value") ;
							$("input[name=dia_chi_thuong_tru]").attr("value",diachi);
							
							var province = $("input[class=billing_address_1]").val();
							var district = $("input[class=billing_address_2]").val();
							var ward = $("input[class=billing_address_3]").val();
							getAddress(province,district,ward);
							
							$("#modalAddress").modal("show");
						})
						
						$("#btnModalAddress").on("click",function(){
							$('#messagesModalNumber').css("display","none")
							$('#messagesModalAddress').css("display","none")
							$('#messagesModalAddress1').css("display","none")
							$('#messagesModalAddress2').css("display","none")
							$('#messagesModalAddress3').css("display","none")
								var sdt = $("input[name=so_dien_thoai]").val();
								var diachi = $("input[name=dia_chi_thuong_tru]").val();
								var address_1 = $('select[name="chon_tinh_thanh"] :selected').text();
								var address_2 = $('select[name="chon_quan_huyen"] :selected').text();
								var address_3 = $('select[name="chon_xa_thitran"] :selected').text();
			
								if(sdt == null || sdt === "" || diachi == null || diachi === "" || address_1 == null || address_1 === "Tỉnh / Thành phố" || address_2 == null || address_2 === "Quận / Huyện" || address_3 == null || address_3 === "Xã / Thị trấn"){
									console.log("nulll");
									if(sdt == null || sdt === "" ){
										$('#messagesModalNumber').css("display","block")
									}
									if(diachi == null || diachi === "" ){
										$('#messagesModalAddress').css("display","block")
									}
									if(address_1 == null || address_1 === "Tỉnh / Thành phố" ){
										$('#messagesModalAddress1').css("display","block")
									}
									if(address_2 == null || address_2 === "Quận / Huyện" ){
										$('#messagesModalAddress2').css("display","block")
									}
									if(address_3 == null || address_3 === "Xã / Thị trấn" ){
										$('#messagesModalAddress3').css("display","block")
									}
									
									
								}else{
									$("input.billing_number").attr("value",sdt) 
									$("input.billing_address").attr("value",diachi) 
									$('input.billing_address_1').attr('value',address_1)
									$('input.billing_address_2').attr('value',address_2)
									$('input.billing_address_3').attr('value',address_3)
									$("#modalAddress").modal("hide");
								}
							
						})
						
						</script>
	<script type="text/javascript">
	
		var json= ${jsonn};
		function getAddress(province,district,ward) {
			$('select[name="chon_tinh_thanh"]').each(function() {
				
				var $this = $(this);
				stc = '';
				json.forEach(function(vv, index) {
					if(province === json[index].name){
						stc += '<option selected="selected" value=' + json[index].id + '>'+ json[index].name + '</option>'
					}else{
						stc += '<option value=' + json[index].id + '>'+ json[index].name + '</option>'
					}
				}) 
					$this.html('<option value="">Tỉnh / Thành phố</option>'+ stc)
					
				$this.html('<option value="">Tỉnh / Thành phố</option>'+ stc)
				$(this).on('change', function(i) {
					$('select[name="chon_xa_thitran"]').html('<option value="">Xã / Thị trấn</option>'+ "");
					
					itext = $('select[name="chon_tinh_thanh"] :selected').text();
					if(itext === "Tỉnh / Thành phố"){
						$('select[name="chon_quan_huyen"]').html('<option value="">Quận / Huyện</option>'+ "");
					}else{
						i = $('select[name="chon_tinh_thanh"] :selected').val();
						var str = ''
						if (i != '') {	
							json[i-1].districts.forEach(function(name, index) {											
								str += '<option value="' + index + '">'+ json[i-1].districts[index].name + '</option>'										
							}) 
							$('select[name="chon_quan_huyen"]').html('<option value="">Quận / Huyện</option>'+ str)
							
						}
					}	
				})
				
				if(district != null && district !== ''){
i = $('select[name="chon_tinh_thanh"] :selected').val();
					
					var str = ''
					if (i != '') {
						
						json[i-1].districts.forEach(function(name, index) {
							if(district === json[i-1].districts[index].name){
								str += '<option selected="selected" value="' + index + '">'+ json[i-1].districts[index].name + '</option>'
							}else{
								str += '<option value="' + index + '">'+ json[i-1].districts[index].name + '</option>'
							}
							
							$('select[name="chon_quan_huyen"]').html('<option value="">Quận / Huyện</option>'+ str)
						}) 
					}
				}
				
				if(ward != null && ward !== ''){
					y = $('select[name="chon_quan_huyen"] :selected').val();
					i = $('select[name="chon_tinh_thanh"] :selected').val();
					
					console.log(ward);
					var str = ''
					if (y != '') {	
						json[i-1].districts[y].wards.forEach(function(name, index) {
							if(ward === json[i-1].districts[y].wards[index].name ){
								str += '<option selected="selected" value="' + index + '">'+ json[i-1].districts[y].wards[index].name + '</option>'
							}else{
								str += '<option value="' + index + '">'+ json[i-1].districts[y].wards[index].name + '</option>'
							}
							
							$('select[name="chon_xa_thitran"]').html('<option value="">Xã / Thị trấn</option>'+ str)
						}) 
					}
				}
})						
$('select[name="chon_quan_huyen"]').on('change', function(i,y) {
y = $('select[name="chon_quan_huyen"] :selected').val();
i = $('select[name="chon_tinh_thanh"] :selected').val();

console.log(ward);
var str = ''
if (y != '') {	
json[i-1].districts[y].wards.forEach(function(name, index) {
		str += '<option value="' + index + '">'+ json[i-1].districts[y].wards[index].name + '</option>'
	
	$('select[name="chon_xa_thitran"]').html('<option value="">Xã / Thị trấn</option>'+ str)
}) 
}
})
		}

	</script>
	<!-- <script type="text/javascript">
	
	
	
	</script> -->
	<script type="text/javascript">
	var statusCode  = ${model.status};
	$('select.statusOrder').each(function() {
		var select = $(this).children('option').val();
		if(select=== statusCode ){
			select.attr("selected","selected");
		}
	})
	
	</script>
</body>

</html>