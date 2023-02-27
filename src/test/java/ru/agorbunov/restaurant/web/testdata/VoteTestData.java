package ru.agorbunov.restaurant.web.testdata;

import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.service.testdata.ModelMatcher;

import java.time.LocalDateTime;
import java.time.Month;

public class VoteTestData {

    public static final ModelMatcher<Vote> MATCHER = new ModelMatcher<>();

    public static final Vote VOTE_01 = new Vote(LocalDateTime.of(2022, Month.DECEMBER, 14, 10, 25, 0, 0), null, null );
    public static final Vote VOTE_02 = new Vote(LocalDateTime.of(2022, Month.DECEMBER, 15, 10, 17, 0, 0), null, null );
    public static final Vote VOTE_03 = new Vote(LocalDateTime.now(), null, null );
}
