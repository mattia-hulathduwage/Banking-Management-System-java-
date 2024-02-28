import java.util.Scanner;


class Transaction {
    int sourceAccountNumber;
    int destinationAccountNumber;
    double amount;

    public Transaction(int sourceAccountNumber, int destinationAccountNumber, double amount) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }
}

class TransactionNode {
    Transaction data;
    TransactionNode prev, next;

    public TransactionNode(Transaction data) {
        this.data = data;
        this.prev = this.next = null;
    }
}

class DoublyLinkedList {
    TransactionNode head;

    public DoublyLinkedList() {
        this.head = null;
    }

    void addTransaction(Transaction transaction) {
        TransactionNode newNode = new TransactionNode(transaction);
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    void displayTransactionHistory() {
        System.out.println("Transaction History:");
        TransactionNode current = head;
        while (current != null) {
            System.out.println("Amount: LKR." + current.data.amount
                    + ", Destination Account: " + current.data.destinationAccountNumber);
            current = current.next;
        }
    }
}
class BankAccount {
    int accountNumber;
    String accountHolderName;
    double balance;

    DoublyLinkedList transactions; 

    public BankAccount(int accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.transactions = new DoublyLinkedList();
    }

    void addTransaction(Transaction transaction) {
        transactions.addTransaction(transaction);
    }

    void displayTransactionHistory() {
        transactions.displayTransactionHistory();
    }
}

class Node {
    BankAccount data;
    Node left, right;

    public Node(BankAccount data) {
        this.data = data;
        left = right = null;
    }
}

class BinarySearchTree {
    Node root;

    BinarySearchTree() {
        root = null;
    }

    void insert(BankAccount data) {
        root = insertRec(root, data);
    }

    Node insertRec(Node root, BankAccount data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }
        if (data.accountNumber < root.data.accountNumber) {
            root.left = insertRec(root.left, data);
        } else if (data.accountNumber > root.data.accountNumber) {
            root.right = insertRec(root.right, data);
        }

        return root;
    }

void displayAccounts(Node root) {
        if (root != null) {
            displayAccounts(root.left);
            System.out.println("Account Number: " + root.data.accountNumber
                    + ", Account Holder Name: " + root.data.accountHolderName
                    + ", Balance: LKR." + root.data.balance);
            displayAccounts(root.right);
        }
    }

    void displayAccountDetails(int accountNumber) {
        displayAccountDetailsRec(root, accountNumber);
    }

    void displayAccountDetailsRec(Node root, int accountNumber) {
        if (root == null) {
            System.out.println("Account not found!");
            return;
        }
        if (root.data.accountNumber == accountNumber) {
            System.out.println("Account Number: " + root.data.accountNumber
                    + ", Account Holder Name: " + root.data.accountHolderName
                    + ", Balance: LKR." + root.data.balance);
        } else if (root.data.accountNumber > accountNumber) {
            displayAccountDetailsRec(root.left, accountNumber);
        } else {
            displayAccountDetailsRec(root.right, accountNumber);
        }
    }

    void depositMoney(int accountNumber, double amount) {
        root = depositMoneyRec(root, accountNumber, amount);
    }

    Node depositMoneyRec(Node root, int accountNumber, double amount) {
        if (root == null) {
            System.out.println("Account not found!");
            return root;
        }
        if (root.data.accountNumber == accountNumber) {
            root.data.balance += amount;
            System.out.println("Deposit successful! New balance: LKR." + root.data.balance);
        } else if (root.data.accountNumber > accountNumber) {
            root.left = depositMoneyRec(root.left, accountNumber, amount);
        } else {
            root.right = depositMoneyRec(root.right, accountNumber, amount);
        }
        return root;
    }

    void withdrawMoney(int accountNumber, double amount) {
        root = withdrawMoneyRec(root, accountNumber, amount);
    }

    Node withdrawMoneyRec(Node root, int accountNumber, double amount) {
        if (root == null) {
            System.out.println("Account not found!");
            return root;
        }
        if (root.data.accountNumber == accountNumber) {
            if (root.data.balance >= amount) {
                root.data.balance -= amount;
                System.out.println("Withdrawal successful! New balance: LKR." + root.data.balance);
            } else {
                System.out.println("Insufficient funds!");
            }
        } else if (root.data.accountNumber > accountNumber) {
            root.left = withdrawMoneyRec(root.left, accountNumber, amount);
        } else {
            root.right = withdrawMoneyRec(root.right, accountNumber, amount);
        }
        return root;
    }

    Node findAccount(Node root, int accountNumber) {
        if (root == null || root.data.accountNumber == accountNumber) {
            return root;
        }
        if (root.data.accountNumber > accountNumber) {
            return findAccount(root.left, accountNumber);
        }
        return findAccount(root.right, accountNumber);
    }

