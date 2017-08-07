package com.source.train.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.util.IOUtils;
import com.source.utils.train.BufferByte;
/**
 * 获取所有车站
 */
public class Station {

	private String jianPin;
	private String stationName;
	private String stationCode;
	private String quanPin;
	public Station() {
		super();
	}

	/**
	 * @param _0
	 * @param stationName
	 * @param stationCode
	 * @param jianPin
	 * @param quanPin
	 * @param _5
	 * @param _6
	 */
	public Station(String jianPin,String stationName, String stationCode, String quanPin) {
		super();
		this.stationName = stationName;
		this.stationCode = stationCode;
		this.jianPin = jianPin;
		this.quanPin = quanPin;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getJianPin() {
		return jianPin;
	}

	public void setJianPin(String jianPin) {
		this.jianPin = jianPin;
	}

	public String getQuanPin() {
		return quanPin;
	}

	public void setQuanPin(String quanPin) {
		this.quanPin = quanPin;
	}

	@Override
	public String toString() {
		return stationName + "-" + stationCode;
	}

	private static Station[] stations;

	public static Station[] create12306Station() {
		if (stations != null)
			return stations;
		String stationStr;
		try {
			stationStr = new String(read(IOUtils.class.getClassLoader()
                    .getResourceAsStream("Station.js")), "UTF-8");
			String[] cities = stationStr.split("@");
			stations = new Station[cities.length];
			for (int i = 0; i < cities.length; i++) {
				String[] part = cities[i].split("\\|");
				stations[i] = new Station(part[0], part[1], part[2], part[3]);
			}
			return stations;
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		}
		return new Station[0];
	}

	

	public static byte[] read(InputStream is) throws IOException {
		BufferByte buffer = new BufferByte();
		byte[] bs = new byte[1024];
		int len = -1;
		while ((len = is.read(bs)) != -1) {
			buffer.append(bs, 0, len);
		}
		is.close();
		return buffer.getBuffer();
	}
}
