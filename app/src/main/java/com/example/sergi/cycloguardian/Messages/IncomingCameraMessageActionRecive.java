package com.example.sergi.cycloguardian.Messages;

/**
 * Created by sergi on 12/03/2018.
 */

public class IncomingCameraMessageActionRecive extends IncomingCameraMessage {

    public IncomingCameraMessageActionRecive(int rval, int msg_id){
        this.rval = rval;
        this.msg_id = msg_id;
    }
}
