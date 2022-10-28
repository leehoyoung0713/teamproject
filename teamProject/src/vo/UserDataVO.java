package vo;

public class UserDataVO {
private  int Number;
private  String Id;
private  String KeyWord;


public UserDataVO(){;}

public int getNumber() {
	return Number;
}

public  void setNumber(int number) {
	Number = number;
}

public  String getId() {
	return Id;
}

public  void setId(String id) {
	Id = id;
}

public String getKeyWord() {
	return KeyWord;
}

public  void setKeyWord(String keyWord) {
	KeyWord = keyWord;
}

@Override
public String toString() {
	String str = Number + "," + Id + "," + KeyWord;
	return str;
}


}
