package ro.tuc.ds2020.controllers.handlers;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;
import com.thetransactioncompany.jsonrpc2.server.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;


@Slf4j
public class MessageHandler implements RequestHandler {

    private SimpMessagingTemplate simpMessagingTemplate;

    public MessageHandler(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public String[] handledRequests() {
        return new String[]{"send", "typing", "seen"};
    }

    public JSONRPC2Response process(JSONRPC2Request req, MessageContext ctx) {

        List params = (List) req.getParams();
        Object to = params.get(0);
        Object from = params.get(1);

        if (req.getMethod().equals("send")) {
            Object message = params.get(2);
            log.info("Req" + req);
            simpMessagingTemplate.convertAndSend("/all/chat/"+ to + "/" + from +"/", message);
            return new JSONRPC2Response("Success", req.getID());
        }

        if(req.getMethod().equals("typing")){

            log.info("Req" + req);
            simpMessagingTemplate.convertAndSend("/all/chat/"+ to + "/" + from +"/", "typing");

            return new JSONRPC2Response("Success", req.getID());

        }
        if(req.getMethod().equals("seen")){

            log.info("Req" + req);
            simpMessagingTemplate.convertAndSend("/all/chat/"+ to + "/" + from +"/", "seen");

            return new JSONRPC2Response("Success", req.getID());

        }
        else {
            return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, req.getID());
        }
    }
}
