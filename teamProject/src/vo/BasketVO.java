package vo;

public class BasketVO {
   private String userId;
   private String itemName;
   private int itemPrice;
   private String itemAddress;
   private String itemUrl;
   private int itemView;
   

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getItemName() {
      return itemName;
   }

   public void setItemName(String itemName) {
      this.itemName = itemName;
   }

   public int getItemPrice() {
      return itemPrice;
   }

   public void setItemPrice(int itemPrice) {
      this.itemPrice = itemPrice;
   }
   
   public String getItemAddress() {
      return itemAddress;
   }

   public void setItemAddress(String itemAddress) {
      this.itemAddress = itemAddress;
   }

   public String getItemUrl() {
      return itemUrl;
   }

   public void setItemUrl(String itemUrl) {
      this.itemUrl = itemUrl;
   }

   public int getItemView() {
      return itemView;
   }

   public void setItemView(int itemView) {
      this.itemView = itemView;
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
      String str =  userId + ", " +  itemName + ", " + insertComma(String.valueOf(itemPrice)) + ", " + itemAddress + ", " + itemUrl + ", " + itemView;
      return str;
   }
}