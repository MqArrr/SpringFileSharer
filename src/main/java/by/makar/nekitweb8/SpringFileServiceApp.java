package by.makar.nekitweb8;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class SpringFileServiceApp {


    private static final RestTemplate restTemplate = new RestTemplate();
    public static String IP;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringFileServiceApp.class, args);

        try {
            IP = String.valueOf(InetAddress.getLocalHost()).replaceAll("\\S*/", "");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean checkHealth() throws IOException {
        if((restTemplate.getForObject("http://localhost:8080/health", String.class).equals("\"OK\"")))
            return true;
        return false;
    }
}
