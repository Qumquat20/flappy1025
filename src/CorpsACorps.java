import java.util.Random;

public class CorpsACorps extends Entity{
    public CorpsACorps(double[] initialCoords, int radius) {
        super(initialCoords, radius);

        damage = 100;
    }

    @Override
    public void updatePosition(double deltaTime) {
        coordinates[0] -= dx * deltaTime;
    }
}
