package com.qyc.design.netty;


import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyConfig {
    /**
     * netty监听的端口
     */
    private int port;

    /**
     * websocket访问路径
     */
    private String path;


    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static ConcurrentHashMap<String, Channel> userChannelMap = new ConcurrentHashMap<>();


    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public ConcurrentHashMap<String,Channel> getUserChannelMap(){
        return userChannelMap;
    }
}
