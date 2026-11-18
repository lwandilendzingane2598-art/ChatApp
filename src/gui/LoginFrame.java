package gui;

import dao.UserDAO;
import models.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("AccessoryShop Portal - Secure Login");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));
        mainPanel.setBackground(new Color(240, 243, 246));

        JLabel titleLabel = new JLabel("🛍️ AccessoryShop Secure Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(15, 23, 42));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 6, 6));
        formPanel.setBackground(new Color(240, 243, 246));
        formPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        formPanel.add(new JLabel("Username:"));
        userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(userField);

        formPanel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(passField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        btnPanel.setBackground(new Color(240, 243, 246));

        // Royal Blue button with Black text (No hover effects)
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(37, 99, 235)); 
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setRolloverEnabled(false);

        // Vibrant Orange button with Black text (No hover effects)
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(234, 88, 12)); 
        registerBtn.setForeground(Color.BLACK);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerBtn.setFocusPainted(false);
        registerBtn.setRolloverEnabled(false);

        btnPanel.add(loginBtn);
        btnPanel.add(registerBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                User authenticatedUser = UserDAO.loginUser(username, password);
                if (authenticatedUser != null) {
                    new MainWebPortalFrame(authenticatedUser).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid Username or Password!", "Access Denied", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}