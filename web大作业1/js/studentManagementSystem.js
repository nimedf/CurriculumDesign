// 模态框中新增按钮的点击事件
function AddRow() {
	var number=document.getElementById("number").value;	// 获取各个输入框中的值
	var studentId=document.getElementById("studentId").value;
	var name=document.getElementById("name").value;
	var college=document.getElementById("college").value;
	var professional=document.getElementById("professional").value;
	var grade=document.getElementById("grade").value;
	var theClass=document.getElementById("theClass").value;
	var age=document.getElementById("age").value;
	var table=document.getElementById("tabel");
	var row=table.insertRow();
	row.innerHTML="<tr><td><input type='checkbox'></td>" + 
					"<td>"+ number + "</td>" + 
					"<td>"+ studentId + "</td>" +
					"<td>"+ name + "</td>" +
					"<td>"+ college + "</td>" +
					"<td>"+ professional + "</td>" +
					"<td>"+ grade + "</td>" +
					"<td>"+ theClass + "</td>" +
					"<td>"+ age + "</td>" +
					"<td>" + 
					"<a href='#' class='active' title='查看'>" +
					"<span class='glyphicon glyphicon-eye-open'></span></a>" +
					"<a href='#' class='active' style='color: black;'' title='修改'>" +
					"<span class='glyphicon glyphicon-pencil'></span></a>" +
					"<a href='#' class='active' style='color: red' title='删除'>" +
					"<span class='glyphicon glyphicon-trash'></span></a></td>" +
					"</tr>";
	alert("添加成功");
	clearInput();	//	清空各个输入框
}

// 清空模态框中各个输入框
function clearInput() {
	var input=document.getElementsByTagName('input');	// 获取所有input的集合
	for (var i = 0; i < input.length; i++) {
		if (input[i].type=='text') {
			input[i].value="";
		}
	}
}

// 取消按钮的方法
function AddCancel() {
	clearInput();
}

// 删除按钮的方法
function deleteData() {
	var currents=document.getElementsByTagName('input');
	var count=0;		// 保存有几个复选框
	for (var i=0; i<currents.length-1; i++) {	// 找出选择了几个复选框
		if (currents[i].type=='checkbox') {
			if (currents[i].checked) {
				count++;
			}
		}
	}
	var index=new Array(count);		// 保存复选框对应的行
	for (var i=currents.length-1, k=0; i>=0; i--) {
		if (currents[i].checked) {
			index[k++]=i-1;
		}
	}
	if (count == 0) {
		alert("请选择一条数据!");
	}
	else {
		if (confirm("确定删除嘛？")) {
			for (var i = 0; i < index.length; i++) {	// 少循环一次，不删除表格的第一行
			if (index[i]==0) 
				continue;
			var table=document.getElementById("tabel");
			table.deleteRow(index[i]);
		}
		}
	}
}

// 是否全选的checkbox
function generations(){
	var checks=document.getElementsByClassName("checks");
	if (checks[0].checked) {		// 如果该复选框被选择，则全选下面的所有的复选框
		var currents=document.getElementsByTagName('input');
		for (var i = 0; i < currents.length; i++) {
			if (currents[i].type=='checkbox') {
				currents[i].checked=true;
			}
		}
	}
	else {
		var currents=document.getElementsByTagName('input');
		for (var i = 0; i < currents.length; i++) {
			if (currents[i].type=='checkbox') {
				currents[i].checked=false;
			}
		}
	}
}

// 删除指定行的内容
function getTableContent(node) {
	if (confirm("确定删除这一行嘛?")) {
		var tr1=node.parentNode.parentNode;		// 获取点击对象所在的行
		var table=document.getElementById("tabel");	// 获取当前table的所有数据
		// alert(tr1.cells[3].innerText); 		// 获取指定行和列的单元格的数据
		table.deleteRow(tr1.rowIndex);	// 删除点击对象所在的哪一行
	}
}

// 查看指定行的资料
function seeTheData(node) {
	var tr1=node.parentNode.parentNode;		// 获取点击对象所在的行
	document.getElementById("newNumber").value=tr1.cells[1].innerText;	// 将这一行的值一次放入模态框的输入框内
	document.getElementById("newStudentId").value=tr1.cells[2].innerText;
	document.getElementById("newName").value=tr1.cells[3].innerText;
	document.getElementById("newCollege").value=tr1.cells[4].innerText;
	document.getElementById("newProfessional").value=tr1.cells[5].innerText;
	document.getElementById("newGrade").value=tr1.cells[6].innerText;
	document.getElementById("newTheClass").value=tr1.cells[7].innerText;
	document.getElementById("newAge").value=tr1.cells[8].innerText;
	$("#myModal").modal('show');
}

var nodes;

// 修改数据
function changeData(node) {
	var tr1=node.parentNode.parentNode;		// 获取点击对象所在的行
	document.getElementById("changeNumber").value=tr1.cells[1].innerText;	// 将这一行的值一次放入模态框的输入框内
	document.getElementById("changeStudentId").value=tr1.cells[2].innerText;
	document.getElementById("changeName").value=tr1.cells[3].innerText;
	document.getElementById("changeCollege").value=tr1.cells[4].innerText;
	document.getElementById("changeProfessional").value=tr1.cells[5].innerText;
	document.getElementById("changeGrade").value=tr1.cells[6].innerText;
	document.getElementById("changeTheClass").value=tr1.cells[7].innerText;
	document.getElementById("changeAge").value=tr1.cells[8].innerText;
	$("#changeModal").modal('show');
	nodes=node;
}

function change() {
	var mytab=document.getElementById("tabel");		// 获取table里面的全部数据
	var tr1=nodes.parentNode.parentNode;		// 获取点击对象所在的行
	var numOfRow=tr1.rowIndex;	// 获取第几行
	mytab.rows[numOfRow].cells[1].innerHTML=document.getElementById("changeNumber").value;	// 获取各个输入框中的值
	mytab.rows[numOfRow].cells[2].innerHTML=document.getElementById("changeStudentId").value;
	mytab.rows[numOfRow].cells[3].innerHTML=document.getElementById("changeName").value;
	mytab.rows[numOfRow].cells[4].innerHTML=document.getElementById("changeCollege").value;
	mytab.rows[numOfRow].cells[5].innerHTML=document.getElementById("changeProfessional").value;
	mytab.rows[numOfRow].cells[6].innerHTML=document.getElementById("changeGrade").value;
	mytab.rows[numOfRow].cells[7].innerHTML=document.getElementById("changeTheClass").value;
	mytab.rows[numOfRow].cells[8].innerHTML=document.getElementById("changeAge").value;
}