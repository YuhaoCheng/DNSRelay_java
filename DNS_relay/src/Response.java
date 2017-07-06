import java.net.*;
import java.util.*;
public class Response {
	private DatagramPacket in;
	private String ip;
	private boolean existFlag = true;
	public Response(DatagramPacket p, String ip){
		this.in = p;
		this.ip = ip;
	}
	public byte[] createResponse(){
		ArrayList<Byte> temp_r = new ArrayList<Byte>();
		Tool t = new Tool();
		
		byte[] inData = in.getData();
		Query q = new Query(in);
		
		int cursor = q.queryStop + 1;
		for(int i = 0; i < cursor;i++){
			temp_r.add(inData[i]);
		}
		byte[] pointer = t.hexStringToBytes("C00C");
		for(int i = 0; i< pointer.length;i++){
			temp_r.add(pointer[i]);
		}
		cursor++;
		
		byte[] domainType = t.unsignedShortToByte2((short) q.queryType);
		for(int i = 0;i<2;i++){
			temp_r.add(domainType[i]);
			cursor++;
		}
		
		byte[] domainClass = t.unsignedShortToByte2((short) q.queryClass);
		for(int i = 0;i<2;i++){
			temp_r.add(domainClass[i]);
			cursor++;
		}
		
		byte[] timeToLive = t.hexStringToBytes("00015180");
		for(int i = 0;i<4;i++){
			temp_r.add(timeToLive[i]);
			cursor++;
		}
		
		int dataLength = 4;
		if(q.queryType==28){ // AAAA Îª28 £¬IPv6
			dataLength = 16;
		}
		byte[] resourceDataLength = t.unsignedShortToByte2((short) dataLength);
		for(int i = 0; i<2; i++){
			temp_r.add(resourceDataLength[i]);
			cursor++;
		}
		
		byte[] IP = t.convertIPToByte4(ip);
		for(int i = 0; i< 4; i++){
			temp_r.add(IP[i]);
			cursor++;
		}
		
		byte[] response = new byte[temp_r.size()];
		for(int j = 0;j<response.length;j++){
			response[j] = temp_r.get(j);
		}
		
		return response;
	}
}
