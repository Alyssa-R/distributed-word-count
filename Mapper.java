public class Mapper implements iMapper {

  /* begins a mapping task on the machine where it's called on?
   * what is the name param? is probably useful for master to track mappers and know which ones have terminated?
   */ 
  public iMapper createMapTask(String name) throws RemoteException, AlreadyBoundException{
  
  }
  
  /* does actual word counting and saves the hashtable somewhere locally??
   * lets master know that it has intermediate data when done?
   * 
   */ 
  public void processInput (String input, iMaster theMaster) throws RemoteException, AlreadyBoundException {
    
    /*
     * count the words
     * make a hashtable --> array of keys to send to getReducers
     * getReducers from master
     * for each reducer/key:
     *     send corresponding value from hashtable reducer.input(value)
     * mapper mark as done
  
  }

}