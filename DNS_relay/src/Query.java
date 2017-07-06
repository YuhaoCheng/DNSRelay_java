import java.net.*;
public class Query {
	private DatagramPacket packet;
    public String domainName;
    public int queryType;
    public int queryClass;
    public int queryStop;
    
	public Query(DatagramPacket p){
		this.packet = p;
		byte[] data = packet.getData();
        String name = "";
		int i = 0;
		for(i = 12;data[i]!= 0x00;){
			int count = (int)data[i];
			//System.out.println(count); //test
			
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
		
		this.queryStop = i + 1;
		
		this.queryClass = temp;
		System.out.println(queryType + "The type of the query");
		System.out.println(queryClass +"The class of the query");
	}
	
}
