package gui;

import dao.MessageDAO;
import models.Message;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.List;

public abstract class BaseChatUI extends JFrame {
    protected JTextArea chatArea;
    protected JTextField inputField;
    protected JButton sendButton;
    protected String currentUser;
    protected String recipientUser;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public BaseChatUI(String title, String currentUser, String recipientUser) {
        super(title);
        this.currentUser = currentUser;
        this.recipientUser = recipientUser;

        initGUI();
        loadHistory();
        connectToServer();
    }

    private void initGUI() {
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chatArea.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 243, 246));

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(37, 99, 235)); // Royal Blue
        sendButton.setForeground(Color.BLACK);
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sendButton.setFocusPainted(false);
        sendButton.setRolloverEnabled(false);

        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        setLocationRelativeTo(null);
    }

    private void loadHistory() {
        try {
            List<Message> history = MessageDAO.getChatHistory(currentUser, recipientUser);
            for (int i = 0; i < history.size(); i++) {
                Message msg = history.get(i);
                chatArea.append(msg.getContent() + "\n");
            }
        } catch (Exception e) {
            chatArea.append("[Warning: Could not load chat history]\n");
        }
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String line;
                    try {
                        while ((line = in.readLine()) != null) {
                            chatArea.append(line + "\n");
                        }
                    } catch (IOException e) {
                        chatArea.append("[Disconnected from server]\n");
                    }
                }
            }).start();
            
        } catch (IOException e) {
            chatArea.append("[Cannot connect to server. Start ChatServer first.]\n");
        }
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty() && out != null) {
            out.println(currentUser + ":" + recipientUser + ":[" + currentUser + "]: " + text);
            inputField.setText("");
        }
    }
}