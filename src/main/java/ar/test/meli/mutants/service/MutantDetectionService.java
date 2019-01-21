package ar.test.meli.mutants.service;

import ar.test.meli.mutants.model.Detector;
import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import ar.test.meli.mutants.persistence.VerifiedSequence;
import ar.test.meli.mutants.persistence.VerifiedSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
public class MutantDetectionService {

    private final VerifiedSequenceRepository verifiedSequences;

    @Autowired
    public MutantDetectionService(VerifiedSequenceRepository verifiedSequences) {
        this.verifiedSequences = verifiedSequences;
    }

    public boolean verify(String[] dna, int minNBRepetitionToVerifyMutant, int maxNbSequenceLength)
            throws InvalidSequenceException {
        String sequence = String.join(",", dna);
        validateInputSequence(sequence, maxNbSequenceLength);

        Detector detector = new Detector(minNBRepetitionToVerifyMutant);
        boolean isMutant = detector.isMutant(dna);

        persistVerifiedSequence(sequence, isMutant);
        return isMutant;
    }

    private void validateInputSequence(String sequence, int maxNbSequenceLength) throws InvalidSequenceException {
        if (sequence.length() > maxNbSequenceLength) {
            throw new InvalidSequenceException(String.format("DNA sequence must have a maximum of %d " +
                    "nitrogenous bases per row", calculateMaxNBPerRow(maxNbSequenceLength)));
        }
    }

    private int calculateMaxNBPerRow(int maxNbSequenceLength) {
        BigDecimal rowLength = BigDecimal.valueOf(Math.sqrt(maxNbSequenceLength));
        BigDecimal value = BigDecimal.valueOf(rowLength.intValue());
        int maxValue = value.pow(2).add(value.subtract(BigDecimal.valueOf(1))).intValue();
        return value.subtract(BigDecimal.valueOf((maxValue > maxNbSequenceLength) ? 1 : 0)).intValue();
    }

    private void persistVerifiedSequence(String sequence, boolean isMutant) {
        VerifiedSequence verifiedSequence = this.verifiedSequences.findBySequence(sequence);
        if (verifiedSequence == null) {
           verifiedSequence = new VerifiedSequence(sequence, isMutant);
        }
        verifiedSequence.updateIsMutant(isMutant);
        this.verifiedSequences.save(verifiedSequence);
    }
}
