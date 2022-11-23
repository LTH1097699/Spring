<%@ page import="com.springbook.utils.SecurityUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="cartList" value="/home/cart/list" />
<c:url var="createAddress" value="/home/account/address/create" />
<fmt:setLocale value="vi_VN" />

<!DOCTYPE div PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Thêm địa chỉ</title>
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
				<a href="" class="list-group-item">Thông tin tài khoản</a> <a
					href="<c:url value ="/home/account/address"/>"
					class="list-group-item">Thông tin địa chỉ</a>
			</div>
		</div>
		<!-- /.col-lg-3 -->
		<div class="col-lg-9">
			<div class="container" style="min-height: 786px;">
				<h1 class="my-4 font-weight-normal">Thêm địa chỉ</h1>

				<form:form action="${createAddress}" modelAttribute="model"
					method="post">


					<div class="row">
						<div class="col-md-5 mb-3">
							<label for="number">Số điện thoại</label>
							<form:input type="text" cssClass="form-control" path="number" />
							<form:errors path="number" cssClass="error" cssStyle="color:red;"/>
						</div>
					</div>
					<div class="row">
						<div class="col-md-5 mb-3">
							<label for="address">Số nhà</label>
							<form:input type="text" cssClass="form-control" path="address" />
							<form:errors path="address" cssClass="error" cssStyle="color:red;"/>
						</div>
					</div>
					<div class="row">
						<div class="col-md-5 mb-3">
						<label for="province">Tỉnh / Thành phố</label>						
								<select class="form-control" name="chon_tinh_thanh" >
									<option  value="0">Tỉnh / Thành phố</option>
								</select>
								<form:errors path="province" cssClass="error" cssStyle="color:red;"/>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-5 mb-3">
						<label for="district">Quận / Huyện</label>
								<select class="form-control" name="chon_quan_huyen" >
									<option  value="0">Quận / Huyện</option>
								</select> 
								<form:errors path="district" cssClass="error" cssStyle="color:red;"/>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-5 mb-3">	
						<label for="ward">Xã / Thị trấn</label>
								<select class="form-control" name="chon_xa_thitran"  required>
									<option value="0">Xã / Thị trấn</option>
								</select> 
								<form:errors path="ward" cssClass="error" cssStyle="color:red;"/>
						</div>
					</div>


					<div class="row">
						<!-- BUTTON -->
						
						<div class="row" style="margin-left: 10px;">
						
							<div class="col-md-6 pull-left">
								<a href="<c:url value="/home/account/address"/>"
									style="margin-bottom: 30px;width: 135%;"
									class="btn btn-danger text-white btn-lg btn-block">Quay
									lại</a>
							</div>
							<div class="col-md-6 pull-right">
								<button style="margin-bottom: 30px;width: 135%;"
									class="btn btn-primary text-white btn-lg btn-block"
									type="submit">Thêm</button>
							</div>

						</div>

					</div>

				<form:hidden path="province" class="billing_address_1"  value=""/>
				<form:hidden path="district" class="billing_address_2"  value=""/>
				<form:hidden path="ward" class="billing_address_3"   value=""/>





				</form:form>

			</div>


		</div>
		<!-- /.col-lg-9 -->

	</div>
	<content tag="script"> 
	
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