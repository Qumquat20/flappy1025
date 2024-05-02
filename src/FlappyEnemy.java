import javafx.application.Application;
import javafx.scene.control.Separator;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class FlappyEnemy extends Application {

    public static void main( String[] args ) {
        FlappyEnemy.launch( args );
    }

    @Override
    public void start( Stage primaryStage ) {
        Controller controller = new Controller();

        controller.startGame();

        primaryStage = controller.getStage();
        primaryStage.show();
    }
}