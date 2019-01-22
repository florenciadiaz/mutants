package ar.test.meli.mutants.controller;

import ar.test.meli.mutants.configuration.ApplicationProperties;
import ar.test.meli.mutants.persistence.VerifiedSequenceRepository;
import ar.test.meli.mutants.service.DNAVerificationStatsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DNAVerificationStatsController.class)
@ActiveProfiles("test")
class DNAVerificationStatsControllerIntegrationTest {

    private static final String STATS_URL = "/stats/";

    @TestConfiguration
    static class MutantDetectionControllerTestContextConfiguration {

        @MockBean
        private VerifiedSequenceRepository verifiedSequenceRepository;

        @Bean
        public DNAVerificationStatsService dnaVerificationStatsService() {
            return new DNAVerificationStatsService(verifiedSequenceRepository);
        }

        @Bean
        public ApplicationProperties properties() {
            return new ApplicationProperties();
        }
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    ApplicationContext context;

    @Test
    void getStats_givenMutantsAndHumans_mustReturnOKStatus() throws Exception {
        VerifiedSequenceRepository verifiedSequences = context.getBean(VerifiedSequenceRepository.class);
        Mockito.when(verifiedSequences.countByIsMutantIsTrue()).thenReturn(40L);
        Mockito.when(verifiedSequences.countByIsMutantIsFalse()).thenReturn(100L);
        MockHttpServletRequestBuilder requestBuilder = get(STATS_URL);

        MvcResult resultActions = mvc.perform(requestBuilder)
                .andExpect(request().asyncStarted())
                .andReturn();

        mvc.perform(asyncDispatch(resultActions))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").exists())
                .andExpect(jsonPath("$.count_mutant_dna").value(40L))
                .andExpect(jsonPath("$.count_human_dna").exists())
                .andExpect(jsonPath("$.count_human_dna").value(100L))
                .andExpect(jsonPath("$.ratio").exists())
                .andExpect(jsonPath("$.ratio").value(0.40f));
    }
}