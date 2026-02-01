package Frames;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.SQLException;

public class DashBoard implements ActionListener {

    JFrame dashboard_frame;
    JPanel titlepanel, menupanel;
    JLabel imagelabel, title, foodlabel, emplabel, payment;
    JButton addfood, deletefood, updatefood, addemp, deleteemp, billhistory, logout;
    ImageIcon addfood_icn, deletefood_icn, updatefood_icn, addemployee_icn, deleteemployee_icn, icn6;

    DashBoard() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {

        }
        //adding frame
        dashboard_frame = new JFrame("DashBoard");
        dashboard_frame.setLayout(null);
        dashboard_frame.setSize(1200, 800);
        dashboard_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dashboard_frame.setLocationRelativeTo(null);
        dashboard_frame.setResizable(false);

        //adding menu panel at left of frame
        menupanel = new JPanel();
        menupanel.setLayout(null);
        menupanel.setBounds(0, 60, 1200, 800);
        dashboard_frame.add(menupanel);

        //adding title panel to JFrame
        titlepanel = new JPanel();
        titlepanel.setLayout(null);
        titlepanel.setBounds(0, 0, 1540, 60);
        titlepanel.setBackground(Color.BLACK);

        //all label code
        title = new JLabel("Taste-Max Restaurant", JLabel.CENTER);
        title.setFont(new Font("century gothic", Font.BOLD, 50));
        title.setForeground(Color.ORANGE);
        title.setBounds(0, 0, 1200, 50);
        titlepanel.add(title);
        dashboard_frame.add(titlepanel);
        foodlabel = new JLabel("Food Details");
        foodlabel.setFont(new Font("century gothic", Font.BOLD, 30));
        foodlabel.setBounds(0, 0, 200, 50);
        menupanel.add(foodlabel);
        emplabel = new JLabel("Employee Details");
        emplabel.setFont(new Font("century gothic", Font.BOLD, 30));
        emplabel.setBounds(0, 150, 280, 50);
        menupanel.add(emplabel);
      /*  payment = new JLabel("Payment Details");
        payment.setFont(new Font("century gothic", Font.BOLD, 30));
        payment.setBounds(0, 320, 280, 50);
        payment.setBackground(Color.YELLOW);
        menupanel.add(payment);
        */
        //all button code
        addfood_icn = new ImageIcon(getClass().getResource("/icons/add.png"));
        addfood = new JButton("Add Food Item", addfood_icn);
        addfood.setFont(new Font("century gothic", Font.BOLD, 20));
        addfood.setBounds(50, 60, 250, 50);
        addfood.setBackground(Color.YELLOW);
        addfood.addActionListener(this);
        menupanel.add(addfood);
        deletefood_icn = new ImageIcon(getClass().getResource("/icons/delete.png"));
        deletefood = new JButton("Delete Food Item", deletefood_icn);
        deletefood.setFont(new Font("century gothic", Font.BOLD, 20));
        deletefood.setBackground(Color.YELLOW);
        deletefood.setBounds(400, 60, 250, 50);
        deletefood.addActionListener(this);
        menupanel.add(deletefood);
        updatefood_icn = new ImageIcon(getClass().getResource("/icons/update.png"));
        updatefood = new JButton("Update Food Item", updatefood_icn);
        updatefood.setFont(new Font("century gothic", Font.BOLD, 18));
        updatefood.setBackground(Color.YELLOW);
        updatefood.setBounds(720, 60, 230, 50);
        updatefood.addActionListener(this);
        menupanel.add(updatefood);
        addemployee_icn = new ImageIcon(getClass().getResource("/icons/add.png"));
        addemp = new JButton("Add Employee", addemployee_icn);
        addemp.setFont(new Font("century gothic", Font.BOLD, 20));
        addemp.setBounds(50, 220, 250, 50);
        addemp.setBackground(Color.getHSBColor((float) 2.0, (float) 2.0, (float) 2.0));
        addemp.addActionListener(this);
        menupanel.add(addemp);
        deleteemployee_icn = new ImageIcon(getClass().getResource("/icons/delete.png"));
        deleteemp = new JButton("Delete Employee", deleteemployee_icn);
        deleteemp.setFont(new Font("century gothic", Font.BOLD, 20));
        deleteemp.setBounds(400, 220, 250, 50);
        deleteemp.setBackground(Color.getHSBColor((float) 1.1, (float) 3.2, (float) 2.6));
        menupanel.add(deleteemp);
        deleteemp.addActionListener(this);

       /* //adding payment history button in menupanel
        icn6 = new ImageIcon(getClass().getResource("/icons/bill.png"));
        billhistory = new JButton("Bill History", icn6);
        billhistory.setFont(new Font("century gothic", Font.BOLD, 20));
        billhistory.setBackground(Color.YELLOW);
        billhistory.setBounds(50, 390, 250, 50);
        menupanel.add(billhistory);
        */
        //adding logout button in menupanel
        logout = new JButton("LOGOUT");
        logout.setFont(new Font("century gothic", Font.BOLD, 15));
        logout.setForeground(Color.WHITE);
        logout.setBackground(Color.red);
        logout.setBounds(1000, 600, 100, 30);
        menupanel.add(logout);
        logout.addActionListener(this);

        //adding image in background
        imagelabel = new JLabel("");
        imagelabel.setSize(1200, 800);
        imagelabel.setIcon(new ImageIcon(getClass().getResource("/images/fries2.jpeg")));
        menupanel.add(imagelabel);

        dashboard_frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addemp) {
            try {
                dashboard_frame.dispose();
                Add_Employee addemp = new Add_Employee();
                addemp.empframe.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == deleteemp) {
            try {
                dashboard_frame.dispose();
                Delete_Employee deleteemp = new Delete_Employee();
                deleteemp.employeeframe.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == addfood) {
            try {
                dashboard_frame.dispose();
                Add_FoodItem addfood = new Add_FoodItem();
                addfood.addfood_frame.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == deletefood) {
            try {
                dashboard_frame.dispose();
                Delete_Food delfood = new Delete_Food();
                delfood.deleteframe.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == updatefood) {
            try {
                dashboard_frame.dispose();
                Update_Food updfood = new Update_Food();
                updfood.updatefood.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == logout) {
            try {
                dashboard_frame.dispose();
                Login_File first = new Login_File();
                first.loginframe.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        new DashBoard();
    }
}
