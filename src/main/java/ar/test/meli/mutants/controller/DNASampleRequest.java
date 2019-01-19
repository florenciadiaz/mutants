package ar.test.meli.mutants.controller;

class DNASampleRequest {

    private String[] dna;

    private DNASampleRequest() {
    }

    DNASampleRequest(String[] dna) {
        this();
        this.dna = dna;
    }

    public String[] getDna() {
        return dna;
    }
}
