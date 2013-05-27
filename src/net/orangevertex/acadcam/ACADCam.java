/**
 * ACADCam
 * A CAD Camera. A camera resembling the orbit navigation system in CAD-software.
 * http://orange-vertex.net/ACADCam
 *
 * Copyright (C) 2012 Petros Koutsolampros http://orange-vertex.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      Petros Koutsolampros http://orange-vertex.net
 * @modified    08/01/2012
 * @version     0.1.1 (1)
 */

package net.orangevertex.acadcam;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


import processing.core.*;

/**
 * This is a template class and can be used to start a new processing library or
 * tool. Make sure you rename this class as well as the name of the example
 * package 'template' to your own library or tool naming convention.
 * 
 * @example Hello
 * 
 *          (the tag @example followed by the name of an example included in
 *          folder 'examples' will automatically include the example in the
 *          javadoc.)
 * 
 */

public class ACADCam implements MouseWheelListener {

	// myParent is a reference to the parent sketch
	PApplet p;

	private float rotFactor = 0.002f;
	private float pitchFactor = -0.002f;
	private float panFactor = -0.5f;
	private float zoomFactor = 50f;
	private int keyCode = 16;
	private int mouseButton = 2;
	private float camDist = -300;
	private float camRot = (float) (Math.PI * 0.25);
	private float camPitch = (float) (Math.PI * 0.25);
	private PVector panVector = new PVector(0, 0);
	private PVector rotVector = new PVector(0, 0);
	private PVector camCenter = new PVector(0, 0, 0);
	private PVector camPos = new PVector(0, 0, 0);
	private int startX, startY;
	private int mousePressed = 0;
	private int ctrlPressed = 0;
	private int switchPanOrbit = 0;
	private int extra = 0;

	public final static String VERSION = "0.1.1";

	public ACADCam(PApplet theParent) {
		p = theParent;
		welcome();
	}

	private void welcome() {
		System.out
				.println("ACADCam 0.1.1");
	}

	public void registerEvents() {
		p.registerMouseEvent(this);
		p.registerKeyEvent(this);
		p.addMouseWheelListener(this);
	}

	public void call() {

		camRot += rotVector.x;
		if ((camPitch + rotVector.y) < 0.99 && (camPitch + rotVector.y) > 0.01)
			camPitch += rotVector.y;

		PVector camPan = new PVector((panVector.x * (float) Math.sin(Math.PI
				* 0.5 + (Math.PI * camRot)))
				+ (panVector.y * (float) Math.sin(Math.PI * 1.0
						+ (Math.PI * camRot))),
				(panVector.y * (float) Math.cos(Math.PI * 1.0
						+ (Math.PI * camRot)))
						+ (panVector.x * (float) Math.cos(Math.PI * 0.5
								+ (Math.PI * camRot))));
		camPan.mult(0.05f*PVector.dist(camPos, camCenter));
		camCenter.x += camPan.x;
		camCenter.y += camPan.y;
		camPan.x = 0;
		camPan.y = 0;
		panVector.x = 0;
		panVector.y = 0;
		rotVector.x = 0;
		rotVector.y = 0;
		// camPitch = 10;
		p.stroke(0, 0, 0);

		camPos.x = camCenter.x - camDist * (float) Math.sin(Math.PI * camRot)
				* (float) Math.sin(Math.PI * camPitch);
		camPos.y = camCenter.y - camDist * (float) Math.cos(Math.PI * camRot)
				* (float) Math.sin(Math.PI * camPitch);
		camPos.z = camCenter.z - camDist * (float) Math.cos(Math.PI * camPitch);

		// System.out.println(camPos.x);
		p.camera(camPos.x, camPos.y, camPos.z, camCenter.x, camCenter.y,
				camCenter.z, 0, 0, -1);
	}

	/**
	 * return the version of the library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}

	/**
	 * 
	 * @param theA
	 *            the width of test
	 * @param theB
	 *            the height of test
	 */
	public void setRotFactor(float v) {
		rotFactor = v;
	}

	public void setPitchFactor(float v) {
		pitchFactor = v;
	}

	public void setPanFactor(float v) {
		panFactor = v;
	}

	public void setZoomFactor(float v) {
		zoomFactor = v;
	}

	public void setStartDist(float v) {
		camDist = v;
	}

	public void setKeyCode(int v) {
		keyCode = v;
	}

	public void setMouseButton(int v) {
		mouseButton = v;
	}

	public void setExtraMouseButton(int v) {
		extra = v;
	}

