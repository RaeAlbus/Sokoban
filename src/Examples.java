import javalib.worldimages.Posn;
import tester.Tester;

import java.util.ArrayList;

/**
 * filler documentation
 */

public class Examples {

    String threeGround;
    String threeContent;
    Sokoban three;

    String fiveIceGround;
    String blankGround;
    String fiveContent;
    Sokoban fiveIce;
    Sokoban fiveNoIce;

    String oneContent;
    String oneGround;
    Sokoban one;

    String one2Content;
    String one2Ground;
    Sokoban one2;

    String threeItemHoleContent;
    String threeItemHoleGround;
    Sokoban threeItemHole;

    String threeHoleContent;
    String threeHoleGround;
    Sokoban threeHole;

    Sokoban sokHoles;

    Sokoban exSok;
    Sokoban exSokWon;


    void init() {

        // a 3x3 test case to test simple movements of the player
        threeGround = "__G\n_I_\n___";
        threeContent = "_>_\n___\ng__";
        three = new Sokoban(3, 3, 1, 1, threeGround, threeContent);

        // a 5x5 test case to test pushing mechanics on ice and no ice
        fiveContent =
                "WWWWWWW\n" +
                "W>B___W\n" +
                "Wb____W\n" +
                "W_____W\n" +
                "W_____W\n" +
                "W_____W\n" +
                "W_____W\n" +
                "WWWWWWW\n";
        blankGround =
                "_______\n" +
                        "_______\n" +
                        "_______\n" +
                        "_______\n" +
                        "_______\n" +
                        "_______\n" +
                        "_______\n" +
                        "_______\n";
        fiveIceGround =
                "_______\n" +
                        "__II___\n" +
                        "_______\n" +
                        "_I_____\n" +
                        "_I_____\n" +
                        "_______\n" +
                        "_______\n" +
                        "_______\n";
        fiveIce = new Sokoban(7, 8, 1, 1, fiveIceGround, fiveContent);
        fiveNoIce = new Sokoban(7, 8, 1, 1, blankGround, fiveContent);

        // a 1 by 9 test case to test pushing of two items
        oneContent = "W_bb>BB_W";
        oneGround = "__II>____";
        one = new Sokoban(9, 1, 1, 1, oneGround, oneContent);

        // a second 1 by 9 test case to test pushing one item onto ice and colliding with another
        one2Content = "W>B_B__W";
        one2Ground = "___II___";
        one2 = new Sokoban(9, 1, 1, 1, one2Ground, one2Content);

        // a second 3 by 3 case with holes
        threeHoleGround = "___\n_I_\n___";
        threeHoleContent= "_>H\n___\n_H_";
        threeHole = new Sokoban(3, 3, 1, 1, threeHoleGround, threeHoleContent);

        // a third 3 by 3 example with holes and items
        threeItemHoleGround = "___\nI__\n___";
        threeItemHoleContent = ">BH\nB__\nH__";
        threeItemHole = new Sokoban(3, 3, 1, 1, threeItemHoleGround, threeItemHoleContent);

        String exampleLevelGround = "________\n" + "___R____\n" + "________\n" + "_B____Y_\n"
                + "________\n" + "___G____\n" + "________";
        String exampleLevelContents = "__WWW___\n" + "__W_WW__\n" + "WWWr_WWW\n" + "W_b>yB_W\n"
                + "WW_gWWWW\n" + "_WW_W___\n" + "__WWW___";

        String exampleLevelContentsWon = "___>____\n" + "___r____\n" + "___BBBB_\n" + "_b____y_\n"
                + "__WW____\n" + "___g____\n" + "____WWWW";

        // sets up the Sokobans to test on
        exSok = new Sokoban(7, 7, 4, 4, exampleLevelGround, exampleLevelContents);
        exSokWon = new Sokoban(7, 7, 4, 4, exampleLevelGround, exampleLevelContentsWon);

        // sets up the test level with holes
        String contentHoles = "WWWWWWW\nW_>___W\nW_H_r_W\nWH_HB_W\nW_H___W\nW_____W\nWWWWWWW";
        String groundHoles = "_______\n_______\n_______\n__R____\n_______\n_______\n_______";
        sokHoles = new Sokoban(7, 7, 1, 1, groundHoles, contentHoles);

    }

