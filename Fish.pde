public abstract class Fish implements Tankable {

  public PVector location, velocity;
  protected color skin;
  protected color scales;
  protected float weight;
  protected int age;
  protected boolean dead;
  protected boolean croaked;
  protected FishTank myFishTank;

  Fish(FishTank f, float w, int a) {
    this.dead = false;
    this.croaked = false;
    this.myFishTank = f;
    this.location = new PVector(width/2, height/2, 0);
    this.velocity = new PVector(random(10), 1, -1);
    this.skin = color(random(255), 0, 0, 255);
    this.scales = color(255, 255, 255, 0);
    this.weight = w;
    this.age = a;
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
    stroke(scales);
    sphere(weight);
    //box(20, 10, 30);
    popMatrix();
  }//show()

  //accessors
  void setWeight(float w) {
    weight = w;
  }

  abstract void move();
  abstract boolean tryToEat(Tankable r);
  //public abstract boolean hasCollision(Tankable t);
  //public boolean hasCollision(Tankable f) {
  //  println("gotit");
  //  return true;
  //  //myPellet.remove(p);
  //  //remove(f);
  //}

  public float getVX() { 
    return velocity.x;
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
  public boolean isCroaked() {
    croaked = true;
    return true;
  }
  public PVector getLocation() {
    return location;
  }
  public PVector getVelocity() {
    return velocity;
  }
  public boolean getDead() {
    return dead;
  }
  public void setVX(float v) {
    velocity.x = v;
  }
  public void setVY(float v) {
    velocity.y = v;
  }
  public void setVZ(float v) {
    velocity.z = v;
  }
  public int getAge() {
    return age;
  }
}//Fish class