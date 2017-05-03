
public class Reducer implements iReducer {
  iMapper master;
  int total;
  String key;
  
  public Reducer(String k, iMaster m){
    total = 0;
    master = m;
    key = k;
  }
  
  public iReducer createReduceTalk(String key, iMaster master) throws RemoteException, AlreadyBoundException{
    return Reducer(key, master);
  }
  
  public void receiveValues(int value) throws RemoteException{
    total+= value;
  }
  
  public int terminate() throws RemoteException{
    //should this write to a file?
    return total;
  }
  
  // missing a main method, but also don't think one is needed tbh, master can call the start and tell the
  //result of terminate to write to the correct place

}