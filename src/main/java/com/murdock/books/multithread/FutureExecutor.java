/**
 * 
 */
package com.murdock.books.multithread;


/**
 * @author weipeng
 *
 */
public class FutureExecutor {
	
	public FutureTask submit(String id) {
		FutureTask ft = new FutureTask();
		Thread t = new Thread(new QueryJob(id, ft));
		t.start();
		
		return ft;
	}
	
	class QueryJob implements Runnable {
		
		private String id;
		private FutureTask ft;
		
		public QueryJob(String id, FutureTask ft) {
			this.id = id;
			this.ft = ft;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("result for " + id);
			ft.put(new Object());
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		FutureExecutor fe = new FutureExecutor();
		FutureTask ft = fe.submit("1");
		FutureTask ft1 = fe.submit("2");
		
		long start = System.currentTimeMillis();
		Object obj = ft.get(3000);
		
		System.out.println("cost : " + (System.currentTimeMillis() - start));
		Object obj2 = ft1.get(3000);
		
		System.out.println(obj);
		System.out.println(obj2);
		System.out.println("cost : " + (System.currentTimeMillis() - start));
		
	}
}
