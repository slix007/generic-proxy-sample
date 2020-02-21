package org.slix.mproxy.repository;

import org.slix.mproxy.model.ItemDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<ItemDocument, String> {

}
