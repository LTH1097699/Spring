<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="home" value="/home"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang chủ</title>

</head>
<body>
<div class="row">
	<h1>${cart.size() }</h1>

	<div class="col-md-12">


		<div style="margin-top: 10px; padding: 5px;"
			class="text-uppercase font-weight-bold bg-secondary text-white l-1">
			<div class="ml-2">Sách nổi bật</div>
		</div>
		<div
			style="margin-bottom: 50px; border: 1px solid rgba(0, 0, 0, .125)"
			id="carouselExampleControls" class="carousel slide "
			data-ride="carousel">

			<div class="carousel-inner">

				<c:forEach var="item" items="${modelnewrelease.listResult}"
					varStatus="loop">
					<c:if test="${loop.index == 0}">
						<div class="carousel-item active">
					</c:if>

					<c:if test="${loop.index != 0}">
						<div class="carousel-item">
					</c:if>

					<div class="row">
						<div class="col-md-3" style="max-width: 300px; height: 400px;">
							<img style="width: 100%; height: 100%;" class="d-block w-100"
								src="${pageContext.request.contextPath}/image/${item.image}"
								alt="First slide">
						</div>
						<div class="col-md-9">
							<p>${item.shortContent}</p>
						</div>
					</div>
			</div>
			</c:forEach>
		</div>
		<a class="carousel-control-prev" href="#carouselExampleControls"
			role="button" data-slide="prev"> <span
			class="carousel-control-prev-icon" aria-hidden="true"></span> <span
			class="sr-only">Previous</span>
		</a> <a class="carousel-control-next" href="#carouselExampleControls"
			role="button" data-slide="next"> <span
			class="carousel-control-next-icon" aria-hidden="true"></span> <span
			class="sr-only">Next</span>
		</a>
	</div>

	<div style="margin-top: 10px; padding: 5px;"
		class="text-uppercase font-weight-bold bg-white text-black clearfix">
		<div class="d-flex justify-content-between">
			<div class="ml-2">Sách nổi bật</div>
			<div class="mr-2">Hiển thị tất cả</div>
		</div>
	</div>
	</div>
	<div class="row">

	
		<c:forEach var="item" items="${model.listResult}">
			<div class="col-lg-3 col-md-3 mb-3" style="width: 800px;">
				<div class="card h-100 ">
					<div class="product shadow-none ">	
							<a href="#">
								<div style="height: 400px;">
									<img style="width: 100%; height: 100%;" class="card-img-top"
										src="${pageContext.request.contextPath}/image/${item.image}">
								</div>
							</a>
						<div class="specifies">							
							<div style="height: 140px;">
								<div style="padding: 5px;" class="card-body">
									<h4 class="card-title">
										<!-- Name -->
										<a href="#">
											<div class="an" title="${item.title}"
												style="display: block; display: -webkit-box; height: 16px*1.3*3; line-height: 1.3; -webkit-line-clamp: 1; /* số dòng hiển thị */ -webkit-box-orient: vertical; overflow: hidden; text-overflow: ellipsis; margin-top: 1px;">
												${item.title}</div>
										</a>
									</h4>
									<fmt:setLocale value="vi_VN" />
									<!-- price -->
									<h5>
										<fmt:formatNumber type="currency">${item.price}</fmt:formatNumber>
									</h5>
									<!-- shortDescription -->
									<div class="an" title="${item.shortContent}"
										style="display: block; display: -webkit-box; height: 16px*1.3*3; line-height: 1.3; -webkit-line-clamp: 3; /* số dòng hiển thị */ -webkit-box-orient: vertical; overflow: hidden; text-overflow: ellipsis; margin-top: 10px;">
										${item.shortContent}</div>
								</div>
							</div>
							
							<a  onclick="addCart(${item.id})"
									class="text-white btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
									title="Xóa bài viết">  Thêm vào giỏ</i>
								</a>
							<a 
									class="text-white btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
									title="Xóa bài viết">Xem chi tiết</i>
								</a>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<!-- Modal -->
 <div class="modal fade" id="getCodeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
       
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Thêm sản phẩm vào giỏ hàng thành công</p>
      </div>
      <div class="modal-footer">
       
        <button style="padding: 10px;background-color: steelblue;text-shadow:none" type="button" class="btn btn-primary close " data-dismiss="modal" >
         Tiếp tục mua
        </button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Thanh toán</button>
      </div>
    </div>
  </div>
 </div>
	

	<!-- /.row -->

<c:url var="addcart" value="/addcart"/>

	<!-- /.col-lg-9 -->
	</div>
<script >
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