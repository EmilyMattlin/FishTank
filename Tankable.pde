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