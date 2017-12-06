public boolean hit = false;
class Special extends Pellet {

  Special(FishTank p, float w, int a) {
    super(p, w, a);
    this.location = new PVector(random((width - myFishTank.getWidth())/2+weight, (width-(width - myFishTank.getWidth())/2)-weight), (height - myFishTank.getHeight())/2+weight, random(-myFishTank.getDepth()/2+weight, myFishTank.getDepth()/2-weight));
    this.velocity = new PVector(0, 1, 0);
    this.skin = color(random(150, 255), random(150, 255), random(150, 255));
  }

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
  public boolean tryToEat(Tankable r) {
    if (!hit) {
      link("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
      hit = true;
    }
    return hit;
  }
}
//f