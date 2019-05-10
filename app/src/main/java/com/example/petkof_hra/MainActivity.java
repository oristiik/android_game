package com.example.petkof_hra;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
//import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    public void startOnePlayer(View view) {
        Intent startOnePlayer = new Intent(getApplicationContext(), GameActivity.class);
        startOnePlayer.putExtra("mode", "onePlayer");

        startActivity(startOnePlayer);
    }

    public void startTwoPlayers(View view) {
        Intent startTwoPlayers = new Intent(getApplicationContext(), GameActivity.class);
        startTwoPlayers.putExtra("mode", "twoPlayers");

        startActivity(startTwoPlayers);
    }
}
