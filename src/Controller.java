import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Controller {
    private long lastTime = 0;
    private final View view;
    private final BackgroundModel background;
    private final AnimationTimer timer;
    private Enemy enemy;
    private Entity[] heros;

    public Controller() {
        view = new View(640, 440);
        background = new BackgroundModel();

        // Initialize enemy object
        enemy = new Enemy(new double[] {0, 0});
        System.out.println(enemy.dy);

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

                updateBackground(deltaTime);
                enemy.updatePosition(deltaTime);
                view.setEnemySpritePos(enemy.getCoords()[1]);

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
}
