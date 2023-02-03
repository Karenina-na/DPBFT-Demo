package org.example.PBFT;

import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

import java.util.Queue;

//  This is the main class for the PBFT protocol
@ToString
@Data
public class PBFT_Agent {

    //  The id of the agent
    private int id;
    //  The view of the agent
    private int view;
    //  The sequence number of the agent
    private int seq;
    //  state of the agent
    private State state;

    //  queue
    private Queue PrePrepareQueue;
    private Queue PrepareQueue;
    private Queue CommitQueue;
}
