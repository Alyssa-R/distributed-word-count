public class Master implements iMaster{

  int maps;
  int doneMaps;
  /*
   * Constructor
   */ 
  
  public Master(String[] arrayOfIPAddresses?) {
  //initialize total number of mappers
  }
  
  /* sends back customized array of reducers to each mapper, based off of the keys the mapper found
   * step 5 (incl parts a and b) 
   */ 
  public iReducer[] getReducers(String[] keys) throws RemoteException, AlreadyBoundException{}
  
  /*does not return anything
   * should it take in a certain mapper to know which one to mark as done?
   * happens to mapper after sending data to reducers
   */ 
  public void markMapperDone() throws RemoteException{
    doneMaps++; //(????!!)
    //if all are done, do something that lets reducers know, starting to near the end of the whole shebang
    
  }
  
  /* 
   * called by reducer onto master after reducer has finished work, to consildate final data
   * stores in the final output file
   * reducer process terminated immediately after this method called
   */  
  public void receiveOutput (String key, int value) throws RemoteException{}
  
  /*
   * main method
   */ 
  
  public void run(){
    /*register and bind
     * go get mapper/reducer objects from IPs of other machines in cluster
     * while (input.hasNextLine){
     *     scan a line, send it to mapper (createMapTask), process it
     * }
     * wait for semaphore that signifies output file is fully written and do something to show of output?
     */ 
  
  }

}