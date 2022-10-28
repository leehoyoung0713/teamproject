package vo;

import java.util.ArrayList;

public class UserVO {
public UserVO() {;}

private int userNumber;
private String userID;
private String userPassword;
private String Category;


public int getUserNumber() {
	return userNumber;
}
public void setUserNumber(int userNumber) {
	this.userNumber = userNumber;
}
public String getUserID() {
	return userID;
}
public void setUserID(String userID) {
	this.userID = userID;
}
public String getUserPassword() {
	return userPassword;
}
public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
}
public String getCategory() {
	return Category;
}
public void setCategory(String category) {
	Category = category;
}
@Override
	public String toString() {
	
	String str = userNumber + ","
			+ userID + ","
			+ userPassword + ","
			+ Category;
		
		return str;
	}

}
