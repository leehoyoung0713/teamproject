package com.meommi.app.postFile.vo;

//댓글 작성시 화면에서 입력받을 데이터를 저장하기 위한 PostFileVO클래스 선언
public class PostFileVO {

//	멤버 변수 선언
	private int postFileNumber;
	private String postFileSystemName;
	private String postFileOriginName;
	private String postFilePath;
	private int postNumber;
	private int userNumber;
	
//	기본 생성자 선언
	public PostFileVO() {;}

//	getter, setter메서드 선언
	public int getPostFileNumber() {
		return postFileNumber;
	}

	public void setPostFileNumber(int postFileNumber) {
		this.postFileNumber = postFileNumber;
	}

	public String getPostFileSystemName() {
		return postFileSystemName;
	}

	public void setPostFileSystemName(String postFileSystemName) {
		this.postFileSystemName = postFileSystemName;
	}

	public String getPostFileOriginName() {
		return postFileOriginName;
	}

	public void setPostFileOriginName(String postFileOriginName) {
		this.postFileOriginName = postFileOriginName;
	}

	public String getPostFilePath() {
		return postFilePath;
	}

	public void setPostFilePath(String postFilePath) {
		this.postFilePath = postFilePath;
	}

	public int getPostNumber() {
		return postNumber;
	}

	public void setPostNumber(int postNumber) {
		this.postNumber = postNumber;
	}

	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

//	toString메서드 재정의
	@Override
	public String toString() {
		return "PostFileVO [postFileNumber=" + postFileNumber + ", postFileSystemName=" + postFileSystemName
				+ ", postFileOriginName=" + postFileOriginName + ", postFilePath=" + postFilePath + ", postNumber="
				+ postNumber + ", userNumber=" + userNumber +  "]";
	}
}
