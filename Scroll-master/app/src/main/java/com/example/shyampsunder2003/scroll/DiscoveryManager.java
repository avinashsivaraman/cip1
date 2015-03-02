package com.example.shyampsunder2003.scroll;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Build;
import android.text.format.Formatter;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by navin on 12/12/14.
 */
public  class DiscoveryManager
{
	public static  final  String TAG = "Discovery Manager";

	public static final String TXT_RECORD = "available";
	public static final String SERVICE_INSTANCE = "discovery_manager";
	public static final String SERVICE_TYPE = "wifip2p";
	public  static  final  String ADDRESS = "peer.ipaddress";
	public static  ArrayList<Node>  nodes;

	private String address;
	private ActionListener listener;
	private WifiP2pManager manager;
	private  WifiManager wifiManager;
	private WifiInfo wifiInfo;
	private WifiP2pManager.Channel channel;
	private WifiP2pDnsSdServiceRequest serviceRequest;

	private Context context;
	private Activity activity;

	public  DiscoveryManager(Context context) {
		this.context = context;
	}

	public static DiscoveryManager newInstance(Context context) {
		DiscoveryManager rManager = new DiscoveryManager(context);
		rManager.initialize();
		return  rManager;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void initialize() {

		manager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		channel = manager.initialize(context,context.getMainLooper(),null);
		nodes = new ArrayList<>();
		wifiInfo = wifiManager.getConnectionInfo();
		address = Formatter.formatIpAddress(wifiInfo.getIpAddress());
		broadcast();
	}

	public void broadcast() {
		Map <String,String> record = new HashMap<>();
		record.put(TXT_RECORD,"visible");
		record.put(ADDRESS,address);

		WifiP2pDnsSdServiceInfo serviceInfo = WifiP2pDnsSdServiceInfo.newInstance(SERVICE_INSTANCE,SERVICE_TYPE, record);
		manager.addLocalService(channel,serviceInfo,new WifiP2pManager.ActionListener() {
			@Override
			public void onSuccess() {
				Log.d(TAG,"Local Service Added");
			}

			@Override
			public void onFailure(int reason) {
				Log.d(TAG,"Falied to add Local Service");
			}
		});
	}

	public void setPeerFoundListener(ActionListener listener) {
		this.listener = listener;
	}


	public interface ActionListener {

		public void onFound(String ipAddress);

	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void discoverPeers() {


		WifiP2pManager.DnsSdTxtRecordListener txtRecordListener = new WifiP2pManager.DnsSdTxtRecordListener() {

			@Override
			public void onDnsSdTxtRecordAvailable(
								     String fullDomainName, Map<String, String> record,
								     WifiP2pDevice device) {

				String tempAddress = record.get(ADDRESS);
				if(tempAddress != null)
				{
					try {
						listener.onFound(tempAddress);
						nodes.add(new Node(InetAddress.getByName(tempAddress)));
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
				Log.d(TAG,
					     device.deviceName + " is "
						     + record.get(TXT_RECORD)+ " at "+ tempAddress);
			}
		};

		WifiP2pManager.DnsSdServiceResponseListener  serviceResponseListener = new WifiP2pManager.DnsSdServiceResponseListener() {
			@Override
			public void onDnsSdServiceAvailable(String instanceName, String registrationType, WifiP2pDevice srcDevice) {

				if(instanceName.equalsIgnoreCase(SERVICE_INSTANCE)) {
					Log.d(TAG, "Device "+ srcDevice.deviceAddress+" is found");
				}

			}
		};

		manager.setDnsSdResponseListeners(channel,serviceResponseListener,txtRecordListener);


		WifiP2pDnsSdServiceRequest serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();

		manager.addServiceRequest(channel,serviceRequest,new WifiP2pManager.ActionListener() {
			@Override
			public void onSuccess() {
				Log.d(TAG,"Service Request Added");
			}

			@Override
			public void onFailure(int reason) {
				Log.d(TAG,"Service Request Add Failed");
			}
		});

		manager.discoverServices(channel,new WifiP2pManager.ActionListener() {
			@Override
			public void onSuccess() {
				Log.d(TAG,"Disovery Initiated");
			}

			@Override
			public void onFailure(int reason) {
				Log.d(TAG,"Discovery Falied");
			}
		});

	}

	public void stopDiscovery() {

			manager.clearServiceRequests(channel,new WifiP2pManager.ActionListener() {
				@Override
				public void onSuccess() {
					Log.d(TAG,"Service Request Cleared");
				}

				@Override
				public void onFailure(int reason) {

				}
			});

	}


	public ArrayList<Node> getPeers() {
		return  nodes;
	}


}
