package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> sender = getById(fromId);
        Optional<Account> recipient = getById(toId);
        if (sender.isEmpty()
                || recipient.isEmpty()
                || sender.get().amount() < amount) {
            return false;
        }
        update(new Account(sender.get().id(), sender.get().amount() - amount));
        update(new Account(recipient.get().id(), recipient.get().amount() + amount));
        return true;
    }
}
