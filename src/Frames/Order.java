package Frames;

import Connection.DBUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.SQLException;
import java.awt.print.PrinterException;

public class Order implements ActionListener {

    JFrame orderframe;
    JComboBox<String> categorybox, itembox;
    JLabel orderidlabel, categorylabel, itemlabel, employeelabel, pricelabel, customerlabel, quantitylabel, totalpricelabel;
    JTextField orderid_txt, employee_txt, price_txt, customer_txt, quantity_txt, totalprice_txt;
    JButton placeorder, bill, print, back;
    JTextArea bill_textarea;
    Connection con;
    ResultSet rs;
    PreparedStatement pst;
    Statement stmt;

    // Constructor
    Order() throws SQLException {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }

        orderframe = new JFrame("Order Form");
        orderframe.setSize(1200, 800);
        orderframe.setLayout(null);
        orderframe.setLocationRelativeTo(null);
        orderframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        orderframe.getContentPane().setBackground(new Color(255, 210, 102));

        //All label 
        orderidlabel = new JLabel("Order ID");
        orderidlabel.setFont(new Font("century gothic", Font.BOLD, 20));
        orderidlabel.setBounds(50, 50, 180, 30);
        orderframe.add(orderidlabel);
        categorylabel = new JLabel("Item Category");
        categorylabel.setFont(new Font("century gothic", Font.BOLD, 20));
        categorylabel.setBounds(50, 100, 180, 30);
        orderframe.add(categorylabel);
        itemlabel = new JLabel("Item Name");
        itemlabel.setFont(new Font("century gothic", Font.BOLD, 20));
        itemlabel.setBounds(50, 150, 180, 30);
        orderframe.add(itemlabel);
        employeelabel = new JLabel("Employee Name");
        employeelabel.setFont(new Font("century gothic", Font.BOLD, 20));
        employeelabel.setBounds(50, 200, 180, 30);
        orderframe.add(employeelabel);
        pricelabel = new JLabel("Item Price");
        pricelabel.setFont(new Font("century gothic", Font.BOLD, 20));
        pricelabel.setBounds(50, 250, 180, 30);
        orderframe.add(pricelabel);
        customerlabel = new JLabel("Customer Name");
        customerlabel.setFont(new Font("century gothic", Font.BOLD, 20));
        customerlabel.setBounds(50, 300, 180, 30);
        orderframe.add(customerlabel);
        quantitylabel = new JLabel("No. of Items");
        quantitylabel.setFont(new Font("century gothic", Font.BOLD, 20));
        quantitylabel.setBounds(50, 350, 180, 30);
        orderframe.add(quantitylabel);
        totalpricelabel = new JLabel("Total Price");
        totalpricelabel.setFont(new Font("century gothic", Font.BOLD, 20));
        totalpricelabel.setBounds(50, 400, 180, 30);
        orderframe.add(totalpricelabel);

        //All textfiled and combobox    
        // con = DBUtil.getConnection();
        orderid_txt = new JTextField(generateOrderId());
        orderid_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        orderid_txt.setBounds(250, 50, 200, 30);
        orderid_txt.setEditable(false); // Make the field non-editable
        orderframe.add(orderid_txt);
        categorybox = new JComboBox<>(new String[]{"Please Select", "Pizza", "Burger", "Pasta", "Chinese", "Fries", "Snacks & Quick Bites", "Ice-Cream", "Cold-Drinks"});
        categorybox.setFont(new Font("century gothic", Font.BOLD, 15));
        categorybox.setBounds(250, 100, 200, 30);
        orderframe.add(categorybox);
        itembox = new JComboBox<>();
        itembox.setFont(new Font("century gothic", Font.BOLD, 15));
        itembox.setBounds(250, 150, 200, 30);
        orderframe.add(itembox);
        employee_txt = new JTextField();
        employee_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        employee_txt.setBounds(250, 200, 200, 30);
        orderframe.add(employee_txt);
        price_txt = new JTextField();
        price_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        price_txt.setBounds(250, 250, 200, 30);
        price_txt.setEditable(false); // Cannot be edited by the user
        orderframe.add(price_txt);
        customer_txt = new JTextField();
        customer_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        customer_txt.setBounds(250, 300, 200, 30);
        orderframe.add(customer_txt);
        quantity_txt = new JTextField();
        quantity_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        quantity_txt.setBounds(250, 350, 200, 30);
        orderframe.add(quantity_txt);
        totalprice_txt = new JTextField();
        totalprice_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        totalprice_txt.setBounds(250, 400, 200, 30);
        totalprice_txt.setEditable(false); // Cannot be edited by the user
        orderframe.add(totalprice_txt);

        //Button
        placeorder = new JButton("Place Order");
        placeorder.setFont(new Font("century gothic", Font.BOLD, 20));
        placeorder.setBounds(155, 450, 175, 50);
        orderframe.add(placeorder);
        placeorder.addActionListener(this);
        bill = new JButton("Generate Bill");
        bill.setFont(new Font("century gothic", Font.BOLD, 20));
        bill.setBounds(155, 520, 175, 50);
        orderframe.add(bill);
        bill.addActionListener(this);
        back = new JButton("Back");
        back.setFont(new Font("century gothic", Font.BOLD, 20));
        back.setBounds(155, 590, 175, 50);
        orderframe.add(back);
        back.addActionListener(this);
        print = new JButton("Print");
        print.setFont(new Font("century gothic", Font.BOLD, 20));
        print.setBounds(700, 600, 175, 50);
        orderframe.add(print);
        print.addActionListener(this);

