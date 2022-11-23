<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglib.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="searchBook">
								<h4>Sản phẩm</h4>
							
								<div class="input-group" style="margin: 0px 5px 0;">
																	

																	<input title="search" name="keyword" id="keyword" type="text" class="form-control search-query">
																	<span class="input-group-btn">
																		<button name="searchBookToNewOrder" type="button" class="btn btn-inverse btn-white">
																			<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
																			Search
																		</button>
																	</span>
																</div>
								<c:if test="${not empty message_search}">
											<h5 class="alert alert-${alert_search}">${message_search}</h5>
								</c:if>
								<div class="form-group" style="margin: 0px 5px 0;">
									
									<div class="tbl-header" >
									<table class="table table-striped table-bordered table-hover" style="width: 100%;margin-bottom:0px;">
										<thead>
											<tr>
												<th width="5%"><input type="checkbox" onclick="toggle(this);" /></th>
												<th width="50%">Tên sách</th>
												<th width="25%">Giá</th>
												<th width="20%">Số lượng</th>
											</tr>
										</thead>
									</table>
								</div>
								<div style="width: 100%; overflow: auto; max-height: 400px;">
									<table class="table table-striped table-bordered table-hover" style="width: 100%;">
									
									<c:forEach var="item" items="${book}">
										<tr>
											<td width="5%">
											<c:if test="${ item.quantity > 0 }"  >
                                        	
                                        
                                          <%--  <input name="chosedBookIncart" id="chosedBookIncart" value="${item.id}" type="checkbox"> --%>
                                           <form:checkbox path="chosedBookIncart" value="${item.id}"  />
                                        </c:if>  
                                        </td>
											<td width="50%">${item.title}</td>
											<td width="25%">${item.price}</td>
											<td width="20%">${item.quantity}</td>
										</tr>
									</c:forEach>
									
									</table>
								</div>
								</div>
	</div>

