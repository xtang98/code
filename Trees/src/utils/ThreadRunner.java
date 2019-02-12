package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadRunner {
	ExecutorService executor;
	public ThreadRunner() {
		executor = Executors.newCachedThreadPool();
	}
	
	public static void main(String[] args) throws InterruptedException {
		Runnable task = ()->{
			for (int i=0; i<8; i++) {
				System.out.println(Thread.currentThread().getName()+"..."+i);
				try {
				Thread.sleep(100);
				} catch (InterruptedException ie) {
					throw new RuntimeException(Thread.currentThread().getName()+"...done!");
				}
				System.out.println();
				return;
			}
		};
		ThreadRunner runner = new ThreadRunner();
		runner.executor.execute(task);
		runner.executor.execute(task);
		runner.executor.execute(task);
		runner.executor.execute(task);
		
		
		for (int i=0; i<10; i++) {
			System.out.println(Thread.currentThread().getName()+"--->"+i);
			Thread.sleep(100);
		}
		runner.executor.awaitTermination(4, TimeUnit.SECONDS);
		for (int i=11; i<20; i++) {
			System.out.println(Thread.currentThread().getName()+"--->"+i);
			Thread.sleep(100);
		}		
	}

}
