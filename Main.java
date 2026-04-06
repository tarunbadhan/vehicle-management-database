import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;

public class Main {

    static String url = "jdbc:sqlite:vehicle.db";

    public static void main(String[] args) {

        setupDatabase(); // auto setup

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Vehicle Management System ---");
            System.out.println("1. View Vehicles");
            System.out.println("2. Add Vehicle");
            System.out.println("3. Update Vehicle Color");
            System.out.println("4. Delete Vehicle");
            System.out.println("5. View Owners");
            System.out.println("6. View Service Records");
            System.out.println("7. Add Service Record");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1: viewVehicles(); break;
                case 2: addVehicle(sc); break;
                case 3: updateVehicle(sc); break;
                case 4: deleteVehicle(sc); break;
                case 5: viewOwners(); break;
                case 6: viewServiceRecords(); break;
                case 7: addServiceRecord(sc); break;
                case 8: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice.");
            }

        } while (choice != 8);

        sc.close();
    }

    // ---------- DATABASE SETUP ----------
    public static void setupDatabase() {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();

            stmt.execute("DROP TABLE IF EXISTS ServiceRecord");
            stmt.execute("DROP TABLE IF EXISTS Vehicle");
            stmt.execute("DROP TABLE IF EXISTS Owner");

            stmt.execute("CREATE TABLE Owner (owner_id INT PRIMARY KEY, full_name TEXT, phone TEXT, email TEXT, address TEXT)");
            stmt.execute("CREATE TABLE Vehicle (vehicle_id INT PRIMARY KEY, owner_id INT, plate_number TEXT, brand TEXT, model TEXT, year INT, color TEXT, FOREIGN KEY(owner_id) REFERENCES Owner(owner_id))");
            stmt.execute("CREATE TABLE ServiceRecord (service_id INT PRIMARY KEY, vehicle_id INT, service_date TEXT, service_type TEXT, cost REAL, notes TEXT, FOREIGN KEY(vehicle_id) REFERENCES Vehicle(vehicle_id))");

            stmt.execute("INSERT INTO Owner VALUES (1,'Tarun','5061112222','tarun@email.com','Saint John')");
            stmt.execute("INSERT INTO Owner VALUES (2,'Alex','5063334444','alex@email.com','Fredericton')");

            stmt.execute("INSERT INTO Vehicle VALUES (101,1,'NBX123','Toyota','Corolla',2020,'Black')");
            stmt.execute("INSERT INTO Vehicle VALUES (102,1,'NBY456','Honda','Civic',2022,'White')");
            stmt.execute("INSERT INTO Vehicle VALUES (103,2,'NBZ789','Ford','Escape',2019,'Blue')");

            stmt.execute("INSERT INTO ServiceRecord VALUES (1,101,'2026-03-01','Oil Change',79.99,'Done')");
            stmt.execute("INSERT INTO ServiceRecord VALUES (2,101,'2026-03-15','Brake Check',49.99,'Checked')");

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ---------- VEHICLE ----------
    public static void viewVehicles() {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Vehicle");
    
            System.out.println("\n--- Vehicles ---");
    
            // HEADINGS
            System.out.printf("%-5s %-10s %-10s %-10s %-6s %-10s\n",
                    "ID", "Plate", "Brand", "Model", "Year", "Color");
            System.out.println("--------------------------------------------------------------");
    
            // DATA
            while (rs.next()) {
                System.out.printf("%-5d %-10s %-10s %-10s %-6d %-10s\n",
                        rs.getInt("vehicle_id"),
                        rs.getString("plate_number"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getString("color"));
            }
    
            conn.close();
    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addVehicle(Scanner sc) {
        try {
            Connection conn = DriverManager.getConnection(url);

            System.out.print("Vehicle ID: ");
            int id = sc.nextInt(); sc.nextLine();

            // Show available owners
            System.out.println("\nAvailable Owners:");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Owner");
            
            while (rs.next()) {
                System.out.println(
                    rs.getInt("owner_id") + " | " +
                    rs.getString("full_name")
                );
            }
            
            System.out.print("Owner ID: ");
            int ownerId = sc.nextInt(); sc.nextLine();

            String plate = "NB" + id;

            System.out.print("Brand: ");
            String brand = sc.nextLine();

            System.out.print("Model: ");
            String model = sc.nextLine();

            System.out.print("Year: ");
            int year = sc.nextInt(); sc.nextLine();

            System.out.print("Color: ");
            String color = sc.nextLine();

            PreparedStatement ps = conn.prepareStatement("INSERT INTO Vehicle VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setInt(2, ownerId);
            ps.setString(3, plate);
            ps.setString(4, brand);
            ps.setString(5, model);
            ps.setInt(6, year);
            ps.setString(7, color);

            ps.executeUpdate();
            System.out.println("Vehicle added.");

            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateVehicle(Scanner sc) {
        try {
            Connection conn = DriverManager.getConnection(url);

            System.out.print("Vehicle ID: ");
            int id = sc.nextInt(); sc.nextLine();

            System.out.print("New Color: ");
            String color = sc.nextLine();

            PreparedStatement ps = conn.prepareStatement("UPDATE Vehicle SET color=? WHERE vehicle_id=?");
            ps.setString(1, color);
            ps.setInt(2, id);

            ps.executeUpdate();
            System.out.println("Updated.");

            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteVehicle(Scanner sc) {
        try {
            Connection conn = DriverManager.getConnection(url);

            System.out.print("Vehicle ID: ");
            int id = sc.nextInt(); sc.nextLine();

            PreparedStatement ps = conn.prepareStatement("DELETE FROM Vehicle WHERE vehicle_id=?");
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Deleted.");

            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ---------- OWNER ----------
    public static void viewOwners() {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Owner");
    
            System.out.println("\n--- Owners ---");
    
            // HEADINGS
            System.out.printf("%-5s %-15s %-15s %-25s %-15s\n",
                    "ID", "Name", "Phone", "Email", "Address");
            System.out.println("--------------------------------------------------------------------------");
    
            // DATA
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-15s %-25s %-15s\n",
                        rs.getInt("owner_id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"));
            }
    
            conn.close();
    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ---------- SERVICE ----------
    public static void viewServiceRecords() {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ServiceRecord");
    
            System.out.println("\n--- Service Records ---");
            System.out.printf("%-5s %-10s %-12s %-15s %-10s %-20s\n",
                    "ID", "Vehicle", "Date", "Service", "Cost", "Notes");
            System.out.println("------------------------------------------------------------------------");
    
            while (rs.next()) {
                System.out.printf("%-5d %-10d %-12s %-15s $%-9.2f %-20s\n",
                        rs.getInt("service_id"),
                        rs.getInt("vehicle_id"),
                        rs.getString("service_date"),
                        rs.getString("service_type"),
                        rs.getDouble("cost"),
                        rs.getString("notes"));
            }
    
            stmt.close();
            conn.close();
    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void addServiceRecord(Scanner sc) {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
    
            // Show available vehicles
            System.out.println("\nAvailable Vehicles:");
            ResultSet rs = stmt.executeQuery("SELECT * FROM Vehicle");
    
            System.out.printf("%-5s %-10s %-10s %-10s\n", "ID", "Plate", "Brand", "Model");
            System.out.println("------------------------------------------------");
    
            while (rs.next()) {
                System.out.printf("%-5d %-10s %-10s %-10s\n",
                        rs.getInt("vehicle_id"),
                        rs.getString("plate_number"),
                        rs.getString("brand"),
                        rs.getString("model"));
            }
    
            // Ask for vehicle ID
            System.out.print("Enter Vehicle ID: ");
            int vehicleId = sc.nextInt();
            sc.nextLine();
    
            // Validate vehicle exists
            ResultSet check = stmt.executeQuery(
                    "SELECT * FROM Vehicle WHERE vehicle_id = " + vehicleId
            );
    
            if (!check.next()) {
                System.out.println("Invalid Vehicle ID.");
                conn.close();
                return;
            }
    
            // Show service options
            System.out.println("\nSelect Service Type:");
            System.out.println("1. Oil Change - $79.99");
            System.out.println("2. Brake Check - $49.99");
            System.out.println("3. Tire Change - $120.00");
            System.out.println("4. Battery Check - $35.00");
            System.out.print("Enter choice: ");
    
            int serviceChoice = sc.nextInt();
            sc.nextLine();
    
            String serviceType = "";
            double cost = 0.0;
    
            switch (serviceChoice) {
                case 1:
                    serviceType = "Oil Change";
                    cost = 79.99;
                    break;
                case 2:
                    serviceType = "Brake Check";
                    cost = 49.99;
                    break;
                case 3:
                    serviceType = "Tire Change";
                    cost = 120.00;
                    break;
                case 4:
                    serviceType = "Battery Check";
                    cost = 35.00;
                    break;
                default:
                    System.out.println("Invalid service choice.");
                    conn.close();
                    return;
            }
    
            // Auto-generate service ID
            ResultSet idRs = stmt.executeQuery("SELECT MAX(service_id) AS max_id FROM ServiceRecord");
            int newId = 1;
            if (idRs.next() && idRs.getInt("max_id") != 0) {
                newId = idRs.getInt("max_id") + 1;
            }
    
            // Auto-generate current date
            String date = java.time.LocalDate.now().toString();
    
            // Optional notes
            System.out.print("Enter Notes: ");
            String notes = sc.nextLine();
    
            // Insert record
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO ServiceRecord VALUES (?, ?, ?, ?, ?, ?)"
            );
    
            ps.setInt(1, newId);
            ps.setInt(2, vehicleId);
            ps.setString(3, date);
            ps.setString(4, serviceType);
            ps.setDouble(5, cost);
            ps.setString(6, notes);
    
            ps.executeUpdate();
    
            System.out.println("Service record added successfully.");
            System.out.println("Service: " + serviceType);
            System.out.println("Price: $" + String.format("%.2f", cost));
            System.out.println("Date: " + date);
    
            ps.close();
            stmt.close();
            conn.close();
    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