	public void setVarsFull(float v1, float v2, float v3, float v4, float v5,
			int v6, int v7, int v8, int s, int i) {
		rotFactor = v1;
		pitchFactor = v2;
		panFactor = v3;
		zoomFactor = v4;
		camDist = v5;
		keyCode = v6;
		mouseButton = v7;
		extra = v8;
		switchPanOrbit = s;
		if (i == 1)
			registerEvents();
	}

	public void setVarsMode(float v1, float v2, float v3, float v4,
			int m) {
		rotFactor = v1;
		pitchFactor = -v1;
		panFactor = v2;
		zoomFactor = v3;
		camDist = v4;
		switch (m) {
		case 0: // AUTOCAD
			keyCode = 16;
			mouseButton = 2;
			extra = 0;
			switchPanOrbit = 0;
			break;
		case 1: // MAX
			keyCode = 17;
			mouseButton = 2;
			extra = 0;
			switchPanOrbit = 0;
			break;
		case 2: // RHINO
			keyCode = 16;
			mouseButton = 3;
			extra = 0;
			switchPanOrbit = 1;
			break;
		case 3: // MAYA
			keyCode = 18;
			mouseButton = 1;
			extra = 2;
			switchPanOrbit = 0;
			break;
		}

		registerEvents();
	}

	public float getRotFactor() {
		return rotFactor;
	}

	public float getPitchFactor() {
		return pitchFactor;
	}

	public float getPanFactor() {
		return panFactor;
	}

	public float getZoomFactor() {
		return zoomFactor;
	}

	public float getStartDist() {
		return camDist;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public int getMouseButton() {
		return mouseButton;
	}

	public int getExtraMouseButton() {
		return extra;
	}

	public PVector getCamPos() {
		return camPos;
	}

	public PVector getTargetPos() {
		return camCenter;
	}

	public void keyEvent(final KeyEvent e) {
		switch (e.getID()) {
		case KeyEvent.KEY_PRESSED:
			if (e.getKeyCode() == keyCode)
				ctrlPressed = 1;
			break;
		case KeyEvent.KEY_RELEASED:
			if (e.getKeyCode() == keyCode)
				ctrlPressed = 0;
			break;
		}
	}

	public void mouseEvent(java.awt.event.MouseEvent e) {
		switch (e.getID()) {
		case MouseEvent.MOUSE_PRESSED:
			if (e.getButton() == mouseButton) {
				startX = e.getX();
				startY = e.getY();
				mousePressed = 1 + ctrlPressed;
			}
			if (extra > 0 && e.getButton() == extra) {
				startX = e.getX();
				startY = e.getY();
				mousePressed = 1 + ctrlPressed + extra;
			}
			break;
		case MouseEvent.MOUSE_RELEASED:
			if (e.getButton() == mouseButton) {
				panVector.x = 0;
				panVector.y = 0;
				rotVector.x = 0;
				rotVector.y = 0;
				mousePressed = 0;
			}
			break;
		case MouseEvent.MOUSE_CLICKED:
			// do something for mouse clicked
			break;
		case MouseEvent.MOUSE_DRAGGED:
			if (mousePressed > 0) {
				if (mousePressed == 1 && extra == 0) {
					if (switchPanOrbit == 0) {
						panVector.x = panFactor * (startX - e.getX());
						panVector.y = -1*panFactor * (startY - e.getY());
					} else {
						rotVector.x = rotFactor * (startX - e.getX());
						rotVector.y = pitchFactor * (startY - e.getY());

					}
				}
				if (mousePressed == 2) {
					if (switchPanOrbit == 0) {
						rotVector.x = rotFactor * (startX - e.getX());
						rotVector.y = pitchFactor * (startY - e.getY());
					} else {
						panVector.x = panFactor * (startX - e.getX());
						panVector.y = -1*panFactor * (startY - e.getY());
					}
				}
				if (extra > 0 && mousePressed == 2 + extra) {
					if (switchPanOrbit == 0) {
						panVector.x = panFactor * (startX - e.getX());
						panVector.y = -1*panFactor * (startY - e.getY());

					} else {
						rotVector.x = rotFactor * (startX - e.getX());
						rotVector.y = pitchFactor * (startY - e.getY());
					}
				}
				startX = e.getX();
				startY = e.getY();
			}
			break;
		case MouseEvent.MOUSE_MOVED:
			// umm... forgot
			break;
		}
	}

	public void keyReleased(java.awt.event.KeyEvent e) {

	}

//	void mouseWheel(int delta) {
//
//		if (camDist > 1.0)
//			camDist += zoomFactor * delta;
//		else
//			camDist = 1.01f;
//
//	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (camDist > 1.0)
			camDist += (0.5f*camDist)* zoomFactor * e.getWheelRotation();
		else
			camDist = 1.01f;

	}

}
