package org.example.PBFT;

//  state of the agent
enum State {
    //  The agent is in the initial state
    INITIAL,
    //  The agent is in the pre-prepare state
    PRE_PREPARE,
    //  The agent is in the prepare state
    PREPARE,
    //  The agent is in the commit state
    COMMIT,
    //  The agent is in the view change state
    VIEW_CHANGE,
    //  The agent is in the new view state
    NEW_VIEW
}