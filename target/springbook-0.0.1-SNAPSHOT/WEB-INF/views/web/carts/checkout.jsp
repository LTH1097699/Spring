<%@ page import="com.springbook.utils.SecurityUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="cartList" value="/home/cart/list" />
<c:url var="addOrder" value="/order/add" />
<fmt:setLocale value="vi_VN" />

<!DOCTYPE div PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Thanh toán đơn hàng</title>



</head>

<body>

	<div class="container">
		<div class="py-5 text-center">
			<h2>Thanh toán đơn hàng</h2>
		</div>

		<div class="row">
			<!-- CHI TIẾT GIỎ HÀNG -->
			<div class="col-md-4 order-md-2 mb-4">
				<h4 class="d-flex justify-content-between align-items-center mb-3">
					<span class="text-muted">Sản phẩm</span>
				</h4>
				<ul class="list-group mb-3">
					<c:forEach var="item" items="${cart}">
						<div class="list-group">
							<a
								class="list-group-item list-group-item-action flex-column align-items-start">
								<div class="d-flex w-100 justify-content-between">
									<h6 class="mb-1">${item.value.book.title}</h6>
								</div>
								<p class="mb-1">
									Giá tiền:
									<fmt:formatNumber type="currency">${item.value.totalPrice}</fmt:formatNumber>
								</p> <small class="text-muted">Số lượng:
									${item.value.quantity }</small>
							</a>
						</div>
					</c:forEach>
					<li class="list-group-item d-flex justify-content-between"
						style="color: black;"><span>Total</span>
						<p class="mb-1">
							<fmt:formatNumber type="currency">${totalPrice}</fmt:formatNumber>
						</p></li>
				</ul>
			</div>
			<!-- CHI TIẾT GIỎ HÀNG -->

			<!-- THÔNG TIN KHÁCH HÀNG -->
			<div class="col-md-8 order-md-1">
				<h4 class="mb-3">Thông tin giao hàng</h4>
				<form:form action="${addOrder}" modelAttribute="order" method="post">

					<!-- -- ANONYMOUS -- -->
					<security:authorize access="isAnonymous()">
						<div class="row">
							<div class="col-md-6 mb-3">
								<label for="email">Email</label>
								<form:input type="text" cssClass="form-control" path="email" />
								<form:errors path="email" cssClass="error" cssStyle="color:red;"/>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 mb-3">
								<label for="username">Họ và tên</label>
								<form:input type="text" cssClass="form-control" path="username" />
								<form:errors path="username" cssClass="error" cssStyle="color:red;"/>
							</div>
						</div>
						
						<div class="mb-3">
							<label for="number">Số điện thoại</label>
							<form:input type="text" cssClass="form-control" path="number" />
							<form:errors path="number" cssClass="error" cssStyle="color:red;"/>
						</div>
						<div class="mb-3">
							<label for="address">Địa chỉ thường trú</label>
							<form:input type="text" cssClass="form-control" path="address" />
							<form:errors path="address" cssClass="error" cssStyle="color:red;"/>
						</div>

						<div class="row">
							<div class="col-md-5 mb-3">
								<select class="form-control" name="chon_tinh_thanh" >
									<option value="">Tỉnh / Thành phố</option>
								</select>
								<form:errors path="province" cssClass="error" cssStyle="color:red;"/>
							</div>
							<div class="col-md-4 mb-3">
								<select class="form-control" name="chon_quan_huyen" >
									<option value="">Quận / Huyện</option>
								</select> 
								<form:errors path="district" cssClass="error" cssStyle="color:red;"/>
							</div>
							<div class="col-md-4 mb-3">
								<select class="form-control" name="chon_xa_thitran" >
									<option value="">Xã / Thị trấn</option>
								</select> 
								<form:errors path="ward" cssClass="error" cssStyle="color:red;"/>
							</div>
	
        

      </div>
      					<form:hidden class="billing_address_1" path="province"  value=""/>
						<form:hidden class="billing_address_2" path="district"  value=""/>
						<form:hidden class="billing_address_3" path="ward"  value=""/>
						
						

						
					</security:authorize>
					<!-- -- ANONYMOUS -- -->

					<!-- -- Authenticated -- -->
					<security:authorize access="isAuthenticated()">
						<form:hidden path="user.id" value="${order.user.id}"/>
						<input class="billing_address"  type="hidden" value="">
						<input class="billing_number"  type="hidden" value="">
						<input class="billing_address_1"  type="hidden" value="">
						<input class="billing_address_2"  type="hidden" value="">
						<input class="billing_address_3"  type="hidden" value="">
						<div class="container">
							<div class="row mt-2">
								<div class="col-12">

									<div class="form-group row">
										<div class="col-12">
										<div class="row">
							<div class="col-md-6 mb-3">
								<label for="email"><h6>Email</h6></label>
								<form:input type="text" cssClass="form-control" path="email" value="${order.user.userName }" readonly="true" />
								
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 mb-3">
								<label for="username"><h6>Họ và tên</h6></label>
								<form:input type="text" cssClass="form-control" path="username" value="${order.user.fullName }" readonly="true" />
								
							</div>
						</div>
							<form:errors path="shipAddress" cssClass="error" cssStyle="color:red;"/>
											<div class="card-deck" style="display: flow-root;" >
											<c:if test="${not empty order.user.shipAddress}">
												<c:forEach var="item" items="${order.user.shipAddress}" varStatus="i">
													
													

														<div id="shipAddress${item.id }-card" class="card mb-4 float-left " style='min-width: 300px;max-width: 300px;'>
															<div class="card-body" role="button">
																<h5 class="card-title">
																	<input id="shipAddress${item.id}" type="radio">
																	<form:hidden path="user.shipAddress[${i.index}].id" 
																		value="${item.id}" class="idAdressUser"/>
																	<form:label path="user.fullName" />
																
																</h5>
																<p class="card-text">
																	<label>SĐT : ${item.number}</label>
																	<form:hidden path="user.shipAddress[${i.index}].number" name="number"
																		value="${item.number}" class="numberAdressUser"/>

																</p>
																<p class="card-text">
																	<label>Số nhà : ${item.address}</label>
																	<form:hidden path="user.shipAddress[${i.index}].address" 
																		value="${item.address}" class="addressAdressUser" />

																</p>
																<p class="card-text">
																	<label>Tỉnh/Thành : ${item.province}</label>
																	<form:hidden path="user.shipAddress[${i.index}].province" 
																		value="${item.province}" class="provinceAdressUser" />
																</p>
																<p class="card-text">
																	<label>Quận/Huyện : ${item.district}</label>
																	<form:hidden path="user.shipAddress[${i.index}].district" 
																		value="${item.district}" class="districtAdressUser" />
																</p>
																<p class="card-text">
																	<label>Phường/Xã : ${item.ward}</label>
																	<form:hidden path="user.shipAddress[${i.index}].ward" 
																		value="${item.ward}" class="wardAdressUser" />
																</p>
																
																
															</div>
														</div>
													
													
												</c:forEach>
											</c:if>
											
											<c:if test="${empty order.user.shipAddress}">
												
												
											</c:if>
											</div>
											<div class="form-group row">
													<a href="#" style="width: 64%;"
														data-toggle="modal" data-target="#exampleModalCenter">
														Thêm địa chỉ giao hàng</a>
												</div>
										</div>
									</div>


								</div>
							</div>
						</div>
						<!-- MODAL -->
						<div class="modal fade" id="exampleModalCenter" tabindex="-1"
							role="dialog" aria-labelledby="exampleModalCenterTitle"
							aria-hidden="true">
							<div class="modal-dialog modal-dialog-centered" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="exampleModalLongTitle">Thêm
											địa chỉ giao hàng</h5>
									</div>
									<div class="modal-body">
										<div class="row">
										 <div class="col-md-9">
							<label for="number">Số điện thoại <span style="color: red;" >*</span></label>
							<input type="text" class="form-control" name="so_dien_thoai" />
							<span id="errorNumber" style="display: none;color:red; " >Giá trị không hợp lệ</span>	
						</div></div>
						<div class="row">
						<div class="col-md-9">
							<label for="address">Địa chỉ thường trú <span style="color: red;" >*</span></label>
							<input type="text" class="form-control" name="dia_chi_thuong_tru" />
							<span id="errorAddress" style="display: none;color:red; " >Giá trị không hợp lệ</span>		
						</div></div>

						<div class="row">
							<div class="col-md-9">
							<label for="chon_tinh_thanh">Tỉnh / Thành phố <span style="color: red;" >*</span></label>
								<select class="form-control" name="chon_tinh_thanh" >
									<option  value="0">Tỉnh / Thành phố</option>
								</select>
								<span id="errorProvince" style="display: none;color:red;" >Chưa chọn</span>
							</div></div>
						<div class="row">
							<div class="col-md-9 ">
								<label for="chon_quan_huyen">Quận / Huyện <span style="color: red;" >*</span></label>
								<select class="form-control" name="chon_quan_huyen" >
									<option  value="0">Quận / Huyện</option>
								</select> 
								<span id="errorDistrict" style="color:red; display: none;" >Chưa chọn</span>
								
							</div></div>
							<div class="row">
							<div class="col-md-9">
							<label for="chon_xa_thitran">Xã / Thị trấn <span style="color: red;" >*</span></label>
								<select class="form-control" name="chon_xa_thitran"  required>
									<option value="0">Xã / Thị trấn</option>
								</select> 
								<span id="errorWard" style="display: none;color:red; " >Chưa chọn</span>		
							</div>
                         </div>	
								
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-dismiss="modal">Đóng</button>
										<button onclick="createAddress();" type="button"
											class="btn btn-primary">Xác nhận</button>
									</div>
								</div>
							</div>
						</div>
						

					</security:authorize>
					<!-- -- Authenticated -- -->
					
								
																<!-- VALUE FOR ORDER -->
																	<form:hidden path="shipAddress.id" class="idInCreateOrder"
																		value="" />
																	<form:hidden path="shipAddress.number" class="numberInCreateOrder"
																		value="" />
																	<form:hidden path="shipAddress.address" class="addressInCreateOrder"
																		value="" />
																	<form:hidden path="shipAddress.province" class="provinceInCreateOrder"
																		value="" />
																	<form:hidden path="shipAddress.district" class="districtInCreateOrder"
																		value="" />
																	<form:hidden path="shipAddress.ward" class="wardInCreateOrder"
																		value="" />
															<!-- VALUE FOR ORDER -->
				
					
					<!-- SHIP METHOD -->
					<h4 class="mb-3">Hình thức vận chuyển</h4>
					<form:select path="shipMethod">
					<form:option value="0">Giao hàng tận nhà</form:option>
					<form:option value="1">Nhận tại cửa hàng</form:option>
					</form:select>
					<!-- BUTTON -->
					<hr class="mb-4">
					<div class="col-md-6 pull-left">
							<a style="margin-bottom: 30px;" href="<c:url value='/home' /> "
								class="btn btn-danger text-white btn-lg btn-block" type="submit">Quay lại</a>
						</div>
						<div class="col-md-6 pull-right">
							<button style="margin-bottom: 30px;"
								class="btn btn-primary text-white btn-lg btn-block"
								type="submit">Xác nhận đơn hàng</button>
						</div>
				</form:form>
			</div>
		</div>

	</div>

	<content tag="script"> 
	<script>
		$(document).ready(function() {
			$(":radio[name=paymentMethod]").change(function() {
				if ($(this).is(':checked')) {
					var name = $(this).attr('id')
					$('span').hide()
					$('span#' + name + 'demo').show()
				}

			});
		});
	</script> 
	<!-- select address -->
	<script>
		$(document).ready(
				function() {
					$("div.card-deck").on(
							"click",
							function(event) {
								$('input:radio').change(function() {//Clicking input radio
									var radioClicked = $(this).attr('id');
									var id = $(this).find('.idAdressUser').attr('value');
									var number = $(this).find('.numberAdressUser').attr('value');
									var address = $(this).find('.addressAdressUser').attr('value');
									var province = $(this).find('.provinceAdressUser').attr('value');
									var district = $(this).find('.districtAdressUser').attr('value');
									var ward = $(this).find('.wardAdressUser').attr('value');
									
									$('.idInCreateOrder').attr('value',id); 
									$('.numberInCreateOrder').attr('value',number); 
									$('.addressInCreateOrder').attr('value',address); 
									$('.provinceInCreateOrder').attr('value',province); 
									$('.districtInCreateOrder').attr('value',district); 
									$('.wardInCreateOrder').attr('value',ward); 
									
									unclickRadio();
									removeActive();
									clickRadio(radioClicked);
									makeActive(radioClicked);
								});
								$("div.card").click(
										function() {//Clicking the card
											var inputElement = $(this).find('input[type=radio]').attr('id');
										
											var id = $(this).find('.idAdressUser').attr('value');
											var number = $(this).find('.numberAdressUser').attr('value');
											var address = $(this).find('.addressAdressUser').attr('value');
											var province = $(this).find('.provinceAdressUser').attr('value');
											var district = $(this).find('.districtAdressUser').attr('value');
											var ward = $(this).find('.wardAdressUser').attr('value');
											
											$('.idInCreateOrder').attr('value',id); 
											$('.numberInCreateOrder').attr('value',number); 
											$('.addressInCreateOrder').attr('value',address); 
											$('.provinceInCreateOrder').attr('value',province); 
											$('.districtInCreateOrder').attr('value',district); 
											$('.wardInCreateOrder').attr('value',ward); 
										
											unclickRadio();
											removeActive();
											makeActive(inputElement);
											clickRadio(inputElement);
										});
							});
				});
		function unclickRadio() {
			$("input:radio").prop("checked", false);
		}

		function clickRadio(inputElement) {
			$("#" + inputElement).prop("checked", true);

		}

		function removeActive() {
			$(".card").removeClass("active");
		}

		function makeActive(element) {
			$("#" + element + "-card").addClass("active");
		}
	</script> 
	<!-- /select address -->
	<script>
	
		function createAddress() {
			var random = Math.floor(Math.random() * (1200009999 - 1 + 1) + 1);
			console.log(random);
			
			
			const container = document.getElementById('errorNumber');
			container.style.display = "none";
			const container1 = document.getElementById('errorAddress');
			container1.style.display = "none";	
			const container2 = document.getElementById('errorProvince');
			container2.style.display = "none";	
			const container3 = document.getElementById('errorDistrict');
			container3.style.display = "none";	
			const container4 = document.getElementById('errorWard');
			container4.style.display = "none";	
			
			var number = $('input[name=so_dien_thoai]').val();
			var address = $('input[name=dia_chi_thuong_tru]').val();
			
			var city = $('input.billing_address_1').val();
			var quan = $('input.billing_address_2').val();
			var xa = $('input.billing_address_3').val();
			
			testCity = $('select[name="chon_tinh_thanh"] :selected').val();
			testDistrict = $('select[name="chon_quan_huyen"] :selected').val();
			testWard = $('select[name="chon_xa_thitran"] :selected').val();
			console.log(testCity)
			if(number === ""){
				const container = document.getElementById('errorNumber');
				  container.style.display = "block";	
			}else if(address === ""){
				const container = document.getElementById('errorAddress');
				  container.style.display = "block";	
			}
			else if(testCity ==0){
				
				  const container = document.getElementById('errorProvince');
				  container.style.display = "block";	
			}
			else if(testDistrict ===""){
				const container = document.getElementById('errorDistrict');
				  container.style.display = "block";	
			}
			else if(testWard ===""){
				const container = document.getElementById('errorWard');
				  container.style.display = "block";	
			}
			else{
				var newRow = $("<div id='"+random+"-card' class='card mb-4 float-left ' style='min-width: 300px;max-width: 300px;'>");
				var cols = '';

				// Table columns
				cols += '<div class="card-body" role="button" >';
				cols += '<h5 class="card-title">';
				cols += '<input id="'+random+'" type="radio" value="" class="idAdressUser" ></h5>';
				cols += '<p class="card-text">SĐT :'+ number+ '</p><input class="numberAdressUser"   type="hidden" value="'+number+'">';
				cols += '<p class="card-text">Số nhà :'+ address+ '</p><input class="addressAdressUser"   type="hidden" value="'+address+'">';
				cols += '<p class="card-text">Tỉnh/Thành :'+ city+ '</p><input class="provinceAdressUser"   type="hidden" value="'+city+'">';
				cols += '<p class="card-text">Quận/Huyện :'+ quan+ '</p><input class="districtAdressUser"  type="hidden" value="'+quan+'">';
				cols += '<p class="card-text">Xã/Thị trấn :'+ xa+ '</p><input  class="wardAdressUser"  type="hidden" value="'+xa+'">';
				cols += '<p class="card-text"></p>';

				// Insert the columns inside a row
				newRow.append(cols);

				// Insert the row inside a table
				$(".card-deck").append(newRow);
				
				$("#exampleModalCenter").modal('hide');
				
			}
			
			
		}
	</script> 
	
	<script type="text/javascript">
