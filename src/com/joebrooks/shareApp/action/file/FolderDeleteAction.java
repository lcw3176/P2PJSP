package com.joebrooks.shareApp.action.file;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dto.FileDTO;
import com.joebrooks.shareApp.service.file.DeleteFolderService;
import com.joebrooks.shareApp.service.file.IFileService;

// 폴더 삭제
// 하위 폴더와 파일 모두 삭제, 상위 폴더는 용량 표기 업데이트
public class FolderDeleteAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sid");
		String dynamicPath = (String) session.getAttribute("dynamicPath");
		
		String folderName = URLDecoder.decode((String)request.getParameter("folderName"), "utf-8");
		FileDTO dto = new FileDTO();
		dto.setId(id);
		dto.setName(folderName);
		dto.setPath(dynamicPath);
		dto.setType("folder");
		
		IFileService service = new DeleteFolderService();
		service.execute(dto);


		return "redirect:/P2PJSP/showall.do?first=true	";
	}

}
