package hadoop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.Hashtable;
import java.util.Arrays;


public class Master extends UnicastRemoteObject implements iMaster{

	private static Master master;
	private static String[] clientIds;
	private int mappersCount = 0;
	private int reducersCount = 0;
	private BufferedWriter outputFile;
	private BufferedReader inputFile;
	private Hashtable<String, iReducer> reducerHashtable;
	private static Hashtable<String, iMapper> mapperListings;
	private static Hashtable<String, iReducer> reducerListings;
	private int mapperIndex = 0;
	private int reducerIndex = 0;


	public Master() {
		//not sure what this needs to take in, don't think it needs anything 
	}


	/* sends back customized array of reducers to each mapper, based off of the keys the mapper found
	 * step 5 (incl parts a and b) 
	 */ 
	public iReducer[] getReducers(String[] keys) throws RemoteException, AlreadyBoundException{
		iReducer[] reducer = new iReducer[keys.length];
		for (int i = 0; i < keys.length; i++) {
			iReducer reducer;
			if (!reducerHashtable.containsKey(keys[i])) {
				reducersCount++;
				reducer = reducerListings.get(clientIds[reducerIndex]).createReduceTask(keys[i], master);
				reducerHashtable.put(keys[i], reducer);
				reducerIndex++;
				reducerIndex = reducerIndex%clientIds.length; //if index is above clientIds.length, reset index to 0

			}
		}

	}


	/* does not return anything
	 * happens to mapper after sending data to reducers
	 */ 
	public void markMapperDone() throws RemoteException{
		//if all are done, do something that lets reducers know, start to terminate reducers
		//when terminating reducers, put their key and return value in the output file (unless reducers should directly write to file themselves)
		//so I guess counts how many have done and compares it to have 
		mappersCount--;

		//check if mapping is done
		if (mappersCount == 0) {
			for (int i = 0; i < reducerHastable.size(); i++) {
				reducerMapping.get(reducerMapping[i]).terminate(); //syntax?
			}
		}

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


	private iMapper getNextMapper(String line){
		mappersCount++;
		iMapper mapper = mapperListings.get(clientIds[mapperTotal]).createMapTask(clientIds[mapperTotal]);
		mapper.processInput(line, master);
		mapperIndex++;
		mapperIndex = mapperTotal%clientIds.length; //if index is above clientIds.length, reset index to 0
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

		inputFile = new BufferedReader(new FileReader("./poems/4-MASKS.txt"));

		String line = inputFile.readLine();
		while (line != null) {
			getNextMapper(line);
			line = inputFile.readLine();
		}

		//check if mapping is done
		if (mappersCount == 0) {
			for (int i = 0; i < reducerHastable.size(); i++) {
				reducerMapping.get(reducerMapping[i]).terminate(); //syntax?
			}
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
		try {

			// Initialize info
			String selfPort = args[0];
			String selfIp = InetAddress.getLocalHost().getHostAddress();
			clientIds = Arrays.copyOfRange(args, 1, args.length);

			// Get the local registry
			Registry registry = LocateRegistry.getRegistry(selfIp, Integer.parseInt(selfPort));

			// Set up master
			master = new Master();

			// Bind the remote object's stub in the registry
			registry.bind("Master", master);
			System.out.println("Master ready");

			// Connect from clientIps array


			// Connect to peers from ips in clientIds
			mapperDirectory = new Hashtable<String, iMapper>();
			reducerDirectory = new Hashtable<String, iReducer>();
			for (int i = 0; i < clientIds.length; i++) {
				String[] parts = clientIds[i].split(":");
				String nodeIp = parts[0];
				int nodePort = Integer.parseInt(parts[1]);
				try{
					Registry nodeRegistry = LocateRegistry.getRegistry(nodeIp, nodePort);
					iMapper mapperStub = (iMapper) nodeRegistry.lookup("Mapper");
					iReducer reducerStub = (iReducer) nodeRegistry.lookup("Reducer");
					mapperDirectory.put(clientIds[i], mapperStub);
					reducerDirectory.put(clientIds[i], reducerStub);
				} catch (RemoteException e) {
					System.err.println("Connection exception: " + e.toString());
					e.printStackTrace();
				}
			}
			master.start();
		} catch (Exception e) {
			System.err.println("Connection exception: " + e.toString());
			e.printStackTrace();
		}



	} catch (Exception e) {
		System.err.println("Reducer connection exception: " + e.toString());
		e.printStackTrace();
	}


}


}