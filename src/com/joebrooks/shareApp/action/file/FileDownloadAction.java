package com.joebrooks.shareApp.action.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joebrooks.shareApp.controller.CommandAction;

// 파일 다운로드
public class FileDownloadAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String isGuest = request.getParameter("isguest");
		
		HttpSession session = null;
		String id, dynamicPath, path, fileName;
		File file = null;
		InputStream in = null;
		OutputStream os = null;
		
		
		// 게스트 자격으로 입장했을때
		if(isGuest != null) {
			id = request.getParameter("id");
			dynamicPath = request.getParameter("dynamicPath");

		} else {
			session = request.getSession();
			id = (String) session.getAttribute("sid");
			dynamicPath = (String) session.getAttribute("dynamicPath");
		}
		
		path = "D:\\uploadFile\\" + id + "\\" + dynamicPath;
		fileName = URLDecoder.decode((String) request.getParameter("fileName"), "utf-8");
		file = new File(path, fileName);

		try {
			in = new FileInputStream(file);

			// 파일 다운로드 헤더 지정
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "JSP Generated Data");

			// 한글 파일명 처리
			fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");

			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");

			response.setHeader("Content-Length", "" + file.length());

			os = response.getOutputStream();
			byte b[] = new byte[(int) file.length()];
			int leng = 0;

			while ((leng = in.read(b)) > 0) {
				os.write(b, 0, leng);
			}

			in.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
			os.close();
		}

		return "NonePage";
	}

}
