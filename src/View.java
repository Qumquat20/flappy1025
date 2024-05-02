import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class View extends VBox{
    private HBox gameBox;
    private Stage stage;
    private Scene scene;
    private final ImageView bg1, bg2;

    public View(int WIDTH, int HEIGHT) {
        Pane gameBox = new Pane();
        MenuView menu = new MenuView();

        // Intialize backgrounds for continuous scrolling
        bg1 = new ImageView(new Image("bg.png"));
        bg2 = new ImageView(new Image("bg.png"));

        gameBox.getChildren().addAll(bg1, bg2);

        this.getChildren().addAll(gameBox,menu);

        scene = new Scene(this, WIDTH, HEIGHT);

        stage = new Stage();
        stage.setTitle("UNO reverse Flappy Enemy VS Hero with GUNS POWPOWPOW");
        stage.setScene( scene );
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
}
