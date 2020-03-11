package com.lancoo.cpk12.cplibrary.utils;


import com.lancoo.cpk12.cplibrary.bean.EventMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus 工具类
 */

public class EventBusUtils {


    private EventBusUtils() {
    }

    /**
     * 注册 EventBus * * @param subscriber
     */
    public static void register(Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber);
        }
    }

    /**
     * 解除注册 EventBus * * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber);
        }
    }

    /**
     * 发送事件消息 * * @param event
     */
    public static void post(EventMessage event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送粘性事件消息 * * @param event  粘性事件的含义就是此事件发送后，再次注册
     * 事件。仍然会收到消息
     */
    public static void postSticky(EventMessage event) {
        EventBus.getDefault().postSticky(event);
    }


}
