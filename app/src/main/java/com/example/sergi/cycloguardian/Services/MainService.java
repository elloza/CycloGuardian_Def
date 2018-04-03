package com.example.sergi.cycloguardian.Services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.sergi.cycloguardian.Files.Photo;
import com.example.sergi.cycloguardian.Messages.IncomingCameraMessage;
import com.example.sergi.cycloguardian.Messages.OutcomingCameraMessagePhoto;
import com.example.sergi.cycloguardian.Messages.OutcomingCameraMessageRequest;
import com.example.sergi.cycloguardian.Utils.Constants;
import com.example.sergi.cycloguardian.Utils.Parser;

import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by sergi on 25/03/2018.
 */

public class MainService extends Service {
    NetworkTask networktask;
    Context context;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("SERVICE", "JFOASDJFA");
        hacerFoto();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SERVICE", "JFOASDJFA");
        hacerFoto();
        return START_STICKY;
    }

    @Override
    public  void onDestroy() {

    }

    public void hacerFoto() {
        OutcomingCameraMessagePhoto msg = new OutcomingCameraMessagePhoto(Constants.MSG_ID_TAKE_PHOTO); //Constructor del mensaje
        networktask = new NetworkTask();
        networktask.execute(msg);
    }

    public class NetworkTask extends AsyncTask<OutcomingCameraMessagePhoto, byte[], Photo> {

        Socket nsocket; //Network Socket
        InputStream nis; //Network Input Stream
        OutputStream nos; //Network Output Stream

        @Override
        protected void onPreExecute() {
            Log.i("AsyncTask", "onPreExecute");
        }

        @Override
        protected Photo doInBackground(OutcomingCameraMessagePhoto... params) { //This runs on a different thread
            String result;
            String ruta = null;
            String fileName = null;
            String url = null;
            Parser p = new Parser();
            Photo photo = null;
            try {

                OutcomingCameraMessagePhoto msgPhoto = params[0];
                OutcomingCameraMessageRequest msg = new OutcomingCameraMessageRequest(Constants.MSG_ID_REQUEST);

                //TODO INICIAR LA CONEXIÓN
                Log.i("AsyncTask", "doInBackground: Creating socket");
                SocketAddress sockaddr = new InetSocketAddress(Constants.IP_CAMARA, Constants.PUERTO_CAMARA);
                nsocket = new Socket();
                try {
                    nsocket.connect(sockaddr, Constants.TIMEOUT_SOCKET); //10 segundos de timeout
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (nsocket.isConnected()) {
                    nis = nsocket.getInputStream();  //Creamos los streams
                    nos = nsocket.getOutputStream();
                    Log.i("AsyncTask", "doInBackground: Socket created, streams assigned");
                    Log.i("AsyncTask", "doInBackground: Waiting for inital data...");
                    //TODO OBTENER EL TOKEN
                    //Enviamos el mensaje inicial para obtener el token
                    String cmd = msg.componerMensajeRequest();  //Creación de la cadena inicial
                    nos.write(cmd.getBytes());
                    Log.i("AsyncTask", "doInBackground: Waiting for reply message..");
                    byte[] buffer = new byte[Constants.TAMANO_BUFFER];
                    int read = nis.read(buffer, 0, Constants.TAMANO_BUFFER); //This is blocking
                    byte[] tempdata = new byte[read];
                    System.arraycopy(buffer, 0, tempdata, 0, read);
                    publishProgress(tempdata);
                    //Parsear la cadena recibida
                    IncomingCameraMessage inMsgReply = p.parsearMensaje(tempdata);
                    if(inMsgReply.rval < 0) {
                        //La acción de tomar foto ha fallado
                        photo = new Photo(null, null);
                    }

                    //TODO SETEAR EL TOKEN EN EL MSG
                    //OutcomingCameraMessage msgPhoto = new OutcomingCameraMessage(769, inMsgReply.paramToken);
                    msgPhoto.setToken(inMsgReply.paramToken); //Modificamos el token del mensaje
                    String cmdPhoto = msgPhoto.componerMensajePhoto();

                    //TODO ENVIAR MSG
                    nos.write(cmdPhoto.getBytes());

                    //TODO ESPERAR RESPUESTA --> CON TIMEOUT
                    byte[] inputData = new byte[1024];
                    int readCount = readInputStreamWithTimeout(nis, inputData, Constants.TIMEOUT_MENSAJE);  //TimeOut de 6 segundos
                    byte[] tempdataPhoto = new byte[readCount];
                    System.arraycopy(inputData, 0, tempdataPhoto, 0, readCount);
                    publishProgress(tempdataPhoto);

                    //Comprobar los mensajes recibidos y determinar si ha habido una respuesta correcta
                    //Primeramente parseamos las cadenas
                    int i;
                    String cadena = new String(tempdataPhoto);
                    String delimiter = "[{]";
                    String[] parts = cadena.split(delimiter);
                    for (i = 0; i < parts.length; i++){  //Recorremos el array de partes
                        //Añadimos el caracter que anteriormente hemos eliminado
                        parts[i] = "{" + parts[i];

                    }
                    IncomingCameraMessage[] msgsRespond = new IncomingCameraMessage[i];

                    for (i = 0; i < parts.length; i++){  //Recorremos el array de partes y cremos los distintos mensajes
                        msgsRespond[i] = p.parsearMensaje(parts[i].getBytes());
                        //msgsRespond[i].parserRespondMessage();
                        if(msgsRespond[i].type != null) {
                            if (msgsRespond[i].type.contains("photo_taken")) {
                                if (msgsRespond[i].param != null){
                                    ruta = msgsRespond[i].param;
                                    fileName = p.extractFileName(ruta);
                                    url = p.generateFileURL(fileName);
                                    photo = new Photo(url, fileName);
                                }
                            }
                        }
                    }


                    //TODO save image into Internal Storage


                    //TODO CERRAR SOCKETS Y STREAMS
                    nis.close();
                    nos.close();
                    nsocket.close();

                }

                //TODO RETURN OK | ERROR

            } catch (IOException e) {
                e.printStackTrace();
            }

            return photo;  //Devolvemos la foto con la url y el nombre al método PostExecute
        }

        public int readInputStreamWithTimeout(InputStream is, byte[] b, int timeoutMillis)
                throws IOException  {
            int bufferOffset = 0;
            long maxTimeMillis = System.currentTimeMillis() + timeoutMillis;
            while (System.currentTimeMillis() < maxTimeMillis && bufferOffset < b.length) {
                int readLength = java.lang.Math.min(is.available(),b.length-bufferOffset);
                // can alternatively use bufferedReader, guarded by isReady():
                int readResult = is.read(b, bufferOffset, readLength);
                if (readResult == -1) break;
                bufferOffset += readResult;
            }
            return bufferOffset;
        }


        @Override
        protected void onProgressUpdate(byte[]... values) {
            String str = null;
            if (values.length > 0) {
                Log.i("AsyncTask", "onProgressUpdate: " + values[0].length + " bytes received.");
                str = new String(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Photo photo) {
            if (photo.getUrl() != null) {
                Log.i("AsyncTask", "onPostExecute: Completed.");
                //Hacer aquí un Toast de que algo ha fallado
                //Mostrar la imagen de la foto realizada
               /* textStatus.setText(photo.getUrl());
                Glide.with(imageView.getContext())
                        .load(photo.getUrl())
                        .into(imageView);*/

                //Extraemos la foto a bitmap
                //extractBitmap(photo);

            } else {
                Log.i("AsyncTask", "onPostExecute: Something ocurred.");
                //textStatus.setText("No se ha podido realizar la foto");
            }
        }


    }

    private void extractBitmap(final Photo photo) {
        Glide
                .with(this)
                .load(photo.getUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(300,300) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        photo.setBitmap(resource);
                        //imageView.setImageBitmap(resource);
                        String ruta = saveToInternalStorage(photo);
                        System.out.println(ruta);
                    }
                });
    }

    private String saveToInternalStorage(Photo photo){
        //imageView.setImageBitmap(photo.getBitmap());
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/CycloGuardian/Photo");

        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

       /* if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }*/

        myDir.mkdirs();

        String fname = photo.getNamePhoto();
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            photo.getBitmap().compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }


}