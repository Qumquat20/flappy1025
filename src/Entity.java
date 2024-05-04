public class Entity {
    protected double dx, dy, hp, damage;
    protected double[] coordinates;
    protected int radius;

    public Entity(double[] initialCoords, int radius, int dx) {
        coordinates = initialCoords;
        hp = 100;
        this.dx = dx;
        this.radius = radius;
    }

    // Return coordinates of the top left corner of the Entity
    public double[] getCoords() {
        return coordinates;
    }

    // Return coordinates of center of enemy radius
    public double[] getCenterCoords() {
        return new double[] {coordinates[0] + radius, coordinates[1] + radius};
    }

    // Take dmg damage (i.e decrease hp by given dmg)
    public void takeDamage(int dmg) {
        hp -= dmg;
    }

    public double getHp() {
        return hp;
    }

    public void increaseDx(int addDx) {
        dx += addDx;
    }

    public int getRadius() {
        return radius;
    }

    public void updatePosition(double deltaTime) {
        return;
    }

    // Return true if entity is dead, false otherwise
    public boolean isDead() {
        return hp <= 0;
    }
}
