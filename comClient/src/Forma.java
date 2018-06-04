import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Forma extends JFrame {

    private JTextArea t1;
    private JButton b1;
    private JLabel l1;
    private JLabel pane;
    private JTextArea f;

    public Forma(String s){
        super(s);
        setLayout(new FlowLayout());
        b1 = new JButton("Send");
        f = new JTextArea();
        l1 = new JLabel("Введите сообщение");
        t1 = new JTextArea();
        pane = new JLabel(t1.getText());
        b1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                /**
                 * Проверяем наличие сообщения и записываем его в файл
                 * */
               //String o = f.setText(t1.getText());
                //System.out.print();
                if(t1.getText().trim().length()!=0){
                    try(FileWriter writer = new FileWriter("textMsg.txt", false)) {
                        String text = t1.getText();
                        writer.write(text);
                        writer.flush();
pane.setText(text);
pane.setText(text);

                    }
                    catch(IOException ex){

                        System.out.println(ex.getMessage());
                    }
                    ExecutorService exec = Executors.newFixedThreadPool(10);
                    exec.execute(new TestRunnableClientTester());
                    exec.shutdown();
                }
            }
        });

        f.setBounds(10,30,120,21);
        f.setSize(220,170);
        f.setBackground(Color.yellow);


        add(b1);
        add(l1);
        add(t1);

        add(pane);



    }
}
