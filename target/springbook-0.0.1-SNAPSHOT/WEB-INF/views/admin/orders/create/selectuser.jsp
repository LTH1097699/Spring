<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url var="userURL" value="/admin/user-list" />
<c:url var="deleteURL" value="/user-delete" />

<!DOCTYPE html >
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Chọn khách hàng cho đơn hàng</title>
</head>

<body>
	<div class="main-content">
		
		<form:form action="" id="formSubmit"
			method="get">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="<c:url value="/admin/home" />">Trang
								chủ</a></li>
					</ul>
					<!-- /.breadcrumb -->
				</div>
				<div class="page-content">
				<div class="page-header"> <h2 class="tm-block-title d-inline-block">Chọn khách hàng</h2></div>
				<!-- /.page-header -->
				<div class="row">
					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box table-filter">
								<div class="table-btn-controls">
								
								<div class="pull-left tableTools-container">									
									<div class="form-group">	
								<form action=""${userURL}" method="get">
									<input id="nameS" name="name" placeholder="search">
									<button type="submit">Tìm kiếm</button>
								</form>								
										
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
                                                    <th>Tên tài khoản</th>
													<th>Họ và tên</th>
													<th>Thao tác</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="item" items="${model.listResult}">
													<tr>
                                                        <td>${item.id}</td>
														<td>${item.userName}</td>                                               
														<td>${item.fullName}</td>
														<td>
                                                            <c:url var="createUrl" value="/admin/order/create/user/${item.id}"/>
																
                                                           <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip" title="Xác nhận"
															href='${createUrl}'> Xác nhận
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
                if (currentPage != page){   /* nếu trang đang đứng khác khác trang request */               
                	$('#limit').val(2);
                	$('#page').val(page);
                	$('#name').val(name)
                	$('#formSubmit').submit(); /* when submit from formId,it will getdata to display  */
                }else{                	
                	$('#limit').val(2);
                	$('#page').val(1);
                	$('#name').val(name)
                } 
            }
        });
      
    });
    
  

</script>
</body>

</html>