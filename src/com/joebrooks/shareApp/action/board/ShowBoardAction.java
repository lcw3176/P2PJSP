package com.joebrooks.shareApp.action.board;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dao.BoardDAO;
import com.joebrooks.shareApp.model.dto.BoardDTO;

// 게시글 리스트 보여주기
public class ShowBoardAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		BoardDAO dao = BoardDAO.getInstance();
		String page = request.getParameter("page");
		String search = request.getParameter("search");
		ArrayList<BoardDTO> dto = null;
		int totalPage = 0;
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("sid");
		
		if(id == null) {
			return "./view/noMember.jsp";
		}
		
		// 페이지가 따로 없다면 1페이지로 설정
		if (page == null) {
			page = "1";
		}
		
		// 게시글을 검색했을때
		if(search != null) {
			dto = dao.selectSearchResult(search, Integer.parseInt(page));
			totalPage = dao.getSearchResultLength(search);
			request.setAttribute("searchPage", search);
			
		} else {
			dto = dao.selectBoardList(Integer.parseInt(page));
			totalPage = dao.getBoardListLength();
		}
		
		
		request.setAttribute("pagesInfo", dto);
		request.setAttribute("totalPage", totalPage);


		return "./view/shareBoardList.jsp";

	}

}
