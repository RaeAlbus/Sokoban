import javalib.worldimages.*;
import java.awt.*;
import java.util.ArrayList;

// represents a game of Sokoban
class Sokoban {

    int steps;

    int width;
    int length;

    int trophies;
    int targets;

    ArrayList<ArrayList<AContent>> groundState;
    ArrayList<ArrayList<AContent>> levelContent;
    ArrayList<ArrayList<AContent>> previousLevelContent;

    // default constructor
    public Sokoban(int width, int length, int trophies, int targets,
                   ArrayList<ArrayList<AContent>> groundState, ArrayList<ArrayList<AContent>> levelContent) {
        this.width = width;
        this.length = length;
        this.trophies = trophies;
        this.targets = targets;
        this.groundState = groundState;
        this.levelContent = levelContent;
        this.steps = 0;
        this.previousLevelContent = new ArrayList<>();
    }

    // convenience constructor that takes two Strings representing the groundState
    // and the
    // levelContents, and builds a Sokoban from it
    public Sokoban(int width, int length, int trophies, int targets, String groundState,
                   String levelContents) {
        this(width, length, trophies, targets,
                new Utils().convertTo2DArrayList(groundState, new IsStopPredicate(), true),
                new Utils().convertTo2DArrayList(levelContents, new IsStopPredicate(), false));
    }

    // returns true if this level has been won
    public boolean levelWon() {
        if (this.targets > this.trophies) {
            return false;
        } else {
            // every target must have a trophy, but not every trophy must be on a target
            // therefore, this loop recurs through both arrays but only check for
            // correspondence
            // between trophy and target if the ground state is a target
            int totalMatched = 0;
            for (int i = 0; i < groundState.size(); i += 1) {
                for (int j = 0; j < groundState.get(i).size(); j += 1) {
                    totalMatched += new IsMatched(levelContent.get(i).get(j)).apply(groundState.get(i).get(j));
                }
            }
            return totalMatched == this.targets;
        }
    }

    // finds the position of the first item in list that satisfies pred
    Posn findPlayer() {
        // nested for loop that iterates over all columns and rows of the list
        // and checks if any satisfy pred
        ArrayList<ArrayList<AContent>> list = this.levelContent;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                if (list.get(i).get(j).isPlayer()) {
                    return new Posn(j, i);
                }
            }
        }
        return new Posn(-1, -1);
    }

    // draws this Sokoban by overlaying the WorldImage of content and ground
    public WorldImage draw() {
        WorldImage score = new TextImage("Score: " + this.steps, 30, Color.BLACK);
        WorldImage content = new OverlayOffsetImage(score, (this.width *60) - 120, (this.length*60) - 60, new Utils().draw(levelContent));
        WorldImage ground = new Utils().draw(groundState);
        return new OverlayImage(content, ground);
    }

    // EFFECT: moves the character by s if the destination square is empty and
    // within bounds,
    // otherwise checks if the object in front of the character is pushable, if so,
    // push it
    // into the next next square
    // returns false if the game has been lost as a result of the move s
    // for example: moving the character onto a hole
    public boolean move(String s) {

        Utils utils = new Utils();

        // get the person position
        Posn pos = this.findPlayer();

        AContent playerObject = this.levelContent.get(pos.y).get(pos.x);

        // check if the square at position [person + s] is empty
        // if so, move the person there, and empty the current position of the person

        if (this.canMoveHere(pos, s) && this.withinBounds(pos, s)) {

            if (new IsHole().apply(utils.getObject(this.levelContent, pos, s, 1))) {
                utils.setObject(this.levelContent, pos, s, playerObject, 1);
                return false;
            } else if (new IsIce().apply(utils.getObject(this.groundState, pos, s, 1))) {
                utils.setObject(this.levelContent, pos, s, playerObject, 1);
                utils.setObject(this.levelContent, pos, s, new Ground(), 0);
                return this.move(s);
            } else {
                utils.setObject(this.levelContent, pos, s, playerObject, 1);
            }
            utils.setObject(this.levelContent, pos, s, new Ground(), 0);

        } else if (this.withinBounds(pos, s) && this.canPush(pos, s)) {

            AContent prevThing;

            if (new IsHole().apply(utils.getObject(levelContent, pos, s, 2))) {
                utils.setObject(this.levelContent, pos, s, new Ground(), 2);
                utils.setObject(this.levelContent, pos, s, playerObject, 1);
            } else if (new IsIce().apply(utils.getObject(groundState, pos, s, 2))) {
                prevThing = utils.getObject(this.levelContent, pos, s, 1);
                utils.setObject(this.levelContent, pos, s, playerObject, 1);
                utils.setObject(this.levelContent, pos, s, prevThing, 2);
                utils.setObject(this.levelContent, pos, s, playerObject, 1);
                utils.setObject(this.levelContent, pos, s, new Ground(), 0);
                this.move(s);
            } else {
                prevThing = utils.getObject(this.levelContent, pos, s, 1);
                utils.setObject(this.levelContent, pos, s, playerObject, 1);
                utils.setObject(this.levelContent, pos, s, prevThing, 2);
            }
            utils.setObject(this.levelContent, pos, s, new Ground(), 0);

        }
        return true;
    }

    void storeCurrentGameState() {
        this.previousLevelContent = new Utils().duplicate(this.levelContent);
    }

    void goBack() {
        this.levelContent = new Utils().duplicate(this.previousLevelContent);
    }

    void increaseSteps() {
        this.steps += 1;
    }

    // returns true if the character can push the block in front of it
    boolean canPush(Posn pos, String s) {

        Utils utils = new Utils();
        if (this.withinBounds(utils.moveWith(pos, s, 1), s)) {
            AContent distOne = new Utils().getObject(this.levelContent, pos, s, 1);
            AContent distTwo = new Utils().getObject(this.levelContent, pos, s, 2);
            return new IsPushable().apply(distOne) && new IsMovable().apply(distTwo);
        }
        return false;

    }

    // returns true if a player at (x, y) can move in direction s
    // onto either an empty square or a hole
    boolean canMoveHere(Posn pos, String s) {
        AContent next = new Utils().getObject(this.levelContent, pos, s, 1);
        return new IsMovable().apply(next);
    }

    // returns true if a player at (x, y) can move in direction s,
    // where s is one of wasd, and still be within bounds
    boolean withinBounds(Posn pos, String s) {
        if (s.equals("w")) {
            return pos.y - 1 >= 0;
        } else if (s.equals("s")) {
            return pos.y + 1 < this.length;
        } else if (s.equals("a")) {
            return pos.x - 1 >= 0;
        } else {
            return pos.x + 1 < this.width;
        }
    }

}


