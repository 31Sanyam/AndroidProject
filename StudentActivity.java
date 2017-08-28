package com.example.sanyam.ftt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sanyam on 02-08-2017.
 */
public class StudentActivity extends Activity
{
    Button img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        img= (Button) findViewById(R.id.imageButton_schoolTuition);
        //this.getActionBar().setDisplayHomeAsUpEnabled(true);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Sanyam","onClickCalled");
                Intent intent=new Intent(getApplicationContext(),SchoolTuitionAdapter.class);
                startActivity(intent);
                Log.i("Sanyam", "onClickEnd");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
