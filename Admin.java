import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Admin {

    DefaultTableModel model = new DefaultTableModel();
    Font text = new Font("Times New Roman", Font.PLAIN, 18);
    Connection con;
    int check;
    JButton edit;
    JButton delete;
    JButton add;

    public void adminView() throws SQLException {
        JFrame frame = new JFrame();
        Font btn = new Font("Times New Roman", Font.BOLD, 20);

        // Close Button
        JLabel x = new JLabel("X");
        x.setForeground(Color.decode("#37474F"));
        x.setBounds(965, 10, 100, 20);
        x.setFont(new Font("Times New Roman", Font.BOLD, 20));
        frame.add(x);
        x.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        // Back Button
        JLabel back = new JLabel("<BACK");
        back.setForeground(Color.decode("#37474F"));
        back.setFont(new Font("Times New Roman", Font.BOLD, 17));
        back.setBounds(18, 10, 100, 20);
        frame.add(back);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });

        // Panel
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1000, 35);
        panel.setBackground(Color.decode("#DEE4E7"));
        frame.add(panel);

        // Table
        JTable table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model = (DefaultTableModel) table.getModel();
        model.addColumn("ID");
        model.addColumn("USERNAME");
        model.addColumn("NAME");
        tblupdt();
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        JScrollPane scPane = new JScrollPane(table);
        scPane.setBounds(500, 50, 480, 525);
        frame.add(scPane);

        // ID Field
        JLabel id = new JLabel("ID:");
        id.setFont(text);
        id.setBounds(25, 60, 40, 20);
        id.setForeground(Color.decode("#DEE4E7"));
        frame.add(id);
        JTextField idbox = new JTextField();
        idbox.setBounds(60, 60, 50, 25);
        idbox.setBackground(Color.decode("#DEE4E7"));
        idbox.setFont(text);
        idbox.setForeground(Color.decode("#37474F"));
        idbox.setEditable(false);
        frame.add(idbox);

        // Username Field
        JLabel user = new JLabel("USERNAME: ");
        user.setFont(text);
        user.setBounds(25, 120, 150, 20);
        user.setForeground(Color.decode("#DEE4E7"));
        frame.add(user);
        JTextField username = new JTextField();
        username.setBounds(25, 160, 400, 35);
        username.setBackground(Color.decode("#DEE4E7"));
        username.setFont(text);
        username.setForeground(Color.decode("#37474F"));
        username.setEditable(false);
        frame.add(username);

        // Name Field
        JLabel nm = new JLabel("NAME: ");
        nm.setFont(text);
        nm.setBounds(25, 240, 150, 20);
        nm.setForeground(Color.decode("#DEE4E7"));
        frame.add(nm);
        JTextField name = new JTextField();
        name.setBounds(25, 270, 400, 35);
        name.setBackground(Color.decode("#DEE4E7"));
        name.setFont(text);
        name.setForeground(Color.decode("#37474F"));
        name.setEditable(false);
        frame.add(name);

        // Password Field
        JLabel pass = new JLabel("PASSWORD: ");
        pass.setFont(text);
        pass.setBounds(25, 350, 150, 20);
        pass.setForeground(Color.decode("#DEE4E7"));
        frame.add(pass);
        JTextField password = new JTextField();
        password.setBounds(25, 380, 400, 35);
        password.setBackground(Color.decode("#DEE4E7"));
        password.setFont(text);
        password.setForeground(Color.decode("#37474F"));
        password.setEditable(false);
        frame.add(password);

        // Save Button
        JButton save = new JButton("SAVE");
        save.setBounds(25, 500, 125, 50);
        save.setFont(btn);
        save.setBackground(Color.decode("#DEE4E7"));
        save.setForeground(Color.decode("#37474F"));
        save.setEnabled(false);
        frame.add(save);
        save.addActionListener(e -> {
            try {
                if (check == 1) {
                    adder(Integer.parseInt(idbox.getText()), username.getText(), name.getText(), password.getText());
                } else if (check == 2) {
                    if (password.getText().isEmpty()) {
                        editor(Integer.parseInt(idbox.getText()), username.getText(), name.getText());
                    } else {
                        editor(Integer.parseInt(idbox.getText()), username.getText(), name.getText(), password.getText());
                    }
                }
                resetFields(idbox, username, name, password);
                tblupdt();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Edit Button
        edit = new JButton("EDIT");
        edit.setBounds(175, 500, 125, 50);
        edit.setFont(btn);
        edit.setEnabled(false);
        edit.setBackground(Color.decode("#DEE4E7"));
        edit.setForeground(Color.decode("#37474F"));
        frame.add(edit);
        edit.addActionListener(e -> {
            edit.setEnabled(false);
            save.setEnabled(true);
            check = 2;
            username.setEditable(true);
            name.setEditable(true);
            password.setEditable(true);
        });

        // Add Button
        add = new JButton("ADD");
        add.setBounds(325, 500, 125, 50);
        add.setFont(btn);
        add.setBackground(Color.decode("#DEE4E7"));
        add.setForeground(Color.decode("#37474F"));
        frame.add(add);
        add.addActionListener(e -> {
            add.setEnabled(false);
            save.setEnabled(true);
            delete.setEnabled(false);
            username.setEditable(true);
            name.setEditable(true);
            password.setEditable(true);
            check = 1;
            try {
                idbox.setText(String.valueOf(getid()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Delete Button
        delete = new JButton("DELETE");
        delete.setBounds(175, 432, 125, 50);
        delete.setFont(btn);
        delete.setBackground(Color.decode("#DEE4E7"));
        delete.setForeground(Color.decode("#37474F"));
        delete.setEnabled(false);
        frame.add(delete);
        delete.addActionListener(e -> {
            try {
                deleter(Integer.parseInt(idbox.getText()));
                resetFields(idbox, username, name, password);
                tblupdt();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Table Action
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                idbox.setText(String.valueOf(table.getModel().getValueAt(row, 0)));
                username.setText(String.valueOf(table.getModel().getValueAt(row, 1)));
                name.setText(String.valueOf(table.getModel().getValueAt(row, 2)));
                edit.setEnabled(true);
                delete.setEnabled(true);
                save.setEnabled(false);
                username.setEditable(false);
                name.setEditable(false);
                password.setEditable(false);
            }
        });

        // Frame Settings
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.decode("#37474F"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void resetFields(JTextField idbox, JTextField username, JTextField name, JTextField password) throws SQLException {
        idbox.setText(String.valueOf(getid()));
        username.setText("");
        name.setText("");
        password.setText("");
        edit.setEnabled(false);
        delete.setEnabled(false);
        add.setEnabled(true);
    }

    public void tblupdt() {
        try (ResultSet res = dbSearch()) {
            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }
            while (res.next()) {
                model.addRow(new Object[]{res.getInt("id"), res.getString("username"), res.getString("name")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet dbSearch() throws SQLException {
        String query = "SELECT * FROM user WHERE prio = 1";
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance", "root", "Divinity:100");
        PreparedStatement pst = con.prepareStatement(query);
        return pst.executeQuery();
    }

    public int getid() throws SQLException {
        String query = "SELECT MAX(id) AS max_id FROM user";
        try (PreparedStatement pst = con.prepareStatement(query);
             ResultSet rst = pst.executeQuery()) {
            if (rst.next()) {
                return rst.getInt("max_id") + 1;
            }
        }
        return 1;
    }

    public void adder(int id, String username, String name, String password) throws SQLException {
        String query = "INSERT INTO user (id, username, name, password, prio) VALUES (?, ?, ?, ?, 1)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.setString(2, username);
            pst.setString(3, name);
            pst.setString(4, password);
            pst.executeUpdate();
        }
    }

    public void deleter(int id) throws SQLException {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }

    public void editor(int id, String username, String name) throws SQLException {
        String query = "UPDATE user SET username = ?, name = ? WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            pst.setString(2, name);
            pst.setInt(3, id);
            pst.executeUpdate();
        }
    }

    public void editor(int id, String username, String name, String password) throws SQLException {
        String query = "UPDATE user SET username = ?, name = ?, password = ? WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            pst.setString(2, name);
            pst.setString(3, password);
            pst.setInt(4, id);
            pst.executeUpdate();
        }
    }
}
