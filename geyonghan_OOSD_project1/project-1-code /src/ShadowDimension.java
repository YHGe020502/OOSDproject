import bagel.*;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2022
 * Please enter your name below
 *
 * @author Yonghan Ge
 */

public class ShadowDimension extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final static String FAE_IMAGE = "res/faeRight.png";
    private final static String WALL_IMAGE = "res/wall.png";
    private final static String SINKHOLE_IMAGE = "res/sinkhole.png";
    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final static String START_MESSAGE = "PRESS SPACE TO START";
    private final static String PLAY_MESSAGE = "USE ARROW KEYS TO FIND GATE";
    private final static int TITLE_MESSAGE_X = 260;
    private final static int TITLE_MESSAGE_Y = 250;
    private final static int START_MESSAGE_X = 350;
    private final static int START_MESSAGE_Y = 440;
    private final static int PLAY_MESSAGE_X = 300;
    private final static int PLAY_MESSAGE_Y = 500;
    private final static String WIN_MESSAGE = "CONGRATULATIONS!";
    private final static String LOSE_MESSAGE = "GAME OVER!";
    private final static int WIN_MESSAGE_X = 265;
    private final static int WIN_MESSAGE_Y = 380;
    private final static int LOSE_MESSAGE_X = 350;
    private final static int LOSE_MESSAGE_Y = 380;
    private Player Fae;
    private boolean gameWin;
    private boolean gameLose;
    private boolean gameStart;
    private Point topLeft;
    private Point bottomRight;
    private final Font FONT_BIG = new Font("res/frostbite.ttf", 75);
    private final Font FONT_SMALL = new Font("res/frostbite.ttf", 40);
    private int wallCount = 0;
    private int sinkHoleCount = 0;
    private final static int MAXIMUM_ENTRIES = 60;
    private final Wall[] WALLS = new Wall[MAXIMUM_ENTRIES];
    private final Sinkhole[] SINKHOLES = new Sinkhole[MAXIMUM_ENTRIES];
    private final static int MIN_HEALTH_POINT = 0;
    private final static int WIN_X = 950;
    private final static int WIN_Y = 670;

    public ShadowDimension() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        String fileName = "res/level0.csv";
        readCSV(fileName);
        /*initial the condition*/
        gameStart = false;
        gameLose = false;
        gameWin = false;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }

    /**
     * Method used to read file and create objects (You can change this
     * method as you wish).
     */
    private void readCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] parts = text.split(",");
                /*save the read numbers according to types*/
                switch (parts[0]) {
                    case "Player":
                        Fae = new Player(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), FAE_IMAGE);
                        break;
                    case "Wall":
                        WALLS[wallCount] = new Wall(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), WALL_IMAGE);
                        wallCount++;
                        break;
                    case "Sinkhole":
                        SINKHOLES[sinkHoleCount] = new Sinkhole(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), SINKHOLE_IMAGE);
                        sinkHoleCount++;
                        break;
                    case "TopLeft":
                        topLeft = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                        break;
                    case "BottomRight":
                        bottomRight = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
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
        /*draw messages on different conditions*/
        if (!gameStart) {
            FONT_BIG.drawString(GAME_TITLE, TITLE_MESSAGE_X, TITLE_MESSAGE_Y);
            FONT_SMALL.drawString(START_MESSAGE, START_MESSAGE_X, START_MESSAGE_Y);
            FONT_SMALL.drawString(PLAY_MESSAGE, PLAY_MESSAGE_X, PLAY_MESSAGE_Y);
            if (input.wasPressed(Keys.SPACE)) {
                gameStart = true;
            }
        }
        if (gameWin) {
            FONT_BIG.drawString(WIN_MESSAGE, WIN_MESSAGE_X, WIN_MESSAGE_Y);
        }
        if (gameLose) {
            FONT_BIG.drawString(LOSE_MESSAGE, LOSE_MESSAGE_X, LOSE_MESSAGE_Y);
        }
        if (gameStart && !gameWin && !gameLose) {
            /*draw background, walls, sinkholes and Fae on screen*/
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

            for (int i = 0; i < wallCount; i++) {
                WALLS[i].placeObject();
            }
            for (int i = 0; i < sinkHoleCount; i++) {
                SINKHOLES[i].placeObject();
            }
            Fae.placeObject(input,WALLS,SINKHOLES,topLeft,bottomRight);
            /* Win/Loss Condition Judgment*/
            if(Fae.getPosition_x() >= WIN_X && Fae.getPosition_y() >= WIN_Y) {
                gameWin = true;
            }if(Fae.getHealthPoint() <= MIN_HEALTH_POINT){
                gameLose = true;
            }
        }
    }

}
