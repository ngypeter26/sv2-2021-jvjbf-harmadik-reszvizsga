package vehiclerental;

import java.time.LocalTime;
import java.util.*;

public class RentService {
    private Set<Rentable> rentables = new HashSet<>();
    private Set<User> users = new HashSet<>();
    private TreeMap<Rentable, User> actualRenting = new TreeMap<>();

    public void addRentable(Rentable rentable) {
        rentables.add(rentable);
    }

    public void registerUser(User user) {
        if (users.contains(user)) {
            throw new UserNameIsAlreadyTakenException("Username is taken!");
        }
        users.add(user);
    }

    public void rent(User user, Rentable rentable, LocalTime time) {
        if (rentable.getRentingTime() != null || user.getBalance() < rentable.calculateSumPrice(3 * 60)) {
            throw new IllegalStateException("Something wrong!");
        }
        rentable.rent(time);
        actualRenting.put(rentable, user);
    }

    public void closeRent(Rentable rentable, int minutes) {

        actualRenting.get(rentable).minusBalance(rentable.calculateSumPrice(minutes));
        actualRenting.remove(rentable);
        rentable.closeRent();
    }

    public Map<Rentable, User> getActualRenting() {
        return actualRenting;
    }

    public Set<Rentable> getRentables() {
        return rentables;
    }

    public Set<User> getUsers() {
        return users;
    }


}
