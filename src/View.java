import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class View extends VBox{
    private final ImageView bg1, bg2;
    private HBox gameBox;
    private Stage stage;
    public Scene scene;
    private Image heroSprite;
    private ImageView enemyImgView;
    private ImageView[] heroImgViews;

    public View(int WIDTH, int HEIGHT) {
        Pane gameBox = new Pane();
        MenuView menu = new MenuView();

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

    public void addHero(ImageView hero) {

    }

    public Stage getStage() {
        return stage;
    }

    public HBox getGameBox() {
        return gameBox;
    }

    public ImageView[] getBackgrounds() {
        return new ImageView[] {bg1, bg2};
    }

    // Only update y coordinate because x doesn't change
    public void setEnemySpritePos(double y) {
        enemyImgView.setY(y);
    }

    public void drawHeroes() {

    }
}
