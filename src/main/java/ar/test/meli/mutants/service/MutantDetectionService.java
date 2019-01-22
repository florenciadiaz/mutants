package ar.test.meli.mutants.service;

import ar.test.meli.mutants.model.Detector;
import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import ar.test.meli.mutants.persistence.VerifiedSequence;
import ar.test.meli.mutants.persistence.VerifiedSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class MutantDetectionService {

    private final VerifiedSequenceRepository verifiedSequences;

    @Autowired
    public MutantDetectionService(VerifiedSequenceRepository verifiedSequences) {
        this.verifiedSequences = verifiedSequences;
    }

    public boolean verify(String[] dna, int minNBRepetitionToVerifyMutant, int maxNBSequenceLength)
            throws InvalidSequenceException {
        String sequence = String.join(",", dna);
        validateInputSequence(sequence, maxNBSequenceLength);

        Detector detector = new Detector(minNBRepetitionToVerifyMutant);
        boolean isMutant = detector.isMutant(dna);

        persistVerifiedSequence(sequence, isMutant);
        return isMutant;
    }

    private void validateInputSequence(String sequence, int maxNBSequenceLength) throws InvalidSequenceException {
        int maxNbSequenceSize  = calculateMaxNBPerRow(maxNBSequenceLength);
        if (sequence.length() > maxNbSequenceSize) {
            throw new InvalidSequenceException(String.format("DNA sequence must have a maximum of %d " +
                    "nitrogenous bases per row", maxNBSequenceLength));
        }
    }

    private int calculateMaxNBPerRow(int maxNbSequenceLength) {
        return BigDecimal.valueOf((Math.pow(maxNbSequenceLength, 2) + maxNbSequenceLength)).intValue();
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
