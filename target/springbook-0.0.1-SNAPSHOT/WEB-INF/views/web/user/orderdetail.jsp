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
				<a href="<c:url value='/home/account'/>" class="list-group-item">Thông
					tin người dùng</a> <a href="<c:url value='/home/account/order'/>"
					class="list-group-item">Đơn hàng</a>
			</div>
			<div class="list-group">
				<a href="<c:url value='/home/account/detail'/>" class="list-group-item">Thông tin tài khoản</a>
					<a href="<c:url value ="/home/account/address"/>" class="list-group-item">Thông tin địa chỉ</a>	
			</div>


		</div>
		<!-- /.col-lg-3 -->
		<div class="col-lg-9">
			
			<h3 class="my-4 font-weight-normal">Đơn hàng <a href="<c:url value='/pdf/downloadRecept/${order.id}'/>"><small class="text-muted"><i class="fa fa-print"></i>In đơn hàng</small></a></h3>

			<!-- FORM -->
			<form:form modelAttribute="order" action="#" id="formSubmit"
				method="get">
				<div class="container">
					<div class="row">
						<div class="col-md-6">
							<h6 class="font-weight-bold">Thông tin đơn hàng</h6>
							<div class="row">
								<div class="col-md-6 text-left"
									style="padding: 5px 4px 6px 12px;">Mã đơn hàng</div>
								<div class="col-md-6 text-right"
									style="padding: 5px 4px 6px 12px;">${order.id}</div>
							</div>
							<div class="row">
								<div class="col-md-6 text-left"
									style="padding: 5px 4px 6px 12px;">Ngày tạo</div>
								<div class="col-md-6 text-right"
									style="padding: 5px 4px 6px 12px;"><fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${order.shipDate}" /></div>
							</div>
							<div class="row">
								<div class="col-md-6 text-left"
									style="padding: 5px 4px 6px 12px;">Trạng thái</div>
								<c:if test="${order.status == 0 }">
									
								</c:if>
									
									<c:if test="${order.status == 0}">
								<div class="col-md-6 text-right">Đã tiêp nhận</div>
							</c:if>
							<c:if test="${order.status == 1}">
							<div class="col-md-6 text-right">Hoàn thành</div>
							
							</c:if>
							<c:if test="${order.status == 2}">
							<div class="col-md-6 text-right">Hủy</div>
							
							</c:if>
							<c:if test="${order.status == 3}">
							<div class="col-md-6 text-right">Đang xử lý</div>
							
							</c:if>
							<c:if test="${order.status == 5}">
							<div class="col-md-6 text-right">Đang giao hàng</div>
								
							</c:if>
							<c:if test="${order.status == 6}">
							<div class="col-md-6 text-right">Hoàn đơn</div>
							
							</c:if>		
							</div>
						</div>
						<div class="col-md-6">

							<h6 class="font-weight-bold">Thông tin khách hàng</h6>
							<div class="row">
								<div class="col-md-6 text-left"
									style="padding: 5px 4px 6px 12px;">Tên khách hàng</div>
								<div class="col-md-6 text-right">
									<p>${user.fullName}</p>
								</div></div>
								<div class="row">
									<div class="col-md-6 text-left"
										style="padding: 5px 4px 6px 12px;">Email</div>
									<div class="col-md-6 text-right">
										<p>${user.userName}</p>
									</div>
								
							</div>






						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<h5 class="font-weight-bold" style="font-weight: 600;">Địa
								chỉ giao hàng</h5>
							<div class="row">
								<div class="col-md-6">Số nhà : ${order.address}</div>
							</div>
							<div class="row">
								<div class="col-md-6">Phường/Xã : ${order.ward}</div>
							</div>
							<div class="row">
								<div class="col-md-6">Quận/Huyện : ${order.district}</div>
							</div>
							<div class="row">
								<div class="col-md-6">Tỉnh/Thành : ${order.province}</div>
							</div>
							<div class="row">
								<div class="col-md-6">SĐT: ${order.number}</div>
							</div>
						</div>

					</div>


				</div>

				<div class="row">
					<table class="table">
						<thead class="thead-light">
							<tr>
							<tr>
								<th style="background-color: white;" class="center">id</th>
								<th style="background-color: white;">Thông tin sản phẩm</th>
								<th style="background-color: white;" class="hidden-xs">Giá</th>
								<th style="background-color: white;" class="hidden-480">Số
									lượng</th>
								<th style="background-color: white;">Tổng tiền</th>

							</tr>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${order.orderDetails}">
								<tr>
									<td class="center">${item.book.id}</td>

									<td>
										<div class="product-title">${item.book.title}</div>
										<div class="product-title">Mã: ${item.book.bookCode}</div>

									</td>
									<td class="hidden-xs"><fmt:formatNumber type="currency">${item.unitPrice}</fmt:formatNumber>
									</td>
									<td class="hidden-480">${item.quantity}</td>
									<td><fmt:formatNumber type="currency">${item.totalPrice}</fmt:formatNumber></td>

								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>

				<!-- /.row -->
			</form:form>
		</div>
		<!-- /.col-lg-9 -->
	</div>
</body>
</html>