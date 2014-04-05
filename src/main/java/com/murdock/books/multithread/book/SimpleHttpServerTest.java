package com.murdock.books.multithread.book;

public class SimpleHttpServerTest {
	public static void main(String[] args) throws Exception {
		SimpleHttpServer.setPort(8080);
		SimpleHttpServer.setBasePath("/Users/weipeng2k/Documents/arena/multithread/src/main/resources");
		SimpleHttpServer.start();
	}
}
