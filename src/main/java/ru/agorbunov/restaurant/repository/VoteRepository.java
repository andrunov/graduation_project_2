package ru.agorbunov.restaurant.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.Vote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = true)
public class VoteRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Vote save(Vote vote) {
        if (vote.isNew()) {
            em.persist(vote);
            return vote;
        } else {
            return em.merge(vote);
        }
    }

    public Vote get(int id) {
        return em.find(Vote.class, id);
    }

}
