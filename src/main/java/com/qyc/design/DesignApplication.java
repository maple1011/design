package com.qyc.design;

import com.qyc.design.netty.NettyWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesignApplication implements CommandLineRunner {


    @Autowired
    private NettyWebSocketServer nettyWebSocketServer;
    public static void main(String[] args) {
        SpringApplication.run(DesignApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(nettyWebSocketServer).start();
    }
}
