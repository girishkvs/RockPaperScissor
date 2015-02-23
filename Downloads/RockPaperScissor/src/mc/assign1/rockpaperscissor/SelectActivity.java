package mc.assign1.rockpaperscissor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends ActionBarActivity{

	Intent loginPage;
	String mode = "";
	public static String MULTIPLAYER = "multiplayer";  
	public static String SINGLEPLAYER = "singleplayer";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		Button singlePlayer = (Button) findViewById(R.id.SinglePayer);
		Button multiPlayer = (Button) findViewById(R.id.MultiPlayer);

		singlePlayer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mode = SINGLEPLAYER;				
				loginPage = new Intent("mc.assign1.rockpaperscissor.LOGIN");
				loginPage.putExtra("mode", mode);
				startActivity(loginPage);
			}
		});
		multiPlayer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mode = MULTIPLAYER;				
				loginPage = new Intent("mc.assign1.rockpaperscissor.LOGIN");
				loginPage.putExtra("mode", mode);
				startActivity(loginPage);
			}
		});
	}
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
		.setMessage("Are you sure you want to exit?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {

				finish();
			}
		}).setNegativeButton("No", null).show();
	}

}
