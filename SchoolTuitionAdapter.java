package com.example.sanyam.ftt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanyam on 05-08-2017.
 */
public class SchoolTuitionAdapter extends Activity
        implements AdapterView.OnItemSelectedListener,MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    Spinner spin_class,spin_subject,spin_board,spin_location,spin_area;
    Button btn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentadapter);
        //this.getActionBar().setDisplayHomeAsUpEnabled(true);
        btn_search= (Button) findViewById(R.id.search);
        spin_class = (Spinner) findViewById(R.id.spinner_class);
        spin_class.setOnItemSelectedListener(this);
        spin_subject = (Spinner) findViewById(R.id.spinner_subject);
        spin_board = (Spinner) findViewById(R.id.spinner_Board);
        spin_location = (Spinner) findViewById(R.id.spinner_location);


        ArrayAdapter<CharSequence> adapter_class = ArrayAdapter.createFromResource(this, R.array.category_class, android.R.layout.simple_spinner_item);
        spin_class.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter_class,
                R.layout.spinner_item,this));
        adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter_subject = ArrayAdapter.createFromResource(this, R.array.category_class, android.R.layout.simple_spinner_item);
        spin_subject.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter_subject,
                R.layout.spinner_item1,this));
        adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter_Board = ArrayAdapter.createFromResource(this, R.array.category_board, android.R.layout.simple_spinner_item);
        spin_board.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter_Board,
                R.layout.spinner_item2,this));
        adapter_Board.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter_location = ArrayAdapter.createFromResource(this, R.array.category_location, android.R.layout.simple_spinner_item);
        spin_location.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter_location,
                R.layout.spinner_item3,this));
        adapter_location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Sanyam", "onSearchCalled");

                try
                {
                    if(spin_class.getSelectedItem() ==null || spin_subject.getSelectedItem() == null || spin_board.getSelectedItem() == null || spin_location.getSelectedItem() ==null ) {
                    Toast.makeText(getApplicationContext(),"Field can't be empty",Toast.LENGTH_LONG).show();
                }else
                {
                    String Class = spin_class.getSelectedItem().toString();
                    String location=spin_location.getSelectedItem().toString();
                    Log.i("Sanyam","Selected item"+Class);
                    Intent intent=new Intent(getApplicationContext(),ResultDisplay.class);
                    intent.putExtra("Class",Class);
                   // intent.putExtra("Location",location);
                    startActivity(intent);

                }
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }


                Log.i("Sanyam", "onClickEnd");
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sp1 = String.valueOf(spin_class.getSelectedItem());
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        if (sp1.contentEquals   ("Class 1 - 5")) {
            List<String> list = new ArrayList<String>();
            list.add("HINDI");
            list.add("MATH");
            list.add("Science");
            list.add("EVS");
            list.add("Computer");
            list.add("All subjects");
           // multiSelectionSpinner.setItems(list);
           // multiSelectionSpinner.setListener(this);
            ArrayAdapter adapter_subject = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);

            spin_subject.setAdapter(new NothingSelectedSpinnerAdapter(
                    adapter_subject,
                    R.layout.spinner_item1, this));
            adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }
        if (sp1.contentEquals("Class 6 - 8")) {
            List<String> list = new ArrayList<String>();
            list.add("HINDI");
            list.add("MATH");
            list.add("EVS");
            list.add("History");
            list.add("Science");
            list.add("English");
            list.add("Social Studies");
            list.add("Computer");
            list.add("All subjects");
            // multiSelectionSpinner.setItems(list);
            // multiSelectionSpinner.setListener(this);
            ArrayAdapter adapter_subject = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);

            spin_subject.setAdapter(new NothingSelectedSpinnerAdapter(
                    adapter_subject,
                    R.layout.spinner_item1, this));
            adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }
        if (sp1.contentEquals("Class 9 - 10")) {
            List<String> list = new ArrayList<String>();
            list.add("HINDI");
            list.add("MATH");
            list.add("Science");
            list.add("English");
            list.add("Social Studies");
            list.add("Computer");
            list.add("All subjects");
            // multiSelectionSpinner.setItems(list);
            // multiSelectionSpinner.setListener(this);
            ArrayAdapter adapter_subject = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);

            spin_subject.setAdapter(new NothingSelectedSpinnerAdapter(
                    adapter_subject,
                    R.layout.spinner_item1, this));
            adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }if (sp1.contentEquals("Class 11")) {
            List<String> list = new ArrayList<String>();
            list.add("Physics");
            list.add("Chemistry");
            list.add("Math");
            list.add("Accounts");
            list.add("Economics");
            list.add("Business Studies");
            list.add("Biology");

            ArrayAdapter adapter_subject = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);

            spin_subject.setAdapter(new NothingSelectedSpinnerAdapter(
                    adapter_subject,
                    R.layout.spinner_item1, this));
            adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }
        if (sp1.contentEquals("Class 12")) {
        List<String> list = new ArrayList<String>();
        list.add("Physics");
        list.add("Chemistry");
        list.add("Math");
        list.add("Accounts");
        list.add("Economics");
        list.add("Business Studies");
        list.add("Biology");

        ArrayAdapter adapter_subject = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);

        spin_subject.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter_subject,
                R.layout.spinner_item1, this));
        adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
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
    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(this,StudentActivity.class);
        startActivity(intent);
    }
}
