package com.rohan.games.palace;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GameOptions extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_options);
    }

    public void onStartGameClick(View view)
    {
        int numOfPlayers = 0;
        RadioButton button = (RadioButton) findViewById(R.id.radioButton2);
        if(button.isChecked())
        {
            numOfPlayers = 2;
        }

        button = (RadioButton) findViewById(R.id.radioButton);
        if(button.isChecked())
        {
            numOfPlayers = 3;
        }

        button = (RadioButton) findViewById(R.id.radioButton3);
        if(button.isChecked())
        {
            numOfPlayers = 4;
        }

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("numOfPlayers", numOfPlayers);
        startActivity(intent);
    }
}
