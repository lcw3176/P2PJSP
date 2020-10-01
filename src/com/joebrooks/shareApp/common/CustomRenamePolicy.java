package com.joebrooks.shareApp.common;

import java.io.File;
import java.io.IOException;

import com.oreilly.servlet.multipart.FileRenamePolicy;

// 파일 업르도 정책
// 전송 중 취소를 대비해 업로드 되는 파일의 이름을 가져오는 기능 추가

public class CustomRenamePolicy implements FileRenamePolicy {
	@Override
	public File rename(File f) {
		String removePlus =  f.getName().replaceAll("\\+", " ");
		f = new File(f.getParent(), removePlus);;
		
		if (createNewFile(f)) {
			return f;
		}
		String name = f.getName();
		String body = null;
		String ext = null;

		int dot = name.lastIndexOf(".");
		if (dot != -1) {
			body = name.substring(0, dot);
			ext = name.substring(dot); // includes "."
		} else {
			body = name;
			ext = "";
		}

		int count = 0;
		while (!createNewFile(f) && count < 9999) {
			count++;
			String newName = body + count + ext;
			f = new File(f.getParent(), newName);
		}

		return f;
	}

	public String getName(File f) {
		if (!f.exists()) {
			return f.getName();
		}

		String name = f.getName();
		String body = null;
		String ext = null;

		int dot = name.lastIndexOf(".");
		if (dot != -1) {
			body = name.substring(0, dot);
			ext = name.substring(dot); // includes "."
		} else {
			body = name;
			ext = "";
		}
		
		String newName = null;
		int count = 0;
		while (f.exists() && count < 9999) {
			count++;
			newName = body + count + ext;
			f = new File(f.getParent(), newName);
		}
		
		return newName;
	}

	private boolean createNewFile(File f) {
		try {
			return f.createNewFile();
		} catch (IOException ignored) {
			return false;
		}
	}
}
