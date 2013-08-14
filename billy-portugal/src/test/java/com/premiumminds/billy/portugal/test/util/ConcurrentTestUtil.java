package com.premiumminds.billy.portugal.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentTestUtil {

	private int totalThreads;

	public ConcurrentTestUtil(int totalThreads) {
		this.totalThreads = totalThreads;
	}

	public List<Future<?>> runThreads(Callable<?> callable) {
		ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
		ArrayList<Future<?>> futures = new ArrayList<Future<?>>();

		for (int i = 0; i < totalThreads; i++) {
			futures.add(executor.submit(callable));
		}

		executor.shutdown();
		while (!executor.isTerminated()) {

		}

		return futures;
	}

	public void testFuture(Future<?> future) throws Exception {
		future.get();
	}
}
