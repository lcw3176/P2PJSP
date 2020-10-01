package com.joebrooks.shareApp.action.board;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dao.BoardDAO;
import com.joebrooks.shareApp.model.dao.FileDAO;
import com.joebrooks.shareApp.model.dto.BoardDTO;
import com.joebrooks.shareApp.model.dto.FileDTO;

// 게시글 업로드

public class BoardUploadAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		HttpSession sess = request.getSession();
		String id = (String)sess.getAttribute("sid");
		String state = request.getParameter("state");
		
		// 게시글을 작성하려고 한다면
		if(state.equals("write")) {
			// 게스트 폴더로 설정한 폴더 리스트 가져오기
			FileDAO dao = FileDAO.getInstance();
			ArrayList<FileDTO> dto = dao.selectGuestFolder(id);
			request.setAttribute("shareFolderList", dto);
			
			return "./view/shareBoardWriting.jsp";
		}
		
		// 게시글 작성이 완료되었다면 업로드 후 게시판 리스트로 리다이렉
		else if(state.equals("complete")){
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String shareFolder = request.getParameter("sharefolder");
			
			LocalDate nowDate = LocalDate.now();
			Date date = Date.valueOf(nowDate);
			
			BoardDTO dto = new BoardDTO(id, title, content, date);
			dto.setShare(shareFolder);
			
			BoardDAO dao = BoardDAO.getInstance();
			dao.insert(dto);

			
			return "redirect:/P2PJSP/showboard.do";
		}

		
		else {
			return "./view/error.jsp";
		}
		

	}

}
