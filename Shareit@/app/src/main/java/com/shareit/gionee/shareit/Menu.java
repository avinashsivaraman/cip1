package com.shareit.gionee.shareit;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by gionee on 27/2/15.
 */
public class Menu extends ListActivity {
    String classes[]={"MainActivity","TextPlay","Mail","Camera","Example4","Example5","Example6"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(Menu.this,android.R.layout.simple_list_item_1,classes));
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        String cheese = classes[position];
        try {

            Class ourClass = Class.forName("com.shareit.gionee.shareit."+cheese);
            Intent our = new Intent(Menu.this,ourClass);
            startActivity(our);
        }
        catch   (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally {

        }
    }


}
