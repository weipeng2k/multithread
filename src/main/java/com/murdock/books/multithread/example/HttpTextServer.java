/**
 * 
 */
package com.murdock.books.multithread.example;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <pre>
 * 请求：
 * GET /p/1845211588 HTTP/1.1
 * 
 * 响应：
 * HTTP/1.1 200 OK
 * Date: Fri, 14 Sep 2012 11:39:26 GMT
 * Content-Type: text/html; charset=GBK
 * Transfer-Encoding: chunked
 * Connection: Keep-Alive
 * Vary: Accept-Encoding
 * tracecode: 23665957650539960842091419, 23665874971177305354091419
 * Content-Encoding: gzip
 * Server: Apache
 * </pre>
 * 
 * @author weipeng
 * 
 */
public class HttpTextServer {

	static ThreadPool<TextHandler> threadPool = new DefaultThreadPool<TextHandler>(
			10);

	static String basePath = "/home/weipeng/project/multithread/src/main/resources";

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(8080);
		Socket socket = null;
		while ((socket = ss.accept()) != null) {
			threadPool.execute(new TextHandler(socket));
		}

		ss.close();
	}

	static class TextHandler implements Runnable {

		private Socket socket;

		public TextHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			String line = null;
			BufferedReader br = null;
			BufferedReader reader = null;
			PrintWriter out = null;
			InputStream in = null;
			try {
				reader = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				String header = reader.readLine();
				String filePath = basePath + header.split(" ")[1];

				if (filePath.contains("jpg") || filePath.contains("ico")) {
					in = new FileInputStream(filePath);
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					try {
						int i = 0;
						while ((i = in.read()) != -1) {
							baos.write(i);
						}
					} catch (Exception  ex) {
						ex.printStackTrace();
					}
					
					byte[] array = baos.toByteArray();
					
					out = new PrintWriter(socket.getOutputStream());

					out.println("HTTP/1.1 200 OK");
					out.println("Content-Type: image/jpeg");
					out.println("Content-Length: " + array.length);
					out.println("Server: SimpleMolly");
					out.println("");
					
					socket.getOutputStream().write(array, 0, array.length);
					
					out.flush();
				} else {

					br = new BufferedReader(new InputStreamReader(
							new FileInputStream(filePath)));
					out = new PrintWriter(socket.getOutputStream());

					out.println("HTTP/1.1 200 OK");
					out.println("Content-Type: text/html; charset=UTF-8");
					out.println("Server: SimpleMolly");
					out.println("");

					while ((line = br.readLine()) != null) {
						out.println(line);
					}
					out.println("CURRENT-THREAD ===> " + Thread.currentThread());
					out.flush();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						br = null;
					}
				}

				if (in != null) {
					try {
						in.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						in = null;
					}
				}

				if (reader != null) {
					try {
						reader.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						reader = null;
					}
				}

				if (out != null) {
					try {
						out.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						out = null;
					}
				}

				if (socket != null) {
					try {
						socket.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						socket = null;
					}
				}
			}
		}
	}

}
