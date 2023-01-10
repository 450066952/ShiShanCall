package com.inch.rest.action.ws;

import java.io.Serializable;

/**
 * @program: TaiCang
 * @description: 111
 * @author: tony.inch
 * @create: 2020-01-15 11:06
 **/
public class Offer implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String Date;
    private String DateTimer;
    private String QueueNO;
    private String QueueType;
    private String Number;
    private String CredentialNO;
    private String CredentialName;
    private String BusinessType;
    private String QueuePeopleNum;
    private String MachineNO;
    private String NumberType;
    private String QrCode;

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

    public String getNumberType() {
        return NumberType;
    }

    public void setNumberType(String numberType) {
        NumberType = numberType;
    }

    public String getQrCode() {
        return QrCode;
    }

    public void setQrCode(String qrCode) {
        QrCode = qrCode;
    }
}
