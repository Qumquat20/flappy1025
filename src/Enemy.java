public class Enemy extends Entity{
    private int gravity;
    public double dy;

    public Enemy(double[] initialCoords) {
        super(initialCoords);
        dy = -300;
        radius = 30;
        gravity = 500;
    }

    public void increaseGravity(int g) {
        gravity += g;
    }

    public void updatePosition(double deltaTime) {
        dy += gravity * deltaTime;

        // Make sure enemy never accelerates downwards at more than 300px/s
        if (dy > 300) { dy = 300; };

        if (coordinates[1] >= 375 || coordinates[1] <= 0) {
            dy *= -1;
        }

        //coordinates[1] += dy * deltaTime;
    }

    public void jump() {
        dy = -300;
    }
}
