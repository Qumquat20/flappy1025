import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class MenuView extends HBox{
    public MenuView() {
        // Left area of menu for pause button
        HBox buttonRegion = new HBox();
        Button pauseButton = new Button("Pause");
        buttonRegion.getChildren().add(pauseButton);
        buttonRegion.setAlignment(Pos.CENTER_RIGHT);

        // Center area of menu for hp counter
        HBox lifeRegion = new HBox();
        Text lifeText = new Text("Life: ");
        lifeRegion.getChildren().add(lifeText);
        lifeRegion.setAlignment(Pos.CENTER);

        // Right area of menu for coin counter
        HBox coinregion = new HBox();
        Text coinText = new Text("Coins: ");
        coinregion.getChildren().add(coinText);
        coinregion.setAlignment(Pos.CENTER_LEFT);

        // Set size of areas to fit
        buttonRegion.setPrefWidth(280);
        lifeRegion.setPrefWidth(80);
        coinregion.setPrefWidth(280);

        this.getChildren().addAll(buttonRegion, new Separator(Orientation.VERTICAL),
                lifeRegion, new Separator(Orientation.VERTICAL), coinregion);
    }
}