    // SOKOBAN TESTS

    void testSimpleMove(Tester t) {

        init();
        String threeContentA = ">__\n___\ng__";
        three.move("a");
        t.checkExpect(three, new Sokoban(3, 3, 1, 1, threeGround, threeContentA));

        init();
        String threeContentD = "__>\n___\ng__";
        three.move("d");
        t.checkExpect(three, new Sokoban(3, 3, 1, 1, threeGround, threeContentD));

        init();
        String threeContentS = "___\n___\ng>_";
        three.move("s");
        t.checkExpect(three, new Sokoban(3, 3, 1, 1, threeGround, threeContentS));

    }

    void testPushMove(Tester t) {

        // test pushing an item that's already on ice
        init();
        String fiveIceContentD =
                "WWWWWWW\n" +
                "W__>B_W\n" +
                "Wb____W\n" +
                "W_____W\n" +
                "W_____W\n" +
                "W_____W\n" +
                "W_____W\n" +
                "WWWWWWW\n";
        fiveIce.move("d");
        t.checkExpect(fiveIce, new Sokoban(7, 8, 1, 1, fiveIceGround, fiveIceContentD));

        // test pushing an item onto ice
        init();
        String fiveIceContentS =
                "WWWWWWW\n" +
                "W_B___W\n" +
                "W_____W\n" +
                "W_____W\n" +
                "W>____W\n" +
                "Wb____W\n" +
                "W_____W\n" +
                "WWWWWWW\n";
        fiveIce.move("s");
        t.checkExpect(fiveIce, new Sokoban(7, 8, 1, 1, fiveIceGround, fiveIceContentS));

        // test pushing an item
        init();
        String fiveNoIceContentS =
                "WWWWWWW\n" +
                "W_B___W\n" +
                "W>____W\n" +
                "Wb____W\n" +
                "W_____W\n" +
                "W_____W\n" +
                "W_____W\n" +
                "WWWWWWW\n";
        fiveNoIce.move("s");
        t.checkExpect(fiveNoIce, new Sokoban(7, 8, 1, 1, blankGround, fiveNoIceContentS));

        // test pushing a wall
        init();
        fiveNoIce.move("a");
        t.checkExpect(fiveNoIce, new Sokoban(7, 8, 1, 1, blankGround, fiveContent));

    }

    void testPushMoveTwo(Tester t) {

        // tests moving two objects on ground
        init();
        one.move("a");
        t.checkExpect(one, new Sokoban(9, 1, 1, 1, oneGround, oneContent));

        // tests moving two objects on ice
        init();
        one.move("d");
        t.checkExpect(one, new Sokoban(9, 1, 1, 1, oneGround, oneContent));

        init();
        one2.move("d");
        String one2ContentD = "W_>BB__W";
        t.checkExpect(one2, new Sokoban(9, 1, 1, 1, one2Ground, one2ContentD));

    }

    void testHole(Tester t) {

        // test walking into a hole
        init();
        t.checkExpect(threeHole.move("d"), false);

        // test sliding into a hole
        init();
        t.checkExpect(threeHole.move("s"), false);

        // test sliding an item into a hole
        init();
        threeItemHole.move("s");
        String iceHoleAfter = "_BH\n>__\n___";
        t.checkExpect(threeItemHole, new Sokoban(3, 3, 1, 1, threeItemHoleGround, iceHoleAfter));

        // test pushing an item into a hole
        init();
        threeItemHole.move("d");
        String holeAfter = "_>_\nB__\nH__";
        t.checkExpect(threeItemHole, new Sokoban(3, 3, 1, 1, threeItemHoleGround, holeAfter));

    }

    void testUndo(Tester t) {

        // tests the goBack function
        init();
        MyWorld threeWorld = new MyWorld(three);
        threeWorld.onKeyEvent("a");
        threeWorld.sokoban.goBack();
        MyWorld threeAfterWorld = new MyWorld(three);
        threeAfterWorld.sokoban.increaseSteps();
        t.checkExpect(threeWorld.sokoban, threeAfterWorld.sokoban);

    }

