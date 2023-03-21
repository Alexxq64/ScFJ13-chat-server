import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    ChatServer cs;
    Scanner in;
    PrintStream out;
    String name;

    public Client(Socket socket, ChatServer cs) {
        this.socket = socket;
        this.cs = cs;
        // запускаем поток
        new Thread(this).start();
    }

    void receive(String message) {
        out.println(">> " +message);
    }


    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!");
            out.println("What's your name?");
            name = in.nextLine();
            out.println("Hi, " + name);
            String input = in.nextLine();
            while (!input.equals("bye")) {
                cs.sendAll(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}