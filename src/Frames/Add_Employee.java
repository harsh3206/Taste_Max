package Frames;

import Connection.DBUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class Add_Employee implements ActionListener {

    JFrame empframe;
    JPanel emp_panel;
    JLabel emplbl, namelbl, agelbl, salarylbl, doblbl, genderlbl;
    JButton addbtn, backbtn;
    JRadioButton male_radiobutton, female_radiobutton;
    ButtonGroup jbg;
    JTextField name_txt, age_txt, salary_txt, dob_txt;
    Connection con;
    ResultSet rs;
    PreparedStatement pst;
    DefaultTableModel emp_tableodel;
    JTable emp_table;

    Add_Employee() throws SQLException {
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
        empframe = new JFrame("ADD EMPLOYEE");
        empframe.setSize(1200, 800);
        empframe.setLayout(null);
        empframe.setLocationRelativeTo(null);
        empframe.setResizable(false);
        empframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //adding panel to JFrame
        emp_panel = new JPanel();
        emp_panel.setLayout(null);
        emp_panel.setBounds(0, 0, 1200, 800);
        emp_panel.setBackground(new Color(250, 200, 10));
        empframe.add(emp_panel);

        //all label
        emplbl = new JLabel("Add Employee Detail ");
        emplbl.setFont(new Font("century gothic", Font.BOLD, 20));
        emplbl.setBounds(120, 10, 250, 30);
        emp_panel.add(emplbl);
        namelbl = new JLabel("Name ");
        namelbl.setFont(new Font("century gothic", Font.BOLD, 20));
        namelbl.setBounds(10, 60, 150, 30);
        agelbl = new JLabel("Age ");
        agelbl.setFont(new Font("century gothic", Font.BOLD, 20));
        agelbl.setBounds(10, 110, 150, 30);
        emp_panel.add(agelbl);
        salarylbl = new JLabel("Salary");
        salarylbl.setFont(new Font("century gothic", Font.BOLD, 20));
        salarylbl.setBounds(10, 160, 150, 30);
        emp_panel.add(salarylbl);
        doblbl = new JLabel("DOB");
        doblbl.setFont(new Font("century gothic", Font.BOLD, 20));
        doblbl.setBounds(10, 210, 150, 30);
        emp_panel.add(doblbl);
        genderlbl = new JLabel("Gender");
        genderlbl.setFont(new Font("century gothic", Font.BOLD, 20));
        genderlbl.setBounds(10, 260, 150, 30);
        emp_panel.add(genderlbl);

        //text-fields & radio-button
        emp_panel.add(namelbl);
        name_txt = new JTextField();
        name_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        name_txt.setBounds(200, 60, 200, 30);
        emp_panel.add(name_txt);
        age_txt = new JTextField();
        age_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        age_txt.setBounds(200, 110, 200, 30);
        emp_panel.add(age_txt);
        salary_txt = new JTextField();
        salary_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        salary_txt.setBounds(200, 160, 200, 30);
        emp_panel.add(salary_txt);
        dob_txt = new JTextField();
        dob_txt.setFont(new Font("century gothic", Font.BOLD, 15));
        dob_txt.setBounds(200, 210, 200, 30);
        emp_panel.add(dob_txt);
        jbg = new ButtonGroup();
        male_radiobutton = new JRadioButton("Male");
        male_radiobutton.setFont(new Font("century gothic", Font.BOLD, 20));
        male_radiobutton.setBounds(200, 260, 80, 30);
        female_radiobutton = new JRadioButton("Female");
        female_radiobutton.setFont(new Font("century gothic", Font.BOLD, 20));
        female_radiobutton.setBounds(290, 260, 110, 30);
        jbg.add(male_radiobutton);
        jbg.add(female_radiobutton);
        emp_panel.add(male_radiobutton);
        emp_panel.add(female_radiobutton);

        //Buttons
        addbtn = new JButton("ADD");
        addbtn.setFont(new Font("century gothic", Font.BOLD, 20));
        addbtn.setBounds(130, 380, 200, 50);
        emp_panel.add(addbtn);
        addbtn.addActionListener(this);
        backbtn = new JButton("Back");
        backbtn.setFont(new Font("century gothic", Font.BOLD, 20));
        backbtn.setBounds(130, 440, 200, 50);
        emp_panel.add(backbtn);
        backbtn.addActionListener(this);

        //table
        emp_tableodel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Salary", "Dob", "Gender"}, 0);
        emp_table = new JTable(emp_tableodel);
        emp_table.setFont(new Font("century gothic", Font.BOLD, 20));
        emp_table.setForeground(Color.WHITE);
        emp_table.setBackground(new Color(35, 110, 100));
        emp_table.setRowHeight(50);
        JScrollPane scrollpane = new JScrollPane(emp_table);
        scrollpane.setBounds(450, 50, 700, 500);
        emp_panel.add(scrollpane);

        empframe.setVisible(true);
        con = DBUtil.getConnection();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addbtn) {
            String name = name_txt.getText();
            String age = age_txt.getText();
            String salary = salary_txt.getText();
            String dob = dob_txt.getText();

            // Handle gender selection
            String gender = null;
            if (male_radiobutton.isSelected()) {
                gender = "Male";
            } else if (female_radiobutton.isSelected()) {
                gender = "Female";
            }

            // Validate all fields
            if (name.trim().isEmpty() || age.trim().isEmpty() || salary.trim().isEmpty() || dob.trim().isEmpty() || gender == null) {
                JOptionPane.showMessageDialog(null, "Please fill data in all fields, including gender.");
                return;
            }
            if (!name.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Name must contain only letters and spaces.");
                return;
            }
            // Validate numeric values
            int validAge;
            try {
                validAge = Integer.parseInt(age);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Age must be a valid whole number.");
                return;
            }
            int validSalary;
            try {
                validSalary = Integer.parseInt(salary);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Salary must be a valid number.");
                return;
            }

            // Validate date format (yyyy-MM-dd)
            java.sql.Date sqlDob;
            try {
                java.util.Date parsedDob = new java.text.SimpleDateFormat("dd-MM-yyyy").parse(dob);
                sqlDob = new java.sql.Date(parsedDob.getTime());
            } catch (java.text.ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please use dd-MM-yyyy or yyyy-MM-dd.");
                return;
            }

            // Insert into database
            try {
                String query = "insert into emp_details(emp_name, emp_age, emp_salary, emp_dob, emp_gender) values (?, ?, ?, ?, ?)";
                pst = con.prepareStatement(query);
                pst.setString(1, name);
                pst.setInt(2, validAge);
                pst.setFloat(3, validSalary);
                pst.setDate(4, sqlDob);
                pst.setString(5, gender);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Employee add Successfully!");
                name_txt.setText("");
                age_txt.setText("");
                salary_txt.setText("");
                dob_txt.setText("");
                male_radiobutton.setSelected(false);
                female_radiobutton.setSelected(false);

                loadtable();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
                ex.printStackTrace(); // for debugging
            }
        }

        /*String name = name_txt.getText();
        String age = age_txt.getText();
        String salary = salary_txt.getText();
        String dob = dob_txt.getText();
        String gender = null;
        if (male_radiobutton.isSelected()) {
            gender = "Male";
        }
        if (female_radiobutton.isSelected()) {
            gender = "Female";
        }
        
        if (name.trim().isEmpty() || age.trim().isEmpty() || salary.trim().isEmpty() || dob.trim().isEmpty() || gender == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return;
        }

        if (e.getSource() == addbtn) {

            try {
                String query = "insert into emp_details(emp_name,emp_age,emp_salary,emp_dob,emp_gender) values(?,?,?,?,?)";
                pst = con.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, age);
                pst.setString(3, salary);
                pst.setString(4, dob);
                pst.setString(5, gender);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data Saved Successfully!");
                name_txt.setText("");
                age_txt.setText("");
                salary_txt.setText("");
                dob_txt.setText("");

                loadtable();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Database Error : " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }*/
        if (e.getSource() == backbtn) {
            DashBoard db = new DashBoard();
            db.dashboard_frame.setVisible(true);
            empframe.dispose();
        }
    }

    //load employee details from database
    private void loadtable() {

        try {
            emp_tableodel.setRowCount(0);
            pst = con.prepareStatement("select * from emp_details");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                emp_tableodel.addRow(new Object[]{rs.getInt("id"), rs.getString("emp_name"), rs.getString("emp_age"), rs.getString("emp_salary"), rs.getString("emp_dob"), rs.getString("emp_gender")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error :" + e.getMessage());
        }
    }

    public static void main(String args[]) throws SQLException {
        new Add_Employee();
    }
}
