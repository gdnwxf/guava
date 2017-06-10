package com.wch.guava.event;

class OrderEvent {
    private String message;
    public OrderEvent(String message) {        
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}