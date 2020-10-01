package com.joebrooks.shareApp.action.file;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.controller.CommandAction;

// 폴더 클릭해서 이동
// dynamicPath: 사용자가 현재 탐색중인 폴더의 경로
// Ex) D:\\uploadFile\(userId)\hello 폴더에 접속하면 => dynamicPath: hello\
public class FolderMoveAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String query = request.getParameter("query");
		HttpSession session = request.getSession();
		String dynamicPath = (String) session.getAttribute("dynamicPath");
		
		
		if (query.equals("search")) {
			String addDynamicPath = URLDecoder.decode((String)request.getParameter("folderName"), "utf-8");

			if (dynamicPath.equals("")) {
				dynamicPath = addDynamicPath + "\\";
			} else {
				dynamicPath += addDynamicPath + "\\";
			}
			
			session.setAttribute("dynamicPath", dynamicPath);
		}

		if (query.equals("back")) {
			if (!dynamicPath.equals("")) {
				String removeString = (String) session.getAttribute("dynamicPath");
				removeString = removeString.substring(0, removeString.length() - 1); // 마지막 '\' 지워줌
				int removeIndex = removeString.lastIndexOf("\\");
				dynamicPath = removeString.substring(0, removeIndex + 1);
				
				session.setAttribute("dynamicPath", dynamicPath);
			}
		}
		
		return "redirect:/P2PJSP/showall.do";
	}

}
