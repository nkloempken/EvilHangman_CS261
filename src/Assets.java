import java.awt.image.BufferedImage;
import java.io.File;

/**
 * The Assets class stores all of the images that will be drawn to the screen as well as the wordList that is used by
 * the Hangman game to come up with a word for the user to try to guess.
 */
abstract class Assets {

    static final BufferedImage backgroundImage, deadHangmanImage, livingHangmanImage, gallowsImage, sadHangmanImage, happyHead, sadHead;

    // This array holds the 10 stages of hangman as he is being constructed on the gallows for each incorrect guess.
    public static final BufferedImage[] images;

    // The word list.
    public static final File wordsFile, backgroundMusic, startGame, winGame, loseGame, rightAnswer, wrongAnswer, clickButton;

    static{
        backgroundImage = ImageLoader.loadImage("res/linedPaper.jpg").getSubimage(50,50,400,450);
        deadHangmanImage = ImageLoader.loadImage("res/hangman.png");
        livingHangmanImage = ImageLoader.loadImage("res/livingHangman.png");
        gallowsImage = ImageLoader.loadImage("res/gallows.png");
        sadHangmanImage = ImageLoader.loadImage("res/sadHangman.png");
        sadHead = ImageLoader.loadImage("res/sadHangmanHead.png");
        happyHead = ImageLoader.loadImage("res/happyHangmanHead.png");

        BufferedImage stage1, stage2, stage3, stage4, stage5, stage6, stage7, stage8, stage9;
        stage1 = ImageLoader.loadImage("res/stage1.png");
        stage2 = ImageLoader.loadImage("res/stage2.png");
        stage3 = ImageLoader.loadImage("res/stage3.png");
        stage4 = ImageLoader.loadImage("res/stage4.png");
        stage5 = ImageLoader.loadImage("res/stage5.png");
        stage6 = ImageLoader.loadImage("res/stage6.png");
        stage7 = ImageLoader.loadImage("res/stage7.png");
        stage8 = ImageLoader.loadImage("res/stage8.png");
        stage9 = ImageLoader.loadImage("res/stage9.png");
        images = new BufferedImage[]{stage1,stage2,stage3,stage4,stage5,stage6,stage7,stage8,stage9,deadHangmanImage};

        wordsFile = new File("src/res/wordList.txt");
        backgroundMusic = new File("src/res/backgroundMusic.wav");
        startGame = new File("src/res/ding1.wav");
        winGame = new File("src/res/cheering.wav");
        loseGame = new File("src/res/lose.wav");
        rightAnswer = new File("src/res/ding2.wav");
        wrongAnswer = new File("src/res/wrong.wav");
        clickButton = new File("src/res/buttonClick.wav");
    }

}
