package com.joebrooks.shareApp.common;

import java.io.File;

// 폴더 사이즈 가져오기 
// 재귀함수를 통해 소속되어 있는 모든 파일의 용량을 가져온다.

public class FolderSize {
	public long getSize(File folder) {
		long length = 0;
		File[] files = folder.listFiles();
		int count = files.length;
		for (int i = 0; i < count; i++) {
			if (files[i].isFile()) {
				length += files[i].length();
			} else {
				length += getSize(files[i]);
			}
		}
		return length;
	}

}
