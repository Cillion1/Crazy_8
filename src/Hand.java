import java.util.ArrayList;
import java.util.Scanner;

public class Hand {
	Scanner keyb = new Scanner(System.in);
	ArrayList<Card> hand = new ArrayList<Card>();
	Deck deck;
	Pile pile;
	String holdSuit;

	/**
     * Constructor. Create a Hand object that is initially empty.
     */
    public Hand(Deck deck, Pile pile) {
    	this.deck = deck;
    	this.pile = pile;
    }

    /**
     * Add the card c to the hand.  c should be non-null.
     * @throws NullPointerException if c is null.
     */
    public void addCard(Card c) {
    	hand.add(c);
    }

    /**
     * If the specified card is in the hand, it is removed.
     */
    public void removeCard(Card c) {
    	hand.remove(c);
    }

    /**
     * Remove the card in the specified position from the
     * hand.  Cards are numbered counting from zero.
     * @throws IllegalArgumentException if the specified 
     *    position does not exist in the hand.
     */
    public Card removeCard(int position) {
    	return hand.remove(position);
    }

    /**
     * Return the number of cards in the hand.
     */
    public int getHandSize() {
    	return hand.size();
    }

    /**
     * Get the card from the hand in given position, where 
     * positions are numbered starting from 0.
     * @throws IllegalArgumentException if the specified 
     *    position does not exist in the hand.
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
    
    public void playCard(int index) {
    	if (index < 0) {
    		addCard(deck.removeCard());
    	} else if (index + 1 <= getHandSize()) {
			if (getCard(index).value.equals("8")) {
				discardCard(index);
				play8(index);
			} else if (pile.lastCard().suit.equals(getCard(index).suit) || pile.lastCard().value.equals(getCard(index).value)) {
				System.out.println("It works");
				discardCard(index);
				holdSuit = pile.lastCard().suit;
				System.out.println(holdSuit);
			} else {
				System.out.println("Invalid Command. Invalid Card");
			}
		} else {
			System.out.println("Invalid Command. Hand Overflow");
		}
	}
    
    public void discardCard(int index) {
    	pile.addCard(removeCard(index));
	}
    
    public void play8(int index) {
		System.out.println("Choose any suit ([S]pades, [H]eart, [C]lub, [D]iamonds).");
		do {
			String newSuit = keyb.next();
			if (newSuit.equalsIgnoreCase("S")) {
				holdSuit = "♠";
				System.out.println("New suit is Spades.");
				break;
			} else if (newSuit.equalsIgnoreCase("H")) {
				holdSuit = "♥";
				System.out.println("New suit is Hearts.");
				break;
			} else if (newSuit.equalsIgnoreCase("C")) {
				holdSuit = "♣";
				System.out.println("New suit is Clubs.");
				break;
			} else if (newSuit.equalsIgnoreCase("D")) {
				holdSuit = "♦";
				System.out.println("New suit is Diamonds.");
				break;
			} else {
				System.out.println("Please type the correct letter to continue.");
			}
		} while (true);
	}
    
    public void pickUpCard() {
		addCard(deck.removeCard());
    }
}