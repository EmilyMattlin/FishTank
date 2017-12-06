FishTank theTank;

void setup() {
  size(700, 700, P3D);
  lights();
  theTank = new FishTank(500, 400, 200);
  theTank.clearObjects();
}

void draw() {
  camera(mouseX, mouseY, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
  background(255);
  theTank.update();
  lights();
}

void mousePressed() {
  if (mouseButton == LEFT) {
    for (int i = 0; i <=random(6); i++) {
      theTank.addBubble(new Bubble(theTank, random(10), new PVector(mouseX+random(-10, 10), mouseY-random(-10, 10), 0)));
    }
    theTank.velocityChange();
  }
  if (mouseButton == RIGHT) {
    for (int i = 0; i <=5; i++) {
      theTank.addPellet(new Special(theTank, random(5), frameCount));
    }
  }
}

void keyPressed() {
  if (key == 'g') {
    theTank.addFish(new Goldfish(theTank, randomGaussian()*0.25+20, frameCount));
  }
  if (key == 'p') {
    theTank.addFish(new Piranha(theTank, randomGaussian()*0.25+35, frameCount));
  }
  if (key == 'w') {
    theTank.addFish(new Whale(theTank, 0, frameCount));
  }
  if (key == 't') {
    theTank.addFish(new ToroidalFin(theTank, random(15, 20), frameCount));
  }

  if (key == 'f') {
    for (int i = 0; i <=5; i++) {
      theTank.addPellet(new Food(theTank, random(5), frameCount));
    }
  }
  if (key == 'x') {
    for (int i = 0; i <=5; i++) {
      theTank.addPellet(new Poison(theTank, random(5), frameCount));
    }
  }
  if (key == 'c') {
    theTank.clearObjects();
  }
}