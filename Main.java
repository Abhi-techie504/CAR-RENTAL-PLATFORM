import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
            System.out.println("Car rented successfully for " + days + " days.");
        } else {
            System.out.println("CAR IS NOT AVAILABLE FOR RENT.");
        }
    }

    public void returnCar(Car car) {
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            car.returnCar();
            System.out.println("CAR RETURNED SUCCESSFULLY.");
        } else {
            System.out.println("CAR WAS NOT RENTED.");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== CAR RENTAL SYSTEM =====");
            System.out.println("1. RENT A CAR");
            System.out.println("2. RETURN A CAR");
            System.out.println("3. EXIT");
            System.out.print("ENTER YOUR CHOICE: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\n== RENT A CAR ==\n");
                    System.out.print("ENTER YOUR NAME: ");
                    String customerName = sc.nextLine();

                    System.out.println("\nAVAILABLE CARS:");
                    for (Car car : cars) {
                        if (car.isAvailable()) {
                            System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                        }
                    }

                    System.out.print("\nENTER THE CAR ID YOU WANT TO RENT: ");
                    String carId = sc.nextLine();

                    System.out.print("ENTER THE NUMBER OF DAYS FOR RENTING: ");
                    int rentalDays = sc.nextInt();
                    sc.nextLine();

                    Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                    addCustomer(newCustomer);

                    Car selectedCar = null;
                    for (Car car : cars) {
                        if (car.getCarId().equals(carId) && car.isAvailable()) {
                            selectedCar = car;
                            break;
                        }
                    }

                    if (selectedCar != null) {
                        double totalPrice = selectedCar.calculatePrice(rentalDays);
                        System.out.printf("\n== RENTAL INFORMATION ==\n");
                        System.out.printf("CUSTOMER ID: %s\n", newCustomer.getCustomerId());
                        System.out.printf("CUSTOMER NAME: %s\n", newCustomer.getName());
                        System.out.printf("CAR: %s %s\n", selectedCar.getBrand(), selectedCar.getModel());
                        System.out.printf("RENTAL DAYS: %d\n", rentalDays);
                        System.out.printf("TOTAL PRICE: $%.2f\n", totalPrice);

                        System.out.print("\nCONFIRM RENTAL (Y/N): ");
                        String confirm = sc.nextLine();

                        if (confirm.equalsIgnoreCase("Y")) {
                            rentCar(selectedCar, newCustomer, rentalDays);
                        } else {
                            System.out.println("\nRENTAL CANCELED.");
                        }
                    } else {
                        System.out.println("\nINVALID CAR SELECTION OR CAR IS NOT AVAILABLE.");
                    }
                }
                case 2 -> {
                    System.out.println("\n== RETURN A CAR ==\n");
                    System.out.print("ENTER THE CAR ID YOU WANT TO RETURN: ");
                    String carId = sc.nextLine();

                    Car carToReturn = null;
                    for (Car car : cars) {
                        if (car.getCarId().equals(carId) && !car.isAvailable()) {
                            carToReturn = car;
                            break;
                        }
                    }

                    if (carToReturn != null) {
                        returnCar(carToReturn);
                    } else {
                        System.out.println("INVALID CAR ID OR CAR IS NOT RENTED.");
                    }
                }
                case 3 -> {
                    System.out.println("\nTHANK YOU FOR USING THE CAR RENTAL PLATFORM!");
                    return;
                }
                default -> System.out.println("INVALID CHOICE. PLEASE ENTER A VALID OPTION.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        rentalSystem.addCar(new Car("C001", "Toyota", "Camry", 1000));
        rentalSystem.addCar(new Car("C002", "Honda", "Accord", 1500));
        rentalSystem.addCar(new Car("C003", "Mahindra", "Thar", 2000));

        rentalSystem.menu();
    }
}
