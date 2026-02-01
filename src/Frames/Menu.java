package Frames;

import Connection.DBUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

public class Menu implements ChangeListener {

    JFrame menuframe;
    JPanel pizza_panel, burger_panel, pasta_panel, chinese_panel, fries_panel, snacks_quick_panel, icecream_panel, cloddrink_panel;
    JTabbedPane jtp;
    JTable pizza_table, burger_table, pasta_table, chinese_table, fries_table, snacks_quick_table, icecrame_table, cloddrink_table;
    DefaultTableModel pizza_tablemodel, burger_tablemodel, pasta_tablemodel, chinese_tablemodel, fries_tablemodel, snacks_quick_tablemodel, icecream_tablemodel, cloddrink_tablemodel;
    Connection con;
    ResultSet rs;
    PreparedStatement pst;

    Menu() throws SQLException {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }

        menuframe = new JFrame("Taste-Max Menu");
        menuframe.setSize(880, 700);
        //menuframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuframe.setLocationRelativeTo(null);

        //all panel
        pizza_panel = new JPanel();
        pizza_panel.setLayout(new GridLayout(1, 1));
        burger_panel = new JPanel();
        burger_panel.setLayout(new GridLayout(1, 1));
        pasta_panel = new JPanel();
        pasta_panel.setLayout(new GridLayout(1, 1));
        chinese_panel = new JPanel();
        chinese_panel.setLayout(new GridLayout(1, 1));
        fries_panel = new JPanel();
        fries_panel.setLayout(new GridLayout(1, 1));
        snacks_quick_panel = new JPanel();
        snacks_quick_panel.setLayout(new GridLayout(1, 1));
        icecream_panel = new JPanel();
        icecream_panel.setLayout(new GridLayout(1, 1));
        cloddrink_panel = new JPanel();
        cloddrink_panel.setLayout(new GridLayout(1, 1));

        //adding panel to jtabbedpane
        jtp = new JTabbedPane(JTabbedPane.TOP);
        jtp.setBackground(Color.YELLOW);
        jtp.setFont(new Font("century gothic", Font.BOLD, 20));
        jtp.addChangeListener(this);
        jtp.addTab("Pizza", pizza_panel);
        jtp.addChangeListener(this);
        jtp.addTab("Burger", burger_panel);
        jtp.addChangeListener(this);
        jtp.addTab("Pasta", pasta_panel);
        jtp.addChangeListener(this);
        jtp.addTab("Chinese", chinese_panel);
        jtp.addChangeListener(this);
        jtp.addTab("Fries", fries_panel);
        jtp.addChangeListener(this);
        jtp.addTab("Snacks & Quick Bites", snacks_quick_panel);
        jtp.addChangeListener(this);
        jtp.addTab("Ice-Cream", icecream_panel);
        jtp.addChangeListener(this);
        jtp.addTab("Cold-Drinks", cloddrink_panel);

        //adding pizza table in panel1
        pizza_tablemodel = new DefaultTableModel(new String[]{"Id","Category","Name", "Price"}, 0);
        pizza_table = new JTable(pizza_tablemodel);
        pizza_table.setFont(new Font("century gothic", Font.BOLD, 20));
        pizza_table.setForeground(Color.WHITE);
        pizza_table.setBackground(new Color(35, 110, 100));
        pizza_table.setRowHeight(50);
        JScrollPane scrollpane = new JScrollPane(pizza_table);
        scrollpane.setBounds(450, 50, 700, 500);
        pizza_panel.add(scrollpane);

        //adding burger table in panel2
        burger_tablemodel = new DefaultTableModel(new String[]{"Id", "Category", "Name", "Price"}, 0);
        burger_table = new JTable(burger_tablemodel);
        burger_table.setFont(new Font("century gothic", Font.BOLD, 20));
        burger_table.setForeground(Color.WHITE);
        burger_table.setBackground(new Color(35, 110, 100));
        burger_table.setRowHeight(50);
        JScrollPane scrollpane2 = new JScrollPane(burger_table);
        scrollpane2.setBounds(450, 50, 700, 500);
        burger_panel.add(scrollpane2);

        //adding pasta table in panel3
        pasta_tablemodel = new DefaultTableModel(new String[]{"Id", "Category", "Name", "Price"}, 0);
        pasta_table = new JTable(pasta_tablemodel);
        pasta_table.setFont(new Font("century gothic", Font.BOLD, 20));
        pasta_table.setForeground(Color.WHITE);
        pasta_table.setBackground(new Color(35, 110, 100));
        pasta_table.setRowHeight(50);
        JScrollPane scrollpane3 = new JScrollPane(pasta_table);
        scrollpane3.setBounds(450, 50, 700, 500);
        pasta_panel.add(scrollpane3);

        //adding pasta table in panel4
        chinese_tablemodel = new DefaultTableModel(new String[]{"Id", "Category", "Name", "Price"}, 0);
        chinese_table = new JTable(chinese_tablemodel);
        chinese_table.setFont(new Font("century gothic", Font.BOLD, 20));
        chinese_table.setForeground(Color.WHITE);
        chinese_table.setBackground(new Color(35, 110, 100));
        chinese_table.setRowHeight(50);
        JScrollPane scrollpane4 = new JScrollPane(chinese_table);
        scrollpane4.setBounds(450, 50, 700, 500);
        chinese_panel.add(scrollpane4);

        //adding pasta table in panel5
        fries_tablemodel = new DefaultTableModel(new String[]{"Id", "Category", "Name", "Price"}, 0);
        fries_table = new JTable(fries_tablemodel);
        fries_table.setFont(new Font("century gothic", Font.BOLD, 20));
        fries_table.setForeground(Color.WHITE);
        fries_table.setBackground(new Color(35, 110, 100));
        fries_table.setRowHeight(50);
        JScrollPane scrollpane5 = new JScrollPane(fries_table);
        scrollpane5.setBounds(450, 50, 700, 500);
        fries_panel.add(scrollpane5);

