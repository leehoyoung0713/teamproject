package com.meommi.app.comments;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meommi.app.Execute;
import com.meommi.app.Result;
import com.meommi.app.comments.dao.CommentsDAO;

public class CommentDeleteController implements Execute {

	@Override
	public Result execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CommentsDAO commentsDAO=new CommentsDAO();
		int commentsNumber=Integer.valueOf(req.getParameter("commentNumber"));
		commentsDAO.deleteComment(commentsNumber);
		return null;
	}

}
