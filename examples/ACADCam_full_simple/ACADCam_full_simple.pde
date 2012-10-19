import acadcam.*;
ACADCam cam = new ACADCam(this);

void setup() {
  size(800, 600, P3D);
  background(255);
  
  /* - Values in setVarsFull:
   1. Rotation sensitivity on XY plane
   2. Height increase sensitiviy (pitch)
   3. Pan sensitivity
   4. Zoom sensitivity
   5. Camera starting Distance from target
   6. Key to press to orbit / pan (17: CTRL / 16: SHIFT / 18: ALT)
   7. Mouse to press to orbit / pan (1: left click / 2: middle / 3: right)
   8. Extra mouse button to get either pan or orbit
   9. Whether to switch the mouseButtons from pan to orbit and otherwise
   10. Whether to register mouse/key events. Elliminates
   the need for: cam.registerEvents();
   
   setVarsMode: 
    - replaces 6-9 to preconfigured values and removes 10:
    - merges 1 and 2 to one variable
   
   -- AUTOCAD
   cam.setVarsMode(0.002,-0.5,50,300,0); 
    OR
   cam.setVarsFull(-0.002,-0.002,-0.5,50,300,16,2,0,0,1);
   
   -- 3DS MAX
   cam.setVarsMode(0.002,-0.5,50,300,1); 
    OR
   cam.setVarsFull(-0.002,-0.002,-0.5,50,300,17,2,0,0,1);
   
   -- RHINO
   cam.setVarsMode(0.002,-0.5,50,300,2);
    OR
   cam.setVarsFull(-0.002,-0.002,-0.5,50,300,16,3,0,1,1);
   
   -- MAYA
   cam.setVarsMode(0.002,-0.5,50,300,3);
    OR
   cam.setVarsFull(-0.002,-0.002,-0.5,50,300,18,1,2,0,1);
   
   */
  cam.setVarsFull(0.002, -0.002, -0.5, 50, 300, 16, 2, 0, 0, 1);
  println("Roatation(x/y Speed: " + cam.getRotFactor());
  println("Pitch(z) Speed: " + cam.getPitchFactor()) ;
  println("Pan Speed: " + cam.getPanFactor());
  println("Zoom Speed: " + cam.getZoomFactor());
  println("Current orbit key( 17 = CTRL / 16 = SHIFT): " + cam.getKeyCode());
  println("Camera Starting Distance: " + cam.getStartDist());
  println("PVector with Camera Position: " + cam.getCamPos()); // works after .call
  println("PVector with Target Positione: " + cam.getTargetPos()); // works after .call
}
void draw() {
  background(255);
  noFill();
  box(100);
  pushMatrix();
  translate(0,0,50);
  box(10);
  popMatrix();
  cam.call();
}

