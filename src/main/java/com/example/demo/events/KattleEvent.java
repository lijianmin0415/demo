package com.example.demo.events;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 定义kattle执行事件
 */
@Data
public class KattleEvent extends ApplicationEvent {
    private String message;

    public KattleEvent(Object source, String message) {
        super(source);//强制调用
        this.message = message;
    }
    @Override
    public Object getSource(){
        return super.getSource();
    }
}
