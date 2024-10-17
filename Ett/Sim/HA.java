package Sim;

import java.util.ArrayList;

public class HA extends Node {
	 private SimEnt _peer;
	 private int intId;
	 private int networkId;
	 private NetworkAddr oldObject;
	 private NetworkAddr _id;
	 

    public HA(int network, int node, int intid, NetworkAddr id, ArrayList<HA> LN){
        super(network, node);
        this._id = id;
        this.setNetworkId(intId);
        LN.add(this);
        
    }


    // Override the recv method to handle message forwarding based on care-of address
    @Override
    public void recv(SimEnt src, Event ev) {
    	if (ev instanceof Message) {
    		send(_peer, new Message(_id, new NetworkAddr(oldObject.networkId(), oldObject.nodeId()), ((Message) ev).seq()),0);
    		System.out.println("message handled by HA, sent to MN");
    	}
    	
       
    }
    public void setPeer(SimEnt i) {
    	this._peer =i;
    }
    
    public int get_intId() {
    	return intId;
    }


	public int networkId() {
		return networkId;
	}


	public void setNetworkId(int networkId) {
		this.networkId = networkId;
	}
}
