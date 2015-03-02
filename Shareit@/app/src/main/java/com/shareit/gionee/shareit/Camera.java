package com.shareit.gionee.shareit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by gionee on 28/2/15.
 */
public class Camera extends Activity implements View.OnClickListener{

    final static int cameraData = 0;
    ImageView iv;
    Bitmap bmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        initialize();
    }
    private void initialize(){
         iv = (ImageView)findViewById(R.id.ivReturnedPic);
        ImageButton ib = (ImageButton)findViewById(R.id.ibTakePic);
        Button b = (Button)findViewById(R.id.bSetWall);
        b.setOnClickListener(this);
        ib.setOnClickListener(this);


    }

       public void onClick(View v)
       {
           switch (v.getId()){
               case R.id.bSetWall:
                   try
                   {
                   getApplicationContext().setWallpaper(bmp);
                   }
                   catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                   break;

               case R.id.ibTakePic:
                   Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   startActivityForResult(i,cameraData);

                         break;
           }
       }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            Bundle extra = data.getExtras();
            bmp = (Bitmap)extra.get("data");
            iv.setImageBitmap(bmp);

        }
    }
}
