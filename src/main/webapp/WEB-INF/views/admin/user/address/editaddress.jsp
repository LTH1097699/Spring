<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Thêm địa chỉ</title>
</head>
<body>
	<div class="main-content">
		<div class="main-content-inner">
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {}
				</script>
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i>
						<a href="<c:url value="/admin/home"/>">Trang chủ</a>
					</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<div class="page-header">Thêm địa chỉ</div>
				<!-- /.page-header -->
				<div class="row">
				<c:if test="${not empty message}">
					<div class="alert alert-danger">${message}</div>
				</c:if> 
					<div class="col-xs-12">					
						<!-- PAGE CONTENT BEGINS FORM -->
						<c:url var="saveURL" value='/admin/user/${model.id}/address/edit/${addressdto.id}'/>					
						<form:form action="${saveURL}" class="form-horizontal" modelAttribute="model" method="post">								
							<!-- BUTTON -->
							<div class="clearfix form-actions"
								style="margin-top: 0px; padding: 8px 20px 8px 20px; background-color: white; border-bottom: 1px solid #E5E5E5; border-top: 0 solid #E5E5E5;">
								<div class="col-md-offset-9 col-md-3 ">
									<a class="btn btn-danger text-white "
										href="<c:url value='/admin/user/edit/${model.id}' />"> <i
										class="ace-icon fa fa-arrow-left icon-on-left"></i>Quay lại
									</a> &nbsp; &nbsp; &nbsp;
									<button class="btn btn-info " type="submit"
										id="btnAddOrUpdateBook">
										<i class="ace-icon fa fa-check bigger-110"></i> Cập nhật
									</button>

								</div>
							</div>
							<form:hidden value="${addressdto.id}" path="address.id" cssClass="col-xs-10 col-sm-5"/>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-1"> Số nhà </label>
								<div class="col-sm-9">
									<form:input value="${addressdto.address}" path="address.address" cssClass="col-xs-10 col-sm-5"/>
									<form:errors path="address.address"  cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-1"> Số điện thoại </label>
								<div class="col-sm-9">
									<form:input value="${addressdto.number}" path="address.number" cssClass="col-xs-10 col-sm-5"/>
									<form:errors path="address.number"  cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-1">Tỉnh/Thành phố</label>
								<div class="col-sm-9">
									<select class="col-xs-10 col-sm-5" name="chon_tinh_thanh" >
									<option value="">Tỉnh / Thành phố</option>
								</select>
								<form:errors path="address.province"  cssClass="error" />
								<form:hidden value="${addressdto.province}"  path="address.province" cssClass="col-xs-10 col-sm-5 billing_address_1"/>
								
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-1">Quận/Huyện </label>
								<div class="col-sm-9">
									<select class="col-xs-10 col-sm-5" name="chon_quan_huyen" >
									<option value="">Quận / Huyện</option>
								</select> 
								<form:errors path="address.district"  cssClass="error" />
								<form:hidden value="${addressdto.district}"  path="address.district" cssClass="col-xs-10 col-sm-5 billing_address_2"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-1">Xã/Thị trấn </label>
								<div class="col-sm-9">
										<select class="col-xs-10 col-sm-5" name="chon_xa_thitran" >
									<option value="">Xã / Thị trấn</option>
								</select>
								<form:errors path="address.ward"  cssClass="error" />
								<form:hidden value="${addressdto.ward}"  path="address.ward" cssClass="col-xs-10 col-sm-5 billing_address_3"/> 
								</div>
							</div>
							
							
						</form:form>
					</div>
				</div>
				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.page-content -->
	<script type="text/javascript">
	
	var ward = $('input.billing_address_3').val();
	var district = $('input.billing_address_2').val();
	var province = $('input.billing_address_1').val();
	console.log(district);
	
	
var json= ${jsonn};
		$('select[name="chon_tinh_thanh"]').each(function() {
							
							var $this = $(this);
							stc = '';
							json.forEach(function(vv, index) {	
								if(json[index].name == province){
									stc += '<option selected="selected" value=' + json[index].id + '>'+ json[index].name + '</option>'
								}else{
									stc += '<option value=' + json[index].id + '>'+ json[index].name + '</option>'
								}
								
								
								}) 
								$this.html('<option value="0">Tỉnh / Thành phố</option>'+ stc)
								
							
							$(this).on('change', function(i) {
								/* reset value */
								$('select[name="chon_xa_thitran"]').html('<option value="">Xã / Thị trấn</option>'+ "");
								$('input.billing_address_3').attr('value',"")
								$('input.billing_address_2').attr('value',"")
								
								i = $('select[name="chon_tinh_thanh"] :selected').val();
								
								var address_1 = $('select[name="chon_tinh_thanh"] :selected').text();
								$('.billing_address_1').attr('value',address_1)
							
								var str = ''
								if (i != '') {
									
									json[i-1].districts.forEach(function(name, index) {
										
										str += '<option value="' + index + '">'+ json[i-1].districts[index].name + '</option>'
										$('select[name="chon_quan_huyen"]').html('<option value="">Quận / Huyện</option>'+ str)
									}) 
								}
							})
							
							if(district != null){
								i = $('select[name="chon_tinh_thanh"] :selected').val();
								var str = ''
								json[i-1].districts.forEach(function(name, index) {
									if(json[i-1].districts[index].name == district){
										str += '<option selected="selected" value="' + index + '">'+ json[i-1].districts[index].name + '</option>'
									}else{
										str += '<option value="' + index + '">'+ json[i-1].districts[index].name + '</option>'
									}
									
									$('select[name="chon_quan_huyen"]').html('<option value="">Quận / Huyện</option>'+ str)
								}) 
							}
			})						

	</script>
	<script type="text/javascript">
	$('select[name="chon_quan_huyen"]').on('change', function(i,y) {
		y = $('select[name="chon_quan_huyen"] :selected').val();
		i = $('select[name="chon_tinh_thanh"] :selected').val();
		
		var address_2 = $('select[name="chon_quan_huyen"] :selected').text();
		$('input.billing_address_2').attr('value',address_2)
		
		
		var str = ''
		if (y != '') {	
			json[i-1].districts[y].wards.forEach(function(name, index) {
				
				str += '<option value="' + index + '">'+ json[i-1].districts[y].wards[index].name + '</option>'
				$('select[name="chon_xa_thitran"]').html('<option value="">Xã / Thị trấn</option>'+ str)
			}) 
		}
	})
	if(ward != null){
		y = $('select[name="chon_quan_huyen"] :selected').val();
		i = $('select[name="chon_tinh_thanh"] :selected').val();
								var str = ''
									json[i-1].districts[y].wards.forEach(function(name, index) {
										if(json[i-1].districts[y].wards[index].name == ward){
											str += '<option selected="selected" value="' + index + '">'+ json[i-1].districts[y].wards[index].name + '</option>'
										}else{
											str += '<option value="' + index + '">'+ json[i-1].districts[y].wards[index].name + '</option>'
										}
										
										$('select[name="chon_xa_thitran"]').html('<option value="">Xã / Thị trấn</option>'+ str)
									}) 
							}
	
	
	
	
	$('select[name="chon_xa_thitran"]').on('change', function() {
		var address_3 = $('select[name="chon_xa_thitran"] :selected').text();
		$('input.billing_address_3').attr('value',address_3)

	})		
	
	
	</script>
</body>




</html>
