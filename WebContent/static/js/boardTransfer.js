function doWrite(){
	location.href = "boardwrite.do?state=write"
}

function doCommit(){

	if(emptyCheck()){
		var form = document.getElementById("boardform");
		form.submit();	
	}
}

function doList(){
	location.href = "showboard.do?page=1";
}


function doDelete(idx){
	if(confirm("정말 삭제하시겠습니까?")){
		location.href = "boardremove.do?idx=" + idx
	}	
}

function emptyCheck(){
	var title = document.getElementById("title").value;
	var content = document.getElementById("content").value;

	if(title == null || title.trim() == ""){
		alert("제목을 입력해 주세요");
		return false;
	}
	
	if(content == null || content.trim() == ""){
		alert("내용을 입력해 주세요");
		return false;
	}
	
	return true;
}