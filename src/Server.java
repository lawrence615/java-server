import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    public static void main(String[] args) throws IOException {

        QuoteService quoteService = new QuoteService();

        // request for a server port
        ServerSocket socketServer = new ServerSocket(9999);

        // start an infinite loop
        while (true) {
            System.out.println("Waiting for clients..");
            Socket socket = socketServer.accept();

            InputStream in = socket.getInputStream(); // sent by the client
            OutputStream out = socket.getOutputStream(); // sent to the client

            System.out.println("Waiting for product information from the client");
            byte request[] = new byte[100];
            in.read(request);
            String product = new String(request).trim();

            System.out.println("Received product name - " + product);

            String price = quoteService.getQuote(product);
            if (price == null) {
                price = "Invalid product";
            }

            out.write(price.getBytes());

            System.out.println("Response sent...");

            socket.close();
        }


//        System.out.println("Received from client - " + new String(buffer).trim());
//
//        out.write("Hello from server:".getBytes());
//
//
//        socketServer.close();
    }
}


class QuoteService {
    private Map<String, String> productInfo = new HashMap<>();

    public QuoteService() {

        productInfo.put("a", "100");
        productInfo.put("b", "233");
        productInfo.put("c", "201");
        productInfo.put("d", "2309");
    }

    public String getQuote(String product) {
        return productInfo.get(product);
    }
}
