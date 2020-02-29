package com.cxy.demonetty.IM.singleChat.server;

import com.cxy.demonetty.IM.session.Session;
import com.cxy.demonetty.procotol.packet.request.LoginRequestPacket;
import com.cxy.demonetty.procotol.packet.response.LoginResponsePacket;
import com.cxy.demonetty.procotol.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    /**
     * 服务端处理登录请求
     * @param ctx
     * @param loginRequestPacket
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {


        // 只能处理登录请求数据包
            System.out.println(new Date() + ": 收到来自客户端的登录请求……");

            LoginResponsePacket responsePacket = login(loginRequestPacket, ctx.channel());
//            // 编码有编码器了
//            ByteBuf responseByteBuf = PacketCodeC.INSTANCE().encode(ctx.alloc(), responsePacket);
        //发送登陆响应
        ctx.channel().writeAndFlush(responsePacket);

    }
    private LoginResponsePacket login(LoginRequestPacket loginRequestPacket,Channel channel){
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(loginRequestPacket.getVersion());
        responsePacket.setUserName(loginRequestPacket.getUserName());
        // 登录校验
        if (valid(loginRequestPacket)) {
            //随机一个userId
            responsePacket.setSuccess(true);
            String userId = randomUserId();
            responsePacket.setUserId(userId);
            //设置该连接为已登陆
            SessionUtil.bindSession(new Session(userId,loginRequestPacket.getUserName()),channel);
            System.out.println(new Date() + ": 登录成功!");
        } else {
            responsePacket.setReason("账号密码校验失败");
            responsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }
        return responsePacket;
    }


    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }


    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    /**
     * 用户断线之后取消绑定
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        SessionUtil.unBindSession(ctx.channel());
    }


}
