package com.example.shyampsunder2003.scrollclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class MainActivity extends Activity implements ScrollViewListener{

    private ScrollViewExt scrollView=null;
    private TextView t,t1,t2;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView=(ScrollViewExt)findViewById(R.id.scroll1);
        scrollView.setScrollViewListener(this);
        final DiscoveryManager d=DiscoveryManager.newInstance(getApplicationContext());
        d.setPeerFoundListener(new DiscoveryManager.ActionListener() {
            @Override
            public void onFound(String ipAddress) {

            }
        });
        d.discoverPeers();
        TextView t=(TextView) findViewById(R.id.text1);
        t1=(TextView) findViewById(R.id.text2);
        new Thread(new Runnable() {
            String address[]=null;
            InetAddress iparray[];
            public void run() {
                try {
                    iparray=InetAddress.getAllByName("localhost");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        String s="";
        for(int i=0;i<1000;++i)
        {
            s+=i+"\n";
        }
        t.setText(s);
        new Thread(new Runnable() {
            public void run() {
                DatagramSocket serverSocket=null;
                try {
                    serverSocket = new DatagramSocket(10000);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                byte[] receiveData = new byte[1024];
                byte[] sendData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                while (true)
                {
                    try {
                        serverSocket.receive(receivePacket);

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    String message = null;
                    try {
                        message = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if(count==0)
                    {
                        d.stopDiscovery();
                        count++;
                    }

                    Log.d("Received", message);
                    //String sentence = new String(receivePacket.getData());
                    final int y = Integer.parseInt(message);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            scrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    scrollView.smoothScrollTo(0,y);
                                }
                            });

                        }
                    });
                }
            }
        }).start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        //scrollView.scrollTo(200, 200);
        //String toast = scrollView.getScrollX() + "," + scrollView.getScrollY();
        //Toast t=Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT);
        //t.show();
        //t1=(TextView) findViewById(R.id.text2);
        //t1.setText(String.valueOf(scrollView.getScrollY()));
        //Log.d("Scroll",String.valueOf(scrollView.getScaleY()));
        //Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();

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
