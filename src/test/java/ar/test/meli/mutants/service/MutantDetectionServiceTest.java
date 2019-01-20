package ar.test.meli.mutants.service;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import ar.test.meli.mutants.persistence.VerifiedSequence;
import ar.test.meli.mutants.persistence.VerifiedSequenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class MutantDetectionServiceTest {

    @Mock
    private VerifiedSequenceRepository verifiedSequences;

    @BeforeEach
    void setUp() {
        this.verifiedSequences = Mockito.mock(VerifiedSequenceRepository.class);
    }

    @Test
    void verify_givenMutantDNA_mustBeTrue() throws InvalidSequenceException {
        String[] mutantDna = {"ATGCGA","CAGTGC","TTCTGT","AGAATG","CCCCTA","TCACTG"};
        MutantDetectionService service = new MutantDetectionService(this.verifiedSequences);

        boolean actual = service.verify(mutantDna);

        assertTrue(actual);
        verify(this.verifiedSequences, times(1)).save(Mockito.any());
    }

    @Test
    void verify_givenHumanDNA_mustBeFalse() throws InvalidSequenceException {
        String[] humanDna = {"ATGCGA","CAGTGC","TTCTGT","AGAATG","CACCTA","TCACTG"};
        MutantDetectionService service = new MutantDetectionService(this.verifiedSequences);

        boolean actual = service.verify(humanDna);

        assertFalse(actual);
        verify(this.verifiedSequences, times(1)).save(Mockito.any());
    }

    @Test
    void verify_givenInvalidDNA_mustThrowException() {
        String[] invalidDNA = {"ATGCGA","CAGTGC","TXCTGT","AGAATG","CACCTA","TCACTG"};
        MutantDetectionService service = new MutantDetectionService(this.verifiedSequences);

        Throwable exception = assertThrows(InvalidSequenceException.class, () -> service.verify(invalidDNA));

        assertEquals("DNA sequence must contain only valid nitrogenous bases", exception.getMessage());
        verify(this.verifiedSequences, never()).save(Mockito.any());
    }

    @Test
    void verify_givenDuplicatedDNA_mustOverwritePrevious() throws InvalidSequenceException {
        String[] humanDna = {"ATGCGA","CAGTGC","TTCTGT","AGAATG","CACCTA","TCACTG"};
        String sequence = Arrays.toString(humanDna);
        VerifiedSequence originalSequence = mock(VerifiedSequence.class);
        when(originalSequence.getIsMutant()).thenReturn(true).thenCallRealMethod();
        when(this.verifiedSequences.findBySequence(sequence)).thenReturn(originalSequence);
        MutantDetectionService service = new MutantDetectionService(this.verifiedSequences);

        boolean actual = service.verify(humanDna);

        assertFalse(actual);
        verify(this.verifiedSequences, times(1)).save(Mockito.any());
        verify(this.verifiedSequences, times(1)).findBySequence(sequence);
        verify(originalSequence, times(1)).updateIsMutant(false);
    }
}