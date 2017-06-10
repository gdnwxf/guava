package com.wch.guava.event;

import com.google.common.eventbus.Subscribe;

class MultiEventListener {

    @Subscribe
    public void listen(OrderEvent event) {
        System.out.println("receive msg:"+event.getMessage());
    }
    
    /**
     * 可以没有对应的处理方法
     * @param event
     */
    @Subscribe
    public void listen(String event) {
        System.out.println("receive msg:"+event);
    }
}