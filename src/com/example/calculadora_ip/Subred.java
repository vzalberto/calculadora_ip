package com.example.calculadora_ip;

import android.util.Log;
import android.widget.TextView;

public class Subred {
	public long NetID, broadcastAddr, start, end;
	private static final long SEGUNDO_OCTETO = 16711680L;
	private static final long TERCER_OCTETO = 65280L;
	private static final long CUARTO_OCTETO = 255L;
	
	public Subred(int hosts, long startAddr){
		NetID = startAddr;
		broadcastAddr = NetID + (long) hosts + 1;
		start = NetID + 1;
		end = broadcastAddr - 1;
		
	}
	
	public long getNetID(){
		return this.NetID;
	}
	
	public long getbroadcastAddr(){
		return this.broadcastAddr;
	}
	
	public long getStart(){
		return this.start;
	}
	
	public long getEnd(){
		return this.end;
	}
	static int calculaSubredes(double prefijo, int claseIP){
		switch(claseIP){
			case 1:
				return (int) Math.pow(2, (prefijo - 8));
				//break;
			case 2:
				return (int) Math.pow(2, (prefijo - 16));
				//break;
			case 3:
				return (int) Math.pow(2,(prefijo -	24));
				//break;
			default:
				return 1;
		}
	}
	
	static int calculaHosts(double prefijo){
		return (int) Math.pow(2, (32 - prefijo)) - 2;
	}
	
	static int getIPClass(long ipAddr){
		int octect = (int) (ipAddr >> 24);
		
		if(octect == 10 || octect == 172 || octect == 192)
			return -2;
		else if(octect < 127)
			return 1;
		else if(octect > 127 && octect < 192)
			return 2;
		else if(octect > 191 && octect <= 223)
			return 3;
		else if(octect > 223 && octect < 240)
			return 4;
		else if(octect > 239 && octect < 255)
			return 5;
		else if(octect == 127)
			return 6;
		else 
			return -1;
	}	

	static String longToIp(long ip){
		String ipString = "";
			ipString = String.valueOf((ip >> 24)) + ".";
			Log.d("AY", Long.toBinaryString(ip));
			Log.d("AY", Long.toBinaryString(ip>>>24));
			
			ipString+= String.valueOf((ip & SEGUNDO_OCTETO) >>> 16) + ".";
			Log.d("AY", Long.toBinaryString(SEGUNDO_OCTETO));
			Log.d("AY", Long.toBinaryString(ip & SEGUNDO_OCTETO));
			Log.d("AY", Long.toBinaryString((ip & SEGUNDO_OCTETO) >>> 16));
			
			ipString+= String.valueOf((ip & TERCER_OCTETO) >>> 8) + ".";
			Log.d("AY", Long.toBinaryString(TERCER_OCTETO));			
			Log.d("AY", Long.toBinaryString(ip & TERCER_OCTETO));
			Log.d("AY", Long.toBinaryString((ip & TERCER_OCTETO) >>> 8));
			
			ipString+= String.valueOf(ip & CUARTO_OCTETO);
			Log.d("AY", Long.toBinaryString(CUARTO_OCTETO));
			Log.d("AY", Long.toBinaryString(ip & CUARTO_OCTETO));
			
		return ipString;
	}
	
	static int defaultCIDR(long ip){
		switch(getIPClass(ip)){
    	case 1:
    		return 8;
    	case 2:
    		return 16;
    	case 3:
    		return 24;

		default: return 0;
    	}
		}
	
	static void tellClass(TextView claseIP, long ipAddr){
		switch(getIPClass(ipAddr)){
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
	
	

}
