package auctionsniper.main;

enum ArgumentIndex {
	Hostname(0),
	Username(1),
	Password(2);
	private final int index;
	ArgumentIndex(int index) { this.index = index; }
	public int index() { return index; }
}