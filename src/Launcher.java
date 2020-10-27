/**
 * The Launcher class holds the main method which is what makes a new Hangman Game object and begins the loop. Sets the width,
 * height and title of the game (Although currently, the window is not resizable.)
 *
 * The music files I used in this project came from Orange Free Sounds - http://www.orangefreesounds.com/category/music/
 *
 * The images were drawn on Microsoft Paint 3D (Yes I know they are 2D but 3D has more editing tools).
 *
 * @author Nathan Kloempken
 * @version 04/11/2019
 */
class Launcher {

    private static final int WINDOW_WIDTH = 1100; // Do not change!  Resizable screen not implemented.
    private static final int WINDOW_HEIGHT = 700; // Do not change!  Resizable screen not implemented.

    /**
     * The main method just makes and starts a new Game.
     * @param args - main
     */
    public static void main(String[] args){
        Game game = new Game("Hangman",WINDOW_WIDTH,WINDOW_HEIGHT);
        game.start();
    }

}
