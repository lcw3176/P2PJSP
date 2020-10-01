// 파일 전송 관련 js

var cancel = false;

	function doUpload() {
		
		var progress = document.getElementById('ajax-progress');
		var uploadFileName = document.getElementsByName('upload')[0].files[0].name;
		var progressFileName = document.getElementById('progress-fileName');
		var speed = document.getElementById('progress-speed');
		var size = 0;
		
		$("#file").ajaxForm({
			url : "/P2PJSP/fileupload.do?fileName=" + encodeURIComponent(uploadFileName),
			enctype : "multipart/form-data",
			beforeSend : function() {
				start = 0;
				progress.innerHTML = '0%';
				progressFileName.innerHTML = uploadFileName;
				cancel = false;
			},

			uploadProgress : function(event, position, total, percentComplete) {
				if(cancel){
					location.href = "showall.do";
				}
				
				elapsed = event.timeStamp - start;
				
				if(elapsed >= 3000){
					size = (position - size) / (elapsed / 1000);
					speed.innerHTML = getFileSize(size) + "/s";
					start = event.timeStamp;
					size = position;
				}

				progress.innerHTML = percentComplete + '%';
				progress.setAttribute('aria-valuenow', percentComplete);
				progress.setAttribute('style', 'width: ' + percentComplete + '%');
			},
			
			success: function (data){
   				location.href = "showall.do";
				
            },
		});

		$("#file").submit();
	}

	function doDelete(name) {
		location.href = "filedelete.do?fileName=" + encodeURIComponent(name);
	}

	function doDownload(name) {
		location.href = "filedownload.do?fileName=" + encodeURIComponent(name);
	}

	function domkdir() {
		var name = prompt("폴더 제목을 입력하세요");
		if (name != null || !name.trim() == "") {
			location.href = "folderupdate.do?folderName=" + encodeURIComponent(name);
		}

	}
	function doDeleteDir(name) {
		location.href = "folderdelete.do?folderName=" + encodeURIComponent(name);
	}

	function doGoBackdir() {
		location.href = "foldermove.do?query=back";
	}

	function cancelUpload(){
		cancel = true;
	}
	
	function domkGuestDir(){
		var name = prompt("게스트 폴더 제목을 입력하세요.")
		if(name != null || !name.trim() == ""){
			name += "(Guest)";
			location.href = "folderupdate.do?folderName=" + encodeURIComponent(name) + "&isguest=true";
		}
		
	}
	
	function getFileSize(x) {
	  var s = ['bytes', 'kB', 'MB', 'GB', 'TB', 'PB'];
	  var e = Math.floor(Math.log(x) / Math.log(1024));
	  return (x / Math.pow(1024, e)).toFixed(2) + " " + s[e];
	}