package net.thempra.overmind;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class CommunicationManager {
        
        public final static int OK = 0;
        private final static int BAD_DATA = -1;
        private final static int IOERROR = -2;
        private final static int UNSUCCESSFUL = -3;
        
        private BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        private BluetoothDevice device = null;
        private BluetoothSocket socket = null;
        
        private InputStream ins = null;
        private OutputStream outs = null;
        
        private boolean debugging = true;
        
        public boolean hasAdapter(){
                
                /*
                 * Purpose: Allows other classes to learn whether this device has bluetooth
                 * Input: Nothing
                 * Output: T/F to whether there is bluetooth on the device
                 */
                
                if(adapter!=null) return true;
                else return false;
        }
        
        public boolean hasBluetoothOn(){
                
                /*
                 * Purpose: Allows other classes to learn if bluetooth is on
                 * Input: Nothing
                 * Output: T/F Whether bluetooth is turned on
                 */
        
                return adapter.isEnabled();
        }
        
  /*      public int initialize_bluetooth (){
        
  
        // Looks only at the successfully paired devices
        
                Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
                Iterator<BluetoothDevice> i = pairedDevices.iterator();
                while(i.hasNext()){
                        device = i.next();
                        
                        // Finds the device named obddevice
                        
                        if(device.getName().equalsIgnoreCase("ThempraEEG\n")){ 
                                int error = connect(device);
                                return error;
                        }
                }       
                return UNSUCCESSFUL ;
    }   
    */    
        public int get_num_devices (){
            
            /*
             * Purpose: Initialize set up with the Bluetooth Device
             * Input: Nothing
             * Output: Error Codes  
             */
            
            // Looks only at the successfully paired devices
            
                    Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
                    return pairedDevices.size();

        }   
       public CharSequence[] get_devices (){
            
            /*
             * Purpose: Initialize set up with the Bluetooth Device
             * Input: Nothing
             * Output: Error Codes  
             */
            
//                  if(debugging) log("initBluetooth()");
            
            // Looks only at the successfully paired devices
            
                    Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
                    Iterator<BluetoothDevice> i = pairedDevices.iterator();
                	CharSequence[] arrayOfCharSequence= new CharSequence[pairedDevices.size()];
                	
                	int j=0;
                	while(i.hasNext()){
                         device = i.next();
                	
                           
                            // Finds the device named obddevice
                            arrayOfCharSequence[j] = (device.getName() + "\n" + device.getAddress());
                            j++;
                	 }
                     
                    return arrayOfCharSequence ;
        }   
        
    int connect(int item){
    	Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        Iterator<BluetoothDevice> i = pairedDevices.iterator();
    	
    	int j=0;
    	while(i.hasNext()){
             device = i.next();
	    	if (j== item)
	    		return connect(device);       
	        j++;
    	 }
    	
    	return UNSUCCESSFUL;
       }
    
    private int connect(BluetoothDevice device){ 
        
                /*
                 * Purpose: Connect to a Bluetooth Device
                 * Input: Device to connect to
                 * Output: Error Codes
                 */
                
                //if(debugging) log("connect()");
                
                try{
                        
                        //socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        
                        Method m;
                        m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                        socket = (BluetoothSocket) m.invoke(device, Integer.valueOf(1));
                        socket.connect();
                }
                catch(Exception e){
                        log("Error - connect(): " + e.getLocalizedMessage());
                        //connection_state = PREVIOUSLY_INITIALIZED;
                        return UNSUCCESSFUL;
                }
                return OK;
        }
        
        public int initialize_communication(){
                
                /*
                 * Purpose: Initialize communcation protocols to communicate with bluetooth
                 * Input: Nothing
                 * Output: Returns whether or not the initialization was successful
                 */
                
                try{
                        ins = socket.getInputStream();
                        outs = socket.getOutputStream();
                }
                catch(Exception e){
                        ins = null;
                        outs = null;
                }
                if(ins!=null && outs!=null) return OK;
                return UNSUCCESSFUL;
        }
    
        public boolean wait_for_connection(){
            
            /*
             * Purpose: Wait for a connection to be established
                 * Input: context - Allows the function to print to the GUI while waiting
                 * Output: Nothing
                 */
        
                if(debugging) log("wait_for_connection");
        boolean answer = false;
                int response = 1;
                response = listen();
                switch(response){
                        case OK:
                                answer = true;
                                break;
                        case BAD_DATA:
                                log("Error - waitforconnection(): BAD_DATA");
                                break;
                        case IOERROR:
                                log("Error - waitforconnection(): IOERROR");
                                break;
                }
        return answer;
        }
        
        private int listen(){
                
                /*
                 * Purpose: Listen for data from the Bluetooth Bee
                 * Input: Nothing
                 * Output: Error Codes
                 */
                
                if(debugging) log("listen()");
                
                char[] cmpbuff = {'\r','\n','1','\r','\n'};
                char[] buffer = new char[5];
                int i;
                try{
                                
                        // Wait for a carriage return
                        
                        while((char)ins.read() != '\r');
                        buffer[0] = '\r';
                        
                        // Store data into buffer
                        
                        for(i=1;i<(buffer.length);i++){
                                buffer[i] = (char)ins.read();
                                //log("Buffer[i]: " + buffer[i]);
                        }
                }
                catch(Exception e){
                        log("Error - listen(): " + e.getLocalizedMessage());
                        return IOERROR; 
                }
                if(Arrays.equals(cmpbuff, buffer)){ // Send a message back so that the avr knows a connection has been made
                        char tmp[] = {'\r', '\n', '2', '\r', '\n'};
                        int resp = write(tmp);
                        return resp;
                }
                else return BAD_DATA;
        }
        
		public Eeg geteeg(){
        	Eeg currentEeg = new Eeg();
        	
        	 try {
        		 //currentEeg.signal = Integer.toHexString(ins.read());
        		 currentEeg.signal =ins.read();
        		 
         } catch (Exception e) {
                 log("Error in getmph()");
         }
			return currentEeg;
        }
        public int getmph(){
        
            /*
             * Purpose: Get mph value from the vehicle
             * Input: Nothing
             * Output: Characters from the car - note passes on read's error to main code       
             */
                
                if(debugging) log("getmph()");
                int realmph = -1;
                try {
                        realmph = ins.read();
                } catch (Exception e) {
                        log("Error in getmph()");
                }
        return realmph;
        }
           
    public int getrpm(){
    
                /*
                 *      Purpose: Get rpm value from the vehicle
                 *      Input: Nothing
                 *      Output: Characters from the car - note passes on read's error to main code
                 */
        
                if(debugging) log("getrpm()");
                int realrpm = -1;
                try{
                        realrpm = ins.read();
                        realrpm = realrpm<<8;
                        realrpm = realrpm+ins.read();
                } catch (Exception e) {
                        log("Error in getrpm");
                }
                return realrpm;
                
    }  
        
        public int write(char buffer[]){
        
                /*
                 * Purpose: Write data to the Bluetooth Bee
                 * Input: Data to be sent
                 * Output: Error Code
                 */
                
                if(debugging) log("write()");
        
                int size = buffer.length;
                for(int i=0;i<size;i++){
                        try {
                                outs.write((int)buffer[i]);
                        } catch (Exception e) {
                                log("Error - Write(): " + e.getLocalizedMessage());
                                return IOERROR;
                        }
                }
                return OK;
        }
        
        public void close(){
                
                /*
                 * Purpose: Close ioports/socket
                 * Input: Nothing
                 * Output: Nothing
                 */
                
                if(debugging) log("Close()");
                
                try{
                        if(ins != null) ins.close();
                        if(outs != null) outs.close();
                        if(socket != null) socket.close();
                        
                }
                catch(Exception e){
                        log("Error - close(): " + e.getLocalizedMessage());
                }
        }       
                
        public void bluetooth_off(){
                   
            /*
             * Purpose: Turn Bluetooth Off
             * Input: Nothing
             * Output: Nothing  
             */
        
                try{
                        close();
                        adapter.disable();
                }
                catch(Exception e){
                        log("Error - bluetooth_off: " + e.getLocalizedMessage());
                }
    }
    
    public void bluetooth_on(){
    
            /*
             * Purpose: Turn Bluetooth On
             * Input: Nothing
             * Output: Nothing
             */
        
                if(debugging) log("bluetooth_on()");
        
                try{
                        adapter.enable();
                }
                catch(Exception e){
                        log("Error - bluetooth_on(): " + e.getLocalizedMessage());
                }
    }
    public void acknowledge(){
        
        /*
         * Purpose: Acknowledge to bluetooth device that the connection is still made
         * Input: Nothing
         * Output: Nothing
         */
        
        char[] buffer = {'1'};
        int error = write(buffer);
        if(error != OK) close();
        
    }
    
    private void log(String str){
                
                /*
                 * Purpose: Log data for debugging purposes
                 * Input: String to be output to the debugging location
                 * Output: Outputs data into a log
                 */
                
                Log.e("$$$$$$$$$$", str);
        }
        
}