package ru.agorbunov.restaurant.web.vote;

import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.web.MatcherFactory;

import java.time.LocalDateTime;
import java.time.Month;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);

    public static final int VOTE_01_ID = 1;
    public static final int VOTE_09_ID = 9;
    public static final int VOTE_NOT_FOUND_ID = 100000;
    public static final Vote VOTE_01 = new Vote(1, LocalDateTime.of(2022, Month.DECEMBER, 14, 11, 35, 0, 0), null, null );
    public static final Vote VOTE_02 = new Vote(2, LocalDateTime.of(2022, Month.DECEMBER, 15, 10, 15, 0, 0), null, null );
    public static final Vote VOTE_03 = new Vote(3, LocalDateTime.of(2022, Month.DECEMBER, 16, 11, 18, 0, 0), null, null );

    public static final Vote VOTE_04 = new Vote(4,LocalDateTime.of(2022, Month.DECEMBER, 14, 10, 25, 0, 0), null, null );
    public static final Vote VOTE_05 = new Vote(5, LocalDateTime.of(2022, Month.DECEMBER, 15, 10, 17, 0, 0), null, null );
    public static final Vote VOTE_06 = new Vote(6, LocalDateTime.of(2022, Month.DECEMBER, 16, 11, 18, 0, 0), null, null );
    public static final Vote VOTE_08 = new Vote(8, LocalDateTime.of(2022, Month.DECEMBER, 15, 10, 15, 0, 0), null, null );
    public static final Vote VOTE_09 = new Vote(9, LocalDateTime.now(), null, null );
    public static final Vote VOTE_12 = new Vote(12, LocalDateTime.now(), null, null );
    public static final Vote VOTE_16 = new Vote(16,  LocalDateTime.of(2022, Month.DECEMBER, 14, 10, 35, 0, 0), null, null );

}
