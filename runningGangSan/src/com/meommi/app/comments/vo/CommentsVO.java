package com.meommi.app.comments.vo;

//댓글 작성시 화면에서 입력받을 데이터를 저장하기 위한 CommentsVO클래스 선언
public class CommentsVO {

//	멤버 변수 선언
	private int commentsNumber;
	private String commentsDateTime;
	private String commentsContent;
	private int userNumber;
	private int postNumber;
	
//	기본 생성자 선언
	public CommentsVO() {;}

//	getter, setter메서드 선언
	public int getCommentsNumber() {
		return commentsNumber;
	}

	public void setCommentsNumber(int commentsNumber) {
		this.commentsNumber = commentsNumber;
	}

	public String getCommentsDateTime() {
		return commentsDateTime;
	}

	public void setCommentsDateTime(String commentsDateTime) {
		this.commentsDateTime = commentsDateTime;
	}

	public String getCommentsContent() {
		return commentsContent;
	}

	public void setCommentsContent(String commentsContents) {
		this.commentsContent = commentsContents;
	}

	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	public int getPostNumber() {
		return postNumber;
	}

	public void setPostNumber(int postNumber) {
		this.postNumber = postNumber;
	}

//	toString메서드 재정의
	@Override
	public String toString() {
		return "CommentsVO [commentsNumber=" + commentsNumber + ", commentsDateTime=" + commentsDateTime
				+ ", commentsContent=" + commentsContent + ", userNumber=" + userNumber + ", postNumber=" + postNumber
				+ "]";
	}

	
	
}
