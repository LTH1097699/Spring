<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="subviewOrderUser" class="row">
	<div class="col-xs-12">
		<div class="table-responsive">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Id</th>
						<th>Ngày lập</th>
						<th>Tổng sản phẩm</th>
						<th>Tổng số lượng</th>
						<th>Tổng tiền</th>
						<th>Trạng thái</th>
						<th>Thao tác</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${order.listResult}">
						<tr>
							<td>${item.id}</td>

							<td><fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${item.shipDate}" /></td>
							<td>${item.totalProduct}</td>
							<td>${item.totalQuantity}</td>
							<td>${item.totalPrice}</td>
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
							<td><c:url var="UpdateURL"
									value="/admin/order/edit/${item.id}" /> <a
								class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
								title="Xem chi tiết" href='${UpdateURL}'> <i
									class="fa fa-pencil-square-o" aria-hidden="true"> Xem chi
										tiết</i>
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="container">
			<ul class="pagination" id="pagination"></ul>
			<input type="hidden" value="" id="page" name="page" /> 
		</div>
		<c:url var="pageOrderURL" value="/sub/user/order/${userId}" />
	 <script>
	var currentPage = ${order.page}  
	var totalPage = ${order.totalPage}
	
    $(function () {
        window.pagObj = $('#pagination').twbsPagination({
            totalPages: totalPage,
            visiblePages: 10,
            startPage: currentPage,
            onPageClick: function (event, page) {
            	if (currentPage != page){ 
            	$.ajax({
            		url:"${pageOrderURL}",
            		type: "GET",
            		data:{page:page},
            		success: function(data){
            			$("#subviewOrderUser").html(data);
            		}
            	})
            	}
               
            }
        });
      
    }); 
  
</script> 
	</div>

</div>
