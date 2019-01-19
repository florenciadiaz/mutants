package ar.test.meli.mutants.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifiedSequenceRepository extends CrudRepository<VerifiedSequence, Long> {

    VerifiedSequence findBySequence(String sequence);
}
