import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAdapter {

   
   public static final int PRODUCT_SAVED_OK = 0;
   public static final int PRODUCT_DUPLICATE_ERROR = 1;
   public static final int CUSTOMER_SAVED_OK = 0;
   public static final int CUSTOMER_DUPLICATE_ERROR = 1;

   Connection conn = null;

   public int connect(String dbfile) {
      try {
         // db parameters
         String url = "jdbc:sqlite:" + dbfile;
         // create a connection to the database
         conn = DriverManager.getConnection(url);
      
         System.out.println("Connection to SQLite has been established.");
      
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         return CONNECTION_OPEN_FAILED;
      }
      return CONNECTION_OPEN_OK;
   }

   @Override
   public int disconnect() {
      try {
         conn.close();
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         return CONNECTION_CLOSE_FAILED;
      }
      return CONNECTION_CLOSE_OK;
   }

   public ProductModel loadProduct(int productID) {
      ProductModel product = null;
   
      try {
         String sql = "SELECT ProductID, Name, Price, Quantity FROM Products WHERE ProductID = " + productID;
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         if (rs.next()) {
            product = new ProductModel();
            product.mProductID = rs.getInt("ProductID");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");
         }
      
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
      return product;
   }
   public int saveProduct(ProductModel product) {
      try {
         String sql = "INSERT INTO Products(ProductID, Name, Price, Quantity) VALUES " + product;
         System.out.println(sql);
         Statement stmt = conn.createStatement();
         stmt.executeUpdate(sql);
      
      } catch (Exception e) {
         String msg = e.getMessage();
         System.out.println(msg);
         if (msg.contains("UNIQUE constraint failed"))
            return PRODUCT_DUPLICATE_ERROR;
      }
   
      return PRODUCT_SAVED_OK;
   }

   @Override
   public int savePurchase(PurchaseModel purchase) {
      try {
         String sql = "INSERT INTO purchase VALUES " + purchase;
         System.out.println(sql);
         Statement stmt = conn.createStatement();
         stmt.executeUpdate(sql);
      
      } catch (Exception e) {
         String msg = e.getMessage();
         System.out.println(msg);
         if (msg.contains("UNIQUE constraint failed"))
            return PURCHASE_DUPLICATE_ERROR;
      }
   
      return PURCHASE_SAVED_OK;
   
   }

   public CustomerModel loadCustomer(int customerID) {
      CustomerModel customer = new CustomerModel();
   
      try {
         String sql = "SELECT CustomerID, Name, Phone, PaymentMethod FROM Customers WHERE CustomerID = " + customerID;
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         customer.mCustomerID = rs.getInt("CustomerID");
         customer.mName = rs.getString("Name");
         customer.mPhone = rs.getDouble("Phone");
         customer.mPaymentMethod= rs.getString("PaymentMethod");
      
      
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
      return customer;
   }
   public int saveCustomer(CustomerModel customer) {
      try {
         String sql = "INSERT INTO Customers(CustomerID, Name, Phone, PaymentMethod) VALUES " + customer;
         System.out.println(sql);
         Statement stmt = conn.createStatement();
         stmt.executeUpdate(sql);
      
      } catch (Exception e) {
         String msg = e.getMessage();
         System.out.println(msg);
         if (msg.contains("UNIQUE constraint failed"))
            return CUSTOMER_DUPLICATE_ERROR;
      }
   
      return CUSTOMER_SAVED_OK;
   }

   @Override
   public int loadPurchase(int id) {
      return 0;
   }
}
