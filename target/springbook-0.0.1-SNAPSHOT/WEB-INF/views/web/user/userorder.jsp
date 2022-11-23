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
				<a href="<c:url value='/home/account/details'/>" class="list-group-item">Thông tin tài khoản</a>
					<a href="<c:url value ="/home/account/address"/>" class="list-group-item">Thông tin địa chỉ</a>	
			</div>


		</div>
		<!-- /.col-lg-3 -->
		<div class="col-lg-9">
			<h1 class="my-4 font-weight-normal">Đơn hàng</h1>
			<c:url var="updateURL" value="/home/account/order"/>
			<form:form action="${updateURL}" class="form-horizontal" role="form"
				id="formSubmit" modelAttribute="order" method="GET">

				<div class="row">
					<table class="table">
						<thead class="thead-light">
							<tr>
							<tr>
								<th style="background-color: white;" class="center">id</th>
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
							<c:forEach var="item" items="${order.listResult}">
								<tr>
									<td class="center">${item.id}</td>
									<td>
										<div class="product-title">${item.shipDate}</div>								
									</td>
									<td><div class="product-title">${item.totalProduct}</div></td>
							 		<td class="hidden-480">${item.totalQuantity}</td>
									<td><fmt:formatNumber type="currency">${item.totalPrice}</fmt:formatNumber></td>
										
									<c:if test="${item.status == 0}">
								<td>Chờ xử lý</td>
							</c:if>
							<c:if test="${item.status == 1}">
								<td>Hoàn thành</td>
							</c:if>
							<c:if test="${item.status == 2}">
								<td>Hủy</td>
							</c:if>
							<c:if test="${item.status == 3}">
								<td>Đang xử lý</td>
							</c:if>
							<c:if test="${item.status == 5}">
								<td>Đang giao hàng</td>
							</c:if>
							<c:if test="${item.status == 6}">
								<td>Hoàn đơn</td>
							</c:if>														
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
					<input type="hidden" value="" id="page" name="page" />

				</div>


			</form:form>
		</div>
		<!-- /.col-lg-9 -->


	</div>
	<script>
		function addCart(data) {

			$.ajax({
				url : "${addcart}",
				type : "GET",
				contentType : "application/json",
				dataType : 'text',
				data : 'id=' + data,
				success : function(data) {
					console.log("sucesss");
					$("#getCodeModal").modal('show');
				},
				error : function(e) {
					console.log("ERROR: ", e);
				}
			});
		}
	</script>
	<script>
	var currentPage = ${order.page}  
	var totalPage = ${order.totalPage}
	
    $(function () {
        window.pagObj = $('#pagination').twbsPagination({
            totalPages: totalPage,
            visiblePages: 10,
            startPage: currentPage,
            onPageClick: function (event, page) {
                if (currentPage != page){   /* nếu trang đang đứng khác khác trang request */                            	
                	$('#page').val(page);           	
                	$('#formSubmit').submit();
                }
            }
        });
      
    });

</script>
</body>
</html>