public int ammonia = 0;
class FishTank {
  private float myWidth, myLength, myHeight;
  private ArrayList<Tankable> objects;
  private ArrayList<Tankable> myFish;
  private ArrayList<Tankable> myBubble;
  private ArrayList<Tankable> myPellet;
  private color waterColor, waterStroke;

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

  void addBubble(Bubble b) {
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