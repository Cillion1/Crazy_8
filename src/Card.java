/**
 * Description: Defines what a card is and converts the corresponding values or
 * suit into integers
 * 
 * @author Dennis Situ Last Updated: May 4, 2017
 */

public class Card {
	String suit, value;

	/**
	 * Defines a card with a suit and a value.
	 * 
	 * @param suit
	 *            the suit of the card
	 * @param value
	 *            the value of the card
	 */
	public Card(String suit, String value) {
		this.suit = suit;
		this.value = value;
	}

	/**
	 * Assigns a suit to a value
	 * 
	 * @return int the number that suit corresponds
	 */
	public int suitToInt() {
		switch (suit) {
		case "♦":
			return 1;
		case "♣":
			return 2;
		case "♥":
			return 3;
		case "♠":
			return 4;
		default:
			return 0;
		}
	}

	/**
	 * Assigns a value to a number
	 * 
	 * @return int the number that value corresponds
	 */
	public int valueToInt() {
		switch (value) {
		case "A":
			return 1;
		case "2":
			return 2;
		case "3":
			return 3;
		case "4":
			return 4;
		case "5":
			return 5;
		case "6":
			return 6;
		case "7":
			return 7;
		case "8":
			return 8;
		case "9":
			return 9;
		case "10":
			return 10;
		case "J":
			return 11;
		case "Q":
			return 12;
		case "K":
			return 13;
		default:
			return 0;
		}
	}

	/**
	 * Returns the value and suit as a card.
	 */
	public String toString() {
		return "[" + value + "-" + suit + "]";
	}
}