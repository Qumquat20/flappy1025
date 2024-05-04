public class BackgroundModel {
    double position1 = 0;
    double position2 = 640;
    double dx = 120;

    public void updatePositions(double deltaTime) {
        position1 -= dx * deltaTime;
        position2 -= dx * deltaTime;

        // When position is < 640 then it is totally off screen and has to wrap around
        if (position1 <= -640) { position1 = 640; }
        if (position2 <= -640) { position2 = 640; }
    }

    public double getPosition1() {
        return position1;
    }

    public double getPosition2() {
        return position2;
    }

    public void increaseDx(double addDx) {
        dx += addDx;
    }
}
