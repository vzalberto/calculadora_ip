package com.example.calculadora_ip;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HostsActivity extends Activity {
	String listota;long i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hosts);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		long paquete[] = intent.getLongArrayExtra("AHIVA");
		
		TextView info = (TextView) findViewById(R.id.info);
		TextView listaHosts = (TextView) findViewById(R.id.listaHosts);
		
		listota = "";
		
		info.setText(Subred.longToIp(paquete[0])
				+String.valueOf(paquete[1])
				+String.valueOf(paquete[2]));
		
		
		long startIp = paquete[0] + ((paquete[1]+2)*paquete[2]);
		

		Log.d("PRUEBAMAYO", String.valueOf(paquete[0] + "\n" + paquete[1] + "\n" + paquete[2] + "\n" + startIp));
		
		
		for(i = 1; i <= paquete[1] || i <= 30; i++){
			listota = listota + Subred.longToIp(startIp + i) + "\n";
		}
		
		info.setText("Subred # " + (paquete[2] + 1));
		listaHosts.setText(listota);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hosts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onRestart() {
	    super.onRestart();  // Always call the superclass method first
	    i = 0;
	    // Activity being restarted from stopped state    
	}

}
