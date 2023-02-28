import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConcurrentServer {

    private static final int portNumber = 6969;
    private static final int threadPoolSize = 5;

    public static void main(String[] args) {
        try(
            ServerSocket serverSocket = new ServerSocket(portNumber);
        ){
            Executor executor = Executors.newFixedThreadPool(threadPoolSize);
            System.out.println("Server established with " + threadPoolSize + " threads, waiting for clients");

            while (true){
                Socket clientSocket = serverSocket.accept();
                Runnable worker = new RequestHandler(clientSocket);
                executor.execute(worker);
            }

        } catch (IOException e) {
            System.err.println("Exception caught while listening on port " + portNumber
                    + " or while listening for a connection. Possibly because portNumber < 1024");
            System.exit(1);
        }
    }

}
