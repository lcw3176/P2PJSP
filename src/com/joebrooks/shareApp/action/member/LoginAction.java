package com.joebrooks.shareApp.action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dao.MemberDAO;
import com.joebrooks.shareApp.model.dto.MemberDTO;

// 로그인
public class LoginAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		if(id == null || id.equals("")) {
			return "/view/login.jsp";
		}
		
		MemberDTO dto = new MemberDTO(id, pw);
		MemberDAO dao = MemberDAO.getInstance();
		
		boolean loginResult = dao.login(dto);
	
		
		if(loginResult) {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(20 * 60);
			session.setAttribute("sid", id);
			session.setAttribute("dynamicPath", "");
			System.out.println(id + " 로그인함");
			
		} 
		
		return "redirect:/P2PJSP/showboard.do";
				
	}

}
