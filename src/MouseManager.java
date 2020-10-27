import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 * The MouseManager class uses the MouseListener class given by Java to notify when the mouse is clicked.
 * This notification is converted into a boolean variable which is static so that it can be accessed from anywhere.
 * This is used to create buttons for user inputs.
 */
class MouseManager implements MouseListener {

    public static boolean mousePressed; // Is true for one cycle after the mouse was released and false otherwise.

    /**
     * The default constructor just instantiates mousePressed to false.
     */
    public MouseManager(){
        mousePressed = false;
    }

    /**
     * Necessary method which changes the class variable to true whenever the mouse is clicked.
     * @param e - Default variable for a MouseEvent
     */
    public void mouseReleased(MouseEvent e){
        mousePressed = true;
    }

    //<editor-fold desc="Unused methods required by MouseListener interface">
    public void mouseClicked(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){}
    //</editor-fold>
}
