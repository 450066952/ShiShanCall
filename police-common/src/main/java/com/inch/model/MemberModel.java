package com.inch.model;

public class MemberModel extends BaseModel  {

	private String pic;
	private int star;
	private String no;
	private int member;
	private String name;
	private String winname;
	private String typename;

	public String getWinname() {
		return winname;
	}

	public void setWinname(String winname) {
		this.winname = winname;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
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
}
