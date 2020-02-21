package org.slix.mproxy.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slix.mproxy.repository.ItemRepository;
import org.slix.mproxy.sender.PayloadSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class JmsQueueConsumer {

    private final Logger logger = LoggerFactory.getLogger(JmsQueueConsumer.class);
    private final PayloadSender payloadSender;
    private final ItemRepository itemRepository;

    @Autowired
    public JmsQueueConsumer(@Qualifier("payloadSenderSimple") PayloadSender payloadSender, ItemRepository itemRepository) {
        this.payloadSender = payloadSender;
        this.itemRepository = itemRepository;
    }

    @JmsListener(destination = JmsConfig.FORWARD_QUEUE_NAME, containerFactory = "myFactory")
    public void listener(String documentId) {
        logger.info("Message received {} ", documentId);

        itemRepository.findById(documentId)
                .ifPresent(item -> {
                    payloadSender.sendingForward(item.getPayload());
                    item.setIsSent(true);
                    itemRepository.save(item);
                });
    }
}
