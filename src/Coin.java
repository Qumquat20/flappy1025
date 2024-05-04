public class Coin extends Entity{
    public Coin(double[] initalCoords, int radius, int dx) {
        super(initalCoords, radius, dx);
    }

    public void updatePosition(double deltaTime) {
        coordinates[0] -= dx * deltaTime;
    }
}
