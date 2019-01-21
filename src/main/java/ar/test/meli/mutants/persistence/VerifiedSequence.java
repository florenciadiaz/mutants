package ar.test.meli.mutants.persistence;

import javax.persistence.*;

@Entity
public class VerifiedSequence {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "varchar(" + Short.MAX_VALUE + ")", unique = true)
    private String sequence;
    private boolean isMutant;

    protected VerifiedSequence() { }

    public VerifiedSequence(String sequence, boolean isMutant) {
        this.sequence = sequence;
        this.isMutant = isMutant;
    }

    public String getSequence() {
        return sequence;
    }

    public boolean getIsMutant() {
        return isMutant;
    }

    public void updateIsMutant(boolean isMutant) {
        this.isMutant = isMutant;
    }
}
