package net.thempra.overmind;

public class Eeg {

	String address; 
	String name; 
	int slotApp;
	int signal; 
	int attention;
	int meditation;
	int theta;
	int delta;
	int lalpha;
	int halpha;
	int lbeta;
	int hbeta;
	int lgamma;
	int hgamma;
	
	

	void Egg()
	{
		
		signal=0; 
		attention=0;
		meditation=0;
		theta=0;
		delta=0;
		lalpha=0;
		halpha=0;
		lbeta=0;
		hbeta=0;
		lgamma=0;
		hgamma=0;
	}

	public int getAttention() {
		return attention;
		
	}

	public int getMeditation() {
		return meditation;
		
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	public int getSlot() {
		return this.slotApp;
	}
	
	public void setAdress(String address) {
		// TODO Auto-generated method stub
		this.address=address;
	}

	public void setName(String name) {
		this.name=name;
	}

	public void setSlot(int slot) {
		this.slotApp= slot;
		
	}
}
