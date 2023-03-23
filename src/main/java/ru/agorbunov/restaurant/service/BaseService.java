package ru.agorbunov.restaurant.service;

import ru.agorbunov.restaurant.repository.BaseRepository;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

public abstract class BaseService<T extends BaseRepository<M>, M> {

    protected final T repository;

    public BaseService(T repository) {
        this.repository = repository;
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    public M get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public M getExisted(int id) {
        return repository.getExisted(id);
    }
}
