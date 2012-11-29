package auctionsniper.main;

public class ConnectionInfo {
	private String[] args;
	public ConnectionInfo(String[] args) {
		this.args = args;
	}
	//TODO rule 3. Wrap all primitives and Strings
	// これをラップしても、直後でtoString() することになるだけ。保留。
	public String hostname() { return args[ArgumentIndex.Hostname.index()]; }
	public String username() { return args[ArgumentIndex.Username.index()]; }
	public String password() { return args[ArgumentIndex.Password.index()]; }
}