package org.example.PBFT.Msg;

import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;

@ToString
@Data
@Log
public class Message {
    protected MessageType type;

    public Message() {
    }

    public Message(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
