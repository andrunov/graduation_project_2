package ru.agorbunov.restaurant.repository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<Vote> getByRestaurant(int id) {
        return em.createNamedQuery(Vote.BY_RESTAURANT, Vote.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Vote> getByRestaurantAndDate(int id, LocalDate date) {
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();
        return em.createNamedQuery(Vote.BY_RESTAURANT_DATE, Vote.class)
                .setParameter("id", id)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
    }

    public Vote getByUserAndDate(int id, LocalDate date) {
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();
        List<Vote> votes = em.createNamedQuery(Vote.BY_USER_DATE, Vote.class)
                .setParameter("id", id)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();

        return DataAccessUtils.singleResult(votes);
    }

    public List<Vote> getByUserAndRestaurant(int userId, int restaurantId) {
        return em.createNamedQuery(Vote.BY_USER_RESTAURANT, Vote.class)
                .setParameter("user_id", userId)
                .setParameter("restaurant_id", restaurantId)
                .getResultList();
    }



}
