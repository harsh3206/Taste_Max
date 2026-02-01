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

public class Update_Food implements ActionListener {

    JFrame updatefood;
    JPanel update_food_panel;
    JLabel food_id, food_category, food_name, food_price;
    JTextField category_txt, name_txt, price_txt;
    JComboBox foodid_combobox;
    JButton update, search, back;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    Statement stm;
    JTable update_food_table;
    DefaultTableModel update_food_tablemodel;

    Update_Food() throws SQLException {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {

        }
        //frame code
        updatefood = new JFrame("UPDATE FOOD");
        updatefood.setSize(1200, 800);
        updatefood.setLayout(null);
        updatefood.setLocationRelativeTo(null);
        updatefood.setResizable(false);
        updatefood.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adding panel to JFrame
        update_food_panel = new JPanel();
        update_food_panel.setLayout(null);
        update_food_panel.setBackground(new Color(13, 143, 213));
        update_food_panel.setBounds(0, 0, 1200, 800);
        updatefood.add(update_food_panel);

        //all label code
        food_id = new JLabel("Food ID");
        food_id.setFont(new Font("century gothic", Font.BOLD, 20));
        food_id.setBounds(10, 50, 150, 30);
        update_food_panel.add(food_id);
        food_category = new JLabel("Category");
        food_category.setFont(new Font("century gothic", Font.BOLD, 20));
        food_category.setBounds(10, 100, 150, 30);
        update_food_panel.add(food_category);
        food_name = new JLabel("Food Name");
        food_name.setFont(new Font("century gothic", Font.BOLD, 20));
        food_name.setBounds(10, 150, 150, 30);
        update_food_panel.add(food_name);
        food_price = new JLabel("Food Price");
        food_price.setFont(new Font("century gothic", Font.BOLD, 20));
        food_price.setBounds(10, 200, 150, 30);
        update_food_panel.add(food_price);

        //all textfield & jcombobox
        foodid_combobox = new JComboBox();
        foodid_combobox.setFont(new Font("century gothic", Font.BOLD, 15));
        foodid_combobox.setBounds(200, 50, 200, 30);
        update_food_panel.add(foodid_combobox);
        category_txt = new JTextField("");
        category_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        category_txt.setBounds(200, 100, 200, 30);
        update_food_panel.add(category_txt);
        name_txt = new JTextField("");
        name_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        name_txt.setBounds(200, 150, 200, 30);
        update_food_panel.add(name_txt);
        price_txt = new JTextField("");
        price_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        price_txt.setBounds(200, 200, 200, 30);
        update_food_panel.add(price_txt);

        //all button code
        search = new JButton("Search");
        search.setFont(new Font("century gothic", Font.BOLD, 20));
        search.setBounds(120, 350, 200, 50);
        search.addActionListener(this);
        update_food_panel.add(search);
        update = new JButton("Update");
        update.setFont(new Font("century gothic", Font.BOLD, 20));
        update.setBounds(120, 420, 200, 50);
        update.addActionListener(this);
        update_food_panel.add(update);
        back = new JButton("Back");
        back.setFont(new Font("century gothic", Font.BOLD, 20));
        back.setBounds(120, 490, 200, 50);
        back.addActionListener(this);
        update_food_panel.add(back);

        //table
        update_food_tablemodel = new DefaultTableModel(new String[]{"Id", "Category", "Name", "Price"}, 0);
        update_food_table = new JTable(update_food_tablemodel);
        update_food_table.setShowGrid(true);
        update_food_table.setFont(new Font("century gothic", Font.BOLD, 20));
        update_food_table.setForeground(Color.WHITE);
        update_food_table.setBackground(new Color(35, 110, 100));
        update_food_table.setRowHeight(50);
        JScrollPane scrollpane = new JScrollPane(update_food_table);
        scrollpane.setBounds(450, 50, 700, 500);
        update_food_panel.add(scrollpane);

        updatefood.setVisible(true);
        con = DBUtil.getConnection();
        Loaddata();
        loadtable();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update) {
            String category = category_txt.getText();
            String name = name_txt.getText();
            String price = price_txt.getText();
            String id = foodid_combobox.getSelectedItem().toString();

            if (name.trim().isEmpty() || price.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill proper data in all fields...");
                return;
            }
            if (!category.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Food Category must contain only letters and spaces.");
                return;
            }
            if (!name.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Food Name must contain only letters and spaces.");
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
                pst = con.prepareStatement("update food_details set food_category=?,food_name=?,food_price=? where id=?");
                pst.setString(1, category);
                pst.setString(2, name);
                pst.setString(3, price);
                pst.setString(4, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(updatefood, "RECORD HAS BEEN UPDATED SUCCESSFULLY");
                loadtable();
                foodid_combobox.setSelectedIndex(0);
                category_txt.setText("");
                name_txt.setText("");
                price_txt.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(Update_Food.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == search) {
            try {
                String id = foodid_combobox.getSelectedItem().toString();
                pst = con.prepareStatement("select * from food_details where id = ?");
                pst.setString(1, id);
                rs = pst.executeQuery();
                if (rs.next() == true) {
                    category_txt.setText(rs.getString(2));
                    name_txt.setText(rs.getString(3));
                    price_txt.setText(rs.getString(4));
                } else {
                    JOptionPane.showMessageDialog(null, "No Record Found!!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Update_Food.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == back) {
            DashBoard db = new DashBoard();
            db.dashboard_frame.setVisible(true);
            updatefood.dispose();
        }
    }

    public void Loaddata() {
        try {
            pst = con.prepareStatement("select id from food_details");
            rs = pst.executeQuery();
            foodid_combobox.removeAllItems();
            while (rs.next()) {
                foodid_combobox.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Update_Food.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadtable() {
        try {
            update_food_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from food_details");
            rs = pst.executeQuery();
            while (rs.next()) {
                update_food_tablemodel.addRow(new Object[]{rs.getInt("id"), rs.getString("food_category"), rs.getString("food_name"), rs.getString("food_price")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    public static void main(String args[]) throws SQLException {
        new Update_Food();
    }

}
