public class BackgroundModel {
    double position1 = 0;
    double position2 = 640;
    double deltaTime;

    public BackgroundModel(double deltaTime) {
        this.deltaTime = deltaTime;
    }

    public void updatePositions() {
        position1 -= deltaTime;
        position2 -= deltaTime;

        if (position1 <= -640) { position1 = 640; }
        if (position2 <= -640) { position2 = 640; }
    }

    public double getPosition1() {
        return position1;
    }

    public double getPosition2() {
        return position2;
    }
}
