<%@include file="/common/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url var="listURL" value="/admin/book/list" />
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Danh sách sản phẩm</title>
</head>

<body>
	<div class="main-content">
		<form:form action="${listURL}" id="formSubmit"
			method="get">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="<c:url value="/admin/home"/>">Trang
								chủ</a></li>
					</ul>
					<!-- /.breadcrumb -->
				</div>
				<div class="page-content">
				<div class="page-header"> <h2 class="tm-block-title d-inline-block">Danh sách sản phẩm</h2></div>
				<!-- /.page-header -->
				<div class="row">
					<div class="row">
						<div class="col-xs-12">
							<c:if test="${not empty message}">
								<div class="alert alert-${alert}">${message}</div>
							</c:if>

							<div class="widget-box table-filter">
								<div class="table-btn-controls">
								
								<div class="pull-left tableTools-container">									
									<div class="form-group">
									<form action=""${listURL}" method="get">
									<input id="nameS" name="name" placeholder="search">
									<button type="submit">Tìm kiếm</button>
								</form>		
								</div>
									
									</div>
									<div class="pull-right tableTools-container">
										
										<div class="dt-buttons btn-overlap btn-group">
											<!-- THEM BAI VIET -->
											<c:url var="createURL" value="/admin/book/create" />
											<a flag="info"
												class="dt-button buttons-colvis btn btn-white btn-primary btn-bold"
												data-toggle="tooltip" title='Thêm bài viết'
												href='${createURL}'> <span> <i
													class="fa fa-plus-circle bigger-110 purple">Thêm sách</i>
											</span>
											</a>											
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="table-responsive">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>ID</th>
                                                    <th>Hình ảnh</th>
													<th>Thông tin sản phẩm</th>
													<th>Số lượng</th>
													<th>Giá</th>
                                                    <th>Trạng thái</th>
                                                    <th>Mô tả ngắn</th>
													<th>Thao tác</th>
												</tr>
											</thead>
											<tbody>
											<c:if test="${not empty mess}">
													<tr>
													<td colspan="8">${mess}</td>
													</tr>
											</c:if>
											
												<c:forEach var="item" items="${model.listResult}">
													<tr>
														<td>${item.id}</td>
                                                        <td width="120px;"><img width="100%" src="${pageContext.request.contextPath}/image/${item.image}" alt=""></td>
														<td>
														<h5><p> <strong>Tên sách: </strong>	${item.title}</p></h5>
														<h6><p> <strong>Mã: </strong>	${item.bookCode}</p></h6>
														
																																																																	
														</td>
                                                        <td>${item.quantity}</td>
                                                        <td width="200px;">
                                                        <fmt:setLocale value="vi_VN"/>    														    															
                                                        	<h6><p> <strong>Giá đề xuất </strong>
                                                        		<fmt:formatNumber type="currency">${item.price}</fmt:formatNumber>	
                                                        	</p></h6>
															<h6><p> <strong>Giá giảm </strong>
																<fmt:formatNumber type="currency">${item.discount}</fmt:formatNumber>	
															</p></h6>
															<h6><p> <strong>Giá bán: </strong>
																<fmt:formatNumber type="currency">${item.price-item.discount}</fmt:formatNumber>
															</p></h6>
                                                        </td>
                                                        
                                                        <c:if test="${item.status==1}">
                                                        	<td>Hiển thị</td>
                                                        </c:if>
                                                        <c:if test="${item.status==0}">
                                                        	<td>Ẩn</td>
                                                        </c:if>
                                                        
														<td width="300px;" style="overflow:break-word;word-wrap: break-word;word-break:break-all;">
															${item.shortContent}
														</td>
														
														<td width="150px;">
                                                            <c:url var="editURL" value="/admin/book/edit/${item.id}"/>
                                                            <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip" title="Cập nhật sách"
															href='${editURL}'> <i class="fa fa-pencil-square-o"aria-hidden="true"></i>
                                                            </a> 
                                                            <c:url var="deleteURL" value="/admin/book/delete" />
                                                            <a class="btn btn-sm btn-danger btn-edit delete-cart" data-id="${item.id}" data-toggle="tooltip" title="Xóa sách">
															<i class="fa fa-trash-o" aria-hidden="true"></i>
															</a>
                                                        </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								<div class="container">
									<ul class="pagination" id="pagination"></ul>
									<input type="hidden" value="" id="page" name="page" /> 
									<input type="hidden" value="" id="limit" name="limit" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</div>
		</form:form>
	</div>
	<!-- /.main-content -->
	<script>
	var currentPage = ${model.page}  
	var totalPage = ${model.totalPage}
	var name = document.getElementById("nameS").value;
    $(function () {
        window.pagObj = $('#pagination').twbsPagination({
            totalPages: totalPage,
            visiblePages: 10,
            startPage: currentPage,
            onPageClick: function (event, page) {
                if (currentPage != page){   
                	$('#page').val(page);
                	$('#name').val(name)
                	$('#formSubmit').submit();
                }  
            }
        });
      
    });
    
    $(".delete-cart").on("click", function() {
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
				window.location.href = "${deleteURL}?id="+id;
    	  }
    	})
  
	});
</script>
</body>

</html>