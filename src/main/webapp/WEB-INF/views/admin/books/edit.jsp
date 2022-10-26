<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"
      prefix="fmt" %>
<html>
<head>
<title>Chỉnh sửa bài viết</title>
</head>
<body>
	<div class="main-content">
		<div class="main-content-inner">
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {

					}
				</script>
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">Trang
							chủ</a></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<div class="page-header"> <h2 class="tm-block-title d-inline-block">Cập nhật sản phẩm</h2></div>
				<!-- /.page-header -->
				<div class="row">
			<div class="col-xs-12">					
						<!-- PAGE CONTENT BEGINS FORM -->
       
     			<c:url var="updateURL" value="/admin/book/edit/${model.id}"/>
                <form:form action="${updateURL}" method="post" commandName="model" enctype="multipart/form-data">
					
					<input type="hidden" id="wareHouseId" name="wareHouseId" class="listWW" value="">
					
					<!-- 	BUTTON -->
							<div class="clearfix form-actions"
								style="margin-top: 0px; padding: 8px 20px 8px 20px; background-color: white; border-bottom: 1px solid #E5E5E5; border-top: 0 solid #E5E5E5;">
								<div class="col-md-offset-9 col-md-3 ">
									<a class="btn btn-danger text-white "
										href="<c:url value="/admin/book/list?page=1" />"> <i
										class="ace-icon fa fa-arrow-left icon-on-left"></i>Quay lại
									</a> &nbsp; &nbsp; &nbsp;
									<button class="btn btn-info " type="submit"
										id="btnAddOrUpdateBook">
										<i class="ace-icon fa fa-check bigger-110"></i> Cập nhật
									</button>

								</div>
							</div>
                   <div class="row">
                        <div class="col-md-7">
                            <div class="form-group">
                                <label for="bookCode">Mã sách
                                </label>
                                <form:input path="bookCode" type="text" class="form-control"/>
                                <form:errors path="bookCode" style="color:red;" cssClass="error" />
                              </div>
                          <div class="form-group">
                            <label for="title">Tiêu đề
                            </label>
                            <form:input path="title" type="text" class="form-control validate"/>
                            <form:errors path="title" style="color:red;" cssClass="error" />
                          </div>
                          <div class="form-group">
                            <label for="quantity">Số lượng
                            </label>
                             <form:input path="quantity" type="text" class="form-control validate" />
                            <form:errors path="quantity" style="color:red;" cssClass="error" />
                          </div>
        
                          <div class="row">
                            <div class="form-group mb-3 col-xs-12 col-sm-6">
                                <label for="price">Giá
                                </label>                                                                                      
                                <input id="price" name="price" value="<fmt:formatNumber type="number" groupingUsed = "false" value='${model.price}' />"  type="text" class="form-control validate" />
                              <form:errors path="price" style="color:red;" cssClass="error" />
                              </div>
                              <div class="form-group mb-3 col-xs-12 col-sm-6">
                                <label for="discount">Giảm giá
                                </label>
                                <input id="discount" name="discount" value="<fmt:formatNumber type="number" groupingUsed = "false" value='${model.discount}' />"  type="text" class="form-control validate" />
                              	<form:errors path="discount" style="color:red;" cssClass="error" />
                              </div>
                        </div>
        					<div class="form-group">
                            <label for="shortcontent">Mô tả ngắn</label>
                           <form:textarea path="shortContent" class="form-control validate" rows="3"/>
                            <form:errors path="shortContent" style="color:red;" cssClass="error" />
                          </div>
        
                          <div class="form-group">
                            <label for="description">Nội dung</label>
                            <form:textarea path="context" class="form-control validate" rows="3"/>
                            <form:errors path="context" style="color:red;" cssClass="error" />
                          </div>
                          <div class="form-group">
                          <div class="row">
                          <div class="col-md-6">
                            <label for="category">Thể loại</label>
                            <form:select class="custom-select tm-select-accounts"  path="categoryCode" id="categoryCode">
                              <option selected="">Chọn thể loại</option>
                              <form:options items="${category}" />
                            </form:select>
                            <form:errors path="categoryCode" style="color:red;" cssClass="error" />
                          </div>
                           <div class="col-md-6">
                          	
                            <label for="category">Nhà xuất bản</label>
                            <form:select class="custom-select tm-select-accounts"  path="publisherCode" id="publisherCode">
                              <option selected="">Chọn nhà xuất bản</option>
                              <form:options items="${publisher}" />
                            </form:select> 
                            <form:errors path="publisherCode" style="color:red;" cssClass="error" />                                              
                          </div>                        
                          </div>
                           </div>                      
                          <div class="form-group">
                          <div class="row">
                          <div class="col-md-6">
                          	<label for="category">Tác giả</label>
                            <form:select class="custom-select tm-select-accounts"  path="authorCode" id="authorCode">
                              <option selected="">Chọn tác giả</option>
                              <form:options items="${author}" />
                            </form:select>
                            <form:errors path="authorCode" style="color:red;" cssClass="error" />
                          </div>
                          </div>
                          </div><div class="form-group">
                          <div class="row">
											
															
															<form:select path="warehouseCode" class="selectWarehouse" style="width:100px;" >
															<option value="" disabled selected>-- Chọn kho --</option>
															<form:options items="${warehouse}"/>
															
															</form:select>
															
														
													<a style="margin-left: 20px;" type="button" class="btn btn-primary btn-sm "id="insertRow"> Xác nhận</a>
															
															<div id="messagesModal" class="alert alert-danger" style="display: none;"></div>
															<table id="tableWareHouse" class="table table-bordered"  width="100%">
															<thead>
																<tr>												
																	<th>Mã kho</th>
																	<th>Tên Kho</th>
																	<th>Số lượng</th>
																	<th>Thao tác</th>
																</tr>
															</thead>
															<tbody>
															
															</tbody>
															</table>
										</div></div>
                         
                         
                        </div>
                       <div class="col-md-3">
                            <img src="${pageContext.request.contextPath}/image/${model.image}" width="80%" alt="" >
                            <div class="form-group">
                                <label for="image">Hình ảnh
                                </label>
                                
                                <form:input path="imageFile" type="file" class="form-control validate" required=""/>
                               
                            </div>
                              <div class="form-group ">
                                <label for="category">Năm xuất bản</label>                                                  
                                 <form:input path="releaseYear" type="text" class="form-control validate" />
                                <form:errors path="releaseYear" style="color:red;" cssClass="error" />
                              </div>

                            <div class="form-group ">
                                <label for="stock">Status
                                </label>
                                <form:select path="status">
										<form:option value="0">Ẩn</form:option>	
										<form:option value="1">Hiển thị</form:option>	
										</form:select>
                                 <form:errors path="status" style="color:red;" cssClass="error" />
                              </div>
                              <div class="form-group ">
                                <label for="category">Chọn thẻ</label>
                            <form:select class="custom-select tm-select-accounts"  path="tagCode" id="tagCode">
                              <option selected="">Chọn thẻ</option>
                              <form:options items="${tag}" />
                            </form:select>
                             <form:errors path="tagCode" style="color:red;" cssClass="error" />
                              </div> 
                        </div>
                        </div>                    
              </form:form>
          
				</div>
				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.page-content -->
