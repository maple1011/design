package com.qyc.design.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
@Slf4j
public class WebSocketChannelConfig extends ChannelInitializer<Channel> {

    @Resource
    private NettyConfig netty;
    @Resource
    private WebSocketHandler webSocketHandler;

    @Autowired
    private AuthHandler authHandler;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // 对http协议的支持.
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ObjectEncoder());
        // 对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // HttpObjectAggregator将多个信息转化成单一的request或者response对象
        pipeline.addLast(new HttpObjectAggregator(8000));
        // 鉴权
        pipeline.addLast(authHandler);
        // 心跳检测
        pipeline.addLast(new IdleStateHandler(10 , 0 , 0));
        pipeline.addLast(new HeartBeatHandler());
        // 将http协议升级为ws协议. 对websocket的支持
        pipeline.addLast(new WebSocketServerProtocolHandler(netty.getPath()));
        // 自定义处理handler
        pipeline.addLast(webSocketHandler);
    }
}
