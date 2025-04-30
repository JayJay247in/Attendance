import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TeacherView {
    public void tcView(int id) {
        JFrame frame = new JFrame();
        Font btnFont = new Font("Times New Roman", Font.BOLD, 20);

        // Close Button
        JLabel closeLabel = new JLabel("X");
        closeLabel.setForeground(Color.decode("#37474F"));
        closeLabel.setBounds(965, 10, 100, 20);
        closeLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        frame.add(closeLabel);
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        // Minimize Button
        JLabel minimizeLabel = new JLabel("_");
        minimizeLabel.setForeground(Color.decode("#37474F"));
        minimizeLabel.setBounds(935, 0, 100, 20);
        frame.add(minimizeLabel);
        minimizeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }
        });

        // Top Panel
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1000, 35);
        panel.setBackground(Color.decode("#DEE4E7"));
        frame.add(panel);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome " + getUser(id) + ",");
        welcomeLabel.setForeground(Color.decode("#DEE4E7"));
        welcomeLabel.setBounds(10, 50, 250, 20);
        welcomeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frame.add(welcomeLabel);

        // Add Attendance Button
        JButton addAttendanceButton = new JButton("ADD ATTENDANCE");
        addAttendanceButton.setBounds(150, 200, 650, 60);
        addAttendanceButton.setFont(btnFont);
        addAttendanceButton.setBackground(Color.decode("#DEE4E7"));
        addAttendanceButton.setForeground(Color.decode("#37474F"));
        frame.add(addAttendanceButton);
        addAttendanceButton.addActionListener((ActionEvent e) -> {
            AddAttendance addAttendance = new AddAttendance();
            try {
                addAttendance.addView();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Edit Attendance Button
        JButton editAttendanceButton = new JButton("EDIT ATTENDANCE");
        editAttendanceButton.setBounds(150, 350, 650, 60);
        editAttendanceButton.setFont(btnFont);
        editAttendanceButton.setBackground(Color.decode("#DEE4E7"));
        editAttendanceButton.setForeground(Color.decode("#37474F"));
        frame.add(editAttendanceButton);
        editAttendanceButton.addActionListener((ActionEvent e) -> {
            EditAttendance editAttendance = new EditAttendance();
            try {
                editAttendance.editView();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Frame Settings
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.getContentPane().setBackground(Color.decode("#37474F"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String getUser(int id) {
        String url = "jdbc:mysql://localhost:3306/attendance";
        String user = "root";
        String pass = "Divinity:100";

        String userName = "Unknown";
        try (Connection con = DriverManager.getConnection(url, user, pass);
             Statement stm = con.createStatement();
             ResultSet rst = stm.executeQuery("SELECT name FROM user WHERE id = " + id)) {

            if (rst.next()) {
                userName = rst.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userName;
    }
}
