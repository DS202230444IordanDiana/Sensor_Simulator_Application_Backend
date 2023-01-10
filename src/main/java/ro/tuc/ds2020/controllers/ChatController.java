package ro.tuc.ds2020.controllers;


import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.services.ChatService;

@RestController
@CrossOrigin
@RequestMapping(path = "/chat")
@Slf4j
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public JSONRPC2Response sendMessage(@RequestBody String  request) {
        return chatService.sendMessage(request);
    }
}
