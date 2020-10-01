package com.joebrooks.shareApp.action.member;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dao.MemberDAO;
import com.joebrooks.shareApp.model.dto.MemberDTO;

// 회원가입
public class JoinAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		// 요청된 id 값이 없으면 회원가입 창 보여주기
		if(id == null || id.equals(""))
		{
			return "/view/join.jsp";
		}
		
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList<MemberDTO> dto = dao.select();
		
		// id 중복검사
		for(int i = 0; i < dto.size(); i++)
		{
			MemberDTO temp = dto.get(i);
			
			if(temp.getId().equals(id))
			{
				request.setAttribute("errorcode", "alreadyId");				
				return "/view/join.jsp";
			}
			
		}
		
		MemberDTO newMember = new MemberDTO(id, pw);
		dao.insert(newMember);
		
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(20 * 60);
		session.setAttribute("sid", id);
		
		return "redirect:/P2PJSP/";
	}
	
}
