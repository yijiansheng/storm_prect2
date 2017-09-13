package com.stormio;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

/**
 * Author: changdalin
 * Date: 2017/9/13
 * Description:
 **/
public class HuidiaoServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(
            HuidiaoServerHandler.class.getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        System.out.println(cause.getMessage());
        ctx.close();
    }

}
