package com.meommi.app.post;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meommi.app.Result;

//web.xml에서 지정한 매핑 이름에 대해 수행되는 클래스이며, *.po로 끝나는 확장자는 모두 PostFrontController로 이동
public class PostFrontController extends HttpServlet {
	
//	get메서드 재정의
     @Override
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         doProcess(req, resp);
      }
      
// 	post메서드 재정의
      @Override
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         doProcess(req, resp);
      }
      
//  	get 또는 post로 요청시 실행을 위해 doProcess를 재정의하여 하나의 메서드로 처리가 가능하게 함
      protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	  
//  		requestURI는 경로 및 매개변수로 전달되는 파라미터 전부를 포함
  		String requestURI = req.getRequestURI();
  		
//  		contextPath파라미터를 제외한 경로만을 포함
  		String contextPath = req.getContextPath();
  		
//  		URI중 contextPath에 해당되는 부분만 추출
  		String request = requestURI.substring(contextPath.length());
  		
//  		redirect의 경우가 있을 수 있으므로 Result객체 초기화
  		Result result = null;
         
//		request의 경로가 "/meommi/Postlist.po"인 경우
         if(request.equals("/meommi/Postlist.po")) {
             new PostListController().execute(req, resp);
             
//     	request의 경로가 "/meommi/PostlistOk.po"인 경우
         }else if(request.equals("/meommi/PostlistOk.po")) {
            new PostListOkController().execute(req, resp);
            
//    	request의 경로가 "/meommi/PostRegistration.po"인 경우
         }else if (request.equals("/meommi/PostRegistration.po")) {
            new PostRegistrationController().execute(req, resp);
            
//    		request의 경로가 "/meommi/PostRetouch.po"인 경우
         }else if(request.equals("/meommi/PostRetouch.po")) {
        	 new PostRetouchController().execute(req, resp);
        	 
//     	request의 경로가 "/meommi/PostDelete.po"인 경우
         }else if(request.equals("/meommi/PostDelete.po")) {
        	 new PostDeleteController().execute(req, resp);
        	 
//     	request의 경로가 "/meommi/PostlistFollower.po"인 경우
         }else if(request.equals("/meommi/PostlistFollower.po")) {
        	 new PostListFollowerController().execute(req, resp);
        	 
//     	request의 경로가 "/meommi/PostlistFollowing.po"인 경우
         }else if(request.equals("/meommi/PostlistFollowing.po")) {
        	 new postListFollowingController().execute(req,resp);
         }
            
         /*
          * if(result != null) { if(result.isRedirect()) {
          * resp.sendRedirect(result.getPath()); }else { RequestDispatcher dispatcher =
          * req.getRequestDispatcher(result.getPath()); dispatcher.forward(req, resp); }
          * }
          */
         }
}