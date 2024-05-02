public class Entity {
    protected double dx, dy, hp, damage;
    protected double[] coordinates;

    public Entity(double[] initialCoords) {
        coordinates = initialCoords;
        hp = 100;
    }

    // Return coordinates of the center of the Entity
    public double[] getCoords() {
        return coordinates;
    }

    // Take dmg damage (i.e decrease hp by given dmg)
    public void takeDamage(int dmg) {
        hp -= dmg;
    }

    // Do dmg damage to given Entity
    public void doDamage(Entity entity, int dmg) {
        entity.takeDamage(dmg);
    }
}
