import java.net.*;
public class IDSession {
	private byte[] id = new byte[2];
	private InetAddress pcAddress;
	private int pcPort;
	public IDSession(DatagramPacket p){
		byte[] data = p.getData();
		id[0] = data[0];
		id[1] = data[1];
		this.pcAddress = p.getAddress();
		this.pcPort = p.getPort();
	}
	
	public byte[] getID(){
		return id;
	}
	
	public InetAddress getPCAddr(){
		return pcAddress;
	}
	
	public int getPort(){
		return pcPort;
	}
}
