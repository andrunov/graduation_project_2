package ru.agorbunov.restaurant.web.vote;

import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.web.MatcherFactory;

import java.time.LocalDateTime;
import java.time.Month;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);

    public static final int VOTE_01_ID = 1;
    public static final int NOT_FOUND_ID = 100000;
    public static final Vote VOTE_01 = new Vote(1, LocalDateTime.of(2022, Month.DECEMBER, 14, 11, 35, 0, 0), null, null );
    public static final Vote VOTE_02 = new Vote(LocalDateTime.of(2022, Month.DECEMBER, 15, 10, 15, 0, 0), null, null );
    public static final Vote VOTE_03 = new Vote(LocalDateTime.of(2022, Month.DECEMBER, 16, 11, 18, 0, 0), null, null );

    public static final Vote VOTE_04 = new Vote(LocalDateTime.of(2022, Month.DECEMBER, 14, 10, 25, 0, 0), null, null );
    public static final Vote VOTE_05 = new Vote(LocalDateTime.of(2022, Month.DECEMBER, 15, 10, 17, 0, 0), null, null );
    public static final Vote VOTE_06 = new Vote(LocalDateTime.of(2022, Month.DECEMBER, 16, 11, 18, 0, 0), null, null );

}
