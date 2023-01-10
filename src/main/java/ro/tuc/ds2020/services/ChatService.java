package ro.tuc.ds2020.services;

import com.thetransactioncompany.jsonrpc2.*;
import com.thetransactioncompany.jsonrpc2.server.Dispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.MessageHandler;

@Service
@Slf4j
public class ChatService {

    private SimpMessagingTemplate simpMessagingTemplate;
    private Dispatcher dispatcher;
    private MessageHandler messageHandler;
    private JSONRPC2Parser jsonrpc2Parser ;

    @Autowired
    public ChatService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.dispatcher = new Dispatcher();
        this.messageHandler = new MessageHandler(this.simpMessagingTemplate);
        this.jsonrpc2Parser = new JSONRPC2Parser();

        dispatcher.register(messageHandler);
    }

    public JSONRPC2Response sendMessage(String req) {
        JSONRPC2Request request;
        try {
            request = this.jsonrpc2Parser.parseJSONRPC2Request(req);
            log.info("METHOD:" + request.getMethod());
            log.info("request:" + request);
            return dispatcher.process(request, null);

        } catch (JSONRPC2ParseException e) {
            e.printStackTrace();
        }
        return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, null);
    }
}
