package cs.DistributedSystem.PubSub.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import org.json.simple.JSONObject;

// takes the IP address of the server as an argument
public class MainClient {

	public static void main(String[] args) {
		
		//String IPaddress = args[0];
		String IPaddress = "127.0.0.1";
		// thread to listen for events
		EventThreadListener listener = new EventThreadListener();
		listener.start();
		
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter User Name to Log in");
		String uID = scanner.nextLine();
		
		//initialize commands and connection classes
		Connection connection = new Connection(IPaddress, uID);
		Commands commands = new Commands(scanner, connection, uID);
		
		boolean exit = false;
		
		//commands the user can use
		while(exit != true){
		System.out.println("Enter a command");
		String input = scanner.nextLine();
			switch(input){
				case "publish":
					commands.publish();
					break;
					
				case "subscribe":
					commands.subscribe();
					break;
					
				case "unsubscribe":
					commands.unsubscribe();
					break;
					
				case "unsubscribeAll":
					commands.unscubscribeAll();
					break;
				
				case "listTopics":
					commands.listTopics();
					break;
				case "listMyTopics":
					commands.listMyTopics();
					break;
					
				case "newUserID":
					commands.retypeUserID();
					break;
					
				case "close":
					exit = true;
					break;
					
				default:
					System.out.println("Invalid command, please enter a valid command");
					System.out.println();
			}
		}
		
		System.out.println("System terminating");
		System.exit(1);
	}

}
