import java.util.ArrayList;
import java.util.Scanner;

public class Hand {
	Scanner keyb = new Scanner(System.in);
	ArrayList<Card> hand = new ArrayList<Card>();
	Deck deck;
	Pile pile;
	String input;
	ArrayList<Card> multi = new ArrayList<Card>();

	/**
	 * Constructor: A hand is an array list of cards.
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
     * @return the card in position
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
     * @return the card in position
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
    	ArrayList<Card> newHand = new ArrayList<Card>();
        while (hand.size() > 0) {
           int position = 0;  // Position of minimal card.
           Card firstCard = hand.get(0);  // Minimal card.
           for (int i = 1; i < hand.size(); i++) {
              Card secondCard = hand.get(i);
              if (firstCard.suitToInt() > secondCard.suitToInt() 
            		  || ((firstCard.suitToInt() == secondCard.suitToInt() && firstCard.valueToInt() > secondCard.valueToInt()))) {
            	  position = i;
            	  firstCard = secondCard;
              }
           }
           hand.remove(position);
           newHand.add(firstCard);
        }
        hand = newHand;
    }

    /**
     * Sorts the cards in the hand so that cards are sorted into
     * order of increasing value.  Cards with the same value 
     * are sorted by suit. Note that aces are considered
     * to have the lowest value.
     */
    public void sortByValue() {
    	ArrayList<Card> newHand = new ArrayList<Card>();
        while (hand.size() > 0) {
           int position = 0;  // Position of minimal card.
           Card firstCard = hand.get(0);  // Minimal card.
           for (int i = 1; i < hand.size(); i++) {
              Card secondCard = hand.get(i);
              if (firstCard.valueToInt() > secondCard.valueToInt() 
            		  || ((firstCard.valueToInt() == secondCard.valueToInt() && firstCard.suitToInt() > secondCard.suitToInt()))) {
            	  position = i;
            	  firstCard = secondCard;
              }
           }
           hand.remove(position);
           newHand.add(firstCard);
        }
        hand = newHand;
    }
    
    /**
     * Plays a card from a hand
     */
    public void playCard() {
    	do {
	    	// Checks to see if input is 0 or higher
    		String chooseCard = keyb.next();
    		if (chooseCard.equalsIgnoreCase("S")) {
    			sort();
    		} else {
	    		//try {
					int position = Integer.parseInt(chooseCard) - 1;
			    	if (position == -1) {
			    		if (deck.getDeckSize() >= 0) {
				    		System.out.println("Picked up a card.");
				    		addCard(deck.removeCard());
				    		deck.addToDeck();
				    		break;
			    		} else {
			    			System.out.println("The deck is empty.");
			    		}
			    		sortBySuit();
			    	} else if (position + 1 <= getHandSize()) {

			    		
			    		// Checks what type of card it is
						if (getCard(position).value.equals("8")) {
							pile.addCard(removeCard(position));
							play8();
							deck.addToDeck();
							break;
						} else if (Main.currentSuit.equals(getCard(position).suit) || pile.getCard(pile.getPileCount()-1).value.equals(getCard(position).value)) {
							if (getCard(position).value.equals("J")) {
								discardCard(position);
								playJ(position);
							} else if (getCard(position).value.equals("2")) {
								play2();
								discardCard(position);
								break;
							} else {
								multi = checkMulti(position);
								discardCard(position);
								playMultiples(position);
								break;
							}

						} else {
							System.out.println("Invalid Command. Invalid Card");
						}
					} else {
						System.out.println("Invalid Command. Hand Overflow");
					}
	    	//	} catch (Exception e) {
	    		//	System.out.println("Invalid Command!");
	    			//System.out.println(e);
	    	//	}
    		}
    	} while (true);
	}
    
    public ArrayList<Card> checkMulti(int n){
    	ArrayList<Card> multi = new ArrayList<Card>();
    	for (int i = 0; i < hand.size(); i++) {
			if (getCard(n).value.equals(getCard(i).value) && i != n){
				multi.add(hand.remove(i));
			}
		}
    	return multi;
    }
    
    public void playMultiples(int position){
    	System.out.println("Choosing a card");
    	System.out.println(multi);
    	int num;
		do {
			if (!multi.isEmpty()){
				System.out.println("You have mutliple cards: "+ multi +"would you like to play any?\n(Y)es or (N)o");
				input = keyb.nextLine();
				if (input.equalsIgnoreCase("Y")) {
					System.out.println("which card would you like?\n"+multi);
					num = keyb.nextInt()-1;
					System.out.println(multi.get(num));
					pile.addCard(multi.get(num));
				} else if (input.equalsIgnoreCase("N")) {
					System.out.println("Not playing another card");
					break;
				} else {
					System.out.println("ERROR");
				}
			} else {
				break;
			}
		} while(input == "N" || !multi.isEmpty());
		if (!multi.isEmpty()) {
			for (int i = 0; i < multi.size(); i++) {
				hand.add(multi.get(i));
			}
		}
    }
    
    
    /**
     * Changes the suit to another when an 8 is played.
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
    
    /**
     * Makes the other player pick up 2 - 8 cards depending on the amount of 2's played in a row.
     */
    public void play2() {
    	int stack = 2;
    	try {
	    	if (pile.getCard(pile.getPileCount()-1).value.equals("2")) {
	    		stack = stack + 2;
	    		if (pile.getCard(pile.getPileCount()-2).value.equals("2")) {
	    			stack = stack + 2;
	    			if (pile.getCard(pile.getPileCount()-3).value.equals("2")) {
	    				stack = stack + 2;
	        		}
	    		}
	    	} 
    	} catch (Exception e) {
    		
    	}
		if (this.equals(Main.handOne)) {
			for (int i = 0; i < stack; i++) {
				Main.handTwo.addCard(deck.removeCard());
			}
			System.out.println("Player 2 has picked up " + stack + " cards.");
		} else {
			for (int i = 0; i < stack; i++) {
				Main.handOne.addCard(deck.removeCard());
			}
			System.out.println("Player 1 has picked up " + stack + " cards.");
		}
    }
    
    public void discardCard(int position) {
		pile.addCard(removeCard(position));
		Main.currentSuit = pile.getCard(pile.getPileCount()-1).suit;
		deck.addToDeck();
    }
    
    public void playJ(int position) {
    	System.out.println("Skipped the opponents turn.");
    	Main.printGame();
    	System.out.println(hand);
    	System.out.println("");
    	if (this.equals(Main.handOne)) {
    		System.out.println("Player 1's turn");
    	} else {
    		System.out.println("Player 2's turn");
    	}
    }
    
    public void sort() {
    	System.out.println("Sort by [S]uit");
		System.out.println("Sort by [V]alue");
		do {
			String chooseSort = keyb.next();
			if (chooseSort.equalsIgnoreCase("S")) {
				sortBySuit();
				System.out.println(hand);
				break;
			} else if (chooseSort.equalsIgnoreCase("V")) {
				sortByValue();
				System.out.println(hand);
				break;
			} else {
				System.out.println("Invalid Commad!");
			}
    	} while (true);
    }
}
