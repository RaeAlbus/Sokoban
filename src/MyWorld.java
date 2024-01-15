import javalib.impworld.WorldScene;
import javalib.worldimages.TextImage;

import java.awt.*;

// represents a game state of Sokoban

/**
 * filler documentation
 */
public class MyWorld extends javalib.impworld.World {
    Sokoban sokoban;

    // default constructor
    public MyWorld(Sokoban sokoban) {
        this.sokoban = sokoban;
    }

    // convenience constructor used to set up a starting level
    public MyWorld() {
        this(new Sokoban(8, 7, 4, 4,
                "________\n" + "___RY___\n" + "__IIII__\n" + "________\n" + "________\n" + "___BG___\n"
                        + "________",
                "WWWWWWWW\n" + "W_r>___W\n" + "W______W\n" + "W_b_yB_W\n" + "W__g___W\n" + "W___H__W\n"
                        + "WWWWWWWW"));
    }

    // returns a WorldScene with the Sokoban drawn on it

    /**
     * filler documentation
     */
    public WorldScene makeScene() {
        WorldScene scene = new WorldScene(2560, 1664);
        scene.placeImageXY(this.sokoban.draw(), 600, 450);
        return scene;
    }

    // handles key events by calling this.Sokoban.move and passing in the key
    // pressed

    /**
     * filler documentation
     */
    public void onKeyEvent(String s) {

        if (s.equals("u")) {
            this.sokoban.goBack();
        }

        boolean lost = false;
        this.sokoban.storeCurrentGameState();

        if ((s.equals("a") || s.equals("w") || s.equals("d") || s.equals("s"))) {
            this.sokoban.increaseSteps();
            lost = !this.sokoban.move(s);
        }

        if (lost) {
            endOfWorld("You lost! >:3");
        } else if (this.sokoban.levelWon()) {
            endOfWorld("You won! :3");
        }
    }

    // ends the world by displaying String s
    public void endOfWorld(String s) {
        super.endOfWorld(s);
    }

    // displays the last scene of the world after endOfWorld was called
    public WorldScene lastScene(String msg) {
        WorldScene scene = new WorldScene(2560, 1664);
        scene.placeImageXY(new TextImage(msg, 50, Color.PINK), 600, 450);
        return scene;
    }

}
