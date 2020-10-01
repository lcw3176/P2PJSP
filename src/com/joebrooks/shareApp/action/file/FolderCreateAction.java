package com.joebrooks.shareApp.action.file;

import java.io.File;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dto.FileDTO;
import com.joebrooks.shareApp.service.file.CreateFolderService;
import com.joebrooks.shareApp.service.file.IFileService;

// 폴더 만들기
public class FolderCreateAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sid");
		String path = "D:\\uploadFile\\" + id;
		String dynamicPath = (String) session.getAttribute("dynamicPath");

		String folderName = URLDecoder.decode((String)request.getParameter("folderName"), "utf-8");
		String isGuest = request.getParameter("isguest");
		File folder = new File(path + "\\" + dynamicPath + folderName);
		FileDTO dto = new FileDTO();
		
		// 게스트 폴더 생성시 db의 auth를 guest로 설정해준다
		if(isGuest != null) {
			dto.setAuth("guest");
		} else {
			dto.setAuth("admin");
		}
		
		dto.setId(id);
		dto.setName(folderName);
		dto.setType("folder");
		dto.setPath(dynamicPath);
		
		IFileService service = new CreateFolderService(folder);
		service.execute(dto);
		
		return "redirect:/P2PJSP/showall.do";
	
	}
}
