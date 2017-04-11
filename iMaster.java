import java.rmi.*;
import java.rmi.server;
import java.rmi.RemoteException;

import java.util.*;


public interface iMaster extends Remote {

  /* sends back customized array of reducers to each mapper, based off of the keys the mapper found
   * step 5 (incl parts a and b) 
   */ 
  public iReducer[] getReducers(String[] keys) throws RemoteException, AlreadyBoundException;
  
  /*does not return anything
   * should it take in a certain mapper to know which one to mark as done?
   * happens to mapper after sending data to reducers
   */ 
  public void markMapperDone() throws RemoteException;
  
  /* 
   * called by reducer onto master after reducer has finished work, to consildate final data
   * stores in the final output file
   * reducer process terminated immediately after this method called
   */  
  public void receiveOutput (String key, int value) throws RemoteException;

}