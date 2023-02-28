import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    private static final int portNumber = 6969;

    public static void main(String[] args) {
        try(
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter outgoingConnection = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader incomingConnection = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ){
            System.out.println("Client connected on port " + portNumber + ". Servicing requests.");
            outgoingConnection.println("Connection established"); //welcome message

            String inputLine;
            while((inputLine = incomingConnection.readLine()) != null){
                System.out.println("Received message: " + inputLine + " from " + clientSocket.toString());
                outgoingConnection.println(inputLine.toUpperCase());
            }

        } catch (IOException e) {
            System.err.println("Exception caught while listening on port " + portNumber
                    + " or while listening for a connection. Possibly because portNumber < 1024");
            System.exit(1);
        }
    }

}
