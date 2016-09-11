package engine;

import java.util.Random;

public enum Direction {
    NORTH, SOUTH, EAST, WEST, NONE; //TODO replace NONE with null?

//    private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
//    private static final int SIZE = VALUES.size();
    public static Direction[] getDirections() {
        return new Direction[] {NORTH, SOUTH, EAST, WEST};
    }
    public static Direction getRandom(Random rng) {
        return getDirections()[rng.nextInt(4)];
    }
}
