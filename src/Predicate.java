// represents a generic predicate

import java.util.function.Predicate;

// represents a predicate which applies to a String
// returns true if the given String is \n
class IsStopPredicate implements Predicate<String> {
    // applies this predicate to the given String
    public boolean test(String s) {
        return s.equals("\n");
    }
}