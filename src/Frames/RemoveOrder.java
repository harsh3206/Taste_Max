package Frames;

import Connection.DBUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class RemoveOrder implements ActionListener {

    JFrame removeframe;
    JComboBox<String> orderidcombobox;
    JLabel orderidlabel, categorylabel, itemlabel, employeelabel, pricelabel, customerlabel, quantitylabel, totalpricelabel;
    JTextField categoryfield, itemfield, pricefield, employeefield, customerfield, quantityfield, totalpricefield;
    JButton removeorder, back;
    JTable reomove_table;
    DefaultTableModel remove_tablemodel;
    Connection con;
    ResultSet rs;
    PreparedStatement pst;
    Statement stmt;

    RemoveOrder() throws SQLException {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
        removeframe = new JFrame("Remove Order");
        removeframe.setSize(1200, 800);
        removeframe.setLayout(null);
        removeframe.setLocationRelativeTo(null);
        removeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        removeframe.getContentPane().setBackground(new Color(255, 21, 102));

        //All Label code
        orderidlabel = new JLabel("Order ID");
        orderidlabel.setFont(new Font("century gothic", Font.BOLD, 20));
        orderidlabel.setBounds(50, 50, 180, 30);
        removeframe.add(orderidlabel);
        categorylabel = new JLabel("Item Category");
        categorylabel.setFont(new Font("century gothic", Font.BOLD, 20));
        categorylabel.setBounds(50, 100, 180, 30);
        removeframe.add(categorylabel);
        itemlabel = new JLabel("Item Name");
        itemlabel.setFont(new Font("century gothic", Font.BOLD, 20));
        itemlabel.setBounds(50, 150, 180, 30);
        removeframe.add(itemlabel);
        employeelabel = new JLabel("Employee Name");
        employeelabel.setFont(new Font("century gothic", Font.BOLD, 20));
        employeelabel.setBounds(50, 200, 180, 30);
        removeframe.add(employeelabel);
        pricelabel = new JLabel("Item Price");
        pricelabel.setFont(new Font("century gothic", Font.BOLD, 20));
        pricelabel.setBounds(50, 250, 180, 30);
        removeframe.add(pricelabel);
        customerlabel = new JLabel("Customer Name");
        customerlabel.setFont(new Font("century gothic", Font.BOLD, 20));
        customerlabel.setBounds(50, 300, 180, 30);
        removeframe.add(customerlabel);
        quantitylabel = new JLabel("No. of Items");
        quantitylabel.setFont(new Font("century gothic", Font.BOLD, 20));
        quantitylabel.setBounds(50, 350, 180, 30);
        removeframe.add(quantitylabel);
        totalpricelabel = new JLabel("Total Price");
        totalpricelabel.setFont(new Font("century gothic", Font.BOLD, 20));
        totalpricelabel.setBounds(50, 400, 180, 30);
        removeframe.add(totalpricelabel);

        //All textfiled and combobox
        orderidcombobox = new JComboBox<>();
        orderidcombobox.setFont(new Font("century gothic", Font.BOLD, 15));
        orderidcombobox.setBounds(250, 50, 200, 30);
        removeframe.add(orderidcombobox);
        categoryfield = new JTextField();
        categoryfield.setFont(new Font("century gothic", Font.BOLD, 15));
        categoryfield.setBounds(250, 100, 200, 30);
        categoryfield.setEditable(false);  // Non-editable
        removeframe.add(categoryfield);
        itemfield = new JTextField();
        itemfield.setFont(new Font("century gothic", Font.BOLD, 15));
        itemfield.setBounds(250, 150, 200, 30);
        itemfield.setEditable(false);
        removeframe.add(itemfield);
        employeefield = new JTextField();
        employeefield.setFont(new Font("century gothic", Font.BOLD, 15));
        employeefield.setBounds(250, 200, 200, 30);
        employeefield.setEditable(false);
        removeframe.add(employeefield);
        pricefield = new JTextField();
        pricefield.setFont(new Font("century gothic", Font.BOLD, 15));
        pricefield.setBounds(250, 250, 200, 30);
        pricefield.setEditable(false);
        removeframe.add(pricefield);
        customerfield = new JTextField();
        customerfield.setFont(new Font("century gothic", Font.BOLD, 15));
        customerfield.setBounds(250, 300, 200, 30);
        customerfield.setEditable(false);
        removeframe.add(customerfield);
        quantityfield = new JTextField();
        quantityfield.setFont(new Font("century gothic", Font.BOLD, 15));
        quantityfield.setBounds(250, 350, 200, 30);
        quantityfield.setEditable(false);
        removeframe.add(quantityfield);
        totalpricefield = new JTextField();
        totalpricefield.setFont(new Font("century gothic", Font.BOLD, 15));
        totalpricefield.setBounds(250, 400, 200, 30);
        totalpricefield.setEditable(false);
        removeframe.add(totalpricefield);

        //Button
        removeorder = new JButton("Remove Order");
        removeorder.setFont(new Font("century gothic", Font.BOLD, 20));
        removeorder.setBounds(155, 470, 175, 50);
        removeframe.add(removeorder);
        removeorder.addActionListener(this);
        back = new JButton("Back");
        back.setFont(new Font("century gothic", Font.BOLD, 20));
        back.setBounds(155, 540, 175, 50);
        removeframe.add(back);
        back.addActionListener(this);

        //table
        remove_tablemodel = new DefaultTableModel(new String[]{"Order-ID", "Item-Category", "Item-Name", "Emp-Name", "Item-Price", "Customer-Name", "Item-Quantity", "Total"}, 0) //code for not edit cell
        {
            public boolean isCellEditable(int row, int column) {
                return false; // make all cells non-editable
            }
        };

        reomove_table = new JTable(remove_tablemodel);
        reomove_table.setFont(new Font("century gothic", Font.PLAIN, 20));
        reomove_table.setForeground(Color.WHITE);
        reomove_table.setBackground(new Color(35, 110, 100));
        reomove_table.setRowHeight(50);
        reomove_table.getColumnModel().getColumn(0).setPreferredWidth(60);
        reomove_table.getColumnModel().getColumn(1).setPreferredWidth(180);
        reomove_table.getColumnModel().getColumn(2).setPreferredWidth(180);
        reomove_table.getColumnModel().getColumn(3).setPreferredWidth(120);
        reomove_table.getColumnModel().getColumn(4).setPreferredWidth(70);
        reomove_table.getColumnModel().getColumn(5).setPreferredWidth(120);
        reomove_table.getColumnModel().getColumn(6).setPreferredWidth(80);
        reomove_table.getColumnModel().getColumn(7).setPreferredWidth(80);

        reomove_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollpane = new JScrollPane(reomove_table);
        scrollpane.setBounds(470, 50, 700, 500);
        removeframe.add(scrollpane);

        removeframe.setVisible(true);
        con = DBUtil.getConnection();

        //show order id in combobox
        loadOrderIdsFromDatabase();
        loadtable();

        // Add listeners
        orderidcombobox.addActionListener(e -> {
            // Load selected order details into fields
            String selectedOrderId = (String) orderidcombobox.getSelectedItem();
            if (selectedOrderId != null) {
                loadOrderDetails(selectedOrderId);
            }
        });
    }

    // Remove selected order from database
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeorder) {
            try {
                String orderId = (String) orderidcombobox.getSelectedItem();
                if (orderId != null) {
                    // Connection conn = getConnection();
                    String sql = "delete from order_details where order_id = ?";
                    pst = con.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(orderId));// Convert String to int
                    pst.executeUpdate();
                    //  con.close();

                    JOptionPane.showMessageDialog(removeframe, "Order Removed Successfully!");

                    // Refresh the order ID list and clear the fields
                    clearFields();
                    loadOrderIdsFromDatabase();
                    loadtable();
                    // removeframe.dispose();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == back) {
            Home_Page hm = new Home_Page();
            hm.homepage_frame.setVisible(true);
            removeframe.dispose();
        }
    }

    //load OrderIds From Database
    private void loadOrderIdsFromDatabase() {
        try {
            // Connection conn = getConnection();
            String sql = "select order_id from order_details";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                orderidcombobox.addItem(rs.getString("order_id"));
            }

            //  con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load selected order details 
    private void loadOrderDetails(String orderId) {
        try {
            // Connection conn = getConnection();
            String sql = "select * from order_details where order_id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(orderId));
            rs = pst.executeQuery();

            if (rs.next()) {
                categoryfield.setText(rs.getString("item_category"));
                itemfield.setText(rs.getString("item_name"));
                employeefield.setText(rs.getString("emp_name"));
                pricefield.setText(String.valueOf(rs.getInt("item_price")));
                customerfield.setText(rs.getString("customer_name"));
                quantityfield.setText(String.valueOf(rs.getInt("item_quantity")));
                totalpricefield.setText(String.valueOf(rs.getInt("total")));
            }

            // con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Clear all fields after order removal
    private void clearFields() {
        orderidcombobox.removeAllItems();
        categoryfield.setText("");
        itemfield.setText("");
        pricefield.setText("");
        employeefield.setText("");
        customerfield.setText("");
        quantityfield.setText("");
        totalpricefield.setText("");
        loadOrderIdsFromDatabase();
    }

    private void loadtable() {
        try {
            // Connection con = getConnection();
            remove_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from order_details");
            rs = pst.executeQuery();
            while (rs.next()) {
                remove_tablemodel.addRow(new Object[]{rs.getInt("order_id"), rs.getString("item_category"), rs.getString("item_name"), rs.getString("emp_name"), rs.getString("item_price"), rs.getString("customer_name"), rs.getString("item_quantity"), rs.getString("total")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        new RemoveOrder();
    }
}
