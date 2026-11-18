package gui;

import models.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class MainWebPortalFrame extends JFrame {
    private User currentUser;

    public MainWebPortalFrame(final User currentUser) {
        this.currentUser = currentUser;
        setTitle("Luxe Accessories Store — Welcome " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel headerBanner = new JPanel(new BorderLayout());
        headerBanner.setBackground(new Color(15, 23, 42));
        headerBanner.setBorder(new EmptyBorder(12, 20, 12, 20));

        JLabel brandLabel = new JLabel("LUXE ACCESSORIES & TECH GEAR");
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        brandLabel.setForeground(Color.WHITE);

        JLabel userRoleLabel = new JLabel("Account: " + currentUser.getUsername() + " [" + currentUser.getRole() + "]  ");
        userRoleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        userRoleLabel.setForeground(new Color(203, 213, 225));

        headerBanner.add(brandLabel, BorderLayout.WEST);
        headerBanner.add(userRoleLabel, BorderLayout.EAST);
        add(headerBanner, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // TAB 1: ABOUT US (With Logo Slot `img/logo.jpg`)
        JPanel aboutPanel = new JPanel(new BorderLayout(15, 15));
        aboutPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        aboutPanel.setBackground(Color.WHITE);

        JPanel logoContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoContainer.setBackground(Color.WHITE);
        
        JLabel logoLabel = new JLabel();
        File logoFile = new File("img/logo.jpg");
        if (logoFile.exists()) {
            ImageIcon icon = new ImageIcon("img/logo.jpg");
            Image img = icon.getImage().getScaledInstance(140, 80, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(img));
        } else {
            logoLabel.setText(" [ Insert Logo: img/logo.jpg ] ");
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            logoLabel.setForeground(new Color(37, 99, 235));
            logoLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(191, 219, 254), 2),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
            ));
        }
        logoContainer.add(logoLabel);

        JTextArea aboutText = new JTextArea(
            "Welcome to Luxe Accessories — Your premier destination for premium tech gear, leather wallets, smartwatches, and designer jewelry.\n\n" +
            "Established with a passion for quality and elegance, we curate top-tier accessories designed to elevate your everyday lifestyle. " +
            "Whether you're shopping for high-end fashion statements or durable daily protection accessories, we guarantee supreme craftsmanship.\n\n" +
            "• Direct Customer Support via Live Socket Chat\n" +
            "• Secure User Authentication & Order Tracking"
        );
        aboutText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        aboutText.setEditable(false);
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);
        aboutText.setBackground(new Color(248, 250, 252));
        aboutText.setBorder(new EmptyBorder(15, 15, 15, 15));

        aboutPanel.add(logoContainer, BorderLayout.NORTH);
        aboutPanel.add(new JScrollPane(aboutText), BorderLayout.CENTER);

        // TAB 2: SERVICES / CATALOG (With `img/c.jpg` Placeholders)
        JPanel servicesPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        servicesPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        servicesPanel.setBackground(Color.WHITE);

        servicesPanel.add(createProductCard("Smart Watch Series X", "E1499.00", "Advanced fitness tracking & AMOLED display.", "img/watch.jpg"));
        servicesPanel.add(createProductCard("HP Laptop", "E7500.00", "Silver-grey", "img/laptop.jpg"));
        servicesPanel.add(createProductCard("Wireless Pro Earbuds", "E599.00", "Active noise cancellation & 30h battery.", "img/earbud.jpg"));
        servicesPanel.add(createProductCard("SONY Headphones ", "E500.00", "Black, Wireless", "img/head.jpg"));
        servicesPanel.add(createProductCard("HD Camera ", "E8000.00", "Black", "img/camera.jpg"));

       

        // TAB 3: CONTACT US (Orange Button, Black Text, No Hover)
        JPanel contactPanel = new JPanel(new GridBagLayout());
        contactPanel.setBackground(Color.WHITE);
        
        JButton openChatButton = new JButton("💬 Open Live Support Chat (" + currentUser.getRole() + " Portal)");
        openChatButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        openChatButton.setBackground(new Color(234, 88, 12)); // Vibrant Orange
        openChatButton.setForeground(Color.BLACK);
        openChatButton.setFocusPainted(false);
        openChatButton.setRolloverEnabled(false);
        openChatButton.setPreferredSize(new Dimension(340, 50));

        openChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Admin".equalsIgnoreCase(currentUser.getRole())) {
                    new AdminChatFrame().setVisible(true);
                } else {
                    new UserChatFrame().setVisible(true);
                }
            }
        });
        contactPanel.add(openChatButton);

        // TAB 4: LOG OUT (Blue Button, Black Text, No Hover)
        JPanel logoutPanel = new JPanel(new GridBagLayout());
        logoutPanel.setBackground(Color.WHITE);

        JButton logoutButton = new JButton("🚪 Confirm Log Out");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBackground(new Color(37, 99, 235)); // Royal Blue
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setFocusPainted(false);
        logoutButton.setRolloverEnabled(false);
        logoutButton.setPreferredSize(new Dimension(260, 45));

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(MainWebPortalFrame.this, "Are you sure you want to end your session?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new LoginFrame().setVisible(true);
                    dispose();
                }
            }
        });
        logoutPanel.add(logoutButton);

        tabbedPane.addTab("About Us", aboutPanel);
        tabbedPane.addTab("Services & Products", servicesPanel);
        tabbedPane.addTab("Contact Us (Chat)", contactPanel);
        tabbedPane.addTab("Log Out", logoutPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createProductCard(final String name, final String price, String desc, String imagePath) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(new Color(248, 250, 252));

        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(160, 130, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } else {
            imgLabel.setText("<html><center><b>[ Image Placeholder ]</b><br><font size='2'>Replace: " + imagePath + "</font></center></html>");
            imgLabel.setPreferredSize(new Dimension(160, 130));
            imgLabel.setOpaque(true);
            imgLabel.setBackground(new Color(226, 232, 240));
        }

        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 3, 3));
        infoPanel.setBackground(new Color(248, 250, 252));

        JLabel nameLbl = new JLabel(name);
        nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLbl.setForeground(new Color(15, 23, 42));

        JLabel priceLbl = new JLabel(price);
        priceLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        priceLbl.setForeground(new Color(37, 99, 235));

        JLabel descLbl = new JLabel("<html><font color='#64748B'>" + desc + "</font></html>");
        descLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        infoPanel.add(nameLbl);
        infoPanel.add(priceLbl);
        infoPanel.add(descLbl);

        JButton buyBtn = new JButton("Add to Cart");
        buyBtn.setBackground(new Color(234, 88, 12)); // Vibrant Orange
        buyBtn.setForeground(Color.BLACK);
        buyBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        buyBtn.setFocusPainted(false);
        buyBtn.setRolloverEnabled(false);

        buyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String itemFormattedMessage = "🛒 [Cart Order]: Added " + name + " (" + price + ") to cart.";
                
                // Broadcast cart order directly through the socket to live chat screens
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket s = new Socket("localhost", 12345);
                            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
                            pw.println(currentUser.getUsername() + ":Admin:[" + currentUser.getUsername() + "]: " + itemFormattedMessage);
                            s.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
                
                JOptionPane.showMessageDialog(MainWebPortalFrame.this, name + " added to cart! Notification broadcasted to live chat.");
            }
        });

        card.add(imgLabel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(buyBtn, BorderLayout.SOUTH);

        return card;
    }
}