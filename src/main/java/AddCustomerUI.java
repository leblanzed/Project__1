import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerUI {

   public JFrame view;

   public JButton btnAdd = new JButton("Add");
   public JButton btnCancel = new JButton("Cancel");

   public JTextField txtCustomerID = new JTextField(20);
   public JTextField txtName = new JTextField(20);
   public JTextField txtPhone = new JTextField(20);
   public JTextField txtPaymentmethod = new JTextField(20);


   public AddCustomerUI() {
      this.view = new JFrame();
      
      view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     
      view.setTitle("Add Customer");
      view.setSize(600, 400);
      view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));
   
      String[] labels = {"CustomerID ", "Name ", "Phone ", "PaymentMethod "};
   
      JPanel line1 = new JPanel(new FlowLayout());
      line1.add(new JLabel("CustomerID "));
      line1.add(txtCustomerID);
      view.getContentPane().add(line1);
   
      JPanel line2 = new JPanel(new FlowLayout());
      line2.add(new JLabel("Name "));
      line2.add(txtName);
      view.getContentPane().add(line2);
   
      JPanel line3 = new JPanel(new FlowLayout());
      line3.add(new JLabel("Phone "));
      line3.add(txtPhone);
      view.getContentPane().add(line3);
   
      JPanel line4 = new JPanel(new FlowLayout());
      line4.add(new JLabel("Payment method "));
      line4.add(txtPaymentmethod);
      view.getContentPane().add(line4);
   
      JPanel panelButtons = new JPanel(new FlowLayout());
      panelButtons.add(btnAdd);
      panelButtons.add(btnCancel);
      view.getContentPane().add(panelButtons);
   
      btnAdd.addActionListener(new AddButtonListerner());
   
      btnCancel.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               view.dispose();
            }
         });
   
   }

   public void run() {
      view.setVisible(true);
   }
   
   class AddButtonListerner implements ActionListener {
   
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
         CustomerModel customer = new CustomerModel();
      
         String id = txtCustomerID.getText();
      
         if (id.length() == 0) {
            JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
            return;
         }
      
         try {
            customer.mCustomerID = Integer.parseInt(id);
         } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
            return;
         }
      
         String name = txtName.getText();
         if (name.length() == 0) {
            JOptionPane.showMessageDialog(null, "Customer name cannot be empty!");
            return;
         }
      
         customer.mName = name;
      
         String phone = txtPhone.getText();
         try {
            customer.mPhone = Double.parseDouble(phone);
         } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Phone is invalid!");
            return;
         }
      
         String PaymentMethod = txtPaymentmethod.getText();
         if (PaymentMethod.length() == 0) {
            JOptionPane.showMessageDialog(null, "Payment Method cannot be empty!");
            return;
         }
      
         customer.mPaymentMethod = PaymentMethod;
      
         switch (StoreManager.getInstance().getDataAdapter().saveCustomer(customer)) {
            case SQLiteDataAdapter.CUSTOMER_DUPLICATE_ERROR:
               JOptionPane.showMessageDialog(null, "Product NOT added successfully! Duplicate product ID!");
            default:
               JOptionPane.showMessageDialog(null, "Product added successfully!" + customer);
         }
      }
   }
   

}