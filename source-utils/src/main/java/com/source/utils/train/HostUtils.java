package com.source.utils.train;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HostUtils {

	public static final String hostFilePath = "C:\\Windows\\System32\\drivers\\etc\\hosts";
	// public static final String hostFilePath="/etc/hosts";

	public static void addHostBinding(String ip, String host) {
		Map<String, String> hostToIpMap = readFromIpHostFile();
		hostToIpMap.put(host, ip);
		writeIpHostToReader(hostToIpMap);
	}

	public static void deleteHostBinding(String ip, String host) {
		Map<String, String> hostToIpMap = readFromIpHostFile();
		if (hostToIpMap != null && hostToIpMap.containsKey(host)) {
			hostToIpMap.remove(host);
		}
		writeIpHostToReader(hostToIpMap);
	}

	public static Map<String, String> readFromIpHostFile() {
		Map<String, String> hostToIpMap = new HashMap<String, String>();
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new FileReader(hostFilePath));
			String str = null;
			while ((str = bufferReader.readLine()) != null) {
				if (str != null) {
					String[] ipAndHost = str.split(" ");
					if (ipAndHost != null && ipAndHost.length == 2 && ipAndHost[0] != null
							&& ipAndHost[0].charAt(0) != '#') {
						hostToIpMap.put(ipAndHost[1], ipAndHost[0]);
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferReader.close();
			} catch (Exception e) {

			}
		}
		return hostToIpMap;
	}

	public static void writeIpHostToReader(Map<String, String> hostToIpMap) {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(hostFilePath));
			Iterator<Entry<String, String>> iter = hostToIpMap.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				bufferedWriter.write(entry.getValue() + "   " + entry.getKey() + "\n");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (Exception e) {

			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void jdkDnsNoCache()
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Class clazz = java.net.InetAddress.class;
		final Field cacheField = clazz.getDeclaredField("addressCache");
		cacheField.setAccessible(true);
		final Object o = cacheField.get(clazz);
		Class clazz2 = o.getClass();
		final Field cacheMapField = clazz2.getDeclaredField("cache");
		cacheMapField.setAccessible(true);
		final Map cacheMap = (Map) cacheMapField.get(o);
		AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				try {
					synchronized (o) {// 同步是必须的,因为o可能会有多个线程同时访问修改。
						// cacheMap.clear();//这步比较关键，用于清除原来的缓存
						cacheMap.remove("kyfw.12306.cn");
					}
				} catch (Throwable te) {
					throw new RuntimeException(te);
				}
				return null;
			}
		});

	}

	public static void test() throws IOException, InterruptedException {
		addHostBinding("127.0.0.1", "www.baidu.com");
		deleteHostBinding("127.0.0.1", "www.baidu.com");
		try {
			jdkDnsNoCache();
		} catch (Exception e) {
			e.printStackTrace();
		}
		InetAddress address = InetAddress.getByName("www.baidu.com");
		System.out.println(System.currentTimeMillis() + ":::" + address.getHostAddress());
	}
}
