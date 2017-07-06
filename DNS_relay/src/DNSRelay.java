import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.*;

public class DNSRelay {
	
	public static final String DNS_IP = "202.106.0.20";
	public static final int DNS_PORT = 53;
	public static final int LOCAL_PORT = 53;
	public static final int DATA_LEN = 512;// the length of the data
    byte[] buf = new byte[DATA_LEN];
    public static Map<String, String> IPTable;
    public static Map<String, IDSession> IDTable;
     
	public DatagramPacket inPacket = null;
	public DatagramPacket outPacket = null;
	public DatagramPacket forwardPacket = null;
	public DatagramPacket backPacket = null;
	public DatagramSocket  socket = null;
	
	public void receive() throws Exception{
		System.out.println("Start receiving the packet!");
		inPacket = new DatagramPacket(buf, buf.length);
		socket = new DatagramSocket(LOCAL_PORT);
		while(true){
			
		}
		
	}
	
	public void give(){
		System.out.println("Start return the packet");
	}
	
	public void forward(){
		System.out.println("Start forwarding the packet");
	}
	
	public void back(){
		System.out.println("Start get the back data of local server");
	}
	
	public void start() throws Exception{
		System.out.println("Work!");
		inPacket = new DatagramPacket(buf, buf.length);
		socket = new DatagramSocket(LOCAL_PORT);
		Tool tool = new Tool();
		
		while(true){
			socket.receive(inPacket);
			byte[] data = inPacket.getData();
			byte[] idByte = new byte[2];
			idByte[0] = data[0];
			idByte[1] = data[1];
			
			//String domainName = tool.getDomainName(data);
			IPTable = tool.loadIPTable();
			IDSession item = new IDSession(inPacket);
			String id = tool.convertByteToString(idByte);
			IDTable.put(id, item);
			
			
			if((data[2] & 0x80)==0x00){ // juge whether it is query
				QueryProcess qp = new QueryProcess(data,socket,inPacket,IPTable);
				new Thread(qp).start();
			}
			else{
				ReceiveProcess rp = new ReceiveProcess(inPacket,IDTable);
				new Thread(rp).start();
			}
			//System.out.println(domainName);
		}
		
	}
	
}
