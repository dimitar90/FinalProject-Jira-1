package com.jira.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class ImageConvertor {
	public static String convertFromLocalPathToBase64String(String imagePath) throws FileNotFoundException, UnsupportedEncodingException {
		File file = new File(imagePath);

		if (!file.exists()) {
			return "";
		}

		FileInputStream fis = new FileInputStream(file);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		byte[] bytes = bos.toByteArray();

		byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
		String base64Encoded = new String(encodeBase64, "UTF-8");

		return base64Encoded;
	}
}
