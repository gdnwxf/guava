package com.wch.guava.event;

import com.google.common.eventbus.EventBus;

/**
 * <pre>
 * 在EventBus的架构下，你可以任意重命名@Subscribe注解的处理方法，
 * 并且这类重命名不会被传播（即不会引起其他类的修改），
 * 因为对EventBus来说，处理方法的名字是无关紧要的。
 * 如果测试代码中直接调用了处理方法，那么当然，
 * 重命名处理方法会引起测试代码的变动，但使用EventBus触发处理方法的代码就不会发生变更。
 * 我们认为这是EventBus的特性，而不是漏洞：能够任意重命名处理方法，
 * 可以让你的处理方法命名更清晰。
 * </pre>
 * @author qinghao
 *
 */
public class TestEvent {

	public static void main(String[] args) {
		
		//Creates a new EventBus with the given identifier.
        EventBus eventBus = new EventBus("ricky");

        //register all subscriber 
        eventBus.register(new HelloEventListener());

        //publish event 
        eventBus.post(new OrderEvent("hello"));
        eventBus.post(new OrderEvent("world"));
        
        
        
        
      //Creates a new EventBus with the given identifier.
        EventBus eventBus2 = new EventBus("ricky");

        //register all subscriber 
        eventBus2.register(new HelloEventListener());
        eventBus2.register(new MultiEventListener());

        //publish event 
        eventBus2.post(new OrderEvent("hello"));
        eventBus2.post(new OrderEvent("world"));

        eventBus2.post("Haha");
     
	}
}
