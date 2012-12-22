/**
 * 
 */
package com.murdock.books.multithread;

import java.awt.Canvas;
import java.awt.Graphics;

/**
 * @author weipeng
 * 
 */
public class ParticleCanvas extends Canvas {
	private static final long serialVersionUID = -5806607576857183543L;
	private Particle[] particles;

	public ParticleCanvas(int size) {
		this.setSize(size, size);
	}

	public synchronized Particle[] getParticles() {
		return particles;
	}

	public synchronized void setParticles(Particle[] particles) {
		this.particles = particles;
	}

	public void paint(Graphics g) {
		Particle[] ps = getParticles();

		for (Particle p : ps) {
			p.draw(g);
		}
	}

}
