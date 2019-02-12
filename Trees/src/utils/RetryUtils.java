package utils;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

interface IRetriable<T extends Exception, T2 extends Object>
{
    T2 tryRun ( ) throws T;
}

public class RetryUtils {


	public static <T1 extends Exception, T2 extends Object> T2 retry (int retryCount, long initWait, Log log, IRetriable<T1, T2> runnable) throws T1, InterruptedException {
	    for (int retries = 0;; retries++) {
	        try {
	        	return runnable.tryRun();
	        } catch (Exception e) {
	            if (retries < retryCount) {
	            	if (log != null)
	            		log.error("logging: retry #"+retries+"failed ...");
	            	Thread.sleep(initWait * (1 <<retries));
	                continue;
	            } else {
	                throw e;
	            }
	        }
	    }
	}
	
	public static <V extends Object> V retryCallable(int retryCount, long initWait, Log log, Callable<V> c) throws Exception {
		//Executors.newFixedThreadPool(10);
		for (int retries = 0;; retries++) {
	        try {
	        	return c.call();
	        } catch (Exception e) {
	            if (retries < retryCount) {
	            	if (log != null)
	            		log.error("logging: retry #"+retries+"failed ...");
	            	Thread.sleep(initWait * (1 <<retries));
	                continue;
	            } else {
	                throw e;
	            }
	        }
	    }
	}
	public static void unreliable(Random rand) {
		switch(rand.nextInt(4)) {
			case 0:
				System.out.println("normal path with success!");
				break;
			case 1:
				System.out.println("normal path with error!");
				break;
			default:
				throw new RuntimeException("ooops, exception happens!");
		}
	}
	public static String unreliableWithReturnVal(Random rand) {
		switch(rand.nextInt(4)) {
			case 0:
				//System.out.println("normal path with success!");
				return "success";
			case 1:
				//System.out.println("normal path with error!");
				return "failure";
			default:
				throw new RuntimeException("ooops, exception happens!");
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		Random rand = new Random();
		int numRetries = 2;
		// TODO Auto-generated method stub
		for (int i=0; i<30; i++) {
			try {
				String msg = RetryUtils.retry(numRetries, 0, new Log(), () -> {
					
					return unreliableWithReturnVal(rand);
					
					//use block below to call method without return value;
					//unreliable(rand);
					//return null;
			    });
				if (msg !=null) System.out.println(msg);
			} catch (Exception e) {
				System.out.println("*******all retries failed!");
			}
		}

	}
}

class Log {
	public void error(String msg) {
		System.out.println(msg);
	}
}
