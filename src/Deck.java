import java.util.ArrayList;

public class Deck {
	static ArrayList<Card> deck = new ArrayList<Card>();
	String[] suits = {"♠", "♥", "♦", "♣"};
    String[] value = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    Pile pile;
    
    Deck(Pile pile) {
    	for (int i = 0; i < suits.length; i++) {
    		for (int j = 0; j < value.length; j++) {
    			deck.add(new Card(suits[i], value[j]));				
			}
		}
    	this.pile = pile;
    }
    
    /**
     * Draws a random card from the deck and removes the card from the deck
     * @return a random card from the deck
     */
    public Card removeCard() {
    	if (deck.size() == 0) {
    		return null;
    	} else {
    		int rng = (int) (Math.random() * (deck.size()-1));
    		Card temp = deck.get(rng);
    		deck.remove(rng);
    		return temp;
    	}
    }
    
    /**
     * Adds a card to the deck
     * @param c a card
     */
    public void addCard(Card c) {
    	if (deck.size() < 52) {
    		deck.add(c);
    	}
    }
    
    /**
     * Add cards from the pile to the deck if the deck is low on cards.
     */
    public void addToDeck() {
    	if (deck.size() < 20 && pile.getPileCount()-1 >= 9) {
    		do {
				addCard(pile.removeCard(0));
    		} while (pile.getPileCount()-1 >= 32);
    	}
    }
    
    public int getDeckSize() {
    	return deck.size()-1;
    }
}
