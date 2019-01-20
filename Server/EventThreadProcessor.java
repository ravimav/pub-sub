package cs.DistributedSystem.PubSub.Server;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

/*
 * pull events from the queue and spawn a thread to send out the events
 */

public class EventThreadProcessor extends Thread {

	// event Queue class to access the queue
	ListRepository lists;
	EventQueue eQueue;
	
	public EventThreadProcessor(ListRepository list, EventQueue queue){
		lists = list;
		eQueue = queue;
	}
	
	public void run(){
		while(true){
			
			// this method will wait until notify is called,
			// it will return with an event to pass to a thread
			Event eventFromQueue = eQueue.waitOnQueue();
			//System.out.println("setting up event sender thread");
			EventsEmitter es = new EventsEmitter(lists, eventFromQueue);
			es.start();		
			
		}
	}
	
}
