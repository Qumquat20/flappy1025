import java.util.ArrayList;

public class Enemy extends Entity{
    private int gravity;
    public double dy;

    public Enemy(double[] initialCoords, int radius, int dx) {
        super(initialCoords, radius, dx);
        dy = -300;
        gravity = 500;
    }

    public void increaseGravity(int g) {
        gravity += g;
    }

    public void updatePosition(double deltaTime) {
        dy += gravity * deltaTime;

        // Make sure enemy never accelerates downwards at more than 300px/s
        if (dy > 300) { dy = 300; };

        if (coordinates[1] + 2*radius >= 400 || coordinates[1] <= 0) {
            dy *= -1;
        }

        if (coordinates[1] >= 400) {coordinates[1] -= 20;}
        if (coordinates[1] <= 0) {coordinates[1] += 5;}

        coordinates[1] += dy * deltaTime;
    }

    public void jump() {
        dy = -300;
    }

    public Entity shoot(ArrayList<Entity> heroes) {
        for (Entity hero : heroes) {
            double heroY = hero.getCenterCoords()[1];

            // If y coordinate of center of enemy is between the center coord of the
            // hero +- its radius, then it's a hit
            if (getCenterCoords()[1] <= heroY + hero.getRadius()
                    && getCenterCoords()[1] >= heroY - hero.getRadius()) {
                hero.takeDamage(100);

                return hero;
            }
        }

        return null;
    }
}
