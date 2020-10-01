package com.joebrooks.shareApp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// action들이 상속할 인터페이스
// 각각의 action에서 상황별로 대응한 후, 알맞은 값을 리턴
// 하나의 컨트롤러로 모든 action을 통제함

public interface CommandAction {
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
	throws Throwable;
}