        //adding pasta table in panel6
        snacks_quick_tablemodel = new DefaultTableModel(new String[]{"Id", "Category", "Name", "Price"}, 0);
        snacks_quick_table = new JTable(snacks_quick_tablemodel);
        snacks_quick_table.setFont(new Font("century gothic", Font.BOLD, 20));
        snacks_quick_table.setForeground(Color.WHITE);
        snacks_quick_table.setBackground(new Color(35, 110, 100));
        snacks_quick_table.setRowHeight(50);
        JScrollPane scrollpane6 = new JScrollPane(snacks_quick_table);
        scrollpane6.setBounds(450, 50, 700, 500);
        snacks_quick_panel.add(scrollpane6);

        //adding pasta table in panel7
        icecream_tablemodel = new DefaultTableModel(new String[]{"Id", "Category", "Name", "Price"}, 0);
        icecrame_table = new JTable(icecream_tablemodel);
        icecrame_table.setFont(new Font("century gothic", Font.BOLD, 20));
        icecrame_table.setForeground(Color.WHITE);
        icecrame_table.setBackground(new Color(35, 110, 100));
        icecrame_table.setRowHeight(50);
        JScrollPane scrollpane7 = new JScrollPane(icecrame_table);
        scrollpane7.setBounds(450, 50, 700, 500);
        icecream_panel.add(scrollpane7);

        //adding pasta table in panel8
        cloddrink_tablemodel = new DefaultTableModel(new String[]{"Id", "Category", "Name", "Price"}, 0);
        cloddrink_table = new JTable(cloddrink_tablemodel);
        cloddrink_table.setFont(new Font("century gothic", Font.BOLD, 20));
        cloddrink_table.setForeground(Color.WHITE);
        cloddrink_table.setBackground(new Color(35, 110, 100));
        cloddrink_table.setRowHeight(50);
        JScrollPane scrollpane8 = new JScrollPane(cloddrink_table);
        scrollpane8.setBounds(450, 50, 700, 500);
        cloddrink_panel.add(scrollpane8);

        menuframe.add(jtp);
        menuframe.setVisible(true);
        con = DBUtil.getConnection();
        loadpizza();
        loadburger();
        loadpasta();
        loadChinese();
        loadFries();
        loadSnacks_Quick_Bites();
        loadIce_Cream();
        loadCold_Drinks();
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == pizza_panel) {
            loadpizza();
        }
        if (e.getSource() == burger_panel) {
            loadburger();
        }
        if (e.getSource() == pasta_panel) {
            loadpasta();
        }
        if (e.getSource() == chinese_panel) {
            loadChinese();
        }
        if (e.getSource() == fries_panel) {
            loadFries();
        }
        if (e.getSource() == snacks_quick_panel) {
            loadSnacks_Quick_Bites();
        }
        if (e.getSource() == icecream_panel) {
            loadIce_Cream();
        }
        if (e.getSource() == cloddrink_panel) {
            loadCold_Drinks();
        }
    }

    //for adding pizza details
    private void loadpizza() {
        try {
            pizza_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from food_details where food_category='pizza'");
            rs = pst.executeQuery();
            while (rs.next()) {
                pizza_tablemodel.addRow(new Object[]{rs.getString("id"),rs.getString("food_category"), rs.getString("food_name"), rs.getString("food_price")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    //for adding burger details
    private void loadburger() {
        try {
            burger_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from food_details where food_category='Burger'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                burger_tablemodel.addRow(new Object[]{rs.getInt("id"), rs.getString("food_category"), rs.getString("food_name"), rs.getString("food_price")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    //for adding pasta details
    private void loadpasta() {
        try {
            pasta_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from food_details where food_category='Pasta'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pasta_tablemodel.addRow(new Object[]{rs.getInt("id"), rs.getString("food_category"), rs.getString("food_name"), rs.getString("food_price")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    //for adding Chinese details
    private void loadChinese() {
        try {
            chinese_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from food_details where food_category='Chinese'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                chinese_tablemodel.addRow(new Object[]{rs.getInt("id"), rs.getString("food_category"), rs.getString("food_name"), rs.getString("food_price")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    //for adding Fries details
    private void loadFries() {
        try {
            fries_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from food_details where food_category='Fries'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                fries_tablemodel.addRow(new Object[]{rs.getInt("id"), rs.getString("food_category"), rs.getString("food_name"), rs.getString("food_price")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    //for adding Snacks & Quick Bites details
    private void loadSnacks_Quick_Bites() {
        try {
            snacks_quick_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from food_details where food_category='Snacks & Quick Bites'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                snacks_quick_tablemodel.addRow(new Object[]{rs.getInt("id"), rs.getString("food_category"), rs.getString("food_name"), rs.getString("food_price")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    //for adding Ice-Cream details
    private void loadIce_Cream() {
        try {
            icecream_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from food_details where food_category='Ice-Cream'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                icecream_tablemodel.addRow(new Object[]{rs.getInt("id"), rs.getString("food_category"), rs.getString("food_name"), rs.getString("food_price")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    //for adding Cold-Drinks details
    private void loadCold_Drinks() {
        try {
            cloddrink_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from food_details where food_category='Cold-Drinks'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cloddrink_tablemodel.addRow(new Object[]{rs.getInt("id"), rs.getString("food_category"), rs.getString("food_name"), rs.getString("food_price")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    public static void main(String args[]) throws SQLException {
        new Menu();
    }
}
