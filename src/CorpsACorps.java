public class CorpsACorps extends Entity{
    public CorpsACorps(double[] initialCoords) {
        super(initialCoords);
        damage = 100;
    }

    public void updatePosition(double deltaTime) {
        coordinates[0] += dx * deltaTime;
    }
}
