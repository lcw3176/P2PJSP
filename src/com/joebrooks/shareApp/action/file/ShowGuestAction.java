package com.joebrooks.shareApp.action.file;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dao.FileDAO;
import com.joebrooks.shareApp.model.dto.FileDTO;

// 공유링크로 들어온 게스트에게 폴더 보여주기
public class ShowGuestAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		ArrayList<FileDTO> dto = null;
		String id = request.getParameter("id");
		String folderName = request.getParameter("foldername");

		FileDAO dao = FileDAO.getInstance();
		dto = dao.selectGuestFile(id, folderName);

		request.setAttribute("filesInfo", dto);
		
		return "./view/showFileGuest.jsp";
	}

}
