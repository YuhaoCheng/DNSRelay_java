import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class QueryProcess implements Runnable{
	
	public static final String DNS_IP = "202.106.0.20";
	public static final int DNS_PORT = 53;
	private String domainName;
	private Map<String, String> IPTable;
	private byte[] data;
	private DatagramSocket socket;
	private DatagramPacket packet; // the coming packet
	private DatagramPacket outPacket; // the sending packet
	
	private InetAddress resolverAddress;
	private int resolverPort;
	
	public QueryProcess(byte[] data, DatagramSocket socket, DatagramPacket in, Map<String,String> table){
		this.data = data;
		this.socket = socket;
		this.packet = in;
		this.IPTable = table;
		
	}
	
	void packetHandler() throws Exception{
		Tool tool = new Tool();
		Query q = new Query(packet); // Test the Query class
		domainName = tool.getDomainName(data);
		String ip = ""; 
		resolverAddress = packet.getAddress();
		resolverPort = packet.getPort();
		if(IPTable.containsKey(domainName)){
			System.out.println("In Local");
			ip = IPTable.get(domainName);
			Response r = new Response(packet, ip);
			byte[] sendData = r.createResponse();
			if(ip.equals("0.0.0.0")){
				System.out.println("The address not exist");
				//byte[] sendData = r.createResponse();
				sendData[2] = (byte) (sendData[2] | 0x81); 
				sendData[3] = (byte) (sendData[3] | 0x83); 
				sendData[6] = (byte) (sendData[6] | 0x00); 
				sendData[7] = (byte) (sendData[7] | 0x01); 
				outPacket = new DatagramPacket(sendData, sendData.length, resolverAddress, resolverPort);
				socket.send(outPacket);
				
			}
			else{
				System.out.println(ip);
				//byte[] sendData = r.createResponse();
				sendData[2] = (byte) (sendData[2] | 0x81); 
				sendData[3] = (byte) (sendData[3] | 0x80); 
				sendData[6] = (byte) (sendData[6] | 0x00); 
				sendData[7] = (byte) (sendData[7] | 0x01); 
				outPacket = new DatagramPacket(sendData, sendData.length, resolverAddress, resolverPort);
				socket.send(outPacket);
			}
		}
		else{
			System.out.println("Not in local");
			byte[] sendData = packet.getData();
			outPacket = new DatagramPacket(sendData,sendData.length, InetAddress.getByName(DNS_IP), DNS_PORT);
			socket.send(outPacket); 
			Date d = new Date();
			System.out.println("Forward Time: " + d.getTime());
			System.out.println("Function£º" + "Forward to the remote DNS server");
			
		}
	}

	public void run(){
		try{
			packetHandler();
		}catch(Exception e){
			
		}
		
	}
}
