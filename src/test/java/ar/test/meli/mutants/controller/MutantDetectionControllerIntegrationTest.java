package ar.test.meli.mutants.controller;

import ar.test.meli.mutants.configuration.ApplicationProperties;
import ar.test.meli.mutants.persistence.VerifiedSequenceRepository;
import ar.test.meli.mutants.service.MutantDetectionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MutantDetectionController.class)
@ActiveProfiles("test")
class MutantDetectionControllerIntegrationTest {

    private static final String MUTANT_URL = "/mutant/";

    private final ObjectMapper mapper = new ObjectMapper();

    @TestConfiguration
    static class MutantDetectionControllerTestContextConfiguration {

        @MockBean
        private VerifiedSequenceRepository verifiedSequenceRepository;

        @Bean
        public MutantDetectionService mutantDetectionService() {
            return new MutantDetectionService(verifiedSequenceRepository);
        }

        @Bean
        public ApplicationProperties properties() {
            return new ApplicationProperties();
        }
    }

    @Autowired
    private MockMvc mvc;

    @Test
    void detectMutant_givenMutantDNA_mustReturnOKStatus() throws Exception {
        String[] dna = {"ATGCGA","CAGTGC","TTCTGT","AGAATG","CCCCTA","TCACTG"};
        DNASampleRequest dnaSampleRequest = new DNASampleRequest(dna);
        MockHttpServletRequestBuilder requestBuilder = post(MUTANT_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(dnaSampleRequest));

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(MessageResponse.SUCCESS_STATUS))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void detectMutant_givenHumanDNA_mustReturnForbiddenStatus() throws Exception {
        String[] dna = {"ATGCGA","CAGTGC","TTCTGT","AGAATG","CACCTA","TCACTG"};
        DNASampleRequest dnaSampleRequest = new DNASampleRequest(dna);
        MockHttpServletRequestBuilder requestBuilder = post(MUTANT_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(dnaSampleRequest));

        mvc.perform(requestBuilder)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(MessageResponse.SUCCESS_STATUS))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void detectMutant_givenInvalidDNA_mustReturnBadRequestStatus() throws Exception {
        String[] dna = {"ATGCGA","CAGTGC","TXCTGT","AGAATG","CACCTA","TCACTG"};
        DNASampleRequest dnaSampleRequest = new DNASampleRequest(dna);
        MockHttpServletRequestBuilder requestBuilder = post(MUTANT_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(dnaSampleRequest));

        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(MessageResponse.ERROR_STATUS))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }
}