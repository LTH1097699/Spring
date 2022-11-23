<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Danh sách đơn hàng</title>
</head>

<body>
	<fmt:setLocale value="vi_VN" />
	<div class="main-content">
		<c:url var="orderURL" value="/admin/order/list" />
		<form:form action="${orderURL}" modelAttribute="model" id="formSubmit" method="get">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="<c:url value="/admin/home" />">Trang
								chủ</a></li>
					</ul>
					<!-- /.breadcrumb -->
				</div>
				<div class="page-content">
				<div class="page-header"> <h2 class="tm-block-title d-inline-block">Danh sách đơn hàng</h2></div>
				<!-- /.page-header -->
				<div class="row">
					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box table-filter">
								<div class="table-btn-controls">
								
								<div class="pull-left tableTools-container">									
									<div class="form-group">	
								
									<!-- <input id="nameS" name="name" type="hidden" placeholder="search"> -->
									<div class="row">
									<div class="col-md-6">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> Chọn ngày </label>
									<div class="input-daterange input-group">
									
																	<form:input  autoComplete='off' type="text" class="input-sm form-control input-mask-date" path="start"/>
																	<span class="input-group-addon">
																		<i class="fa fa-exchange"></i>
																		
																	</span>

																	<form:input  autoComplete='off' type="text" class="input-sm form-control input-mask-date" path="end"/> 
																	
																		
																</div>
																<button id="clearDate" style="margin-top: 1px;" class="btn btn-white btn-success" type="button">Xóa ngày</button>	
									</div>
									
									<div class="col-md-3">
									<label class="col-sm-6 control-label " > Trạng thái </label>
									<div class="input-group">
									<form:select path="searchStatus">
									<form:option value="">-- Chọn trạng thái --</form:option>
									<form:option value="0">Chờ xử lý</form:option>
									<form:option value="3">Đang xử lý</form:option>
									<form:option value="2">Hủy</form:option>
									<form:option value="5">Đang giao hàng</form:option>
									<form:option value="1">Hoàn thành</form:option>
									<form:option value="6">Hoàn đơn hàng</form:option>
									</form:select>	
									</div>
									
									</div>
									<div class="col-md-3">
									
									
									<button style="margin-top: 21px;" class="btn btn-white btn-success" type="submit">Tìm kiếm</button>	
									</div>
									</div>
									
									
									
									
						
								</div>		
									</div>	
									
									<c:url var="export" value="/admin/orders/export/excel">
									
									<c:param name="searchStatus" value="${model.searchStatus}"></c:param>
									<c:param name="start" value="${model.start}"></c:param>
									<c:param name="end" value="${model.end}"></c:param>
									</c:url>
								<div class="pull-right tableTools-container">									
									<div class="form-group">	
								<a style="margin-top: 21px;" class="btn btn-white btn-success" href="${export}">Xuất Excel</a>							
								<a style="margin-top: 21px;" class="btn btn-white btn-success" href="<c:url value='/admin/order/create/user'/>">Thêm đơn hàng</a>		
								</div>	
								<div class="row">
									
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
                                                    <th>Tên khách hàng</th>												
													<th>Ngày lập</th>
													<th>Tổng sản phẩm</th>
													<th>Tổng số lượng</th>
													<th>Tổng tiền</th>
													<th>Trạng thái</th>
													<th>Thao tác</th>
												</tr>
											</thead>
											<tbody>
											<c:if test="${not empty message}">
											<tr>
											<td colspan="8">${message}</td>
											
											</tr>
											</c:if>
												<c:forEach var="item" items="${model.listResult}">
													<tr>
                                                        <td>${item.id}</td>													
														<td>${item.username}</td> 
														                                             
														<td>
														<fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${item.shipDate}" />
														</td>
														<td>${item.totalProduct}</td>
														<td>${item.totalQuantity}</td>
														<td>
															<fmt:formatNumber type="currency">
																${item.totalPrice}
															</fmt:formatNumber>
														</td>
														
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
														
														<td>
                                                            <c:url var="UpdateURL" value="/admin/order/edit/${item.id}"/>
															
                                                           <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip" title="Xem chi tiết"
															href='${UpdateURL}'> <i class="fa fa-pencil-square-o"aria-hidden="true"> Xem chi tiết</i>
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
	<script>
	var currentPage = ${model.page}  
	var totalPage = ${model.totalPage}
	
    $(function () {
        window.pagObj = $('#pagination').twbsPagination({
            totalPages: totalPage,
            visiblePages: 10,
            startPage: currentPage,
            onPageClick: function (event, page) {
                if (currentPage != page){   /* nếu trang đang đứng khác khác trang request */                            	
                	$('#page').val(page);           	
                	$('#formSubmit').submit(); /* when submit from formId,it will getdata to display  */
                }
            }
        });
      
    });

</script>
<script >
$('#clearDate').on('click',function(){
	
	$('input[name=start]').val('');
	
	$('input[name=end]').val('');
	
})

$('.input-daterange').datepicker({
	autoclose:true,
	language: "vi",
	clearBtn: true,
	
	});
$.mask.definitions['~']='[+-]';
$('.input-mask-date').mask('99/99/9999');
</script>
</body>

</html>