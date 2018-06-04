
import javax.swing.JFrame;



public class Client  {

    public static void main(String[] args) {
        Forma forma = new Forma("ЧАТ");
        //JFrame.setDefaultLookAndFeelDecorated(true);
        forma.setVisible(true);
        forma.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        forma.setSize(400,400);
    }

}
