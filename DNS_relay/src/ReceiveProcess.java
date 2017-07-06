import java.io.*;
import java.net.*;
import java.util.*;
public class ReceiveProcess implements Runnable{
	private static Map<String,IDSession> IDTable = new HashMap<String,IDSession>();
	private DatagramPacket packet;
	public ReceiveProcess(DatagramPacket p,Map<String,IDSession> table){
		this.IDTable = table;
		this.packet = p;
	}
	
	public void packetReceive(){
		
	}
	public void run(){
		try{
			packetReceive();
		}catch(Exception e){
			
		}
	}
}
