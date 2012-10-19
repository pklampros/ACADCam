import acadcam.*;
ACADCam cam = new ACADCam(this);

void setup() {
  size(800, 600, P3D);
  background(255);
  
  cam.setRotFactor(0.002);
  cam.setPitchFactor(-0.002) ;
  cam.setPanFactor(-0.5);
  cam.setZoomFactor(50);
  cam.setStartDist(300);
  cam.setKeyCode(16); // 17: CTRL / 16: SHIFT 18: ALT
  cam.setMouseButton(16); // 1: left click / 2: middle / 3: right
  cam.setExtraMouseButton(16);
  // OR: cam.setVarsFull(-0.002, -0.002, -0.5, 50, 300, 16, 2, 0, 0, 1);
  // OR: cam.setVarsMode(-0.002,-0.002,-0.5,50,300,0);
  
  println("Roatation(x/y Speed: " + cam.getRotFactor());
  println("Pitch(z) Speed: " + cam.getPitchFactor()) ;
  println("Pan Speed: " + cam.getPanFactor());
  println("Zoom Speed: " + cam.getZoomFactor());
  println("Current orbit key( 17 = CTRL / 16 = SHIFT): " + cam.getKeyCode());
  println("Camera Starting Distance: " + cam.getStartDist());
  println("PVector with Camera Position: " + cam.getCamPos()); // works after .call
  println("PVector with Target Positione: " + cam.getTargetPos()); // works after .call

  cam.registerEvents();
}
void draw() {
  background(255);
  noFill();
  box(100);
  cam.call();
}

