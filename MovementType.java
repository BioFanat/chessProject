import java.util.stream.Stream;

public enum MovementType {
    HORIZONTAL(1, 1, 0),
    VERTICAL(2, 0, 1),
    DIAG_INCREASING(3, 1, 1),
    DIAG_DECREASING(4, 1, -1);

    private final int value, xShift, yShift;

    private MovementType(int value, int xShift, int yShift) {
        this.value = value;
        this.xShift = xShift;
        this.yShift = yShift;
    }

    public int getValue() {
        return value;
    }

    public int getxShift() {
        return xShift;
    }

    public int getyShift() {
        return yShift;
    }

    public static MovementType fromValue(int value) {
        return Stream.of(MovementType.values()).filter(e -> e.getValue() == value).findFirst().orElse(null);
    }
}
