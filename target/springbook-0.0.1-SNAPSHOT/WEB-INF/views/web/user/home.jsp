<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang chủ</title>
</head>
<body>
<div class="mt-4"></div>
	<div class="row">
		
		<div class="col-lg-3">

			

			<div class="list-group">
					<a href="<c:url value='/home/account'/>" class="list-group-item">Thông tin người dùng</a>
					<a href="<c:url value='/home/account/order'/>" class="list-group-item">Đơn hàng</a>				
			</div>
			<div class="list-group">
					<a href="<c:url value='/home/account/details'/>" class="list-group-item">Thông tin tài khoản</a>
					<a href="<c:url value ="/home/account/address"/>" class="list-group-item">Thông tin địa chỉ</a>	
			</div>


		</div>
		<!-- /.col-lg-3 -->
		<div class="col-lg-9">
		<h1 class="my-4 font-weight-normal">Thông tin người dùng</h1>
		
		<form action="#" id="formSubmit" method="get">
			
				<h4 class="my-5 font-weight-normal border-bottom" style="margin-bottom: 1rem !important;">Thông tin tài khoản</h4>
				
				<div class="row">
				<!-- <h5 class="font-weight-bold">Thông tin liên hệ</h5> -->
						<div class="col-md-6">
						
						
						<h6 class="font-weight-normal">Họ và tên</h6>
						<h6 class="font-weight-normal">Email</h6>
						
						</div>
						<div class="col-md-6">
						<h6 class="font-weight-normal">${model.fullName}</h6>
						<h6 class="font-weight-normal">${model.userName}</h6>
						</div>
				</div>
				
				<h4 class="my-5 font-weight-normal border-bottom" style="margin-bottom: 1rem !important;}">Đơn hàng gần đây</h4>
				<div class="row">
					<table class="table">
						<thead class="thead-light">
							<tr>
							<tr>
								<th style="background-color: white;" class="center">Id</th>
								<th style="background-color: white;">Ngày đặt</th>
								<th style="background-color: white;" class="hidden-xs">Tổng sản phẩm</th>
								<th style="background-color: white;" class="hidden-480">Số
									lượng</th>
								<th style="background-color: white;">Tổng tiền</th>
								<th style="background-color: white;">Trạng thái</th>
								<th style="background-color: white;">Thao tác</th>
							</tr>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${order}">
								<tr>
									<td class="center">${item.id}</td>

									<td>
										<div class="product-title">${item.shipDate}</div>
										

									</td>
									<td><div class="product-title">${item.totalProduct}</div></td>
									<td class="hidden-480">${item.totalQuantity}</td>
									<td><fmt:formatNumber type="currency">${item.totalPrice}</fmt:formatNumber></td>
									
									<td class="hidden-xs">${item.status}
									</td>
									
									<td><c:url var="detailOrder"
											value="/home/account/order/${item.id}" /> <a
										class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
										title="Xem chi tiết" href='${detailOrder}'>Xem chi tiết </a></td>

								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
				<!-- /.row -->
				<div class="container">
					<ul class="pagination" id="pagination"></ul>
					<input type="hidden" value="" id="page" name="page" /> <input
						type="hidden" value="" id="limit" name="limit" />

				</div>

			
		</form>
		</div>
		<!-- /.col-lg-9 -->

		<!-- Modal -->
		<div class="modal fade" id="getCodeModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">

						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>Thêm sản phẩm vào giỏ hàng thành công</p>
					</div>
					<div class="modal-footer">

						<button
							style="padding: 10px; background-color: steelblue; text-shadow: none"
							type="button" class="btn btn-primary close " data-dismiss="modal">
							Tiếp tục mua</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Thanh toán</button>
					</div>
				</div>
			</div>
		</div>

	</div>
	<script>
	function addCart(data) {
		
		$.ajax({
			url: "${addcart}",
			type: "GET",
			contentType: "application/json",
			dataType : 'text',
			data: 'id='+data,
			success : function(data){
				console.log("sucesss");		
			    $("#getCodeModal").modal('show'); 	    
			},error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	}
	
	</script>
</body>
</html>