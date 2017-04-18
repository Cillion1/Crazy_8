import java.util.ArrayList;
import java.util.Scanner;

public class Hand {
	Scanner keyb = new Scanner(System.in);
	ArrayList<Card> hand = new ArrayList<Card>();
	Deck deck;
	Pile pile;

	/**
	 * Constructor: A hand is an arraylist of cards.
	 * @param deck the deck being used
	 * @param pile the pile being used
	 */
    public Hand(Deck deck, Pile pile) {
    	this.deck = deck;
    	this.pile = pile;
    }

    /**
     * Adds a card to the hand
     * @param c A card object
     */
    public void addCard(Card c) {
    	hand.add(c);
    }

    /**
     * Removes a card from a position in their hand
     * @param position location of where the card is
     * @return card of position
     */
    public Card removeCard(int position) {
    	return hand.remove(position);
    }

	/**
	 * Gets the size of the hand
	 * @return size of hand
	 */
    public int getHandSize() {
    	return hand.size();
    }

    /**
     * Gets a card from that position
     * @param position location of where card is
     * @return card in said position
     */
    public Card getCard(int position) {
    	return hand.get(position);
    } 
   
    /**
     * Sorts the cards in the hand so that cards of the same 
     * suit are grouped together, and within a suit the cards 
     * are sorted by value.
     */
    public void sortBySuit() {
    	
    }

    /**
     * Sorts the cards in the hand so that cards are sorted into
     * order of increasing value.  Cards with the same value 
     * are sorted by suit. Note that aces are considered
     * to have the lowest value.
     */
    public void sortByValue() {
    	
    }
    
    /**
     * Plays a card from a hand
     * @param position position of where card is
     */
    public void playCard() {
    	do {
	    	// Checks to see if input is 0 or higher
    		String chooseCard = keyb.next();
    		try {
				int position = Integer.parseInt(chooseCard) - 1;
		    	if (position == -1) {
		    		if (deck.getDeckSize() >= 0) {
			    		System.out.println("Picked up a card.");
			    		addCard(deck.removeCard());
			    		deck.returnDeck();
			    		break;
		    		} else {
		    			System.out.println("The deck is empty.");
		    		}
		    	} else if (position + 1 <= getHandSize()) {
		    		// Checks what type of card it is
					if (getCard(position).value.equals("8")) {
						pile.addCard(removeCard(position));
						play8();
						deck.returnDeck();
						break;
					} else if (Main.currentSuit.equals(getCard(position).suit) || pile.lastCard().value.equals(getCard(position).value)) {
						if (getCard(position).value.equals("2")) {
							play2();
						}
						pile.addCard(removeCard(position));
						Main.currentSuit = pile.lastCard().suit;
						deck.returnDeck();
						break;
					} else {
						System.out.println("Invalid Command. Invalid Card");
					}
				} else {
					System.out.println("Invalid Command. Hand Overflow");
				}
    		} catch (Exception e) {
    			System.out.println("Invalid Command!");
    		}
    	} while (true);
	}
    
    /**
     * Changes the suit to another.
     */
    public void play8() {
		System.out.println("Choose any suit ([S]pades, [H]eart, [C]lub, [D]iamonds).");
		do {
			String newSuit = keyb.next();
			if (newSuit.equalsIgnoreCase("S")) {
				Main.currentSuit = "♠";
				System.out.println("New suit is Spades.");
				break;
			} else if (newSuit.equalsIgnoreCase("H")) {
				Main.currentSuit = "♥";
				System.out.println("New suit is Hearts.");
				break;
			} else if (newSuit.equalsIgnoreCase("C")) {
				Main.currentSuit = "♣";
				System.out.println("New suit is Clubs.");
				break;
			} else if (newSuit.equalsIgnoreCase("D")) {
				Main.currentSuit = "♦";
				System.out.println("New suit is Diamonds.");
				break;
			} else {
				System.out.println("Please type the correct letter to continue.");
			}
		} while (true);
	}
    
    public void play2() {
    	for (int i = 0; i < 2; i++) {
    		addCard(deck.removeCard());
		}
    }
}

