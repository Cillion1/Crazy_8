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
    
    
    public void addCard(Card c) {
    	if (deck.size() < 52) {
    		deck.add(c);
    	}
    }
    
    public void returnDeck() {
    	if (deck.size() <= 4) {
    		for (int i = 0; i < pile.getPileCount(); i++) {
				addCard(pile.removeCard(i));
			}
    	}
    }
    
    public int getDeckSize() {
    	return deck.size()-1;
    }
}
