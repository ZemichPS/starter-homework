package by.zemich.sessionmicroservice.dao.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Document(collection = "session")
@Builder
public class Session {
    @MongoId(FieldType.OBJECT_ID)
    ObjectId id;
    private String userLogin;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
}
