
public class Card 
{
	private int value;
	private String suit;
	private String displayValue;
	
	public Card(int value, String suit, String displayValue)
	{
		this.value = value;
		this.suit = suit;
		this.displayValue = displayValue;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public String getSuit()
	{
		return suit;
	}
	
	public String getDisplayValue()
	{
		return displayValue;
	}
}
