package com.example.shyampsunder2003.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends Activity implements ScrollViewListener{

    private ScrollViewExt scrollView=null;
    private TextView t,t1;
    private LinkedList<String> list=new LinkedList<String>();
    DiscoveryManager d;
    int count=0;
    static int prev;
    //public LinkedList<Integer> l = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView=(ScrollViewExt)findViewById(R.id.scroll1);
        scrollView.setScrollViewListener(this);
        TextView t=(TextView) findViewById(R.id.text1);
        t1=(TextView) findViewById(R.id.text2);
        String s="";
        d=DiscoveryManager.newInstance(getApplicationContext());
        d.setPeerFoundListener(new DiscoveryManager.ActionListener() {
            @Override
            public void onFound(String ipAddress) {
                list.addFirst(ipAddress);
                Toast.makeText(getApplicationContext(),String.valueOf(list.size())+" is connected",Toast.LENGTH_SHORT).show();
                Log.d("IPAddress",ipAddress);
            }
        });
        d.discoverPeers();
        for(int i=0;i<1000;++i)
        {
            s+=i+"\n";
        }
        t.setText(s);
        /*scrollView=(ScrollViewExt)findViewById(R.id.scroll1);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, 47000);
            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onScrollChanged(final ScrollViewExt scrollView, int x, int y, int oldx, int oldy)  {
        //scrollView.scrollTo(200, 200);
        //String toast = scrollView.getScrollX() + "," + scrollView.getScrollY();
        //Toast t=Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT);
        //t.show();
        //t1=(TextView) findViewById(R.id.text2);
        //t1.setText(String.valueOf(scrollView.getScrollY()));
        //Log.d("Scroll",String.valueOf(scrollView.getScaleY()));
        //Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
        if(count==0)
        {
            d.stopDiscovery();
            count++;
        }

        new Thread(new Runnable() {
            InetAddress IPAddress;

            public void run() {
                DatagramSocket clientSocket = null;
                try {
                    clientSocket = new DatagramSocket();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                byte[] sendData = new byte[1024];
                String sentence = String.valueOf(scrollView.getScrollY());
                if(scrollView.getScrollY()/100!=prev) {
                    Log.d("Division",String.valueOf(scrollView.getScrollY()/100));
                    prev= scrollView.getScrollY()/100;
                    sendData = sentence.getBytes();
                    for (int i = 0; i < list.size(); ++i) {

                        try {
                            IPAddress = InetAddress.getByName(list.get(i));
                        } catch (UnknownHostException e1) {
                            e1.printStackTrace();
                        }
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 10000);
                        try {
                            clientSocket.send(sendPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("Packet Sent", String.valueOf(scrollView.getScrollY()) + String.valueOf(IPAddress));
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
