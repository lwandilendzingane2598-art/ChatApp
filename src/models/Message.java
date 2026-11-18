package models;

import java.sql.Timestamp;

public class Message {
    private int id;
    private String sender;
    private String receiver;
    private String content;
    private Timestamp timestamp;

    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public Message(int id, String sender, String receiver, String content, Timestamp timestamp) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public String getContent() { return content; }
    public Timestamp getTimestamp() { return timestamp; }
}