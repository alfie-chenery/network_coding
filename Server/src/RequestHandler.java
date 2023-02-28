import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable{

    private final Socket clientSocket;

    RequestHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try(
            BufferedReader incomingConnection = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter outgoingConnection = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        ){
            System.out.println("Thread started with name: " + Thread.currentThread().getName());
            outgoingConnection.write("Connection successful");
            outgoingConnection.newLine();
            outgoingConnection.flush();

            String userInput;
            while((userInput = incomingConnection.readLine()) != null){
                userInput = userInput.replaceAll("[^A-Za-z0-9]",""); //sanitise input - remove non-alphanumeric chars
                System.out.println(Thread.currentThread().getName() + " received message: " + userInput);

                outgoingConnection.write(userInput.toUpperCase());
                outgoingConnection.newLine();
                outgoingConnection.flush();
            }
        } catch (IOException e) {
            System.err.println("Exception caught in thread " + Thread.currentThread().getName());
        }
    }
}
