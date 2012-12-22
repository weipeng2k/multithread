package com.murdock.books.multithread;

import java.awt.Graphics;
import java.util.Random;

/**
 * @author weipeng
 *
 */
public class Particle {
	protected int x;
	protected int y;
	protected final Random rng = new Random();
	
	public Particle(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public synchronized void move() {
		x = rng.nextInt(100);
		y = rng.nextInt(100);
	}
	
	public void draw(Graphics g) {
		int lx;
		int ly;
		// 此处加锁，目的是确定的位置确定绘画
		synchronized (this) {
			lx = x;
			ly = y;
		}
		g.drawRect(lx, ly, 10, 10);
	}
}
