package com.dougwang.rpc;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * @Author: MikeWang
 * @Date: 2020/6/8 5:33 PM
 * @Description:
 * ChannelInboundHandlerAdapter
 * ChannelInboundHandlerAdapter是ChannelInboundHandler的一个简单实现，
 * 默认情况下不会做任何处理，
 * 只是简单的将操作通过fire*方法传递到ChannelPipeline中的下一个ChannelHandler中让链中的下一个ChannelHandler去处理。
 *
 * hannelInboundHandler用来处理入站数据以及各种状态变化。ChannelInboundHandlerAdapter对接口做适配，
 * 默认简单的提交到ChannelPipeline的下一个ChannelHandler,
 * 在实现过程中只需要专注重写自己想要的方法即可，但是它不会自动的释放与池化ByteBuf相关的内存，
 * 需要手动调用 ReferenceCountUtil.release()自动的实现在SimpleChannelInboundHandler，
 * 注意如果要传递给下一个ChannelHandler需要调用 ReferenceCountUtil.retain()
    https://juejin.im/post/5af2f7145188256735648544
 */
@Sharable
public class NettyServerHandler  extends ChannelInboundHandlerAdapter {
    // 根据消息内容和请求id，拼接消息帧
    public String generatorFrame(String msg, String reqId) {
        return msg + ":" + reqId + "|";
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        // 异步，释放IO线程
        AllChannelHandler.channelRead(() -> {
            try {
                System.out.println(msg);
                // 1.获取消息体，并且解析出请求id
                String str = (String) msg;
                String reqId = str.split(":")[1];

                // 2.拼接结果，请求id,协议帧分隔符(模拟服务端执行服务产生结果)
                String resp =  generatorFrame("im jiaduo ", reqId);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 3.写回结果
                ctx.channel().writeAndFlush(Unpooled.copiedBuffer(resp.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) {
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("------in active----");
        ctx.fireChannelInactive();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("------handlerRemoved----");
        super.handlerRemoved(ctx);

    }

}
