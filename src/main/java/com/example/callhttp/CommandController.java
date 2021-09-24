package com.example.callhttp;

import java.io.IOException;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommandController {

    @GetMapping(
            value = "/call-http",
            params = { "method=get", "target" }
    )
    public String getURL(@RequestParam String target) {
        return HttpGet.runCommand(target);
    }
}
