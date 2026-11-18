package network;

import dao.MessageDAO;
import models.Message;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<PrintWriter> clientWriters = new HashSet<PrintWriter>();

    public static void main(String[] args) {
        System.out.println("Chat server started on port " + PORT + "...");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            try {
                while (true) {
                    new ClientHandler(serverSocket.accept()).start();
                }
            } finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                String messageStr;
                while ((messageStr = in.readLine()) != null) {
                    String[] parts = messageStr.split(":", 3);
                    if (parts.length == 3) {
                        String sender = parts[0];
                        String receiver = parts[1];
                        String content = parts[2];

                        Message msg = new Message(sender, receiver, content);
                        MessageDAO.saveMessage(msg);

                        synchronized (clientWriters) {
                            Iterator<PrintWriter> iterator = clientWriters.iterator();
                            while (iterator.hasNext()) {
                                PrintWriter writer = iterator.next();
                                writer.println(sender + ": " + content);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Client disconnected.");
            } finally {
                if (out != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(out);
                    }
                }
                try { socket.close(); } catch (IOException e) {}
            }
        }
    }
}