import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class clientCode {

    private static final String hostName = "vertex02";
    private static final int portNumber = 6969;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter outgoingConnection = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader incomingConnection = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ){
            //welcome message from server
            System.out.println(incomingConnection.readLine());

            String userInput;
            while ((userInput = stdIn.readLine()) != null){
                outgoingConnection.println(userInput);
                System.out.println("response: " + incomingConnection.readLine());
            }

        } catch (UnknownHostException e){
            System.err.println("Could not find host: " + hostName);
            System.exit(100);
        } catch (IOException e){
            System.err.println("Error getting I/O for the connection to " + hostName);
            System.exit(200);
        }
    }
}
