<%@ page contentType="text/html; charset=UTF-8" %>
    <%@include file="/common/taglib.jsp" %>

        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
            <html>

            <head>
                <title>Cập nhật đơn hàng</title>
            </head>

            <body>
                <fmt:setLocale value="vi_VN" />
                <div class="main-content">
                    <div class="main-content-inner">
                        <div class="breadcrumbs" id="breadcrumbs">
                            <script type="text/javascript">
                                try {
                                    ace.settings.check('breadcrumbs', 'fixed')
                                } catch (e) { }
                            </script>
                            <ul class="breadcrumb">
                                <li><i class="ace-icon fa fa-home home-icon"></i> <a href="<c:url value="
                                        /admin/home" />">Trang chủ</a></li>
                            </ul>
                            <!-- /.breadcrumb -->
                        </div>
                        <div class="page-content">
                            <div class="page-header"></div>
                            <!-- /.page-header -->
                            <div class="row">
                                <div class="col-xs-12">
                                    <c:url var="updateURL" value="/admin/order/edit/${model.id}" />
                                    <form:form action="${updateURL}" class="form-horizontal" role="form" id="formSubmit"
                                        modelAttribute="model" method="POST">
                                        <div class="col-sm-12">

                                            <!-- 	BUTTON -->
                                            <div class="clearfix form-actions"
                                                style="margin-top: 0px; padding: 8px 20px 8px 20px; background-color: white; border-bottom: 1px solid #E5E5E5; border-top: 0 solid #E5E5E5;">

                                                <div class="row pull-right">
                                                    <a class="btn btn-danger text-white " href="<c:url value="
                                                        /admin/book/list?page=1" />"> <i
                                                        class="ace-icon fa fa-arrow-left icon-on-left"></i>Quay lại
                                                    </a> &nbsp; &nbsp; &nbsp;
                                                    <button class="btn btn-info " type="submit" id="btnAddOrUpdateBook">
                                                        <i class="ace-icon fa fa-check bigger-110"></i> Tạo hóa đơn
                                                        <input type="hidden" value="4" name="action" id="action">
                                                    </button>
                                                </div>

                                                <input id="order-action" name="order-action" value="">
                                            </div>
                                            <c:if test="${not empty message}">
                                                <div class="alert alert-${alert}">${message}</div>
                                            </c:if>
                                            <div class="tab-content">
                                                <div id="home3" class="tab-pane active">
                                                    <h4 class="my-5 font-weight-normal border-bottom"
                                                        style="margin-bottom: 1rem !important;">Đơn hàng</h4>
                                                    <div class="row">
                                                        <div class="col-md-6">

                                                            <h5 class="font-weight-bold">Thông tin đơn hàng</h5>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">Ngày tạo</div>
                                                                <div class="col-md-6 text-right">
                                                                    ${model.shipDate}</div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">Trạng thái</div>
                                                                <c:if test="${model.status == 0 }">
                                                                    <div class="col-md-6 text-right">Chưa xử lý
                                                                    </div>
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">

                                                            <h5 class="font-weight-bold">Thông tin khách hàng
                                                            </h5>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">Tên khách hàng
                                                                </div>
                                                                <div class="col-md-6 text-right">
                                                                    ${model.username}</div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">Email</div>
                                                                <div class="col-md-6 text-right">${model.email}
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <h5 class="font-weight-bold">Địa chỉ giao hàng</h5>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">${model.address}
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">${model.ward},
                                                                    ${model.district}</div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">
                                                                    ${model.province}</div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">SĐT:
                                                                    ${model.number}</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <h5 class="font-weight-bold">Phương thức thanh toán
                                                            </h5>
                                                            <h6 class="font-weight-normal">dd</h6>
                                                            <h6 class="font-weight-normal">số nhà </h6>
                                                            <h6 class="font-weight-normal">thành phố</h6>
                                                            <h6 class="font-weight-normal">SĐT: </h6>
                                                        </div>
                                                    </div>
                                                    <div class="space"></div>
                                                    <div class="row">
                                                        <h4 class="my-5 font-weight-normal border-bottom"
                                                            style="margin-bottom: 1rem !important;">Các sản phẩm
                                                        </h4>
                                                        <div>
                                                            <table class="table table-striped table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="background-color: white;"
                                                                            class="center">id</th>
                                                                        <th style="background-color: white;">Sản
                                                                            phẩm</th>
                                                                        <th style="background-color: white;"
                                                                            class="hidden-xs">Giá</th>
                                                                        <th style="background-color: white;"
                                                                            class="hidden-480">Số lượng</th>
                                                                        <th style="background-color: white;">
                                                                            Tổng tiền</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <c:forEach var="item" items="${model.orderDetails}">
                                                                        <tr>
                                                                            <td class="center">${item.book.id}
                                                                            </td>
                                                                            <td>
                                                                                <div class="product-title">
                                                                                    ${item.book.title}</div>
                                                                                <div class="product-title">Mã:
                                                                                    ${item.book.bookCode}</div>
                                                                            </td>
                                                                            <td class="hidden-xs">
                                                                                <fmt:formatNumber type="currency">
                                                                                    ${item.unitPrice}
                                                                                </fmt:formatNumber>
                                                                            </td>
                                                                            <td class="hidden-480">
                                                                                ${item.quantity}</td>
                                                                            <td>
                                                                                <fmt:formatNumber type="currency">
                                                                                    ${item.totalPrice}
                                                                                </fmt:formatNumber>
                                                                            </td>
                                                                        </tr>
                                                                    </c:forEach>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <!-- /.row -->
                                                    <div class="row">
                                                    
                                                        <div class="col-md-6">
                                                            
                                                        </div>
                                                        <div class="col-md-6">
                                                            <h4 class="my-5 font-weight-normal border-bottom"
                                                                style="margin-bottom: 1rem !important;">Tổng đơn
                                                                hàng</h4>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">Tổng số lượng
                                                                </div>
                                                                <div class="col-md-6 text-right">
                                                                    ${model.totalQuantity}</div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">Tổng sản phẩm
                                                                </div>
                                                                <div class="col-md-6 text-right">
                                                                    ${model.totalProduct}</div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">Tổng tiền</div>
                                                                <div class="col-md-6 text-right">
                                                                    <fmt:formatNumber type="currency">
                                                                        ${model.totalPrice}</fmt:formatNumber>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 text-left">Tổng tiền đã trả
                                                                </div>
                                                                <div class="col-md-6 text-right"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form:form>
                                </div>
                                <!-- /.col -->
                            </div>
                            <!-- /.row -->
                        </div>
                        <!-- /.page-content -->
            </body>

            </html>