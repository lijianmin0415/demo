//package com.example.demo.controller;
//
//import com.example.demo.service.Text;
//import com.example.demo.service.impl.WebSocket;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/demo")
//public class TestController {
//    @Autowired
//    private Text text;
//    @Autowired
//    private WebSocket webSocket;
//
//    @GetMapping(value = "/add")
//    public String add(){
//        text.selectSm();
//        webSocket.sendMessage("成功了");
//        return "成功了";
//    }
//}
