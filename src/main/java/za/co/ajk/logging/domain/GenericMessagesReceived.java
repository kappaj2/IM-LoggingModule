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
    @Field("message_date_created")
    private Instant messageDateCreated;
    
    @NotNull
    @Field("date_received")
    private Instant dateReceived;
    
    @NotNull
    @Field("pub_sub_message_type_code")
    private String pubSubMessageTypeCode;
    
    @NotNull
    @Field("incident_number")
    private Long incidentNumber;
    
    @NotNull
    @Field("incident_header")
    private String incidentHeader;
    
    @NotNull
    @Field("incident_description")
    private String incidentDescription;
    
    @NotNull
    @Field("event_type_code")
    private String eventTypeCode;
    
    @NotNull
    @Field("incident_priority_code")
    private String incidentPriorityCode;
    
    @NotNull
    @Field("operator_name")
    private String operatorName;
    
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
    

    public void setOriginatingModule(String originatingModule) {
        this.originatingModule = originatingModule;
    }

    public Instant getDateReceived() {
        return dateReceived;
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
    
    public Instant getMessageDateCreated() {
        return messageDateCreated;
    }
    
    public void setMessageDateCreated(Instant messageDateCreated) {
        this.messageDateCreated = messageDateCreated;
    }
    
    public String getPubSubMessageTypeCode() {
        return pubSubMessageTypeCode;
    }
    
    public void setPubSubMessageTypeCode(String pubSubMessageTypeCode) {
        this.pubSubMessageTypeCode = pubSubMessageTypeCode;
    }
    
    public Long getIncidentNumber() {
        return incidentNumber;
    }
    
    public void setIncidentNumber(Long incidentNumber) {
        this.incidentNumber = incidentNumber;
    }
    
    public String getIncidentHeader() {
        return incidentHeader;
    }
    
    public void setIncidentHeader(String incidentHeader) {
        this.incidentHeader = incidentHeader;
    }
    
    public String getIncidentDescription() {
        return incidentDescription;
    }
    
    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }
    
    public String getEventTypeCode() {
        return eventTypeCode;
    }
    
    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }
    
    public String getIncidentPriorityCode() {
        return incidentPriorityCode;
    }
    
    public void setIncidentPriorityCode(String incidentPriorityCode) {
        this.incidentPriorityCode = incidentPriorityCode;
    }
    
    public String getOperatorName() {
        return operatorName;
    }
    
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    
    //
    //  Builder methods for object construction
    //
    public GenericMessagesReceived originatingModule(String originatingModule) {
        this.originatingModule = originatingModule;
        return this;
    }
    
    public GenericMessagesReceived dateReceived(Instant dateReceived) {
        this.dateReceived = dateReceived;
        return this;
    }
    
    public GenericMessagesReceived messageId(String  messageId) {
        this.messageId = messageId;
        return this;
    }
    
    public GenericMessagesReceived messageDateCreated(Instant messageDateCreated) {
        this.messageDateCreated = messageDateCreated;
        return this;
    }
    
    public GenericMessagesReceived pubSubMessageTypeCode(String pubSubMessageTypeCode) {
        this.pubSubMessageTypeCode = pubSubMessageTypeCode;
        return this;
    }
    public GenericMessagesReceived incidentNumber(Long incidentNumber) {
        this.incidentNumber = incidentNumber;
        return this;
    }
    
    public GenericMessagesReceived incidentHeader(String incidentHeader) {
        this.incidentHeader = incidentHeader;
        return this;
    }
    
    public GenericMessagesReceived incidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
        return this;
    }
    
    public GenericMessagesReceived eventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
        return this;
    }
    
    public GenericMessagesReceived incidentPriorityCode(String incidentPriorityCode) {
        this.incidentPriorityCode = incidentPriorityCode;
        return this;
    }
    
    public GenericMessagesReceived operatorName(String operatorName) {
        this.operatorName = operatorName;
        return this;
    }
    
    public GenericMessagesReceived payload(String payload) {
        this.payload = payload;
        return this;
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
        
        return Objects.hash(id, messageId, originatingModule, messageDateCreated, dateReceived, pubSubMessageTypeCode,
            incidentNumber, incidentHeader, incidentDescription, eventTypeCode, incidentPriorityCode, operatorName,
            payload);
    }
    
    @Override
    public String toString() {
        return "GenericMessagesReceived{" +
            "id='" + id + '\'' +
            ", messageId='" + messageId + '\'' +
            ", originatingModule='" + originatingModule + '\'' +
            ", messageDateCreated='" + messageDateCreated + '\'' +
            ", dateReceived=" + dateReceived +
            ", pubSubMessageTypeCode='" + pubSubMessageTypeCode + '\'' +
            ", incidentNumber=" + incidentNumber +
            ", incidentHeader='" + incidentHeader + '\'' +
            ", incidentDescription='" + incidentDescription + '\'' +
            ", eventTypeCode='" + eventTypeCode + '\'' +
            ", incidentPriorityCode='" + incidentPriorityCode + '\'' +
            ", operatorName='" + operatorName + '\'' +
            ", payload='" + payload + '\'' +
            '}';
    }
}
