package wordcount;

import java.rmi.*;
import java.rmi.server;
import java.rmi.RemoteException;

import java.util.*;

public interface iMapper extends Remote {
  
  /* begins a mapping task on the machine where it's called on?
   * what is the name param? is probably useful for master to track mappers and know which ones have terminated?
   */ 
  public iMapper createMapTask(String name) throws RemoteException, AlreadyBoundException;
  
  /* does actual word counting and saves the hashtable somewhere locally??
   * lets master know that it has intermediate data when done?
   * 
   */ 
  public void processInput (String input, iMaster theMaster) throws RemoteException, AlreadyBoundException;


}