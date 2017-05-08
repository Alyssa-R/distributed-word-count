public class Reducer implements iReducer {
	iMapper master;
	int total;
	String word;

	public Reducer(String k, iMaster m){
		total = 0;
		master = m;
		word = k;
	}

	public iReducer createReduceTask(String key, iMaster master) throws RemoteException, AlreadyBoundException{
		return Reducer(key, master);
	}

	public void receiveValues(int value) throws RemoteException{
		total+= value;
	}

	public int terminate() throws RemoteException{
		master.receiveOutput(word, total);
		return total;
	}

	/*
	 * Main taken almost directly from main method on Assignment 3
	 */
	public static void main (String args[]) {
		try {
			// Initialize info
			String selfPort = args[0];
			String selfIp = InetAddress.getLocalHost().getHostAddress();

			// Get the local registry
			Registry registry = LocateRegistry.getRegistry(selfIp, Integer.parseInt(selfPort));

			// Set up reducer
			Reducer reducer = new Reducer(); //OK to do?

			// Bind the remote object's stub in the registry
			registry.bind("Reducer", reducer);
			System.out.println("Reducer ready");
		} catch (Exception e) {
			System.err.println("Reducer connection exception: " + e.toString());
			e.printStackTrace();
		}
	}

}