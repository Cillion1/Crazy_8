import java.util.ArrayList;
import java.util.Scanner;

/**
 * Description: Defines a hand as an arraylist of cards. The user can play from
 * the hand
 * 
 * @author Dennis Situ 
 * Last Updated: April 24, 2017
 */

public class Hand {
	Scanner keyb = new Scanner(System.in);
	ArrayList<Card> hand = new ArrayList<Card>();
	ArrayList<Card> multi = new ArrayList<Card>();
	Game game;

	/**
	 * Connects the Hand class to the Game class
	 * 
	 * @param game
	 *            the game
	 */
	public Hand(Game game) {
		this.game = game;
	}

	/**
	 * Adds a card to the hand
	 * 
	 * @param c
	 *            A card object
	 */
	public void addCard(Card c) {
		hand.add(c);
	}

	/**
	 * Removes a card from a position in their hand
	 * 
	 * @param position
	 *            location of where the card is
	 * @return the card in position
	 */
	public Card removeCard(int position) {
		return hand.remove(position);
	}

	/**
	 * Gets the size of the hand
	 * 
	 * @return size of hand
	 */
	public int getHandSize() {
		return hand.size();
	}

	/**
	 * Gets a card from that position
	 * 
	 * @param position
	 *            location of where card is
	 * @return the card in position
	 */
	public Card getCard(int position) {
		return hand.get(position);
	}

