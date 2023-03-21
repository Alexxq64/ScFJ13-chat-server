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
        out.println(">> " + message);
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
            cs.clients.set(cs.clients.size() - 1, this).name = name;
            out.println("Hi, " + name);
            String input;
//                out.println("(0) exit  (1) send a message to all  (2) send message to");
            int choise;
            while (true) {
                out.println("(0) exit  (1) send a message to all  (2) send message to");
                choise = in.nextInt();
                in.nextLine(); // !!!!!!!!!!!!!
                switch (choise) {
                    case 0:
                        socket.close();
                        break;
                    case 1:
                        out.println("enter message: ");
                        input = in.nextLine();
                        cs.sendAll(input);
                        break;
                    case 2:
                        out.println("There are in the chat: ");
                        for (Client c: cs.clients){
                            out.print(c.name + "  ");
                        }
                        out.println();
                        out.println("enter number: ");
                        int receiver;
                        receiver = in.nextInt();
                        in.nextLine();
                        if (receiver >=0 && receiver <= cs.clients.size()) {
                            out.println("enter message: ");
                            input = in.nextLine();
                            cs.clients.get(receiver).out.println("to " + cs.clients.get(receiver).name + " from " + this.name + ">>" + input);
                            out.println("to " + cs.clients.get(receiver).name + " from " + this.name + ">>" + input);
                        }
                }
                choise = in.nextInt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}