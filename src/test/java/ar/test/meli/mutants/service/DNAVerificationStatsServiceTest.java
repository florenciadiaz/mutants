package ar.test.meli.mutants.service;

import ar.test.meli.mutants.persistence.VerifiedSequenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DNAVerificationStatsServiceTest {

    @Mock
    private VerifiedSequenceRepository verifiedSequences;

    @BeforeEach
    void setUp() {
        this.verifiedSequences = Mockito.mock(VerifiedSequenceRepository.class);
    }

    @Test
    void countMutants_givenNoMutants_mustBeZero() {
        when(this.verifiedSequences.countByIsMutantIsTrue()).thenReturn(0L);
        DNAVerificationStatsService service = new DNAVerificationStatsService(this.verifiedSequences);

        Long actual = service.countMutants();

        assertThat(actual, is(0L));
        verify(this.verifiedSequences, times(1)).countByIsMutantIsTrue();
    }

    @Test
    void countHumans_givenNoHumans_mustBeZero() {
        when(this.verifiedSequences.countByIsMutantIsFalse()).thenReturn(0L);
        DNAVerificationStatsService service = new DNAVerificationStatsService(this.verifiedSequences);

        Long actual = service.countHumans();

        assertThat(actual, is(0L));
        verify(this.verifiedSequences, times(1)).countByIsMutantIsFalse();
    }

    @Test
    void countMutants_givenMutants_mustBeCount() {
        Long expected = 1432L;
        when(this.verifiedSequences.countByIsMutantIsTrue()).thenReturn(expected);
        DNAVerificationStatsService service = new DNAVerificationStatsService(this.verifiedSequences);

        Long actual = service.countMutants();

        assertThat(actual, is(expected));
        verify(this.verifiedSequences, times(1)).countByIsMutantIsTrue();
    }

    @Test
    void countHumans_givenHumans_mustBeCount() {
        Long expected = 1432L;
        when(this.verifiedSequences.countByIsMutantIsFalse()).thenReturn(expected);
        DNAVerificationStatsService service = new DNAVerificationStatsService(this.verifiedSequences);

        Long actual = service.countHumans();

        assertThat(actual, is(expected));
        verify(this.verifiedSequences, times(1)).countByIsMutantIsFalse();
    }

    @Test
    void calculateRatio_givenHumansAndNoMutants_mustBeZero() {
        Long mutants = 0L;
        Long humans = 100L;
        DNAVerificationStatsService service = new DNAVerificationStatsService(this.verifiedSequences);

        float actual = service.calculateRatio(mutants, humans, (short) 2);

        assertThat(actual, is(0f));
    }

    @Test
    void calculateRatio_givenMutantsAndNoHumans_mustBeOne() {
        Long mutants = 100L;
        Long humans = 0L;
        DNAVerificationStatsService service = new DNAVerificationStatsService(this.verifiedSequences);

        float actual = service.calculateRatio(mutants, humans, (short) 2);

        assertThat(actual, is(1f));
    }

    @Test
    void calculateRatio_givenMoreMutantsThanHumans_mustBeGreaterThanOne() {
        Long mutants = 100L;
        Long humans = 40L;
        DNAVerificationStatsService service = new DNAVerificationStatsService(this.verifiedSequences);

        float actual = service.calculateRatio(mutants, humans, (short) 2);

        assertThat(actual, greaterThan(1f));
    }

    @Test
    void calculateRatio_givenMoreHumansThanMutants_mustBeLessThanOne() {
        Long mutants = 40L;
        Long humans = 100L;
        DNAVerificationStatsService service = new DNAVerificationStatsService(this.verifiedSequences);

        float actual = service.calculateRatio(mutants, humans, (short) 2);

        assertThat(actual, lessThan(1f));
    }
}