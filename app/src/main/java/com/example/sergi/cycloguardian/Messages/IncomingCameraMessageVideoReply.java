package com.example.sergi.cycloguardian.Messages;

/**
 * Created by sergi on 13/03/2018.
 */

public class IncomingCameraMessageVideoReply extends IncomingCameraMessage {

    //Constructor of the class
    public IncomingCameraMessageVideoReply() {

    }

    public  IncomingCameraMessageVideoReply(int rval, int msg_id, String param) {
        this.rval = rval;
        this.msg_id = msg_id;
        this.param = param;
    }

    @Override
    public void parserMessage(String cadToken) {
        String delims = "[{,}]";
        String[] tokens = cadToken.split(delims);
        int rval = 0, msg_id = 0;
        String param = null;
        for (int i = 0; i < tokens.length; i++) {  //Parseamos las cadenas primero por comas
            String delims2 = "[:]";
            String[] tokens2 = tokens[i].split(delims2);
            for (int j = 0; j < tokens2.length; j++) {   //Parseamos para obtener las parejas atributo valor
                if (tokens2[j].contains("rval")) {
                    rval = Integer.parseInt(tokens2[j + 1]);
                    this.setRval(rval);
                }

                if (tokens2[j].contains("msg_id")) {
                    msg_id = Integer.parseInt(tokens2[j + 1]);
                    this.setMsg_id(msg_id);
                }

                if (tokens2[j].contains("param")) {
                    param = tokens2[j + 1];
                    this.setParam(param);
                }
            }
        }
    }
}

