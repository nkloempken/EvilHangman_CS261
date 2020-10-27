import java.awt.*;
import java.util.ArrayList;

/**
 * The GameOverState is the state which occurs after the user has either lost or won the game of hangman.  In this state
 * the user is given a congratulations or a too bad, and is given the option to play again.
 */
public class GameOverState extends State {

    private static final ArrayList<Button> buttons = new ArrayList<>();

    private boolean buttonsAdded;

    private double timer; // Used for animation.

    private String correctWordDisplay;

    private final Hangman hangman;

    private final String correctWord;

    /**
     * The constructor passes the game object and initializes all fields.  Also plays the correct music depending on a
     * win or loss from the user.
     * @param game - Game object.
     * @param hangman - Hangman game that just ended.
     */
    public GameOverState(Game game, Hangman hangman)
    {
        super(game);
        buttonsAdded = false;
        this.hangman = hangman;
        this.correctWord = hangman.getCorrectWord();
        makeDisplayWord();

        // Play the music that corresponds with game results.
        if(hangman.won){
            PlayMusic.play(Assets.winGame);
        }else{
            PlayMusic.play(Assets.loseGame);
        }
    }

    /**
     * Updated every frame which will update any information and check to see
     * what needs to be changed based on the user input.
     */
    public void tick(){
        if(MouseManager.mousePressed){
            if(buttons.get(0).checkButtonHovered(game)){
                PlayMusic.play(Assets.clickButton);
                State.setState(new StartScreenState(game));
            }
        }
    }

    /**
     * Updated every frame which will update any drawings and check to see
     * what needs to be changed based on the user input.
     * @param g - Graphics2D
     */
    public void render(Graphics g){
        if(!buttonsAdded){
            addButtons(g);
        }
        drawBackground(g);
        drawButtons(g,game);
        Button.drawArrows(g);
    }

    /**
     * Adds any needed buttons to the list of buttons in the state.
     * @param g - Graphics 2D
     */
    private void addButtons(Graphics g){
        Color buttonText = new Color(200,200,200);
        Color buttonHighText = new Color(20,20,20);
        Color main = new Color(180,80,150,170);
        Color mainHigh = new Color(190,100,170,220);
        Color baseBorder = Color.BLACK;
        Color highBorder = new Color(160,160,160);
        int roundedness = 10;
        buttons.add(new Button(game,g,"Play Again",400,320,300,80,buttonText, buttonHighText,
                main,mainHigh,baseBorder,highBorder,50,roundedness,false,true));
        this.buttonsAdded = true;
    }

    /**
     * Draws all buttons as/if they need to be drawn.
     * @param g - Graphics 2D
     * @param game - Game object
     */
    private void drawButtons(Graphics g, Game game){
        for (Button button : buttons) {
            button.drawButton(g,game);
        }
    }

    /**
     * Draws the background to be displayed in this state.
     * @param g - Graphics 2D
     */
    private void drawBackground(Graphics g){
        if(hangman.won){
            // Draws the up and down .

            g.drawImage(Assets.livingHangmanImage,100,150 + (int)(Math.sin(timer)*50),null);
            timer+= 0.12;

            // Draws the sad hangmen off to the right side (The spectators).

            for(int i = 0; i < 2;i++){
                for (int j = 0; j < 4; j ++){
                    g.drawImage(Assets.sadHangmanImage,(i*120)+830,j*175+10,100,165,null);
                }
            }
        }else{

            // Draws the full hung man on the top left of the screen.

            g.setColor(new Color(180,60,0,150));
            g.fillRoundRect(261,100,6,83,3,3);
            g.drawImage(Assets.gallowsImage,0,80,null);
            g.drawImage(Assets.deadHangmanImage,210,140,100,200,null);

            // Draws the happy hangmen off to the right side (The spectators).
            for(int i = 0; i < 2;i++){
                for (int j = 0; j < 4; j ++){
                    g.drawImage(Assets.livingHangmanImage,(i*120)+830,j*175+10,100,165,
                            null);
                }
            }
        }

        // Blue box in the background

        g.setColor(new Color(20,80,150,90));
        g.fillRect(345,35,410,400);

        g.setColor(Color.BLACK);
        g.drawRect(345,35,410,400);

        // Blue box text

        g.setColor(Color.BLACK);
        Font myFont = new Font("SansSerif",Font.BOLD,36);
        g.setFont(myFont);

        if(hangman.won){
            String displayedString = "Congratulations!";
            g.drawString(displayedString,Display.centerString(displayedString, g, myFont, game),80);
            displayedString = "You won!!!";
            g.drawString(displayedString,Display.centerString(displayedString, g, myFont, game),140);
            displayedString = "----------------------";
            g.drawString(displayedString,Display.centerString(displayedString, g, myFont, game),200);

        }else{
            String displayedString = "Oh No!";
            g.drawString(displayedString,Display.centerString(displayedString, g, myFont, game),80);
            displayedString = "You lost!!!";
            g.drawString(displayedString,Display.centerString(displayedString, g, myFont, game),140);
            displayedString = "---------------------";
            g.drawString(displayedString,Display.centerString(displayedString, g, myFont, game),200);
        }

        g.drawString("The correct word was:",Display.centerString("The correct word was:",g,myFont,game),
                400);

        myFont = new Font("SansSerif",Font.BOLD,70);
        g.setFont(myFont);

        // Draws the word as guessed by the user.

        g.drawString(hangman.getWordToGuess(),400-(35*correctWord.length()),540);
        g.drawString(correctWordDisplay,400-(35*correctWord.length()),630);
    }

    /**
     * Builds the formatting for the correct word which will be displayed for the user.
     */
    private void makeDisplayWord(){
        StringBuilder s = new StringBuilder("[ " + correctWord.charAt(0));
        for(int j = 1; j < correctWord.length();j++){
            s.append(" ").append(correctWord.charAt(j));
        }
        s.append(" ]");
        this.correctWordDisplay = s.toString();
    }
}