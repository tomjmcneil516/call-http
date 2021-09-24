package com.example.callhttp;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Error: No Command Entered");
            return;
        }

        String cmd = args[0];

        if (cmd.equals("--daemon")) {
            SpringApplication.run(Application.class, args);
            return;
        }

        if (cmd.equals("--get")) {
            if (args.length == 1) {
                System.out.println("Error: No source given");
                return;
            }
            String source = args[1];
            String response = HttpGet.runCommand(source);
            System.out.println(response);
            return;
        }

        System.out.println("Error: Invalid Command");
    }
}