package com.example.nettyserver.util;

import com.example.nettyserver.attributes.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author qijs
 * @description
 * @date 2022/5/1
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(Boolean.TRUE);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
