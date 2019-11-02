package bg.sofia.uni.fmi.mjt.dungeon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import bg.sofia.uni.fmi.mjt.dungeon.actor.Enemy;
import bg.sofia.uni.fmi.mjt.dungeon.actor.Hero;
import bg.sofia.uni.fmi.mjt.dungeon.treasure.Spell;
import bg.sofia.uni.fmi.mjt.dungeon.treasure.Treasure;
import bg.sofia.uni.fmi.mjt.dungeon.treasure.Weapon;

public class GameEngineTest {
    private Hero hero;
    private char[][] map;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private GameEngine gameEngine;

    @Before
    public void setup() {
        hero = new Hero("hero", 100, 100);
        map = new char[][]{ "###".toCharArray(),
                            "TS.".toCharArray(),
                            "#EG".toCharArray() };
        enemies = new Enemy[] {new Enemy("enemy", 100, 0, new Weapon("enemy weapon", 30), null)};
        treasures = new Treasure[] {new Weapon("strong weapon", 50)};
        gameEngine = new GameEngine(map, hero, enemies, treasures);
    }

    @Test
    public void testMoveToEmptyBlock() {
        String moveMessage = gameEngine.makeMove(Direction.RIGHT);

        assertEquals("You moved successfully to the next position.", moveMessage);
        assertEquals('.', gameEngine.getMap()[1][1]);
        assertEquals('H', gameEngine.getMap()[1][2]);
    }

    @Test
    public void testMoveToObstacle() {
        gameEngine.makeMove(Direction.RIGHT);
        String moveMessage = gameEngine.makeMove(Direction.UP);

        assertEquals("Wrong move. There is an obstacle and you cannot bypass it.", moveMessage);
        assertEquals('H', gameEngine.getMap()[1][2]);
    }

    @Test
    public void testMoveToTreasure() {
        String moveMessage = gameEngine.makeMove(Direction.LEFT);

        assertEquals("Weapon found! Damage points: 50", moveMessage);
        assertEquals('.', gameEngine.getMap()[1][1]);
        assertEquals('H', gameEngine.getMap()[1][0]);

        assertEquals("strong weapon", gameEngine.getHero().getWeapon().getName());
        assertEquals(50, gameEngine.getHero().getWeapon().getDamage());
    }

}
