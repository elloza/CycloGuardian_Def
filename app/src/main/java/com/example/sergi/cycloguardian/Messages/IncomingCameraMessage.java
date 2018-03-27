package com.example.sergi.cycloguardian.Messages;

/**
 * Created by sergi on 28/02/2018.
 */

public class IncomingCameraMessage extends CameraMessage {
    public int rval = 0;
    public String param = null;
    public String type = null;
    public int paramToken = 0;

    public IncomingCameraMessage() {
        this.rval = -1;
    }


}
