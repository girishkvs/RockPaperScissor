package mc.assign1.rockpaperscissor;



import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BluetoothActivity extends ActionBarActivity{
	
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT      = 2;
	
    // Message types sent from the BluetoothGameService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
	private String userName, mode;
	BluetoothAdapter btAdp = BluetoothAdapter.getDefaultAdapter();
	private ArrayAdapter<String> BTArrayAdapter;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetoothdevices);
		Button search = (Button) findViewById(R.id.Search);
		userName = this.getIntent().getExtras().getString("userName");
		mode = this.getIntent().getExtras().getString("mode");
		search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (btAdp.isEnabled()==false) {
					Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
					startActivityForResult(enableBT, REQUEST_ENABLE_BT);
				} else{
					find(v);
				}
			}
		});
		
		ListView myListView = (ListView)findViewById(R.id.listView1);

		// Create the arrayAdapter that contains the BTDevices, and set it to the ListView
		BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		myListView.setAdapter(BTArrayAdapter);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				btAdp.cancelDiscovery();

	            // Get the device MAC address, which is the last 17 chars in the View
	            String info = ((TextView) view).getText().toString();
	            String address = info.substring(info.length() - 17);
	            // Create the result Intent and include the MAC address
	            System.out.println("address: "+address);
	            
        		startGame(userName,address);
	            finish();
			}
		});		
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if(arg0 == 2){
			if(arg1==Activity.RESULT_OK){
				Toast.makeText(getApplicationContext(), "Bluetooth Enabled!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public void startGame(String userName,String deviceaddress ) {
		Intent gameIntent = new Intent(this, GameActivity.class);
		gameIntent.putExtra("userName", userName);
		gameIntent.putExtra("mode", mode);
		gameIntent.putExtra("deviceaddress", deviceaddress);
		startActivity(gameIntent);

	}
	//Discovered devices list
	final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override        
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
				BTArrayAdapter.notifyDataSetChanged();
			}
		}
	};

	public void find(View view) {
		if (btAdp.isDiscovering()) {
			btAdp.cancelDiscovery();
		}
		else {
			btAdp.startDiscovery();
			registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
		}
	}
}
