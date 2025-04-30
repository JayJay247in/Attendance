import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Class {
    DefaultTableModel model = new DefaultTableModel();
    Connection con;
    int check;
    JButton edit;
    JButton delete;
    JButton add;

    public void classView() {
        JFrame frame = new JFrame();
        Font text = new Font("Times New Roman", Font.PLAIN, 18);
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
        JLabel back = new JLabel("<BUTTON");
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

        // ID Label
        JLabel id = new JLabel("ID:");
        id.setFont(text);
        id.setBounds(25, 150, 40, 20);
        id.setForeground(Color.decode("#DEE4E7"));
        frame.add(id);
        JTextField idbox = new JTextField();
        idbox.setBounds(60, 150, 50, 25);
        idbox.setFont(text);
        idbox.setBackground(Color.decode("#DEE4E7"));
        idbox.setForeground(Color.decode("#37474F"));
        idbox.setEditable(false);
        frame.add(idbox);

        // Name Label
        JLabel nm = new JLabel("Name:");
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

        // Save Button
        JButton save = new JButton("SAVE");
        save.setBounds(25, 500, 125, 50);
        save.setFont(btn);
        save.setBackground(Color.decode("#DEE4E7"));
        save.setForeground(Color.decode("#37474F"));
        save.setEnabled(false);
        frame.add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (check == 1) {
                        adder(Integer.parseInt(idbox.getText()), name.getText());
                    } else if (check == 2) {
                        editor(Integer.parseInt(idbox.getText()), name.getText());
                    }
                    resetFields(idbox, name, save);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Edit Button
        edit = new JButton("EDIT");
        edit.setBounds(175, 500, 125, 50);
        edit.setFont(btn);
        edit.setBackground(Color.decode("#DEE4E7"));
        edit.setForeground(Color.decode("#37474F"));
        frame.add(edit);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edit.setEnabled(false);
                save.setEnabled(true);
                check = 2;
                name.setEditable(true);
            }
        });

        // Add Button
        add = new JButton("ADD");
        add.setBounds(325, 500, 125, 50);
        add.setFont(btn);
        add.setBackground(Color.decode("#DEE4E7"));
        add.setForeground(Color.decode("#37474F"));
        frame.add(add);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add.setEnabled(false);
                delete.setEnabled(false);
                save.setEnabled(true);
                name.setEditable(true);
                check = 1;
                try {
                    idbox.setText(String.valueOf(getid()));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
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
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleter(Integer.parseInt(idbox.getText()));
                    resetFields(idbox, name, save);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Table
        JTable table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model = (DefaultTableModel) table.getModel();
        model.addColumn("ID");
        model.addColumn("NAME");
        tblupdt();
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        JScrollPane scPane = new JScrollPane(table);
        scPane.setBounds(500, 50, 480, 525);
        frame.add(scPane);

        // Table Action
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                idbox.setText(String.valueOf(table.getModel().getValueAt(row, 0)));
                name.setText(String.valueOf(table.getModel().getValueAt(row, 1)));
                edit.setEnabled(true);
                save.setEnabled(false);
                delete.setEnabled(true);
            }
        });

        // Frame
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

    public void tblupdt() {
        try (ResultSet res = dbSearch()) {
            while (res.next()) {
                model.addRow(new Object[] { res.getInt("id"), res.getString("name") });
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public int getid() throws SQLException {
        String query = "SELECT MAX(id) FROM class";
        try (Statement stm = con.createStatement(); ResultSet rst = stm.executeQuery(query)) {
            if (rst.next()) {
                return rst.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }

    public ResultSet dbSearch() throws SQLException {
        String query = "SELECT * FROM class";
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance", "root", "Divinity:100");
        Statement stm = con.createStatement();
        return stm.executeQuery(query);
    }

    public void adder(int id, String name) throws SQLException {
        String query = "INSERT INTO class (id, name) VALUES (?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.executeUpdate();
        }
    }

    public void deleter(int id) throws SQLException {
        String query = "DELETE FROM class WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }

    public void editor(int id, String name) throws SQLException {
        String query = "UPDATE class SET name = ? WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setInt(2, id);
            pst.executeUpdate();
        }
    }

    private void resetFields(JTextField idbox, JTextField name, JButton save) throws SQLException {
        idbox.setText(String.valueOf(getid()));
        edit.setEnabled(false);
        delete.setEnabled(false);
        name.setText("");
        name.setEditable(false);
        save.setEnabled(false);
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        tblupdt();
    }
}
