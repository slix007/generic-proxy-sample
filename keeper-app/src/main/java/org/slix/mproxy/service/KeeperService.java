package org.slix.mproxy.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slix.mproxy.jms.JmsQueuePublisher;
import org.slix.mproxy.model.ItemDocument;
import org.slix.mproxy.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class KeeperService {
    private final JmsQueuePublisher jmsQueueSender;
    private final ItemRepository itemRepository;

    public String keepRequestAndForward(Object theItem, String ip) {
        final ObjectId id = new ObjectId();
        final String idString = id.toString();

        final ItemDocument itemDocument = new ItemDocument(id, theItem, ip, new Date(), false);
        itemRepository.save(itemDocument);
        jmsQueueSender.send(idString);

        return idString;
    }
}
