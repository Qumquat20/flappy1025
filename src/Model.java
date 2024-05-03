import java.util.ArrayList;
import java.util.Random;

public class Model {
    private Enemy enemy;
    private ArrayList<Entity> heroes = new ArrayList<Entity>();
    private int coins;

    public Model() {
        enemy = new Enemy(new double[] {75, 300}, 30);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int amount) {
        coins = amount;
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public ArrayList<Entity> getHeroes() {
        return heroes;
    }

    // Creates either a Tank, CorpsACorps or Furtif hero with a radius between
    // 10 and 45 with a random initial y coordinate at right edge of screen
    public void createHero() {
        Random rand = new Random();

        // Initialize radius as random value between 10 and 45
        // Formula = rand.nextInt( (max - min) + 1 ) + min
        int radius = rand.nextInt(36) + 10;

        // Initialize y coordinate as random value between 0 and 400
        int yCord = rand.nextInt(400);
        double[] initialCoords = {640, yCord};

        // Random integer to determine which type of hero to create
        int heroType = rand.nextInt(3);

        switch (heroType) {
            case 0:
                heroes.add(new CorpsACorps(initialCoords, radius));

            case 1:
                heroes.add(new Tank(initialCoords, radius));

            case 2:
                heroes.add(new Furtif(initialCoords, radius));
        }

    }

    // Return true if objects collide, false otherwise
    public static boolean collides(Entity enemy, Entity hero) {
        double[] enemyCenterCoords = enemy.getCenterCoords();
        double[] heroCenterCoords = hero.getCenterCoords();

        double yDiff = enemyCenterCoords[1] - heroCenterCoords[1];
        double xDiff = enemyCenterCoords[0] - heroCenterCoords[1];

        double centerDist = Math.sqrt(Math.pow(yDiff, 2) + Math.pow(xDiff, 2));

        return centerDist <= (enemy.getRadius() + hero.getRadius());
    }
}
