import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Text;

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
    private boolean canShoot = true;
    private boolean paused;


    public Controller() {
        view = new View(640, 440);
        background = new BackgroundModel();
        model = new Model();

        Enemy enemy = model.getEnemy();

        // If spacebar is pressed, jump
        // If e is pressed, shoot
        view.scene.setOnKeyPressed( (event) -> {
            if (event.getCode() == KeyCode.SPACE) {
                enemy.jump();
            } else if (event.getCode() == KeyCode.E && canShoot) {
                Entity heroHit = enemy.shoot(model.getHeroes());

                // Check type of hero hit to gain the right amount of coins
                if ( heroHit != null) {
                    switch (heroHit) {
                        case Tank tank -> {
                            model.addCoins(7);
                        }

                        case Furtif furtif -> {
                            model.addCoins(8);
                        }

                        case CorpsACorps corpsacorps -> {
                            model.addCoins(5);
                        }
                        default -> System.out.println("Unexpected value: " + heroHit);
                    }
                    view.updateCoinsMenu(model.getCollectedCoins());
                };

                // Draw bullet/laser/whatever line showing shot
                view.drawLine(enemy.getCenterCoords()[0], enemy.getCenterCoords()[1]);
                canShoot = false;

                // Enact 1 sec cooldown on shooting
                Timeline cooldown = new Timeline(new KeyFrame(Duration.seconds(1), e -> canShoot = true));
                cooldown.setCycleCount(1);
                cooldown.play();

                // Remove bullet/laser/whatever line showing shot
                Timeline removeLine = new Timeline(new KeyFrame(Duration.millis(150), e -> view.removeLine()));
                removeLine.setCycleCount(1);
                removeLine.play();
            }
        });

        // When button is pressed, pause the game (wtf so ugly)
        Button pauseButton = (Button) ( (HBox) view.getMenu().getChildren().getFirst() ).getChildren().getFirst();
        pauseButton.setOnAction( (event) -> {
            paused = !paused;
        });

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (paused) {
                    lastTime = 0;
                    return;
                }

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

                // Teleport Tank heroes every 0.5 seconds
                if (now - lastTeleport >= teleportInterval) {
                    for (Entity hero : model.getHeroes()) {
                        if (hero instanceof Tank) {
                            ((Tank) hero).teleport();
                        }
                    }

                    lastTeleport = now;
                }

                // Update hero positions
                updateHeros(deltaTime);
                view.setHeroSpritesPos(model.getHeroes());

                // Spawn a new coin every 2 seconds
                if (now - lastCoin >= coinInterval) {
                    model.createCoin();
                    view.spawnCoin();

                    lastCoin = now;
                }

                // Update position of coins
                updateCoins(deltaTime);
                view.setCoinsPos(model.getCoinArray());

                // Handle collisions between entitites and kill dead heroes
                handleCollisions();

                // Check if enemy is dead, if so then GAME OVER
                if (enemy.isDead()) {
                    handleEnemyDead();
                }

                // Remove dead heroes
                killHeroes();

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

    // Remove hero from view and model if dead (i.e hp=0)
    private void killHeroes() {
        ArrayList<Integer> deadHeroes = new ArrayList<>();

        // Store indexes of dead heroes
        for (int i=0; i < model.getHeroes().size(); i++) {
            if (model.getHeroes().get(i).isDead()) {
                deadHeroes.add(i);
            }
        }

        // Use indexes to remove from model and view
        for (int i : deadHeroes) {
            view.removeHero(i);
            model.getHeroes().remove(i);
        }
    }

    private void handleCollisions() {
        // Iterate through heroes, if hero collides with enemy, execute appropriate
        // action and continue
        for (Entity hero : model.getHeroes()) {
            if (Model.collides(model.getEnemy(), hero)) {
                switch (hero) {
                    case Tank tank -> {
                        model.getEnemy().takeDamage(50);
                        tank.takeDamage(100);
                    }

                    case Furtif furtif -> {
                        model.addCoins(-10);
                        view.updateCoinsMenu(model.getCollectedCoins());
                        furtif.takeDamage(100);
                    }

                    case CorpsACorps corpsacorps -> {
                        model.getEnemy().takeDamage(100);
                        corpsacorps.takeDamage(100);
                    }
                    default -> System.out.println("Unexpected value: " + hero);
                }
                System.out.println(model.getEnemy().getHp());
                view.updateHealthMenu(model.getEnemy().getHp());
            }
        }

        // Iterate through coins, if hero collides with one, remove coin from
        // screen and add to collectedCoins in model
        ArrayList<Integer> collidedCoins = new ArrayList<>();

        for (int i=0; i < model.getCoinArray().size(); i++) {
            if (Model.collides(model.getEnemy(), model.getCoinArray().get(i))) {
                collidedCoins.add(i);

                model.addCoins(1);
                model.increaseDx(10);
                model.getEnemy().increaseGravity(15);

                view.updateCoinsMenu(model.getCollectedCoins());

                background.increaseDx(10);

                view.removeCoin(i);
                model.getCoinArray().remove(i);

                return;
            }
        }
    }

    // Display GAME OVER when enemy dies
    private void handleEnemyDead() {
        paused = true;

        Text gameText = new Text("GAME");
        Text overText = new Text("OVER");

        gameText.setFont(Font.font("Arial", 100));
        gameText.setLayoutX(190);
        gameText.setLayoutY(200);

        overText.setFont(Font.font("Arial", 100));
        overText.setLayoutX(200);
        overText.setLayoutY(300);

        view.getGameBox().getChildren().addAll(gameText, overText);
    }
}
