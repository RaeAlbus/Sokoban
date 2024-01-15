interface IConvert<T, AContent> {

    public AContent apply(String obj1);

}

class GroundStringToAContent implements IConvert<String, AContent> {
    // applies this function object to the String given
    public AContent apply(String obj1) {
        if ((obj1.equals("B")) || (obj1.equals("R")) || (obj1.equals("G")) || (obj1.equals("Y"))) {
            return new Target(obj1);
        } else if(obj1.equals("I")){
            return new Ice();
        }else{
            return new Ground();
        }
    }
}

class ContentStringToAContent implements IConvert<String, AContent> {

    // applies this function object to the String given

    public AContent apply(String obj1) {
        if (obj1.equals("W")) {
            return new Wall();
        } else if (obj1.equals("B")) {
            return new Box();
        } else if ((obj1.equals(">")) || (obj1.equals("<")) || (obj1.equals("v")) || (obj1.equals("^"))) {
            return new Player();
        } else if ((obj1.equals("b")) || (obj1.equals("r")) || (obj1.equals("g")) || (obj1.equals("y"))) {
            return new Trophy(obj1);
        } else if (obj1.equals("H")) {
            return new Hole();
        } else {
            return new Ground();
        }
    }
}