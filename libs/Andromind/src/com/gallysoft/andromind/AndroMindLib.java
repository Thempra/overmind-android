package com.gallysoft.andromind;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;


public class  AndroMindLib extends Thread {

	private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private  InputStream mmInStream;
    private  OutputStream mmOutStream;
    private static final UUID UUID_RFCOMM_GENERIC = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private double signal;
    private double meditation;
    private double attention;
    private double rawdelta;
    private double rawtheta;
    private double rawlalpha;
    private double rawhalpha;
    private double rawlbeta;
    private double rawhbeta;
    private double rawlgamma;
    private double rawhgamma;
    private double prodelta;
    private double protheta;
    private double prolalpha;
    private double prohalpha;
    private double prolbeta;
    private double prohbeta;
    private double prolgamma;
    private double prohgamma;
    private AndroMindListener alistener;
 
    public final static int OK = 0;
    private final static int BAD_DATA = -1;
    private final static int IOERROR = -2;
    private final static int UNSUCCESSFUL = -3;
    
    private boolean connected;
    private int deviceN=0;
    
    
    public AndroMindLib(BluetoothDevice device) {
    	 // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;
        this.deviceN=0;
        
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(UUID_RFCOMM_GENERIC);
        } catch (IOException e) { 
        	
        }
        mmSocket = tmp;
    }
    	
    public AndroMindLib(BluetoothDevice device, int deviceN) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;
        this.deviceN=deviceN;
        
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(UUID_RFCOMM_GENERIC);
        } catch (IOException e) { 
        	
        }
        mmSocket = tmp;
    }
 
    public synchronized void run() {
        // Cancel discovery because it will slow down the connection
        
 
    	if (!connected)
    	{
        
			try {
	            // Connect the device through the socket. This will block
	            // until it succeeds or throws an exception
	            mmSocket.connect();
	        } catch (IOException connectException) {
	            // Unable to connect; close the socket and get out
	        	connected = false;
	            try {
	                mmSocket.close();
	            } catch (IOException closeException) { 
	            	
	            }
	            
	            return;
	        }
	        connected = true;    
    	}
    	
       try {
    	   mmInStream = mmSocket.getInputStream();
    	   mmOutStream = mmSocket.getOutputStream();
       } catch (IOException e) { }

      
       int[] buffer = new int[36];  // buffer store for the stream
       int bytes; // bytes returned from read()
       int i=0;

       // Keep listening to the InputStream until an exception occurs
       while (true) {
           try {
               
               bytes = mmInStream.read();

               if(i==0 && bytes==170){
            	   buffer[i] = bytes;
            	   i++;
               }else if (buffer[0]==170 && i==1 && bytes==170){
            	   buffer[i] = bytes;
            	   for(i=2;i<36;i++)
            		   buffer[i] = mmInStream.read();;
            	   
            	   String tmp = new String();
            	   for(i=0;i<36;i++)
            		   tmp = tmp + Integer.toHexString(buffer[i]) + " ";
            	  
            	   
            	   signal=(200-buffer[4]);
            	   
            	   meditation=(buffer[34]);
            	   attention=(buffer[32]);
            	   
            	   
            	   rawdelta = (buffer[7] & 0xFF) << 16 |  (buffer[8] & 0xFF) << 8 | buffer[9];
            	   prodelta = (1/rawdelta) * 1000000;
            	   
            	   
            	   rawtheta = (buffer[10] & 0xFF) << 16 |  (buffer[11] & 0xFF) << 8 | buffer[12];
            	   protheta=(1/rawtheta) * 1000000;
            	  
            	   
            	   rawlalpha = (buffer[13] & 0xFF) << 16 |  (buffer[14] & 0xFF) << 8 | buffer[15];
            	   prolalpha=(1/rawlalpha) * 1000000;
            	 
            	   
            	   
            	   rawhalpha = (buffer[16] & 0xFF) << 16 |  (buffer[17] & 0xFF) << 8 | buffer[18];
            	   prohalpha=(1/rawhalpha) * 1000000;
            	  
            	   
            	  rawlbeta = (buffer[19] & 0xFF) << 16 |  (buffer[20] & 0xFF) << 8 | buffer[21];
            	   prolbeta=(1/rawlbeta) * 1000000;
            	   
            	   
            	   rawhbeta = (buffer[22] & 0xFF) << 16 |  (buffer[23] & 0xFF) << 8 | buffer[24];
            	   prohbeta=(1/rawhbeta) * 1000000;
            	   
            	   
            	   rawlgamma = (buffer[25] & 0xFF) << 16 |  (buffer[26] & 0xFF) << 8 | buffer[27];
            	   prolgamma=(1/rawlgamma) * 1000000;
            	  
            	   
            	   rawhgamma = (buffer[28] & 0xFF) << 16 |  (buffer[29] & 0xFF) << 8 | buffer[30];
            	   prohgamma = (1/rawhgamma) * 1000000;
            	   
            	   if(alistener != null ) alistener.newCaptured(this, deviceN);
               }else i=0;
               
              

           } catch (IOException e) {
               break;
           }
       }
       
       
    }
 
    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
    
    
    public void close() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

	/**
	 * @retorna la calidad de la se�al
	 */
	public synchronized double getSignal() {
		return signal;
	}

	/**
	 * @return la meditaci�n
	 */
	public synchronized double getMeditation() {
		return meditation;
	}

	/**
	 * @return la atencion
	 */
	public synchronized double getAttention() {
		return attention;
	}

	/**
	 * @return el valor de delta sin procesar
	 */
	public synchronized double getRawdelta() {
		return rawdelta;
	}

	/**
	 * @return el valor de theta sin procesar
	 */
	public synchronized double getRawtheta() {
		return rawtheta;
	}

	/**
	 * @return el valor de alpha bajo sin procesar
	 */
	public synchronized double getRawlalpha() {
		return rawlalpha;
	}

	/**
	 * @return el valor de alpha alto sin procesar
	 */
	public synchronized double getRawhalpha() {
		return rawhalpha;
	}

	/**
	 * @return el valor de beta bajo sin procesar
	 */
	public synchronized double getRawlbeta() {
		return rawlbeta;
	}

	/**
	 * @return el valor de beta alto sin procesar
	 */
	public synchronized double getRawhbeta() {
		return rawhbeta;
	}

	/**
	 * @return el valor de gamma bajo sin procesar
	 */
	public synchronized double getRawlgamma() {
		return rawlgamma;
	}

	/**
	 * @return el valor de gamm alto sin procesar
	 */
	public synchronized double getRawhgamma() {
		return rawhgamma;
	}

	/**
	 * @return el valor de delta procesado
	 */
	public synchronized double getProdelta() {
		return prodelta;
	}

	/**
	 * @return el valor de theta procesado
	 */
	public synchronized double getProtheta() {
		return protheta;
	}

	/**
	 * @return el valor de alpha bajo procesado
	 */
	public synchronized double getProlalpha() {
		return prolalpha;
	}

	/**
	 * @return el valor de alpha alto procesado
	 */
	public synchronized double getProhalpha() {
		return prohalpha;
	}

	/**
	 * @return el valor de beta bajo procesado
	 */
	public synchronized double getProlbeta() {
		return prolbeta;
	}

	/**
	 * @return el valor de beta alto procesado
	 */
	public synchronized double getProhbeta() {
		return prohbeta;
	}

	/**
	 * @return el valor de gamma bajo procesado
	 */
	public synchronized double getProlgamma() {
		return prolgamma;
	}

	/**
	 * @return el valor de gamma alto procesado
	 */
	public synchronized double getProhgamma() {
		return prohgamma;
	}

	/**
	 * @param el listener que escuchar� la nueva captura
	 */
	public void setNewCaptureListener(AndroMindListener alistener) {
		this.alistener = alistener;
	}

	public synchronized boolean isconected() {
		if  (connected)
			return true;
		else
			return false;
	}

}

	

