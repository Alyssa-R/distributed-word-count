public class Mapper implements iMapper {

  private String name;
  
  
  //dummy constructor? idk I feel like createMapTask is the real constructor here
  public Mapper(String n){
    name = n;
  }
  
  /* begins a mapping task on the machine where it's called on?
   * what is the name param? is probably useful for master to track mappers and know which ones have terminated?
   */ 
  public iMapper createMapTask(String name) throws RemoteException, AlreadyBoundException{
    return Mapper(name);
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
     */
    
    Hashtable<String, int> results = countWords(line);
    String[] keys = results.getKeys(); //is this kosher/does it exist?
    iReducer[] reducers = theMaster.getReducers(keys);
    iReducer r = reducers[0];
    int val = 0;
    for(int i = 0; i<keys.length; i++){
      r = reducers[i];
      val = results.getValue(keys[i]);
      r.receiveValues(val);
    }
    
    theMaster.markMapperDone();
    
  
  }
  
  
  
  public Hashtable<String, int> countWords(String line){
    Hashtable<String, int> results = new Hashtable<String, int>();
    String[] words = line.split(" ");
    String w = "";
    
    for(int i=0; i<words.length; i++){
      w = words[i];
      if results.containsKey(w){
        results.put(w, table.get(w)+1);
      }
      else{
        results.put(w,1);
      }
    }
    
    return results;
  }
  

}