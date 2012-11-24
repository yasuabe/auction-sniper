package auctionsniper.util;

public class CommandFormat {
	public static final CommandFormat JOIN = new CommandFormat("SOLVersion: 1.1; Command: JOIN;");
	public static final CommandFormat BID  = new CommandFormat("SOLVersion: 1.1; Command: BID; Price: %s;");

	private final String format;

	private CommandFormat(String format) {
		this.format = format;
	}
	public String format(Object...args) {
		return String.format(format, args);
	}
}