package com.source.utils.train;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
/**
 * 将object存入本地文件用来下次使用
 */
public class ObjectToFile {
	private static String basePath = System.getProperty("user.dir");
	private static Object obj=null;
	public static Object readObject() {
		FileInputStream freader = null;
		ObjectInputStream inputStream = null;
		try {
			File[] files = new File(basePath).listFiles();
			Boolean flag=true;
			if (files != null) {
				for (File f : files) {
					if (f.getName().equals("date.dat")) {
						flag=false;
					}
				}
			} else {
				writeObject(null);
			}
			if(flag){
				writeObject(null);
			}
			String path = basePath+ "/date.dat";
			File file = new File(path);
			if (file.exists() || file.isFile()) {
				file.createNewFile();
			}
			freader = new FileInputStream(file);
			inputStream = new ObjectInputStream(freader);
			if(obj==null)
				obj = inputStream.readObject();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (freader != null)
					freader.close();
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void writeObject(Object obj) {
		OutputStream outStream = null;
		ObjectOutputStream outputStream = null;
		try {
			String path =basePath + "/date.dat";
			outStream = new FileOutputStream(path);
			outputStream = new ObjectOutputStream(outStream);
			outputStream.writeObject(obj);
			obj=null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (outStream != null)
					outStream.close();
				if (outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}