</div>
<script>
var list =${mapWareHouse};
$(document).ready(function () {
	var arr = {};
	$('select[class=selectWarehouse] option').each(function() {
		var codeWareHouse = $(this).attr('value');
		var NameWareHouse = $(this).text();
		arr[""+codeWareHouse+""] = NameWareHouse;
	})
	console.table(arr);
	
	for(var key in list){
		for(var k in arr){
			if(k===key){
				console.log(arr[k]);
				var newRow = $("<tr>");
			     var cols = '';  
			     // Table columns
			     cols += '<td name="tdCodeWareHouse">'+k+'</td>';
			     cols += '<td name="tdNameWareHouse">'+arr[k]+'</td>';
			     cols += '<td><input onkeydown="SetDefault();" autocomplete="off" value="'+ list[key] +'" class="form-control aprroved-table" onkeypress="SetDefault();" onpaste="SetDefault();" oninput="SetDefault();" type="text" name="handle" ></td>';
			     cols += '<td><button class="btn btn-danger rounded-0" id ="deleteRow"><i class="fa fa-trash"></i></button</td>';

			     // Insert the columns inside a row
			     newRow.append(cols);

			     // Insert the row inside a table
			     $("table").append(newRow); 
			} 
		}
		
	}
	var table = $('#tableWareHouse');
	
  	var lists = [];
  
  
  	$('tr', table).each(function() {
  		var list = [];
  		var code = null;
  		var quantity = null;
  	    $('td', $(this)).each(function(i,e) {
  	       	
  	        if(i===0){
  	        	code = $(this).text();
  	        	list.push(code);
  	        }
  	        if(i===2 ){
  	        	quantity = $(this).find("input").val();
  	        	list.push(quantity);
  	        }
  	    }); 
  	  lists.push(list); 
  	});
  	$('input.listWW').attr('value',  lists);

	
	
	 
})
</script>


