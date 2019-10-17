public class CustomerModel {
   public int mCustomerID;
   public double mPhone;
   public String mName, mPaymentMethod;

   public String toString() {
      StringBuilder sb = new StringBuilder("(");
      sb.append(mCustomerID).append(",");
      sb.append("\"").append(mName).append("\"").append(",");
      sb.append(mPhone).append(",");
      sb.append(mPaymentMethod).append(")");
      return sb.toString();
   }
}
