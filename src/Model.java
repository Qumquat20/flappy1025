import java.util.ArrayList;
import java.util.Random;

public class Model {
    private Enemy enemy;
    private ArrayList<Entity> heroes = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private int collectedCoins;

    public Model() {
        enemy = new Enemy(new double[] {75, 300}, 30);
    }

    public ArrayList<Coin> getCoinArray() {
        return coins;
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    public void setCoins(int amount) {
        collectedCoins = amount;
    }

    public void addCoins(int amount) {
        if (collectedCoins + amount < 0) {
            collectedCoins = 0;
        } else {
            collectedCoins += amount;
        }
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

        // Initialize y coordinate as random value between 0 and 400-2xradius
        int yCord = rand.nextInt(400-2*radius);
        double[] initialCoords = {640, yCord};

        // Random integer to determine which type of hero to create
        int heroType = rand.nextInt(3);

        switch (heroType) {
            case 0:
                heroes.add(new CorpsACorps(initialCoords, radius));
                break;

            case 1:
                heroes.add(new Tank(initialCoords, radius));
                break;

            case 2:
                heroes.add(new Furtif(initialCoords, radius));
                break;
        }

    }

    public void createCoin() {
        Random rand = new Random();

        int yCord = rand.nextInt(370);
        double[] initialCoords = {640, yCord};

        coins.add(new Coin(initialCoords, 15));
    }

    // Return true if objects collide, false otherwise
    public static boolean collides(Entity enemy, Entity other) {
        double[] enemyCenterCoords = enemy.getCenterCoords();
        double[] otherCenterCoords = other.getCenterCoords();

        double yDiff = enemyCenterCoords[1] - otherCenterCoords[1];
        double xDiff = enemyCenterCoords[0] - otherCenterCoords[0];

        double centerDist = Math.sqrt(Math.pow(yDiff, 2) + Math.pow(xDiff, 2));

        return centerDist <= (enemy.getRadius() + other.getRadius());
    }
}
