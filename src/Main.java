import tester.Tester;

public class Main {

  public static void main(String[] args) {
    MyWorld w = new MyWorld();
    int worldWidth = 1200;
    int worldHeight = 900;
    double tickRate = 5;
    w.bigBang(worldWidth, worldHeight, tickRate);
  }

}
