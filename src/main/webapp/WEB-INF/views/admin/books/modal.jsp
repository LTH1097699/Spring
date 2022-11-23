<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


	
<c:url var="allwarehouse" value="/get-all-employee" />
<!-- Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
				<div id="messagesModal" class="alert alert-danger" style="display: none;"></div>
			</div>
			<div class="modal-body">
				<!-- ++++++++++++++ -->
				<ul class="pagination" id="pagination"></ul>
						<input type="hidden" value="" id="page" name="page" />
				<div class="row">
					<div class="col-xs-12">
						<div class="table-responsive">
							<table id="ldapStreamTable" class="table table-bordered"  width="100%">
								<thead>
									<tr>
									<th><input class="editor-active" type="checkbox" onclick="toggle(this);" /></th>
										<th>Id</th>
										<th>Mã kho</th>
										<th>Tên Kho</th>
										<th>Thao tác</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>				
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
				<button type="button" class="btn btn-primary assign-warehouse">Xác nhận</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="//cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/select/1.3.4/js/dataTables.select.min.js"></script>
<!-- run a datatable -->
<script>
<c:url var="updateURL" value="/admin/warehouse/edit/" /> 
<c:url var="getWareHouseId" value="/getwarehousebyid" /> 
function choseWarehouse(data) {
	if(Object.keys(data).length !== 0 && data.constructor === Object ){
		console.log("get data");
		 console.log(data);
		for(var k in data['data']){
			 console.log(k['id']+"id"+k['code']+"code" );
		}
		
	}else {
		var table = $('#ldapStreamTable').DataTable({
		    //"sDom" : domSetting,
		    "oLanguage": {
		      "sZeroRecords": "No matching records found",
		    },
		    "iDisplayLength": 10,
		    "aaSorting": [
		      [0, 'asc']
		    ],
		    "sAjaxSource": '${allwarehouse}',
		        "columns": [
		        	{ data: "id", orderable: false,className: "dt-body-center", "defaultContent": "",
		        	
	                render: function ( data, type, row ) {
	                    if ( type === 'display' ) {
	                        return '<input name="warehouse" value="'+data+'" type="checkbox" class="editor-active">';
	                    }
	                    return data;
	                }
		        	},
		        	{ "data": "id"},
		            { "data": "code"},
		            { "data": "name"},
		            {data: "id",
		            	  render: function ( data, type, row ) {
		            	    return '<a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip" title="Cập nhật kho" href="${updateURL}'+data+'"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>';
		            	  }
		            	}
		        ]
		  });
	}
}
$(document).ready(function(data){
	
	choseWarehouse(data);
});
function createWareHouse(data) {
	if(Object.keys(data).length !== 0 && data.constructor === Object ){
		/* var d = data; */
		console.table(data)
		var arr=[];
		$('input[name=warehouse]').each(function() {
			var id = $(this).attr('value');
			arr.push(id)
		})
		if(arr.length!=0){
			for(var id in data['data']){
				for(var v in arr){
					if(id.id === v){
						table.row.add(id.id,id.code,id.name).draw(false);
					}
				}
				
			}
		}
		
		var table = $('#ldapStreamTable2').DataTable({
		    //"sDom" : domSetting,
		    "oLanguage": {
		      "sZeroRecords": "No matching records found",
		    },
		    "iDisplayLength": 10,
		    "aaSorting": [
		      [0, 'asc']
		    ],
		    "data": data.data,
		        "columns": [
		        	{ data: "id", orderable: false,className: "dt-body-center", "defaultContent": "",
		        	
	                render: function ( data, type, row ) {
	                    if ( type === 'display' ) {
	                        return '<input name="warehouse" value="'+data+'" type="checkbox" class="editor-active">';
	                    }
	                    return data;
	                }
		        	},
		        	{ "data": "id"},
		            { "data": "code"},
		            { "data": "name"},
		            {data: "id",
		            	  render: function ( data, type, row ) {
		            	    return '<a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip" title="Cập nhật kho" href="${updateURL}'+data+'"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>';
		            	  }
		            	}
		        ]
		  });
		
		
	}choseWarehouse(data);
	
}
</script>

<script>
$(document).ready(function(){
    $("#exampleModalCenter").on('hide.bs.modal', function(){
         $('.editor-active').prop('checked', false);
  });
});
</script> 
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
<script>
$(".assign-warehouse").on("click", function () {
	console.log("get it");
	var arr = [];
	$('input[name=warehouse]:checked').each(function(index, value) {
		var id =  $(this).attr('value');
		arr.push(id)
	});
	
	if(arr.length!=0){
		$('#exampleModalCenter').modal('hide');
		$.ajax({
			url : "${getWareHouseId}",
			type : "GET",
			data: 
				"ids=" + arr
			,
			success : function(data) {			
				console.log(data);	
				createWareHouse(data)
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
		
	}else{
		const container = document.getElementById('messagesModal');
		// Append text to `div` element
		container.insertAdjacentText('beforeend', 'Không thành công');
		container.style.display = "block";
	}
})

</script>

