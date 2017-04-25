import java.util.Scanner;

/**
 * Description: Prints back text.
 * 
 * @author Dennis Situ Last Updated: April 24, 2017
 */
public class Print {
	Scanner keyb = new Scanner(System.in);
	Game game;

	Print(Game game) {
		this.game = game;
	}

	/**
	 * Prints the introduction of the game alongside with commands to start the
	 * game and show the rules
	 */
	public void intro() {
		System.out.println("Crazy 8!\n");
		boolean gameStart = false;
		System.out.println("(P)lay");
		System.out.println("(R)ules/Commands\n");
		System.out.println("Enter your choice:");
		do {
			// Grabs input and checks to see if the input is 'p' or not
			game.input = keyb.next();
			if (game.input.equalsIgnoreCase("p")) {
				gameStart = true;
			} else if (game.input.equalsIgnoreCase("r")) {
				System.out.println("Instructions:\n");
				System.out
						.println("To play Crazy 8’s, each player take turns to play a card that has the same suit or number as the card on the screen. Each player may play up to 4 matching numbers and will change the suit depending on what card was played last. If a player is unable to play, they are forced to pick up one card from the deck and give up their turn. The first person to get rid of their hand wins the game.\n");
				System.out.println("Special Cards\n");
				System.out
						.println("Card [2] forces the other player to pick up 2 cards up to 8 cards depending on how many they were played. If the other player plays a 2 after picking up, then the opposing playing has to pick up 4.\n");
				System.out
						.println("Card [J] skips the other player’s turn. Playing even amounts of jacks skips your own turn giving the other player a turn.\n");
				System.out
						.println("Card [8] changes the current suit to any other suit the player chooses to. The card can be played on any suit.\n");
				System.out.println("Commands:\n");
				System.out.println("S to sort");
				System.out.println("0 to pick up");
				System.out.println("# from left to right\n");
				System.out.println("Type \"P\" to start the game");
			} else {
				System.out
						.println("Invalid Command. Please type the correct value to continue.");
			}
		} while (!gameStart);
	}

	/**
	 * Prints the game interface
	 */
	public void gameInterface() {
		System.out.println(" _____");
		System.out.println("|:::::|");
		System.out.println("|:::::|");
		System.out.println("|:::::|		  "
				+ game.pile.getCard(game.pile.getPileSize() - 1));
		System.out.println("|:::::|");
		System.out.println("|:::::|");
		System.out.println(" -----\n");
	}

	/**
	 * Prints the hand of the players along with some information on the other
	 * players hand
	 * 
	 * @param turn
	 *            whose turn it is
	 */
	public void hand(boolean turn) {
		if (turn) {
			for (int i = 1; i < game.handOne.getHandSize() + 1; i++) {
				if (game.handOne.getCard(i - 1).value.equals("10")) {
					System.out.print(" ");
				}
				if (i <= 10) {
					System.out.print("   " + i + "   ");
				} else if (i > 10) {
					System.out.print("  " + i + "   ");
				}
			}
			System.out.println("");
			System.out.println(game.handOne.hand + "\n");
			System.out.println("Player 1’s Turn!\n");
			System.out.println("Player 2 has " + game.handTwo.getHandSize()
					+ " cards left to play!");
		} else if (!turn) {
			for (int i = 1; i < game.handTwo.getHandSize() + 1; i++) {
				if (game.handTwo.getCard(i - 1).value.equals("10")) {
					System.out.print(" ");
				}
				if (i <= 10) {
					System.out.print("   " + i + "   ");
				} else if (i > 10) {
					System.out.print("  " + i + "   ");
				}
			}
			System.out.println("");
			System.out.println(game.handTwo.hand + "\n");
			System.out.println("Player 2’s Turn!\n");
			System.out.println("Player 1 has " + game.handOne.getHandSize()
					+ " cards left to play!");
		}
	}
}