    void domesticTransaction(int sourceAccountNumber, int destinationAccountNumber, double amount) {
        Node sourceAccountNode = findAccount(root, sourceAccountNumber);
        Node destinationAccountNode = findAccount(root, destinationAccountNumber);

        if (sourceAccountNode != null && destinationAccountNode != null) {
            if (sourceAccountNode.data.balance >= amount) {
                sourceAccountNode.data.balance -= amount;
                destinationAccountNode.data.balance += amount;

                Transaction transaction = new Transaction(sourceAccountNumber, destinationAccountNumber, amount);
                sourceAccountNode.data.addTransaction(transaction);

                System.out.println("Transaction successful!");
            } else {
                System.out.println("Insufficient funds in Account " + sourceAccountNumber + " for the transaction.");
            }
        } else {
            System.out.println("One or both accounts not found!");
        }
    }

void displayTransactionHistory(int accountNumber) {
        Node accountNode = findAccount(root, accountNumber);

        if (accountNode != null) {
            accountNode.data.displayTransactionHistory();
        } else {
            System.out.println("Account not found!");
        }
    }

    void deleteAccount(int accountNumber) {
        root = deleteAccountRec(root, accountNumber);
    }

    Node deleteAccountRec(Node root, int accountNumber) {
        if (root == null) {
            System.out.println("Account not found!");
            return root;
        }

        if (accountNumber < root.data.accountNumber) {
            root.left = deleteAccountRec(root.left, accountNumber);
        } else if (accountNumber > root.data.accountNumber) {
            root.right = deleteAccountRec(root.right, accountNumber);
        } else {
            
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            
            root.data = minValue(root.right);

           
            root.right = deleteAccountRec(root.right, root.data.accountNumber);
        }

        return root;
    }

    BankAccount minValue(Node root) {
        BankAccount minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }
}

public class Pdsajava {
    private static BinarySearchTree bst = new BinarySearchTree();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayMenu();
    }

    private static void displayMenu() {
        while (true) {
            System.out.println();
            System.out.println("1. Add an Account");
            System.out.println("2. Display All Accounts");
            System.out.println("3. Display Account Details");
            System.out.println("4. Deposit Money");
            System.out.println("5. Withdraw Money");
            System.out.println("6. Domestic transaction");
            System.out.println("7. Display transaction history");
            System.out.println("8. Delete an Account");
            System.out.println("9. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            processChoice(choice);
        }
    }

    private static void processChoice(int choice) {
        switch (choice) {
            case 1:
                addAccount();
                break;
            case 2:
                displayAllAccounts();
                break;
            case 3:
                displayAccountDetails();
                break;
            case 4:
                depositMoney();
                break;
            case 5:
                withdrawMoney();
                break;
            case 6:
                domesticTransaction();
                break;
            case 7:
                displayTransactionHistory();
                break;
            case 8:
                deleteAccount();
                break;
            case 9:
                exitSystem();
                break;
            default:
                System.out.println();
                System.out.println("Invalid choice! Please enter a valid option.");
                System.out.println("-------------------------------");
        }
    }

    private static void addAccount() {
        System.out.println();
        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Account Holder Name: ");
        String accountHolderName = scanner.nextLine();
        System.out.print("Enter Balance: ");
        double balance = scanner.nextDouble();
        bst.insert(new BankAccount(accountNumber, accountHolderName, balance));
        System.out.println("New Account added successfully!");
        System.out.println("-------------------------------");
    }

    private static void displayAllAccounts() {
        System.out.println();
        System.out.println("All Accounts:");
        bst.displayAccounts(bst.root);
        System.out.println("-------------------------------");
    }

    private static void displayAccountDetails() {
        System.out.println();
        System.out.print("Enter Account Number to display details: ");
        int displayAccountNumber = scanner.nextInt();
        bst.displayAccountDetails(displayAccountNumber);
        System.out.println("-------------------------------");
    }

    private static void depositMoney() {
        System.out.println();
        System.out.print("Enter Account Number to deposit money: ");
        int depositAccountNumber = scanner.nextInt();
        System.out.print("Enter amount to deposit: ");
        double depositAmount = scanner.nextDouble();
        bst.depositMoney(depositAccountNumber, depositAmount);
        System.out.println("-------------------------------");
    }

    private static void withdrawMoney() {
        System.out.println();
        System.out.print("Enter Account Number to withdraw money: ");
        int withdrawAccountNumber = scanner.nextInt();
        System.out.print("Enter amount to withdraw: ");
        double withdrawAmount = scanner.nextDouble();
        bst.withdrawMoney(withdrawAccountNumber, withdrawAmount);
        System.out.println("-------------------------------");
    }

    private static void domesticTransaction() {
        System.out.println();
        System.out.print("Enter Source Account Number: ");
        int sourceAccountNumber = scanner.nextInt();
        System.out.print("Enter Destination Account Number: ");
        int destinationAccountNumber = scanner.nextInt();
        System.out.print("Enter amount to transfer: ");
        double transferAmount = scanner.nextDouble();
        bst.domesticTransaction(sourceAccountNumber, destinationAccountNumber, transferAmount);
        System.out.println("-------------------------------");
    }

    private static void displayTransactionHistory() {
        System.out.println();
        System.out.print("Enter Account Number to display transaction history: ");
        int historyAccountNumber = scanner.nextInt();
        bst.displayTransactionHistory(historyAccountNumber);
        System.out.println("-------------------------------");
    }

    private static void deleteAccount() {
        System.out.println();
        System.out.print("Enter Account Number to delete: ");
        int deleteAccountNumber = scanner.nextInt();
        bst.deleteAccount(deleteAccountNumber);
        System.out.println("Account deleted successfully! ");
        System.out.println("-------------------------------");
    }

    private static void exitSystem() {
        System.out.println();
        System.out.println("Exiting...");
        System.exit(0);
    }
}
