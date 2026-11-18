package gui;

import dao.UserDAO;
import models.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JComboBox roleCombo;

    public RegisterFrame() {
        setTitle("AccessoryShop - User Registration");
        setSize(380, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(new Color(240, 243, 246));

        JLabel titleLabel = new JLabel("📝 Create Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(15, 23, 42));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        formPanel.setBackground(new Color(240, 243, 246));

        formPanel.add(new JLabel("Username:"));
        userField = new JTextField();
        formPanel.add(userField);

        formPanel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        formPanel.add(passField);

        formPanel.add(new JLabel("Select Role:"));
        roleCombo = new JComboBox(new String[]{"Customer", "Admin"});
        formPanel.add(roleCombo);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        btnPanel.setBackground(new Color(240, 243, 246));
        btnPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JButton regButton = new JButton("Register");
        regButton.setBackground(new Color(37, 99, 235)); // Royal Blue
        regButton.setForeground(Color.BLACK);
        regButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        regButton.setFocusPainted(false);
        regButton.setRolloverEnabled(false);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(234, 88, 12)); // Vibrant Orange
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        backButton.setFocusPainted(false);
        backButton.setRolloverEnabled(false);

        btnPanel.add(regButton);
        btnPanel.add(backButton);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();
                String role = (String) roleCombo.getSelectedItem();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Please fill in all fields.");
                    return;
                }

                User newUser = new User(username, password, role);
                if (UserDAO.registerUser(newUser)) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Registration Successful! Please login.");
                    new LoginFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Registration Failed! Username may already exist.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }
}