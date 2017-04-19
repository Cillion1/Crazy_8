import java.util.Scanner;

public class Main {
	static Scanner keyb = new Scanner(System.in);
	static Pile pile = new Pile();
	static Deck deck = new Deck(pile);
	static Hand handOne = new Hand(deck, pile);
	static Hand handTwo = new Hand(deck, pile);
	static Card discard = null;
	static String currentSuit;
	static boolean whosTurn = true;
	
	public static void main(String[] args) {
		pile.addCard(deck.removeCard());
		currentSuit = pile.getCard(pile.getPileCount()-1).suit;
		createHand();
		
		do {
			printGame();
			playGame(true);
			if (handOne.getHandSize() == 0) {
				System.out.println("Player 1 wins!");
				break;
			}
			printGame();
			playGame(false);
			if (handTwo.getHandSize() == 0) {
				System.out.println("Player 2 wins!");
				break;
			}
		} while (true);
		
	}
	
	public static void printIntro() {
		System.out.println("Crazy 8!");
		System.out.println("");
		boolean gameStart = false;
		do {
			System.out.println("(P)lay");
			System.out.println("(R)ules");
			System.out.println("");
			System.out.println("Enter your choice: ");
			// Grabs input and checks to see if the input is 'p' or not
			String numStart = keyb.next();
			if (numStart.equalsIgnoreCase("p")) {
				gameStart = true;
			} else if (numStart.equalsIgnoreCase("r")) {
				System.out.println("Instructions:");
				System.out.println("");
				System.out.println("To play Crazy 8’s, each player take turns to play a card that has the same suit or number as the card on the screen. Each player may play up to 4 matching numbers and will change the suit depending on what card was played last. If a player is unable to play, they are forced to pick up one card from the deck and give up their turn. The first person to get rid of their hand wins the game.");
				System.out.println("");
				System.out.println("Special Cards");
				System.out.println("");
				System.out.println("Card [2] forces the other player to pick up 2 cards up to 8 cards depending on how many they were played. If the other player plays a 2 after picking up, then the opposing playing has to pick up 4.");
				System.out.println("");
				System.out.println("Card [J] skips the other player’s turn. Playing even amounts of jacks skips your own turn giving the other player a turn.");
				System.out.println("");
				System.out.println("Card [8] changes the current suit to any other suit the player chooses to. The card can be played on any suit.");
				System.out.println("");
			} else {
				System.out.println("Invalid Command. Please type the correct value to continue.");
			}
		} while (!gameStart);
	}
	
	public static void createHand() {
		for (int i = 0; i < 8; i++) {
			handOne.addCard(deck.removeCard());
			handTwo.addCard(deck.removeCard());
		}
	}
	
	public static void printGame() {
		System.out.println(" _____");
		System.out.println("|:::::|");
		System.out.println("|:::::|");
		System.out.println("|:::::|		  " + pile.getCard(pile.getPileCount()-1));
		System.out.println("|:::::|");
		System.out.println("|:::::|");
		System.out.println(" -----");
		System.out.println("");
	}
	
	public static void playGame(boolean turn) {
		if (turn) {
			System.out.println(handOne.hand);
			System.out.println("");
			System.out.println("Player 1’s Turn!");
			handOne.playCard();
			whosTurn = false;
		} else if (!turn) {
			System.out.println(handTwo.hand);
			System.out.println("");
			System.out.println("Player 2’s Turn!");
			handTwo.playCard();
			whosTurn = true;
		}
	}
	
}
