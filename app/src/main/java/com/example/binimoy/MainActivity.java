package com.example.binimoy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();
        ProgressBar pp = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        pp.setIndeterminateDrawable(doubleBounce);

        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        Thread thread=new Thread(new Runnable(){
            @Override
            public void run(){
                doWork();
                next();
            }
        });
        thread.start();
    }

    public void doWork(){

        for(progress=20; progress<=130;progress= progress+20)
            try{

                Thread.sleep(500);
                progressBar.setProgress(progress);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
    }

    public void next() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
        finish();
    }
}
