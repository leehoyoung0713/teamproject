package vo;

public class TotalDTO {
	private String companyName;
	private int number;
	private String name;
	private int price;
	private String address;
	private String url;
	private int view;
	
	public TotalDTO() {;}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	private String insertComma(String data) {
		String result = "";
		for (int i = 0; i < data.length(); i++) {
			if( i% 3 == 0 && i !=0) {
				result = "," + result;
			}
			result = data.charAt(data.length() -1 -i) + result;
		}
		return result;
	}

	
	
	@Override
		public String toString() {
			String str =  companyName + ", " + name + ", " + insertComma(String.valueOf(price)) + ", " + address + ", " + view + ", "  + url;
			return str;
	}
}
