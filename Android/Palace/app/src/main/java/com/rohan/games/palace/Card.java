package com.rohan.games.palace;

/**
 * Created by rohanchalla on 12/16/15.
 */
public class Card
{
    private int value;
    private String suit;
    private String displayValue;
    private int image;

    public Card(int value, String suit, String displayValue, int image)
    {
        this.value = value;
        this.suit = suit;
        this.displayValue = displayValue;
        this.image = image;
    }

    public int getImage() { return image; }

    public int getValue() { return value; }

    public String getSuit()
    {
        return suit;
    }

    public String getDisplayValue() { return displayValue; }
}
