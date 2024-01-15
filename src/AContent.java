import javalib.worldimages.FromFileImage;
import javalib.worldimages.WorldImage;

import java.util.function.Function;

abstract class AContent {

    abstract WorldImage draw();
    abstract <R> R accept(SokobanVisitor<R> visitor);
    // this is ground, visitor has the content

    boolean isPlayer() {
        return false;
    }

}

class Ground extends AContent {

    WorldImage draw() {
        return new FromFileImage("src/images/empty_image.png");
    }

    public <R> R accept(SokobanVisitor<R> visitor) {
        // visitor has the content, and this is ground
        return visitor.visitGround(this);
    }
}

class Wall extends AContent {

    WorldImage draw() {
        return new FromFileImage("src/images/wall.png");
    }

    public <R> R accept(SokobanVisitor<R> visitor) {
        return visitor.visitWall(this);
    }
}

class Target extends AContent {
    String color;

    Target(String color) {
        this.color = color;
    }

    WorldImage draw() {
        if (this.color.equals("B")) {
            return new FromFileImage("src/images/blueTarget.png");
        } else if (this.color.equals("Y")) {
            return new FromFileImage("src/images/yellowTarget.png");
        } else if (this.color.equals("G")) {
            return new FromFileImage("src/images/greenTarget.png");
        } else {
            return new FromFileImage("src/images/redTarget.png");
        }
    }

    public <R> R accept(SokobanVisitor<R> visitor) {
        return visitor.visitTarget(this);
    }
}

class Player extends AContent {

    WorldImage draw() {
        return new FromFileImage("src/images/player.png");
    }

    public <R> R accept(SokobanVisitor<R> visitor) {
        return visitor.visitPlayer(this);
    }

    @Override
    public boolean isPlayer() {
        return true;
    }
}

class Trophy extends AContent {
    String color;

    Trophy(String color) {
        this.color = color;
    }

    WorldImage draw() {

        if (this.color.equals("b")) {
            return new FromFileImage("src/images/blue_trophy.png");
        } else if (this.color.equals("y")) {
            return new FromFileImage("src/images/yellow_trophy.png");
        } else if (this.color.equals("g")) {
            return new FromFileImage("src/images/green_trophy.png");
        } else {
            return new FromFileImage("src/images/red_trophy.png");
        }
    }

    public <R> R accept(SokobanVisitor<R> visitor) {
        return visitor.visitTrophy(this);
    }
}

class Box extends AContent {

    WorldImage draw() {
        return new FromFileImage("src/images/box.png");
    }

    public <R> R accept(SokobanVisitor<R> visitor) {
        return visitor.visitBox(this);
    }

}

class Hole extends AContent {

    WorldImage draw() {
        return new FromFileImage("src/images/hole.png");
    }

    public <R> R accept(SokobanVisitor<R> visitor) {
        return visitor.visitHole(this);
    }

}

class Ice extends AContent{
    WorldImage draw() {
        return new FromFileImage("src/images/ice.png");
    }

    public <R> R accept(SokobanVisitor<R> visitor) {
        return visitor.visitIce(this);
    }
}

interface SokobanVisitor<R> extends Function<AContent, R> {

    R visitGround(Ground g);

    R visitWall(Wall w);

    R visitTarget(Target t);

    R visitPlayer(Player p);

    R visitTrophy(Trophy t);

    R visitBox(Box b);

    R visitHole(Hole c);

    R visitIce(Ice i);
}

class IsMatched implements SokobanVisitor<Integer>{
    AContent level;
    IsMatched(AContent level){
        this.level = level;
    }
    public Integer apply(AContent input){
        return input.accept(this);
    }
    public Integer visitGround(Ground g) {
        return 0;
    }

    public Integer visitWall(Wall w) {
        return 0;
    }

    public Integer visitTarget(Target t) {
        return new SameColor(t.color).apply(this.level);
    }

    public Integer visitPlayer(Player p) {
        return 0;
    }

    public Integer visitTrophy(Trophy t) {
        return 0;
    }

    public Integer visitBox(Box b) {
        return 0;
    }

    public Integer visitHole(Hole h) {
        return 0;
    }

    public Integer visitIce(Ice i){
        return 0;
    }

}
class SameColor implements SokobanVisitor<Integer>{
    String color;
    SameColor(String color){
        this.color = color;
    }
    public Integer apply(AContent input){
        return input.accept(this);
    }
    public Integer visitGround(Ground g) {
        return 0;
    }

    public Integer visitWall(Wall w) {
        return 0;
    }

    public Integer visitTarget(Target t) {
        return 0;
    }

    public Integer visitPlayer(Player p) {
        return 0;
    }

    public Integer visitTrophy(Trophy t) {
        if (t.color.equalsIgnoreCase(this.color)){
            return 1;
        } else{
            return 0;
        }
    }

    public Integer visitBox(Box b) {
        return 0;
    }

    public Integer visitHole(Hole h) {
        return 0;
    }

    public Integer visitIce(Ice i){
        return 0;
    }

}

class IsHole implements SokobanVisitor<Boolean> {

    public Boolean apply(AContent input) {
        return input.accept(this);
    }

    public Boolean visitGround(Ground g) {
        return false;
    }

    public Boolean visitWall(Wall w) {
        return false;
    }

    public Boolean visitTarget(Target t) {
        return false;
    }

    public Boolean visitPlayer(Player p) {
        return false;
    }

    public Boolean visitTrophy(Trophy t) {
        return false;
    }

    public Boolean visitBox(Box b) {
        return false;
    }

    public Boolean visitHole(Hole h) {
        return true;
    }

    public Boolean visitIce(Ice i){
        return false;
    }

}

class IsPushable implements SokobanVisitor<Boolean> {

    public Boolean apply(AContent input) {
        return input.accept(this);
    }

    public Boolean visitGround(Ground g) {
        return false;
    }

    public Boolean visitWall(Wall w) {
        return false;
    }

    public Boolean visitTarget(Target t) {
        return false;
    }

    public Boolean visitPlayer(Player p) {
        return false;
    }

    public Boolean visitTrophy(Trophy t) {
        return true;
    }

    public Boolean visitBox(Box b) {
        return true;
    }

    public Boolean visitHole(Hole h) {
        return false;
    }

    public Boolean visitIce(Ice i){
        return false;
    }
}

class IsMovable implements SokobanVisitor<Boolean> {

    public Boolean apply(AContent input) {
        return input.accept(this);
    }

    public Boolean visitGround(Ground g) {
        return true;
    }

    public Boolean visitWall(Wall w) {
        return false;
    }

    public Boolean visitTarget(Target t) {
        return true;
    }

    public Boolean visitPlayer(Player p) {
        return false;
    }

    public Boolean visitTrophy(Trophy t) {
        return false;
    }

    public Boolean visitBox(Box b) {
        return false;
    }

    public Boolean visitHole(Hole h) {
        return true;
    }

    public Boolean visitIce(Ice i){
        return true;
    }
}

class IsIce implements SokobanVisitor<Boolean> {

    public Boolean apply(AContent input) {
        return input.accept(this);
    }

    public Boolean visitGround(Ground g) {
        return false;
    }

    public Boolean visitWall(Wall w) {
        return false;
    }

    public Boolean visitTarget(Target t) {
        return false;
    }

    public Boolean visitPlayer(Player p) {
        return false;
    }

    public Boolean visitTrophy(Trophy t) {
        return false;
    }

    public Boolean visitBox(Box b) {
        return false;
    }

    public Boolean visitHole(Hole h) {
        return false;
    }

    public Boolean visitIce(Ice i){
        return true;
    }
}

