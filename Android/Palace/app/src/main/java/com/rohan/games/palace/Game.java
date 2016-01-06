package com.rohan.games.palace;

import java.util.*;

/**
 * Created by rohanchalla on 12/24/15.
 */
public class Game
{
    private static final int NUM_IN_HAND = 7;
    private static final int NUM_OF_FACE_DOWN = 3;

    private int cardsLeft = 52;
    private int numOfPlayers;
    private Card[] deck = new Card[52];
    private boolean[] usedDeck = new boolean[52];
    private Player[] players;
    private ArrayList<Card> pile;

    public Game(int numberOfPlayers)
    {
        generateDeck();
        numOfPlayers = numberOfPlayers;
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

    public int getCardsLeft()
    {
        return cardsLeft;
    }

    public Player getPlayer(int turn)
    {
        return players[turn];
    }

    public int getNumOfPlayers() { return numOfPlayers; }

    private void generateDeck()
    {
        deck[0] = new Card(14, "Clubs", "CA", R.drawable.clubsa);
        deck[1] = new Card(13, "Clubs", "CK", R.drawable.clubsk);
        deck[2] = new Card(12, "Clubs", "CQ", R.drawable.clubsq);
        deck[3] = new Card(11, "Clubs", "CJ", R.drawable.clubsj);
        deck[4] = new Card(10, "Clubs", "C10", R.drawable.clubs10);
        deck[5] = new Card(9, "Clubs", "C9", R.drawable.clubs9);
        deck[6] = new Card(8, "Clubs", "C8", R.drawable.clubs8);
        deck[7] = new Card(7, "Clubs", "C7", R.drawable.clubs7);
        deck[8] = new Card(6, "Clubs", "C6", R.drawable.clubs6);
        deck[9] = new Card(5, "Clubs", "C5", R.drawable.clubs5);
        deck[10] = new Card(4, "Clubs", "C4", R.drawable.clubs4);
        deck[11] = new Card(3, "Clubs", "C3", R.drawable.clubs3);
        deck[12] = new Card(2, "Clubs", "C2", R.drawable.clubs2);

        deck[13] = new Card(14, "Diamonds", "DA", R.drawable.diamondsa);
        deck[14] = new Card(13, "Diamonds", "DK", R.drawable.diamondsk);
        deck[15] = new Card(12, "Diamonds", "DQ", R.drawable.diamondsq);
        deck[16] = new Card(11, "Diamonds", "DJ", R.drawable.diamondsj);
        deck[17] = new Card(10, "Diamonds", "D10", R.drawable.diamonds10);
        deck[18] = new Card(9, "Diamonds", "D9", R.drawable.diamonds9);
        deck[19] = new Card(8, "Diamonds", "D8", R.drawable.diamonds8);
        deck[20] = new Card(7, "Diamonds", "D7", R.drawable.diamonds7);
        deck[21] = new Card(6, "Diamonds", "D6", R.drawable.diamonds6);
        deck[22] = new Card(5, "Diamonds", "D5", R.drawable.diamonds5);
        deck[23] = new Card(4, "Diamonds", "D4", R.drawable.diamonds4);
        deck[24] = new Card(3, "Diamonds", "D3", R.drawable.diamonds3);
        deck[25] = new Card(2, "Diamonds", "D2", R.drawable.diamonds2);

        deck[26] = new Card(14, "spades", "CA", R.drawable.spadesa);
        deck[27] = new Card(13, "spades", "CK", R.drawable.spadesk);
        deck[28] = new Card(12, "spades", "CQ", R.drawable.spadesq);
        deck[29] = new Card(11, "spades", "CJ", R.drawable.spadesj);
        deck[30] = new Card(10, "spades", "C10", R.drawable.spades10);
        deck[31] = new Card(9, "spades", "C9", R.drawable.spades9);
        deck[32] = new Card(8, "spades", "C8", R.drawable.spades8);
        deck[33] = new Card(7, "spades", "C7", R.drawable.spades7);
        deck[34] = new Card(6, "spades", "C6", R.drawable.spades6);
        deck[35] = new Card(5, "spades", "C5", R.drawable.spades5);
        deck[36] = new Card(4, "spades", "C4", R.drawable.spades4);
        deck[37] = new Card(3, "spades", "C3", R.drawable.spades3);
        deck[38] = new Card(2, "spades", "C2", R.drawable.spades2);

        deck[39] = new Card(14, "hearts", "DA", R.drawable.heartsa);
        deck[40] = new Card(13, "hearts", "DK", R.drawable.heartsk);
        deck[41] = new Card(12, "hearts", "DQ", R.drawable.heartsq);
        deck[42] = new Card(11, "hearts", "DJ", R.drawable.heartsj);
        deck[43] = new Card(10, "hearts", "D10", R.drawable.hearts10);
        deck[44] = new Card(9, "hearts", "D9", R.drawable.hearts9);
        deck[45] = new Card(8, "hearts", "D8", R.drawable.hearts8);
        deck[46] = new Card(7, "hearts", "D7", R.drawable.hearts7);
        deck[47] = new Card(6, "hearts", "D6", R.drawable.hearts6);
        deck[48] = new Card(5, "hearts", "D5", R.drawable.hearts5);
        deck[49] = new Card(4, "hearts", "D4", R.drawable.hearts4);
        deck[50] = new Card(3, "hearts", "D3", R.drawable.hearts3);
        deck[51] = new Card(2, "hearts", "D2", R.drawable.hearts2);

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

    public boolean checkFaceDown(int turn, ArrayList<Card> x)
    {
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
            if(x.get(0).getValue() == 10)
            {
                addToPile(turn, x);
                clear();
                players[turn].setGoAgain(true);
                return true;
            }
            else if(x.get(0).getValue() == 2)
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
            else if(x.get(0).getValue() >= pile.get(pile.size()-1).getValue())
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
}

