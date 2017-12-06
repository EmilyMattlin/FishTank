import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class FishTales3D extends PApplet {

FishTank theTank;

public void setup() {
  
  lights();
  theTank = new FishTank(500, 400, 200);
  theTank.clearObjects();
}

public void draw() {
  camera(mouseX, mouseY, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
  background(255);
  theTank.update();
  lights();
}

public void mousePressed() {
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

public void keyPressed() {
  if (key == 'g') {
    theTank.addFish(new Goldfish(theTank, randomGaussian()*0.25f+20, frameCount));
  }
  if (key == 'p') {
    theTank.addFish(new Piranha(theTank, randomGaussian()*0.25f+35, frameCount));
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
public class Bubble implements Tankable {
  public PVector location, velocity;
  protected int skin;
  protected int scales;
  protected float weight;
  protected int age;
  protected boolean dead, croaked;
  protected FishTank myFishTank;

  Bubble(FishTank f, float w, PVector loc) {
    this.dead = false;
    this.myFishTank = f;
    this.location = loc;
    this.velocity = new PVector(0, random(-.2f, -1), 0);
    this.skin = color(100, 102, random(200, 255), 20);
    this.weight = w;
    this.age = frameCount;
  }
  //void addFish
  public void update() {
    move();
    if (!dead) {
      show();//what did her mean by object reference"
    }
  }
  public void show() {
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
  public void setWeight(float w) {
    weight = w;
  }
  public boolean isCroaked() {
    if (location.y >= (height - myFishTank.getHeight())/2) {
      velocity.y -=.01f;
    } else {
      velocity.y = 0;
    }
    return true;
  }
  public void move() {
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
      float noiseScale = 0.02f;
      for (int x=0; x < width; x++) {
        float noiseVal = noise((mouseX+x)*noiseScale, mouseY*noiseScale);
        skin = color(noiseVal*random(255), noiseVal*random(255), noiseVal*random(255));
      }
      //fill(skin);
      weight = 15;
    }
    if (location.y >= (height - myFishTank.getHeight())/2) {
      velocity.y -=.01f;
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
public abstract class Fish implements Tankable {

  public PVector location, velocity;
  protected int skin;
  protected int scales;
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
  public void update() {
    move();
    if (!dead) {
      show();//what did her mean by object reference"
    }
  }
  public void show() {
    pushMatrix();
    translate(location.x, location.y, location.z);
    fill(skin);
    stroke(scales);
    sphere(weight);
    //box(20, 10, 30);
    popMatrix();
  }//show()

  //accessors
  public void setWeight(float w) {
    weight = w;
  }

  public abstract void move();
  public abstract boolean tryToEat(Tankable r);
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
public int ammonia = 0;
class FishTank {
  private float myWidth, myLength, myHeight;
  private ArrayList<Tankable> objects;
  private ArrayList<Tankable> myFish;
  private ArrayList<Tankable> myBubble;
  private ArrayList<Tankable> myPellet;
  private int waterColor, waterStroke;

  FishTank(float w, float l, float h) {
    this.myWidth = w;
    this.myLength = l;
    this.myHeight = h;
    this.objects = new ArrayList<Tankable>();
    this.myFish = new ArrayList<Tankable>();
    this.myPellet = new ArrayList<Tankable>();
    this.myBubble = new ArrayList<Tankable>();
    this.waterColor = color(0, 0, 255, 50);
    this.waterStroke = color(0, 0, 255);
  }

  //Accessors
  //public void born() { 
  //  box(20);
  //  //addFish(new Goldfish(theTank, random(20, 30)));
  //}
  public float getWidth() { 
    return myWidth;
  }
  public float getHeight() { 
    return myLength;
  }
  public float getDepth() { 
    return myHeight;
  }
  public void addFish(Tankable f) {
    objects.add(f);
    //objects.add(f);
  }
  public void clearObjects() {
    for (Tankable f : objects) {
      if (f.getCroaked()) {
        f.isDead();
      }
    }
    //objects=new ArrayList<Tankable>();
    //objects.add(f);
    ammonia = 0;
    hit = false;
  }

  public void addPellet(Pellet p) {
    objects.add(p);
    //myPellet.add(p);
  }

  public void velocityChange() {
    for (Tankable f : objects) {
      f.setVX(f.getVX()*-1);
      f.setVY(f.getVY()*-1);
      f.setVZ(f.getVZ()*-1);
    }
    //myPellet.add(p);
  }

  public void addBubble(Bubble b) {
    myBubble.add(b);

    //myBubble.add(b);
  }

  //void removePellet(Tankable p) {
  //  myPellet.remove(p);
  //}
  //public void removeIt(Tankable t) {
  //  if (t instanceof Goldfish) {
  //    objects.remove(t);
  //  }
  //  if (t instanceof Food) {
  //    myPellet.remove(t);
  //  }
  //}

  //void Collision(Pellet p, Fish f) {
  //  myPellet.add(p);
  //}
  //public boolean hasCollision(Tankable f){
  //  f.tryToEat(f);
  //  return false;
  //}
  public boolean hasCollision(Tankable f, Tankable p) {
    f.tryToEat(p);
    return false;//f.isDead();
  }

  /*
   age min to spawn after collision
   cooling period
   sex the fish
   */

  public void update() {
    ammonia++;
    waterColor= color(0, ammonia/10, 255, 50);
    //outerloop:
    for (Tankable f : objects) {
      f.update();
      if ((frameCount-f.getAge()>500 && f instanceof Pellet)||((frameCount-f.getAge()>20000 || f.getWeight()>30 || f.getWeight()<9)&& f instanceof Goldfish)||((frameCount-f.getAge()>10000 || f.getWeight()>50 || f.getWeight()<25)&& f instanceof Piranha) || ((frameCount-f.getAge()>30000 || f.getWeight()>100 || f.getWeight()<40)&& f instanceof Whale)) {
        f.isCroaked();
      }
      for (Tankable r : objects) {

        if ((f!=r&&dist(f.getX(), f.getY(), f.getZ(), r.getX(), r.getY(), r.getZ())<=(r.getWeight()+f.getWeight()))&&!f.getDead()&&!r.getDead()) {
          //r.setWeight(r.getWeight()*.99);
          hasCollision(f, r);
          //break outerloop;
        }
        //for(int i=0;i<=20;i++){
        //  if ((f!=r&&dist(f.getX(), f.getY(), f.getZ(), r.getX(), r.getY(), r.getZ())<=(r.getWeight()+f.getWeight()))&&!f.getDead()&&!r.getDead()) {
        //    r.setWeight(r.getWeight()*.5);
        //  }
        //}
      }
    }    
    //for (Tankable p : myPellet) {
    //  p.update();
    //  if (frameCount-p.getAge()>1000) {
    //    //removeIt(p);
    //    break;
    //  }
    //}
    for (Tankable b : myBubble) {
      b.update();
    }
    show();
  }
  public void show() {
    pushMatrix();//draw the Water
    translate(width/2, height/2, 0);
    stroke(waterStroke);
    fill(waterColor);
    box(myWidth, myLength, myHeight);
    popMatrix();
    pushMatrix();  //draw the Tank
    translate(width/2, height/2-25, 0);
    stroke(0);
    strokeWeight(2);
    noFill();
    box(myWidth, myLength+50, myHeight);
    popMatrix();
  }
}//FishTank class
class Food extends Pellet {

  Food(FishTank p, float w, int a) {
    super(p, w, a);
    this.location = new PVector(random((width - myFishTank.getWidth())/2+weight, (width-(width - myFishTank.getWidth())/2)-weight), (height - myFishTank.getHeight())/2+weight, random(-myFishTank.getDepth()/2+weight, myFishTank.getDepth()/2-weight));
    this.velocity = new PVector(0, 1, 0);
    this.skin = color(0, 0, 0);
  }

  public void move() {

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
  public boolean tryToEat(Tankable r) {return false;}
}
//f
public PVector lok;
class Goldfish extends Fish {
  Goldfish(FishTank f, float w, int a) {
    super(f, w, a);
    this.location = new PVector(random(((width - myFishTank.getWidth())/2)+weight, (width - ((width - myFishTank.getWidth())/2))-weight), random((height - myFishTank.getHeight())/2+weight, (height - (height - myFishTank.getHeight())/2)-weight), random(-myFishTank.getDepth()/2+weight, myFishTank.getDepth()/2-weight));
    this.velocity = new PVector(randomGaussian()*0.75f+0, randomGaussian()*0.75f+0, randomGaussian()*0.75f+0);
    this.skin = color(random(150, 250), 102, 5);
    this.age = a;
    this.croaked = false;
    //this.weight = random(5, maxWeight);
  }

  public void move() {
    float x_buffer = (width - myFishTank.getWidth())/2;
    float y_buffer = (height - myFishTank.getHeight())/2;
    if (!getCroaked()) {
      if (location.x <x_buffer + weight || location.x > (width - x_buffer)-weight) {
        // x_buffer: (width - 500)/2 = 100 on each side
        velocity.x *= -1;
      }
      if (location.y <= y_buffer+weight || location.y >= (height - y_buffer)-weight) {
        // y_buffer: (height - 400)/2   = 150 on each side
        velocity.y *= -1;
      }
      if (location.z < -myFishTank.getDepth()/2+weight || location.z > myFishTank.getDepth()/2-weight) {
        velocity.z *= -1;
      }
    } else {
      if (location.y >= (height - myFishTank.getHeight())/2) {
        velocity.y -=.05f;
        velocity.x =0;
        velocity.z =0;
      } else {
        velocity.y = 0;
      }
    }


    location.add(velocity);
  }//move()

  public boolean tryToEat(Tankable r) {
    if (r instanceof Goldfish && !getCroaked()) {
      float speed = velocity.mag();
      (velocity.sub(PVector.sub(location, r.getLocation()).normalize())).mult(2*PVector.sub(location, r.getLocation()).dot(velocity)).setMag(speed);
      speed = r.getVelocity().mag();
      (r.getVelocity().sub(PVector.sub(r.getLocation(), location).normalize())).mult(2*PVector.sub(r.getLocation(), location).dot(r.getVelocity())).setMag(speed);
      //velocity = new PVector(velocity.x-2*(velocity.x*r.getVX())*r.getVX(), velocity.y-2*(velocity.y*r.getVY())*r.getVY(), velocity.z-2*(velocity.z*r.getVZ())*r.getVZ());
      //location = new PVector(location.x+.1*(abs(velocity.x)/velocity.x), location.y+.1*(abs(velocity.y)/velocity.y), velocity.z +.1*(abs(velocity.z)/velocity.z));
      theTank.addBubble(new Bubble(theTank, random(5, 10), new PVector(((r.getWeight()*r.getX()+weight*location.x)/(r.getWeight()+weight)), ((r.getWeight()*r.getY()+weight*location.y)/(r.getWeight()+weight)), ((r.getWeight()*r.getZ()+weight*location.z)/(r.getWeight()+weight)))));

      location.add((PVector.sub(location, r.getLocation())).setMag(((r.getWeight()+weight)-dist(r.getX(), r.getY(), r.getZ(), location.x, location.y, location.z))/2.0f));
      r.getLocation().add((PVector.sub(r.getLocation(), location)).setMag(((r.getWeight()+weight)-dist(r.getX(), r.getY(), r.getZ(), location.x, location.y, location.z))/2.0f));
      if (PApplet.parseInt(random(10))==4.0f) {
        theTank.addBubble(new Bubble(theTank, 100, new PVector(((r.getWeight()*r.getX()+weight*location.x)/(r.getWeight()+weight)), ((r.getWeight()*r.getY()+weight*location.y)/(r.getWeight()+weight)), ((r.getWeight()*r.getZ()+weight*location.z)/(r.getWeight()+weight)))));
      }
      return false;
    } else if (r instanceof Food) {
      weight+=2;
      return r.isDead();
    } else {
      return false;
    }
  }
}
//g
public abstract class Pellet implements Tankable { //<>//
  protected PVector location, velocity;
  protected int skin;
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

  public void update() {
    move();
    if (!dead) {
      show();
    }
  }

  public void show() {
    pushMatrix();
    translate(location.x, location.y, location.z);
    fill(skin);
    noStroke();
    sphere(weight);
    popMatrix();
  }//show()

  public void move() {

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
  public void setWeight(float w) {
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
  public abstract boolean tryToEat(Tankable r);
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
class Piranha extends Fish {
  Piranha(FishTank f, float w, int a) {
    super(f, w, a);
    this.location = new PVector(random(((width - myFishTank.getWidth())/2)+weight, (width - ((width - myFishTank.getWidth())/2))-weight), height/2, 0);
    this.velocity = new PVector(randomGaussian()*0.9f+0, randomGaussian()*0.9f+0, randomGaussian()*0.9f+0);
    this.skin = color(100, random(200, 250), random(150, 250));
    // this.weight = random(5, maxWeight);
    //m t his.maxWeight = 50;d
  }

  public void move() {
    float x_buffer = (width - myFishTank.getWidth())/2;
    float y_buffer = (height - myFishTank.getHeight())/2;
    if (!getCroaked()) {
      if (location.x <x_buffer + weight || location.x > (width - x_buffer)-weight) {
        // x_buffer: (width - 500)/2 = 100 on each side   
        velocity.x *= -1;
      }
      if (location.y <= y_buffer+weight || location.y >= (height - y_buffer)-weight) {
        // y_buffer: (height - 400)/2   = 150 on each side
        velocity.y *= -1;
      }
      if (location.z < -myFishTank.getDepth()/2+weight || location.z > myFishTank.getDepth()/2-weight) {
        velocity.z *= -1;
      }
    } else {
      if (location.y >= (height - myFishTank.getHeight())/2) {
        velocity.y -=.05f;
        velocity.x =0;
        velocity.z =0;
      } else {
        velocity.y = 0;
      }
    }
    location.add(velocity);
  }//move()
  public boolean tryToEat(Tankable r) {
    if (r instanceof Goldfish || (r instanceof Whale && r.getWeight()<weight )||  (r instanceof Piranha && r.getWeight()<weight) && !getCroaked()) {
      weight+=r.getWeight()*.1f;
      return r.isDead();
    } else if (r instanceof Food) {
      weight+=2;
      return r.isDead();
    } else {
      return false;
    }
  }
}
//p
class Poison extends Pellet {

  Poison(FishTank p, float w, int a) {
    super(p, w, a);
    this.location = new PVector(random((width - myFishTank.getWidth())/2+weight, (width-(width - myFishTank.getWidth())/2)-weight), (height - myFishTank.getHeight())/2+weight, random(-myFishTank.getDepth()/2+weight, myFishTank.getDepth()/2-weight));
    this.velocity = new PVector(0, 1, 0);
    this.skin = color(150, 0, 0);
  }

  public void move() {

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
  public boolean tryToEat(Tankable r) {
    if (!getDead()) {
      r.setWeight(r.getWeight()*.95f);
      return isDead();
    }
    else{
      return false;
    }
  }
}
//f
public boolean hit = false;
class Special extends Pellet {

  Special(FishTank p, float w, int a) {
    super(p, w, a);
    this.location = new PVector(random((width - myFishTank.getWidth())/2+weight, (width-(width - myFishTank.getWidth())/2)-weight), (height - myFishTank.getHeight())/2+weight, random(-myFishTank.getDepth()/2+weight, myFishTank.getDepth()/2-weight));
    this.velocity = new PVector(0, 1, 0);
    this.skin = color(random(150, 255), random(150, 255), random(150, 255));
  }

  public void move() {
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
  public boolean tryToEat(Tankable r) {
    if (!hit) {
      link("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
      hit = true;
    }
    return hit;
  }
}
//f
public interface Tankable {
  public void update();
  //public boolean hasCollision(Tankable t); //avoid writing hasCollision for every type of object
  public float getX();
  public PVector getLocation();
  public PVector getVelocity();
  public void setVX(float v);
  public void setVY(float v);
  public void setVZ(float v);
  public float getY();
  public float getZ();
  public float getVX();
  public float getVY();
  public float getVZ();
  public int getAge();
  public float getWeight();
  public void setWeight(float w);
  public boolean isDead();
  public boolean isCroaked();
  public boolean getCroaked();
  public boolean getDead();
  public boolean tryToEat(Tankable r);
}
class ToroidalFin extends Goldfish {
  ToroidalFin(FishTank f, float w, int a) {
    super(f, w, a);
    // private ArrayList<Fish> myFish;
    // this.myFishTank = f;
    this.location = new PVector(random(((width - myFishTank.getWidth())/2)+weight, (width - ((width - myFishTank.getWidth())/2))-weight), height/2, 0);
    this.velocity = new PVector(random(-4, 4), random(-4, 4), random(-4, 4));
    this.skin = color(200, 200, 200, 0);
    this.scales = color(0, 0, 200, 20);
    // this.weight = random(5, maxWeight);
    //m t his.maxWeight = 50;
  }

  public void move() {
    float x_buffer = (width - myFishTank.getWidth())/2;
    float y_buffer = (height - myFishTank.getHeight())/2;
    if (!getCroaked()) {
      if (location.x <x_buffer + weight) {
        // x_buffer: (width - 500)/2 = 100 on each side   
        location.x = (width - x_buffer)-weight;
      }
      if (location.x > (width - x_buffer)-weight) {
        // x_buffer: (width - 500)/2 = 100 on each side   
        location.x = x_buffer + weight;
      }
      if (location.y < y_buffer+weight) {
        // y_buffer: (height - 400)/2   = 150 on each side
        location.y = (height - y_buffer)-weight;
      }
      if (location.y > (height - y_buffer)-weight) {
        // y_buffer: (height - 400)/2   = 150 on each side
        location.y = y_buffer+weight;
      }
      if (location.z < -myFishTank.getDepth()/2+weight) {
        location.z = myFishTank.getDepth()/2-weight;
      }
      if (location.z > myFishTank.getDepth()/2-weight) {
        location.z = -myFishTank.getDepth()/2+weight;
      }
    } else {
      if (location.y >= (height - myFishTank.getHeight())/2) {
        velocity.y -=.05f;
        velocity.x =0;
        velocity.z =0;
      } else {
        velocity.y = 0;
      }
    }

    location.add(velocity);
  }//move()
  public boolean tryToEat(Tankable r) {
    if (r instanceof ToroidalFin) {
      float speed = velocity.mag();
      (velocity.sub(PVector.sub(location, r.getLocation()).normalize())).mult(2*PVector.sub(location, r.getLocation()).dot(velocity)).setMag(speed);
      speed = r.getVelocity().mag();
      (r.getVelocity().sub(PVector.sub(r.getLocation(), location).normalize())).mult(2*PVector.sub(r.getLocation(), location).dot(r.getVelocity())).setMag(speed);
      //velocity = new PVector(velocity.x-2*(velocity.x*r.getVX())*r.getVX(), velocity.y-2*(velocity.y*r.getVY())*r.getVY(), velocity.z-2*(velocity.z*r.getVZ())*r.getVZ());
      //location = new PVector(location.x+.1*(abs(velocity.x)/velocity.x), location.y+.1*(abs(velocity.y)/velocity.y), velocity.z +.1*(abs(velocity.z)/velocity.z));
      theTank.addBubble(new Bubble(theTank, random(5, 10), new PVector(((r.getWeight()*r.getX()+weight*location.x)/(r.getWeight()+weight)), ((r.getWeight()*r.getY()+weight*location.y)/(r.getWeight()+weight)), ((r.getWeight()*r.getZ()+weight*location.z)/(r.getWeight()+weight)))));
      location.add((PVector.sub(location, r.getLocation())).setMag(((r.getWeight()+weight)-dist(r.getX(), r.getY(), r.getZ(), location.x, location.y, location.z))/2.0f));
      r.getLocation().add((PVector.sub(r.getLocation(), location)).setMag(((r.getWeight()+weight)-dist(r.getX(), r.getY(), r.getZ(), location.x, location.y, location.z))/2.0f));
      if (PApplet.parseInt(random(10))==4.0f) {
        theTank.addBubble(new Bubble(theTank, 150, new PVector(((r.getWeight()*r.getX()+weight*location.x)/(r.getWeight()+weight)), ((r.getWeight()*r.getY()+weight*location.y)/(r.getWeight()+weight)), ((r.getWeight()*r.getZ()+weight*location.z)/(r.getWeight()+weight)))));
      }
      return false;
    } else if (r instanceof Food) {
      weight+=1;
      return r.isDead();
    } else {
      return false;
    }
  }
}
//g
int minWeight = 30;
public int maxWeight = 100;
class Whale extends Fish {
  Whale(FishTank f, float w, int a) {
    super(f, w, a);
    // private ArrayList<Fish> myFish;
    // this.myFishTank = f;
    this.location = new PVector(random(((width - myFishTank.getWidth())/2)+weight*4, (width - ((width - myFishTank.getWidth())/2))-weight), random((height - myFishTank.getHeight())/2+weight, (height - (height - myFishTank.getHeight())/2)-weight*4), random(-myFishTank.getDepth()/2+weight/2, myFishTank.getDepth()/2-weight));
    this.velocity = new PVector(random(-2, 2), 0, random(-2, 2));
    this.skin = color(random(50, 100), random(70, 120), random(100, 150));
    this.weight = randomGaussian()*0.25f+40;
    //m t his.maxWeight = 50;
  }

  public void move() {

    float x_buffer = (width - myFishTank.getWidth())/2;
    float y_buffer = (height - myFishTank.getHeight())/2;
    if (!getCroaked()) {
      if (velocity.x <= abs(.009f) && velocity.y <= abs(.009f) && velocity.z <= abs(.009f)) {
        velocity.x = random(-1, 1);
      }
      if ((location.x <x_buffer + weight || location.x > (width - x_buffer)-weight)) {
        // x_buffer: (width - 500)/2 = 100 on each side   
        velocity.x *= -1;
      }
      if ((location.y <= y_buffer+weight || location.y >= (height - y_buffer)-weight) ) {
        // y_buffer: (height - 400)/2   = 150 on each side
        velocity.y *= -1;
      }
      if ((location.z < -myFishTank.getDepth()/2+weight || location.z > myFishTank.getDepth()/2-weight)) {
        velocity.z *= -1;
      }
      velocity.y =0;
    } else {
      if (location.y >= (height - myFishTank.getHeight())/2) {
        velocity.y -=.01f;
        velocity.x =0;
        velocity.z =0;
      } else {
        velocity.y = 0;
      }
    }
    //velocity.y =0;
    location.add(velocity);
  }//move()
  public boolean tryToEat(Tankable r) {
    if (r instanceof Whale && !getCroaked()) {
      float speed = velocity.mag();
      (velocity.sub(PVector.sub(location, r.getLocation()).normalize())).mult(2*PVector.sub(location, r.getLocation()).dot(velocity)).setMag(speed);
      speed = r.getVelocity().mag();
      (r.getVelocity().sub(PVector.sub(r.getLocation(), location).normalize())).mult(2*PVector.sub(r.getLocation(), location).dot(r.getVelocity())).setMag(speed);
      //velocity.x *= random(-1.5,1.5);
      //velocity.z*= random(-1.5,1.5);
      //r.setVX(r.getVX()*random(-1.5,1.5));
      //r.setVZ(r.getVZ()*random(-1.5,1.5));
      theTank.addBubble(new Bubble(theTank, random(5, 10), new PVector(((r.getWeight()*r.getX()+weight*location.x)/(r.getWeight()+weight)), ((r.getWeight()*r.getY()+weight*location.y)/(r.getWeight()+weight)), ((r.getWeight()*r.getZ()+weight*location.z)/(r.getWeight()+weight)))));

      location.add((PVector.sub(location, r.getLocation())).setMag(((r.getWeight()+weight)-dist(r.getX(), r.getY(), r.getZ(), location.x, location.y, location.z))/2.0f));
      r.getLocation().add((PVector.sub(r.getLocation(), location)).setMag(((r.getWeight()+weight)-dist(r.getX(), r.getY(), r.getZ(), location.x, location.y, location.z))/2.0f));
      if (random(10)>9.9f) {
        theTank.addBubble(new Bubble(theTank, 200, new PVector(((r.getWeight()*r.getX()+weight*location.x)/(r.getWeight()+weight)), ((r.getWeight()*r.getY()+weight*location.y)/(r.getWeight()+weight)), ((r.getWeight()*r.getZ()+weight*location.z)/(r.getWeight()+weight)))));
      }
      return false;
    } else if ((r instanceof Goldfish && !r.getCroaked())|| (r instanceof Piranha && r.getWeight() < weight && !r.getCroaked()) && !getCroaked()) {
      weight+=r.getWeight()*.1f;
      return r.isDead();
    } else if (r instanceof Food && !getCroaked()) {
      weight+=2;
      return r.isDead();
    } else {
      return false;
    }
  }
}
//g
//velocity.x = ((weight-r.getWeight())/(weight+r.getWeight())*velocity.x) + ((2*r.getWeight())/(weight+r.getWeight())*r.getVX()) ;
//velocity.z = ((weight-r.getWeight())/(weight+r.getWeight())*velocity.z) + ((2*r.getWeight())/(weight+r.getWeight())*r.getVZ()) ;
//r.setVX(((weight*2)/(weight+r.getWeight())*velocity.x) + ((weight-r.getWeight())/(weight+r.getWeight())*r.getVX())) ;
//r.setVZ(((weight*2)/(weight+r.getWeight())*velocity.z) + ((weight-r.getWeight())/(weight+r.getWeight())*r.getVZ())) ;
  public void settings() {  size(700, 700, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "FishTales3D" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
