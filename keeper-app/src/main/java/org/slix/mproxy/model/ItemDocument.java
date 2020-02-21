package org.slix.mproxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDocument {

    @Id
    private ObjectId id;
    private Object payload;
    private String ip;
    private Date date;
    private Boolean isSent;
}
