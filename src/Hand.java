import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all actions related to a (player's) hand
 * 
 * @author Dennis Situ Last Updated: May 4, 2017
 */
public class Hand {
	Scanner keyb = new Scanner(System.in);
	ArrayList<Card> hand = new ArrayList<Card>();
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
	 * @return Card the card in position
	 */
	public Card removeCard(int position) {
		return hand.remove(position);
	}

	/**
	 * Gets the size of the hand
	 * 
	 * @return int the size of hand
	 */
	public int getHandSize() {
		return hand.size();
	}

	/**
	 * Gets a card from that position
	 * 
	 * @param position
	 *            location of where card is
	 * @return Card the card in position
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
			int position = 0; // Position of the first card.
			Card firstCard = hand.get(0);
			for (int i = 1; i < hand.size(); i++) {
				Card secondCard = hand.get(i); // Position of the second card.
				// Checks to see if first card value is bigger than second cards
				// value
				if (firstCard.suitToInt() > secondCard.suitToInt()
						|| ((firstCard.suitToInt() == secondCard.suitToInt() && firstCard
								.valueToInt() > secondCard.valueToInt()))) {
					position = i; // Assign
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
			int position = 0; // Position of the first card.
			Card firstCard = hand.get(0);
			for (int i = 1; i < hand.size(); i++) {
				Card secondCard = hand.get(i); // Position of the second card.
				// Checks to see if first card suit is bigger than second cards
				// suit
				if (firstCard.valueToInt() > secondCard.valueToInt()
						|| ((firstCard.valueToInt() == secondCard.valueToInt() && firstCard
								.suitToInt() > secondCard.suitToInt()))) {
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
			// The input that the user types in
			game.input = keyb.next();
			if (game.input.equalsIgnoreCase("S")) {
				sort();
			} else if (Character.isDigit(game.input.charAt(0))) {
				int position = Integer.parseInt(game.input) - 1;
				if (position == -1) {
					if (game.deck.getDeckSize() - 1 >= 0) {
						pickUpCard();
						break;
					} else {
						System.out.println("The deck is empty.");
					}
				} else if (position + 1 <= getHandSize()) {
					ArrayList<Card> multi = checkForMultipleCards(position);
					// Checks what type of card it is
					if (getCard(position).value.equals("8")) {
						game.pile.addCard(removeCard(position));
						if (!hand.isEmpty()) {
							playMultiplesCards(multi);
							play8();
							game.deck.reInitialize();
						}
						break;
						// Checks for matching suit or values.
					} else if (game.currentSuit.equals(getCard(position).suit)
							|| game.pile.getCard(game.pile.getPileSize() - 1).value
									.equals(getCard(position).value)) {
						// If one of the cards values match one of these values,
						// do extra effects, otherwise, discard the card and
						// check the hand for any multiple cards with the same
						// value and break.
						if (getCard(position).value.equals("J")) {
							discardCard(position);
							playMultiplesCards(multi);
							playJ();
							if (hand.size() == 0) {
								break;
							}
						} else if (getCard(position).value.equals("2")) {
							discardCard(position);
							if (!hand.isEmpty()) {
								playMultiplesCards(multi);
								play2();
							}
							break;
						} else {
							discardCard(position);
							playMultiplesCards(multi);
							break;
						}

					} else {
						System.out.println("Invalid Command. Invalid Card");
					}
				} else {
					System.out.println("Invalid Command. Hand Overflow");
				}
			} else {
				System.out.println("Invalid Command!");
			}
		} while (true);
	}

	/**
	 * Creates another hand with only duplicates card values of the card played
	 * 
	 * @param n
	 *            location of where card is in the players hand
	 * @return ArrayList<Card> a hand with only cards of duplicate values.
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
	 * Plays the card that the player picked up
	 */
	public void playPickedUpCard() {
		ArrayList<Card> multi = checkForMultipleCards(getHandSize() - 1);
		System.out.println("You have picked up " + getCard(getHandSize() - 1)
				+ ", would you like to play it? (Y/N)");
		do {
			game.input = keyb.next();
			if (game.input.equalsIgnoreCase("y")) {
				discardCard(getHandSize() - 1);
				playMultiplesCards(multi);
				if (game.pile.getCard(game.pile.getPileSize() - 1).value
						.equals("8")) {
					play8();
				} else if (game.pile.getCard(game.pile.getPileSize() - 1).value
						.equals("2")) {
					play2();
				} else if (game.pile.getCard(game.pile.getPileSize() - 1).value
						.equals("J")) {
					playJ();
					playCard();
				}
				break;
			} else if (game.input.equalsIgnoreCase("n")) {
				break;
			} else {
				System.out.println("Invalid Command!");
			}
		} while (true);
	}

	/**
	 * Picks up a card from the deck to the hand. Plays the card that was picked
	 * into the pile if the card is valid.
	 */
	public void pickUpCard() {
		addCard(game.deck.removeCard());
		if (game.currentSuit.equals(getCard(getHandSize() - 1).suit)
				|| game.pile.getCard(game.pile.getPileSize() - 1).value
						.equals(getCard(getHandSize() - 1).value)
				|| getCard(getHandSize() - 1).value.equals("8")) {
			playPickedUpCard();
		} else if (this.equals(game.handOne)) {
			System.out.println("Player 1 has picked up a card.");
		} else {
			System.out.println("Player 2 has picked up a card.");
		}
		game.deck.reInitialize();
	}

	/**
	 * Checks to see if the multi hand is not empty and allow the user to choose
	 * to play another card if values are the same. Removes the card from both
	 * the multi hand and the original hand if played. Any card in the multi
	 * hand then gets thrown back into the original hand
	 */
	public void playMultiplesCards(ArrayList<Card> multi) {
		do {
			// Only play when when the multi hand has duplicate values.
			if (!multi.isEmpty()) {
				System.out
						.println("You have multiple cards! Would you like to play any?\n\n(Y)es or (N)o");
				game.input = keyb.next();
				if (game.input.equalsIgnoreCase("Y")) {
					System.out.println("Which card would you like to play?");
					// Prints out the hand
					for (int i = 1; i < multi.size() + 1; i++) {
						if (multi.get(i - 1).value.equals("10")) {
							System.out.print(" ");
						}
						if (i <= 10) {
							System.out.print("   " + i + "   ");
						} else if (i > 10) {
							System.out.print("  " + i + "   ");
						}
					}
					System.out.println("");
					System.out.println(multi);
					do {
						game.input = keyb.next();
						// Only accepts integers
						if (Character.isDigit(game.input.charAt(0))) {
							int num = Integer.parseInt(game.input) - 1;
							if (num + 1 <= multi.size()) {
								// Loops through the original hand to find the
								// same cards and remove it from the hand.
								for (int i = 0; i < hand.size(); i++) {
									if (multi.get(num).value
											.equals(hand.get(i).value)
											&& multi.get(num).suit.equals(hand
													.get(i).suit)) {
										hand.remove(i);
										break;
									}
								}
								// Add the card from the multi hand into the
								// pile and update the current suit. Remove the
								// card from the multi hand afterwards.
								game.pile.addCard(multi.get(num));
								game.currentSuit = game.pile.getCard(game.pile
										.getPileSize() - 1).suit;
								multi.remove(num);
								break;
							} else {
								System.out
										.println("Invalid Command! Overflow.");
							}
						} else {
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
		// Add every unplayed card back into the hand.
		for (int i = 0; i < multi.size(); i++) {
			hand.add(multi.get(i));
		}
	}

	/**
	 * Changes the suit to another when an 8 is played.
	 */
	public void play8() {
		System.out
				.println("Choose any suit ([S]pades, [H]eart, [C]lub, [D]iamonds).");
		do {
			game.input = keyb.next();
			if (game.input.equalsIgnoreCase("S")) {
				game.currentSuit = "♠";
				System.out.println("New suit is Spades (♠).\n");
				break;
			} else if (game.input.equalsIgnoreCase("H")) {
				game.currentSuit = "♥";
				System.out.println("New suit is Hearts (♥).\n");
				break;
			} else if (game.input.equalsIgnoreCase("C")) {
				game.currentSuit = "♣";
				System.out.println("New suit is Clubs (♣).\n");
				break;
			} else if (game.input.equalsIgnoreCase("D")) {
				game.currentSuit = "♦";
				System.out.println("New suit is Diamonds (♦).\n");
				break;
			} else {
				System.out
						.println("Please type the correct letter to continue.");
			}
		} while (true);
	}

	/**
	 * Makes the other player pick up 2 - 8 cards depending on the amount of 2's
	 * played in a row.
	 */
	public void play2() {
		int count = 0;
		int stack = 0;
		for (int i = game.pile.getPileSize() - 1; i >= 0; i--) {
			if (game.pile.getCard(i).value.equals("2")) {
				stack = stack + 2;
			} else {
				break;
			}
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
		game.deck.reInitialize();
	}

	/**
	 * Skips the other players turn and gives another play on a card.
	 */
	public void playJ() {
		if (!hand.isEmpty()) {
			System.out.println("Skipped the opponents turn.\n");
			game.print.gameInterface();
			game.print.numberList(this);
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
				game.print.numberList(this);
				System.out.println(hand);
				break;
			} else if (game.input.equalsIgnoreCase("V")) {
				sortByValue();
				game.print.numberList(this);
				System.out.println(hand);
				break;
			} else {
				System.out.println("Invalid Commad!");
			}
		} while (true);
	}
}