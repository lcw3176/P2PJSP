package com.joebrooks.shareApp.action.file;

import java.io.File;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.common.ByteConverter;
import com.joebrooks.shareApp.common.FileType;
import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dto.FileDTO;
import com.joebrooks.shareApp.service.file.DeleteFileService;
import com.joebrooks.shareApp.service.file.IFileService;

// 파일 삭제하기

public class FileDeleteAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sid");
		String dynamicPath = (String) session.getAttribute("dynamicPath");
		String path = "D:\\uploadFile\\" + id + "\\" + dynamicPath;
		String fileName = URLDecoder.decode((String)request.getParameter("fileName"), "utf-8");
		fileName = fileName.replace("&amp;", "&");
		fileName = fileName.replace("&gt;", ">");
		fileName = fileName.replace("&lt;", "<");
		
		System.out.println(fileName);
		File file = new File(path, fileName);
		ByteConverter converter = new ByteConverter();
		String size = converter.byteCalculation(String.valueOf(file.length()));
		String type = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		FileDTO dto = new FileDTO(id, fileName, size, new FileType().getType(type), dynamicPath);
		
		IFileService service = new DeleteFileService();
		service.execute(dto);
				

		return "redirect:/P2PJSP/showall.do?";
	}


}
