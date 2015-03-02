package com.example.shyampsunder2003.scrollclient;


import java.net.InetAddress;


public class Node {	
	
	public InetAddress nAddress;
	public int nPort;

	public  Node(InetAddress address) {
		this.nAddress = address;
		this.nPort = 5566;
	}
	public Node(InetAddress address, int port) {
		this.nAddress = address;
		this.nPort = port;
	}
	@Override
	public boolean equals(Object o)
	{
		Node n= (Node) o;
		if(n.nAddress.equals(this.nAddress) && n.nPort == this.nPort)
			return true;
		return false;
	}
}