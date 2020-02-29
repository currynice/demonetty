package com.cxy.demonetty.IM.groupChat.server;

import com.cxy.demonetty.procotol.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 在 MessageRequestHandler 之前插入了一个 AuthHandler，
 * 因此 MessageRequestHandler 以及后续所有指令相关的的处理都会经过 AuthHandler 的一层过滤，
 * 只要在 AuthHandler 里面处理掉身份认证相关的逻辑，后续所有的 handler 都不用操心身份认证
 */
@ChannelHandler.Sharable
public class AuthHandler  extends ChannelInboundHandlerAdapter {

    // 2. 构造单例
    public static final AuthHandler INSTANCE = new AuthHandler();

    protected AuthHandler() {
    }
    /**
     * 重写了 channelRead() 方法，表明可以处理所有类型的数据
     * 如果未登录，直接强制关闭连接（实际生产环境要复杂些），否则，就把读到的数据向下传递，传递给后续指令处理器。
     *
     * 上述缺陷： 如果客户端已经登录成功了，那么在每次处理客户端数据之前，
     * 除了第一次剩余的 身份校验逻辑都是没有必要的，因为只要连接未断开，客户端只要成功登录过，后续就不需要再进行客户端的身份校验。
     *
     * 改进：判断如果已经经过权限认证，调用 pipeline 的 remove() 方法删除自身，this 就是 AuthHandler 这个对象，
     * 删除之后，这条客户端连接的逻辑链中就不再有这段逻辑了。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            //利用pipeline热插拔特点，减少不必要的逻辑
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }


    /**
     *仅仅为了当前handler移除时进行记录
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
