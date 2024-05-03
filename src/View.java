import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class View extends VBox{
    private final ImageView bg1, bg2;
    private MenuView menu;
    private Pane gameBox;
    private Stage stage;
    public Scene scene;
    private ImageView enemyImgView;
    private ArrayList<ImageView> heroImgViews = new ArrayList<ImageView>();
    private ArrayList<ImageView> coinImgViews = new ArrayList<ImageView>();

    public View(int WIDTH, int HEIGHT) {
        // Initialize game and menu view
        gameBox = new Pane();
        menu = new MenuView();

        // Set respective heights
        gameBox.setPrefHeight(400);
        menu.setPrefHeight(40);

        // Intialize backgrounds for continuous scrolling
        bg1 = new ImageView(new Image("bg.png"));
        bg2 = new ImageView(new Image("bg.png"));

        // Create enemy ImageView
        enemyImgView = new ImageView(new Image("enemy.png"));

        gameBox.getChildren().addAll(bg1, bg2, enemyImgView);

        this.getChildren().addAll(gameBox, new Separator(), menu);

        scene = new Scene(this, WIDTH, HEIGHT);

        stage = new Stage();
        stage.setTitle("UNO reverse Flappy Enemy VS Hero with GUNS POWPOWPOW");
        stage.setScene( scene );
    }

    public Stage getStage() {
        return stage;
    }

    public Pane getGameBox() {
        return gameBox;
    }

    public ImageView[] getBackgrounds() {
        return new ImageView[] {bg1, bg2};
    }

    // Only update y coordinate because x doesn't change
    public void setEnemySpritePos(double x, double y) {
        enemyImgView.setX(x);
        enemyImgView.setY(y);
    }

    public void spawnHero(Entity hero) {
        // Create ImageView object
        heroImgViews.add(new ImageView(new Image("hero.png", 2*hero.getRadius(),
                2*hero.getRadius(), false, false)));
        int lastIndex = heroImgViews.size() - 1;
        // Add ImageView to gameBox
        gameBox.getChildren().add(heroImgViews.get(lastIndex));
    }

    // Remove hero at index i in hero array
    public void removeHero(int i) {
        gameBox.getChildren().remove(heroImgViews.get(i));
    }

    public void setHeroSpritesPos(ArrayList<Entity> heroes) {
        for (int i = 0; i < heroImgViews.size(); i++) {
            double[] heroCoords = heroes.get(i).getCoords();
            heroImgViews.get(i).setX(heroCoords[0]);
            heroImgViews.get(i).setY(heroCoords[1]);
        }
    }

    public void spawnCoin() {
        // Create ImageView object
        coinImgViews.add(new ImageView(new Image("coin.png", 30, 30, false, false)));
        int lastIndex = coinImgViews.size() - 1;
        // Add ImageView to gameBox
        gameBox.getChildren().add(coinImgViews.get(lastIndex));
    }

    public void setCoinsPos(ArrayList<Coin> coins) {
        for (int i = 0; i < coinImgViews.size(); i++) {
            double[] coinCoords = coins.get(i).getCoords();
            coinImgViews.get(i).setX(coinCoords[0]);
            coinImgViews.get(i).setY(coinCoords[1]);
        }
    }
}
