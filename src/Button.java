import java.awt.*;

/**
 * The Button class is used to create buttons which are able to be interacted with by the user.  Highlight when hovered,
 * and an action taken while clicked (Although the clicking check is expected to come from outside this class).
 */
class Button{

    // Class variables
    private static Button selectedButton = null; // Button last hovered by the mouse.
    private static double timer = 0; // Used for animations.

    // Class method

    /**
     * This method draws two oscillating arrows next to a button if it is currently being hovered and is clickable.
     * @param g - Game
     */
    public static void drawArrows(Graphics g){
        //Draws the arrows on both sides of the button.
        if(selectedButton != null && selectedButton.getClickable()){
            g.setColor(Color.BLACK);
            g.fillRoundRect(selectedButton.x-45+(int)(Math.sin(timer)*10),selectedButton.y-
                    (selectedButton.height/2)-2,24,6,2,2);
            g.fillPolygon(new int[]{selectedButton.x+(int)(Math.sin(timer)*10)-25,selectedButton.x+
                            (int)(Math.sin(timer)*10)-25,selectedButton.x+(int)(Math.sin(timer)*10)-15},
                    new int[]{selectedButton.y-(selectedButton.height/2)+30,selectedButton.y-(selectedButton.height/2)-30,-selectedButton.height/2+
                            (selectedButton.y)},3);
            g.fillRoundRect(selectedButton.x+selectedButton.width+20-(int)(Math.sin(timer)*10),selectedButton.y-
                    (selectedButton.height/2)-2,24,6,2,2);
            g.fillPolygon(new int[]{selectedButton.x+selectedButton.width-(int)(Math.sin(timer)*10)+22,
                            selectedButton.x+selectedButton.width-(int)(Math.sin(timer)*10)+22,
                            selectedButton.x+selectedButton.width-(int)(Math.sin(timer)*10)+12},
                    new int[]{selectedButton.y-(selectedButton.height/2)+30,selectedButton.y-(selectedButton.height/2)-30,-selectedButton.height/2+
                            (selectedButton.y)},3);
            timer += 0.2; // Larger number means faster oscillation
            Button.selectedButton = null;
        }
    }


    // Fields
    private final int y, roundedness;
    private int x, width, height, stringLocX, stringLocY;

    private boolean clickable;

    private final String label; // Name of the button

    private final Color textBaseColor;
    private final Color textHighlightedColor;
    private final Color mainColor;
    private final Color baseBorderColor;
    private final Color highlightedMainColor;
    private final Color highlightedBorderColor;

    private Font textFont;

    /**
     * The full constructor takes a lot of parameters to allow for a variety of specific types/looks of Button objects.
     * @param game - Game
     * @param g - Graphics 2D
     * @param label - Name of the button
     * @param x - x location
     * @param y - y location
     * @param width - width
     * @param height - height
     * @param textBaseColor - Unhovered text color - usually black
     * @param textHighlightedColor - Hovered text color - usually white
     * @param mainColor - main, unhovered rectangle color
     * @param highlightedMainColor - main, hovered color
     * @param baseBorderColor - unhovered border color
     * @param highlightedBorderColor - hovered border color
     * @param fontSize - font size of text
     * @param rectRoundedness - roundedness of the button
     * @param centered - whether or not it should be centered horizontally(Overrides x value input).
     * @param clickable - Whether or not the button is currently clickable
     */
    public Button(Game game,Graphics g, String label, int x, int y, int width, int height, Color textBaseColor,
                  Color textHighlightedColor, Color mainColor,Color highlightedMainColor, Color baseBorderColor,
                  Color highlightedBorderColor, int fontSize, int rectRoundedness, boolean centered, boolean clickable){

        // Initialize all fields.
        this.label = label;
        this.changeFont(g,new Font("SansSerif",Font.BOLD,fontSize));

        this.x = x;
        if(centered){
            this.x = (game.width/2)-(this.width/2);
        }
        this.y = y;

        this.roundedness = rectRoundedness;
        this.width = width;
        this.height = height;

        // Makes sure that the given font and text fit inside of the given width and height of the button.
        updateTextSize(g,width,height);

        // Set all colors.
        this.textBaseColor = textBaseColor;
        this.textHighlightedColor = textHighlightedColor;
        this.mainColor = mainColor;
        this.baseBorderColor = baseBorderColor;
        this.highlightedMainColor = highlightedMainColor;
        this.highlightedBorderColor = highlightedBorderColor;
        this.clickable = clickable;
    }

