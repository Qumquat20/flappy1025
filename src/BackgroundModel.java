public class BackgroundModel {
    double position1 = 0;
    double position2 = 640;
    double dx = 120;

    public void updatePositions(double deltaTime) {
        position1 -= dx * deltaTime;
        position2 -= dx * deltaTime;

        if (position1 <= -640) { position1 = 640; }
        if (position2 <= -640) { position2 = 640; }
    }

    public double getPosition1() {
        return position1;
    }

    public double getPosition2() {
        return position2;
    }

    public void setDx(double newDx) {
        dx = newDx;
    }
}
