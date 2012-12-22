/**
 * 
 */
package com.murdock.books.multithread;

import java.applet.Applet;

/**
 * @author weipeng
 * 
 */
public class ParticleApplet extends Applet {
	private static final long serialVersionUID = 3579412797692445546L;
	protected Thread[] threads = null;
	protected final ParticleCanvas canvas = new ParticleCanvas(100);

	protected Thread makeThread(final Particle p) {
		Runnable runnable = new Runnable() {
			private boolean run = true;

			@Override
			public void run() {
				try {
					while (run) {
						p.move();
						canvas.repaint();
						Thread.sleep(100);
					}
				} catch (InterruptedException ex) {
					run = false;
				}
			}

		};

		return new Thread(runnable);
	}

	@Override
	public void init() {
		this.add(canvas);
	}

	
	@Override
	public void start() {
		int n = 10;
		if (threads == null) {
			Particle[] pa = new Particle[n];
			for (int i = 0; i < n; i++) {
				pa[i] = new Particle(50, 50);
			}
			canvas.setParticles(pa);

			threads = new Thread[n];
			for (int i = 0; i < n; i++) {
				threads[i] = makeThread(pa[i]);
				threads[i].start();
			}
		}
	}

	@Override
	public void stop() {
		if (threads != null) {
			for (Thread t : threads) {
				t.interrupt();
			}
		}
	}

}
