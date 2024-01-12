package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

/*Аннотация говорит пользователям данного класса, что класс можно использовать в многопоточном режиме и он будет работать правильно.*/
@ThreadSafe
public class AccountStorage {
    /*выставляется над общим ресурсом. Аннотация имеет входящий параметр.
     Он указывает на монитор, по которому мы будем синхронизироваться.*/
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.put(account.id(), account) == account;
    }

    public synchronized boolean update(Account account) {
        return accounts.put(account.id(), account) == account;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        if (!accounts.containsKey(id)) {
            throw new IllegalStateException("Not found account by id = " + id);
        }
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        int amountFrom = this.getById(fromId).get().amount() - amount;
        int amountTo = this.getById(toId).get().amount() + amount;
        accounts.put(fromId, new Account(fromId, amountFrom));
        accounts.put(toId, new Account(toId, amountTo));
    }
}