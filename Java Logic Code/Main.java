import java.util.*;

public class Main 
{
	static Scanner sc = new Scanner(System.in);
	static Game game;
	
	public static void main(String[] args) 
	{
		while(true)
		{
			int num = getNumberOfPlayers();
			if(num == 0)
			{
				break;
			}
			game = new Game(num);
		
			for(int i = 0; i < num; i++)
			{
				System.out.println("Player " + (i+1) + ": X X X");
			}
			
			chooseFaceUp(num);
			
			boolean gameOver = false;
			ArrayList<Integer> ranking = new ArrayList<Integer>();
			while(!gameOver)
			{
				for(int i = 0; i < num; i++)
				{
					if(!game.isPlayerStillIn(i) && !ranking.contains(i))
					{
						ranking.add(i);
					}
					else if(game.isPlayerStillIn(i))
					{
						printBoard(num);
						printPile();
						Player current = game.getPlayer(i);
						System.out.println("It is player " + (i+1) + "'s turn!");
						
						boolean isTurn = true;
						while(isTurn)
						{
							if(current.getHand().size() > 0)
							{
								printHand(current);
							}
							else
							{
								printFaceUp(current);
							}
							
							if(current.getHand().size() > 0 || current.getFaceUp().size() > 0)
							{
								if(!current.getHand().isEmpty())
								{
									if(game.hasAValidMove(i))
									{
										System.out.print("Please select a card(s): ");
										String choice = sc.nextLine();
										String[] tokens = choice.split(" ");
										
										ArrayList<Card> add = new ArrayList<Card>();
										for(String s : tokens)
										{
											Card c = game.isValidCard(i, s);
											if(c != null)
											{
												add.add(c);
											}
										}
										
										if(!game.isValidAddToPile(i, add))
										{
											System.out.println("Try again!");
										}
									}
									else
									{
										System.out.println("Unfortunately, you do not have a valid move!");
										System.out.println("You have to pick up the pile");
										game.pickUp(i);
									}
								}
								else
								{
									if(game.hasAValidMove(i))
									{
										System.out.print("Please select a card(s): ");
										String choice = sc.nextLine();
										String[] tokens = choice.split(" ");
										
										ArrayList<Card> add = new ArrayList<Card>();
										for(String s : tokens)
										{
											Card c = game.isValidCard(i, s);
											if(c != null)
											{
												add.add(c);
											}
										}
										
										if(!game.isValidAddToPile(i, add))
										{
											System.out.println("Try again!");
										}
									}
									else
									{
										System.out.print("Please select a card(s): ");
										String choice = sc.nextLine();
										String[] tokens = choice.split(" ");
										
										ArrayList<Card> add = new ArrayList<Card>();
										for(String s : tokens)
										{
											Card c = game.isValidCard(i, s);
											if(c != null)
											{
												add.add(c);
											}
										}
										
										System.out.println("You need to pick up!");
										
										current.addCardsToHand(add);
										game.pickUp(i);
									}
								}
								game.draw(i);
							}
							else
							{
								int choice = 0;
								while(true)
								{
									System.out.print("Choose a face down (1-3): ");
									String input = sc.next();
									try
									{
										choice = Integer.parseInt(input);
										if(choice > 0 && choice < current.getFaceDown().size()+1)
										{
											break;
										}
										System.out.println("Please Enter an integer from (1-3)!");
									}
									catch(Exception e)
									{
										System.out.println("Please enter a number!");
									}
								}
								
								if(!game.checkFaceDown(i, choice))
								{
									System.out.println("Sorry, you have to pick up the pile!");
								}
							}
							isTurn = current.getGoAgain();
						}
						
						if(current.getHand().size() == 0 && current.getFaceUp().size() == 0 && current.getFaceDown().size() == 0)
						{
							System.out.println("Winner " + ranking.size() + ": Player " + i);
							current.setInGame(false);
						}
					}
					
					gameOver = ranking.size() == num-1;
				}
			}
		}
		
		System.out.println("Thanks for playing!");
	}
	
