package wordcount;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.RemoteException;

import java.util.*;

public interface iReducer extends Remote {
	/*
	 * begins reducer task when called by master on a given key
	 * key is word
	 * master is iMaster instance
	**/
	public iReducer createReduceTask(String key, iMaster master) throws RemoteException, AlreadyBoundException;

	/*
	 * called by Mapper on Reducer when done with count of specific word key
	 * value is count of key by that mapper
	**/
	public void receiveValues(int value) throws RemoteException;

	/*
	 * Once Reducer has sent input to Master via the Master fxn receiveOutput,
	 * terminate Reducer process
	**/
	public int terminate() throws RemoteException;
}