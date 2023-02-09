package com.meommi.app.post;

import java.io.IOException;


import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meommi.app.Execute;
import com.meommi.app.Result;
import com.meommi.app.post.dao.PostDAO;
import com.meommi.app.post.vo.PostVO;
import com.meommi.app.postFile.dao.PostFileDAO;
import com.meommi.app.postFile.vo.PostFileVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

//Execute 인터페이스를 구현하는 PostRegistrationController클래스 선언
public class PostRegistrationController implements Execute {

	//	execute메서드 재정의
   @Override
   public Result execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      req.setCharacterEncoding("UTF-8");
      
//		데이터베이스의 data에 접근하기 위한 vo 및 dao객체들 생성
      PostDAO postDAO = new PostDAO();
      PostVO postVO = new PostVO();
      PostFileDAO postFileDAO = new PostFileDAO();
      PostFileVO postFileVO = new PostFileVO();
      
//      최근 추가된 게시글에 해당하는 번호를 위한 변수 선언
      int currentSequence = 0;
     
//      업로드된 이미지 파일을 저장하기 위한 경로
      String uploadPath = req.getSession().getServletContext().getRealPath("/") + "upload/";
//      String uploadPath="C:\\gb_0900_lhy\\jsp\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\runningtests\\upload";
      int fileSize = 1024 * 1024 * 5; //5M 
//      request 객체, 업로드 할 경로, 파일의 크키, 인코딩 방식, 이름변경정책
      MultipartRequest multipartRequest = new MultipartRequest(req, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());
      
//      파일 이름을 읽기위한 Enumeration<String>타입의 객체 생성
      Enumeration<String> fileNames = multipartRequest.getFileNames();
      
//		파라미터로 넘겨받은 postContent값을 문자열로 변환하여 저장
      String postContent = multipartRequest.getParameter("postContent");
            
//      세션에 해당하는 유저 번호를 userNumber라는 문자열에 해당하는 값을 문자열로 변환하여 저장
      String userNumber = String.valueOf(req.getSession().getAttribute("userNumber"));
      /* int userNumber = Integer.valueOf(req.getParameter("userNumber")); */
      
      
      postVO.setPostContent(postContent);
      postVO.setUserNumber(Integer.valueOf(userNumber));
      
      
      
      postDAO.insertContent(postVO);
      currentSequence = postDAO.selectCurrentSequence();
      



//      화면에서 구현된 type이 file인 input태그 name속성 값을 모두 가져온다.
      System.out.println(fileNames);
      System.out.println(fileNames.toString());
      
//      가져올 파일이름이 있을때까지 반복문 순회
      while(fileNames.hasMoreElements()) {
    	  
//    	  파일 저장후 불러올때 필요한 정보들을 저장하기 위한 문자열 값들을 추출하여 생성
         String fileName = fileNames.nextElement();
         String fileOriginalName = multipartRequest.getOriginalFileName(fileName);
         String fileSystemName = multipartRequest.getFilesystemName(fileName);
         System.out.println(fileName);
         
//         
         if(fileOriginalName == null) {continue;}
         
//         데이터베이스에 분리된 파일 정보들을 저장하기 위한 부분(같은 파일명이 있을때 범용식별자 처럼 구분짓기 위한 파일명, 원본 파일명)
         postFileVO.setPostFileOriginName(fileOriginalName);
         postFileVO.setPostFileSystemName(fileSystemName);
         
         
         postFileVO.setPostNumber(currentSequence);
         postFileVO.setUserNumber(Integer.valueOf(userNumber));
         postFileDAO.insert(postFileVO);
      }
      
//		페이지 이동이 없으므로 null반환
      return null;
   }

}
   
