package com.example.sanyam.ftt;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Sanyam on 10-08-2017.
 */
public class ResultDisplay extends Activity
{
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Sanyam", "onCreateCalled");
        setContentView(R.layout.activity_result);
        Bundle extras;
        extras =getIntent().getExtras();
        String value=extras.getString("Class");
        String value1=extras.getString("Location");
        this.listView = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        Log.i("Sanyam","Value"+value);
        List<String> quotes = databaseAccess.getResult(value);
        //List<String> quotes1 = databaseAccess.getSearch(value);
        databaseAccess.close();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_listimage, R.id.Itemname,quotes);
       // adapter.add(String.valueOf(quotes1));
        this.listView.setAdapter(adapter);
        Log.i("Sanyam", "onCreateEnd");
    }

}
