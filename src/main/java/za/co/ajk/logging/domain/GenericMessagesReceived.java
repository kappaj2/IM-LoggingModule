package za.co.ajk.logging.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A GenericMessagesReceived.
 */
@Document(collection = "generic_messages_received")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "genericmessagesreceived")
public class GenericMessagesReceived implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("message_id")
    private String messageId;
    
    @NotNull
    @Field("originating_module")
    private String originatingModule;

    @NotNull
    @Field("date_received")
    private Instant dateReceived;
    
    @NotNull
    @Field("payload")
    private String payload;
    

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getMessageId() {
        return messageId;
    }
    
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    public String getOriginatingModule() {
        return originatingModule;
    }

    public GenericMessagesReceived originatingModule(String originatingModule) {
        this.originatingModule = originatingModule;
        return this;
    }

    public void setOriginatingModule(String originatingModule) {
        this.originatingModule = originatingModule;
    }

    public Instant getDateReceived() {
        return dateReceived;
    }

    public GenericMessagesReceived dateReceived(Instant dateReceived) {
        this.dateReceived = dateReceived;
        return this;
    }

    public void setDateReceived(Instant dateReceived) {
        this.dateReceived = dateReceived;
    }
    
    public String getPayload() {
        return payload;
    }
    
    public void setPayload(String payload) {
        this.payload = payload;
    }
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenericMessagesReceived genericMessagesReceived = (GenericMessagesReceived) o;
        if (genericMessagesReceived.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), genericMessagesReceived.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GenericMessagesReceived{" +
            "id=" + getId() +
            ", originating_module='" + getOriginatingModule() + "'" +
            ", date_received='" + getDateReceived() + "'" +
            "}";
    }
}
