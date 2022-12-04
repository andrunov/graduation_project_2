package ru.agorbunov.restaurant.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.Vote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class VoteRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Vote save(Vote vote) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }



        /*
        Query query = em.createQuery("DELETE FROM User u WHERE u.id=:id");
        return query.setParameter("id", id).executeUpdate() != 0;

        return em.createNamedQuery(User.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;

         */
    }

    public List<Vote> getByUser(int userId) {
        return em.createNamedQuery(Vote.BY_USER, Vote.class)
                .setParameter("id", userId)
                .getResultList();
    }

}