    /**
     * Secondary constructor, used when a text only button.
     * @param game - Game
     * @param g - Graphics 2D
     * @param label - Name of the button
     * @param x - x location
     * @param y - y location
     * @param textBaseColor - Unhovered text color - usually black
     * @param textHighlightedColor - Hovered text color - usually white
     * @param fontSize - font size of text
     * @param centered - whether or not it should be centered horizontally(Overrides x value input).
     * @param clickable - Whether or not the button is currently clickable
     */
    public Button(Game game, Graphics g, String label, int x, int y, Color textBaseColor, Color textHighlightedColor,
                  int fontSize, boolean centered, boolean clickable){
        this(game,g,label,x,y,0,0,textBaseColor,textHighlightedColor,null,null,
                null,null, fontSize,0,centered, clickable);
    }

    /**
     * This method draws the button to the screen given a graphics object.  Calls either the hovered or unhovered draw
     * methods depending on which currently needs to be done.
     * @param g - Graphics 2D
     * @param game - Game
     */
    public void drawButton(Graphics g,Game game){
        if(clickable){
            if(!checkButtonHovered(game)){
                drawUnhoveredButton(g);
            }else{
                drawHoveredButton(g);
            }
        }else{
            drawUnhoveredButton(g);
        }
    }

    /**
     * Used by the State classes in order to set whether or not a button is clickable.
     * @param clickable - True if clickable, false if not clickable.
     */
    public void setClickable(boolean clickable){
        this.clickable = clickable;
    }

    /**
     * This method checks to see if the button is currently being hovered given a Game object which has access to
     * the x and y coordinates of the mouse and window.
     * @param game - Game
     * @return - boolean: True if hovered, false if not hovered.
     */
    public boolean checkButtonHovered(Game game){
        int mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX() - game.getDisplay().getFrameX()-5;
        int mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY() - game.getDisplay().getFrameY();
        return !(mouseX < x||mouseX > (x+width)||mouseY < this.y-height|| mouseY > this.y);
    }

    /**
     * Used by State classes to check if a button is clickable or not.
     * @return - boolean: Ture if clickable, false if not.
     */
    public boolean getClickable(){
        return clickable;
    }

    /**
     * Updates the font of a given button.  Automatically makes sure that the button size still works with the new font,
     * and if not, expands the button size.
     * @param g - Graphics 2D
     * @param desiredFont - Desired font.
     */
    private void changeFont(Graphics g, Font desiredFont){
        this.textFont = desiredFont;
        updateTextSize(g, this.width, this.height);
    }

    /**
     * This method draws the button in its hovered state.
     * @param g - Graphics 2D
     */
    private void drawHoveredButton(Graphics g){
        selectedButton = this;
        g.setFont(textFont);

        if(mainColor != null){
            g.setColor(highlightedBorderColor);
            g.fillRoundRect(x,y-height,width,height,roundedness,roundedness);
            g.setColor(highlightedMainColor);
            g.fillRoundRect(x+3,y+3-height,width-6,height-6,roundedness,roundedness);
        }

        g.setColor(textHighlightedColor);
        g.drawString(label,stringLocX,stringLocY);
    }

    /**
     * This method draws the button in its unhovered state.
     * @param g - Graphics 2D
     */
    private void drawUnhoveredButton(Graphics g){
        g.setFont(textFont);

        if(mainColor != null){
            g.setColor(baseBorderColor);
            g.fillRoundRect(x,y-height,width,height,roundedness,roundedness);
            g.setColor(mainColor);
            g.fillRoundRect(x+3,y+3-height,width-6,height-6,roundedness,roundedness);
        }

        g.setColor(textBaseColor);
        g.drawString(label,stringLocX,stringLocY);

        // Makes the button look faded / un-clickable.
        if(!clickable){
            g.setColor(new Color(120,120,120,180));
            g.fillRoundRect(x,y-height,width,height,roundedness,roundedness);
        }
    }

    /**
     * Updates the width and height of the button in order to ensure that the text does not go outside of the box.
     * @param g - Graphics 2D
     * @param width - width of button
     * @param height - height of button
     */
    private void updateTextSize(Graphics g, int width, int height){
        int textSizeX = calcTextWidth(g);
        int textSizeY = calcTextHeight(g);
        if(textSizeX > width){
            this.width = textSizeX;
        }
        if(textSizeY > height){
            this.height = textSizeY;
        }
        stringLocX = x+(this.width/2)-(g.getFontMetrics(textFont).stringWidth(label)/2);
        stringLocY = (y+g.getFontMetrics(textFont).getHeight()/4)-(this.height/2);
    }

    /**
     * Used to calculate how wide text will be given the button text and a font.
     * @param g - Graphics 2D
     * @return - The width of the text on screen.
     */
    private int calcTextWidth(Graphics g){
        return g.getFontMetrics(textFont).stringWidth(label);
    }

    /**
     * Used to calculate how tall text will be given the button text and a font.
     * @param g - Graphics 2D
     * @return - The height of the text on screen.
     */
    private int calcTextHeight(Graphics g){
        return g.getFontMetrics(textFont).getHeight();
    }
}
