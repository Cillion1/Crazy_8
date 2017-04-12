import java.util.ArrayList;


public class Pile {
	ArrayList<Card> pile = new ArrayList<Card>();
	
	Pile() {
		
	}
	
	public void addCard(Card c) {
    	pile.add(c);
    }
	
	public void removeCard(Card c) {
    	pile.remove(c);
    }
	
    public Card removeCard(int position) {
    	return pile.remove(position);
    }
    
    public Card lastCard() {
    	return pile.get(getPileCount());
    }

	public int getPileCount() {
    	return pile.size()-1;
    }
}
