package com.example.sergi.cycloguardian.Messages;

/**
 * Created by sergi on 13/03/2018.
 */

public class IncomingCameraMessageActionState extends IncomingCameraMessage {

    public IncomingCameraMessageActionState(String type, String param, int msg_id) {
        this.msg_id = msg_id;
        this.type = type;
        this.param = param;
    }
}
