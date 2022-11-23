<%@include file="/common/taglib.jsp"%>
<c:url var="authorURL" value="/admin/author/list" />
<c:url var="deleteURL" value="/author/delete" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Danh sách tác giả</title>
</head>

<body>

	<div class="main-content">
	
		<form:form action="${authorURL}" id="formSubmit"
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
				<div class="page-header"> <h2 class="tm-block-title d-inline-block">Danh sách tác giả</h2></div>
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
								<form action="${authorURL}" method="get">
									<input id="nameS" name="name" placeholder="search">
									<button type="submit">Tìm kiếm</button>
								</form>								
										
								</div>
									
									</div>
								
									<div class="pull-right tableTools-container">
										
										<div class="dt-buttons btn-overlap btn-group">
											<!-- THEM THỂ LOẠI -->
											<c:url var="createURL" value="/admin/author/create" />
											<a flag="info"
												class="dt-button buttons-colvis btn btn-white btn-primary btn-bold"
												data-toggle="tooltip" title='Thêm tác giả'
												href='${createURL}'> <span> <i
													class="fa fa-plus-circle bigger-110 purple"> Thêm tác giả</i>
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
                                                    <th>Mã tác giả</th>
													<th>Tên tác giả</th>												
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
														<td>${item.codeAuthor}</td>                                               
														<td>${item.nameAuthor}</td>													
														<td>
                                                            <c:url var="UpdateURL" value="/admin/author/edit/${item.id}">
																
															</c:url> 
                                                            <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip" title="Cập nhật tác giả"
															href='${UpdateURL}'> <i class="fa fa-pencil-square-o"aria-hidden="true"></i>
                                                            </a> 
                                                            
                                                            <c:url var="deleteURL" value="/admin/author/delete" />
                                                            <a class="btn btn-sm btn-danger btn-edit delete-cart" data-id="${item.id}" data-toggle="tooltip" title="Xóa tác giả">
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
<content tag="script"> 
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
                if (currentPage != page){   /* nếu trang đang đứng khác khác trang request */               
                	$('#page').val(page);
                	$('#name').val(name)
                	$('#formSubmit').submit(); /* when submit from formId,it will getdata to display  */
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
    				console.log(id);
    				window.location.href = "${deleteURL}?id="+id;
    	  }
    	})
	}); 
</script>
</content>
</body>

</html>