import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.omg.IOP.Encoding;

import java.net.*;
import java.util.*;
public class Test {

	public static void main(String[] args) throws Exception {
		DNSRelay relay = new DNSRelay();
		relay.start();
		//byte t = 0x10;
		//char c = (char) t;
		//System.out.println(t);
		Tool tool = new Tool();
		//tool.loadIPTable();
	    //byte[] a = tool.convertIPToByte4("10.97.10.11");
	    
 		/**ArrayList<Byte> b = new ArrayList<Byte>();
		b.add((byte) 3);
		byte[] a = new byte[b.size()];
		for(int i =0;i<a.length;i++){
			a[i] = b.get(i);
		}
		System.out.println(a.length + "The length");
		
		for(int j = 0; j<a.length;j++){
			System.out.println(a[j] + " #$" + j);
		}
		byte[] bytes = new byte[2];
		bytes[0] = (byte)80;
		bytes[1] = (byte)2;
	    String temp = tool.convertByteToString(bytes);
	    System.out.println(temp);**/
	    //System.out.println((byte)1);
		
	}

}
