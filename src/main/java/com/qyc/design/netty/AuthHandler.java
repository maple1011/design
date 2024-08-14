package com.qyc.design.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@ChannelHandler.Sharable
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private NettyConfig nettyConfig;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            HttpHeaders headers = request.headers();
            if (headers.size() < 1) {
                ctx.channel().close();
                return;
            }
            String uid = headers.get("Sec-WebSocket-Protocol");
            log.debug("Authentication success. uid: {}", uid);
            AttributeKey<String> key = AttributeKey.valueOf("userId");

            ctx.channel().attr(key).setIfAbsent(uid);
            nettyConfig.getUserChannelMap().put(uid,ctx.channel());
            ctx.pipeline().remove(this);
            ctx.fireChannelRead(msg);
        }else{
            ctx.channel().close();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
