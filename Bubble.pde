public class Bubble implements Tankable {
  public PVector location, velocity;
  protected color skin;
  protected color scales;
  protected float weight;
  protected int age;
  protected boolean dead, croaked;
  protected FishTank myFishTank;

  Bubble(FishTank f, float w, PVector loc) {
    this.dead = false;
    this.myFishTank = f;
    this.location = loc;
    this.velocity = new PVector(0, random(-.2, -1), 0);
    this.skin = color(100, 102, random(200, 255), 20);
    this.weight = w;
    this.age = frameCount;
  }
  //void addFish
  void update() {
    move();
    if (!dead) {
      show();//what did her mean by object reference"
    }
  }
  void show() {
    pushMatrix();
    translate(location.x, location.y, location.z);
    fill(skin);
    noStroke();
    if (weight!=100) {
      sphere(weight);
    } else {
      fill(100, 100, 100, 255);
      sphere(20);
    }
    popMatrix();
  }//show()

  //accessors
  void setWeight(float w) {
    weight = w;
  }
  public boolean isCroaked() {
    if (location.y >= (height - myFishTank.getHeight())/2) {
      velocity.y -=.01;
    } else {
      velocity.y = 0;
    }
    return true;
  }
  void move() {
    if (weight == 100) {
      theTank.addFish(new Goldfish(theTank, 10, frameCount));
      weight = 1;
    }
    if (weight == 150) {
      theTank.addFish(new ToroidalFin(theTank, 25, frameCount));
      weight = 1;
    }
    if (weight == 200) {
      theTank.addFish(new Whale(theTank, 40, frameCount));
      weight = 1;
    }
    if (weight ==-1) {
      float noiseScale = 0.02;
      for (int x=0; x < width; x++) {
        float noiseVal = noise((mouseX+x)*noiseScale, mouseY*noiseScale);
        skin = color(noiseVal*random(255), noiseVal*random(255), noiseVal*random(255));
      }
      //fill(skin);
      weight = 15;
    }
    if (location.y >= (height - myFishTank.getHeight())/2) {
      velocity.y -=.01;
    } else {
      velocity.y = 0;
      isDead();
    }
    location.add(velocity);
  };

  //public abstract boolean hasCollision(Tankable t);
  public boolean hasCollision(Tankable f) {
    println("gotit");
    return true;
    //myPellet.remove(p);
    //remove(f);
  }
  public float getVX() { 
    return velocity.x;
  }
  public PVector getLocation() {
    return location;
  }
  public float getVY() { 
    return velocity.y;
  }
  public float getVZ() { 
    return velocity.z;
  }
  public float getX() { 
    return location.x;
  }
  public float getY() { 
    return location.y;
  }
  public float getZ() { 
    return location.z;
  }
  public float getWeight() { 
    return weight;
  }
  public boolean isDead() {
    dead = true;
    return true;
  }
  public boolean getCroaked() {
    return croaked;
  }
  public PVector getVelocity() {
    return velocity;
  }
  public void setVX(float v) {
    velocity = velocity;
  }
  public void setVY(float v) {
    velocity = velocity;
  }
  public void setVZ(float v) {
    velocity = velocity;
  }
  public boolean getDead() {
    return dead;
  }

  public int getAge() {
    return age;
  }
  public boolean tryToEat(Tankable r) {
    return isDead();
  }
}//Fish class