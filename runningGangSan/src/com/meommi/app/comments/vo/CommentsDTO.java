package com.meommi.app.comments.vo;

//댓글 수정, 삭제시 화면에서 입력받을 데이터를 저장하기 위한 CommentsDTO클래스 선언
public class CommentsDTO {
	
//	멤버 변수 선언
   private int commentsNumber;
   private String commentsDatetime;
   private String commentsContent;
   private int userNumber;
   private int postNumber;
   private String userId;
   
//	기본 생성자 선언
    public CommentsDTO() {;}

//	getter, setter메서드 선언
   public int getCommentsNumber() {
      return commentsNumber;
   }

   public void setCommentsNumber(int commentsNumber) {
      this.commentsNumber = commentsNumber;
   }

   public String getCommentsDatetime() {
      return commentsDatetime;
   }

   public void setCommentsDatetime(String commentsDatetime) {
      this.commentsDatetime = commentsDatetime;
   }

   public String getCommentsContent() {
      return commentsContent;
   }

   public void setCommentsContent(String commentsContent) {
      this.commentsContent = commentsContent;
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

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

//	toString메서드 재정의
   @Override
   public String toString() {
      return "CommentsDTO [commentsNumber=" + commentsNumber + ", commentsDatetime=" + commentsDatetime
            + ", commentsContent=" + commentsContent + ", userNumber=" + userNumber + ", postNumber=" + postNumber
            + ", userId=" + userId +  "]";
   }

}
