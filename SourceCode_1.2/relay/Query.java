package relay;
import java.net.*;
public class Query {
	private DatagramPacket packet;
    public String domainName;
    public int queryType;
    public int queryClass;
    public int queryStop;
    public boolean IPv6_FLAG = false;
    public String id;
    
	public Query(DatagramPacket p){
		this.packet = p;
		byte[] data = packet.getData();
		Tool tool = new Tool();
        String name = "";
		int i = 0;
		for(i = 12;data[i]!= 0x00;){
			int count = (int)data[i];
			//System.out.println(count); 
			
			for(int j = i+1; j<i +count + 1; j++){
				name = name + (char)data[j];
			}
			i = i + count + 1;
			if(data[i]!=0x00){
				name = name + '.';
			}
		}
		this.domainName = name;
		i++;
		int temp = (int)data[i] + (int)data[i+1];
		this.queryType = temp;
		
		i = i+2;
		temp = (int)data[i] + (int)data[i+1];
		
		this.queryStop = i + 1; // record the no.byte at which the query part stops
		this.queryClass = temp;
		
		if(this.queryType == 28){
			IPv6_FLAG = true;
		}
		//byte[] data = packet.getData();
		byte[] idByte = new byte[2];
		idByte[0] = data[0];
		idByte[1] = data[1];
		String id = tool.convertByteToString(idByte);
		this.id = id;
		//System.out.println(queryType + "The type of the query");
		//System.out.println(queryClass +"The class of the query");
	}
	
}
