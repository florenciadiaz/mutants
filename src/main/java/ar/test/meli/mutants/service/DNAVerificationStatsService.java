package ar.test.meli.mutants.service;

import ar.test.meli.mutants.persistence.VerifiedSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DNAVerificationStatsService {

    private final VerifiedSequenceRepository verifiedSequences;

    @Autowired
    public DNAVerificationStatsService(VerifiedSequenceRepository verifiedSequences) {
        this.verifiedSequences = verifiedSequences;
    }

    public Long countMutants() {
        return this.verifiedSequences.countByIsMutantIsTrue();
    }

    public Long countHumans() {
        return this.verifiedSequences.countByIsMutantIsFalse();
    }

    public Float calculateRatio(Long mutantsCount, Long humansCount, short decimalPlaces) {
        double ratio = (humansCount > 0)
                ? (double) mutantsCount / humansCount
                : ((mutantsCount > 0) ? 1 : 0);
        return BigDecimal.valueOf(ratio)
                .setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP)
                .floatValue();
    }
}
