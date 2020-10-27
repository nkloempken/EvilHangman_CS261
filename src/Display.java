import javax.swing.*;
import java.awt.*;

/**
 * The Display class is used to create the actual window that the game is displayed in.  It uses a Jframe to make the
 * window, and then fills it with a Canvas.The Canvas can have images drawn on it using a graphics object from the
 * given java class Graphics.  This drawing occurs in the render() methods within the Game, and State
 * classes.
 */
class Display
{
    private JFrame frame; // The window the game is in.
    private Canvas canvas; // How images are drawn inside the JFrame.

    private final String title;
    private final int width;
    private final int height;

    /**
     * The default constructor.  Creates a new window for the new game to be stored in.  Titled "Blackjack," The size
     * can be set to anything but it is always 1000 by 500 for this game.
     * @param title - Title of the window of the new display.
     * @param width - Width of the window of the new display.
     * @param height - Height of the window of the new display.
     */
    public Display(String title, int width, int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }

    /**
     *This method is the one that actually creates the frame and builds it at the proper width, height, and
     * sets it as visible.  Also adds the canvas to the frame.
     */
    private void createDisplay()
    {
        frame = new JFrame(title);
        frame.setSize(width,height);

        // Makes it so the red X in the top right actually closes the game.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Specifying window settings
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Makes a new canvas object to fit in the frame.
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height));
        canvas.setMinimumSize(new Dimension(width,height));
        canvas.setMaximumSize(new Dimension(width,height));

        frame.add(canvas); // Adds the canvas to the frame.
        frame.pack(); // Makes sure everything is organized properly.
    }

    /**
     * Returns the current canvas.  Used to draw to the canvas from other Classes.
     * @return - canvas object being used by the JFrame.
     */
    public Canvas getCanvas()
    {
        return canvas; // Returns the canvas.
    }

    /**
     * Returns the X coordinate of the frame's location on the screen.  This number will be used when calculating the
     * mouse location to make sure the CPU knows where it is relative to the window.
     * @return - X coordinate of the frame.
     */
    public int getFrameX()
    {
        return (int)frame.getBounds().getLocation().getX(); // ...
    }
    /**
     * Returns the Y coordinate of the frame's location on the screen.  This number will be used when calculating the
     * mouse location to make sure the CPU knows where it is relative to the window.
     * @return - Y coordinate of the frame.
     */
    public int getFrameY()
    {
        return (int)frame.getBounds().getLocation().getY()+27; // Includes a +27 to account for the 27 pixel
        //tall menu bar in the top of the frame.
    }

    /**
     * This method returns an integer x position to place a string so that it appears in the center of the screen when
     * drawn.
     * @param string - The String in question.
     * @param g - Graphics2D object.
     * @param f - The font being used when drawing.
     * @param game - Game.
     * @return - int x pos of String in order to be centered.
     */
    public static int centerString(String string, Graphics g, Font f, Game game){
        return (game.width/2)-(g.getFontMetrics(f).stringWidth(string))/2;
    }
}