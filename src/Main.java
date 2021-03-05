
public class Main {

	public static void main(String[] args) {
		Countdown countdown = new Countdown();
		
		CountdownThread t1 = new CountdownThread(countdown);
		t1.setName("Thread 1");
		CountdownThread t2 = new CountdownThread(countdown);
		t2.setName("Thread 2");

		//local variables are stored in the thread stack
		//t1 and t2 share the object countdown and can change countdown's instance variables**
		//this means that both threads can see the changes brought about by the other thread
		
		t1.start();
		t2.start();
	}	
}

class Countdown{
	
	private int i;
	
	public void doCountdown() {
		String colour;
		
		switch(Thread.currentThread().getName()) {
		case "Thread 1":
			colour = ThreadColour.ANSI_CYAN;
			break;
		case "Thread 2":
			colour = ThreadColour.ANSI_PURPLE;
			break;
		default:
			colour = ThreadColour.ANSI_GREEN;
		}

		// this effectively forces all other threads to wait (i.e. the for loop cannot be interrupted as before)
		synchronized (this) {
			for(i = 10; i >0; i--) {
				System.out.println(colour + Thread.currentThread().getName() + ": i = "+ i);
			}
		}
		
		//generally only synchronise the minimum needed, otherwise this blocks threads unnecessarily and prevents use of multithreaded
		//optimisations; the methods which can only be run in a synchronised block are wait, notify and notifyAll.

	}
}

class CountdownThread extends Thread{
	
	private Countdown threadCountdown;
	
	public CountdownThread(Countdown countdown) {
		this.threadCountdown = countdown;
		threadCountdown.doCountdown();
	}
	
	public void run() {
		threadCountdown.doCountdown();
	}
}