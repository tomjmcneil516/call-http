package com.example.callhttp;

import java.io.IOException;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommandController {

    @GetMapping("/call-http")
    public String getURL(@RequestParam String method, String target) throws IOException, InterruptedException {
        return HttpGet.runCommand(target);
    }
}
