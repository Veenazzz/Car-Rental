package Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarRentalSystem {
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

        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            try {
                System.out.println("\n===== Car Rental System =====");
                System.out.println("1. Rent a Car");
                System.out.println("2. Return a Car");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine()); // Parse input
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    continue;
                }
                
                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    continue;
                }
    
                if (choice == 1) {
                    // Rent a Car
                    if (cars.isEmpty()) {
                        System.out.println("No cars are available in the system.");
                        continue;
                    }
    
                    System.out.println("\n== Rent a Car ==\n");
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();
    
                    System.out.println("\nAvailable Cars:");
                    boolean hasAvailableCars = false;
                    for (Car car : cars) {
                        if (car.isAvailable()) {
                            hasAvailableCars = true;
                            System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                        }
                    }
    
                    if (!hasAvailableCars) {
                        System.out.println("No cars are currently available for rent.");
                        continue;
                    }
    
                    System.out.print("\nEnter the car ID you want to rent: ");
                    String carId = scanner.nextLine();
    
                    Car selectedCar = null;
                    for (Car car : cars) {
                        if (car.getCarId().equals(carId) && car.isAvailable()) {
                            selectedCar = car;
                            break;
                        }
                    }
                    if (selectedCar == null) {
                        System.out.println("Invalid car ID or car is not available.");
                        continue;
                    }
    
                    System.out.print("Enter the number of rental days: ");
                    int rentalDays;
                    try {
                        rentalDays = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter a valid number of days.");
                        continue;
                    }
    
                    if (rentalDays <= 0) {
                        System.out.println("Rental days must be a positive number.");
                        continue;
                    }
    
                    Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                    addCustomer(newCustomer);
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
    
                    System.out.println("\n== Rental Information ==\n");
                    System.out.printf("Total Price: $%.2f%n", totalPrice);
                    System.out.print("Confirm rental (Y/N): ");
                    String confirm = scanner.nextLine();
    
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("Car rented successfully.");
                    } else {
                        System.out.println("Rental canceled.");
                    }
    
                } else if (choice == 2) {
                    // Return a Car
                    System.out.println("\n== Return a Car ==\n");
                    System.out.print("Enter the car ID you want to return: ");
                    String carId = scanner.nextLine();
    
                    Car carToReturn = null;
                    for (Car car : cars) {
                        if (car.getCarId().equals(carId) && !car.isAvailable()) {
                            carToReturn = car;
                            break;
                        }
                    }
                    if (carToReturn == null) {
                        System.out.println("Invalid car ID or car is not rented.");
                        continue;
                    }
    
                    returnCar(carToReturn);
                    System.out.println("Car returned successfully.");
                } else if (choice == 3) {
                    break;
                }
    
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    
        System.out.println("\nThank you for using the Car Rental System!");
    }
    
}

