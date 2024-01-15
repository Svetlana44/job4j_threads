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
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.put(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    /*необходимо найти 2 аккаунта, проверить что это не пустые Optional,
     проверить баланс аккаунта донора
     и только после этого переназначить балансы аккаунтов*/
    public synchronized void transfer(int fromId, int toId, int amount) {

        Optional<Account> fromAcc = this.getById(fromId);
        Optional<Account> toAcc = this.getById(toId);

        if (fromAcc.isEmpty()) {
            throw new IllegalStateException("Not found account by id = " + fromId);
        }
        if (toAcc.isEmpty()) {
            throw new IllegalStateException("Not found account by id = " + toId);
        }
        if (fromAcc.get().amount() < amount) {
            throw new IllegalStateException("The account has insufficient funds.");
        }
        int amountFrom = fromAcc.get().amount() - amount;
        int amountTo = toAcc.get().amount() + amount;
        accounts.put(fromId, new Account(fromId, amountFrom));
        accounts.put(toId, new Account(toId, amountTo));
    }
}