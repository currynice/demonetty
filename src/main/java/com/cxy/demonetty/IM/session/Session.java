package com.cxy.demonetty.IM.session;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用户会话信息 todo 头像,...
 */
@Data
@NoArgsConstructor
public class Session {
    // 用户唯一性标识
    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId + ":" + userName;
    }

}
