public class Furtif extends Entity {
    public Furtif(double[] initialCoords) {
        super(initialCoords);
        damage = 0;
    }

    public void updatePosition(double deltaTime) {
        coordinates[0] += dx * deltaTime;
    }
}
