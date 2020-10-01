package com.joebrooks.shareApp.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerAction extends HttpServlet{
	private Map commandMap = new HashMap();
	
	public void init(ServletConfig config) throws ServletException{
		loadProperties("com.joebrooks.shareApp.property/Command");
	}
	
	// properties 설정
	private void loadProperties(String path) {
		ResourceBundle rbHome = ResourceBundle.getBundle(path);

		Enumeration<String> actionEnumHome = rbHome.getKeys();
	
		while(actionEnumHome.hasMoreElements())
		{
			String command = actionEnumHome.nextElement();
			String className = rbHome.getString(command);

			try
			{
				Class commandClass = Class.forName(className);
				Object commandInstance = commandClass.newInstance();

				commandMap.put(command, commandInstance);
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		requestPro(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		requestPro(req, resp);
	}
	
	// 요청 분석 후 처리
	private void requestPro(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		
		String view = null;		
		CommandAction com = null;
		
		try
		{
			String command = request.getRequestURI();
			
			if(command.indexOf(request.getContextPath()) == 0)
			{
				command = command.substring(request.getContextPath().length());
			}
			

			com = (CommandAction)commandMap.get(command);

			if(com == null)
			{
				return;
			}
			
			view = com.requestPro(request, response);
			
			if(view == null)
			{
				return;
			}			
			
		}
		
		catch(Throwable e)
		{
			throw new ServletException(e);
		}
		
		if(view.equals("NonePage"))
		{
			return;
		}
		
		if(view.split(":")[0].equals("redirect"))
		{			
			response.sendRedirect(view.split(":")[1]);
			return;
		}
				
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		request.setAttribute("view", view);
		dispatcher.forward(request, response);


	}
}
