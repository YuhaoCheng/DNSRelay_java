package relay;
public class DNS {
	public static void main(String[] args){
		DNSRelay relay = new DNSRelay();
		try {
			relay.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
