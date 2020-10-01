package com.joebrooks.shareApp.action.file;

import java.io.File;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.common.ByteConverter;
import com.joebrooks.shareApp.common.CustomRenamePolicy;
import com.joebrooks.shareApp.common.FileType;
import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dto.FileDTO;
import com.joebrooks.shareApp.service.file.IFileService;
import com.joebrooks.shareApp.service.file.UploadFileService;
import com.oreilly.servlet.MultipartRequest;

// 파일 업로드
public class FileUploadAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sid");
		String dynamicPath = (String) session.getAttribute("dynamicPath");

		String path = "D:\\uploadFile\\" + id + "\\" + dynamicPath;
		String requestName = URLDecoder.decode((String)request.getParameter("fileName"), "utf-8");

		int maxSize = 1024 * 1024 * 1200;
		File folder = new File(path);
		MultipartRequest multi = null;
		CustomRenamePolicy policy = new CustomRenamePolicy();
		String prefixName = policy.getName(new File(path, requestName));

		if (!folder.exists()) {
			folder.mkdir();
		}

		try {
			multi = new MultipartRequest(request, path, maxSize, "utf-8", policy);

			File file = multi.getFile("upload");
			String name = multi.getFilesystemName("upload");
			ByteConverter converter = new ByteConverter();
			String size = converter.byteCalculation(String.valueOf(file.length()));
			String type = name.substring(name.lastIndexOf(".") + 1);
			
			
			FileDTO dto = new FileDTO(id, name, size, new FileType().getType(type), dynamicPath);
			IFileService service = new UploadFileService();
			service.execute(dto);
		}

		catch (Exception e) {
			// 만약 에러가 난다면
			// 서버에서 받고있던 미완성된 파일을 지운다.
			e.printStackTrace();
			File file = new File(path, prefixName);
			file.delete();
		}

		return "NonePage";
	}
	
}
