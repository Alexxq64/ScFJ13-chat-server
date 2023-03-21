public class MyServer extends Thread{
    @Override
    public void run() {
        ServerSocket server = new ServerSocket(1234);
        while (true) {
            System.out.println("Waiting...");
            // ждем клиента из сети
            Socket socket = server.accept();
            System.out.println("Client connected!");

            // создаем клиента на своей стороне
            Client client = new Client(socket);
            serverData.clients.add(client);
            if (serverData.clients.size() == 1) sendToClient(serverData.clients.getFirst(), "You're only client in the chat.");
            for (Client c: serverData.clients) {
                System.out.println(c.socket);
            }
            // запускаем поток
            Thread thread = new Thread(client);
            thread.start();
        }

    }
}
