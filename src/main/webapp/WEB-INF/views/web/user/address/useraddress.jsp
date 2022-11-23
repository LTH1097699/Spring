<%@ page import="com.springbook.utils.SecurityUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>

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
					href="<c:url value ="/home/account/address"/>" class="list-group-item">Thông tin địa chỉ</a>
			</div>


		</div>
		<!-- /.col-lg-3 -->
		<div class="col-lg-9">
		<div class="container" style="min-height: 786px;">
			<h1 class="my-4 font-weight-normal">Thông tin địa chỉ</h1>
			
			<div class="row">
					<table class="table">
						<thead class="thead-light">
							
							<tr>
								<th style="background-color: white;" class="center">STT</th>
								<th style="background-color: white;">SĐT</th>
								<th style="background-color: white;">Địa chỉ</th>
								<th style="background-color: white;">Tỉnh/thành</th>
								<th style="background-color: white;" class="hidden-480">Quận/huyện</th>
								<th style="background-color: white;" class="hidden-xs">Phường,xã</th>
								<th style="background-color: white;">Thao tác</th>
							</tr>
					
						</thead>
						<tbody>
							<c:forEach var="item" items="${model}" varStatus="i" >
								<tr>
									<td class="center">${i.index + 1}</td>
									<td>${item.number}</td>
									<td>
										<div class="product-title">${item.address}</div>
										

									</td>
									<td class="hidden-480">${item.province}</td>
									<td class="hidden-xs">${item.district}
									</td>
									
									
									<td><div class="product-title">${item.ward}</div></td>
									<td>
									<div class="row">
									<c:url var="editAdress" value="/home/account/address/edit/${item.id}" /> 
									<a	class="btn btn-sm btn-primary btn-edit text-white" data-toggle="tooltip"
										title="Cập nhật" href='${editAdress}'>Cập nhật</a>
									<c:url var="deleteURL" value="/home/account/address/delete/" />
                                     <a class="btn btn-sm btn-danger text-white btn-edit delete-address" data-id="${item.id}" data-toggle="tooltip" title="Xóa tác giả">
											Xóa
									</a>
									</div>
									</td>

								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
				<c:url var="createAdress" value="/home/account/address/create" /> 
									<a
										class="btn btn-sm-3 col-md-3 btn-primary btn-edit" data-toggle="tooltip"
										title="Cập nhật" href='${createAdress}'>Thêm địa chỉ mới</a>
		</div>
		</div>
		<!-- /.col-lg-9 -->
	</div>

	<content tag="script"> 
	<script>
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
      			var id = $(this).data('id');		
    				
    				window.location.href = "${deleteURL}"+id;
    	  }
    	})
	}); 
	</script>
	
	
	
	<script
		src='https://cdn.jsdelivr.net/gh/vietblogdao/js/districts.min.js'></script>
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
	</script> <script>
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
								i = $this.children('option:selected').index() - 1
								var address_1 = $this.children(
										'option:selected').text();
								$('input.billing_address_1').attr('value',
										address_1)
								var str = ''

								if (i != '') {
									arr[i]
											.forEach(function(name, index) {
												if (arraa == name) {
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