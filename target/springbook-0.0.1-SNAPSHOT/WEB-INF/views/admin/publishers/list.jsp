<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Danh sách </title>
</head>

<body>
	<div class="main-content">
	<c:url var="publisherURL" value="/admin/publisher/list" />
		<form:form action="${publisherURL}" id="formSubmit"
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
				<div class="page-header"> <h2 class="tm-block-title d-inline-block">Danh sách Nhà xuất bản</h2></div>
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
								<form action=""${publisherURL}" method="get">
									<input id="nameS" name="name" placeholder="search">
									<button type="submit">Tìm kiếm</button>
								</form>								
										
								</div>
									
									</div>
									<div class="pull-right tableTools-container">
										
										<div class="dt-buttons btn-overlap btn-group">
											<!-- THEM THỂ LOẠI -->
											<c:url var="createURL" value="/admin/publisher/create" />
											<a flag="info"
												class="dt-button buttons-colvis btn btn-white btn-primary btn-bold"
												data-toggle="tooltip" title='Thêm nhà xuất bản'
												href='${createURL}'> <span> <i
													class="fa fa-plus-circle bigger-110 purple"> Thêm nhà xuất bản</i>
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
													
													<th>Id</th>
                                                    <th>Mã nhà xuất bản</th>
													<th>Tên nhà xuất bản</th>
													
													<th>Thao tác</th>
												</tr>
											</thead>
											<tbody>
											<c:if test="${not empty mess}">
														<tr>
														<td colspan="4">${mess}</td>
														</tr>
											</c:if>
												<c:forEach var="item" items="${model.listResult}">
													<tr>
														
                                                        <td>${item.id}</td>
														
														<td>${item.codePublisher}</td>                                               
														<td>${item.namePublisher}</td>
														
														<td>
                                                            <c:url var="UpdateURL" value="/admin/publisher/edit/${item.id}"/>
                                                            <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip" title="Cập nhật nhà xuất bản"
															href='${UpdateURL}'> <i class="fa fa-pencil-square-o"aria-hidden="true"></i>
                                                            </a> 
                                                            
                                                           <c:url var="deleteURL" value="/admin/publisher/delete" />
															<a class="btn btn-sm btn-danger btn-edit delete-cart" data-id="${item.id}" data-toggle="tooltip" title="Xóa nhà xuất bản">
															<i class="fa fa-trash-o" aria-hidden="true"></i>
														    </a></td>
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