package com.joebrooks.shareApp.common;

// 파일 분류 클래스
// 비디오, 오디오, 이미지 파일을 판별해서 리턴

public class FileType {

	public String getType(String type) {
		String[] videoType = new String[] { "mkv", "avi", "mp4", "mpg", "flv", "wmv", "asf", "asx", "ogm", "ogv",
				"mov" };
		String[] imageType = new String[] { "bmp", "jpg", "gif", "png", "tif" };
		String[] audioType = new String[] { "wav", "mp3", "ogg", "flac", "wma", "msv" };

		for (String video : videoType) {
			if (type.equals(video)) {
				return "video";
			}
		}

		for (String image : imageType) {
			if (type.equals(image)) {
				return "image";
			}
		}

		for (String audio : audioType) {
			if (type.equals(audio)) {
				return "audio";
			}
		}

		return "file";
	}

}
