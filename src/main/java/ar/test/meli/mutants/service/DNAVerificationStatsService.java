package ar.test.meli.mutants.service;

import ar.test.meli.mutants.configuration.ApplicationProperties;
import ar.test.meli.mutants.controller.DNAVerificationStatsResponse;
import ar.test.meli.mutants.persistence.VerifiedSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DNAVerificationStatsService {

    private final VerifiedSequenceRepository verifiedSequences;
    private final ApplicationProperties properties;

    @Autowired
    public DNAVerificationStatsService(VerifiedSequenceRepository verifiedSequences, ApplicationProperties properties) {
        this.verifiedSequences = verifiedSequences;
        this.properties = properties;
    }

    public DNAVerificationStatsResponse calculateStats() {
        Long mutantsCount = this.verifiedSequences.countByIsMutantIsTrue();
        Long humansCount = this.verifiedSequences.countByIsMutantIsFalse();

        Float ratio = this.calculateRatio(mutantsCount, humansCount);
        return new DNAVerificationStatsResponse(mutantsCount, humansCount, ratio);
    }

    private Float calculateRatio(Long mutantsCount, Long humansCount) {
        double ratio = (humansCount > 0)
                ? (double) mutantsCount / humansCount
                : ((mutantsCount > 0) ? 1 : 0);
        return BigDecimal.valueOf(ratio)
                    .setScale(properties.getStats().getRatioDecimalPlaces(), BigDecimal.ROUND_HALF_UP)
                    .floatValue();
    }
}
