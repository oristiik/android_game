package com.example.petkof_hra;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity
{
    public int score_AI = 0;
    public int score_player = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (getIntent().getStringExtra("mode").equals("onePlayer"))
        {
            setTitle(R.string.onePlayer);
        }
        else if(getIntent().getStringExtra("mode").equals("twoPlayers"))
        {
            setTitle(R.string.twoPlayers);

            setPlayerButtonsClickable(false);

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask()
            {
                @Override
                public void run()
                {
                    autoplay();
                }
            }, 500, 1000);
        }

        setScore_AI(getScore_AI());
        setScore_player(getScore_player());
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        menu.add(0, 0, 0, "Reset");
        menu.add(0, 1, 1, "Konec");

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case 0:
                setScore_AI(0);
                setScore_player(0);

                return true;

            case 1:
                Intent backToMenu = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(backToMenu);

                return true;
        }

        return false;
    }

    public void select(View view)
    {
        ImageView image_rock_AI = findViewById(R.id.imageView_rock_AI);
        ImageView image_paper_AI = findViewById(R.id.imageView_paper_AI);
        ImageView image_scissors_AI = findViewById(R.id.imageView_scissors_AI);
        ImageView image_rock_player = findViewById(R.id.imageView_rock_player);
        ImageView image_paper_player = findViewById(R.id.imageView_paper_player);
        ImageView image_scissors_player = findViewById(R.id.imageView_scissors_player);
        ImageView[] imageViews = {image_rock_AI, image_paper_AI, image_scissors_AI, image_rock_player, image_paper_player, image_scissors_player};

        String[] options = {"rock", "paper", "scissors"};

        Random random = new Random();

        final String selected_player = view.getTag().toString();
        final String selected_AI = options[random.nextInt(options.length)];

        for (ImageView imageView : imageViews)
        {
            String imageViewName = imageView.getResources().getResourceEntryName(imageView.getId());

            if (imageViewName.contains(selected_player) && imageViewName.contains("player") || imageViewName.contains(selected_AI) && imageViewName.contains("AI"))
            {
                animate(imageView);
            }
        }

        getResult(selected_player, selected_AI);
    }

    public void autoplay()
    {
        ImageView image_rock_AI = findViewById(R.id.imageView_rock_AI);
        ImageView image_paper_AI = findViewById(R.id.imageView_paper_AI);
        ImageView image_scissors_AI = findViewById(R.id.imageView_scissors_AI);
        ImageView image_rock_player = findViewById(R.id.imageView_rock_player);
        ImageView image_paper_player = findViewById(R.id.imageView_paper_player);
        ImageView image_scissors_player = findViewById(R.id.imageView_scissors_player);
        final ImageView[] imageViews = {image_rock_AI, image_paper_AI, image_scissors_AI, image_rock_player, image_paper_player, image_scissors_player};

        String[] options = {"rock", "paper", "scissors"};

        Random random = new Random();

        final String selected_player = options[random.nextInt(options.length)];
        final String selected_AI = options[random.nextInt(options.length)];

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        for (ImageView imageView : imageViews)
                        {
                            String imageViewName = imageView.getResources().getResourceEntryName(imageView.getId());

                            if (imageViewName.contains(selected_player) && imageViewName.contains("player") || imageViewName.contains(selected_AI) && imageViewName.contains("AI"))
                            {
                                animate(imageView);
                            }
                        }

                        getResult(selected_player, selected_AI);
                    }
                });
            }
        });
    }

    private void animate(ImageView imageView)
    {
        String imageViewName = imageView.getResources().getResourceEntryName(imageView.getId());

        int distance = 50;

        if (imageViewName.contains("player"))
        {
            distance = -distance;
        }

        final ObjectAnimator move_img = ObjectAnimator.ofFloat(imageView, "translationY", distance);
        move_img.setDuration(500);
        move_img.setRepeatCount(1);
        move_img.setRepeatMode(ObjectAnimator.REVERSE);
        move_img.start();
    }

    private void getResult(final String selected_player, final String selected_AI)
    {
        setPlayerButtonsClickable(false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                switch (selected_player)
                {
                    case "rock":
                        if (selected_AI.equals("paper"))
                        {
                            win_AI();
                        }
                        else if (selected_AI.equals("scissors"))
                        {
                            win_player();
                        }
                        break;

                    case "paper":
                        if (selected_AI.equals("scissors"))
                        {
                            win_AI();
                        }
                        else if (selected_AI.equals("rock"))
                        {
                            win_player();
                        }
                        break;

                    case "scissors":
                        if (selected_AI.equals("rock"))
                        {
                            win_AI();
                        }
                        else if (selected_AI.equals("paper"))
                        {
                            win_player();
                        }
                        break;
                }

                setPlayerButtonsClickable(true);
            }
        }, 1000);
    }

    private void setPlayerButtonsClickable(boolean clickable)
    {
        ImageView image_rock_player = findViewById(R.id.imageView_rock_player);
        ImageView image_paper_player = findViewById(R.id.imageView_paper_player);
        ImageView image_scissors_player = findViewById(R.id.imageView_scissors_player);

        image_rock_player.setClickable(clickable);
        image_paper_player.setClickable(clickable);
        image_scissors_player.setClickable(clickable);
    }

    private void win_AI()
    {
        setScore_AI(getScore_AI() + 1);
    }

    private void win_player()
    {
        setScore_player(getScore_player() + 1);
    }

    public int getScore_AI()
    {
        return score_AI;
    }

    public int getScore_player()
    {
        return score_player;
    }

    public void setScore_AI(int score_AI)
    {
        this.score_AI = score_AI;

        TextView text_score_AI = findViewById(R.id.textView_score_AI);

        if (getIntent().getStringExtra("mode").equals("onePlayer"))
        {
            text_score_AI.setText(R.string.score_AI);
        }
        else if(getIntent().getStringExtra("mode").equals("twoPlayers"))
        {
            text_score_AI.setText(R.string.score_player1);
        }
        text_score_AI.append(" " + getScore_AI());
    }

    public void setScore_player(int score_player)
    {
        this.score_player = score_player;

        TextView text_score_player = findViewById(R.id.textView_score_player);

        if (getIntent().getStringExtra("mode").equals("onePlayer"))
        {
            text_score_player.setText(R.string.score_player);
        }
        else if(getIntent().getStringExtra("mode").equals("twoPlayers"))
        {
            text_score_player.setText(R.string.score_player2);
        }
        text_score_player.append(" " + getScore_player());
    }
}