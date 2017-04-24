import java.util.ArrayList;

public class Pile {
	ArrayList<Card> pile = new ArrayList<Card>();

	/**
	 * Adds a card to the pile
	 * 
	 * @param c
	 *            a card
	 */
	public void addCard(Card c) {
		pile.add(c);
	}

	/**
	 * Removes a card from the pile and returns it
	 * 
	 * @param position
	 *            the location of the card
	 * @return the card in that position
	 */
	public Card removeCard(int position) {
		return pile.remove(position);
	}

	/**
	 * Grabs the amount of cards in the pile
	 * 
	 * @return amount of cards in the pile
	 */
	public int getPileSize() {
		return pile.size();
	}

	/**
	 * Grabs a card in position
	 * 
	 * @param position
	 *            location of card
	 * @return the card in that position
	 */
	public Card getCard(int position) {
		return pile.get(position);
	}
}