import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Controller {
    private long lastTime = 0, lastSpawn = 0, lastTeleport = 0, lastCoin = 0;
    private final View view;
    private final BackgroundModel background;
    private final AnimationTimer timer;
    private final Model model;
    private final long coinInterval = 2_000_000_000L;
    private final long spawnInterval = 3_000_000_000L;
    private final long teleportInterval = 500_000_000L;
    private final long shootCooldown = 1_000_000_000L;
    private boolean canShoot = true;


    public Controller() {
        view = new View(640, 440);
        background = new BackgroundModel();
        model = new Model();

        Enemy enemy = model.getEnemy();

        // If spacebar is pressed, jump
        view.scene.setOnKeyPressed( (event) -> {
            if (event.getCode() == KeyCode.SPACE) {
                enemy.jump();
            } else if (event.getCode() == KeyCode.E && canShoot) {
                enemy.shoot(model.getHeroes());
                canShoot = false;

                Timeline cooldown = new Timeline(new KeyFrame(Duration.seconds(1), e -> canShoot = true));
                cooldown.setCycleCount(1);
                cooldown.play();
            }
        });

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    lastSpawn = now;
                    lastTeleport = now;
                    lastCoin = now;
                    return;
                }

                double deltaTime = (now - lastTime) * 1e-9;

                // Update background scroll
                updateBackground(deltaTime);

                // Update enemy position
                enemy.updatePosition(deltaTime);
                view.setEnemySpritePos(enemy.getCoords()[0], enemy.getCoords()[1]);


                // Spawn new hero every 3 seconds
                if (now - lastSpawn >= spawnInterval) {
                    model.createHero();
                    view.spawnHero(model.getHeroes().getLast());

                    lastSpawn = now;
                }

                if (now - lastTeleport >= teleportInterval) {
                    for (Entity hero : model.getHeroes()) {
                        if (hero instanceof Tank) {
                            ((Tank) hero).teleport();
                        }
                    }

                    lastTeleport = now;
                }

                // Update hero positions
                killHeroes();
                updateHeros(deltaTime);
                view.setHeroSpritesPos(model.getHeroes());

                // If 2 seconds have elapsed, spawn a new coin
                if (now - lastCoin >= coinInterval) {
                    System.out.println("COIn");
                    model.createCoin();
                    view.spawnCoin();

                    lastCoin = now;
                }

                // Update position of coins
                updateCoins(deltaTime);
                view.setCoinsPos(model.getCoinArray());

                lastTime = now;
            }
        };
    }

    public Stage getStage() {
        return view.getStage();
    }

    public void startGame() {
        timer.start();
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

    private void updateCoins(double deltaTime) {
        for (Coin coin : model.getCoinArray()) {
            coin.updatePosition(deltaTime);
        }
    }

    // Kill hero if dead (i.e hp=0)
    private void killHeroes() {
        for (int i=0; i < model.getHeroes().size(); i++) {
            if (model.getHeroes().get(i).isDead()) {
                view.removeHero(i);
            }
        }
    }
}
