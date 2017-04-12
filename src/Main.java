import java.util.Scanner;

public class Main {
	static Scanner keyb = new Scanner(System.in);
	static Deck deck = new Deck();
	static Pile pile = new Pile();
	static Hand handTwo = new Hand(deck, pile);
	static Hand handOne = new Hand(deck, pile);
	static Card discard = null;
	static Card currentCard = deck.removeCard();
	static String holdSuit = currentCard.suit;

	public static void main(String[] args) {
		
		pile.addCard(deck.removeCard());
		createHand();
		do {
			System.out.println(pile.pile);
			printGame();
			System.out.println(pile.getPileCount());
			playerTurns(true);
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
	
	public static void playCard(Hand hand) {
		int temp = 0;
		do {
			try {
				String chooseCard = keyb.next();
				if (chooseCard.equalsIgnoreCase("0")) {
					pickUpCard(hand);
					break;
				}
				temp = Integer.parseInt(chooseCard) - 1;
				if (temp <= hand.getCardCount()) {
					System.out.println("Card played: " + hand.getCard(temp).suit);
					System.out.println("Hold suit: " + holdSuit);
					System.out.println("Current Card: " + currentCard);
					if (hand.getCard(temp).value.equals("8")) {
						discardCard(temp, hand);
						play8(temp, hand);
						break;
					} else if (holdSuit.equals(hand.getCard(temp).suit) || currentCard.value.equals(hand.getCard(temp).value)) {
						System.out.println("It works");
						discardCard(temp, hand);
						holdSuit = currentCard.suit;
						break;
					} else {
						System.out.println("Invalid Command. Invalid Card");
					}
				} else {
					System.out.println("Invalid Command. Hand Overflow");
				}
			} catch (Exception e) {
				System.out.println("Invalid Command. Please type the correct value to continue.");
			}
		} while (true);
	}
	
	public static void pickUpCard(Hand hand) {
		hand.addCard(deck.removeCard());
	}
	
	public static void printGame() {
		System.out.println(" _____");
		System.out.println("|:::::|");
		System.out.println("|:::::|");
		System.out.println("|:::::|		  " + currentCard);
		System.out.println("|:::::|");
		System.out.println("|:::::|");
		System.out.println(" -----");
		System.out.println("");
	}
	
	public static void play8(int index, Hand hand) {
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
	
	public static void play2(int index, Hand hand) {
		
	}
	
	public static void playJ(int index, Hand hand) {
		
	}
	
	public static void discardCard(int index, Hand hand) {
		currentCard = hand.removeCard(index);
		if (discard != null) {
			deck.addCard(discard);
		}
		discard = currentCard;
	}
	
	public static void playerTurns(boolean turn) {
		if (turn == true) {
			System.out.println(handOne.hand);
			System.out.println("");
			System.out.println("Player 1’s Turn!");
			String chooseCard = keyb.next();
			int temp = Integer.parseInt(chooseCard) - 1;
			handOne.playCard(temp);
		} else if (turn == false) {
			System.out.println(handTwo.hand);
			System.out.println("");
			System.out.println("Player 2’s Turn!");
			playCard(handTwo);
		}
	}
	
}