	public static void printPile()
	{
		System.out.print("Top four of Pile (Top to Bottom): ");
		if(game.getPile().size() > 0 && game.getPile().size() < 5)
		{
			for(int i = game.getPile().size()-1; i > -1; i--)
			{
				System.out.print(game.getPile().get(i).getDisplayValue() + " ");
			}
		}
		else if(game.getPile().size() > 4)
		{
			for(int i = game.getPile().size()-1; i > game.getPile().size()-5; i--)
			{
				System.out.print(game.getPile().get(i).getDisplayValue() + " ");
			}
		}
		
		System.out.println();
	}
	
	public static void printFaceUp(Player p)
	{
		System.out.print("Player " + (p.getTurn()+1) + " Face Up: ");
		for(int i = 0; i < p.getFaceUp().size(); i++)
		{
			System.out.print(p.getFaceUp().get(i).getDisplayValue() + " ");
		}
		System.out.println();
	}
	
	public static void printHand(Player p)
	{
		System.out.print("Player " + (p.getTurn()+1) + " Hand: ");
		for(int i = 0; i < p.getHand().size(); i++)
		{
			System.out.print(p.getHand().get(i).getDisplayValue() + " ");
		}
		System.out.println();
	}
	
	public static void chooseFaceUp(int num)
	{
		for(int i = 0; i < num; i++)
		{
			System.out.print("Player " + (i+1) + " Hand: ");
			ArrayList<Card> hand = game.getPlayer(i).getHand();
			for(int j = 0; j < hand.size(); j++)
			{
				System.out.print(hand.get(j).getDisplayValue() + " ");
			}
			System.out.println("\nChoose Face ups!");
			
			ArrayList<Card> faceUp = new ArrayList<Card>();
			while(faceUp.size() < 3)
			{
				System.out.print("Enter a card from your hand (H2): ");
				String choice = sc.next();
				
				Card card = game.isValidCard(i, choice);
				
				if(card == null)
				{
					System.out.println("Please enter a card in your hand!");
				}
				else
				{
					faceUp.add(card);
				}
			}
			game.getPlayer(i).setFaceUp(faceUp);
		}
	}
	
	public static int getNumberOfPlayers()
	{
		while(true)
		{
			System.out.print("Enter number of players or 0 to quit: ");
			String input = sc.next();
			try
			{
				int num = Integer.parseInt(input);
				if(input.isEmpty() || num == 0)
				{
					return 0;
				}
				else if(num > 0 && num < 5)
				{
					return num;
				}
				System.out.println("Please Enter an integer from (0-4)!");
			}
			catch(Exception e)
			{
				System.out.println("Please enter a number!");
			}
		}
	}
	
	public static void printBoard(int num)
	{
		for(int i = 0; i < num; i++)
		{
			if(game.isPlayerStillIn(i))
			{
				System.out.print("\nPlayer " + (i+1) + ": ");
				int size = game.getPlayer(i).getFaceUp().size();
				if(size == 3)
				{
					System.out.print(game.getPlayer(i).getFaceUp().get(0).getDisplayValue() + " ");
					System.out.print(game.getPlayer(i).getFaceUp().get(1).getDisplayValue() + " ");
					System.out.print(game.getPlayer(i).getFaceUp().get(2).getDisplayValue() + " ");
				}
				else if(size == 2)
				{
					System.out.print(game.getPlayer(i).getFaceUp().get(0).getDisplayValue() + " ");
					System.out.print(game.getPlayer(i).getFaceUp().get(1).getDisplayValue() + " ");
					System.out.print("X ");
				}
				else if(size == 1)
				{
					System.out.print(game.getPlayer(i).getFaceUp().get(0).getDisplayValue() + " ");
					System.out.print("X X ");
				}
				else if(size == 0)
				{
					for(int j = 0; j < game.getPlayer(i).getFaceDown().size(); j++)
					{
						System.out.print("X ");
					}
				}
			}
		}
		System.out.println();
	}
}
