package com.groofycode.GroofyCode.controller;

import com.groofycode.GroofyCode.model.Message;
import com.groofycode.GroofyCode.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ClanChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    // Handle messages sent by users to a specific clan chat
    @MessageMapping("/clan/{clanId}/sendMessage")
    public void sendMessageToClan(@Payload String message, @DestinationVariable("clanId") Long clanId) {
        // Send the message to all subscribers of the clan
        System.out.println(message);
        messagingTemplate.convertAndSend("/topic/clan/" + clanId, message);
    }



    // Handle messages sent by users to a specific clan chat
    @MessageMapping("/clan/{clanId}/sendMessage")
    public void sendMessageToClan(@Payload Message message, @DestinationVariable("clanId") Long clanId) {
        messageService.sendMessageToClan(message, clanId);

        // Send the message to all subscribers of the clan
        messagingTemplate.convertAndSend("/topic/clan/" + clanId, message);
    }

}