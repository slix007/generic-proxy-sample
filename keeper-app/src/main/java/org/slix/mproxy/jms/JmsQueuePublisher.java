package org.slix.mproxy.jms;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@RequiredArgsConstructor
public class JmsQueuePublisher {

    private final Queue forwardQueue;
    private final JmsTemplate jmsTemplate;

    public void send(String id) {
        jmsTemplate.convertAndSend(forwardQueue, id);
    }
}
