import java.util.*;

public class Game 
{
	private static final int NUM_IN_HAND = 7;
	private static final int NUM_OF_FACE_DOWN = 3;
	private static final String[] SUITS = {"Clubs", "Spades", "Hearts", "Diamonds"};
	
	private int cardsLeft = 52;
	private Card[] deck = new Card[52];
	private boolean[] usedDeck = new boolean[52];
	private Player[] players;
	private ArrayList<Card> pile;
	
	public Game(int numberOfPlayers)
	{
		generateDeck();
		players = new Player[numberOfPlayers];
		
		for(int i = 0; i < numberOfPlayers; i++)
		{
			players[i] = new Player(i, generateFaceDown(), generateHand());
		}
		pile = new ArrayList<Card>();
	}
	
	public Card[] getDeck()
	{
		return deck;
	}
	
	public Player getPlayer(int turn)
	{
		return players[turn];
	}
	
	private void generateDeck()
	{
		int count = 0;
		for(String s : SUITS)
		{
			for(int i = 2; i < 15; i++)
			{
				deck[(i-2) + (count*13)] = new Card(i, s, s.charAt(0)+convertValue(i));
			}
			count++;
		}
		for(int i = 0; i < usedDeck.length; i++)
		{
			usedDeck[i] = false;
		}
	}
	
	private ArrayList<Card> generateFaceDown()
	{
		ArrayList<Card> faceDown = new ArrayList<Card>();
		Random rand = new Random();
		
		while(faceDown.size() < NUM_OF_FACE_DOWN)
		{
			int location = rand.nextInt(deck.length);
			if(!usedDeck[location])
			{
				faceDown.add(deck[location]);
				usedDeck[location] = true;
				cardsLeft--;
			}
		}
		return faceDown;
	}
	
	private ArrayList<Card> generateHand()
	{
		ArrayList<Card> hand = new ArrayList<Card>();
		Random rand = new Random();
		
		while(hand.size() < NUM_IN_HAND)
		{
			int location = rand.nextInt(deck.length);
			if(!usedDeck[location])
			{
				hand.add(deck[location]);
				usedDeck[location] = true;
				cardsLeft--;
			}
		}
		return hand;
	}
	
