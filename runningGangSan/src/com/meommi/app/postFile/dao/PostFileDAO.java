package com.meommi.app.postFile.dao;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meommi.app.postFile.vo.PostFileVO;
import com.meommi.mybatis.config.MyBatisConfig;

//데이터베이스의 data에 접근하기 위한 dao클래스 선언
public class PostFileDAO {
	
//	초기 설정을 하며 db와 연결후 connection객체를 반환받는다.
	   SqlSessionFactory sqlSessionFactory = MyBatisConfig.getSqlsessionFactory();
	   SqlSession sqlSession;
	   
//		기본 생성자 선언하며, sqlSessionFactory의 openSession메서드를 통해 auto commit옵션을 true로 설정
	   public PostFileDAO() {
		   sqlSession = sqlSessionFactory.openSession(true);
	   }
	   
//		List<PostFileVO>타입의 selectList 메서드를 통해 페이지당 가져올 게시글 별 댓글수를 조회하여 반환하는 메서드
	   public List<PostFileVO> selectPostfileDefault(){
		   return sqlSession.selectList("PostFile.selectPostfileDefault");
	   }
	   
//		List<PostFileVO>타입의 selectList 메서드를 통해 스크롤 이벤트시 1개의 게시글에 해당하는 파일수를 조회하여 반환하는 메서드
	   public List<PostFileVO> selectPostfileRow(int startIndex){
		   return sqlSession.selectList("PostFile.selectPostfileRow",startIndex);
	   }
	   
//		게시글 추가시 파일 추가를 위한 메서드
	   public void insert(PostFileVO postFileVO) {
	         sqlSession.insert("PostFile.insert",postFileVO);
	      }
	   
//	   팔로우하는 사람들의 게시글에 해당하는 파일수 조회를 위한 메서드
	   public List<PostFileVO> selectFollowerPostfileDefault(int userNumber){
		   return sqlSession.selectList("PostFile.selectFollowerPostfileDefault",userNumber);
	   }
}
