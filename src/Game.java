import java.util.Scanner;

public class Game {
	Scanner keyb = new Scanner(System.in);
	Pile pile = new Pile();
	Deck deck = new Deck(pile);
	Print print = new Print(pile);
	Hand handOne = new Hand(this);
	Hand handTwo = new Hand(this);
	String input;
	static String currentSuit;

//	Game(Pile pile, Deck deck, Print print, Hand handOne, Hand handTwo) {
//		this.pile = pile;
//		this.deck = deck;
//		this.print = print;
//		this.handOne = handOne;
//		this.handTwo = handTwo;
//	}

	/**
	 * Plays the game
	 */
	public void play() {
		print.intro();
		do {
			pile.addCard(deck.removeCard());
			currentSuit = pile.getCard(pile.getPileSize() - 1).suit;
			createHand();
			do {
				print.gameInterface();
				playerTurn(true);
				if (handOne.getHandSize() == 0) {
					System.out.println("Player 1 wins!");
					break;
				}
				print.gameInterface();
				playerTurn(false);
				if (handTwo.getHandSize() == 0) {
					System.out.println("Player 2 wins!");
					break;
				}
			} while (true);
			resetGame();
		} while (!end());
		System.out.println("Quiting game.");
	}

	/**
	 * Empties out the hand and the pile back to the deck
	 */
	public void resetGame() {
		while (pile.getPileSize() > 0) {
			deck.addCard(pile.removeCard(0));
		}
		while (handOne.getHandSize() > 0) {
			deck.addCard(handOne.removeCard(0));
		}
		while (handTwo.getHandSize() > 0) {
			deck.addCard(handTwo.removeCard(0));
		}
	}

	/**
	 * Creates 8 cards to form a hand by grabbing cards from the deck.
	 */
	public void createHand() {
		for (int i = 0; i < 8; i++) {
			handOne.addCard(deck.removeCard());
			handTwo.addCard(deck.removeCard());
		}
	}

	/**
	 * Determine whose turn is it
	 * 
	 * @param turn
	 *            which player is playing currently
	 */
	public void playerTurn(boolean turn) {
		if (turn) {
			System.out.println(handOne.hand + "\n");
			System.out.println("Player 1’s Turn!\n");
			System.out.println("Player 2 has " + handTwo.getHandSize() + " cards left to play!");
			handOne.playCard();
		} else if (!turn) {
			System.out.println(handTwo.hand + "\n");
			System.out.println("Player 2’s Turn!\n");
			System.out.println("Player 1 has " + handOne.getHandSize() + " cards left to play!");
			handTwo.playCard();
		}
	}

	/**
	 * Checks to see if the user wants to play again or not.
	 * 
	 * @return true or false depending on users choice
	 */
	public boolean end() {
		System.out.println("(P)lay Again\n" + "(Q)uit");
		do {
			input = keyb.next();
			if (input.equalsIgnoreCase("P")) {
				return false;
			} else if (input.equalsIgnoreCase("Q")) {
				return true;
			} else {
				System.out.println("Invalid Command!");
			}
		} while (true);
	}
}