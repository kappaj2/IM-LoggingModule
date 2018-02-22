package za.co.ajk.logging.web.rest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import za.co.ajk.logging.LoggingModuleApp;
import za.co.ajk.logging.domain.GenericMessagesReceived;
import za.co.ajk.logging.repository.GenericMessagesReceivedRepository;
import za.co.ajk.logging.repository.search.GenericMessagesReceivedSearchRepository;
import za.co.ajk.logging.service.GenericMessagesReceivedService;
import za.co.ajk.logging.web.rest.errors.ExceptionTranslator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static za.co.ajk.logging.web.rest.TestUtil.createFormattingConversionService;

/**
 * Test class for the GenericMessagesReceivedResource REST controller.
 *
 * @see GenericMessagesReceivedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoggingModuleApp.class)
public class GenericMessagesReceivedResourceIntTest {

    private static final String DEFAULT_ORIGINATING_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATING_MODULE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_RECEIVED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_RECEIVED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String MESSAGE_ID = "122";
    private static final String PUB_SUB_TYPE_CODE = "C1";
    private static final Long INCIDENT_NUMBER = 123l;
    private static final String INCIDENT_HEADER = "Incident_header";
    private static final String INCIDENT_DESCRIPTION = "Incident_description";
    private static final String EVENT_TYPE_CODE = "EV1";
    private static final String INCIDENT_PRIORITY_CODE = "PRIO1";
    private static final String OPERATOR_NAME = "Operator1";
    private static final String PAYLOAD = "Payload of incident";
    
    @Autowired
    private GenericMessagesReceivedRepository genericMessagesReceivedRepository;

    @Autowired
    private GenericMessagesReceivedService genericMessagesReceivedService;

    @Autowired
    private GenericMessagesReceivedSearchRepository genericMessagesReceivedSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restGenericMessagesReceivedMockMvc;

    private GenericMessagesReceived genericMessagesReceived;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GenericMessagesReceivedResource genericMessagesReceivedResource = new GenericMessagesReceivedResource(genericMessagesReceivedService);
        this.restGenericMessagesReceivedMockMvc = MockMvcBuilders.standaloneSetup(genericMessagesReceivedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GenericMessagesReceived createEntity() {
        GenericMessagesReceived genericMessagesReceived = new GenericMessagesReceived()
            .originatingModule(DEFAULT_ORIGINATING_MODULE)
            .dateReceived(DEFAULT_DATE_RECEIVED)
            .messageId(MESSAGE_ID)
            .messageDateCreated(UPDATED_DATE_RECEIVED)
            .pubSubMessageTypeCode(PUB_SUB_TYPE_CODE)
            .incidentNumber(INCIDENT_NUMBER)
            .incidentHeader(INCIDENT_HEADER)
            .incidentDescription(INCIDENT_DESCRIPTION)
            .eventTypeCode(EVENT_TYPE_CODE)
            .incidentPriorityCode(INCIDENT_PRIORITY_CODE)
            .operatorName(OPERATOR_NAME)
            .payload(PAYLOAD);
        
        return genericMessagesReceived;
    }

    @Before
    public void initTest() {
        genericMessagesReceivedRepository.deleteAll();
        genericMessagesReceivedSearchRepository.deleteAll();
        genericMessagesReceived = createEntity();
    }

    @Test
    public void createGenericMessagesReceived() throws Exception {
        int databaseSizeBeforeCreate = genericMessagesReceivedRepository.findAll().size();

        // Create the GenericMessagesReceived
        restGenericMessagesReceivedMockMvc.perform(post("/api/generic-messages-received")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericMessagesReceived)))
            .andExpect(status().isCreated());

        // Validate the GenericMessagesReceived in the database
        List<GenericMessagesReceived> genericMessagesReceivedList = genericMessagesReceivedRepository.findAll();
        assertThat(genericMessagesReceivedList).hasSize(databaseSizeBeforeCreate + 1);
        GenericMessagesReceived testGenericMessagesReceived = genericMessagesReceivedList.get(genericMessagesReceivedList.size() - 1);
        assertThat(testGenericMessagesReceived.getOriginatingModule()).isEqualTo(DEFAULT_ORIGINATING_MODULE);
        assertThat(testGenericMessagesReceived.getDateReceived()).isEqualTo(DEFAULT_DATE_RECEIVED);

        // Validate the GenericMessagesReceived in Elasticsearch
        GenericMessagesReceived genericMessagesReceivedEs = genericMessagesReceivedSearchRepository.findOne(testGenericMessagesReceived.getId());
        assertThat(genericMessagesReceivedEs).isEqualToIgnoringGivenFields(testGenericMessagesReceived);
    }

    @Test
    public void createGenericMessagesReceivedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = genericMessagesReceivedRepository.findAll().size();

        // Create the GenericMessagesReceived with an existing ID
        genericMessagesReceived.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenericMessagesReceivedMockMvc.perform(post("/api/generic-messages-received")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericMessagesReceived)))
            .andExpect(status().isBadRequest());

        // Validate the GenericMessagesReceived in the database
        List<GenericMessagesReceived> genericMessagesReceivedList = genericMessagesReceivedRepository.findAll();
        assertThat(genericMessagesReceivedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkOriginating_moduleIsRequired() throws Exception {
        int databaseSizeBeforeTest = genericMessagesReceivedRepository.findAll().size();
        // set the field null
        genericMessagesReceived.setOriginatingModule(null);

        // Create the GenericMessagesReceived, which fails.

        restGenericMessagesReceivedMockMvc.perform(post("/api/generic-messages-received")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericMessagesReceived)))
            .andExpect(status().isBadRequest());

        List<GenericMessagesReceived> genericMessagesReceivedList = genericMessagesReceivedRepository.findAll();
        assertThat(genericMessagesReceivedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDate_receivedIsRequired() throws Exception {
        int databaseSizeBeforeTest = genericMessagesReceivedRepository.findAll().size();
        // set the field null
        genericMessagesReceived.setDateReceived(null);

        // Create the GenericMessagesReceived, which fails.

        restGenericMessagesReceivedMockMvc.perform(post("/api/generic-messages-received")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericMessagesReceived)))
            .andExpect(status().isBadRequest());

        List<GenericMessagesReceived> genericMessagesReceivedList = genericMessagesReceivedRepository.findAll();
        assertThat(genericMessagesReceivedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllGenericMessagesReceiveds() throws Exception {
        // Initialize the database
        genericMessagesReceivedRepository.save(genericMessagesReceived);

        // Get all the genericMessagesReceivedList
        restGenericMessagesReceivedMockMvc.perform(get("/api/generic-messages-received?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genericMessagesReceived.getId())))
            .andExpect(jsonPath("$.[*].originatingModule").value(hasItem(DEFAULT_ORIGINATING_MODULE.toString())))
            .andExpect(jsonPath("$.[*].dateReceived").value(hasItem(DEFAULT_DATE_RECEIVED.toString())));
    }

    @Test
    public void getGenericMessagesReceived() throws Exception {
        // Initialize the database
        genericMessagesReceivedRepository.save(genericMessagesReceived);

        // Get the genericMessagesReceived
        restGenericMessagesReceivedMockMvc.perform(get("/api/generic-messages-received/{id}", genericMessagesReceived.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(genericMessagesReceived.getId()))
            .andExpect(jsonPath("$.originatingModule").value(DEFAULT_ORIGINATING_MODULE.toString()))
            .andExpect(jsonPath("$.dateReceived").value(DEFAULT_DATE_RECEIVED.toString()));
    }

    @Test
    public void getNonExistingGenericMessagesReceived() throws Exception {
        // Get the genericMessagesReceived
        restGenericMessagesReceivedMockMvc.perform(get("/api/generic-messages-received/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGenericMessagesReceived() throws Exception {
        // Initialize the database
        genericMessagesReceivedService.save(genericMessagesReceived);

        int databaseSizeBeforeUpdate = genericMessagesReceivedRepository.findAll().size();

        // Update the genericMessagesReceived
        GenericMessagesReceived updatedGenericMessagesReceived = genericMessagesReceivedRepository.findOne(genericMessagesReceived.getId());
        updatedGenericMessagesReceived
            .originatingModule(UPDATED_ORIGINATING_MODULE)
            .dateReceived(UPDATED_DATE_RECEIVED);

        restGenericMessagesReceivedMockMvc.perform(put("/api/generic-messages-received")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGenericMessagesReceived)))
            .andExpect(status().isOk());

        // Validate the GenericMessagesReceived in the database
        List<GenericMessagesReceived> genericMessagesReceivedList = genericMessagesReceivedRepository.findAll();
        assertThat(genericMessagesReceivedList).hasSize(databaseSizeBeforeUpdate);
        GenericMessagesReceived testGenericMessagesReceived = genericMessagesReceivedList.get(genericMessagesReceivedList.size() - 1);
        assertThat(testGenericMessagesReceived.getOriginatingModule()).isEqualTo(UPDATED_ORIGINATING_MODULE);
        assertThat(testGenericMessagesReceived.getDateReceived()).isEqualTo(UPDATED_DATE_RECEIVED);

        // Validate the GenericMessagesReceived in Elasticsearch
        GenericMessagesReceived genericMessagesReceivedEs = genericMessagesReceivedSearchRepository.findOne(testGenericMessagesReceived.getId());
        assertThat(genericMessagesReceivedEs).isEqualToIgnoringGivenFields(testGenericMessagesReceived);
    }

    @Test
    public void updateNonExistingGenericMessagesReceived() throws Exception {
        int databaseSizeBeforeUpdate = genericMessagesReceivedRepository.findAll().size();

        // Create the GenericMessagesReceived

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGenericMessagesReceivedMockMvc.perform(put("/api/generic-messages-received")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(genericMessagesReceived)))
            .andExpect(status().isCreated());

        // Validate the GenericMessagesReceived in the database
        List<GenericMessagesReceived> genericMessagesReceivedList = genericMessagesReceivedRepository.findAll();
        assertThat(genericMessagesReceivedList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteGenericMessagesReceived() throws Exception {
        // Initialize the database
        genericMessagesReceivedService.save(genericMessagesReceived);

        int databaseSizeBeforeDelete = genericMessagesReceivedRepository.findAll().size();

        // Get the genericMessagesReceived
        restGenericMessagesReceivedMockMvc.perform(delete("/api/generic-messages-received/{id}", genericMessagesReceived.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean genericMessagesReceivedExistsInEs = genericMessagesReceivedSearchRepository.exists(genericMessagesReceived.getId());
        assertThat(genericMessagesReceivedExistsInEs).isFalse();

        // Validate the database is empty
        List<GenericMessagesReceived> genericMessagesReceivedList = genericMessagesReceivedRepository.findAll();
        assertThat(genericMessagesReceivedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchGenericMessagesReceived() throws Exception {
        // Initialize the database
        genericMessagesReceivedService.save(genericMessagesReceived);

        // Search the genericMessagesReceived
        restGenericMessagesReceivedMockMvc.perform(get("/api/_search/generic-messages-received?query=id:" + genericMessagesReceived.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genericMessagesReceived.getId())))
            .andExpect(jsonPath("$.[*].originatingModule").value(hasItem(DEFAULT_ORIGINATING_MODULE.toString())))
            .andExpect(jsonPath("$.[*].dateReceived").value(hasItem(DEFAULT_DATE_RECEIVED.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenericMessagesReceived.class);
        GenericMessagesReceived genericMessagesReceived1 = new GenericMessagesReceived();
        genericMessagesReceived1.setId("id1");
        GenericMessagesReceived genericMessagesReceived2 = new GenericMessagesReceived();
        genericMessagesReceived2.setId(genericMessagesReceived1.getId());
        assertThat(genericMessagesReceived1).isEqualTo(genericMessagesReceived2);
        genericMessagesReceived2.setId("id2");
        assertThat(genericMessagesReceived1).isNotEqualTo(genericMessagesReceived2);
        genericMessagesReceived1.setId(null);
        assertThat(genericMessagesReceived1).isNotEqualTo(genericMessagesReceived2);
    }
}
