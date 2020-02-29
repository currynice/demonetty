package com.cxy.demonetty.IM.singleChat.client;

import com.cxy.demonetty.IM.session.Session;
import com.cxy.demonetty.procotol.packet.response.LoginResponsePacket;
import com.cxy.demonetty.procotol.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 客户端登录请求逻辑
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + ": 客户端开始登录");
//
//        // 创建登录对象
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setUserName("cxy");
//        loginRequestPacket.setPassword("123");
//
//        // ctx.alloc()获取与当前连接相关的ByteBuf分配器
////        ByteBuf buffer = PacketCodeC.INSTANCE().encode(ctx.alloc(), loginRequestPacket);
//
//        // 写回登陆响应包数据
//        ctx.channel().writeAndFlush(loginRequestPacket);
//    }


    /**
     * 客户端处理登录响应
     * @param ctx
     * @param loginResponsePacket
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {
        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();
        if (loginResponsePacket.isSuccess()) {
            System.out.println("[" + userName + "]登录成功，userId 为: " + loginResponsePacket.getUserId());
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            System.out.println("[" + userName + "]登录失败，原因：" + loginResponsePacket.getReason());
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
