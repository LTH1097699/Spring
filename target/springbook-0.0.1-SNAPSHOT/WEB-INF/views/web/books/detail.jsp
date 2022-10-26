<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="bookURL" value="/home/book" />
<c:url var="addcart" value="/cart/add" />
<c:url var="listCart" value="/home/cart/list" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang chủ</title>
</head>
<body>
	<div class="row">
		<div class="col-lg-12">
			<section class="py-5">
				<div class="container px-4 px-lg-5 mystyle"
					style="margin-top: -1rem;"></div>
				<div class="container px-4 px-lg-5 my-5">
					<div class="row gx-4 gx-lg-5 align-items-center">

						<div class="col-md-6">
							<img class="card-img-top mb-5 mb-md-0" style="width: 80%;"
								src="${pageContext.request.contextPath}/image/${model.image}"
								alt="..." />
						</div>
						<div class="col-md-6">
							<div class="row">
								<div class="small mb-1">${model.bookCode}</div>
							</div>
							<div class="row">
								<h1 class="display-5 fw-bolder">${model.title}</h1>
							</div>
							<div class="row">
								<fmt:setLocale value="vi_VN" />
								<div class="fs-5 mb-5">
									<span><fmt:formatNumber type="currency">${model.price}</fmt:formatNumber></span>

								</div>
							</div>
							<c:if test="${model.quantity == 0}">
							<div class="row">
							<div class="col-md-3">
										<p>
											Đã hêt hàng</span>
									</div>

							</div>
							</c:if>
							<c:if test="${model.quantity > 5}">
							<div class="row">
								<div class="col-md-3">
										<p>
											Còn hàng</span>
									</div>
							</div>
							<div class="row">
									<div class="col-md-6">
										<a onclick="addCart(${model.id})"
											class="text-white btn btn-sm btn-primary btn-edit"
											data-toggle="tooltip"> Thêm vào giỏ</i></a>
									</div>
									<div class="col-md-6">
										<a href="${listCart}"
											class="text-white btn btn-sm btn-primary btn-edit"
											data-toggle="tooltip"> Thanh toán</i></a>
									</div>
								</div>
								</c:if>
								
								<c:if test="${model.quantity == 5}">
								<div class="row">
								<div class="col-md-3">
										<p>
											Sắp hêt hàng</span>
									</div>
							</div>
							<div class="row">
									<div class="col-md-6">
										<a onclick="addCart(${model.id})"
											class="text-white btn btn-sm btn-primary btn-edit"
											data-toggle="tooltip"> Thêm vào giỏ</i></a>
									</div>
									<div class="col-md-6">
										<a href="${listCart}"
											class="text-white btn btn-sm btn-primary btn-edit"
											data-toggle="tooltip"> Thanh toán</i></a>
									</div>
								</div>
								</c:if>
						</div>
					</div>
				</div>
				<ul class="nav nav-tabs" id="myTab" role="tablist">
					<li class="nav-item"><a class="nav-link active" id="home-tab"
						data-toggle="tab" href="#home" role="tab" aria-controls="home"
						aria-selected="true" style="box-shadow: none; padding: 0px 40px;">Nội
							dung</a></li>
					<li class="nav-item"><a class="nav-link" id="profile-tab"
						data-toggle="tab" href="#profile" role="tab"
						aria-controls="profile" aria-selected="false"
						style="box-shadow: none; padding: 0px 40px;">Đánh giá</a></li>

				</ul>
				<div class="tab-content" id="myTabContent">
					<div class="tab-pane fade show active" id="home" role="tabpanel"
						aria-labelledby="home-tab"
						style="box-shadow: none; background-color: white;">${model.context }</div>

					<div class="tab-pane fade" id="profile" role="tabpanel"
						aria-labelledby="profile-tab"
						style="box-shadow: none; background-color: white;">

						<div class="container">
							<div class="be-comment-block">
								<c:url var="bookDetail" value="/home/book/${model.id}" />
								<c:forEach var="item" items="${comment.listResult}">
									<div class="be-comment">
										<div class="be-img-comment">
											<a href="blog-detail-2.html"> <img
												src="https://bootdey.com/img/Content/avatar/avatar3.png"
												alt="" class="be-ava-comment">
											</a>
										</div>
										<div class="be-comment-content">
											<span class="be-comment-name"> <a
												href="blog-detail-2.html">${item.user.fullName}</a>
											</span> <span class="be-comment-time"> <i
												class="fa fa-clock-o"></i>
											<fmt:formatDate type="both" dateStyle="short"
													timeStyle="short" value="${item.dateComment}" />
											</span>
											<p class="be-comment-text">${item.content}</p>
										</div>
									</div>
								</c:forEach>
								<security:authorize access="isAuthenticated()">
									<c:url var="addComment"
										value="/home/book/addcomment/${model.id}" />
									<form class="form-block">
										<div class="row">

											<div class="col-xs-12 col-sm-12">
												<div class="form-group fl_icon">
													<span>Nhập nội dung</span>
												</div>
												<div class="form-group">
													<textarea id="commentProduct" class="form-input"
														required=""></textarea>
												</div>

												<a style="width: 30%;" class="btn btn-info text-white col-md-3 pull-right addComment">Xác nhận</a>
											</div>


										</div>
									</form>
								</security:authorize>
								<security:authorize access="isAnonymous()">
									<form class="form-block">
										<div class="row">

											<div class="col-xs-12 col-sm-12">
												<div class="form-group fl_icon">
													<span>Đăng nhập để đánh giá sản phẩm</span>
												</div>



											</div>


										</div>
									</form>
								</security:authorize>
								<div class="container">
									<ul class="pagination" id="pagination"></ul>
									<input type="hidden" value="" id="page" name="page" />
								</div>
							</div>
						</div>




					</div>

				</div>
			</section>
			
		</div>
		<!-- /.col-lg-9 -->
	</div>
	
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

						<a href="${listCart}" type="button" class="btn btn-secondary">Thanh
							toán</a>
					</div>
				</div>
			</div>
		</div><!-- Modal -->
	
	
	
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
	<script>
	$(".addComment").on("click", function() {
		console.log("addComment")
		var comment = $("textarea[id=commentProduct]").val();
		console.log(comment);
		var data = {}
		data["comment"] = comment;
		$.ajax({
			url: "${addComment}",
			type: "GET",
			data: data,
			success : function(data){
				$("#reload").html(data);  
			},error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	})
	
	</script>

<!-- 	<script>
	var currentPage = ${comment.page}  
	var totalPage = ${comment.totalPage}
	/* var name = document.getElementById("nameS").value; */
    $(function () {
        window.pagObj = $('#pagination').twbsPagination({
            totalPages: totalPage,
            visiblePages: 10,
            startPage: currentPage,
            onPageClick: function (event, page) {
                if (currentPage != page){   /* nếu trang đang đứng khác khác trang request */
                	var pag =page;
                	console.log(pag);
                	var search = {}
                    search["page"] = page;
               	
               $.ajax({
                			url: "${bookDetail}",
                			type: "GET",
                			data: search,
                			success: function(data){
                				$("#reload").html(data);
                			}				        
                		   });
                
                }
           
            }
        });
      
    });
	if(currentPage != 1){
		$('#profile-tab').attr('class','nav-link active show');
		$('#profile-tab').attr('aria-selected','true');
		$('#profile').attr('class','tab-pane fade active show');
	}

</script> -->
<!-- <script>
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
	
	</script> -->
</body>
</html>