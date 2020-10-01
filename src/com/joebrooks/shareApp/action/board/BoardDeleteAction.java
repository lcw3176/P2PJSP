package com.joebrooks.shareApp.action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dao.BoardDAO;
import com.joebrooks.shareApp.model.dto.BoardDTO;

// 게시글 삭제

public class BoardDeleteAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		HttpSession sess = request.getSession();
		String id = (String)sess.getAttribute("sid");
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		BoardDTO dto = new BoardDTO();
		dto.setIdx(idx);
		dto.setId(id);
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.delete(dto);
		
		return "redirect:/P2PJSP/showboard.do";
	}

}
