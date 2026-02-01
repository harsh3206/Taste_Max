package Frames;

import Connection.DBUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public class Delete_Employee implements ActionListener {

    JFrame employeeframe;
    JPanel employeepanel;
    JLabel id, namelbl, agelbl, salarylbl, doblbl, genderlbl;
    JTextField name_txt, age_txt, salary_txt, dob_txt, gender_txt;
    JComboBox employeeid_combobox;
    JButton deletebtn, backbtn, searchbtn;
    Connection con;
    ResultSet rs;
    PreparedStatement pst;
    JTable employee_table;
    DefaultTableModel employee_tablemodel;
    
    Delete_Employee() throws SQLException {
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
        employeeframe = new JFrame("DELETE EMPLOYEE");
        employeeframe.setSize(1200, 800);
        employeeframe.setLayout(null);
        employeeframe.setLocationRelativeTo(null);
        employeeframe.setResizable(false);
        employeeframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Main Panel
        employeepanel = new JPanel();
        employeepanel.setLayout(null);
        employeepanel.setBounds(0, 0, 1200, 800);
        employeepanel.setBackground(new Color(200, 200, 10));
        employeeframe.add(employeepanel);

        //all label
        id = new JLabel("ID");
        id.setFont(new Font("century gothic", Font.BOLD, 20));
        id.setBounds(10, 60, 150, 30);
        employeepanel.add(id);
        namelbl = new JLabel("Name ");
        namelbl.setFont(new Font("century gothic", Font.BOLD, 20));
        namelbl.setBounds(10, 110, 150, 30);
        employeepanel.add(namelbl);
        agelbl = new JLabel("Age ");
        agelbl.setFont(new Font("century gothic", Font.BOLD, 20));
        agelbl.setBounds(10, 160, 150, 30);
        employeepanel.add(agelbl);
        salarylbl = new JLabel("Salary");
        salarylbl.setFont(new Font("century gothic", Font.BOLD, 20));
        salarylbl.setBounds(10, 210, 150, 30);
        employeepanel.add(salarylbl);
        doblbl = new JLabel("DOB");
        doblbl.setFont(new Font("century gothic", Font.BOLD, 20));
        doblbl.setBounds(10, 260, 150, 30);
        employeepanel.add(doblbl);
        genderlbl = new JLabel("Gender");
        genderlbl.setFont(new Font("century gothic", Font.BOLD, 20));
        genderlbl.setBounds(10, 310, 150, 30);
        employeepanel.add(genderlbl);

        //text-fields & radio-button
        employeeid_combobox = new JComboBox();
        employeeid_combobox.setFont(new Font("century gothic", Font.BOLD, 15));
        employeeid_combobox.setBounds(200, 60, 200, 30);
        employeepanel.add(employeeid_combobox);
        name_txt = new JTextField();
        name_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        name_txt.setBounds(200, 110, 200, 30);
        name_txt.setEditable(false);
        employeepanel.add(name_txt);
        age_txt = new JTextField();
        age_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        age_txt.setBounds(200, 160, 200, 30);
        age_txt.setEditable(false);
        employeepanel.add(age_txt);
        salary_txt = new JTextField();
        salary_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        salary_txt.setBounds(200, 210, 200, 30);
        salary_txt.setEditable(false);
        employeepanel.add(salary_txt);
        dob_txt = new JTextField();
        dob_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        dob_txt.setBounds(200, 260, 200, 30);
        dob_txt.setEditable(false);
        employeepanel.add(dob_txt);
        gender_txt = new JTextField();
        gender_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        gender_txt.setBounds(200, 310, 200, 30);
        gender_txt.setEditable(false);
        employeepanel.add(gender_txt);

        //Buttons
        searchbtn = new JButton("Search");
        searchbtn.setFont(new Font("century gothic", Font.BOLD, 20));
        searchbtn.setBounds(130, 420, 200, 50);
        employeepanel.add(searchbtn);
        searchbtn.addActionListener(this);
        deletebtn = new JButton("Delete");
        deletebtn.setFont(new Font("century gothic", Font.BOLD, 20));
        deletebtn.setBounds(130, 490, 200, 50);
        employeepanel.add(deletebtn);
        deletebtn.addActionListener(this);
        backbtn = new JButton("Back");
        backbtn.setFont(new Font("century gothic", Font.BOLD, 20));
        backbtn.setBounds(130, 560, 200, 50);
        employeepanel.add(backbtn);
        backbtn.addActionListener(this);

        //table
        employee_tablemodel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Salary", "Dob", "Gender"}, 0);
        employee_table = new JTable(employee_tablemodel);
        employee_table.setFont(new Font("century gothic", Font.BOLD, 20));
        employee_table.setForeground(Color.WHITE);
        employee_table.setBackground(new Color(35, 110, 100));
        employee_table.setRowHeight(50);
        JScrollPane scrollpane = new JScrollPane(employee_table);
        scrollpane.setBounds(450, 50, 700, 500);
        employeepanel.add(scrollpane);

        employeeframe.setVisible(true);
        con = DBUtil.getConnection();
        loadtable();
        loaddata();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deletebtn) {
            try {
                String id = employeeid_combobox.getSelectedItem().toString();
                pst = con.prepareStatement("delete from emp_details where id = ?");
                pst.setString(1, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "RECORD HAS BEEN DELETED SUCCESSFULLY");
                name_txt.setText("");
                age_txt.setText("");
                salary_txt.setText("");
                dob_txt.setText("");
                gender_txt.setText("");
              //  txt6.setText("");

                loaddata();
                loadtable();
            } catch (SQLException ex) {
                Logger.getLogger(Delete_Food.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == searchbtn) {
            try {
                String id = employeeid_combobox.getSelectedItem().toString();
                pst = con.prepareStatement("select * from emp_details where id = ?");
                pst.setString(1, id);
                rs = pst.executeQuery();
                if (rs.next() == true) {
                    name_txt.setText(rs.getString(2));
                    age_txt.setText(rs.getString(3));
                    salary_txt.setText(rs.getString(4));
                    dob_txt.setText(rs.getString(5));
                    gender_txt.setText(rs.getString(6));
                    //txt6.setText(rs.getString(7));
                } else {
                    JOptionPane.showMessageDialog(null, "No Record Found!!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Delete_Food.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == backbtn) {
            DashBoard db = new DashBoard();
            db.dashboard_frame.setVisible(true);
            employeeframe.dispose();
        }
    }

    //load employee id from database
    public void loaddata() {
        try {
            pst = con.prepareStatement("select id from emp_details");
            rs = pst.executeQuery();
            employeeid_combobox.removeAllItems();
            while (rs.next()) {
                employeeid_combobox.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Delete_Food.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //load employee details from database
    private void loadtable() {
        try {
            employee_tablemodel.setRowCount(0);
            pst = con.prepareStatement("select * from emp_details");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                employee_tablemodel.addRow(new Object[]{rs.getInt("id"), rs.getString("emp_name"), rs.getString("emp_age"), rs.getString("emp_salary"), rs.getString("emp_dob"), rs.getString("emp_gender")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    public static void main(String args[]) throws SQLException {
        new Delete_Employee();
    }
}
