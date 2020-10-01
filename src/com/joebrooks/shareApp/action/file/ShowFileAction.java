package com.joebrooks.shareApp.action.file;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dao.FileDAO;
import com.joebrooks.shareApp.model.dto.FileDTO;

// 서버에 올려놓은 유저 파일, 폴더 보여주
public class ShowFileAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		ArrayList<FileDTO> dto = null;
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sid");
		String dynamicPath = (String) session.getAttribute("dynamicPath");
		String page = (String)request.getParameter("page");
		String type = (String)request.getParameter("type");
		String isFirstVisit = (String)request.getParameter("first");
		
		if(id == null) {
			return "./view/noMember.jsp";
		}
		
		if(page == null) {
			page = "1";
		}

		if(type == null) {
			type = "all";
		}
		
		if(isFirstVisit != null) {
			session.setAttribute("dynamicPath", "");
			dynamicPath = "";
		}
		
		// 게스트 폴더일시 폴더 생성 기능들 삭제
		if(dynamicPath.contains("(Guest)")) {
			request.setAttribute("isGuest", "true");
		}
		
		FileDAO dao = FileDAO.getInstance();
		int totalPage = 0;
		if(type.equals("all")) {
			dto = dao.select(id, dynamicPath, page);
			totalPage = dao.getLength(id, dynamicPath);
		} else {
			dto = dao.selectFromType(id, page, type);
			totalPage = dao.getLengthFromType(id, type);
		}
	
		
		request.setAttribute("filesInfo", dto);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("type", type);
		
		return "./view/showFile.jsp";
	}

}
