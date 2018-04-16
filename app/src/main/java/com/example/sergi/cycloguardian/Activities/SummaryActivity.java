package com.example.sergi.cycloguardian.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.sergi.cycloguardian.Models.Session;
import com.example.sergi.cycloguardian.R;

import java.text.SimpleDateFormat;
import java.util.Queue;

public class SummaryActivity extends AppCompatActivity {

    TextView textViewDateStart, textViewDateStop, textViewTimeElapsed, textViewIncidencesNumber,
            textViewAverageOvertaking;
    Queue<Float> summaryQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        long hour, minute, restHour, restMinute, seconds, restSeconds;
        String fechaIni, fechaFin;
        Float dateSum = 0.0f;
        Float distanceAverage = 0.0f;

        //Instances of the xml
        textViewDateStart = (TextView) findViewById(R.id.textViewFechaIni);
        textViewDateStop = (TextView) findViewById(R.id.textViewFechaFin);
        textViewTimeElapsed = (TextView) findViewById(R.id.textViewTimeElapsed);
        textViewIncidencesNumber = (TextView) findViewById(R.id.textViewNumberIncidences);
        textViewAverageOvertaking = (TextView) findViewById(R.id.textViewAverage);

        //SimpleDateFormat for the Date
        String pattern = "EEE, d MMM yyyy  HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        fechaIni = simpleDateFormat.format(Session.getInstance().getSessionStart());
        fechaFin = simpleDateFormat.format(Session.getInstance().getSessionEnd());

        //Average of overtaking in the session
        summaryQueue = Session.getInstance().getSensorDatesQueue();
        Float dateQueue;
        int numberOfDates = 0;
        if (summaryQueue != null) {
            numberOfDates = summaryQueue.size();
            dateQueue = summaryQueue.poll();
            while (dateQueue != null) {
                dateSum = dateSum + dateQueue;
                dateQueue = summaryQueue.poll();
            }
        }


        distanceAverage = dateSum / numberOfDates;

        //Convert miliseconds to hour:minute:seconds
        hour = Session.getInstance().getTimeElapsedSession() / 3600000;
        restHour = Session.getInstance().getTimeElapsedSession() % 3600000;

        minute = restHour / 60000;
        restMinute = restHour % 60000;

        seconds =  restMinute / 1000;
        restSeconds = restMinute % 1000;

        //Put date to the screen
        textViewDateStart.append( "  " + fechaIni);
        textViewDateStop.append("  " + fechaFin);
        textViewTimeElapsed.append("  " + hour + ":" + minute + ":" + seconds);
        textViewIncidencesNumber.append("  " + Session.getInstance().getIncidenceArryList().size());
        textViewAverageOvertaking.append("  " + distanceAverage);
    }
}
