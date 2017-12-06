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

  void move() {
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
    if (r instanceof ToroidalFin) {
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