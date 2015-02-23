package mc.assign1.rockpaperscissor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {

	private DBDelegate dbDelegate;
	private EditText userName;
	private EditText age;
	private RadioButton sex;
	private String mode;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		Button okButton = (Button) findViewById(R.id.ok);
		dbDelegate = new DBDelegate(getApplicationContext());
		mode = getIntent().getExtras().getString("mode");
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				userName = (EditText) findViewById(R.id.inputUserName);
				age = (EditText) findViewById(R.id.inputAge);
				if (userName.getText().length() == 0
						|| age.getText().length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Field cannot be empty", Toast.LENGTH_SHORT).show();
				} else {
					RadioGroup radioSex = (RadioGroup) findViewById(R.id.radioSex);
					int selectedSex = radioSex.getCheckedRadioButtonId();
					sex = (RadioButton) findViewById(selectedSex);

					if (!dbDelegate.isUserExists(userName.getText().toString())) {
						dbDelegate.insert(userName.getText().toString(),
								Integer.parseInt(age.getText().toString()), sex
								.getText().toString());
						startGame(userName.getText().toString());
					} else if (dbDelegate.isUserExists(userName.getText()
							.toString())) {

						if (dbDelegate.isAgeSexValid(userName.getText()
								.toString(), Integer.parseInt(age.getText()
										.toString()), sex.getText().toString())) {
							startGame(userName.getText().toString());
						} else {
							Toast.makeText(
									getApplicationContext(),
									"Username already exists. Please choose a different username",
									Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	public void startGame(String userName) {
		if(mode.equalsIgnoreCase(SelectActivity.SINGLEPLAYER)){
			Intent gameIntent = new Intent(this, GameActivity.class);
			gameIntent.putExtra("userName", userName);
			gameIntent.putExtra("mode", mode);
			startActivity(gameIntent);
		}
		else if(mode.equalsIgnoreCase(SelectActivity.MULTIPLAYER)){
			Intent gameIntent = new Intent(this, BluetoothActivity.class);
			gameIntent.putExtra("userName", userName);
			gameIntent.putExtra("mode", mode);
			startActivity(gameIntent);
		}
	}
	// The Handler that gets information back from the BluetoothChatService
   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

}
