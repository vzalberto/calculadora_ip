package com.example.calculadora_ip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	int totalHosts, cidr, fronteraDerecha;
	long materiaPrima[];
	Intent intent;
	private EditText oct1, oct2, oct3, oct4, misterio;
	private TextView claseIP, tuIP, valorPrefijo;
	private final String TAG = "CALCULADORA";
	private RadioGroup quePasara;
	private SeekBar barraPrefijo;
	
	public final static String EXTRA_MESSAGE = "calculadoraIP.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		oct1 = (EditText) findViewById(R.id.oct1);
		oct2 = (EditText) findViewById(R.id.oct2);
		oct3 = (EditText) findViewById(R.id.oct3);
		oct4 = (EditText) findViewById(R.id.oct4);
		
		claseIP = (TextView) findViewById(R.id.claseIP);
		tuIP = (TextView) findViewById(R.id.tuIP);
		
		misterio = (EditText) findViewById(R.id.misterio);
		quePasara = (RadioGroup) findViewById(R.id.radioGroup);
		
		barraPrefijo = (SeekBar) findViewById(R.id.barraPrefijo);
		valorPrefijo = (TextView) findViewById(R.id.valorPrefijo);
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	int parseIP(){
		String unaIP = 
				oct1.getText().toString() +
				oct2.getText().toString() +
				oct3.getText().toString() +
				oct4.getText().toString();
		
		return Integer.parseInt(unaIP);
		
	}
	
	
	
	String parseIP2(){
		int a,b,c,d;
		String x,y,z,w;
		a = Integer.valueOf(oct1.getText().toString());
		b = Integer.valueOf(oct2.getText().toString());
		c = Integer.valueOf(oct3.getText().toString());
		d = Integer.valueOf(oct4.getText().toString());
		
		x = Integer.toBinaryString(a);
		y = Integer.toBinaryString(b);
		z = Integer.toBinaryString(c);
		w = Integer.toBinaryString(d);
		
		return x + y + z + w;		
		
	}
	
	String parseIPtoLong(){
		long a,b,c,d;
		String x,y,z,w;
		
		a = Long.valueOf(oct1.getText().toString());
		b = Long.valueOf(oct2.getText().toString());
		c = Long.valueOf(oct3.getText().toString());
		d = Long.valueOf(oct4.getText().toString());
		
		x = Long.toBinaryString(a);
		y = Long.toBinaryString(b);
		z = Long.toBinaryString(c);
		w = Long.toBinaryString(d);
		
		return x + y + z + w;		
	}
	
	long IPtoLong(){
		long a,b,c,d;
		String x,y,z,w;
		
		a = Long.valueOf(oct1.getText().toString());
		b = Long.valueOf(oct2.getText().toString());
		c = Long.valueOf(oct3.getText().toString());
		d = Long.valueOf(oct4.getText().toString());
		
		return (a << 24) + (b << 16) + (c << 8) + d;		
		
	}

	void tellClass(){
		switch(Subred.getIPClass(IPtoLong())){
		case -2:
			claseIP.setText("Red Privada");
			break;
		case 1:
			claseIP.setText("Clase A");
			break;
			
		case 2:
			claseIP.setText("Clase B");
			break;			
			
		case 3:
			claseIP.setText("Clase C");
			break;	
			
		case 4:
			claseIP.setText("Clase D");
			break;	
			
		case 5:
			claseIP.setText("Clase E");
			break;	
			
		case 6:
			claseIP.setText("localhost");
			break;	
		}
	}
	
	public void action(View view){
		//Log.d(TAG+"1", String.valueOf(parseIP()));
		//Log.d(TAG, parseIP2());
		Log.d(TAG, parseIPtoLong());
		Log.d(TAG, Long.toBinaryString(IPtoLong()));
		Log.d(TAG, String.valueOf(IPtoLong()));
		Log.d(TAG, String.valueOf(Subred.longToIp(IPtoLong())));
		tuIP.setText(parseIP2());
		tellClass();
		
		vamonos();
		
	}
	
	public void vamonos() {
	    // Is the button now checked?
	    int selectedRadio = quePasara.getCheckedRadioButtonId();
	    
	    // Check which radio button was clicked
	    switch(selectedRadio) {
	        case R.id.radio_hosts:
	                // Calcular para host
	            	Log.d(TAG, "HOST");
	            	
	            	cidr = 31;
	            	fronteraDerecha = 2;
	            	
	            	while(!(fronteraDerecha >= Integer.parseInt(misterio.getText().toString()))){
	            		cidr-=1;
	            		fronteraDerecha*=2;
	            	}	            	
	            	
	            	Log.d(TAG, String.valueOf(cidr+" y "+fronteraDerecha));
	            	
	            	long materiaPrima[] = {IPtoLong(),(long)cidr};
	        		intent = new Intent(this, ListViewSubredes.class);
	        		intent.putExtra(EXTRA_MESSAGE, materiaPrima);	        		
	        		startActivity(intent);
	            	
	            break;
	        case R.id.radio_subredes:
	                // Calcular para subredes
	            	Log.d(TAG, "SUBREDES");
	            	
	            	int sumarAlPrefijo = 0;
	            	int subredes = Integer.parseInt(misterio.getText().toString());
	            	fronteraDerecha = 1;
	            	boolean finCiclo = false;
	            	
	            	while(fronteraDerecha < subredes){
	            		fronteraDerecha*=2;
	            	}
	            	
	            	sumarAlPrefijo = (int) Math.round(Math.log(fronteraDerecha) / Math.log(2));
	            	            	
	            	
	            	switch(Subred.getIPClass(IPtoLong())){
	            	case 1:
	            		cidr = 8;
	            		break;
	            	case 2:
	            		cidr = 16;
	            		break;
	            	case 3:
	            		cidr = 24;
	            		break;
	            	}
	            	
	            	cidr += sumarAlPrefijo;
	            	Log.d(TAG, String.valueOf(cidr+" y "+fronteraDerecha));
	            	long materiaPrima3[] = {IPtoLong(),(long)cidr};
	        		intent = new Intent(this, ListViewSubredes.class);
	        		intent.putExtra(EXTRA_MESSAGE, materiaPrima3);
	        		
	        		startActivity(intent);
	            	
	            	
	            	
	            break;
	        case R.id.radio_prefijo:
	        		// Calcular para prefijo
	        		Log.d(TAG, "Prefijo ");
	        		
	        		cidr = Integer.parseInt(misterio.getText().toString());
	        		
	        		//VALIDAR PREFIJO
	        		if(cidr > 30 || cidr < Subred.defaultCIDR(IPtoLong())){
	        			Toast.makeText(this, "VALOR DEL PREFIJO INVALIDO", Toast.LENGTH_LONG).show();
	        			break;
	        		}
	        		
	        		long aveda = generaMascara(cidr);
	        		int totalSubredes = Subred.calculaSubredes((double) cidr, Subred.getIPClass(IPtoLong()));
	        		totalHosts = Subred.calculaHosts((double) cidr);
	        		
	        		long materiaPrima2[] = {IPtoLong(),(long)cidr};
	        		intent = new Intent(this, ListViewSubredes.class);
	        		intent.putExtra(EXTRA_MESSAGE, materiaPrima2);
	        		
	        		startActivity(intent);
	        		
	        		
	        	
	        	break;
	        	
	    }
	}
	
	long generaMascara(int prefijo){
		if(prefijo == 8){
			return 4278190080L;
		}
		else
			return generaMascara(prefijo - 1) + (1L << (32 - prefijo));
		
	}
	
	
	
				
	}
	
	