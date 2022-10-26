<%@ page import="com.springbook.utils.SecurityUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="cartList" value="/home/cart/list" />

<fmt:setLocale value="vi_VN" />

<!DOCTYPE div PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Thông tin địa chỉ</title>
</head>

<body>
	<div class="row">

		<div class="col-lg-3">

			<div class="list-group">
				<a href="<c:url value='/home/account'/>" class="list-group-item">Thông
					tin người dùng</a> <a href="<c:url value='/home/account/order'/>"
					class="list-group-item">Đơn hàng</a>
			</div>
			<div class="list-group">
				<a href="<c:url value='/home/account/details'/>" class="list-group-item">Thông tin tài khoản</a> <a
					href="<c:url value ="/home/account/address"/>"
					class="list-group-item">Thông tin địa chỉ</a>
			</div>
		</div>
		<!-- /.col-lg-3 -->
		<div class="col-lg-9" >
		<div class="container" style="min-height: 786px;">
			<h1 class="my-4 font-weight-normal">Cập nhật địa chỉ</h1>
			<c:url var="editAddress"
				value="/home/account/address/edit/${model.id}" />
			<form:form action="${editAddress}" modelAttribute="model" method="post">

				
				<div class="row">
					<div class="col-md-5 mb-3">
						<label for="number">Số điện thoại</label>
						<form:input type="text" cssClass="form-control" path="number" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-5 mb-3">
						<label for="address">Địa chỉ thường trú</label>
						<form:input type="text" cssClass="form-control" path="address" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-5 mb-3">
						<select class="form-control" name="chon_tinh_thanh" required="">
							<option value="">Tỉnh / Thành phố</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col-md-5 mb-3">
						<select class="form-control" name="chon_quan_huyen" required="">
							<option value="">Quận / Huyện</option>
						</select>
					</div>
				</div>

		
				<!-- BUTTON -->
				<hr class="mb-4">
				<div class="row">
					<div class="col-md-3 pull-left">
						<a href="<c:url value="/home/account/address"/>"
							style="margin-bottom: 30px;"
							class="btn btn-danger text-white btn-lg btn-block">Quay lại</a>
					</div>
					<div class="col-md-3 pull-right">

						<button style="margin-bottom: 30px;"
							class="btn btn-primary text-white btn-lg btn-block" type="submit">Cập
							nhật</button>
					</div>

				</div>

				



				<input class="billing_address_1" name="province" type="text"
					value="${model.province}">
				<input class="billing_address_2" name="district" type="text"
					value="${model.district}">
			<!-- 	<input class="billing_address_1" name="" type="hidden" value="">
				<input class="billing_address_2" name="" type="hidden" value=""> -->





			</form:form>

</div>
		</div>
		<!-- /.col-lg-9 -->

	</div>
	<content tag="script"> <script
		src='https://cdn.jsdelivr.net/gh/vietblogdao/js/districts.min.js'></script>
	<script>
		/* thêm các thành phố khi load trang */
		var arra = $(".billing_address_1").val();
		var arraa = $(".billing_address_2").val();
		console.log(arra);
		$('select[name="chon_quan_huyen"] ').on(
				'change',
				function() {
					var target = $(this).children('option:selected')
					target.attr('selected', '')
					$('select[name="chon_quan_huyen"] option').not(target)
							.removeAttr('selected')
					var address_2 = target.text()
					$('input.billing_address_2').attr('value', address_2);
				})

		$('select[name="chon_tinh_thanh"]')
				.each(
						function() {
							var $this = $(this), stc = '';
							c
									.forEach(function(name, index) {
										index += +1;

										if (arra == name) {
											
											stc += '<option value=' + index + ' selected="selected">'
													+ name + '</option>'
										} else {
											stc += '<option value=' + index + '>'
													+ name + '</option>'
										}
										$this
												.html('<option value="">Tỉnh / Thành phố</option>'
														+ stc)
									})
							$this.on('change', function(i) {
								i = $this.children('option:selected').index()
								var address_1 = $this.children(
										'option:selected').text();
								$('input.billing_address_1').attr('value',
										address_1)
								var str = ''
								if (i != '') {
									arr[i - 1].forEach(function(name, index) {
										str += '<option value="' + index + '">'
												+ name + '</option>'
										$('select[name="chon_quan_huyen"]')
												.html(
														'<option value="">Quận / Huyện</option>'
																+ str)
									})
								}
							})
							if (arra != null) {
								console.log("da check ");
								i = $this.children('option:selected').index()
								var address_1 = $this.children(
										'option:selected').text();
								$('input.billing_address_1').attr('value',
										address_1)
								var str = '';
								if (i != '') {
									arr[i-1].forEach(function(name, index) {
												if (arraa == name) {
													console.log(name);
													str += '<option value="' + index + '" selected="selected">'
															+ name
															+ '</option>'
												}
												str += '<option value="' + index + '">'
														+ name + '</option>'
												$(
														'select[name="chon_quan_huyen"]')
														.html(
																'<option value="">Quận / Huyện</option>'
																		+ str)
											})
								}
							}

						})
	</script> </content>
</body>
</html>