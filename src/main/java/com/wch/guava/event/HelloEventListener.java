package com.wch.guava.event;

import com.google.common.eventbus.Subscribe;

class HelloEventListener{

    @Subscribe
    public void listen(OrderEvent event) {
        System.out.println("receive msg:"+event.getMessage());
    }
    
    @Subscribe
    public void listen2(OrderEvent event) {
    	System.out.println("@2 --> receive msg:"+event.getMessage());
    }
}