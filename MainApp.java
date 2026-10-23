import java.util.*;

class User {
    private String name;
    private double monthlyBudget;
    private double totalExpense;

    public User(String name, double monthlyBudget) {
        this.name = name;
        this.monthlyBudget = monthlyBudget;
        this.totalExpense = 0;
    }

    public String getName() {
        return name;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void addExpense(double amount) {
        totalExpense += amount;
    }

    public double getRemainingBudget() {
        return monthlyBudget - totalExpense;
    }
}

class Expense {
    private String category;
    private double amount;

    public Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}

class ExpenseService {
    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, ArrayList<Expense>> expenses = new HashMap<>();

    public void registerUser(String name, double budget) {
        if (users.containsKey(name)) {
            System.out.println("User already exists!");
            return;
        }

        users.put(name, new User(name, budget));
        expenses.put(name, new ArrayList<>());
        System.out.println("User Registered Successfully!");
    }

    public User login(String name) {
        if (users.containsKey(name)) {
            System.out.println("Login Successful!");
            return users.get(name);
        }

        System.out.println("User not found! Please register first.");
        return null;
    }

    public void addExpense(String name, String category, double amount) {
        if (amount <= 0) {
            System.out.println("Enter a valid amount!");
            return;
        }

        Expense exp = new Expense(category, amount);
        expenses.get(name).add(exp);
        users.get(name).addExpense(amount);

        System.out.println("Expense Added Successfully!");
    }

    public void showExpenses(String name) {
        ArrayList<Expense> list = expenses.get(name);

        if (list.isEmpty()) {
            System.out.println("No expenses found!");
            return;
        }

        System.out.println("\n----- ALL EXPENSES -----");

        for (Expense e : list) {
            System.out.println(
                "Category: " + e.getCategory()
                + " | Amount: Rs. " + e.getAmount()
            );
        }
    }

    public void showSummary(User user) {
        System.out.println("\n----- EXPENSE SUMMARY -----");
        System.out.println("Total Expense: Rs. " + user.getTotalExpense());
        System.out.println("Remaining Budget: Rs. " + user.getRemainingBudget());
    }
}

public class MainApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ExpenseService service = new ExpenseService();

        int choice;

        do {
            System.out.println("\n=== PERSONAL EXPENSE TRACKER ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter monthly budget: ");
                    double budget = sc.nextDouble();
                    sc.nextLine();

                    if (budget <= 0) {
                        System.out.println("Enter a valid budget!");
                    } else {
                        service.registerUser(name, budget);
                    }
                    break;

                case 2:
                    System.out.print("Enter name: ");
                    String loginName = sc.nextLine();

                    User currentUser = service.login(loginName);

                    if (currentUser != null) {
                        int ch;

                        do {
                            System.out.println("\n=== USER MENU ===");
                            System.out.println("1. Add Expense");
                            System.out.println("2. View Expenses");
                            System.out.println("3. Summary");
                            System.out.println("4. Logout");
                            System.out.print("Enter your choice: ");

                            ch = sc.nextInt();
                            sc.nextLine();

                            switch (ch) {

                                case 1:
                                    System.out.print("Enter category: ");
                                    String cat = sc.nextLine();

                                    System.out.print("Enter amount: ");
                                    double amt = sc.nextDouble();
                                    sc.nextLine();

                                    service.addExpense(
                                        currentUser.getName(), cat, amt
                                    );
                                    break;

                                case 2:
                                    service.showExpenses(
                                        currentUser.getName()
                                    );
                                    break;

                                case 3:
                                    service.showSummary(currentUser);
                                    break;

                                case 4:
                                    System.out.println("Logged out successfully!");
                                    break;

                                default:
                                    System.out.println("Invalid choice!");
                            }

                        } while (ch != 4);
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using Personal Expense Tracker!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 3);

        sc.close();
    }
}