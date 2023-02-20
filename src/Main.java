import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        // ������� ��������� ����� �� ����� 1234
        ServerData serverData = new ServerData();
        ServerSocket server = new ServerSocket(1234);
        while (true) {
            System.out.println("Waiting...");
            // ���� ������� �� ����
            Socket socket = server.accept();
            System.out.println("Client connected!");

            // ������� ������� �� ����� �������
            Client client = new Client(socket);
            ServerData.clients.add(client);
            for (Client c: ServerData.clients) {
                System.out.println(c.socket);
            }
            // ��������� �����
            Thread thread = new Thread(client);
            thread.start();
        }
    }
}
