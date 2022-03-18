package com.netty.client;

import com.netty.client.netty.NettyClientBootStrap;
import com.netty.client.netty.NettyClientHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyClientApplication implements ApplicationRunner {

	@Autowired
	private NettyClientBootStrap nettyClientBootStrap;

	public static void main(String[] args) {
		SpringApplication.run(NettyClientApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		nettyClientBootStrap.start(new NioEventLoopGroup());
	}
}
