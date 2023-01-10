package com.socket.server.command.dto;

import org.jboss.netty.channel.Channel;

import java.io.Serializable;

/**
 * @date:2017/11/2
 * @package_name:com.socket.server.command.dto
 * @author:Tony
 * @description:${description}
 */
public class PcLoginModel implements Serializable {


    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String winid;
    private String winname;
    private String wintype;
    private Channel channel;
    private String pic;
    private int star;
    private String no;
    private int member;
    private String name;
    private String typename;
    private String userid;
    private String version;
    private String state;
    private int schoolid;
    private String schoolname;
    private String firsttype;
    private String course;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getFirsttype() {
        return firsttype;
    }

    public void setFirsttype(String firsttype) {
        this.firsttype = firsttype;
    }

    public int getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(int schoolid) {
        this.schoolid = schoolid;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public int getMember() {
            return member;
        }

        public void setMember(int member) {
            this.member = member;
        }

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getWinid() {
            return winid;
        }

        public void setWinid(String winid) {
            this.winid = winid;
        }

        public String getWinname() {
            return winname;
        }

        public void setWinname(String winname) {
            this.winname = winname;
        }

        public String getWintype() {
            return wintype;
        }

        public void setWintype(String wintype) {
            this.wintype = wintype;
        }
}
