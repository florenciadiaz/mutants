package ar.test.meli.mutants.service;

import ar.test.meli.mutants.model.Detector;
import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import ar.test.meli.mutants.persistence.VerifiedSequence;
import ar.test.meli.mutants.persistence.VerifiedSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
@Transactional
public class MutantDetectionService {

    @Autowired
    private final VerifiedSequenceRepository verifiedSequences;

    public MutantDetectionService(VerifiedSequenceRepository verifiedSequences) {
        this.verifiedSequences = verifiedSequences;
    }

    public boolean verify(String[] dna) throws InvalidSequenceException {
        Detector detector = new Detector();
        boolean isMutant = detector.isMutant(dna);
        persistVerifiedSequence(dna, isMutant);
        return isMutant;
    }

    private void persistVerifiedSequence(String[] dna, boolean isMutant) {
        String sequence = Arrays.toString(dna);
//        VerifiedSequence verifiedSequence = this.verifiedSequences.findBySequence(sequence);
//        if (verifiedSequence == null) {
           VerifiedSequence verifiedSequence = new VerifiedSequence(sequence, isMutant);
//        }
        this.verifiedSequences.save(verifiedSequence);
    }
}
