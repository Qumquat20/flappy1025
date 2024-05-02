import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class Controller {
    private long lastTime = 0;
    private final View view;
    private final BackgroundModel background;
    private final AnimationTimer timer;
    private Enemy enemy;
    private Entity[] heros;
    private double scrollSpeed = 0.25;

    public Controller() {
        view = new View(640, 440);
        background = new BackgroundModel(scrollSpeed);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if( lastTime == 0 ) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) * 1e-9;

                updateBackground(deltaTime);

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
        background.updatePositions();
        view.getBackgrounds()[0].setLayoutX(background.getPosition1());
        view.getBackgrounds()[1].setLayoutX(background.getPosition2());
    }
}
