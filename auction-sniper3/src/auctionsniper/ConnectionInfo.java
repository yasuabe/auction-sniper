package auctionsniper;

public class ConnectionInfo {
	//TODO rule 3. Wrap all primitives and Strings
	public static final int ARG_HOSTNAME = 0;
	public static final int ARG_USERNAME = 1;
	public static final int ARG_PASSWORD = 2;
	private String[] args;
	public ConnectionInfo(String[] args) {
		this.args = args;
	}
	//TODO rule 3. Wrap all primitives and Strings
	public String hostname() { return args[ARG_HOSTNAME]; }
	public String username() { return args[ARG_USERNAME]; }
	public String password() { return args[ARG_PASSWORD]; }
}