package com.meommi.app.post.vo;

//댓글 작성시 화면에서 입력받을 데이터를 저장하기 위한 PostVO클래스 선언
public class PostVO {

//	멤버 변수 선언
   private int postNumber; 
   private int userNumber;
   private String postContent;
   private String postDateTime;
   
//	기본 생성자 선언
   public PostVO() {;}

//	getter, setter메서드 선언
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

   public String getPostContent() {
      return postContent;
   }

   public void setPostContent(String postContent) {
      this.postContent = postContent;
   }

   public String getPostDateTime() {
      return postDateTime;
   }

   public void setPostDateTime(String postDateTime) {
      this.postDateTime = postDateTime;
   }
   
//	toString메서드 재정의
   @Override
   public String toString() {
      return "PostVO [postNumber=" + postNumber + ", userNumber=" + userNumber + ", postContent=" + postContent
            + ", postDateTime=" + postDateTime +"]";
   }
   
   
}