package com.inch.model;

public class Offer {

    private String Date;//取号日期, yyyy-MM-dd   --必填
    private String DateTimer;//取号日期时间, yyyy-MM-dd HH:mm:ss 如2016-10-01 14:30:00  --必填
    private String QueueNO;//队列号（101,102,103）  --必填
    private String QueueType;//队列类型，同QueueNO（101,102,103）  --必填
    private String Number;//取号号码  --必填
    private String NumberType;//用于取号的证件类型0：二维码1：身份证号  --必填
    private String CredentialNO;//证件号码  --必填
    private String CredentialName;//姓名   --可空
    private String BusinessType;//要办理的业务类型（普通护照14，港澳证21，大陆证25，其他99）（若办多个用;隔开，比如14;21;25）----可空
    private String QueuePeopleNum;//此号所在队列前面排队人数  --必填
    private String MachineNO;//取号机机器号（排队叫号系统自己定义 比如QHJ001）  --必填

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDateTimer() {
        return DateTimer;
    }

    public void setDateTimer(String dateTimer) {
        DateTimer = dateTimer;
    }

    public String getQueueNO() {
        return QueueNO;
    }

    public void setQueueNO(String queueNO) {
        QueueNO = queueNO;
    }

    public String getQueueType() {
        return QueueType;
    }

    public void setQueueType(String queueType) {
        QueueType = queueType;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getNumberType() {
        return NumberType;
    }

    public void setNumberType(String numberType) {
        NumberType = numberType;
    }

    public String getCredentialNO() {
        return CredentialNO;
    }

    public void setCredentialNO(String credentialNO) {
        CredentialNO = credentialNO;
    }

    public String getCredentialName() {
        return CredentialName;
    }

    public void setCredentialName(String credentialName) {
        CredentialName = credentialName;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public String getQueuePeopleNum() {
        return QueuePeopleNum;
    }

    public void setQueuePeopleNum(String queuePeopleNum) {
        QueuePeopleNum = queuePeopleNum;
    }

    public String getMachineNO() {
        return MachineNO;
    }

    public void setMachineNO(String machineNO) {
        MachineNO = machineNO;
    }
}
