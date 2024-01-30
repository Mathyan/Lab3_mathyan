import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Main {
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("Neispravan broj argumenata");
            System.out.println("java -jar Lab3.jar <ip> <port>");
            return;
        }
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        try {
            connect(ip, port);
        } catch (UnknownHostException e) {
            System.out.println("Nepoznata adresa");
        } catch (IOException e) {
            System.out.println("Neuspjelo spajanje");
        }
        while (true){
            try {
                System.out.print("Unesite poruku: ");
                String msg = scanner.nextLine();
                if (msg.equals("exit")) break;
                sendMessage(msg);
            } catch (Exception e) {
                System.out.println("Neuspjelo slanje poruke");
            }
        }
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Neuspjelo zatvaranje");
        }
    }
    //POVEZI SE NA SOCKET
    private static void connect(String ip, int port) throws IOException {
        Socket requestSocket = new Socket(ip, port);
        System.out.println("***Spojen na "+ip+":"+port);
        out = new ObjectOutputStream(requestSocket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(requestSocket.getInputStream());
    }
    //SALJI PORUKU U KANAL
    static void sendMessage(String msg){
        try{
            out.writeObject(msg);
            out.flush();
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
}