var json= ${jsonn};
		$('select[name="chon_tinh_thanh"]').each(function() {
							
							var $this = $(this);
							stc = '';
							json.forEach(function(vv, index) {	
								stc += '<option value=' + json[index].id + '>'+ json[index].name + '</option>'
								}) 
								$this.html('<option value="0">Tỉnh / Thành phố</option>'+ stc)
								
							
							$(this).on('change', function(i) {
								$('select[name="chon_xa_thitran"]').html('<option value="">Xã / Thị trấn</option>'+ "");
								i = $('select[name="chon_tinh_thanh"] :selected').val();
								
								var address_1 = $('select[name="chon_tinh_thanh"] :selected').text();
								$('input.billing_address_1').attr('value',address_1)
							
								var str = ''
								if (i != '') {
									
									json[i-1].districts.forEach(function(name, index) {
										
										str += '<option value="' + index + '">'+ json[i-1].districts[index].name + '</option>'
										$('select[name="chon_quan_huyen"]').html('<option value="">Quận / Huyện</option>'+ str)
									}) 
								}
							})				
			})						

	</script>
	<script type="text/javascript">
	$('select[name="chon_quan_huyen"]').on('change', function(i,y) {
		y = $('select[name="chon_quan_huyen"] :selected').val();
		i = $('select[name="chon_tinh_thanh"] :selected').val();
		
		var address_2 = $('select[name="chon_quan_huyen"] :selected').text();
		$('input.billing_address_2').attr('value',address_2)
		
		
		var str = ''
		if (y != '') {	
			json[i-1].districts[y].wards.forEach(function(name, index) {
				
				str += '<option value="' + index + '">'+ json[i-1].districts[y].wards[index].name + '</option>'
				$('select[name="chon_xa_thitran"]').html('<option value="">Xã / Thị trấn</option>'+ str)
			}) 
		}
	})
	
	$('select[name="chon_xa_thitran"]').on('change', function() {
		var address_3 = $('select[name="chon_xa_thitran"] :selected').text();
		$('input.billing_address_3').attr('value',address_3)

	})		
	
	
	</script>
	
	 </content>
</body>
</html>