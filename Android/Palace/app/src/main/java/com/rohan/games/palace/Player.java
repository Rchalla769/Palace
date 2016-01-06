package com.rohan.games.palace;

import java.util.*;

/**
 * Created by rohanchalla on 12/24/15.
 */

public class Player
{
    private int turn;
    private ArrayList<Card> hand;
    private ArrayList<Card> faceUp;
    private ArrayList<Card> faceDown;
    private boolean inGame;
    private boolean goAgain;
    private boolean chooseFaceUp;

    public Player(int turn, ArrayList<Card> faceDown, ArrayList<Card> hand)
    {
        this.turn = turn;
        this.hand = hand;
        this.faceDown = faceDown;
        inGame = true;
        goAgain = false;
        chooseFaceUp = true;
    }

    public void setGoAgain(boolean goAgain)
    {
        this.goAgain = goAgain;
    }

    public void setChooseFaceUp(boolean chooseFaceUp) { this.chooseFaceUp = chooseFaceUp; }

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

    public boolean getChooseFaceUp() { return chooseFaceUp; }

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

