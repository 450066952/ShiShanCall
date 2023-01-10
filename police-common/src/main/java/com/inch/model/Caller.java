package com.inch.model;

public class Caller {

    private String WindowNO;//叫号窗口
    private String UserNO;//窗口当前员工号
    private String QueueNO;//被叫队列号  --必填
    private String Number;//被叫号号码  --必填
    private String DateTimer;//叫号日期时间yyyy-MM-dd HH:mm:ss如2016-10-01 14:30:00  --必填

    public String getWindowNO() {
        return WindowNO;
    }

    public void setWindowNO(String windowNO) {
        WindowNO = windowNO;
    }

    public String getUserNO() {
        return UserNO;
    }

    public void setUserNO(String userNO) {
        UserNO = userNO;
    }

    public String getQueueNO() {
        return QueueNO;
    }

    public void setQueueNO(String queueNO) {
        QueueNO = queueNO;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getDateTimer() {
        return DateTimer;
    }

    public void setDateTimer(String dateTimer) {
        DateTimer = dateTimer;
    }
}
