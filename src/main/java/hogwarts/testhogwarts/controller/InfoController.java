package hogwarts.testhogwarts.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/info")
public class InfoController {
    private final static Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private int port;

    @GetMapping("/port")
    public int port() {
        logger.error("Getting port {}", port);
        return port;
    }
}