	private boolean checkFourOfKind()
	{
		if(pile.size() >= 4)
		{
			Card x = pile.get(pile.size()-1);
			for(int i = pile.size()-1; i > pile.size()-5; i--)
			{
				if(!(x.getValue() == pile.get(i).getValue()))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public void draw(int turn)
	{
		if(cardsLeft > 0)
		{
			ArrayList<Card> hand = new ArrayList<Card>();
			Random rand = new Random();
			while(hand.size() < 4 - players[turn].getHand().size())
			{
				int location = rand.nextInt(deck.length);
				if(!usedDeck[location])
				{
					hand.add(deck[location]);
					usedDeck[location] = true;
					cardsLeft--;
				}
			}
			players[turn].addCardsToHand(hand);
		}
	}
	
	public void addToPile(int turn, ArrayList<Card> add)
	{
		players[turn].removeFromHand(add);
		players[turn].removeFromFaceUp(add);
		players[turn].removeFromFaceDown(add);
		pile.addAll(add);
	}
	
	public void pickUp(int turn)
	{
		players[turn].addCardsToHand(pile);
		pile.clear();
		players[turn].setGoAgain(false);
	}
	
	public void clear()
	{
		pile.clear();
	}
	
	public boolean hasAValidMove(int turn)
	{
		if(pile.size() == 0)
		{
			return true;
		}
		
		if(players[turn].getHand().isEmpty() && players[turn].getFaceUp().isEmpty())
		{
			return true;
		}
		
		if(players[turn].getHand().isEmpty())
		{
			ArrayList<Card> faceUp = players[turn].getFaceUp();
			for(int i = 0; i < faceUp.size(); i++)
			{
				if(faceUp.get(i).getValue() == 2 || faceUp.get(i).getValue() == 10)
				{
					return true;
				}
				else if(faceUp.get(i).getValue() >= pile.get(pile.size()-1).getValue())
				{
					return true;
				}
			}
		}
		else
		{
			ArrayList<Card> hand = players[turn].getHand();
			for(int i = 0; i < hand.size(); i++)
			{
				if(hand.get(i).getValue() == 2 || hand.get(i).getValue() == 10)
				{
					return true;
				}
				else if(hand.get(i).getValue() >= pile.get(pile.size()-1).getValue())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public Card isValidCard(int turn, String displayValue)
	{
		if(players[turn].getHand().size() == 0)
		{
			ArrayList<Card> faceUp = players[turn].getFaceUp();
			for(int i = 0; i < faceUp.size(); i++)
			{
				if(faceUp.get(i).getDisplayValue().equalsIgnoreCase(displayValue))
				{
					return faceUp.get(i);
				}
			}
		}
		else
		{
			ArrayList<Card> hand = players[turn].getHand();
			for(int i = 0; i < hand.size(); i++)
			{
				if(hand.get(i).getDisplayValue().equalsIgnoreCase(displayValue))
				{
					return hand.get(i);
				}
			}
		}
		return null;
	}
	
	public boolean checkFaceDown(int turn, int choice)
	{
		Card c = players[turn].getFaceDown().get(choice-1);
		ArrayList<Card> x = new ArrayList<Card>();
		x.add(c);
		
		if(pile.isEmpty())
		{
			addToPile(turn, x);
			players[turn].setGoAgain(false);
			
			if(x.get(0).getValue() == 10)
			{
				clear();
				players[turn].setGoAgain(true);
			}
			else if(x.get(0).getValue() == 2)
			{
				players[turn].setGoAgain(true);
			}
			return true;
		}
		else
		{
			if(c.getValue() == 10)
			{
				addToPile(turn, x);
				clear();
				players[turn].setGoAgain(true);
				return true;
			}
			else if(c.getValue() == 2)
			{
				addToPile(turn, x);
				players[turn].setGoAgain(true);
				
				if(checkFourOfKind())
				{
					clear();
					players[turn].setGoAgain(true);
				}
				
				return true;
			}
			else if(c.getValue() >= pile.get(pile.size()-1).getValue())
			{
				addToPile(turn, x);
				players[turn].setGoAgain(false);
				
				if(checkFourOfKind())
				{
					clear();
					players[turn].setGoAgain(true);
				}
				
				return true;
			}
			else
			{
				players[turn].setGoAgain(false);
				players[turn].addCardsToHand(x);
				pickUp(turn);
				return false;
			}
		}
	}
	
	public boolean isValidAddToPile(int turn, ArrayList<Card> add)
	{
		if(add.size() == 0)
		{
			players[turn].setGoAgain(true);
			return false;
		}
		
		if(pile.isEmpty())
		{
			if(add.size() == 1)
			{
				addToPile(turn, add);
				players[turn].setGoAgain(false);
				
				if(add.get(0).getValue() == 10)
				{
					clear();
					players[turn].setGoAgain(true);
				}
				else if(add.get(0).getValue() == 2)
				{
					players[turn].setGoAgain(true);
				}
				return true;
			}
			else
			{
				Card one = add.get(0);
				for(Card x : add)
				{
					if(!(one.getValue() == x.getValue()))
					{
						players[turn].setGoAgain(false);
						return false;
					}
				}
				addToPile(turn, add);
				players[turn].setGoAgain(false);
				
				if(one.getValue() == 10)
				{
					clear();
					players[turn].setGoAgain(true);
				}
				else if(one.getValue() == 2)
				{
					players[turn].setGoAgain(true);
				}
				
				if(checkFourOfKind())
				{
					clear();
					players[turn].setGoAgain(true);
				}
				return true;
			}
		}
		else
		{
			if(add.size() == 1)
			{
				if(add.get(0).getValue() == 10)
				{
					addToPile(turn, add);
					clear();
					players[turn].setGoAgain(true);
					
					if(checkFourOfKind())
					{
						clear();
						players[turn].setGoAgain(true);
					}
					return true;
				}
				else if(add.get(0).getValue() == 2)
				{
					addToPile(turn, add);
					players[turn].setGoAgain(true);
					
					if(checkFourOfKind())
					{
						clear();
						players[turn].setGoAgain(true);
					}
					return true;
				}
				else if(add.get(0).getValue() >= pile.get(pile.size()-1).getValue())
				{
					addToPile(turn, add);
					players[turn].setGoAgain(false);
					
					if(checkFourOfKind())
					{
						clear();
						players[turn].setGoAgain(true);
					}
					
					return true;
				}
				else
				{	
					players[turn].setGoAgain(true);
					return false;
				}
			}
			else
			{
				Card one = add.get(0);
				for(Card x : add)
				{
					if(one.getValue() != x.getValue())
					{
						players[turn].setGoAgain(true);
						return false;
					}
				}
				
				if(one.getValue() == 10)
				{
					addToPile(turn, add);
					clear();
					players[turn].setGoAgain(true);
					return true;
				}
				else if(one.getValue() == 2)
				{
					addToPile(turn, add);
					players[turn].setGoAgain(true);
					
					if(checkFourOfKind())
					{
						clear();
						players[turn].setGoAgain(true);
					}
					
					return true;
				}
				else if(one.getValue() >= pile.get(pile.size()-1).getValue())
				{
					addToPile(turn, add);
					players[turn].setGoAgain(false);
					
					if(checkFourOfKind())
					{
						clear();
						players[turn].setGoAgain(true);
					}
					
					return true;
				}
				players[turn].setGoAgain(true);
				return false;
			}
		}
	}
	
	public boolean isPlayerStillIn(int turn)
	{
		if(players[turn].getInGame())
		{
			if(players[turn].getHand().size() == 0)
			{
				if(players[turn].getFaceUp().size() == 0)
				{
					if(players[turn].getFaceDown().size() == 0)
					{
						players[turn].setInGame(false);
					}
				}
			}
		}
		return players[turn].getInGame();
	}
	
	public ArrayList<Card> getPile()
	{
		return pile;
	}
	
	private String convertValue(int value)
	{
		if(value == 11)
		{
			return "J";
		}
		else if(value == 12)
		{
			return "Q";
		}
		else if(value == 13)
		{
			return "K";
		}
		else if(value == 14)
		{
			return "A";
		}
		else
		{
			return value + "";
		}
	}
}
