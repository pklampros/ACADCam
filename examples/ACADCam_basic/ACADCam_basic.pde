import acadcam.*;
ACADCam cam = new ACADCam(this);

void setup() {
  size(800, 600, P3D);
  background(255);
  cam.registerEvents();
}
void draw() {
  background(255);
  noFill();
  box(100);
  cam.call();
}

