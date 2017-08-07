package com.source.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.log4j.Logger;

public class FileUtil {
	
	private static Logger log = Logger.getLogger(FileUtil.class);
	
	/**
	 * NIO way
	 */
	public static byte[] toByteArray(String filename) {

		File f = new File(filename);
		if (!f.exists()) {
			log.error("文件未找到！" + filename);
			throw new RuntimeException();
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			throw new RuntimeException();		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				throw new RuntimeException();			}
			try {
				fs.close();
			} catch (IOException e) {
				throw new RuntimeException();			}
		}
	}
}