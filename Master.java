import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class Master implements iMaster{
	
	private Master master;
	private static String[] clientIds;
	private int mappersCount = 0;
	private int reducersCount = 0;
	private BufferedWriter outputFile;

  
  public Master() {
   //not sure what this needs to take in, don't think it needs anything 
  }
  
  
  /* sends back customized array of reducers to each mapper, based off of the keys the mapper found
   * step 5 (incl parts a and b) 
   */ 
  public iReducer[] getReducers(String[] keys) throws RemoteException, AlreadyBoundException{}
  
  /* does not return anything
   * happens to mapper after sending data to reducers
   */ 
  public void markMapperDone() throws RemoteException{
    //if all are done, do something that lets reducers know, start to terminate reducers
    //when terminating reducers, put their key and return value in the output file (unless reducers should directly write to file themselves)
    //so I guess counts how many have done and compares it to have 
    
  }
  
  /* 
   * called by reducer onto master after reducer has finished work, to consolidate final data
   * stores in the final output file
   * reducer process terminated immediately after this method called
   */  
  public void receiveOutput (String key, int value) throws RemoteException{
    //this has to do with I/O and I don't know how right now, I think there's a class called FileWriter?
	  reducersCount--;
	  
	  //check if file needs to be created
	  if (outputFile == null) {
		  outputFile = new BufferedWriter(new FileWriter("wordcount.txt"));
	  }
	  outputFile.write(key + " : " + value + " ");
  
  }
  

  private iMapper getNextMapper(){
    //createMapTask??
  }
  
  
  public void run(iMapper map, iReducer reduce){
    /*
     * create Master instance to get mapper/reducer objects from IPs of other machines (constructor)
     * 
     * while (input.hasNextLine){
     *     scan a line, send it to mapper (createMapTask), process it
     *     (mapper sends it to a reducer)
     * }
     * wait for semaphore that signifies output file is fully written?-->deal with file I/O
     * 
     */ 
    
    
    //I have no memory of the below code
    Scanner scan_file = new Scanner();
    String next_line = "";
    
    while(scan_file.hasNext()){
      next_line = scan_file.nextLine();
      iMapper mapper = getNextMapper();
      mapper.processInput(next_line);
    }

  }
  
  //actual main
  public static void main(String[] args){
  /*    
    register and bind this part of the rmi
    take in ip addresses of token mapper and reducer from args
        get their stubs (mapper/reducer objs)
    pass those objs to run()
  */

  
  }
  

}