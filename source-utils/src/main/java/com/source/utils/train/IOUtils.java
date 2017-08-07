package com.source.utils.train;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 获取image 工具类
 */
public class IOUtils {
	public static byte[] getImage(String fileName) {
        InputStream is = null;

        try {
            is = new BufferedInputStream(IOUtils.class.getClassLoader()
                    .getResourceAsStream(fileName));
            byte[] b = new byte[is.available()];
            is.read(b);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
            	if(is!=null)
            		is.close();
            } catch (IOException e) {
            }
        }
    }
}
