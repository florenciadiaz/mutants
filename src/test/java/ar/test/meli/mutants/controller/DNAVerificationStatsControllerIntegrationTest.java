package ar.test.meli.mutants.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DNAVerificationStatsController.class)
class DNAVerificationStatsControllerIntegrationTest {

    private static final String MUTANT_URL = "/stats/";

    @Autowired
    private MockMvc mvc;

}