	/**
	 * Sorts the cards in the hand so that cards of the same suit are grouped
	 * together, and within a suit the cards are sorted by value.
	 */
	public void sortBySuit() {
		ArrayList<Card> newHand = new ArrayList<Card>();
		while (hand.size() > 0) {
			int position = 0; // Position of minimal card.
			Card firstCard = hand.get(0); // Minimal card.
			for (int i = 1; i < hand.size(); i++) {
				Card secondCard = hand.get(i);
				if (firstCard.suitToInt() > secondCard.suitToInt() || ((firstCard.suitToInt() == secondCard.suitToInt()
						&& firstCard.valueToInt() > secondCard.valueToInt()))) {
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
	 * Sorts the cards in the hand so that cards are sorted into order of
	 * increasing value. Cards with the same value are sorted by suit. Note that
	 * aces are considered to have the lowest value.
	 */
	public void sortByValue() {
		ArrayList<Card> newHand = new ArrayList<Card>();
		while (hand.size() > 0) {
			int position = 0; // Position of minimal card.
			Card firstCard = hand.get(0); // Minimal card.
			for (int i = 1; i < hand.size(); i++) {
				Card secondCard = hand.get(i);
				if (firstCard.valueToInt() > secondCard.valueToInt()
						|| ((firstCard.valueToInt() == secondCard.valueToInt()
								&& firstCard.suitToInt() > secondCard.suitToInt()))) {
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
	 * Plays a card from the players hand. Check input of string or number and
	 * parse it into an integer. Checks the matching suit from the card played
	 * to the last card in the pile and throw the card in the pile if they
	 * match.
	 */
	public void playCard() {
		do {
			// Checks to see if input is 0 or higher
			game.input = keyb.next();
			if (game.input.equalsIgnoreCase("S")) {
				sort();
			} else {
				// try {
				int position = Integer.parseInt(game.input) - 1;
				if (position == -1) {
					if (game.deck.getDeckSize() - 1 >= 0) {
						pickUpCard();
						break;
					} else {
						System.out.println("The deck is empty.");
					}
				} else if (position + 1 <= getHandSize()) {
					multi = checkForMultipleCards(position);
					// Checks what type of card it is
					if (getCard(position).value.equals("8")) {
						game.pile.addCard(removeCard(position));
						if (!hand.isEmpty()) {
							playMultiplesCards();
							play8();
							game.deck.addToDeck();
						}
						break;
					} else if (game.currentSuit.equals(getCard(position).suit)
							|| game.pile.getCard(game.pile.getPileSize() - 1).value.equals(getCard(position).value)) {
						if (getCard(position).value.equals("J")) {
							discardCard(position);
							playMultiplesCards();
							playJ(position);
							if (hand.size() == 0) {
								break;
							}
						} else if (getCard(position).value.equals("2")) {
							discardCard(position);
							if (!hand.isEmpty()) {
								playMultiplesCards();
								play2();
							}
							break;
						} else {
							discardCard(position);
							playMultiplesCards();
							break;
						}

					} else {
						System.out.println("Invalid Command. Invalid Card");
					}
				} else {
					System.out.println("Invalid Command. Hand Overflow");
				}
				// } catch (Exception e) {
				// System.out.println("Invalid Command!");
				// System.out.println(e);
				// }
			}
		} while (true);
	}

	/**
	 * Creates another hand with only duplicates card values of the card played
	 * 
	 * @param n
	 *            location of where card is in the players hand
	 * @return a hand with only duplicate values
	 */
	public ArrayList<Card> checkForMultipleCards(int n) {
		ArrayList<Card> multiHand = new ArrayList<Card>();
		for (int i = 0; i < hand.size(); i++) {
			if (getCard(n).value.equals(getCard(i).value) && i != n) {
				multiHand.add(hand.get(i));
			}
		}
		return multiHand;
	}

	/**
	 * Picks up a card from the deck to the hand.
	 */
	public void pickUpCard() {
		if (this.equals(game.handOne)) {
			System.out.println("Player 1 has picked up a card.");
		} else {
			System.out.println("Player 2 has picked up a card.");
		}
		addCard(game.deck.removeCard());
		game.deck.addToDeck();
	}

	/**
	 * Checks to see if the multi hand is not empty and allow the user to choose
	 * to play another card if values are the same. Removes the card from both
	 * the multi hand and the original hand if played. Any card in the multi
	 * hand then gets thrown back into the original hand
	 */
	public void playMultiplesCards() {
		do {
			if (!multi.isEmpty()) {
				System.out.println("You have multiple cards! Would you like to play any?\n\n(Y)es or (N)o");
				game.input = keyb.next();
				if (game.input.equalsIgnoreCase("Y")) {
					System.out.println("Which card would you like?\n\n" + multi);
					do {
						game.input = keyb.next();
						try {
							int num = Integer.parseInt(game.input) - 1;
							for (int i = 0; i < hand.size(); i++) {
								if (multi.get(num).value.equals(hand.get(i).value)
										&& multi.get(num).suit.equals(hand.get(i).suit)) {
									hand.remove(i);
									break;
								}
							}
							game.pile.addCard(multi.get(num));
							game.currentSuit = game.pile.getCard(game.pile.getPileSize() - 1).suit;
							multi.remove(num);
							break;
						} catch (Exception e) {
							System.out.println("Invalid Command");
						}
					} while (true);
				} else if (game.input.equalsIgnoreCase("N")) {
					multi.clear();
					break;
				} else {
					System.out.println("Invalid Command!");
				}
			} else {
				break;
			}
		} while (true);
		for (int i = 0; i < multi.size(); i++) {
			hand.add(multi.get(i));
		}
	}

	/**
	 * Changes the suit to another when an 8 is played.
	 */
	public void play8() {
		System.out.println("Choose any suit ([S]pades, [H]eart, [C]lub, [D]iamonds).");
		do {
			game.input = keyb.next();
			if (game.input.equalsIgnoreCase("S")) {
				game.currentSuit = "♠";
				System.out.println("New suit is Spades.\n");
				break;
			} else if (game.input.equalsIgnoreCase("H")) {
				game.currentSuit = "♥";
				System.out.println("New suit is Hearts.\n");
				break;
			} else if (game.input.equalsIgnoreCase("C")) {
				game.currentSuit = "♣";
				System.out.println("New suit is Clubs.\n");
				break;
			} else if (game.input.equalsIgnoreCase("D")) {
				game.currentSuit = "♦";
				System.out.println("New suit is Diamonds.\n");
				break;
			} else {
				System.out.println("Please type the correct letter to continue.");
			}
		} while (true);
	}

	/**
	 * Makes the other player pick up 2 - 8 cards depending on the amount of 2's
	 * played in a row.
	 */
	public void play2() {
		int count = 0;
		int stack = 2;
		try {
			if (game.pile.getCard(game.pile.getPileSize() - 2).value.equals("2")) {
				stack = stack + 2;
				if (game.pile.getCard(game.pile.getPileSize() - 3).value.equals("2")) {
					stack = stack + 2;
					if (game.pile.getCard(game.pile.getPileSize() - 4).value.equals("2")) {
						stack = stack + 2;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("");
		}
		if (this.equals(game.handOne)) {
			for (int i = 0; i < stack; i++) {
				if (game.deck.getDeckSize() - 1 <= 0) {
					break;
				}
				game.handTwo.addCard(game.deck.removeCard());
				count++;
			}
			System.out.println("Player 2 has picked up " + count + " cards.");
		} else {
			for (int i = 0; i < stack; i++) {
				if (game.deck.getDeckSize() - 1 <= 0) {
					break;
				}
				game.handOne.addCard(game.deck.removeCard());
				count++;
			}
			System.out.println("Player 1 has picked up " + count + " cards.");
		}
	}

	/**
	 * Discards the card from the hand and changes the suit from the discard
	 * card to the new one. Reshuffle pile back to deck if the deck is low on
	 * count.
	 * 
	 * @param position
	 *            location of card in hand
	 */
	public void discardCard(int position) {
		game.pile.addCard(removeCard(position));
		game.currentSuit = game.pile.getCard(game.pile.getPileSize() - 1).suit;
		game.deck.addToDeck();
	}

	/**
	 * Skips the other players turn and gives another play on a card.
	 * 
	 * @param position
	 *            location of card in hand
	 */
	public void playJ(int position) {
		if (!hand.isEmpty()) {
			System.out.println("Skipped the opponents turn.\n");
			game.print.gameInterface();
			System.out.println(hand + "\n");
			if (this.equals(game.handOne)) {
				System.out.println("Player 1's turn");
			} else {
				System.out.println("Player 2's turn");
			}
		}
	}

	/**
	 * Sorts the hand by value or suit.
	 */
	public void sort() {
		System.out.println("Sort by [S]uit");
		System.out.println("Sort by [V]alue");
		do {
			game.input = keyb.next();
			if (game.input.equalsIgnoreCase("S")) {
				sortBySuit();
				System.out.println(hand);
				break;
			} else if (game.input.equalsIgnoreCase("V")) {
				sortByValue();
				System.out.println(hand);
				break;
			} else {
				System.out.println("Invalid Commad!");
			}
		} while (true);
	}
}