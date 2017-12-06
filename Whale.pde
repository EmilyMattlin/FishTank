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
    this.weight = randomGaussian()*0.25+40;
    //m t his.maxWeight = 50;
  }

  void move() {

    float x_buffer = (width - myFishTank.getWidth())/2;
    float y_buffer = (height - myFishTank.getHeight())/2;
    if (!getCroaked()) {
      if (velocity.x <= abs(.009) && velocity.y <= abs(.009) && velocity.z <= abs(.009)) {
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
        velocity.y -=.01;
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

      location.add((PVector.sub(location, r.getLocation())).setMag(((r.getWeight()+weight)-dist(r.getX(), r.getY(), r.getZ(), location.x, location.y, location.z))/2.0));
      r.getLocation().add((PVector.sub(r.getLocation(), location)).setMag(((r.getWeight()+weight)-dist(r.getX(), r.getY(), r.getZ(), location.x, location.y, location.z))/2.0));
      if (random(10)>9.9) {
        theTank.addBubble(new Bubble(theTank, 200, new PVector(((r.getWeight()*r.getX()+weight*location.x)/(r.getWeight()+weight)), ((r.getWeight()*r.getY()+weight*location.y)/(r.getWeight()+weight)), ((r.getWeight()*r.getZ()+weight*location.z)/(r.getWeight()+weight)))));
      }
      return false;
    } else if ((r instanceof Goldfish && !r.getCroaked())|| (r instanceof Piranha && r.getWeight() < weight && !r.getCroaked()) && !getCroaked()) {
      weight+=r.getWeight()*.1;
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