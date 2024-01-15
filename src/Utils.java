import javalib.worldimages.*;

import java.util.ArrayList;
import java.util.function.Predicate;

// represents a utility class that contains functions

/**
 * filler documentation
 */
public class Utils {

    // converts a string into a 2D arraylist, with line separations marked by the
    // characters that satisfy pred
    ArrayList<ArrayList<AContent>> convertTo2DArrayList(String s, Predicate<String> pred, boolean isGround) {

        // accumulates all the items that belong in one row of the game grid
        ArrayList<AContent> currentList = new ArrayList<>();
        // accumulates all the items that belong in the whole game grid, with
        // each inner ArrayList<String> represnting a row
        ArrayList<ArrayList<AContent>> allLists = new ArrayList<>();
        // accumulator that decreases in size by one for every iteration
        // stores the remainder of the string not yet processed.
        String deaccString = s;
        String first;

        // iterates over the whole given string, takes the first letter, and tests
        // if it satisfies pred. if it does, it is a line break: move current
        // horizontal accumulator into allLists and refresh it. otherwise add it
        // to the current horizontal accumulator
        for (int i = 0; i < s.length(); i += 1) {
            first = deaccString.substring(0, 1);
            deaccString = deaccString.substring(1);
            if (pred.test(first)) {
                // add the contents of currentList into allLists, and renew currentList
                allLists.add(currentList);
                currentList = new ArrayList<>();
            } else {
                // add the current letter into currentList
                if (isGround) {
                    currentList.add(new GroundStringToAContent().apply(first));
                } else {
                    currentList.add(new ContentStringToAContent().apply(first));
                }
            }
        }

        allLists.add(currentList);
        return allLists;
    }

    // Returns the object that is in the direction of the given String starting
    // from the current position of the player
    public AContent getObject(ArrayList<ArrayList<AContent>> content, Posn currentPos, String direction, int steps) {

        int tempX = currentPos.x;
        int tempY = currentPos.y;

        while (steps > 0) {
            if (direction.equals("a")) {
                tempX = tempX - 1;
            } else if (direction.equals("d")) {
                tempX = tempX + 1;
            } else if (direction.equals("w")) {
                tempY = tempY - 1;
            } else if (direction.equals("s")) {
                tempY = tempY + 1;
            }
            steps = steps - 1;
        }

        return content.get(tempY).get(tempX);
    }

    // sets the object at the position described by the string of directions
    void setObject(ArrayList<ArrayList<AContent>> content, Posn currentPos, String direction, AContent newObject, int distance) {

        Posn newPos = this.moveWith(currentPos, direction, distance);
        content.get(newPos.y).set(newPos.x, newObject);

    }

    // draws the given arraylist with the given IDraw function object
    WorldImage draw(ArrayList<ArrayList<AContent>> list) {

        // accumulates the overall image of the game grid
        WorldImage accumulator = new EmptyImage();
        // accumulates the current horizontal row of the game grid
        WorldImage horAccumulator = new EmptyImage();

        // for loops which iterate over all rows of the arrayList, adding each
        // accumulated
        // row to the large accumulator with aboveImage, and then clearing out
        // horAccumulator
        for (int i = 0; i < list.size(); i += 1) {
            // for loop which iterates over all columns of the arrayList and
            // accumulates them together with besideImage onto horAccumulator
            for (int j = 0; j < list.get(i).size(); j += 1) {
                horAccumulator = new BesideImage(horAccumulator, list.get(i).get(j).draw());
            }
            accumulator = new AboveImage(accumulator, horAccumulator);
            horAccumulator = new EmptyImage();
        }
        return accumulator;
    }

    // returns a new Posn, such that it is the old Posn after
    // movement s (one of wasd) has been performed
    Posn moveWith(Posn old, String s, int distance) {

        int tempX = old.x;
        int tempY = old.y;

        if (s.equals("w")) {
            tempY -= distance;
        } else if (s.equals("s")) {
            tempY += distance;
        } else if (s.equals("a")) {
            tempX -= distance;
        } else if (s.equals("d")) {
            tempX += distance;
        }

        return new Posn(tempX, tempY);
    }

    // returns a duplicate of a 2D ArrayList of AContent with the same AContents
    // such that the returned 2D ArrayList is not an alias of the original ArrayList
    ArrayList<ArrayList<AContent>> duplicate(ArrayList<ArrayList<AContent>> original) {
        ArrayList<ArrayList<AContent>> duplicate = new ArrayList<>();
        for (ArrayList<AContent> aContents : original) {
            duplicate.add(new ArrayList<>(aContents));
        }
        return duplicate;
    }

}
