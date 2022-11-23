<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="saveURL" value="/author-save" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Thêm đơn hàng</title>
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
					<li><i class="ace-icon fa fa-home home-icon"></i> <a href="<c:url value="/admin/home" />">Trang chủ</a></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<div class="page-header"></div>
				<!-- /.page-header -->
				<div class="row">
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS FORM -->
						<c:url var="updateUrl" value='/admin/order/create/user/${model.user.id}' />
						<form:form action="${updateUrl}" class="form-horizontal" id="formSubmit"
							modelAttribute="model" method="post">

							<!-- 	BUTTON -->
							<div class="clearfix form-actions"
								style="margin-top: 0px; padding: 8px 20px 8px 20px; background-color: white; border-bottom: 1px solid #E5E5E5; border-top: 0 solid #E5E5E5;">
								<div class="row pull-right">
									<a class="btn btn-danger"
										href="<c:url value="/admin/order/list"/>"> <i
										class="ace-icon fa fa-arrow-left icon-on-left"></i>Quay lại
									</a> &nbsp; &nbsp; &nbsp;

									<button class="btn btn-info" type="button"
										id="btnAddOrUpdateBook">
										<i class="ace-icon fa fa-check bigger-110"></i> Thêm đơn hàng
									</button>

								</div>
							</div>

							<div class="col-xs-4">
							
								<div class="row">
								
								<button style="margin: 0 5px 0;" class="btn btn-white btn-info pull-right" name="addProductInCartToNewOrder" >
											Thêm
								</button>
								</div>
								<div class="row">
								
								
								<div class="form-group" style="margin: 0px 5px 0;">
									<h4>Giỏ hàng</h4>
									<div class="tbl-header" >
									<table class="table table-striped table-bordered table-hover" style="width: 100%;margin-bottom:0px;">
										<thead>
											<tr>
												<th width="5%"><input type="checkbox" onclick="toggle(this);" /></th>
												<th width="50%">Tên sách</th>
												<th width="25%">Giá</th>
												<th width="20%">Số lượng</th>
											</tr>
										</thead>
									</table>
								</div>
								<div style="width: 100%; overflow: auto; max-height: 100px;">
									<table class="table table-striped table-bordered table-hover" style="width: 100%;">
									
									<c:forEach var="item" items="${cart}">
										<tr>
											<td width="5%">
											<c:if test="${ item.value.book.quantity > 0 }"  >
                                        	
                                         <form:checkbox path="chosedBookIncart" value="${item.key}"  />
                                            
                                        </c:if>  
                                        </td>
											<td width="50%">${item.value.book.title}</td>
											<td width="25%">${item.value.book.price}</td>
											<td width="20%">${item.value.quantity}</td>
										</tr>
									</c:forEach>
									
									</table>
								</div>
								</div>
								</div>
								<div class="row">
								
								<%@ include file="/WEB-INF/views/admin/orders/create/search.jsp"%>
												
								</div>

							</div>
							<div class="col-xs-8">
								<form:hidden class="billing_address_1" path="province" value="" />
								<form:hidden class="billing_address_2" path="district" value="" />
								<form:hidden class="billing_address_3" path="ward" value="" />
								<!-- Tên -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="form-field-1"> Email : </label>
									<div class="col-sm-6">
										<form:input path="email" value="${model.user.userName}" cssClass="form-control"  readonly="true"/>
										

									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="form-field-1"> Họ và tên : </label>
									<div class="col-sm-6">
										<form:input path="username" value="${model.user.fullName}" cssClass="form-control" readonly="true" />
										
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="form-field-1"> Hình thức vận chuyển</label>
									<div class="col-sm-6">
										<form:select path="shipMethod" cssClass="form-control">
											<form:option value="0">Giao hàng tận nhà</form:option>
											<form:option value="1">Nhận tại cửa hàng</form:option>
										</form:select>
									</div>
								</div>
								
								
								<div class="form-group" >
									<label class="col-sm-3 control-label no-padding-right"
										for="form-field-1"> Địa chỉ</label>
									<div class="col-sm-6">
										<form:select path="addressId" cssClass="form-control">
											<form:options items="${model.user.shipAddress}" itemValue="id" itemLabel="str"/>
										</form:select>
									</div>

								</div>
								
								
							<div class="form-group" style="margin: 0px 5px 0;">
										<label class="col-sm-3 "> Sản phẩm đơn hàng :</label>
									<div class="tbl-header" >
									<table class="table table-striped table-bordered table-hover" style="width: 100%;margin-bottom:0px;">
										<thead>
											<tr>
												
												<th width="40%">Tên sách</th>
												<th width="25%">Giá</th>
												<th width="20%">Số lượng</th>
												<th width="15%">Thao tác</th>
											</tr>
										</thead>
									</table>
								</div>
								<div style="width: 100%; overflow: auto;">
									<table class="table table-striped table-bordered table-hover" style="width: 100%;">
									<c:forEach var="item" items="${model.orderDetails}" varStatus="i">
										<tr>
										<form:hidden path="orderDetails[${i.index}].id" value="${item.book.id}" />
											
											<td width="40%">${item.book.title}</td>
											<td width="25%">${item.book.price}</td>
											<td width="20%">
											<form:input type="number" path="orderDetails[${i.index}].quantity" value="${item.quantity}" />
											<input type="hidden" name="quantityWareHouse" id="quantityWareHouse" value="${item.book.quantity}">
											<input type="hidden" name="idBook" id="idBook" value="${item.book.id}">
											</td>
											<td width="15%">
											
											<button type="button" class="btn " id="deleteProduct" value="${item.book.id}">Xoa</button>
										</tr>
									</c:forEach>
									</table>
								</div>

								</div>
								
								
								
								
								
								
								
						
							</div>
							<form:hidden path="action"/>
							<form:hidden path="deleteOrderDetail"/>	
							<script type="text/javascript">
							$('button[id=deleteProduct]').click(function(){
								
								var id = $(this).attr("value");
								$("#action").attr("value","30");
								$("#deleteOrderDetail").attr("value",id);
								
								var aa = $("#action").val();
								var bb = $("#deleteOrderDetail").val();
								if(aa!=null && aa!== "" && bb!=null && bb!== ""){
									$('form').submit();
								}
								
							})
							</script>	
						
							
							<script type="text/javascript">
							$('button[name=addProductInCartToNewOrder]').on('click',function(){
								$("#action").attr("value","20");
								var aa = $("#action").val();
								console.log(aa)
								$("#deleteOrderDetail").attr("value","");
								if(aa!=null && aa!== ""){
									$('form').submit();
								}
								
							})
							</script>	
							
							<c:url var="searchBook" value="/subview/search/book"/>
							<script type="text/javascript">
							$('button[name=searchBookToNewOrder]').on('click',function(e){
								e.preventDefault()
								$("#action").attr("value","40");
								var aa = $("#action").val();
								var keyword = $("#keyword").val();
								$.ajax({
						 			url: "${searchBook}",
						 			type: "GET",
						 			data: {action:aa,keyword:keyword},
						 			success: function(data){
						 				$("#searchBook").html(data);
						 			}				        
						 		}); 
							})
							</script>
							<script type="text/javascript">
							$('button[id=btnAddOrUpdateBook]').on('click',function(e){
								
								$("#action").attr("value","0");
								
								$('#formSubmit').submit();
							
							})
							</script>	

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
	<script type="text/javascript">
		var json = ${jsonn};
		$('select[name="chon_tinh_thanh"]')
				.each(
						function() {

							var $this = $(this);
							stc = '';
							json.forEach(function(vv, index) {
								stc += '<option value=' + json[index].id + '>'
										+ json[index].name + '</option>'
							})
							$this
									.html('<option value="0">Tỉnh / Thành phố</option>'
											+ stc)

							$(this)
									.on(
											'change',
											function(i) {
												$(
														'select[name="chon_xa_thitran"]')
														.html(
																'<option value="">Xã / Thị trấn</option>'
																		+ "");
												i = $(
														'select[name="chon_tinh_thanh"] :selected')
														.val();

												var address_1 = $(
														'select[name="chon_tinh_thanh"] :selected')
														.text();
												$('input.billing_address_1')
														.attr('value',
																address_1)

												var str = ''
												if (i != '') {

													json[i - 1].districts
															.forEach(function(
																	name, index) {

																str += '<option value="' + index + '">'
																		+ json[i - 1].districts[index].name
																		+ '</option>'
																$(
																		'select[name="chon_quan_huyen"]')
																		.html(
																				'<option value="">Quận / Huyện</option>'
																						+ str)
															})
												}
											})
						})
	</script>
	<script type="text/javascript">
		$('select[name="chon_quan_huyen"]')
				.on(
						'change',
						function(i, y) {
							y = $('select[name="chon_quan_huyen"] :selected')
									.val();
							i = $('select[name="chon_tinh_thanh"] :selected')
									.val();

							var address_2 = $(
									'select[name="chon_quan_huyen"] :selected')
									.text();
							$('input.billing_address_2').attr('value',
									address_2)

							var str = ''
							if (y != '') {
								json[i - 1].districts[y].wards
										.forEach(function(name, index) {

											str += '<option value="' + index + '">'
													+ json[i - 1].districts[y].wards[index].name
													+ '</option>'
											$('select[name="chon_xa_thitran"]')
													.html(
															'<option value="">Xã / Thị trấn</option>'
																	+ str)
										})
							}
						})

		$('select[name="chon_xa_thitran"]').on(
				'change',
				function() {
					var address_3 = $(
							'select[name="chon_xa_thitran"] :selected').text();
					$('input.billing_address_3').attr('value', address_3)

				})
	</script>
</body>
</html>
