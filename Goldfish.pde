public PVector lok;
class Goldfish extends Fish {
  Goldfish(FishTank f, float w, int a) {
    super(f, w, a);
    this.location = new PVector(random(((width - myFishTank.getWidth())/2)+weight, (width - ((width - myFishTank.getWidth())/2))-weight), random((height - myFishTank.getHeight())/2+weight, (height - (height - myFishTank.getHeight())/2)-weight), random(-myFishTank.getDepth()/2+weight, myFishTank.getDepth()/2-weight));
    this.velocity = new PVector(randomGaussian()*0.75+0, randomGaussian()*0.75+0, randomGaussian()*0.75+0);
    this.skin = color(random(150, 250), 102, 5);
    this.age = a;
    this.croaked = false;
    //this.weight = random(5, maxWeight);
  }

  void move() {
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
        velocity.y -=.05;
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

      location.add((PVector.sub(location, r.getLocation())).setMag(((r.getWeight()+weight)-dist(r.getX(), r.getY(), r.getZ(), location.x, location.y, location.z))/2.0));
      r.getLocation().add((PVector.sub(r.getLocation(), location)).setMag(((r.getWeight()+weight)-dist(r.getX(), r.getY(), r.getZ(), location.x, location.y, location.z))/2.0));
      if (int(random(10))==4.0) {
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