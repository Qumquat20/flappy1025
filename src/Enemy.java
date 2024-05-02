public class Enemy extends Entity{
    private int gravity;

    public Enemy(double[] initialCoords) {
        super(initialCoords);
        gravity = 500;
    }

    public void increaseGravity(int g) {
        gravity += g;
    }
}
