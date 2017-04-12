public class Card {
	String suit, value;

	public Card (String suit, String value) {
		this.suit = suit;
		this.value = value;
	}
	
	public String toString() {
	    return "[" + value + "-" + suit + "]";
	}
}