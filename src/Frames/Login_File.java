package Frames;

import Connection.DBUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.*;
import java.sql.SQLException;

class Login_File implements ActionListener {

    JFrame loginframe;
    JPanel image_panel, login_panel;
    JLabel imagelabel, username, password;
    JTextField username_txt;
    JPasswordField passwordfield;
    JButton login_button;
    Connection con;
    ResultSet rs;
    PreparedStatement pst;
    Statement stmt;

    Login_File() throws SQLException {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }

        loginframe = new JFrame("Login Panel");
        loginframe.setSize(1200, 800);
        loginframe.setLayout(null);
        loginframe.setLocationRelativeTo(null);
        loginframe.setResizable(false);
        loginframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //adding panel1 
        image_panel = new JPanel();
        image_panel.setLayout(null);
        image_panel.setBounds(0, 0, 600, 800);
        imagelabel = new JLabel();
        imagelabel.setIcon(new ImageIcon(getClass().getResource("/images/burger.jpg")));
        imagelabel.setBounds(0, 0, 600, 800);
        image_panel.add(imagelabel);
        //adding panel2
        login_panel = new JPanel();
        login_panel.setLayout(null);
        login_panel.setBounds(601, 0, 585, 763);
        login_panel.setBackground(new Color(244, 164, 96));
        login_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        loginframe.add(image_panel);
        loginframe.add(login_panel);

        //adding username(label) & password(label) to panel2
        username = new JLabel("USERNAME");
        username.setBounds(150, 200, 200, 30);
        username.setFont(new Font("broadway", Font.BOLD, 25));
        username.setForeground(Color.WHITE);
        login_panel.add(username);
        password = new JLabel("PASSWORD");
        password.setBounds(150, 330, 200, 30);
        password.setFont(new Font("broadway", Font.BOLD, 25));
        password.setForeground(Color.WHITE);
        login_panel.add(password);

        //adding textfield in username
        username_txt = new JTextField();
        username_txt.setBounds(150, 240, 300, 70);
        username_txt.setFont(new Font("Serif", Font.BOLD, 25));
        login_panel.add(username_txt);
        //adding passwordfield in password 
        passwordfield = new JPasswordField();
        passwordfield.setBounds(150, 370, 300, 70);
        passwordfield.setFont(new Font("Serif", Font.BOLD, 25));
        login_panel.add(passwordfield);

        //add login button in jframe
        login_button = new JButton("LOGIN");
        login_button.setBounds(200, 500, 200, 60);
        login_button.setFont(new Font("broadway", Font.BOLD, 25));
        login_button.addActionListener(this);
        login_panel.add(login_button);

        loginframe.setVisible(true);
        con = DBUtil.getConnection();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login_button) {
            String name = username_txt.getText();
            String passwordtext = new String(passwordfield.getPassword());

            if (name.isEmpty() || passwordtext.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username and Password cannot be empty");
            }
            try {
                String query = "select user_role from login where user_name = ? and password = ?";
                pst = con.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, passwordtext);

                // Execute the query
                rs = pst.executeQuery();

                if (rs.next()) {
                    // If credentials are correct, show success message and navigate to dashboard
                    JOptionPane.showMessageDialog(loginframe, "Login Successful!", "Login", JOptionPane.INFORMATION_MESSAGE);
                    loginframe.dispose();

                    String role = rs.getString("user_role");

                    switch (role.toLowerCase()) {
                        case "admin":
                            DashBoard dash = new DashBoard();
                            dash.dashboard_frame.setVisible(true);
                            break;
                        case "employee":
                            Home_Page homepage = new Home_Page();
                            homepage.homepage_frame.setVisible(true);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Unknown User Role");
                    }
                    /* // Navigate to the appropriate page based on user role
                if (role.equals("admin")) {
                    DashBoard dash = new DashBoard();
                    dash.dashboard_frame.setVisible(true);
                } else if (role.equals("employee")) {
                    Home_Page homepage = new Home_Page();
                    homepage.homepage_frame.setVisible(true);
                }*/
                } else {
                    // Invalid credentials
                    JOptionPane.showMessageDialog(loginframe, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(loginframe, "A DataBase error occurred while login.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String args[]) throws SQLException {
        // TODO code application logic here
        new Login_File();
    }
}
