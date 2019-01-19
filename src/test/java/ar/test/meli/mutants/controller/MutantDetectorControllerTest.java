package ar.test.meli.mutants.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MutantDetectorController.class)
class MutantDetectorControllerTest {

    private static final String MUTANT_URL = "/mutant/";

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;


    @Test
    void detectMutant_givenMutantDNA_MustReturnOKStatus() throws Exception {
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
    void detectMutant_givenHumanDNA_MustReturnForbiddenStatus() throws Exception {
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
                .andExpect(jsonPath("$.message").isNotEmpty());;
    }

    @Test
    void detectMutant_givenInvalidDNA_MustReturnBadRequestStatus() throws Exception {
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