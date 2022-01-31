package vehiclerental;

import java.time.LocalTime;

public class Car implements Rentable {
    private String id;
    private LocalTime rentingTime;
    private int rentPrice;

    public Car(String id, int rentPrice) {
        this.id = id;
        this.rentPrice = rentPrice;
    }

    @Override
    public int calculateSumPrice(long minutes) {
        return (int) minutes * rentPrice;
    }

    @Override
    public LocalTime getRentingTime() {
        return rentingTime;
    }

    @Override
    public void rent(LocalTime time) {
        rentingTime = time;
    }

    @Override
    public void closeRent() {
        rentingTime = null;
    }
}
