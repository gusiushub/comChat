import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;



public class TestRunnableClientTester implements Runnable {

    static Socket socket;


    public  TestRunnableClientTester() {
        try {
            // создаём сокет общения на стороне клиента в конструкторе объекта
            socket = new Socket("localhost", 3345);
            // System.out.println("Введите имя:");
            //String name = str.nextLine();
            System.out.println("клиент, подключен к сокету");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (// создаём объект для записи строк в созданный скокет, для
             // чтения строк из сокета
             // в try-with-resources стиле
             DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream())) {
            System.out.println("Клиент oos & ois инициализирован ");

            /**
             * пишем сообщение автогенерируемое циклом клиента в канал
             * сокета для сервера
             */

            try(FileReader reader = new FileReader("textMsg.txt"))
            {
                char[] buf = new char[256];
                int c;
                while((c = reader.read(buf))>0){

                    if(c < 256){
                        buf = Arrays.copyOf(buf, c);
                    }
                    System.out.print(buf);
                    String msg = String.valueOf(buf);
                    oos.writeUTF("Сообщение клиента " + msg );
                    // проталкиваем сообщение из буфера сетевых сообщений в канал
                    oos.flush();
                }
            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }

            // ждём чтобы сервер успел прочесть сообщение из сокета и
            // ответить
            Thread.sleep(10);
            System.out.println("Клиент написал & начать ждать данных с сервера...");
            // забираем ответ из канала сервера в сокете
            // клиента и сохраняем её в ois переменную, печатаем на
            // консоль
            System.out.println("чтение...");
            String in = ois.readUTF();
            System.out.println(in);
            Thread.sleep(2000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}