package si.grrr;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_form);
		
		Button deleteText = (Button)findViewById(R.id.deleteText);
		deleteText.setOnClickListener(this);
		
		Button deleteFile = (Button)findViewById(R.id.deleteFile);
		deleteFile.setOnClickListener(this);
		
		Button saveText = (Button)findViewById(R.id.saveText);
		saveText.setOnClickListener(this);
		
		Button showText = (Button)findViewById(R.id.showText);
		showText.setOnClickListener(this);
	}

	public void onClick(View v) {
		EditText text = (EditText)findViewById(R.id.etName);
		String enterText = text.getText().toString();
		String fileName = "list";
		FileOutputStream fos;
		
		switch(v.getId()) {
		case R.id.deleteText:
			text.setText("");
			break;
		case R.id.showText:
			try {
				InputStream in = openFileInput(fileName); 
				if(in != null) {
				    InputStreamReader isr = new InputStreamReader(in);
				    BufferedReader reader = new BufferedReader(isr);
				    String line;
				    String result = "";
					
				    while((line = reader.readLine()) != null) {
				    	if(result != "") {
				    		result += ", " + line;
				    	} else {
				    		result += line;
				    	}
				    }
				    in.close();
				    Toast.makeText(getApplicationContext(), 
				    	"Content of file: " + result, Toast.LENGTH_LONG).show();
				}
				break;
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "list is empty", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.saveText:
			try {
				fos = openFileOutput(fileName, MODE_APPEND);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				OutputStreamWriter osw = new OutputStreamWriter(bos);
				BufferedWriter writer = new BufferedWriter(osw);
				
				writer.newLine();
				writer.flush();
				writer.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.deleteFile:
			deleteFile(fileName);
			break;
		}
	}
}
