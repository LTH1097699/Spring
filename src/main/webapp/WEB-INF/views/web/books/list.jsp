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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
	rel="stylesheet" />
</head>
<body>
	<div class="row">
		<div class="col-lg-3">
			<h4 class="my-4">Danh mục</h4>

			<!-- MENU -->
			<input type="hidden" name="isCheckedCategory"
				value="${categoryIsChecked}">
			<div class="header-category">
				<a style="background-color: #e3f2fd;" class="btn " type="button">
					<h6 style="margin-bottom: 0px">
						THỂ LOẠI <i class="fa fa-angle-down"></i>
					</h6>
				</a>
			</div>
			<div class="collapse-category">
				<ul class="list-group" style="border-bottom: solid 1px">
					<c:forEach var="item" items="${cate}">
						<li style="margin-left: 20px;">
							<div class="custom-control custom-checkbox">
								<input name="categoryKey" class="custom-control-input"
									id="customCheckCategory-${item.key}" type="checkbox"
									value="${item.key}"> <label
									style="font-weight: 600; color: black;"
									class="cursor-pointer font-italic d-block custom-control-label"
									for="customCheckCategory-${item.key}">${item.value}</label>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			<input type="hidden" name="isCheckedAuthor"
				value="${authorIsChecked}">
			<div class="header-author">
				<a style="background-color: #e3f2fd;" class="btn " type="button">
					<h6 style="margin-bottom: 0px">
						TÁC GIẢ <i class="fa fa-angle-down"></i>
					</h6>
				</a>
			</div>
			<div class="collapse-author" id="collapseAuthor">
				<ul class="list-group" style="border-bottom: solid 1px">
					<c:forEach var="item" items="${autho}">
						<li style="margin-left: 20px;">
							<div class="custom-control custom-checkbox">
								<input name="authorKey" class="custom-control-input"
									id="customCheckAuthor-${item.key}" type="checkbox"
									value="${item.key}"> <label
									style="font-weight: 600; color: black;"
									class="cursor-pointer font-italic d-block custom-control-label"
									for="customCheckAuthor-${item.key}">${item.value}</label>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			<input type="hidden" name="isCheckedPublisher"
				value="${publisherIsChecked}">
			<div class="header-publisher">
				<a style="background-color: #e3f2fd;" class="btn " type="button">
					<h6 style="margin-bottom: 0px">
						NHÀ XUẤT BẢN <i class="fa fa-angle-down"></i>
					</h6>
				</a>
			</div>
			<div class="collapse-publisher" id="collapsePublisher">
				<ul class="list-group" style="border-bottom: solid 1px">
					<c:forEach var="item" items="${publish}">
						<li style="margin-left: 20px;">
							<div class="custom-control custom-checkbox">
								<input name="publisherKey" class="custom-control-input"
									id="customCheckPublisher-${item.key}" type="checkbox"
									value="${item.key}"> <label
									style="font-weight: 600; color: black;"
									class="cursor-pointer font-italic d-block custom-control-label"
									for="customCheckPublisher-${item.key}">${item.value}</label>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>

			<button class="btn btn-primary" name="buttonSearchCategory">Tìm
				kiếm</button>
			<!-- MENU -->



		</div>
		<div class="col-lg-9">
			<!-- /.col-lg-3 -->



			<div class="row">
				<div class="col-md-6"></div>
				<div class="col-md-6">
					<div class="input-group rounded " style="margin-top: 10px;">
						<input type="hidden" name="isSearchedName" value="${isSearchedName}">
						<input name="searchByName" type="search" class="form-control rounded"
							placeholder="Tìm kiếm" aria-label="Search"
							aria-describedby="search-addon" value="${isSearchedName}" /> 
						<button name="btnSearchName" style="width: 15%; margin: 0 0 0;" class="btn btn-primary"> <i
							class="fa fa-search">
							
						</i>
						</button>
					</div>
				</div>

			</div>
			<div class="row">
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

							<c:forEach var="item" items="${model2}" varStatus="loop">
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
			</div>




			<div class="row" style="margin: 1px;">
				<c:forEach var="item" items="${model.listResult}">
					<c:url var="bookDetail" value="/home/book/${item.id}" />

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
												<a href="${bookDetail}">
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

									<a onclick="addCart(${item.id})"
										class="text-white btn btn-sm btn-primary btn-edit"
										data-toggle="tooltip"> Thêm vào giỏ</i>
									</a> <a href="${bookDetail}"
										class="text-white btn btn-sm btn-primary btn-edit"
										data-toggle="tooltip">Xem chi tiết</i>
									</a>
								</div>
							</div>
						</div>
					</div>

				</c:forEach>

			</div>
			<!-- /.row -->
			<div class="container">
				<ul class="pagination" id="pagination"></ul>
			</div>


		</div>
		<!-- /.col-lg-9 -->

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
		</div>

	</div>
	<script>
	var currentPage = ${model.page}  
	var totalPage = ${model.totalPage}
	/* var name = document.getElementById("nameS").value; */
	var categoryKey = {};
	
	var categor = [];
	var autho = [];
	var publishe = [];
	//Search with category
	$("button[name=buttonSearchCategory]").on('click',function() {
		categor= [];
		autho = [];
		publishe = [];
		$("input[name=categoryKey]").each(function() {
			if ($(this).is(':checked')) {
				var name = $(this).attr('value')		
				categor.push(name);
			}
		})
		$("input[name=authorKey]").each(function() {
			if ($(this).is(':checked')) {
				var name = $(this).attr('value')	
				autho.push(name);
			}
		})
		$("input[name=publisherKey]").each(function() {			
			if ($(this).is(':checked')) {
				var name = $(this).attr('value')
				publishe.push(name);
			}	
		})
		categoryKey["categoryKey"] = categor;
		categoryKey["authorKey"] = autho;
		categoryKey["publisherKey"] = publishe;
		
		$.ajax({
 			url: "${bookURL}",
 			type: "GET",
 			data: categoryKey,
 			success: function(data){
 				$("#reload").html(data);
 			}				        
 		}); 
	})

    $(function pagination() {
        window.pagObj = $('#pagination').twbsPagination({
            totalPages: totalPage,
            visiblePages: 10,
            startPage: currentPage,
            onPageClick: function (event, page) {
                if (currentPage != page){   /* nếu trang đang đứng khác khác trang request */
                	//get id after seach 
                	
                	var isSearchedName = $("input[name=isSearchedName]").attr("value");
                	var categoryChec = $("input[name=isCheckedCategory]").attr("value");
                	var categoryKeyList = [];
                	for(var i in categoryChec){
                		if(categoryChec[i] !== '[' && categoryChec[i] !== "]" && categoryChec[i] !=="," && categoryChec[i] !==" "){
                			console.log("convert"+categoryChec[i]);
                			categoryKeyList.push(categoryChec[i]);
                		}   	
                	}
                	var authorChec = $("input[name=isCheckedAuthor]").attr("value");
                	var authorKeyList = [];
                	for(var i in authorChec){
                		if(authorChec[i] !== '[' && authorChec[i] !== "]" && authorChec[i] !=="," && authorChec[i] !==" "){
                			authorKeyList.push(authorChec[i]);
                		}	
                	}
                	var publisherChec = $("input[name=isCheckedPublisher]").attr("value");
                	var publisherKeyList = [];
                	for(var i in publisherChec){
                		if(publisherChec[i] !== '[' && publisherChec[i] !== "]" && publisherChec[i] !=="," && publisherChec[i] !==" "){
                			publisherKeyList.push(publisherChec[i]);
                		}
                	}
                	var search = {}
                    search["page"] = page;
                	search["categoryKey"] = categoryKeyList;
                	search["authorKey"] = authorKeyList;
                	search["publisherKey"] = publisherKeyList;
                	search["name"] = isSearchedName;
                	console.log(search);
               		$.ajax({
                			url: "${bookURL}",
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
</script>
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
	//show which checkbox is checked
	var categoryChec = $("input[name=isCheckedCategory]").attr("value");
	$("input[name=categoryKey]").each(function() {
		var categoryId = $(this).attr('value');
		for(let i = 0; i < categoryChec.length; i++){
				if(categoryChec[i] === categoryId){
					$(this).prop("checked", true);
				}
		}
	})
	var authorChec = $("input[name=isCheckedAuthor]").attr("value");
	$("input[name=authorKey]").each(function() {
		var authorId = $(this).attr('value');
		for(let i = 0; i < authorChec.length; i++){
				if(authorChec[i] === authorId){
					$(this).prop("checked", true);
				}
		}
	})
	var publisherChec = $("input[name=isCheckedPublisher]").attr("value");
	$("input[name=publisherKey]").each(function() {
		var publisherId = $(this).attr('value');
		for(let i = 0; i < publisherChec.length; i++){
				if(publisherChec[i] === publisherId){
					$(this).prop("checked", true);
				}
		}
	})
	</script>

	<script type="text/javascript">
	//show list menu when click
	$(".header-category").click(function () {
	    $content = $(".collapse-category");
	    $content.slideToggle(500, function () {
	        return $content.is(":visible");
	    });
	});
	$(".header-author").click(function () {
	    $header = $(this);
	    //getting the next element
	    $content = $(".collapse-author");
	    //open up the content needed - toggle the slide- if visible, slide up, if not slidedown.
	    $content.slideToggle(500, function () {
	        //execute this after slideToggle is done
	        //change text of header based on visibility of content div
	        return $content.is(":visible") ? "Collapse" : "Expand";    
	    });
	});
	$(".header-publisher").click(function () {
	    $content = $(".collapse-publisher");
	    $content.slideToggle(500, function () {
	        return $content.is(":visible"); 
	    });

	});

	//show list menu when have data
	var categoryList = $("input[name=categoryKey]:checked").map(function() {
		return $(this).val();
	}).get()
	if(categoryList.length != 0){
		 $content = $(".collapse-category");
		    $content.slideToggle(500, function () {
		        return $content.is(":visible");
		    });
	}
	
	var authorList = $("input[name=authorKey]:checked").map(function() {
		return $(this).val();
	}).get()
	if(authorList.length != 0){
		 $content = $(".collapse-author");
		 $content.slideToggle(500, function () {
		     return $content.is(":visible");
		 });
	}
	
	var publisherList = $("input[name=publisherKey]:checked").map(function() {
		return $(this).val();
	}).get()
	if(publisherList.length != 0){
		 $content = $(".collapse-publisher");
		 $content.slideToggle(500, function () {
		     return $content.is(":visible");
		 });
	}
	</script>
	<script type="text/javascript">
	
	$("button[name=btnSearchName]").on('click',function() {
		var name = $('input[name=searchByName]').val();

		$.ajax({
 			url: "${bookURL}",
 			type: "GET",
 			data: {name:name},
 			success: function(data){
 				$("#reload").html(data);
 			}				        
 		}); 
		
	})
	
	
	</script>

</body>
</html>