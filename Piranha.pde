class Piranha extends Fish {
  Piranha(FishTank f, float w, int a) {
    super(f, w, a);
    this.location = new PVector(random(((width - myFishTank.getWidth())/2)+weight, (width - ((width - myFishTank.getWidth())/2))-weight), height/2, 0);
    this.velocity = new PVector(randomGaussian()*0.9+0, randomGaussian()*0.9+0, randomGaussian()*0.9+0);
    this.skin = color(100, random(200, 250), random(150, 250));
    // this.weight = random(5, maxWeight);
    //m t his.maxWeight = 50;d
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
    if (r instanceof Goldfish || (r instanceof Whale && r.getWeight()<weight )||  (r instanceof Piranha && r.getWeight()<weight) && !getCroaked()) {
      weight+=r.getWeight()*.1;
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