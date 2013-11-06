package com.example.spinner.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SpinnerActivity extends Activity implements OnItemSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //spinner element
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        //spinner click listener
        spinner.setOnItemSelectedListener(this);
        //spinner drop down listener
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Thor");
        categories.add("Valhalla");
        categories.add("Odin");
        categories.add("Chrom");
        
        //create adapter for the list
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinner.setAdapter(aa);
    }

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		//On Selecting a spinner item
		String item = parent.getItemAtPosition(position).toString();
		Toast.makeText(getApplicationContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
}
