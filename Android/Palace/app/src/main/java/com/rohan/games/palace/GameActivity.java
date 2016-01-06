package com.rohan.games.palace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends Activity
{
    private Game game;
    private int currentPlayer;
    private ArrayList<Card> selectedCards;
    private int faceDownToFlip;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int numOfPlayers = intent.getIntExtra("numOfPlayers", 2);

        LinearLayout second = (LinearLayout)findViewById(R.id.player2nd);
        LinearLayout third = (LinearLayout)findViewById(R.id.player3rd);
        LinearLayout fourth = (LinearLayout)findViewById(R.id.player4th);

        if(numOfPlayers == 2)
        {
            third.setVisibility(LinearLayout.INVISIBLE);
            fourth.setVisibility(LinearLayout.INVISIBLE);
        }
        else if(numOfPlayers == 3)
        {
            second.setVisibility(LinearLayout.INVISIBLE);
            third.setRotation(22);
            fourth.setRotation(-22);
        }
        game = new Game(numOfPlayers);
        currentPlayer = 0;

        setUpTurn();
    }

    private void setUpTurn()
    {
        if(!game.isPlayerStillIn(currentPlayer))
        {
            currentPlayer++;
            if (currentPlayer == game.getNumOfPlayers())
            {
                currentPlayer = 0;
            }
        }

        populateTable();
        populateHand();
        populatePile();

        if(game.getCardsLeft() == 0)
        {
            ImageView deck = (ImageView)findViewById(R.id.deck);
            deck.setVisibility(View.INVISIBLE);
        }
        else
        {
            ImageView deck = (ImageView)findViewById(R.id.deck);
            deck.setVisibility(View.VISIBLE);
        }

        if(!game.hasAValidMove(currentPlayer))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
            builder.setMessage("No Valid Move! Have to Pick Up!");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    game.pickUp(currentPlayer);
                    currentPlayer++;
                    if (currentPlayer == game.getNumOfPlayers())
                    {
                        currentPlayer = 0;
                    }
                    setUpTurn();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void populatePile()
    {
        ImageView fourthPile = (ImageView)findViewById(R.id.fourthPile);
        fourthPile.setVisibility(LinearLayout.INVISIBLE);
        ImageView thirdPile = (ImageView)findViewById(R.id.thirdPile);
        thirdPile.setVisibility(LinearLayout.INVISIBLE);
        ImageView secondPile = (ImageView)findViewById(R.id.secondPile);
        secondPile.setVisibility(LinearLayout.INVISIBLE);
        ImageView firstPile = (ImageView)findViewById(R.id.firstPile);
        firstPile.setVisibility(LinearLayout.INVISIBLE);

        if(game.getPile().size() > 0)
        {
            fourthPile.setVisibility(LinearLayout.VISIBLE);
            fourthPile.setImageResource(game.getPile().get(game.getPile().size() - 1).getImage());

            if(game.getPile().size() > 1)
            {
                thirdPile.setVisibility(LinearLayout.VISIBLE);
                thirdPile.setImageResource(game.getPile().get(game.getPile().size() - 2).getImage());
            }

            if(game.getPile().size() > 2)
            {
                secondPile.setVisibility(LinearLayout.VISIBLE);
                secondPile.setImageResource(game.getPile().get(game.getPile().size() - 3).getImage());
            }

            if(game.getPile().size() > 3)
            {
                firstPile.setVisibility(LinearLayout.VISIBLE);
                firstPile.setImageResource(game.getPile().get(game.getPile().size() - 4).getImage());
            }
        }
    }

    private void populateHand()
    {
        selectedCards = new ArrayList<Card>();
        LinearLayout hand = (LinearLayout)findViewById(R.id.hand);
        hand.removeAllViews();
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 15, 0, 0);

        for(int i = 0; i < game.getPlayer(currentPlayer).getHand().size(); i++)
        {
            ImageView img = new ImageView(getApplicationContext());
            img.setId(30+i);
            img.setImageResource(game.getPlayer(currentPlayer).getHand().get(i).getImage());
            img.setClickable(true);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected());
                    Card card = game.getPlayer(currentPlayer).getHand().get(v.getId()-30);
                    if(v.isSelected())
                    {
                        selectedCards.add(card);
                    }
                    else
                    {
                        selectedCards.remove(card);
                    }
                }
            });
            hand.addView(img, params);
        }
    }

    private void createCards(ImageView card1, ImageView card2, ImageView card3, int player)
    {
        card1.setVisibility(LinearLayout.INVISIBLE);
        card1.setImageResource(R.drawable.facedown);
        card2.setVisibility(LinearLayout.INVISIBLE);
        card2.setImageResource(R.drawable.facedown);
        card3.setVisibility(LinearLayout.INVISIBLE);
        card3.setImageResource(R.drawable.facedown);

        if(game.getPlayer(player).getFaceUp() != null)
        {
            if(game.getPlayer(player).getFaceUp().size() == 0)
            {
                if(game.getPlayer(player).getFaceDown().size() > 0)
                {
                    card1.setVisibility(LinearLayout.VISIBLE);
                }

                if(game.getPlayer(player).getFaceDown().size() > 1)
                {
                    card2.setVisibility(LinearLayout.VISIBLE);
                }

                if(game.getPlayer(player).getFaceDown().size() > 2)
                {
                    card3.setVisibility(LinearLayout.VISIBLE);
                }
            }

            if (game.getPlayer(player).getFaceUp().size() > 0)
            {
                card1.setVisibility(LinearLayout.VISIBLE);
                card2.setVisibility(LinearLayout.VISIBLE);
                card3.setVisibility(LinearLayout.VISIBLE);
                card1.setImageResource(game.getPlayer(player).getFaceUp().get(0).getImage());
            }

            if (game.getPlayer(player).getFaceUp().size() > 1)
            {
                card2.setImageResource(game.getPlayer(player).getFaceUp().get(1).getImage());
            }

            if (game.getPlayer(player).getFaceUp().size() > 2)
            {
                card3.setImageResource(game.getPlayer(player).getFaceUp().get(2).getImage());
            }
        }
        else
        {
            card1.setVisibility(LinearLayout.VISIBLE);
            card2.setVisibility(LinearLayout.VISIBLE);
            card3.setVisibility(LinearLayout.VISIBLE);
        }
    }

    private void populateTable()
    {
        ImageView card1 = (ImageView)findViewById(R.id.firstCard1);
        ImageView card2 = (ImageView)findViewById(R.id.firstCard2);
        ImageView card3 = (ImageView)findViewById(R.id.firstCard3);
        createCards(card1, card2, card3, currentPlayer);

        if(game.getNumOfPlayers() > 1)
        {
            if(game.getNumOfPlayers() == 2)
            {
                card1 = (ImageView) findViewById(R.id.thirdCard1);
                card2 = (ImageView) findViewById(R.id.thirdCard2);
                card3 = (ImageView) findViewById(R.id.thirdCard3);
            }
            else
            {
                card1 = (ImageView) findViewById(R.id.secondCard1);
                card2 = (ImageView) findViewById(R.id.secondCard2);
                card3 = (ImageView) findViewById(R.id.secondCard3);
            }
            createCards(card1, card2, card3, (currentPlayer+1)%game.getNumOfPlayers());
        }

        if(game.getNumOfPlayers() > 2)
        {
            if(game.getNumOfPlayers() == 3)
            {
                card1 = (ImageView) findViewById(R.id.fourthCard1);
                card2 = (ImageView) findViewById(R.id.fourthCard2);
                card3 = (ImageView) findViewById(R.id.fourthCard3);
            }
            else
            {
                card1 = (ImageView) findViewById(R.id.thirdCard1);
                card2 = (ImageView) findViewById(R.id.thirdCard2);
                card3 = (ImageView) findViewById(R.id.thirdCard3);
            }
            createCards(card1, card2, card3, (currentPlayer+2)%game.getNumOfPlayers());
        }

        if(game.getNumOfPlayers() > 3)
        {
            card1 = (ImageView) findViewById(R.id.fourthCard1);
            card2 = (ImageView) findViewById(R.id.fourthCard2);
            card3 = (ImageView) findViewById(R.id.fourthCard3);
            createCards(card1, card2, card3, (currentPlayer + 3) % game.getNumOfPlayers());
        }
    }

    public void onTableCardClicked(View v)
    {
        ArrayList<Card> choices = game.getPlayer(currentPlayer).getFaceUp();

        if(game.getPlayer(currentPlayer).getFaceUp() != null)
        {
            switch (v.getId())
            {
                case R.id.firstCard1:
                    if(game.getPlayer(currentPlayer).getFaceUp().size() > 0)
                    {
                        v.setSelected(!v.isSelected());
                        if (v.isSelected())
                        {
                            selectedCards.add(choices.get(0));
                        } else
                        {
                            selectedCards.remove(choices.get(0));
                        }
                    }
                    else if(game.getPlayer(currentPlayer).getFaceUp().size() == 0)
                    {
                        choices = game.getPlayer(currentPlayer).getFaceDown();
                        v.setSelected(!v.isSelected());
                        if (v.isSelected())
                        {
                            faceDownToFlip = R.id.firstCard1;
                            selectedCards.add(choices.get(0));
                        } else
                        {
                            selectedCards.remove(choices.get(0));
                        }
                    }
                    break;
                case R.id.firstCard2:
                    if(game.getPlayer(currentPlayer).getFaceUp().size() > 1)
                    {
                        v.setSelected(!v.isSelected());
                        if (v.isSelected())
                        {
                            selectedCards.add(choices.get(1));
                        } else
                        {
                            selectedCards.remove(choices.get(1));
                        }
                    }
                    else if(game.getPlayer(currentPlayer).getFaceUp().size() == 0)
                    {
                        choices = game.getPlayer(currentPlayer).getFaceDown();
                        v.setSelected(!v.isSelected());
                        if (v.isSelected())
                        {
                            faceDownToFlip = R.id.firstCard2;
                            selectedCards.add(choices.get(1));
                        } else
                        {
                            selectedCards.remove(choices.get(1));
                        }
                    }
                    break;
                case R.id.firstCard3:
                    if(game.getPlayer(currentPlayer).getFaceUp().size() > 2)
                    {
                        v.setSelected(!v.isSelected());
                        if (v.isSelected())
                        {
                            selectedCards.add(choices.get(2));
                        } else
                        {
                            selectedCards.remove(choices.get(2));
                        }
                    }
                    else if(game.getPlayer(currentPlayer).getFaceUp().size() == 0)
                    {
                        choices = game.getPlayer(currentPlayer).getFaceDown();
                        v.setSelected(!v.isSelected());
                        if (v.isSelected())
                        {
                            faceDownToFlip = R.id.firstCard3;
                            selectedCards.add(choices.get(2));
                        } else
                        {
                            selectedCards.remove(choices.get(2));
                        }
                    }
                    break;
            }
        }
    }

    public void onPlayGameClick(View v)
    {
        if(game.getPlayer(currentPlayer).getChooseFaceUp())
        {
            if (selectedCards.size() == 3)
            {
                game.getPlayer(currentPlayer).setFaceUp(selectedCards);
                game.getPlayer(currentPlayer).setChooseFaceUp(false);
                currentPlayer++;

                if(currentPlayer == game.getNumOfPlayers())
                {
                    currentPlayer = 0;
                }
            }
            else if (selectedCards.size() > 3)
            {
                createDialog("Too many cards selected!");
            }
            else
            {
                createDialog("Too few cards selected!");
            }
        }
        else
        {
            if(game.getPlayer(currentPlayer).getHand().size() > 0 || game.getPlayer(currentPlayer).getFaceUp().size() > 0)
            {
                if(checkFaceUpCondition())
                {
                    if (game.isValidAddToPile(currentPlayer, selectedCards))
                    {
                        game.draw(currentPlayer);
                        if (!game.getPlayer(currentPlayer).getGoAgain())
                        {
                            currentPlayer++;
                            if (currentPlayer == game.getNumOfPlayers())
                            {
                                currentPlayer = 0;
                            }
                        }
                    }
                    else
                    {
                        createDialog("Not a Valid Move!");
                    }
                }
                else
                {
                    createDialog("Not a Valid Move!");
                }
            }
            else
            {
                if (selectedCards.size() == 1)
                {
                    ImageView img = (ImageView) findViewById(faceDownToFlip);
                    img.setImageResource(selectedCards.get(0).getImage());
                    img.invalidate();

                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    if(!game.checkFaceDown(currentPlayer, selectedCards))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setMessage("Bad Luck! You'll need to pick up the pile!");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                currentPlayer++;
                                if (currentPlayer == game.getNumOfPlayers())
                                {
                                    currentPlayer = 0;
                                }
                                setUpTurn();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    if(!game.getPlayer(currentPlayer).getGoAgain())
                    {
                        currentPlayer++;
                        if (currentPlayer == game.getNumOfPlayers())
                        {
                            currentPlayer = 0;
                        }
                    }
                }
                else if (selectedCards.size() > 1)
                {
                    createDialog("Too many cards selected!");
                }
                else
                {
                    createDialog("Too few cards selected!");
                }
            }
        }
        setUpTurn();
    }

    private void createDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean checkFaceUpCondition()
    {
        if(game.getPlayer(currentPlayer).getHand().size() == 0)
        {
            return true;
        }

        if(game.getPlayer(currentPlayer).getHand().size()-selectedCards.size() <= 0)
        {
            Card cur = selectedCards.get(0);
            for(int i = 1; i < selectedCards.size(); i++)
            {
                if(cur.getValue() != selectedCards.get(i).getValue())
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            for(int i = 0; i < selectedCards.size(); i++)
            {
                if(!game.getPlayer(currentPlayer).getHand().contains(selectedCards.get(i)))
                {
                    return false;
                }
            }
            return true;
        }
    }
}