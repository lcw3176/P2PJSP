package com.joebrooks.shareApp.common;

import java.text.DecimalFormat;

// 바이트 크기를 사람이 보기 좋게 변환
public class ByteConverter {
	public String byteCalculation(String bytes) {
		String retFormat = "0";
		Double size = Double.parseDouble(bytes);

		String[] s = { "bytes", "KB", "MB", "GB", "TB", "PB" };

		if (!bytes.equals("0")) {
			int idx = (int) Math.floor(Math.log(size) / Math.log(1024));
			DecimalFormat df = new DecimalFormat("#,###.##");
			double ret = ((size / Math.pow(1024, Math.floor(idx))));
			retFormat = df.format(ret) + " " + s[idx];
		} else {
			retFormat += " " + s[0];
		}

		return retFormat;
	}
}
