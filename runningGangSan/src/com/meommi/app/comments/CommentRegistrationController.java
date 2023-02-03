package com.meommi.app.comments;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meommi.app.Execute;
import com.meommi.app.Result;
import com.meommi.app.comments.dao.CommentsDAO;
import com.meommi.app.comments.vo.CommentsDTO;

//Execute 인터페이스를 구현하는 CommentRegistrationController클래스 선언
public class CommentRegistrationController implements Execute {

	//	execute메서드 재정의
	@Override
	public Result execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		데이터베이스의 data에 접근하기 위한 dao객체들 생성
		CommentsDAO commentsDAO=new CommentsDAO();
		CommentsDTO commentsDTO=new CommentsDTO();
		
//		파라미터로 넘겨받은 content값을 문자열로 변환하여 저장
		String content=req.getParameter("content");
		
//		userNumber에 해당하는 session값을 정수로 변환하여 저장
		int memberNumber=(int) req.getSession().getAttribute("userNumber");
		
//		파라미터로 넘겨받은 postNumber값을 정수로 변환하여 저장
		int postNumber= Integer.valueOf(req.getParameter("postNumber"));
		
//		댓글 등록을 하기 위해 필요한 정보들을 인자로 전달
		commentsDTO.setCommentsContent(content);
		commentsDTO.setPostNumber(postNumber);
		commentsDTO.setUserNumber(memberNumber);
		commentsDAO.insertComment(commentsDTO);
		
//		페이지 이동이 없으므로 null반환
		return null;
	}

}
