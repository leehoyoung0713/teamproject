package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vo.UserDataVO;
import vo.UserVO;

public class UserDAO {
	private final int KEY = 3;

	public Connection connection; // 연결 객체
	public PreparedStatement preparedStatement; // 쿼리(SQL문) 객체
	public ResultSet resultSet; // 결과 테이블 객체
	Statement stmt = null;
//	아이디 중복검사
	public boolean checkId(String id) {
//		TBL_USERS 테이블에 내가 찾고자 하는 ID가 몇 개 있는지 찾는 쿼리
//		즉, 1개가 나온다면 중복된 아이디로 간수하고, boolean 을 true 값으로 준다. → checkId 메소드가 true이면 중복된 아이디라는 뜻
		String query = "SELECT COUNT(USER_ID) FROM TBL_USERS WHERE USER_ID = ?";
		boolean check = false;

		try {
			connection = DBConnector.getConnection();
			preparedStatement = connection.prepareStatement(query);
//			쿼리문의 첫 번째 물음표에 checkId 메소드 매개변수를 넣는다. 
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();	//Select문이기 때문에 executeQuery를 사용한다.
			resultSet.next();
//			resultSet의 getInt는 0또는 1만 나올 수 있다.(아이디는 1개만 가질 수 있으므로, 같은 아이디값은 0 또는 1이 나올 수밖에 없기 때문이다.)
			check = resultSet.getInt(1) == 1;
		} catch (SQLException e) {
			System.out.println("checkId()에서 쿼리문 오류");
		} finally {
			try {
//	            연결 객체 모두 닫기
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		return check;
	}
	
//	회원가입 (TBL_USERS 에 저장)
//	join의 객체는 가입한 유저의 모든 정보를 저장해야 하므로, 매개변수로 UserVO 전체를 받는다.
	public void join(UserVO userVO) {
//		TBL_USERS 테이블에 유저 번호, ID, PW, 선호 카테고리를 집어넣는다.
//		유저 번호는 시퀀스를 사용하여 1씩 증가시킨다.
		 String query = "INSERT INTO TBL_USERS "
		            + "(USER_NUMBER, USER_ID, USER_PW, USER_CG) "
		            + "VALUES(USERS_SEQ.NEXTVAL, ?, ?, ?) ";
		 
		 try {
			connection = DBConnector.getConnection();
			 preparedStatement = connection.prepareStatement(query);
			 preparedStatement.setString(1, userVO.getUserID());
			 preparedStatement.setString(2, userVO.getUserPassword());
			 preparedStatement.setString(3, userVO.getCategory());
			 preparedStatement.executeUpdate(); 		//			 Select문이 아니기 때문에 executeUpdate()를 사용한다.
			 
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("join()에서 쿼리문 오류");
		} finally {
	         try {
	             if(preparedStatement != null) {
	                preparedStatement.close();
	             }
	             if(connection != null) {
	                connection.close();
	             }
	          } catch (SQLException e) {
	             throw new RuntimeException(e.getMessage());
	          }
	       }
		 
	}
	
//	검색어 저장! (TBL_USERSDATA에 저장)
//	유저의 아이디 정보와 검색어 정보를 일치시키므로 UserDataVO 전체를 객체로 받는다.
	public void joinUserData(UserDataVO userDataVO) {
//		TBL_USERSDATA 테이블에 제품번호, ID, 유저가 검색한 키워드 값을 추가한다. 
		 String query = "INSERT INTO TBL_USERSDATA "
		            + "(USERSDATA_PRODUCTNUMBER, USERSDATA_ID, USERSDATA_KEY) "
		            + "VALUES(USERSDATA__SEQ.NEXTVAL, ?, ?) ";
		 
		 try {
			connection = DBConnector.getConnection();
			 preparedStatement = connection.prepareStatement(query);
			 preparedStatement.setString(1, userDataVO.getId());
			 preparedStatement.setString(2, userDataVO.getKeyWord());
			 preparedStatement.executeUpdate();
			 
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("join()에서 쿼리문 오류");
		} finally {
	         try {
	             if(preparedStatement != null) {
	                preparedStatement.close();
	             }
	             if(connection != null) {
	                connection.close();
	             }
	          } catch (SQLException e) {
	             throw new RuntimeException(e.getMessage());
	          }
	       }
		 
	}
//	로그인
//	로그인을 위해 id와 비밀번호가 일치하는지 여부를 확인해야하므로, login 메소드 객체로 id와 password 값을 받는다.
	 public int login(String userId, String userPassword) {
//		 TBL_USERS 테이블의 아이디와 비밀번호가 일치하는 유저의 번호를 불러온다.
	      String query = "SELECT USER_NUMBER FROM TBL_USERS WHERE USER_ID = ? AND USER_PW = ?";
	      int userNumber = 0;
	      try {
	         connection = DBConnector.getConnection();
	         preparedStatement = connection.prepareStatement(query);
	         preparedStatement.setString(1, userId);
	         preparedStatement.setString(2, userPassword);
	         resultSet = preparedStatement.executeQuery();
//	         resultSet.next()는 결과값이 존재한다면 true를 반환하여, 유저 정보와 맞는 유저 번호를 반환한다. 
	         if(resultSet.next()) {
	            userNumber = resultSet.getInt("USER_NUMBER");
	         }
	         
	      } catch (SQLException e) {
	         e.printStackTrace();
	         System.out.println("login() 쿼리문 오류");
	      } finally {
	         try {
	            if(resultSet != null) {
	               resultSet.close();
	            }
	            if(preparedStatement != null) {
	               preparedStatement.close();
	            }
	            if(connection != null) {
	               connection.close();
	            }
	         } catch (SQLException e) {
	            throw new RuntimeException(e.getMessage());
	         }
	      }
	      return userNumber;
	   }
	 
//	유저 자신의 개인 정보 조회
//	나의 개인정보 전체가 조회되어야 하므로 리턴은 UserVO 로 받는다.
	public UserVO selectUser(int userNumber) {
//		유저 번호와 일치하는 유저 정보 출력
		String query = "SELECT USER_NUMBER, USER_ID, USER_PW, USER_CG FROM TBL_USERS "
	            + "WHERE USER_NUMBER = ?";
		UserVO userVO = new UserVO();
		ArrayList<UserVO> cgs = new ArrayList<UserVO>();
		try {
			connection = DBConnector.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userNumber);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			userVO.setUserNumber(resultSet.getInt(1));
			userVO.setUserID(resultSet.getString(2));
			userVO.setUserPassword(resultSet.getString(3));
			userVO.setCategory(resultSet.getString(4));
		} catch (SQLException e) {
			System.out.println("selectUser() 쿼리문 오류");
			e.printStackTrace();
		}finally {
	         try {
	             if(resultSet != null) {
	                resultSet.close();
	             }
	             if(preparedStatement != null) {
	                preparedStatement.close();
	             }
	             if(connection != null) {
	                connection.close();
	             }
	          } catch (SQLException e) {
	             throw new RuntimeException(e.getMessage());
	          }
		}
		return userVO;
	}
//	유저가 관심있는 카테고리 정보 수정
	public void update(UserVO userVO) {
//		정보 수정은 UPDATE 쿼리를 사용
//		쿼리문은 USER_NUMBER가 일치하는 계정의 정보 중 USER_CG를 입력하는 값으로 수정한다는 뜻. 
		String query = "UPDATE TBL_USERS "
	            + "SET USER_CG=? "
	            + "WHERE USER_NUMBER = ?";
		try {
			connection = DBConnector.getConnection();
			preparedStatement = connection.prepareStatement(query);
	         preparedStatement.setString(1, userVO.getCategory());
	         preparedStatement.setInt(2, userVO.getUserNumber());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update() 쿼리 오류");
			e.printStackTrace();
		}finally {
	         try {
	             if(preparedStatement != null) {
	                preparedStatement.close();
	             }
	             if(connection != null) {
	                connection.close(); 
	             }
	          } catch (SQLException e) {
	             throw new RuntimeException(e.getMessage());
	          }
	       }
	}	
}
