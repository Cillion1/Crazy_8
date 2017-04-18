public class Card {
	String suit, value;
	
	/**
	 * Constructor: A card has a suit and a value
	 * @param suit the suit of the card
	 * @param value the value of the card
	 */
	public Card (String suit, String value) {
		this.suit = suit;
		this.value = value;
	}
	
	/**
	 * Returns the value and suit as a card.
	 */
	public String toString() {
	    return "[" + value + "-" + suit + "]";
	}
}