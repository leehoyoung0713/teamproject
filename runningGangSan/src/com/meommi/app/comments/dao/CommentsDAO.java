package com.meommi.app.comments.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meommi.app.comments.vo.CommentsDTO;
import com.meommi.mybatis.config.MyBatisConfig;

//데이터베이스의 data에 접근하기 위한 dao클래스 선언
public class CommentsDAO {
	
//	초기 설정을 하며 db와 연결후 connection객체를 반환받는다.
	   SqlSessionFactory sqlSessionFactory = MyBatisConfig.getSqlsessionFactory();
	   SqlSession sqlSession;
	   
//		기본 생성자 선언하며, sqlSessionFactory의 openSession메서드를 통해 auto commit옵션을 true로 설정
	   public CommentsDAO() {
		   sqlSession = sqlSessionFactory.openSession(true);
	   }
	   
//		List<CommentsDTO>타입의 selectList 메서드를 통해 페이지당 가져올 게시글 별 댓글수를 조회하여 반환하는 메서드
	   public List<CommentsDTO> selectCommentDefault(){
		   return sqlSession.selectList("Comments.selectCommentDefault");
	   }

//		List<CommentsDTO>타입의 selectList 메서드를 통해 스크롤 이벤트시 1개의 게시글에 해당하는 댓글수를 조회하여 반환하는 메서드
		 public List<CommentsDTO> selectCommentRow(HashMap<String,Integer>Outputindex){ 
			 return sqlSession.selectList("Comments.selectCommentRow",Outputindex); 
		}
		 
//		댓글 삽입을 위한 메서드
		public void insertComment(CommentsDTO commentsDTO) {
			sqlSession.insert("Comments.insertComment",commentsDTO);
		}
		
//		댓글 수정을 위한 메서드
	   public void updateComment(CommentsDTO commentsDTO) {
		   sqlSession.update("Comments.updateComment",commentsDTO);
	   }
	   
//	   댓글 삭제를 위한 메서드
	   public void deleteComment(int commentsNumber) {
		   sqlSession.delete("Comments.deleteComment",commentsNumber);
	   }
	   
//	   팔로우하는 사람들의 게시글에 해당하는 댓글수 조회를 위한 메서드
	   public List<CommentsDTO> selectFollowerCommentDefault(int userNumber){
		   return sqlSession.selectList("Comments.selectFollowerCommentDefault",userNumber);
	   }
}
