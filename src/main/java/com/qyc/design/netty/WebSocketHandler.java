package com.qyc.design.netty;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
@Slf4j
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Resource
    private NettyConfig nettyConfig;

    /**
     * 当客户端连接的时候
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        nettyConfig.getChannelGroup().add(ctx.channel());
        log.info("有新的连接加入。。。");
    }

    /**
     * 当客户端断开连接的时候
     * @param ctx
     * @throws Exception
     */


    /**
     * 读取客户端发送的消息
     * @param channelHandlerContext
     * @param textWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg = textWebSocketFrame.text();
        log.info("msg:{}", msg);
        /**
         * 回复消息
         */
        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("服务器连接成功！"));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //当有客户端断开连接的时候,就移除对应的通道
        nettyConfig.getChannelGroup().remove(channel);
        removeUserId(ctx);
        log.info("有连接已经断开。。。");
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        log.info("异常消息:{}", cause.getMessage());
        nettyConfig.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
    }


    private void removeUserId(ChannelHandlerContext ctx){
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        String userId = ctx.channel().attr(key).get();
        nettyConfig.getUserChannelMap().remove(userId);
    }
}
