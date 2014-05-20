package com.example.calculadora_ip;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewSubredes extends ListActivity {
	protected static final String EXTRA_MESSAGE = null;
	int totalSubredes, totalHosts;

    long ipMuestra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_subredes);
		// Show the Up button in the action bar.
		Log.d("DEBUG", "HOLAA");
		
		//Recibe direcciï¿½n IP y CIDR
		Intent intent = getIntent();
		long materiaPrima[] = intent.getLongArrayExtra(MainActivity.EXTRA_MESSAGE);
		ipMuestra = materiaPrima[0];
		
		// Create the text view
		String message = String.valueOf(
				Subred.longToIp(materiaPrima[0])+
				" /"+materiaPrima[1]);
	    TextView textView = (TextView) findViewById(R.id.aver);
	    textView.setTextSize(40);
	    textView.setText(message);
	    
	    String TAG = "WERO";
	    
	    totalSubredes = Subred.calculaSubredes(materiaPrima[1], Subred.getIPClass(materiaPrima[0]));
	    totalHosts = Subred.calculaHosts(materiaPrima[1]);
	    
	    //Informacion previa
	    long mascara = generaMascara((int) materiaPrima[1]);
		String message2 = String.valueOf("Subredes: " + totalSubredes + "\nHosts por red: " + totalHosts + "\nMáscara: " + Subred.longToIp(mascara));
	    TextView textView2 = (TextView) findViewById(R.id.produccionTotal);
	    textView2.setTextSize(20);
	    textView2.setText(message2);
	    
	    TextView textView3 = (TextView) findViewById(R.id.cuantaClase);
	    textView3.setTextSize(20);
	    
	    Subred.tellClass(textView3, materiaPrima[0]);
	    
	    //Crear arreglo de subredes
	    Log.d(TAG, "HOLA WERO");
	    long firstAddr = materiaPrima[0];
	    List<Subred> subredes = new ArrayList<Subred>();
	    for(int i = 0; i < totalSubredes; i++){
	    	subredes.add(new Subred(totalHosts, firstAddr));
	    	firstAddr+=totalHosts+2;
	    }
	    Log.d(TAG, "WERO");
	    
	    
	    CustomSubredesAdapter adapter = new CustomSubredesAdapter(this, R.layout.custom_lista_redes, subredes);
	    setListAdapter(adapter);
	    
	    ListView listView = (ListView)findViewById(android.R.id.list);

	    listView.setOnItemClickListener(new OnItemClickListener() {

	    	  @Override
	    	  public void onItemClick(AdapterView<?> parent, View view,
	    	    int position, long id) {
	    		TextView textView = (TextView) view.findViewById(R.id.netID);
	    		String inicio = textView.getText().toString();
	    	    Toast.makeText(getApplicationContext(),
	    	      inicio + " :D " + position, Toast.LENGTH_LONG)
	    	      .show();
	    	    
	    	    Intent intent = new Intent(getApplicationContext(), HostsActivity.class);
	    	    long paqueteria[] = {ipMuestra, (long)totalHosts, (long)position};
	    	    Log.d("ELTAG", String.valueOf(paqueteria[0])+","+String.valueOf(paqueteria[1])+","+String.valueOf(paqueteria[2]));
        		intent.putExtra("AHIVA", paqueteria);	        		
        		startActivity(intent);
	    	    
	    	  }
	    	}); 
	    
		setupActionBar();
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
		getMenuInflater().inflate(R.menu.list_view_subredes, menu);
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
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
	    String item = (String) getListAdapter().getItem(position);
	    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	  }
	
	long generaMascara(int prefijo){
		if(prefijo == 8){
			return 4278190080L;
		}
		else
			return generaMascara(prefijo - 1) + (1L << (32 - prefijo));
		
	}

	
	
	
}
