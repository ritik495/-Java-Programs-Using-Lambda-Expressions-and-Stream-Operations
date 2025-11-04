
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.Optional;

// ===============================================
// PART A: Sorting Employee Objects Using Lambda Expressions
// ===============================================
class Employee {
    String name;
    int age;
    double salary;

    Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("%-10s | Age: %2d | Salary: %.2f", name, age, salary);
    }
}

// ===============================================
// PART B: Filtering and Sorting Students Using Streams
// ===============================================
class Student {
    String name;
    double marks;

    Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return String.format("%-10s | Marks: %.2f", name, marks);
    }
}

// ===============================================
// PART C: Stream Operations on Product Dataset
// ===============================================
class Product {
    String name;
    double price;
    String category;

    Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("%-10s | Category: %-10s | Price: %.2f", name, category, price);
    }
}

// ===============================================
// MAIN CLASS - Combines All Three Parts
// ===============================================
public class LambdaStreamDemo {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println(" PART A: Sorting Employee Objects ");
        System.out.println("====================================");
        partA();

        System.out.println("\n====================================");
        System.out.println(" PART B: Filtering & Sorting Students ");
        System.out.println("====================================");
        partB();

        System.out.println("\n====================================");
        System.out.println(" PART C: Stream Operations on Products ");
        System.out.println("====================================");
        partC();
    }

    // ---------- PART A ----------
    public static void partA() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", 28, 55000));
        employees.add(new Employee("Bob", 35, 72000));
        employees.add(new Employee("Charlie", 22, 48000));
        employees.add(new Employee("David", 30, 64000));

        System.out.println("\nOriginal List:");
        employees.forEach(System.out::println);

        // Sort by name (alphabetically)
        employees.sort((e1, e2) -> e1.name.compareTo(e2.name));
        System.out.println("\nSorted by Name:");
        employees.forEach(System.out::println);

        // Sort by age (ascending)
        employees.sort(Comparator.comparingInt(e -> e.age));
        System.out.println("\nSorted by Age:");
        employees.forEach(System.out::println);

        // Sort by salary (descending)
        employees.sort(Comparator.comparingDouble((Employee e) -> e.salary).reversed());
        System.out.println("\nSorted by Salary (Descending):");
        employees.forEach(System.out::println);
    }

    // ---------- PART B ----------
    public static void partB() {
        List<Student> students = Arrays.asList(
            new Student("Riya", 82.5),
            new Student("Karan", 67.0),
            new Student("Neha", 91.2),
            new Student("Amit", 74.5),
            new Student("Simran", 88.0)
        );

        System.out.println("\nAll Students:");
        students.forEach(System.out::println);

        System.out.println("\nStudents Scoring > 75% (Sorted by Marks):");
        students.stream()
                .filter(s -> s.marks > 75)
                .sorted(Comparator.comparingDouble(s -> s.marks))
                .map(s -> s.name + " (" + s.marks + "%)")
                .forEach(System.out::println);
    }

    // ---------- PART C ----------
    public static void partC() {
        List<Product> products = Arrays.asList(
            new Product("Laptop", 75000, "Electronics"),
            new Product("Phone", 55000, "Electronics"),
            new Product("Shirt", 1500, "Clothing"),
            new Product("Pants", 2000, "Clothing"),
            new Product("TV", 40000, "Electronics"),
            new Product("Blender", 3500, "Home"),
            new Product("Sofa", 25000, "Home")
        );

        System.out.println("\nAll Products:");
        products.forEach(System.out::println);

        // Group by category
        System.out.println("\nProducts Grouped by Category:");
        Map<String, List<Product>> grouped = products.stream()
                .collect(Collectors.groupingBy(p -> p.category));
        grouped.forEach((category, list) -> {
            System.out.println(category + ": " + list.stream()
                    .map(p -> p.name)
                    .collect(Collectors.joining(", ")));
        });

        // Find most expensive product in each category
        System.out.println("\nMost Expensive Product in Each Category:");
        Map<String, Optional<Product>> maxByCategory = products.stream()
                .collect(Collectors.groupingBy(
                        p -> p.category,
                        Collectors.maxBy(Comparator.comparingDouble(p -> p.price))
                ));
        maxByCategory.forEach((cat, prod) -> 
            System.out.println(cat + " -> " + prod.get().name + " (" + prod.get().price + ")")
        );

        // Calculate average price
        double avgPrice = products.stream()
                .collect(Collectors.averagingDouble(p -> p.price));
        System.out.println("\nAverage Price of All Products: " + avgPrice);
    }
}
