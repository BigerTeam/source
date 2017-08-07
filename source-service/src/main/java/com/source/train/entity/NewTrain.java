package com.source.train.entity;

public class NewTrain {
    private Boolean startFlag;// 是否始发

    private Boolean endFlag;// 是否终点

    private String trainNo; // 车次

    private String location_code; // 车次

    private String secretStr; // 到达地

    private String ypInfo; // 到达地

    private String fromStationTelecode; // 出发地编码

    private String toStationTelecode; // 目的地编码

    private String toStationName; // 到达地

    private String buttonTextInfo; // 按钮汉字

    private String stationTrainCode; // 火车编号

    private String fromStationName; // 出发地

    private String startTime; // 出发时间

    private String arriveTime; // 到达时间

    private String lishi; // 需要时间

    private String zyNum; // 一等座数量

    private String zeNum; // 二等座数量

    private String swzNum; // 商务座数量

    private String grNum; // 高级软卧数量

    private String rwNum; // 软卧数量

    private String rzNum; // 软座数量

    private String ywNum; // 硬卧数量

    private String yzNum; // 硬座数量

    private String tzNum; // 特等座数量

    private String wzNum; // 无座数量

    private String qtNum; // 无座数量

    public String getQtNum() {
        return "".equals(qtNum) || qtNum == null ? "--" : qtNum;
    }

    public void setQtNum(String qtNum) {
        this.qtNum = qtNum;
    }

    public Boolean getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(Boolean startFlag) {
        this.startFlag = startFlag;
    }

    public Boolean getEndFlag() {
        return endFlag;
    }

    public void setEndFlag(Boolean endFlag) {
        this.endFlag = endFlag;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getLocation_code() {
        return location_code;
    }

    public void setLocation_code(String location_code) {
        this.location_code = location_code;
    }

    public String getSecretStr() {
        return secretStr;
    }

    public void setSecretStr(String secretStr) {
        this.secretStr = secretStr;
    }

    public String getYpInfo() {
        return ypInfo;
    }

    public void setYpInfo(String ypInfo) {
        this.ypInfo = ypInfo;
    }

    public String getFromStationTelecode() {
        return fromStationTelecode;
    }

    public void setFromStationTelecode(String fromStationTelecode) {
        this.fromStationTelecode = fromStationTelecode;
    }

    public String getToStationTelecode() {
        return toStationTelecode;
    }

    public void setToStationTelecode(String toStationTelecode) {
        this.toStationTelecode = toStationTelecode;
    }

    public String getToStationName() {
        return toStationName;
    }

    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }

    public String getButtonTextInfo() {
        return buttonTextInfo;
    }

    public void setButtonTextInfo(String buttonTextInfo) {
        this.buttonTextInfo = buttonTextInfo;
    }

    public String getStationTrainCode() {
        return stationTrainCode;
    }

    public void setStationTrainCode(String stationTrainCode) {
        this.stationTrainCode = stationTrainCode;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public void setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getLishi() {
        return lishi;
    }

    public void setLishi(String lishi) {
        this.lishi = lishi;
    }

    public String getZyNum() {
        return "".equals(zyNum) || zyNum == null ? "--" : zyNum;
    }

    public void setZyNum(String zyNum) {
        this.zyNum = zyNum;
    }

    public String getZeNum() {
        return "".equals(zeNum) || zeNum == null ? "--" : zeNum;
    }

    public void setZeNum(String zeNum) {
        this.zeNum = zeNum;
    }

    public String getSwzNum() {
        return "".equals(swzNum) || swzNum == null ? "--" : swzNum;
    }

    public void setSwzNum(String swzNum) {
        this.swzNum = swzNum;
    }

    public String getGrNum() {
        return "".equals(grNum) || grNum == null ? "--" : grNum;
    }

    public void setGrNum(String grNum) {
        this.grNum = grNum;
    }

    public String getRwNum() {
        return "".equals(rwNum) || rwNum == null ? "--" : rwNum;
    }

    public void setRwNum(String rwNum) {
        this.rwNum = rwNum;
    }

    public String getRzNum() {
        return "".equals(rzNum) || rzNum == null ? "--" : rzNum;
    }

    public void setRzNum(String rzNum) {
        this.rzNum = rzNum;
    }

    public String getYwNum() {
        return "".equals(ywNum) || ywNum == null ? "--" : ywNum;
    }

    public void setYwNum(String ywNum) {
        this.ywNum = ywNum;
    }

    public String getYzNum() {
        return "".equals(yzNum) || yzNum == null ? "--" : yzNum;
    }

    public void setYzNum(String yzNum) {
        this.yzNum = yzNum;
    }

    public String getTzNum() {
        return "".equals(tzNum) || tzNum == null ? "--" : tzNum;
    }

    public void setTzNum(String tzNum) {
        this.tzNum = tzNum;
    }

    public String getWzNum() {
        return "".equals(wzNum) || wzNum == null ? "--" : wzNum;
    }

    public void setWzNum(String wzNum) {
        this.wzNum = wzNum;
    }

}