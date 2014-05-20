package com.example.calculadora_ip;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomSubredesAdapter extends ArrayAdapter<Subred>{
	
	public CustomSubredesAdapter(Context context, int ViewResourceId){
		super(context, ViewResourceId);
	}
	
	public CustomSubredesAdapter(Context context, int ViewResourceId, List<Subred> subredes){
		super(context, ViewResourceId, subredes);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

	    View v = convertView;

	    if (v == null) {

	        LayoutInflater vi;
	        vi = LayoutInflater.from(getContext());
	        v = vi.inflate(R.layout.custom_lista_redes, null);

	    }

	    Subred p = getItem(position);

	    if (p != null) {

	        TextView tt = (TextView) v.findViewById(R.id.netID);
	        TextView tt1 = (TextView) v.findViewById(R.id.broadcastAddr);
	        TextView tt3 = (TextView) v.findViewById(R.id.rango);

	        if (tt != null) {
	            tt.setText("Net ID: " + Subred.longToIp(p.getNetID()));
	        }
	        if (tt1 != null) {

	            tt1.setText("Broadcast: " + Subred.longToIp(p.getbroadcastAddr()));
	        }
	        if (tt3 != null) {

	            tt3.setText("Rango: " + Subred.longToIp(p.getStart()) 
	            		+ " - " 
	            		+ Subred.longToIp(p.getEnd()));
	        }
	    }

	    return v;

	}

}