<script>
//create new warehouse table for book
var myMap = new Map();
	  $("#insertRow").on("click", function (event) {
		  
		  //close message error
		  document.getElementById("messagesModal").innerHTML = "";
		  const container = document.getElementById('messagesModal');
		  container.style.display = "none";		
		  
		  var selectedcode = $(".selectWarehouse option:selected").val();
		  var selectedtext = $(".selectWarehouse option:selected").text();
	      event.preventDefault();

	      var arr = [];
			 $('td[name=tdCodeWareHouse]').each(function() { 
				 var code = $(this).text();
		    	 arr.push(code);	
		  });

	      if(arr.length!=0){
	    		  if(arr.includes(selectedcode) === true){
		    			container.insertAdjacentText('beforeend', 'Không thành công');
		    			container.style.display = "block";
	    		  }else if (selectedcode === "") {
						container.insertAdjacentText('beforeend',
						'Chưa chọn kho');
				container.style.display = "block";
				}else {
	    			  var newRow = $("<tr>");
	    		      var cols = '';
	    		    
	    		      cols += '<td name="tdCodeWareHouse">'+selectedcode+'</td>';
	    		      cols += '<td name="tdNameWareHouse">'+selectedtext+'</td>';
	    		      cols += '<td><input onkeydown="SetDefault();" autocomplete="off" class="form-control aprroved-table" onkeypress="SetDefault();" onpaste="SetDefault();" oninput="SetDefault();" type="text" name="handle" ></td>';
	    		      cols += '<td><button class="btn btn-danger rounded-0" id ="deleteRow"><i class="fa fa-trash"></i></button</td>';
	    		      newRow.append(cols);
	    		      $("table").append(newRow);
	    	 }
	      }else{
	    	  if (selectedcode === "") {
					container.insertAdjacentText('beforeend',
							'Chưa chọn kho');
					container.style.display = "block";
				} else {
	    	  var newRow = $("<tr>");
		      var cols = '';
		     
		      cols += '<td name="tdCodeWareHouse">'+selectedcode+'</td>';
		      cols += '<td name="tdNameWareHouse">'+selectedtext+'</td>';
		      cols += '<td><input onkeydown="SetDefault();" autocomplete="off" id="mapw.value" class="form-control aprroved-table" onkeypress="SetDefault();" onpaste="SetDefault();" oninput="SetDefault();" type="text" name="mapw.value" ></td>';
		      cols += '<td><button class="btn btn-danger rounded-0" id ="deleteRow"><i class="fa fa-trash"></i></button</td>';

		      // Insert the columns inside a row
		      newRow.append(cols);

		      // Insert the row inside a table
		      $("table").append(newRow);
				}
	      } 
	     
	  });
	  // Remove row when delete btn is clicked
	  $("table").on("click", "#deleteRow", function (event) {
	      $(this).closest("tr").remove();     
	  });
	  
</script>


<script type="text/javascript">
function SetDefault() {
	$('.aprroved-table').on("input propertychange",function(){
		
		var table = $('#tableWareHouse');
		
	  	var lists = [];
	  
	  
	  	$('tr', table).each(function() {
	  		var list = [];
	  		var code = null;
	  		var quantity = null;
	  	    $('td', $(this)).each(function(i,e) {
	  	       	
	  	        if(i===0){
	  	        	code = $(this).text();
	  	        	list.push(code);
	  	        }
	  	        if(i===2 ){
	  	        	quantity = $(this).find("input").val();
	  	        	list.push(quantity);
	  	        }
	  	    }); 
	  	  lists.push(list); 
	  	});
	  	$('input.listWW').attr('value',  lists);
	});

}
	
</script>
</body>
</html>
