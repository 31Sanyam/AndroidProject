package com.example.sanyam.ftt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
     public void studentLogin(View v)
     {
         String strStudent="Students Login";
         Intent studentIntent=new Intent(this,LoginActivity.class);
         studentIntent.putExtra("tempString",strStudent);
         startActivity(studentIntent);
     }

     public void tutorInstituteLogin(View v)
     {
         String strTutor="Tutor/Institute Login";
        Intent tutorIntent=new Intent(this,LoginActivity.class);
        tutorIntent.putExtra("tempString",strTutor);
         startActivity(tutorIntent);
     }
}

