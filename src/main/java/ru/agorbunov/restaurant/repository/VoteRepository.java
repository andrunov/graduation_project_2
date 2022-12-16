package ru.agorbunov.restaurant.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class VoteRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Vote save(Vote vote, int userID) {
        vote.setUser(em.getReference(User.class, userID));
        return em.merge(vote);
    }

    public Vote get(int id) {
        return em.find(Vote.class, id);
    }

    @Transactional
    public boolean delete(int id) {

        try {
            Vote ref = em.getReference(Vote.class, id);
            em.remove(ref);
            return true;

        } catch (Exception e2) {
            throw new NotFoundException("Vote with ID=" + id + " not found");
        }
    }

    public List<Vote> getByUser(int id) {
        return em.createNamedQuery(Vote.BY_USER, Vote.class)
                .setParameter("id", id)
                .getResultList();
    }

}
