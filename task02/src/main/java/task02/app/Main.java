package task02.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Main {
    public static String name = "Brendon Teo Wei Jie";
    public static String email = "bren0019@e.ntu.edu.sg";
    public static void main(String[] args) {
        try {
            System.out.println("Attempting to connect. . .");
            Socket sock = new Socket("68.183.239.26", 80);
            System.out.println("Connected");

            OutputStream os = sock.getOutputStream();
            InputStream is = sock.getInputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            ObjectInputStream ois = new ObjectInputStream(is);

            String serverRequest = ois.readUTF();
            System.out.println(serverRequest);

            String[] splitReq = serverRequest.split(" ");
            String reqID = splitReq[0];
            String[] numbers = splitReq[1].split(",");
            int total = 0;

            for(int i=0; i<numbers.length; i++) {
                total += Integer.parseInt(numbers[i]);
            }
            
            float average = total/((float)numbers.length);

            System.out.println(reqID);
            System.out.println(name);
            System.out.println(email);
            System.out.println(average);

            oos.writeUTF(reqID);
            oos.flush();
            oos.writeUTF(name);
            oos.flush();
            oos.writeUTF(email);
            oos.flush();
            oos.writeFloat(average);
            oos.flush();

            boolean succeed = ois.readBoolean();

            if(succeed) {
                System.out.println("SUCCESS");
                sock.close();
            } else {
                String errorMessage = ois.readUTF();
                System.out.println("FAILED");
                System.out.println(errorMessage);
                sock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
