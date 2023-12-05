package team.ocean;

import javafx.scene.image.ImageView;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ObstacleTest {

    @Test
    void testCreate() throws URISyntaxException {
        ImageView fireImage = Obstacle.create(Obstacle.Type.FIRE, 30, 1, 1, 1);
        assertNotNull(fireImage);
        assertEquals(30, fireImage.getFitWidth());
        assertEquals(30, fireImage.getFitHeight());
        assertEquals(30, fireImage.getTranslateX());
        assertEquals(30, fireImage.getTranslateY());
    }

    @Test
    void testAddFire() throws URISyntaxException {
        ImageView fireImage = Obstacle.addFire(30, 1, 1);
        assertNotNull(fireImage);
        assertEquals(30, fireImage.getFitWidth());
        assertEquals(30, fireImage.getFitHeight());
        assertEquals(30, fireImage.getTranslateX());
        assertEquals(30, fireImage.getTranslateY());
        assertEquals(1, Obstacle.firePositions.size());
        assertEquals(new Pair<>(1, 1), Obstacle.firePositions.get(0));
    }

    @Test
    void testAddPit() throws URISyntaxException {
        ImageView pitImage = Obstacle.addPit(30,  2, 2);
        assertNotNull(pitImage);
        assertEquals(30, pitImage.getFitWidth());
        assertEquals(30, pitImage.getFitHeight());
        assertEquals(60, pitImage.getTranslateX());
        assertEquals(60, pitImage.getTranslateY());
        assertEquals(1, Obstacle.pitPositions.size());
        assertEquals(new Pair<>(2, 2), Obstacle.pitPositions.get(0));
    }

    @Test
    void testAddSpike() throws URISyntaxException {
        ImageView spikeImage = Obstacle.addSpike(30, 3, 3);
        assertNotNull(spikeImage);
        assertEquals(30, spikeImage.getFitWidth());
        assertEquals(30, spikeImage.getFitHeight());
        assertEquals(90, spikeImage.getTranslateX());
        assertEquals(90, spikeImage.getTranslateY());
        assertEquals(1, Obstacle.spikePositions.size());
        assertEquals(new Pair<>(3, 3), Obstacle.spikePositions.get(0));
    }

    @Test
    void testAddTeleport() throws URISyntaxException {
        ImageView teleportImage = Obstacle.addTeleport(30,  4, 4);
        assertNotNull(teleportImage);
        assertEquals(30, teleportImage.getFitWidth());
        assertEquals(30, teleportImage.getFitHeight());
        assertEquals(120, teleportImage.getTranslateX());
        assertEquals(120, teleportImage.getTranslateY());
        assertEquals(1, Obstacle.teleportPositions.size());
        assertEquals(new Pair<>(4, 4), Obstacle.teleportPositions.get(0));
    }
}
