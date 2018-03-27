package com.example.sergi.cycloguardian.Messages;

/**
 * Created by sergi on 13/03/2018.
 */

public class IncomingCameraMessageVideoStart extends IncomingCameraMessage {

    public IncomingCameraMessageVideoStart(String type, int msg_id) {
        this.msg_id = msg_id;
        this.type = type;
    }
}
