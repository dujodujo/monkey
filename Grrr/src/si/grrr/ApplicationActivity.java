package si.grrr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ApplicationActivity extends Activity implements OnClickListener{
	private EditText author;
	private String savedAuthor;
	private TextView appAuthor;
	private int saved;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.app);
		
		Button save = (Button)findViewById(R.id.save);
		save.setOnClickListener(this);
		
		((Button)findViewById(R.id.back)).setOnClickListener(this);
		
		author = (EditText)findViewById(R.id.author);
		
		appAuthor = (TextView)findViewById(R.id.tvAuthor);

		
		if(savedInstanceState != null) {
			this.savedAuthor = savedInstanceState.getString("name");
			this.saved = savedInstanceState.getInt("saved");
			if(this.saved == 1) {
				appAuthor.setText(savedAuthor);
				Log.d("tv set", "asd");
			}
		}
	}

	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.save:
			saveAuthor();
			break;
		case R.id.back:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		}
	}

	private void saveAuthor() {
		String authorName = author.getText().toString();
		if(authorName.length() > 0) {
			this.saved = 1;
			this.savedAuthor = authorName;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("saved", 1);
		outState.putString("name", this.savedAuthor);
		super.onSaveInstanceState(outState);
	}
}
