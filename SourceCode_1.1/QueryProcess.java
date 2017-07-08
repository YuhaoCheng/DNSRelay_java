package relay;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

public class QueryProcess{
	
	public static final String DNS_IP = "10.3.9.6";
	public static final int DNS_PORT = 53;
	private String domainName;
	private Map<String, String> IPTable;
	private byte[] data;
	private DatagramSocket socket;
	private DatagramPacket packet; // the coming packet
	private DatagramPacket outPacket; // the sending packet
	//private static final boolean IPv6_FLAG;
	
	private InetAddress resolverAddress;
	private int resolverPort;
	
	public QueryProcess(byte[] data, DatagramSocket socket, DatagramPacket in, Map<String,String> table){
		this.data = data;
		this.socket = socket;
		this.packet = in;
		this.IPTable = table;
		
	}
	
	void packetHandler() throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Tool tool = new Tool();
		
		Query q = new Query(packet); // Test the Query class
		
		if(q.IPv6_FLAG){
			System.out.println("IPv6 Packet");
			byte[] sendData = packet.getData();
			outPacket = new DatagramPacket(sendData,sendData.length, InetAddress.getByName(DNS_IP), DNS_PORT);
			socket.send(outPacket); 
			Date d = new Date();
			//System.out.println("Forward Time: " + df.format(new Date()));
			System.out.println("The query id: "+q.id + "--IPv6 packet forwards to the remote DNS server." + df.format(new Date()));
			
		}
		else{
		domainName = tool.getDomainName(data);
		String ip = ""; 
		resolverAddress = packet.getAddress();
		resolverPort = packet.getPort();
		
		if(IPTable.containsKey(domainName)){
			System.out.println(q.domainName +"--In Local" + df.format(new Date()));
			ip = IPTable.get(domainName);
			Response r = new Response(packet, ip);
			byte[] sendData = r.createResponse();
			
			if(ip.equals("0.0.0.0")){
				System.out.println(q.domainName +"--The address not exist! "+ "Line: 49" + df.format(new Date()));
				//byte[] sendData = r.createResponse();
				sendData[2] = (byte) (sendData[2] | 0x81); 
				sendData[3] = (byte) (sendData[3] | 0x83); // set the reply code to 3
				sendData[6] = (byte) (sendData[6] | 0x00); 
				sendData[7] = (byte) (sendData[7] | 0x01); 
				outPacket = new DatagramPacket(sendData, sendData.length, resolverAddress, resolverPort);
				socket.send(outPacket);
				//System.out.println("Send the shield packet Line:57 in QP " + df.format(new Date()));
			}
			else{
				System.out.println(q.domainName+"--The address exist! IP is: "+ ip +"Line: 60" + df.format(new Date()));
				//byte[] sendData = r.createResponse();
				sendData[2] = (byte) (sendData[2] | 0x81); 
				sendData[3] = (byte) (sendData[3] | 0x80); 
				sendData[6] = (byte) (sendData[6] | 0x00); 
				sendData[7] = (byte) (sendData[7] | 0x01); 
				outPacket = new DatagramPacket(sendData, sendData.length, resolverAddress, resolverPort);
				socket.send(outPacket);
				//System.out.println("Send the packet Line:68 in QP " + df.format(new Date()));
			}
		}
		else{
			System.out.println(q.domainName + "--Not in local");
			byte[] sendData = packet.getData();
			outPacket = new DatagramPacket(sendData,sendData.length, InetAddress.getByName(DNS_IP), DNS_PORT);
			socket.send(outPacket); 
			Date d = new Date();
			//System.out.println("Forward Time: " + df.format(new Date()));
			System.out.println(q.id + "--Forward to the remote DNS server" + df.format(new Date()));
			
		}
	}	
	}
	/**
	public void run(){
		try{
			packetHandler();
		}catch(Exception e){
			
		}
		
	}**/
}
