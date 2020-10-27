import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameState extends State {

    private boolean gameOver, userTurn, buttonsAdded, okayButtonAdded, happyDrawn;

    private final Hangman hangman;

    private Button okayButton;

    private String[] userInfo;

    private final ArrayList<BufferedImage> hangmanProgression = new ArrayList<>();
    private final ArrayList<Button> buttons = new ArrayList<>();

    /**
     * The constructor initializes the fields and passes the game object, and then gives the user instructions on what
     * to do.
     * @param game - Game object.
     * @param isEvil - Whether the user chose Evil or regular hangman.
     * @param wordLength - word length.
     */
    GameState(Game game, boolean isEvil, int wordLength) {
        super(game);
        this.gameOver = false;
        this.userTurn = true;
        this.hangman = new Hangman(wordLength, isEvil);
        this.happyDrawn = true;
        this.okayButtonAdded = false;
        updateUserInfo();
    }

    /**
     * Updated every frame which will update any information and check to see
     * what needs to be changed based on the user input.
     */
    public void tick(){
        if(gameOver){
            if(hangman.won){
                State.setState( new GameOverState(game, hangman));
            }else{
                State.setState( new GameOverState(game, hangman));
            }
        }

        if(MouseManager.mousePressed){
            if(userTurn){
                if(buttons.get(buttons.size()-1).checkButtonHovered(game)){
                    PlayMusic.play(Assets.clickButton);
                    happyDrawn = !happyDrawn;
                }

                for(int i = 0;i < buttons.size()-1;i++){
                    if(buttons.get(i).checkButtonHovered(game)&&buttons.get(i).getClickable()){
                        buttons.get(i).setClickable(false);
                        hangman.playUserTurn(hangman.letterKey.get(i));
                        userTurn = false;
                        updateUserInfo();

                        if(!hangman.giveLetter){
                            hangmanProgression.add(Assets.images[hangmanProgression.size()]);
                            PlayMusic.play(Assets.wrongAnswer);
                        }else{
                            PlayMusic.play(Assets.rightAnswer);
                        }
                    }
                }
            }else{
                if(buttons.get(buttons.size()-1).checkButtonHovered(game)){
                    userTurn = true;
                    PlayMusic.play(Assets.clickButton);
                    updateUserInfo();
                    gameOver = hangman.won;
                }
            }
        }
    }

    /**
     * Updated every frame which will update any drawings and check to see
     * what needs to be changed based on the user input.
     * @param g - Graphics2D
     */
    public void render(Graphics g){
        if(!userTurn && !okayButtonAdded){
            okayButtonAdded = true;
            buttons.add(okayButton);
        }
        if(userTurn && buttonsAdded && okayButtonAdded){
            okayButtonAdded = false;
            buttons.remove(buttons.size()-1);
        }
        if(!buttonsAdded){
            int buttonSize = 70;
            Color buttonText = new Color(230,230,230);
            Color buttonHighText = new Color(20,20,20);
            Color main = new Color(180,80,150,170);
            Color mainHigh = new Color(190,100,170,220);
            Color baseBorder = Color.BLACK;
            Color highBorder = new Color(160,160,160);
            int roundedness = 10;
            int fontSize = 40;
            for(int i = 0; i < 26;i++){
                buttons.add(new Button(game,g,""+hangman.letterKey.get(i),(buttonSize+15)*(i%9)+50,490+((i/9)*(buttonSize+15)),buttonSize,buttonSize,
                        buttonText,buttonHighText,main,mainHigh,baseBorder,highBorder,fontSize,roundedness,false, true));
            }
            buttons.add(new Button(game,g,"",730,660,buttonSize,buttonSize,
                    buttonText,buttonHighText,main,mainHigh,baseBorder,highBorder,fontSize,roundedness,false, true));
            okayButton = new Button(game,g,"Okay",0,370,130,80,buttonText,buttonHighText,main,mainHigh,baseBorder,highBorder,45,roundedness,true,true);
            this.buttonsAdded = true;
        }

        if(hangman.guessesUsed <= hangman.GUESSES){
            drawBackground(g);
            printUserInfo(g);
            drawHangman(g);
            drawButtons(g,game);
            Button.drawArrows(g);
        }

        if(hangman.guessesUsed == hangman.GUESSES && userTurn){
            if(hangman.wordList.size() == 1 && hangman.check()){
                hangman.won = true;
            }
            gameOver = true;
        }

    }

    /**
     * Draws the hangman depending on how far along he is currently.
     * @param g Graphics 2D
     */
    private void drawHangman(Graphics g){
        for (BufferedImage aHangmanProgression : hangmanProgression) {
            g.drawImage(aHangmanProgression, 211, 143, null);
        }
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
        if(happyDrawn){
            g.drawImage(Assets.sadHead,734,595,null);
        }else{
            g.drawImage(Assets.happyHead,734,595,null);
        }
        if(!userTurn){
            g.setColor(new Color(50,50,50,140));
        }
    }

    /**
     * Draws the background to be displayed in this state.
     * @param g - Graphics 2D
     */
    private void drawBackground(Graphics g){
        g.setColor(new Color(180,60,0,150));
        g.fillRoundRect(261,100,6,73,3,3);
        g.drawImage(Assets.gallowsImage,0,80,null);

        if(happyDrawn){
            for(int i = 0; i < 2;i++){
                for (int j = 0; j < 4; j ++){
                    g.drawImage(Assets.livingHangmanImage,(i*120)+830,j*175+10,100,165,null);
                }
            }
        }else{
            for(int i = 0; i < 2;i++){
                for (int j = 0; j < 4; j ++){
                    g.drawImage(Assets.sadHangmanImage,(i*120)+830,j*175+10,100,165,null);
                }
            }
        }

        g.setColor(new Color(20,80,150,90));
        g.fillRect(328,25,470,370);

        g.setColor(Color.BLACK);
        g.drawRect(328,25,470,370);
    }

    /**
     * Displays a message for the user depending on the state of the hangman game.
     * @param g
     */
    private void printUserInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font myFont;
        if(userTurn){
            Font my2Font = new Font("SansSerif",Font.BOLD,50);
            g.setFont(my2Font);
            String string = hangman.getWordToGuess();
            g.drawString(string,Display.centerString(string,g,my2Font,game)+13,330);
            myFont = new Font("SansSerif",Font.BOLD,25);
        }else{
            myFont = new Font("SansSerif",Font.BOLD,30);
        }
        g.setFont(myFont);
        for(int i = 0; i < userInfo.length;i++){
            g.drawString(userInfo[i],Display.centerString(userInfo[i],g,myFont,game)+13,i*(g.getFontMetrics(myFont).getHeight()+15)+90);
        }
    }

    /**
     * Updates the message for the user depending on the state of the hangman game.
     */
    private void updateUserInfo(){
        if(userTurn){
            userInfo = hangman.returnUserInfo().split("=");
        }else{
            userInfo = hangman.returnCPUTurn().split("=");
        }
    }
}