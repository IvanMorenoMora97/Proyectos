package lib;

public class Threads {

	private Threads() {}
	
	public static void sleep(long millis) {
		
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void spend(long millis) {
		
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < millis);
	}	
}

