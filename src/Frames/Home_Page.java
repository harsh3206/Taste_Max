package Frames;

import Connection.DBUtil;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.awt.event.*;

public class Home_Page implements ActionListener {

    JFrame homepage_frame;
    JPanel home_panel;
    JMenuBar mb;
    JMenu food_menu, order;
    JMenuItem menu, create_order, remove_order;
    JButton logout;
    JLabel l;

    Home_Page() {
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
        homepage_frame = new JFrame("Taste-Max Restuarnt Management System");
        homepage_frame.setSize(1200, 800);
        homepage_frame.setLayout(null);
        homepage_frame.setLocationRelativeTo(null);
        homepage_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homepage_frame.setVisible(true);

        //fream add in panel
        home_panel = new JPanel();
        home_panel.setLayout(null);
        home_panel.setBounds(0, 0, 1200, 800);
        homepage_frame.add(home_panel);

        //menubar code
        mb = new JMenuBar();

        food_menu = new JMenu("Food Menu");
        food_menu.setFont(new Font("century gothic", Font.BOLD, 20));
        order = new JMenu("Order");
        order.setFont(new Font("century gothic", Font.BOLD, 20));

        //menu-items in menu
        menu = new JMenuItem("Open Menu");
        menu.addActionListener(this);
        menu.setFont(new Font("century gothic", Font.BOLD, 15));
        create_order = new JMenuItem("Create Order");
        create_order.addActionListener(this);
        create_order.setFont(new Font("century gothic", Font.BOLD, 15));
        remove_order = new JMenuItem("Remove Order");
        remove_order.addActionListener(this);
        remove_order.setFont(new Font("century gothic", Font.BOLD, 15));

        //add menu-item in menu
        food_menu.add(menu);

        order.add(create_order);
        order.add(remove_order);

        //add menu in menubar
        mb.add(food_menu);
        mb.add(order);

        //add menubar in frame
        homepage_frame.add(mb);
        homepage_frame.setJMenuBar(mb);

        //Logout Button
        logout = new JButton("LOGOUT");
        logout.setFont(new Font("century gothic", Font.BOLD, 20));
        logout.setForeground(Color.decode("#4F7C7D"));
        logout.setBackground(Color.decode("#F4CE85"));
        logout.setBounds(1050, 650, 110, 30);
        logout.addActionListener(this);
        home_panel.add(logout);

        // image code
        l = new JLabel("");
        l.setBounds(0, 0, 1200, 800);
        l.setIcon(new ImageIcon(getClass().getResource("/images/b8.jpg")));
        home_panel.add(l);

        homepage_frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu) {
            try {
                Menu m = new Menu();
                m.menuframe.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == create_order) {
            try {
                Order o = new Order();
                homepage_frame.dispose();
                o.orderframe.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == remove_order) {
            try {
                RemoveOrder r = new RemoveOrder();
                homepage_frame.dispose();
                r.removeframe.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == logout) {
            try {
                homepage_frame.dispose();
                Login_File first = new Login_File();
                first.loginframe.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        new Home_Page();
    }
}
