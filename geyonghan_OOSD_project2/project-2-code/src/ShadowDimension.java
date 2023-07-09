import bagel.*;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2022
 * Please enter your name below
 * @author Yonghan Ge
 */
public class ShadowDimension extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final Image BACKGROUND_IMAGE_0 = new Image("res/background0.png");
    private final Image BACKGROUND_IMAGE_1 = new Image("res/background1.png");
    private final static String FAE_IMAGE = "res/fae/faeRight.png";
    private final static String WALL_IMAGE = "res/wall.png";
    private final static String SINKHOLE_IMAGE = "res/sinkhole.png";
    private final static String TREE_IMAGE = "res/tree.png";
    private final static String LEVEL0_FILENAME = "res/level0.csv";
    private final static String LEVEL1_FILENAME = "res/level1.csv";
    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final static String START_MESSAGE = "PRESS SPACE TO START";
    private final static String PLAY_MESSAGE = "USE ARROW KEYS TO FIND GATE";
    private final static String ATTACK_MESSAGE = "PRESS A TO ATTACK";
    private final static String DEFEAT_MESSAGE = "DEFEAT NAVEC TO WIN";
    private final static int TITLE_MESSAGE_X = 260;
    private final static int TITLE_MESSAGE_Y = 250;
    private final static int START_MESSAGE_X = 350;
    private final static int START_MESSAGE_Y = 440;
    private final static int START_MESSAGE_X_1 = 350;
    private final static int START_MESSAGE_Y_1 = 340;
    private final static int PLAY_MESSAGE_X = 300;
    private final static int PLAY_MESSAGE_Y = 370;
    private final static int ATTACK_MESSAGE_X = 350;
    private final static int ATTACK_MESSAGE_Y = 390;
    private final static int DEFEAT_MESSAGE_X = 350;
    private final static int DEFEAT_MESSAGE_Y = 440;
    private final static String WIN_MESSAGE = "CONGRATULATIONS!";
    private final static String LOSE_MESSAGE = "GAME OVER!";
    private final static String LEVEL_MESSAGE = "LEVEL COMPLETE!";
    private final static int WIN_MESSAGE_X = 265;
    private final static int WIN_MESSAGE_Y = 380;
    private final static int LOSE_MESSAGE_X = 350;
    private final static int LOSE_MESSAGE_Y = 380;
    private final static int LEVEL_MESSAGE_X = 300;
    private final static int LEVEL_MESSAGE_Y = 380;
    private final static int WIN_X = 950;
    private final static int WIN_Y = 670;
    private Player Fae;
    private Navec navec;
    private boolean gameWin;
    private boolean gameLose;
    private boolean gameStart;
    private boolean levelUp;
    private boolean levelUpStart;
    private double levelUpTime;
    private static final int LEVEL_UP_TIME = 3000;
    private static final int MIN_HEALTH_POINT = 0;
    private Point topLeft;
    private Point bottomRight;
    private final Font FONT_BIG = new Font("res/frostbite.ttf", 75);
    private final Font FONT_SMALL = new Font("res/frostbite.ttf", 40);
    private final ArrayList<Object> walls = new ArrayList<>();
    private ArrayList<Sinkhole> sinkholes = new ArrayList<>();
    private final ArrayList<Object> trees = new ArrayList<>();
    private final ArrayList<Demon> demons = new ArrayList<>();

    private final TimeControl timeControl = new TimeControl();


    public ShadowDimension() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV(LEVEL0_FILENAME);
        gameStart = false;
        gameLose = false;
        gameWin = false;
        levelUp = false;
        levelUpStart=false;
        levelUpTime=0;
    }

    /**The main method which makes use of ShadowDimension
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }

    /**
     * Method used to read file and create objects
     * @param filename this is the first parameter that store the filename needed to be read
     */
    private void readCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] parts = text.split(",");
                /*save the read numbers according to types*/
                switch (parts[0]) {
                    case "Fae":
                        Fae = new Player(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), FAE_IMAGE);
                        break;
                    case "Wall":
                        walls.add(new Wall(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), WALL_IMAGE));
                        break;
                    case "Sinkhole":
                        sinkholes.add(new Sinkhole(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), SINKHOLE_IMAGE));
                        break;
                    case "TopLeft":
                        topLeft = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                        break;
                    case "BottomRight":
                        bottomRight = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                        break;
                    case "Tree":
                        trees.add(new Tree(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), TREE_IMAGE));
                        break;
                    case "Demon":
                        demons.add(new Demon(Double.parseDouble(parts[1]), Double.parseDouble(parts[2])));
                        break;
                    case "Navec":
                        navec = new Navec(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        /*print the start messages of two levels*/
        if (!gameStart) {
            if (!levelUpStart) {
                FONT_BIG.drawString(GAME_TITLE, TITLE_MESSAGE_X, TITLE_MESSAGE_Y);
                FONT_SMALL.drawString(START_MESSAGE, START_MESSAGE_X, START_MESSAGE_Y);
                FONT_SMALL.drawString(PLAY_MESSAGE, PLAY_MESSAGE_X, PLAY_MESSAGE_Y);
            } else{
                FONT_SMALL.drawString(START_MESSAGE, START_MESSAGE_X_1, START_MESSAGE_Y_1);
                FONT_SMALL.drawString(ATTACK_MESSAGE, ATTACK_MESSAGE_X, ATTACK_MESSAGE_Y);
                FONT_SMALL.drawString(DEFEAT_MESSAGE, DEFEAT_MESSAGE_X, DEFEAT_MESSAGE_Y);
            }
            if (input.wasPressed(Keys.SPACE)) {
                gameStart = true;
            }
        }
        /*check the frame for showing level up message*/
        if (levelUp && !levelUpStart) {
            levelUpTime++;
            FONT_BIG.drawString(LEVEL_MESSAGE, LEVEL_MESSAGE_X, LEVEL_MESSAGE_Y);
        }if (((levelUpTime * 1000) / 60) > LEVEL_UP_TIME || input.wasPressed(Keys.W)) {
                /*restore the new sinkhole locations for level 1*/
                sinkholes = new ArrayList<>();
                levelUpStart = true;
                readCSV(LEVEL1_FILENAME);
                gameStart = false;
                levelUpTime=0;
        }
        if (gameLose) {
            FONT_BIG.drawString(LOSE_MESSAGE, LOSE_MESSAGE_X, LOSE_MESSAGE_Y);
        }
        if (gameWin) {
            FONT_BIG.drawString(WIN_MESSAGE, WIN_MESSAGE_X, WIN_MESSAGE_Y);
        }
        /*print all the objects on the screen*/
        if (gameStart && !gameWin && !gameLose) {
            if (!levelUp && !levelUpStart) {
                BACKGROUND_IMAGE_0.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
                for (Object wall : walls) {
                    wall.placeObject();
                }
                for (Sinkhole sinkhole : sinkholes) {
                    sinkhole.placeObject();
                }
                Fae.placeObject(input, walls, sinkholes, demons, navec, topLeft, bottomRight); /*TOGO*/
                if (Fae.getPosition_x() >= WIN_X && Fae.getPosition_y() >= WIN_Y) {
                    levelUp = true;
                }
                if (Fae.getHealthPoint() <= MIN_HEALTH_POINT) {
                    gameLose = true;
                }
            }if (levelUpStart && gameStart) {
                BACKGROUND_IMAGE_1.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
                for (Object tree : trees) {
                    tree.placeObject();
                }
                for (Sinkhole sinkhole : sinkholes) {
                    sinkhole.placeObject();
                }
                for (Demon demon : demons) {
                    if (demon.getHealthPoint() != MIN_HEALTH_POINT) {
                        demon.placeDemon(trees, sinkholes, Fae, topLeft, bottomRight);
                    }
                }
                navec.placeNavec(trees, sinkholes, Fae, topLeft, bottomRight);
                Fae.placeObject(input, trees, sinkholes, demons, navec, topLeft, bottomRight);
                if (navec.getHealthPoint() == MIN_HEALTH_POINT) {
                    gameWin = true;
                }
                if (Fae.getHealthPoint() == MIN_HEALTH_POINT) {
                    gameLose = true;
                }
                /*time control for controlling speed of enemies*/
                if (input.wasPressed(Keys.L)) {
                    timeControl.increaseTimeScale(demons, navec);
                }
                if (input.wasPressed(Keys.K)) {
                    timeControl.decreaseTimeScale(demons, navec);
                }
            }
        }

    }
}
