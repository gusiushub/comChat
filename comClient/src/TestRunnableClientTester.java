import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;



public class TestRunnableClientTester implements Runnable {

    static Socket socket;

    public TestRunnableClientTester() {
        try {
            // создаём сокет общения на стороне клиента в конструкторе объекта
            socket = new Socket("localhost", 3345);
            System.out.println("Клиент, подключенный к сокету");
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
            // создаём рабочий цикл
            int i = 0;
            while (i < 5) {
                /**
                 * пишем сообщение автогенерируемое циклом клиента в канал
                 * сокета для сервера
                 */
                Scanner str = new Scanner(System.in);
                System.out.println("Введите сообщение");
                String text = str.nextLine();
                //Thread.sleep(5000);
                oos.writeUTF("Сообщение клиента " + text);
                // проталкиваем сообщение из буфера сетевых сообщений в канал
                oos.flush();
                // ждём чтобы сервер успел прочесть сообщение из сокета и
                // ответить
                Thread.sleep(10);
                System.out.println("Client wrote & start waiting for data from server...");
                // забираем ответ из канала сервера в сокете
                // клиента и сохраняем её в ois переменную, печатаем на
                // консоль
                System.out.println("чтение...");
                String in = ois.readUTF();
                System.out.println(in);
                i++;
                Thread.sleep(5000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}