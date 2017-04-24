public class Main {
//	static Pile pile = new Pile();
//	static Deck deck = new Deck(pile);
//	static Print print = new Print(pile);
//	static Hand handOne = new Hand(deck, pile, print);
//	static Hand handTwo = new Hand(deck, pile, print);
//	static String input;

	public static void main(String[] args) {
		Game game = new Game();//pile, deck, print, handOne, handTwo);
		game.play();
	}
}