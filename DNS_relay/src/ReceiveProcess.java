import java.io.*;
import java.net.*;
import java.util.*;
public class ReceiveProcess implements Runnable{
	private static Map<String,IDSession> IDTable = new HashMap<String,IDSession>();
	private DatagramPacket packet;
	private DatagramSocket socket;
	public DatagramPacket outPacket;
	public ReceiveProcess(DatagramPacket p,DatagramSocket socket,Map<String,IDSession> table){
		this.IDTable = table;
		this.packet = p;
		this.socket = socket;
	}
	
	public void packetReceive() throws Exception{
		Tool tool = new Tool();
		byte[] data = packet.getData();
		byte[] idByte = new byte[2];
		idByte[0] = data[0];
		idByte[1] = data[1];
		String id = tool.convertByteToString(idByte);
		
		
		if(IDTable.containsKey(id)){
			IDSession ids = IDTable.get(id);
			outPacket = new DatagramPacket(data,data.length,ids.getPCAddr(),ids.getPort());
			socket.send(outPacket);
		}
		
	}
	public void run(){
		try{
			packetReceive();
		}catch(Exception e){
			
		}
	}
}
