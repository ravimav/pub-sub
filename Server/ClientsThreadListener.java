package cs.DistributedSystem.PubSub.Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ClientsThreadListener extends Thread {
	
	int port = 0;
	ListRepository lists;
	EventQueue eventQueue;
	int generatedPort = 10000;
	
	public ClientsThreadListener(int portNumber, ListRepository list, EventQueue EventQueue){
		port = portNumber;
		lists = list;
		eventQueue = EventQueue;
	}
	
	
	//set up connection for accepting incoming connections, then generate port to 
	//communicate on and spawn thread to listen on that port
	public void run(){
		
		try {
			
			//create socket
			ServerSocket serverSocket = new ServerSocket(port);
			
			//loop and accept connections
			while(true){
				Socket clients_Socket = serverSocket.accept();
				//System.out.println("accepted new connection");
				DataOutputStream dataToClient = new DataOutputStream(clients_Socket.getOutputStream());

				//generate port to communicate on 
				if(generatedPort == 65530){
					generatedPort = 10000;
				}
				
				//spawn thread and send port to client
				ClientsThreadHandler thread1 = new ClientsThreadHandler(generatedPort, lists, eventQueue);
				thread1.start();
				String sendPort = Integer.toString(generatedPort);
				dataToClient.writeBytes(sendPort + "\n");
				
				generatedPort++;
				
				dataToClient.close();
				clients_Socket.close();
				//System.out.println("listener thread: waiting for new connections");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	

}