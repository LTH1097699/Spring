 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="cartList" value="/home/cart/list" />
<c:url var="checkOut" value="/home/order"/>
<c:url var="getIdBookCheckOut" value="/checkout"/>
<!DOCTYPE div PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trang chủ</title>
</head>

<body>
    <div class="row">
        <div class="col-md-9">
            <div class="ibox" style="margin-top: 24px;">
                <div class="page-title title-buttons">
                    <div class="page-title-container">
                        <h3 style="display: inline-block; width: auto;">Giỏ hàng</h3>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table class="table shoping-cart-table">
                            <thead>
                                <th><input type="checkbox" onclick="toggle(this);" /></th>
                                <th>Sản phẩm</th>
                                <th>Số lượng</th>
                                <th>Thành tiền</th>
                                <th></th>
                            </thead>
                            <tbody>
                                <fmt:setLocale value="vi_VN" />
                                <c:forEach var="item" items="${cart}">
                                    <tr> 
                                        <td width="50px;">     
                                       <c:if test="${ item.value.book.quantity > 0 }"  >
                                        	
                                         <input name="chosed_book" value="${item.key}"
                                                type="checkbox" />
                                            
                                        </c:if>  
                                        </td>
                                        <td width="auto">
                                            <div class="row">
                                                <div class="col-md-6" style="max-width: 150px; height: 200px;">
                                                    <img style="width: 100%; height: 100%;" alt=""
                                                        src="${pageContext.request.contextPath}/image/${item.value.book.image}" />
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="row">
                                                        <h3>
                                                            <a href="#" class="text-navy">${item.value.book.title }
                                                            </a>
                                                        </h3>
                                                    </div>
                                                    <div class="row">
                                                        <fmt:formatNumber type="currency">${item.value.book.price}
                                                        </fmt:formatNumber>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td width="125">
                                       <c:if test="${ item.value.book.quantity > 0 }"  > 
                                        	<input type="number" id="quantity-card-${item.key}"
                                                value="${item.value.quantity}" class="form-control" placeholder="1">
                                            <span id="errorQuantity" hidden style="color:red"></span>
                                            <input type="hidden" id="stock-quantity-${item.key}"
                                                value="${item.value.book.quantity}">
                                   </c:if>
                                        <c:if test="${ item.value.book.quantity <= 0 }"  >               	
                                            <span id="errorQuantity" style="color:red">Đã hết hàng</span>
                                        </c:if>   
                                        </td>
                                        <td width="105">
                                            <h4>
                                                <fmt:formatNumber type="currency">${item.value.totalPrice}
                                                </fmt:formatNumber>
                                            </h4>
                                        </td>
                                        <c:url var="editCart" value="/cart/edit" />
                                        <td>

                                            <a onclick="editQuantityCard()" data-id="${item.key}"
                                                class="btn btn-primary text-white edit-cart">
                                                Cập nhật
                                            </a>
                                            <c:url var="deleteCart" value="/cart/delete">
                                                <c:param name="id">${item.key}</c:param>
                                            </c:url>
                                            <a href="${deleteCart}" class="btn btn-danger">
                                                Xóa
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="ibox-content"></div>
            </div>
            <div class="row" style="margin-bottom: 54px;">
                <div class="col-md-6">
                    <button class="btn btn-danger">
                        <i class="fa fa-arrow-left"></i> Tiếp tục mua hàng
                    </button>
                </div>
                <div class="col-md-6">
                    <button id="checkOut" class="btn btn-primary pull-right">
                        <i class="fa fa fa-shopping-cart"></i> Thanh toán
                    </button>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="ibox" style="margin-top: 24px;">
                <div class="ibox-title">
                    <h5>Tổng giỏ hàng</h5>
                </div>
                <div class="ibox-content">
                    <span> Tổng tiền </span>
                    <h2 class="font-bold">
                        <fmt:formatNumber type="currency">${TotalPricecart }</fmt:formatNumber>
                    </h2>
                </div>
                <div class="ibox-content">
                    <span> Tổng số lượng </span>
                    <h2 class="font-bold">
                        ${TotalQuantitycart }
                    </h2>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal thông báo chọn sản phẩm -->
    <div class="modal" tabindex="-1" role="dialog" id="ModalInThanhToan">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Thông báo</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>Bạn chưa chọn sản phẩm để thanh toán.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div> <!-- Modal thông báo chọn sản phẩm -->

    <content tag="script">
        <!-- script để chọn tất cả checkbox -->
        <script>
            function toggle(source) {
                var checkboxes = document
                    .querySelectorAll('input[type="checkbox"]');
                for (var i = 0; i < checkboxes.length; i++) {
                    if (checkboxes[i] != source)
                        checkboxes[i].checked = source.checked;
                }
            }
        </script>
        <!-- script cho button cập nhập số lượng sản phẩm -->
        <script>
            $(".edit-cart").on("click", function () {

                var id = $(this).data("id");
                var stockQuantity = $("#stock-quantity-"+ id).val();
				console.log(stockQuantity);
                var quantity = $("#quantity-card-" + id).val();
                if (quantity <= 0) {
                    $("#quantity-card-" + id).val(1);
                    $("#errorQuantity").text("Số lượng nhỏ nhất là 1");
                    $('#errorQuantity').removeAttr("hidden");

                } 
                
                else if (parseInt(quantity) > parseInt(stockQuantity)) {
                	console.log(quantity)
                	console.log(stockQuantity)
                    $("#quantity-card-" + id).val(stockQuantity);
                    $("#errorQuantity").text("Số lượng đặt tối đa là " + stockQuantity);
                    $('#errorQuantity').removeAttr("hidden");
                } else {
                    $.ajax({
                        url: "${editCart}",
                        type: "GET",
                        contentType: "application/json",
                        dataType: 'text',
                        data: 'id=' + id + '&quantity=' + quantity,
                        success: function (data) {
                            window.location.href = "${cartList}";
                        },
                        error: function (e) {
                            console.log("ERROR: ", e);
                        }
                    });
                }
            })
        </script>
        <!-- script cho button thanh toán -->
        <script>
            $("#checkOut").on("click", function (e) {
                e.preventDefault();
                var myMap = {};
                $('input[name=chosed_book]:checked').each(function (index, value) {
                    console.log("checkkkkkkk");
                    var idBook = $(this).attr('value');
                    var stockQuantity = $("#stock-quantity-"+ idBook).val();	
                    var quantityOfIdBook = $("#quantity-card-" + idBook).val();
                    if (quantityOfIdBook <= 0) {
                        $("#quantity-card-" + idBook).val(1);
                        $("#errorQuantity").text("Số lượng nhỏ nhất là 1");
                        $('#errorQuantity').removeAttr("hidden");

                    }else if (parseInt(quantityOfIdBook) > parseInt(stockQuantity)) {
                    	
                        $("#quantity-card-" + idBook).val(stockQuantity);
                        $("#errorQuantity").text("Số lượng đặt tối đa là " + stockQuantity);
                        $('#errorQuantity').removeAttr("hidden");
                    }else{
                         myMap[""+idBook+""] = quantityOfIdBook;         
                    }
                   
                     
                });
                /* console.table(myMap);
                console.log(Object.keys(myMap).length); */

                var jsonString = JSON.stringify(myMap);
			/* 	pareerror : The reason why this parsererror message occurs is that when you simply return a string or another value, it is not really Json, so the parser fails when parsing it.

				So if you remove the dataType: json property, it will not try to parse it as Json.

				With the other method if you make sure to return your data as Json, the parser will know how to handle it properly. */
                if (Object.keys(myMap).length > 0) {
                	 
                    	 $.ajax({
                             url: "${getIdBookCheckOut}",
                             type: "POST",
                             contentType: "application/json",                               
                            data: jsonString ,
                            success: function (data) {
                             	console.log(data);
                                window.location.href = "${checkOut}"; 
                             },
                             error: function (request, status, error) {
                                 console.log("ERROR: ", error);
                                 console.log("REQUEST: ", request);
                                 console.log("STATUS: ", status);
                             }
                         }); 
                } else {
                	var aa = []
                	 $('input[name=chosed_book]:checked').each(function (index, value) {
                		  var idBook = $(this).attr('value');
                		  aa.push(idBook);
                	 })
                	 if(aa.length===0){
                		 $('#ModalInThanhToan').modal('show');
                	 }
                	
                   
                }
            })
        </script>
    </content>
</body>

</html>


$.ajax({
    url: '${search-customer-name-and-city-URL}',
    type: 'GET',
    contentType: 'application/json',
    data: data,
    success: function(result){
           $("#dataTable").html(result);
    },
    error: function(error){
        console.log(error);
    }
})