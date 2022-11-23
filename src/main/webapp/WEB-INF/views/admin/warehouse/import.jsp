<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html >
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Danh sách Kho</title>
</head>
<body>
	<div class="main-content">
	<c:url var="listURL" value="/admin/warehouse/list/book" />
		<form:form action="${listURL}" id="formSubmit" method="get" modelAttribute="model">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a
							href="<c:url value="/admin/home"/>">Trang chủ</a></li>
					</ul>
					<!-- /.breadcrumb -->
				</div>
				<div class="page-content">
					<div class="page-header">
						<h2 class="tm-block-title d-inline-block">Quản lý số lượng kho</h2>
					</div>
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
											<!-- Chose warehouse -->
												
												<form:select path="codeWareHouse" >
												<form:option value="">-- Chọn kho --</form:option>
												<form:options items="${wareHouse}" itemValue="id" itemLabel="name"/>
												</form:select>
												<form:input placeholder="Tìm kiếm sản phẩm"  path="keyword"/>
											
												<button flag="info" type="submit"
													class="dt-button buttons-colvis btn btn-white btn-primary btn-bold"
													data-toggle="tooltip" >
													<span> <i
														class="fa fa-plus-success bigger-110 purple">Tìm kiếm</i>
												</span>
												</button>
											</div>
											

										</div>
									
										
										
										<div class="pull-right tableTools-container">
										<div class="dt-buttons btn-overlap btn-group">
												<c:url var="export" value='/export/warehouse'>
												
												<c:param name="codeWareHouse" value="${model.codeWareHouse}"/>
												<c:param name="keyword" value="${model.keyword}"/>
												</c:url>
										
												<a href="${export}" class="dt-button buttons-colvis btn btn-white btn-primary btn-bold" >
												Xuất Excel</a>

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
														<th>Sản phẩm</th>
														<th>Số lượng</th>													
														<th>Tên Kho</th>								
														<th>Thao tác</th>
													</tr>
												</thead>
												<tbody>
												<c:if test="${not empty mess}">
														<tr><td colspan="5">${mess}</td></tr>
												</c:if>
													<c:forEach var="item" items="${model.listResult}">
														<tr>
															<td>${item.id}</td>
															<td>${item.book.title}</td>
															<td>${item.quantity}</td>
															<td>${item.wareHouse.name}</td>
															<td>
															<c:url var="updateUrl" value="/admin/warehouse/book/${item.id}" />
															 <a href="${updateUrl}"
																class="btn btn-sm btn-primary btn-edit"
																data-toggle="tooltip" title="Cập nhật kho"
																> <i
																	class="fa fa-pencil-square-o" aria-hidden="true"></i>
															</a> 
															 </td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
									
								</div>
								<div class="container">
										<ul class="pagination" id="pagination"></ul>
										<input type="hidden" value="" id="page" name="page" />
										
									</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
	<!-- /.main-content -->
	<content tag="script"> <script>
	var currentPage = ${model.page}  
	var totalPage = ${model.totalPage}
    $(function () {
        window.pagObj = $('#pagination').twbsPagination({
            totalPages: totalPage,
            visiblePages: 10,
            startPage: currentPage,
            onPageClick: function (event, page) {
                if (currentPage != page){   
                	$('#page').val(page);
                	
                	$('#formSubmit').submit();
                }
            }
        });
      
    }); 
  
</script> </content>
</body>
</html>