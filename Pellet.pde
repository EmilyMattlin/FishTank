public abstract class Pellet implements Tankable { //<>// //<>//
  protected PVector location, velocity;
  protected color skin;
  protected float weight;
  protected int age;
  protected boolean dead;
  protected boolean croaked;
  protected FishTank myFishTank;

  Pellet(FishTank p, float w, int a) {
    this.dead = false;
    this.myFishTank = p;
    this.location = new PVector(random((width - myFishTank.getWidth())/2+weight, (width-(width - myFishTank.getWidth())/2)-weight), (height - myFishTank.getHeight())/2+weight, random(-myFishTank.getDepth()/2+weight, myFishTank.getDepth()/2-weight));
    this.velocity = new PVector(0, 1, 0);
    this.skin = color(0, 0, 0);
    this.weight = w;
    this.age = a;
  }

  void update() {
    move();
    if (!dead) {
      show();
    }
  }

  void show() {
    pushMatrix();
    translate(location.x, location.y, location.z);
    fill(skin);
    noStroke();
    sphere(weight);
    popMatrix();
  }//show()

  void move() {

    float x_buffer = (width - myFishTank.getWidth())/2;
    float y_buffer = (height - myFishTank.getHeight())/2;

    if (location.x <x_buffer + weight || location.x > (width - x_buffer)-weight) {
      // x_buffer: (width - 500)/2 = 100 on each side   
      velocity.x *= -1;
    }
    if (location.y <= y_buffer+weight) {
      // y_buffer: (height - 400)/2   = 150 on each side
      location.y = y_buffer+weight;
    }
    if (location.y >= (height - y_buffer)-weight) {
      location.y = (height - y_buffer)-weight;
      velocity.y = 0;
    }
    if (location.z < -myFishTank.getDepth()/2+weight || location.z > myFishTank.getDepth()/2-weight) {
      velocity.z *= -1;
    }
    location.add(velocity);
  }//move()
  //accessors
  void setWeight(float w) {
    weight = w;
  }
  public boolean isDead() {
    dead = true;
    return true;
  }
  public float getVX() { 
    return velocity.x;
  }
  public float getVY() { 
    return velocity.y;
  }
  public float getVZ() { 
    return velocity.z;
  }
  public boolean getDead() {
    return dead;
  }
  public float getX() { 
    return location.x;
  }
  //public boolean hasCollision(Tankable t) {
  //  return true;
  //}
  abstract boolean tryToEat(Tankable r);
  public float getY() { 
    return location.y;
  }
  public float getZ() { 
    return location.z;
  }
  public PVector getLocation() {
    return location;
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
  public boolean isCroaked() {
    dead = true;
    return true;
  }
  public int getAge() { 
    return age;
  }
  public boolean getCroaked() {
    return croaked;
  }
  public PVector getVelocity() {
    return velocity;
  }
  public float getWeight() { 
    return weight;
  }
}//Fish class