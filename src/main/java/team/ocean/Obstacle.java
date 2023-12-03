package team.ocean;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class Obstacle {
    enum Type {
        FIRE,
        SPIKE,
        HOLE,
        TELEPORT
    }
    public static ImageView create(Type type, int size, int length, int xPos, int yPos) throws URISyntaxException {

        Image image;

        image = switch (type) {
            case FIRE -> new Image(team.ocean.Obstacle.class.getResource("fire2.gif").toURI().toString());
            case SPIKE -> new Image(team.ocean.Obstacle.class.getResource("sword.gif").toURI().toString());
            case TELEPORT -> new Image(team.ocean.Obstacle.class.getResource("teleport.png").toURI().toString());
            case HOLE -> new Image(team.ocean.Obstacle.class.getResource("galaxy.gif").toURI().toString());
            default -> null;
        };
        ImageView obsImage = new ImageView(image);
        obsImage.setFitWidth(size*length);
        obsImage.setFitHeight(size);
        obsImage.setTranslateX(xPos*size);
        obsImage.setTranslateY(yPos*size);
        return obsImage;
    }
    public static ArrayList<Pair<Integer, Integer>> firePositions = new ArrayList<>();
    public static ArrayList<Pair<Integer, Integer>> spikePositions = new ArrayList<>();
    public static ArrayList<Pair<Integer, Integer>> telePortPositions = new ArrayList<>();
    public static ArrayList<Pair<Integer, Integer>> pitPositions = new ArrayList<>();

    public static ImageView addFire(int size, int length, int xPos, int yPos) throws URISyntaxException {
        firePositions.add(new Pair<>(xPos,yPos));
        return create(Type.FIRE, size, length, xPos, yPos);
    }
    public static ImageView addPit(int size, int length, int xPos, int yPos) throws URISyntaxException {
        pitPositions.add(new Pair<>(xPos,yPos));
        return create(Type.HOLE, size, length, xPos, yPos);
    }
    public static ImageView addSpike(int size, int length, int xPos, int yPos) throws URISyntaxException {
        spikePositions.add(new Pair<>(xPos,yPos));
        return create(Type.SPIKE, size, length, xPos, yPos);
    }
    public static ImageView addTeleport(int size, int length, int xPos, int yPos) throws URISyntaxException {
        telePortPositions.add(new Pair<>(xPos,yPos));
        return create(Type.TELEPORT, size, length, xPos, yPos);
    }
}
