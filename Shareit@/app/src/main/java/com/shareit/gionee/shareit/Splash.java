package com.shareit.gionee.shareit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by gionee on 26/2/15.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle tt) {


        super.onCreate(tt);
        setContentView(R.layout.splash);
        Thread timer = new Thread(){
            public void run(){
                try{
                        sleep(5000);

                }
                catch (InterruptedException e){
                        e.printStackTrace();
                }
                finally {
                    Intent openStartingPoint = new Intent("com.shareit.gionee.shareit.MENU");
                    startActivity(openStartingPoint);
                }
            }

        };
    timer.start();

    }


    @Override
    protected void  onPause(){
    super.onPause();
     finish();
     }

}
