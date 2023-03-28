package ru.agorbunov.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.repository.MenuListRepository;
import ru.agorbunov.restaurant.repository.RestaurantRepository;
import ru.agorbunov.restaurant.repository.UserRepository;
import ru.agorbunov.restaurant.repository.VoteRepository;
import ru.agorbunov.restaurant.util.DateTimeUtil;
import ru.agorbunov.restaurant.util.exception.AccessDeniedException;
import ru.agorbunov.restaurant.util.exception.IllegalRequestDataException;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service("voteService")
public class VoteService extends BaseService<VoteRepository, Vote> {

    public static final LocalTime DEADLINE = LocalTime.of(11,0);

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuListRepository menulistRepository;

    public VoteService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, MenuListRepository menulistRepository) {
        super(voteRepository);
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.menulistRepository = menulistRepository;
    }



    public Vote get(int id, int userId) {
        checkBelongings(id, userId);
        return super.get(id);
    }

    public void delete(int id, int userId) {
        checkBelongings(id, userId);
        super.delete(id);
    }


    public Vote create(int userId, int restaurantId, int menuListId, LocalDateTime localDateTime) {
        User user = this.userRepository.get(userId);
        Restaurant restaurant = this.restaurantRepository.get(restaurantId);
        MenuList menuList = this.menulistRepository.get(menuListId);
        Vote vote = new Vote(user, restaurant, menuList, localDateTime);
        checkFields(vote);
        return repository.save(vote);
    }

    public Vote update(int voteId, int userId, int restaurantId, int menuListId, LocalDateTime localDateTime) {
        checkBelongings(voteId, userId);
        Vote vote = this.repository.get(voteId);
        checkDateTime(vote, localDateTime);
        vote.setUser(this.userRepository.get(userId));
        vote.setRestaurant(this.restaurantRepository.get(restaurantId));
        vote.setMenuList(this.menulistRepository.get(menuListId));
        vote.setDateTime(localDateTime);
        checkFields(vote);
        return repository.save(vote);
    }


    public List<Vote> getByUser(int id) {
        return repository.getByUser(id);
    }

    public List<Vote> getByRestaurant(int id) {
        return repository.getByRestaurant(id);
    }

    public List<Vote> getByRestaurantAndDate(int id, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();
        return repository.getByRestaurantAndDate(id, from, to);
    }

    public Vote getByUserAndDate(int id, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();
        return repository.getByUserAndDate(id, from, to);
    }

    public List<Vote> getByUserAndRestaurant(int userId, int restaurantId) {
        return repository.getByUserAndRestaurant(userId, restaurantId);
    }

    public void updateDateTime(int voteId, int userId, LocalDateTime newDateTime) {
        checkBelongings(voteId, userId);
        updateDateTime(voteId, newDateTime);
    }

    public void updateDateTime(int voteId,  LocalDateTime newDateTime) {
        Vote vote = this.repository.getExisted(voteId);
        checkDateTime(vote, newDateTime);
        repository.updateDateTime(voteId, newDateTime);
    }

    private void checkDateTime(Vote vote,  LocalDateTime newDateTime) {
        if (vote.getDateTime().toLocalDate().isBefore(LocalDate.now())) {
            throw new AccessDeniedException("Historical vote, update denied");
        } else {
            LocalDateTime now = LocalDateTime.now();
            if (newDateTime.isAfter(now.with(DEADLINE))) {
                throw new AccessDeniedException("It's too late, " + DateTimeUtil.toString(vote.getDateTime()) + " vote needs to be made before 11:00 o'clock");
            }
        }
    }


    private void checkFields(Vote vote) {
        if (vote.getUser() == null) {
            throw new IllegalRequestDataException("User must be presented");

        } else if (vote.getRestaurant() == null) {
            throw new IllegalRequestDataException("Restaurant must be presented");

        } else if (vote.getMenuList() == null) {
            throw new IllegalRequestDataException("MenuList must be presented");

        }
    }


    private User getFromVote(int voteId) {
        User result = null;
        Optional<User> optional = userRepository.findByVote(voteId);
        if (optional.isPresent()) {
            result = optional.get();
        } else {
            throw new IllegalRequestDataException("not found User for vote id=" + voteId);
        }
        return result;
    }

    private void checkBelongings(int voteId, int userId) {
        User userFromVote = this.getFromVote(voteId);
        if (userFromVote.id() != userId) {
            throw new AccessDeniedException("This vote does not belong to user id =" + userId);
        }
    }


}
