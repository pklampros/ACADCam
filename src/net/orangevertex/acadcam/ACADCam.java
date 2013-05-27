/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
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
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package net.orangevertex.acadcam;

import processing.event.KeyEvent;
import processing.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


import processing.core.*;


public class ACADCam implements MouseWheelListener {

	// myParent is a reference to the parent sketch
	PApplet p;

	private float rotFactor = 0.002f;
	private float pitchFactor = -0.002f;
	private float panFactor = -0.5f;
	private float zoomFactor = 50f;
	private int keyCode = 16;
	private int mouseButton = 3; 
	//processing 2.0 changes the buttons L:37 M/W:3 R:39
	private float camDist = -100;
	private float prevCamDist = -100;
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
//	private boolean is3D = false;

	public final static String VERSION = "##library.prettyVersion##";

	public ACADCam(PApplet theParent) {
		p = theParent;
		welcome();
	}

	private void welcome() {
		System.out
				.println("##library.name## ##library.prettyVersion##");
	}

	public void registerEvents() {
//		p.registerMouseEvent(this);
//		p.registerKeyEvent(this);
		p.registerMethod("mouseEvent", this);
        p.registerMethod("keyEvent", this);
		p.addMouseWheelListener(this);
		callOriginal();
	}
	public void callOriginal() {
		camPos = new PVector(p.width*0.5f,p.height*0.5f,(p.height*0.5f) / p.tan(p.PI*30.0f / 180.0f));
		camCenter = new PVector(p.width*0.5f,p.height*0.5f,0f);
		p.camera(camPos.x, camPos.y, camPos.z, camCenter.x, camCenter.y, camCenter.z, 0f, 1f, 0f);
	}
	public void call2D() {
		calcCam(false);
		p.camera(camCenter.x, camCenter.y, camPos.z, camCenter.x, camCenter.y,
				camCenter.z, 0, 1, 0);
	}
	public void call3D() {
		calcCam(true);
		p.camera(camPos.x, camPos.y, camPos.z, camCenter.x, camCenter.y,
				camCenter.z, 0, 0, -1);
	}
	private void calcCam(boolean is3D) {
		if(rotVector.mag() != 0 || panVector.mag() != 0 || camDist != prevCamDist) {
		prevCamDist = camDist;
		camRot += rotVector.x;
		if ((camPitch + rotVector.y) < 0.99 && (camPitch + rotVector.y) > 0.01)
			camPitch += rotVector.y;
		if(is3D) {
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
		} else {
			camCenter.x -= 0.05f*(camPos.z - camCenter.z)*panVector.x;
			camCenter.y += 0.05f*(camPos.z - camCenter.z)*panVector.y;
		}
		panVector.x = 0;
		panVector.y = 0;
		rotVector.x = 0;
		rotVector.y = 0;

		camPos.x = camCenter.x - camDist * (float) Math.sin(Math.PI * camRot)
				* (float) Math.sin(Math.PI * camPitch);
		camPos.y = camCenter.y - camDist * (float) Math.cos(Math.PI * camRot)
				* (float) Math.sin(Math.PI * camPitch);
		camPos.z = camCenter.z - camDist * (float) Math.cos(Math.PI * camPitch);
		}
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
	public void setCamPos(PVector v) {
		camPos = v.get();
	}

	public void setTargetPos(PVector v) {
		camCenter = v.get();
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
		return camPos.get();
	}

	public PVector getTargetPos() {
		return camCenter.get();
	}

	public void keyEvent(final KeyEvent e) {
		switch (e.getKey()) {
		case KeyEvent.PRESS:
			if (e.getKeyCode() == keyCode)
				ctrlPressed = 1;
			break;
		case KeyEvent.RELEASE:
			if (e.getKeyCode() == keyCode)
				ctrlPressed = 0;
			break;
		}
	}

	public void mouseEvent(processing.event.MouseEvent e) {
		
		switch (e.getAction()) {
		case MouseEvent.PRESS:
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
		case MouseEvent.RELEASE:
			if (e.getButton() == mouseButton) {
				panVector.x = 0;
				panVector.y = 0;
				rotVector.x = 0;
				rotVector.y = 0;
				mousePressed = 0;
			}
			break;
//		case MouseEvent.CLICK:
//			// do something for mouse clicked
//			break;
		case MouseEvent.DRAG:
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
		case MouseEvent.MOVE:
			// umm... forgot
			break;
		case MouseEvent.WHEEL:
			System.out.println("the wheel works!");
			break;
		}
	}

	public void keyReleased(processing.event.KeyEvent e) {

	}

//	void mouseWheel(int delta) {
//
//		if (camDist > 1.0)
//			camDist += zoomFactor * delta;
//		else
//			camDist = 1.01f;
//
//	}
	public PVector getNewMouse() {
		  float ph = (p.height*0.5f) / p.tan(p.PI*0.1666667f);
		  float phI = 1 / ph;
		 return new PVector(camCenter.x + p.mouseX -p.width*0.5f + (camPos.z - ph)*(p.mouseX-p.width*0.5f)*phI,camCenter.y + p.mouseY -p.height*0.5f+ (camPos.z - ph)*(p.mouseY-p.height*0.5f)*phI);
		 
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (camDist > 1.0) {
			camDist += (0.5f*camDist)* zoomFactor * e.getWheelRotation();
			PVector newMouse = getNewMouse();
//			PVector toAdd = new PVector(newMouse.x - p.width*0.5f,newMouse.y - p.height*0.5f);
			PVector toAdd = new PVector(newMouse.x,newMouse.y);
			toAdd.sub(new PVector(camCenter.x,camCenter.y));
			toAdd.mult(-0.05f * e.getWheelRotation());
			camPos.add(toAdd);
//			camPos.mult(0.5f);
			camCenter.add(toAdd);
//			camCenter.mult(0.5f);
		}
		else
			camDist = 1.01f;

	}

}
