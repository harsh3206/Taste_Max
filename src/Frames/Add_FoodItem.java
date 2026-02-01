package Frames;

import Connection.DBUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager.*;

public class Add_FoodItem implements ActionListener {

    JFrame addfood_frame;
    JPanel addfood_panel;
    JLabel food_id, food_name, food_category, food_price;
    JTextField name_txt, price_txt;
    JComboBox category_combobox;
    JButton add, back;
    String category[] = {"Pizza", "Burger", "Pasta", "Chinese", "Fries", "Snacks & Quick Bites", "Ice-Cream", "Cold-Drinks"};
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel food_tablefodel;
    JTable food_table;

    Add_FoodItem() throws SQLException {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }

        //frame code
        addfood_frame = new JFrame("ADD FOOD");
        addfood_frame.setSize(1200, 800);
        addfood_frame.setLayout(null);
        addfood_frame.setLocationRelativeTo(null);
        addfood_frame.setResizable(false);
        addfood_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adding panel to JFrame
        addfood_panel = new JPanel();
        addfood_panel.setLayout(null);
        addfood_panel.setBackground(new Color(241, 245, 220));
        addfood_panel.setBounds(0, 0, 1200, 800);
        addfood_frame.add(addfood_panel);

        //all label code
        food_name = new JLabel("Food Category");
        food_name.setFont(new Font("century gothic", Font.BOLD, 20));
        food_name.setBounds(10, 100, 150, 30);
        addfood_panel.add(food_name);
        food_category = new JLabel("Food Item");
        food_category.setFont(new Font("century gothic", Font.BOLD, 20));
        food_category.setBounds(10, 150, 150, 30);
        addfood_panel.add(food_category);
        food_price = new JLabel("Food Price");
        food_price.setFont(new Font("century gothic", Font.BOLD, 20));
        food_price.setBounds(10, 200, 150, 30);
        addfood_panel.add(food_price);

        //textfield & jcombobox code
        category_combobox = new JComboBox(category);
        category_combobox.setFont(new Font("century gothic", Font.BOLD, 15));
        category_combobox.setBounds(200, 100, 200, 30);
        addfood_panel.add(category_combobox);
        name_txt = new JTextField("");
        name_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        name_txt.setBounds(200, 150, 200, 30);
        addfood_panel.add(name_txt);
        price_txt = new JTextField("");
        price_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        price_txt.setBounds(200, 200, 200, 30);
        addfood_panel.add(price_txt);

        //button code
        add = new JButton("Add");
        add.setFont(new Font("century gothic", Font.BOLD, 20));
        add.setBounds(130, 300, 200, 50);
        add.addActionListener(this);
        addfood_panel.add(add);
        back = new JButton("Back");
        back.setFont(new Font("century gothic", Font.BOLD, 20));
        back.setBounds(130, 400, 200, 50);
        back.addActionListener(this);
        addfood_panel.add(back);

        //table
        food_tablefodel = new DefaultTableModel(new String[]{"Id", "Category", "Name", "Price"}, 0);
        food_table = new JTable(food_tablefodel);
        food_table.setFont(new Font("century gothic", Font.BOLD, 20));
        food_table.setForeground(Color.WHITE);
        food_table.setBackground(new Color(35, 110, 100));
        food_table.setRowHeight(50);
        JScrollPane scrollpane = new JScrollPane(food_table);
        scrollpane.setBounds(450, 50, 700, 500);
        addfood_panel.add(scrollpane);

        addfood_frame.setVisible(true);
        con = DBUtil.getConnection();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            String name = name_txt.getText();
            String price = price_txt.getText();

            if (name.trim().isEmpty() || price.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill proper data in all fields...");
                return;
            }
            if (!name.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Food Item must contain only letters and spaces.");
                return;
            }
            // Validate numeric values
            int validPrice;
            try {
                validPrice = Integer.parseInt(price);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Food Price must contain only numbers.");
                return;
            }

            try {
                // insert code
                String query = "insert into food_details (food_name,food_category,food_price) values (?,?,?)";
                pst = con.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, category_combobox.getSelectedItem().toString());
                pst.setString(3, price);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(addfood_frame, "RECORD INSERTED SUCCESSFULLY!!");
                category_combobox.setSelectedIndex(0);
                name_txt.setText("");
                price_txt.setText("");
                loadtable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == back) {
            DashBoard db = new DashBoard();
            db.dashboard_frame.setVisible(true);
            addfood_frame.dispose();
        }
    }

    private void loadtable() {
        try {
            food_tablefodel.setRowCount(0);
            pst = con.prepareStatement("select * from food_details");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                food_tablefodel.addRow(new Object[]{rs.getInt("id"), rs.getString("food_category"), rs.getString("food_name"), rs.getString("food_price")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    public static void main(String args[]) throws SQLException {
        new Add_FoodItem();
    }
}
