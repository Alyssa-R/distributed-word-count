import java.rmi.registry.Registry;

public class Mapper implements iMapper {

  private String name;
  
  
  //dummy constructor? idk I feel like createMapTask is the real constructor here
  //no actually createMapTask just exists to create a constructed object!!
  public Mapper(String n) throws RemoteException() {
    name = n;
  }
  
  /* returns newly constructed mapper
   */ 
  public iMapper createMapTask(String name) throws RemoteException, AlreadyBoundException{
	  Mapper mapper = new Mapper(name);
	  return mapper;
  }
  
  /* does actual word counting and saves the hashtable somewhere locally
   * lets master know that it has intermediate data when done
   * 
   */ 
  public void processInput (String input, iMaster theMaster) throws RemoteException, AlreadyBoundException {
    
    /*
     * results = countWords on input
     * get Keys from Hashtable, convert to String array
     * call theMaster to get Reducers (via getReducers) from the key array
     * for each reducer/key:
     *     send corresponding value from hashtable reducer.input(value)
     * mapper mark as done
     */


    Hashtable<String, int> results = countWords(input);
    Set<String> keysSet = results.keySet(); //get Keys as set
    String [] keys = keySet.toArray(new String[keySet.size()]); //convert to String array
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
    String[] words = line.toLowerCase().replaceAll("[^a-zA-Z\\s]", "").split("\\s");
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
  
  
  /*
   * Main taken almost directly from main method on Assignment 3
   */
  public static void main (String args[]) {
	  try {
		  // Initialize account info
		  String selfPort = args[0];
		  String selfIp = InetAddress.getLocalHost().getHostAddress();
		  
		  // Get the local registry
		  Registry registry = LocateRegistry.getRegistry(selfIp, Integer.parseInt(selfPort));
		  
		  // Set up mapper
		  Mapper mapper = new Mapper(); //OK to do?
		  
		  // Bind the remote object's stub in the registry
		  registry.bind("Mapper", mapper);
		  System.out.println("Mapper ready")
	  } catch (Exception e) {
		  System.err.println("Connection exception: " + e.toString());
		  e.printStackTrace();
	  }
  }

}