package com.meommi.app.comments;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meommi.app.Execute;
import com.meommi.app.Result;
import com.meommi.app.comments.dao.CommentsDAO;
import com.meommi.app.comments.vo.CommentsDTO;

//Execute 인터페이스를 구현하는 CommentRetouchController클래스 선언
public class CommentRetouchController implements Execute {

	//	execute메서드 재정의
	@Override
	public Result execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		데이터베이스의 data에 접근하기 위한 dao객체들 생성
		CommentsDAO commentsDAO=new CommentsDAO();
		CommentsDTO commentsDTO=new CommentsDTO();
		
//		댓글 수정 후 수정시간을 저장하기 위한 설정
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=new Date();
		String commentUpdateTime=simpleDateFormat.format(now);
		
//		파라미터로 넘겨받은 commentContent값을 문자열로 변환하여 저장
		String content=req.getParameter("commentContent");
		
//		파라미터로 넘겨받은 commentNumber값을 정수로 변환하여 저장
		int commentNumber=Integer.valueOf(req.getParameter("commentNumber"));
		
//		댓글 수정을 하기 위해 필요한 정보들을 인자로 전달
		commentsDTO.setCommentsContent(content);
		commentsDTO.setCommentsNumber(commentNumber);
		commentsDTO.setCommentsDatetime(commentUpdateTime);
		commentsDAO.updateComment(commentsDTO);
		
//		페이지 이동이 없으므로 null값 반환
		return null;
	}

}
