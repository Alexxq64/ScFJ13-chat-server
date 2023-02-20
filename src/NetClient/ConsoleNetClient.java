package NetClient;


import java.io.*;
import java.net.Socket;

public class ConsoleNetClient extends Thread {
    final String serverIP = "127.0.0.1";
    final int serverPort = 1234;
    InputStreamReader in;
    //PrintWriter out;
    OutputStreamWriter out;
    void connect() {
        try {
            Socket socket = new Socket(serverIP, serverPort);
            in = new InputStreamReader(socket.getInputStream());
            out = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Server " + serverIP + " port " + serverPort + " " + "" + "NOT AVAILABLE");
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        this.connect();
        BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader serverReader = new BufferedReader(in);
        BufferedWriter serverWriter = new BufferedWriter(out);
        while (true) {
            try {
                System.out.println(serverReader.readLine());
                String str = keyboardReader.readLine();
                //System.out.println("Input check: [" + str + "]");
                serverWriter.write(str);
          serverWriter.append("\n");
          serverWriter.flush();
            } catch (IOException e) {
                System.err.println("\nCONNECTION ERROR");
                e.printStackTrace();
                return;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        ConsoleNetClient cc = new ConsoleNetClient();
        cc.start();
    }
}