        //adding textarea into frame
        bill_textarea = new JTextArea(80, 80);
        bill_textarea.setBounds(500, 50, 550, 500);
        bill_textarea.setFont(new Font("Courier New", Font.BOLD, 20));
        bill_textarea.setBackground(new Color(175, 238, 238));
        orderframe.add(bill_textarea);

        orderframe.setVisible(true);
        con = DBUtil.getConnection();

        // Item Category Selection Listener
        categorybox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                loadItemsFromDatabase((String) categorybox.getSelectedItem());
            }
        });

        // Item Selection Listener to fill price
        itembox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                fillItemPrice((String) itembox.getSelectedItem());
                updateTotalPrice();
            }
        });

        // Quantity Listener to update total price
        quantity_txt.addFocusListener(new FocusAdapter() {
            //@Override
            public void focusLost(FocusEvent e) {
                updateTotalPrice();
            }
        });
    }

    // Insert order in database
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == placeorder) {
            try {
                con = DBUtil.getConnection();
                String orderId = orderid_txt.getText().trim();
                String category = (String) categorybox.getSelectedItem();
                String item = (String) itembox.getSelectedItem();
                String employee = employee_txt.getText().trim();
                String customer = customer_txt.getText().trim();
                int quantity = Integer.parseInt(quantity_txt.getText().trim());
                int price = Integer.parseInt(price_txt.getText().trim());
                int totalPrice = Integer.parseInt(totalprice_txt.getText().trim());

                //Connection con = getConnection();
                String sql = "insert into order_details (order_id, item_category, item_name, emp_name, item_price, customer_name, item_quantity, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(orderId));
                pst.setString(2, category);
                pst.setString(3, item);
                pst.setString(4, employee);
                pst.setInt(5, price);
                pst.setString(6, customer);
                pst.setInt(7, quantity);
                pst.setInt(8, totalPrice);

                pst.executeUpdate();
                // con.close();

                JOptionPane.showMessageDialog(orderframe, "Order Placed Successfully!");
                // orderframe.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == back) {
            Home_Page hm = new Home_Page();
            hm.homepage_frame.setVisible(true);
            orderframe.dispose();

        } else if (e.getSource() == bill) {
            int id = Integer.parseInt(orderid_txt.getText());
            String cname = customer_txt.getText();
            String fitem = itembox.getSelectedItem().toString();
            int qty = Integer.parseInt(quantity_txt.getText());
            int price = Integer.parseInt(totalprice_txt.getText());
            bill_textarea.setText("""
                                         ************Taste-Max Restaurant*************
                                         \nYour Order Id Is : """ + id
                    + "\n\nCustomer Name : " + cname
                    + "\n\nFood Item : " + fitem
                    + "\n\nQuantity : " + qty
                    + "\n\nAmount Payable Is : " + price
                    + "\n\n\n***** Thanks For Order & Visit Again....*****");
        } else if (e.getSource() == print) {
            try {
                boolean complete = bill_textarea.print();
                if (complete) {
                    JOptionPane.showMessageDialog(null, "Printing Complete", "Print", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Printing Cancled", "Print", JOptionPane.WARNING_MESSAGE);
                }
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, "Printing Failed : " + ex.getMessage(), "Printing Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //Automatically generate orderid
    private String generateOrderId() {
        /*int newOrderId = 245; // Default start 

        try {
            //  Connection conn = getConnection();
            con = DBUtil.getConnection();
            String sql = "select max(order_id) as max_order_id from order_details";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                newOrderId = rs.getInt("max_order_id") + 1;  //Increase max order_id by 1
            }

            // con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        
        int newOrderId = 245; // Default start if table is empty

        try {
            con = DBUtil.getConnection();
            String sql = "select max(order_id) as max_order_id from order_details";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int maxorderid = rs.getInt("max_order_id");
                if (maxorderid > 0) {
                    newOrderId = maxorderid + 1;  // Generate next order ID
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(newOrderId);  // Return new Order ID as String
    }

    // Load items from database based on category
    private void loadItemsFromDatabase(String category) {
        itembox.removeAllItems();
        if (category.equals("Please Select")) {
            return;
        } else {
            try {
                // Connection conn = getConnection();
                con = DBUtil.getConnection();
                String sql = "select food_name from food_details where food_category = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, category);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    itembox.addItem(rs.getString("food_name"));
                }

                // con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Fill item price when item is selected
    private void fillItemPrice(String itemName) {
        if (itemName == null) {
            return;
        } else {
            try {
                //   Connection conn = getConnection();
                con = DBUtil.getConnection();
                String sql = "select food_price from food_details where food_name = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, itemName);
                rs = pst.executeQuery();

                if (rs.next()) {
                    price_txt.setText(String.valueOf(rs.getInt("food_price")));
                }

                // con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Update the total price field (price × quantity)
    private void updateTotalPrice() {
        try {
            int price = Integer.parseInt(price_txt.getText());
            int quantity = Integer.parseInt(quantity_txt.getText());
            int total = price * quantity;
            totalprice_txt.setText(String.valueOf(total)); //set price int value into string
        } catch (NumberFormatException e) {
            totalprice_txt.setText("");
        }
    }

    public static void main(String[] args) throws SQLException {
        new Order();
    }
}
