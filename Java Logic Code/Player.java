import java.util.ArrayList;

public class Player 
{
	private int turn;
	private ArrayList<Card> hand;
	private ArrayList<Card> faceUp;
	private ArrayList<Card> faceDown;
	private boolean inGame;
	private boolean goAgain;
	
	public Player(int turn, ArrayList<Card> faceDown, ArrayList<Card> hand)
	{
		this.turn = turn;
		this.hand = hand;
		this.faceDown = faceDown;
		inGame = true;
		goAgain = false;
	}
	
	public void setGoAgain(boolean goAgain)
	{
		this.goAgain = goAgain;
	}
	
	public boolean getGoAgain()
	{
		return goAgain;
	}
	
	public void setInGame(boolean inGame)
	{
		this.inGame = inGame;
	}
	
	public boolean getInGame()
	{
		return inGame;
	}
	
	public void setFaceUp(ArrayList<Card> faceUp)
	{
		this.faceUp = faceUp;
		hand.removeAll(faceUp);
	}
	
	public ArrayList<Card> getFaceUp()
	{
		return faceUp;
	}
	
	public ArrayList<Card> getFaceDown()
	{
		return faceDown;
	}
	
	public ArrayList<Card> getHand()
	{
		return hand;
	}
	
	public void addCardsToHand(ArrayList<Card> pickup)
	{
		hand.addAll(pickup);
	}
	
	public void removeFromHand(ArrayList<Card> cardsPlayed)
	{
		hand.removeAll(cardsPlayed);
	}
	
	public void removeFromFaceUp(ArrayList<Card> cardsPlayed)
	{
		faceUp.removeAll(cardsPlayed);
	}
	
	public void removeFromFaceDown(ArrayList<Card> cardsPlayed)
	{
		faceDown.removeAll(cardsPlayed);
	}
	
	public int getTurn()
	{
		return turn;
	}
}
