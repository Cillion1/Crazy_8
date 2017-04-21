import java.util.Scanner;

public class Main {
	static Scanner keyb = new Scanner(System.in);
	static Pile pile = new Pile();
	static Deck deck = new Deck(pile);
	static Hand handOne = new Hand(deck, pile);
	static Hand handTwo = new Hand(deck, pile);
	static Card discard = null;
	static String currentSuit;
	static String input;
	
	public static void main(String[] args) {
		pile.addCard(deck.removeCard());
		currentSuit = pile.getCard(pile.getPileCount()-1).suit;
		createHand();
		
		do {
			printInterface();
			playGame(true);
			if (handOne.getHandSize() == 0) {
				System.out.println("Player 1 wins!");
				break;
			}
			printInterface();
			playGame(false);
			if (handTwo.getHandSize() == 0) {
				System.out.println("Player 2 wins!");
				break;
			}
		} while (true);
		
	}
	
	/**
	 * Prints the introduction of the game
	 */
	public static void printIntro() {
		System.out.println("Crazy 8!\n");
		boolean gameStart = false;
		do {
			System.out.println("(P)lay");
			System.out.println("(R)ules\n");
			System.out.println("Enter your choice: ");
			// Grabs input and checks to see if the input is 'p' or not
			input = keyb.next();
			if (input.equalsIgnoreCase("p")) {
				gameStart = true;
			} else if (input.equalsIgnoreCase("r")) {
				System.out.println("Instructions:\n");
				System.out.println("To play Crazy 8’s, each player take turns to play a card that has the same suit or number as the card on the screen. Each player may play up to 4 matching numbers and will change the suit depending on what card was played last. If a player is unable to play, they are forced to pick up one card from the deck and give up their turn. The first person to get rid of their hand wins the game.\n");
				System.out.println("Special Cards\n");
				System.out.println("Card [2] forces the other player to pick up 2 cards up to 8 cards depending on how many they were played. If the other player plays a 2 after picking up, then the opposing playing has to pick up 4.\n");
				System.out.println("Card [J] skips the other player’s turn. Playing even amounts of jacks skips your own turn giving the other player a turn.\n");
				System.out.println("Card [8] changes the current suit to any other suit the player chooses to. The card can be played on any suit.\n");
			} else {
				System.out.println("Invalid Command. Please type the correct value to continue.");
			}
		} while (!gameStart);
	}
	
	/**
	 * Creates 8 cards to form a hand by grabbing cards from the deck.
	 */
	public static void createHand() {
		for (int i = 0; i < 8; i++) {
			handOne.addCard(deck.removeCard());
			handTwo.addCard(deck.removeCard());
		}
	}
	
	/**
	 * Prints the game interface
	 */
	public static void printInterface() {
		System.out.println(" _____");
		System.out.println("|:::::|");
		System.out.println("|:::::|");
		System.out.println("|:::::|		  " + pile.getCard(pile.getPileCount()-1));
		System.out.println("|:::::|");
		System.out.println("|:::::|");
		System.out.println(" -----\n");
	}
	
	/**
	 * Determine whose turn is it
	 * @param turn which player is playing currently
	 */
	public static void playGame(boolean turn) {
		if (turn) {
			System.out.println(handOne.hand + "\n");
			System.out.println("Player 1’s Turn!");
			handOne.playCard();
		} else if (!turn) {
			System.out.println(handTwo.hand + "\n");
			System.out.println("Player 2’s Turn!");
			handTwo.playCard();
		}
	}
	
	public static void endGame() {
		System.out.println("(P)lay Again\n "
				+ "(Q)uit");
	}
	
}