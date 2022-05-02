package com.example.nettyserver.attributes;

import io.netty.util.AttributeKey;

/**
 * @author qijs
 * @description
 * @date 2022/5/1
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
