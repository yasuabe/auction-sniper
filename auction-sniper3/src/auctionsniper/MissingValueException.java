package auctionsniper;

@SuppressWarnings("serial")
public class MissingValueException extends Exception {
	public MissingValueException(String fieldName) {
		super("Missing value for " + fieldName);
	}
}