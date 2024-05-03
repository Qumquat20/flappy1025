import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Controller {
    private long lastTime = 0;
    private final View view;
    private final BackgroundModel background;
    private final AnimationTimer timer, heroSpawnTimer;
    private final Model model;
    private final long spawnInterval = 3_000_000_000L;


    public Controller() {
        view = new View(640, 440);
        background = new BackgroundModel();
        model = new Model();

        Enemy enemy = model.getEnemy();

        model.getHeroes().add(new Furtif(new double[]{640, 300}, 30));
        view.spawnHero(model.getHeroes().getFirst());

        // If spacebar is pressed, jump
        view.scene.setOnKeyPressed( (event) -> {
            if (event.getCode() == KeyCode.SPACE) {
                enemy.jump();
            }
        } );


        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) * 1e-9;

                // Update background scroll
                updateBackground(deltaTime);

                // Update enemy position
                enemy.updatePosition(deltaTime);
                view.setEnemySpritePos(enemy.getCoords()[0], enemy.getCoords()[1]);

                // Update hero positions
                updateHeros(deltaTime);
                view.setHeroSpritesPos(model.getHeroes());

                lastTime = now;
            }
        };

        heroSpawnTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0 || now - lastTime >= spawnInterval) {
                    model.createHero();
                    System.out.println("Asdasds");

                    lastTime = now;
                }
            }
        };
    }

    public Stage getStage() {
        return view.getStage();
    }

    public void startGame() {
        timer.start();
        heroSpawnTimer.start();
    }

    private void updateBackground(double deltaTime) {
        background.updatePositions(deltaTime);
        view.getBackgrounds()[0].setLayoutX(background.getPosition1());
        view.getBackgrounds()[1].setLayoutX(background.getPosition2());
    }

    private void updateHeros(double deltaTime) {
        for (Entity hero: model.getHeroes()) {
            hero.updatePosition(deltaTime);
        }
    }
}
