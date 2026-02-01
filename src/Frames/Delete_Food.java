package Frames;

import Connection.DBUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Delete_Food implements ActionListener {

    JFrame deleteframe;
    JPanel delete_panel;
    JLabel food_id, food_name, food_category, food_prize;
    JTextField category_txt, name_txt, price_txt;
    JComboBox foodid_combobox;
    JButton delete, search, back;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    JTable delete_food_table;
    DefaultTableModel delete_tablemodel;

    Delete_Food() throws java.sql.SQLException {
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
        deleteframe = new JFrame("DELETE FOOD");
        deleteframe.setSize(1200, 800);
        deleteframe.setLayout(null);
        deleteframe.setLocationRelativeTo(null);
        deleteframe.setResizable(false);
        deleteframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adding panel to JFrame
        delete_panel = new JPanel();
        delete_panel.setLayout(null);
        delete_panel.setBackground(new Color(14, 143, 212));
        delete_panel.setBounds(0, 0, 1200, 800);
        deleteframe.add(delete_panel);

        //all label code
        food_id = new JLabel("Food ID");
        food_id.setFont(new Font("century gothic", Font.BOLD, 20));
        food_id.setBounds(10, 50, 150, 30);
        delete_panel.add(food_id);
        food_category = new JLabel("Category");
        food_category.setFont(new Font("century gothic", Font.BOLD, 20));
        food_category.setBounds(10, 100, 150, 30);
        delete_panel.add(food_category);
        food_name = new JLabel("Food Name");
        food_name.setFont(new Font("century gothic", Font.BOLD, 20));
        food_name.setBounds(10, 150, 150, 30);
        delete_panel.add(food_name);
        food_prize = new JLabel("Food Prize");
        food_prize.setFont(new Font("century gothic", Font.BOLD, 20));
        food_prize.setBounds(10, 200, 150, 30);
        delete_panel.add(food_prize);

        //all textfield & jcombobox
        foodid_combobox = new JComboBox();
        foodid_combobox.setFont(new Font("century gothic", Font.BOLD, 15));
        foodid_combobox.setBounds(200, 50, 200, 30);
        delete_panel.add(foodid_combobox);
        category_txt = new JTextField("");
        category_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        category_txt.setBounds(200, 100, 200, 30);
        category_txt.setEditable(false);
        delete_panel.add(category_txt);
        name_txt = new JTextField("");
        name_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        name_txt.setBounds(200, 150, 200, 30);
        name_txt.setEditable(false);
        delete_panel.add(name_txt);
        price_txt = new JTextField("");
        price_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        price_txt.setBounds(200, 200, 200, 30);
        price_txt.setEditable(false);
        delete_panel.add(price_txt);

        //all button code
        search = new JButton("Search");
        search.setFont(new Font("century gothic", Font.BOLD, 20));
        search.setBounds(120, 350, 200, 50);
        search.addActionListener(this);
        delete_panel.add(search);
        delete = new JButton("Delete");
        delete.setFont(new Font("century gothic", Font.BOLD, 20));
        delete.setBounds(120, 420, 200, 50);
        delete.addActionListener(this);
        delete_panel.add(delete);
        back = new JButton("Back");
        back.setFont(new Font("century gothic", Font.BOLD, 20));
        back.setBounds(120, 490, 200, 50);
        back.addActionListener(this);
        delete_panel.add(back);

        //table
        delete_tablemodel = new DefaultTableModel(new String[]{"ID", "Category", "Name", "Price"}, 0);
        delete_food_table = new JTable(delete_tablemodel);
        delete_food_table.setShowGrid(true);
        delete_food_table.setFont(new Font("century gothic", Font.BOLD, 20));
        delete_food_table.setForeground(Color.WHITE);
        delete_food_table.setBackground(new Color(35, 110, 100));
        delete_food_table.setRowHeight(50);
        JScrollPane scrollpane = new JScrollPane(delete_food_table);
        scrollpane.setBounds(450, 50, 700, 500);
        delete_panel.add(scrollpane);

        deleteframe.setVisible(true);
        con = DBUtil.getConnection();
        loadtable();
        loaddata();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delete) {
            try {
                // TODO add your handling code here:
                String id = foodid_combobox.getSelectedItem().toString();
                pst = con.prepareStatement("delete from food_details where id = ?");
                pst.setString(1, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(deleteframe, "RECORD HAS BEEN DELETED SUCCESSFULLY");
                category_txt.setText("");
                name_txt.setText("");
                price_txt.setText("");
                loaddata();
                loadtable();
            } catch (SQLException ex) {
                Logger.getLogger(Delete_Food.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(Delete_Food.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == back) {
            DashBoard db = new DashBoard();
            db.dashboard_frame.setVisible(true);
            deleteframe.dispose();
        }
    }

    //load-data from database
    public void loaddata() {
        try {
            pst = con.prepareStatement("select id from food_details");
            rs = pst.executeQuery();
            foodid_combobox.removeAllItems();
            while (rs.next()) {
                foodid_combobox.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Delete_Food.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //load-data in data-table
    private void loadtable() {
        try {
            delete_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from food_details");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                delete_tablemodel.addRow(new Object[]{rs.getInt("id"), rs.getString("food_category"), rs.getString("food_name"), rs.getString("food_price")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    public static void main(String args[]) throws SQLException {
        new Delete_Food();
    }
}