    // tests the canPush method
    void testCanPush(Tester t) {
        init();
        // 1st object is trophy, second is empty
        t.checkExpect(sokHoles.canPush(new Posn(4, 2), "s"), true);
        // 1st object is trophy, second is hole
        t.checkExpect(sokHoles.canPush(new Posn(5, 3), "a"), true);
        // 1st object is trophy, second is wall
        t.checkExpect(exSok.canPush(new Posn(4, 2), "a"), false);
        // 1st object is trophy, second is crate
        t.checkExpect(exSok.canPush(new Posn(3, 3), "d"), false);
    }

    // tests the canMoveHere method
    void testCanMoveHere(Tester t) {
        init();
        t.checkExpect(exSokWon.canMoveHere(new Posn(3, 3), "a"), true);
        t.checkExpect(exSokWon.canMoveHere(new Posn(3, 3), "d"), true);
        t.checkExpect(exSokWon.canMoveHere(new Posn(3, 3), "w"), false);
        t.checkExpect(exSokWon.canMoveHere(new Posn(3, 3), "s"), false);
    }

    // tests the withinBounds method
    void testWithinBounds(Tester t) {
        init();
        t.checkExpect(exSok.withinBounds(new Posn(0, 0), "w"), false);
        t.checkExpect(exSok.withinBounds(new Posn(0, 6), "s"), false);
        t.checkExpect(exSok.withinBounds(new Posn(4, 6), "a"), true);
        t.checkExpect(exSok.withinBounds(new Posn(7, 0), "d"), false);
    }

    void testMoveWith(Tester t) {
        init();
        Posn pos1 = new Posn(4, 4);
        t.checkExpect(new Utils().moveWith(pos1, "w", 1), new Posn(4, 3));
        t.checkExpect(new Utils().moveWith(pos1, "s", 1), new Posn(4, 5));
        t.checkExpect(new Utils().moveWith(pos1, "a", 1), new Posn(3, 4));
        t.checkExpect(new Utils().moveWith(pos1, "d", 1), new Posn(5, 4));
        Posn pos2 = new Posn(2, 5);
        t.checkExpect(new Utils().moveWith(pos2, "w", 2), new Posn(2, 3));
        t.checkExpect(new Utils().moveWith(pos2, "s", 2), new Posn(2, 7));
        t.checkExpect(new Utils().moveWith(pos2, "a", 2), new Posn(0, 5));
        t.checkExpect(new Utils().moveWith(pos2, "d", 2), new Posn(4, 5));
    }

    void testDuplicate(Tester t) {
        init();
        t.checkExpect(fiveIce.levelContent, new Utils().duplicate(fiveIce.levelContent));
        t.checkExpect(fiveIce.levelContent == new Utils().duplicate(fiveIce.levelContent), false);
    }

    void testStoreGameState(Tester t) {
        init();
        t.checkExpect(three.previousLevelContent, new ArrayList<>());
        three.move("d");
        String threeContentD = "__>\n___\ng__";
        t.checkExpect(three.levelContent, new Sokoban(3, 3, 1, 1, threeGround, threeContentD).levelContent);
    }

    void testIncreaseSteps(Tester t) {
        init();
        MyWorld world = new MyWorld(three);
        world.onKeyEvent("d");
        t.checkExpect(world.sokoban.steps, 1);
    }

    void testGetObject(Tester t) {
        init();
        t.checkExpect(new Utils().getObject(fiveIce.levelContent, new Posn(1, 1), "d", 1), new Box());
        t.checkExpect(new Utils().getObject(fiveIce.levelContent, new Posn(1, 1), "s", 1), new Trophy("b"));
        t.checkExpect(new Utils().getObject(fiveIce.groundState, new Posn(1, 1), "u", 1), new Ground());
        t.checkExpect(new Utils().getObject(fiveIce.groundState, new Posn(1, 1), "s", 2), new Ice());
    }


}

