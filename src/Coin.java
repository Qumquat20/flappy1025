public class Coin extends Entity{
    private int dx = 120;

    public Coin(double[] initalCoords, int radius) {
        super(initalCoords, radius);
    }

    public void updatePosition(double deltaTime) {
        coordinates[0] -= dx * deltaTime;
    }
}
