package vo;

public class DanggeunVO {
	
	private int number;
	private String name;
	private int price;
	private String address;
	private String url;
	private int view;
	
	public DanggeunVO() {;}

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

	@Override
		public String toString() {
			String str =  number + ", " + name + ", " + price + ", " + address + ", "  + url;
			return str;
	}

	
}
