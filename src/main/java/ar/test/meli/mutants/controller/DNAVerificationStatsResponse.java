package ar.test.meli.mutants.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

class DNAVerificationStatsResponse {

    private final Long mutantDNACount;
    private final Long humanDNACount;
    private final Float ratio;

    DNAVerificationStatsResponse(Long mutantDNACount, Long humanDNACount, Float ratio) {
        this.mutantDNACount = mutantDNACount;
        this.humanDNACount = humanDNACount;
        this.ratio = ratio;
    }

    @JsonProperty(value = "count_mutant_dna")
    public Long getMutantDNACount() {
        return mutantDNACount;
    }

    @JsonProperty(value = "count_human_dna")
    public Long getHumanDNACount() {
        return humanDNACount;
    }

    @JsonProperty(value = "ratio")
    public Float getRatio() {
        return ratio;
    }
}
