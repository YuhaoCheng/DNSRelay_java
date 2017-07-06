package relay;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DNSRelay {
	
	//public static final String DNS_IP = "202.106.0.20";
	//public static final int DNS_PORT = 53;
	public static final int LOCAL_PORT = 53;
	public static final int DATA_LEN = 512;// the length of the data
    byte[] buf = new byte[DATA_LEN];
    public static Map<String, String> IPTable;
    public static Map<String, IDSession> IDTable = new HashMap<String,IDSession>();
     
	public DatagramPacket inPacket = null;
	//public DatagramPacket outPacket = null;
	//public DatagramPacket forwardPacket = null;
	//public DatagramPacket backPacket = null;
	public DatagramSocket  socket = null;
	
	/**public void receive() throws Exception{
		System.out.println("Start receiving the packet!");
		inPacket = new DatagramPacket(buf, buf.length);
		socket = new DatagramSocket(LOCAL_PORT);
		while(true){
			
		}
		
	}**/
	
	public void start() throws Exception{
		System.out.println("Work!");
		inPacket = new DatagramPacket(buf, buf.length);
		socket = new DatagramSocket(LOCAL_PORT);
		Tool tool = new Tool();
		IPTable = tool.loadIPTable();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while(true){
			socket.receive(inPacket);
			
			System.out.println("The packet is received at" + df.format(new Date()));
			
			byte[] data = inPacket.getData();
			byte[] idByte = new byte[2];
			idByte[0] = data[0];
			idByte[1] = data[1];
			
			//String domainName = tool.getDomainName(data);

			IDSession item = new IDSession(inPacket);
			String id = tool.convertByteToString(idByte);
			System.out.println("The packet id is:" + id);
			
			if(!IDTable.containsKey(id)){
			   IDTable.put(id,item);
			   }
			
			if((data[2] & 0x80)==0x00){ // juge whether it is query
				System.out.println("Function: Querying" + "Line: 66 in DNSRelay" + df.format(new Date()));
				QueryProcess qp = new QueryProcess(data,socket,inPacket,IPTable);
				qp.packetHandler();
			}
			else{
				System.out.println("Function: Receiving" + "Line: 71 in DNSRelay" + df.format(new Date()));
				ReceiveProcess rp = new ReceiveProcess(inPacket,socket,IDTable);
				rp.packetReceive();
			}
			//socket.close();
			//System.out.println(domainName);
			
			
		}
		
	}
	
}
