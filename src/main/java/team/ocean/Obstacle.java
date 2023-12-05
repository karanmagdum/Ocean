package team.ocean;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Represents different types of obstacles in the game.
 * Provides methods to create and add obstacles at specified positions.
 */
public class Obstacle {

    /**
     * Enumeration of possible obstacle types.
     */
    enum Type {
        FIRE,
        SPIKE,
        HOLE,
        TELEPORT
    }

    /**
     * Creates an ImageView of the specified obstacle type at the given position.
     *
     * @param type   The type of obstacle.
     * @param size   The size of the obstacle.
     * @param length The length of the obstacle.
     * @param xPos   The x-coordinate position.
     * @param yPos   The y-coordinate position.
     * @return The ImageView of the created obstacle.
     * @throws URISyntaxException If there is an issue with the URI syntax.
     */
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

    /** Lists of positions for each type of obstacle. */
    public static ArrayList<Pair<Integer, Integer>> firePositions = new ArrayList<>();
    public static ArrayList<Pair<Integer, Integer>> spikePositions = new ArrayList<>();
    public static ArrayList<Pair<Integer, Integer>> teleportPositions = new ArrayList<>();
    public static ArrayList<Pair<Integer, Integer>> pitPositions = new ArrayList<>();

    /**
     * Adds a fire obstacle at the specified position.
            *
            * @param size The size of the obstacle.
            * @param xPos The x-coordinate position.
            * @param yPos The y-coordinate position.
            * @return The ImageView of the added fire obstacle.
     * @throws URISyntaxException If there is an issue with the URI syntax.
    */
    public static ImageView addFire(int size, int xPos, int yPos) throws URISyntaxException {
        firePositions.add(new Pair<>(xPos,yPos));
        return create(Type.FIRE, size, 1, xPos, yPos);
    }

    /**
     * Adds a pit obstacle at the specified position.
     *
     * @param size The size of the obstacle.
     * @param xPos The x-coordinate position.
     * @param yPos The y-coordinate position.
     * @return The ImageView of the added pit obstacle.
     * @throws URISyntaxException If there is an issue with the URI syntax.
     */
    public static ImageView addPit(int size, int xPos, int yPos) throws URISyntaxException {
        pitPositions.add(new Pair<>(xPos,yPos));
        return create(Type.HOLE, size, 1, xPos, yPos);
    }

    /**
     * Adds a spike obstacle at the specified position.
     *
     * @param size The size of the obstacle.
     * @param xPos The x-coordinate position.
     * @param yPos The y-coordinate position.
     * @return The ImageView of the added spike obstacle.
     * @throws URISyntaxException If there is an issue with the URI syntax.
     */
    public static ImageView addSpike(int size, int xPos, int yPos) throws URISyntaxException {
        spikePositions.add(new Pair<>(xPos,yPos));
        return create(Type.SPIKE, size, 1, xPos, yPos);
    }

    /**
     * Adds a teleport obstacle at the specified position.
     *
     * @param size The size of the obstacle.
     * @param xPos The x-coordinate position.
     * @param yPos The y-coordinate position.
     * @return The ImageView of the added teleport obstacle.
     * @throws URISyntaxException If there is an issue with the URI syntax.
     */
    public static ImageView addTeleport(int size, int xPos, int yPos) throws URISyntaxException {
        teleportPositions.add(new Pair<>(xPos,yPos));
        return create(Type.TELEPORT, size, 1, xPos, yPos);
    }
}
