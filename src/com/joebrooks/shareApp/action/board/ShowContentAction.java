package com.joebrooks.shareApp.action.board;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dao.BoardDAO;
import com.joebrooks.shareApp.model.dao.CommentDAO;
import com.joebrooks.shareApp.model.dto.BoardDTO;
import com.joebrooks.shareApp.model.dto.CommentDTO;

// 게시글 내용 보여주기
public class ShowContentAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String title = request.getParameter("title");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String isRedirect = request.getParameter("redirect");
		String replyPage = request.getParameter("commentPage");
		int page = 1;
		
		
		// 게시글에 달린 댓글 페이지 설정
		if(replyPage != null) {
			page = Integer.parseInt(replyPage); 
		}
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardDTO boardDTO = dao.select(idx, title);		
		
		CommentDAO comment = CommentDAO.getInstance();
		ArrayList<CommentDTO> commentDTO = comment.select(idx, page);
		
		
		if(boardDTO == null) {
			return "./view/error.jsp";
		}
		
		// 사용자가 직접 들어온 경우에만 조회수 카운트
		if(isRedirect == null) {
			int view = boardDTO.getViewCount() + 1;
			boardDTO.setViewCount(view);
			
			BoardDAO viewCountDAO = BoardDAO.getInstance();
			viewCountDAO.updateViewCount(boardDTO);
		}
		
		request.setAttribute("contentInfo", boardDTO);
		request.setAttribute("commentInfo", commentDTO);
		
		return "./view/shareBoardOpen.jsp";
	}

}
