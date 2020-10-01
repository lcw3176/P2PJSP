package com.joebrooks.shareApp.action.board;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.controller.CommandAction;
import com.joebrooks.shareApp.model.dao.CommentDAO;
import com.joebrooks.shareApp.model.dto.CommentDTO;

// 댓글 남기기
public class BoardCommentAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		HttpSession session = request.getSession();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
				
		String index = request.getParameter("idx");
		int idx = Integer.parseInt(index);
		String id = (String)session.getAttribute("sid");	
		String title = request.getParameter("title");
		String comment = request.getParameter("comment");
		String date = dateFormat.format(Calendar.getInstance().getTime());
		
		String encodedTitle = URLEncoder.encode(title, "utf-8");
		
		CommentDTO dto = new CommentDTO(idx, id, comment, date);
		CommentDAO dao = CommentDAO.getInstance();
		dao.insert(dto);
		
		return "redirect:/P2PJSP/showcontent.do?idx=" + index + "&title=" + encodedTitle + "&redirect=true";
	}

}
