import java.util.*;
import java.util.zip.GZIPInputStream;
import java.io.*;
public class Tool {
	public static Map<String,String> IPTable = new HashMap<String,String>(); //Map<domian,ip>
	
	public Map loadIPTable() throws Exception{
	   File f = new File("dnsrelay.txt");
	   Scanner input = new Scanner(f);
		while(input.hasNext()){
			String line  = input.nextLine();
			int index = line.indexOf(" ");
			String ip = line.substring(0, index).trim();
			String domainName = line.substring(index+1);
			IPTable.put(domainName,ip);
			//System.out.println(IPTable.get(domainName) + "The IP");
			//System.out.println(ip);
			//System.out.println(domainName);
			//System.out.println(index);
			//System.out.println(line);
		}
	   return IPTable;
	}
	
	public String getDomainName(byte[] data){
		String name = "";
		
		for(int i = 12;data[i]!= 0x00;){
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
		
		return name;
	}
	
	public byte[] convertIPToByte4(String ip) {  
		
		byte[] IPBytes = new byte[4]; 
		int pos1 = ip.indexOf('.'); 
		int pos2 = ip.indexOf('.', pos1 + 1);  
		int pos3 = ip.indexOf('.', pos2 + 1);   
		IPBytes[0] = (byte) Integer.parseInt(ip.substring(0, pos1)); 
		IPBytes[1] = (byte) Integer.parseInt(ip.substring(pos1 + 1, pos2));  
		IPBytes[2] = (byte) Integer.parseInt(ip.substring(pos2 + 1, pos3)); 
		IPBytes[3] = (byte) Integer.parseInt(ip.substring(pos3 + 1)); 
		return IPBytes;  
		}
	
	public  byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	
	private byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  
	 
	public  byte[] unsignedShortToByte2(int s) {  
        byte[] targets = new byte[2];  
        targets[0] = (byte) (s >> 8 & 0xFF);  
        targets[1] = (byte) (s & 0xFF);  
        return targets;  
    }  
	
	public String convertByteToString(byte[] bytes){
		String temp = "";
		
		for(int i = 0; i <bytes.length; i++){
			temp = temp + Byte.toString(bytes[i]);
		}
		return temp;
		
	}
	 
}
