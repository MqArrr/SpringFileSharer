package by.makar.nekitweb8;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

@SpringBootApplication
public class NekitWeb8Application {


    private static final RestTemplate restTemplate = new RestTemplate();
    public static String IP;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(NekitWeb8Application.class, args);
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
