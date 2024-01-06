package top.yigumoyan;

public class Main {
    public static void main(String[] args) {
        LargeNumber num1 = new LargeNumber("142");
        LargeNumber num2 = new LargeNumber("144");

        LargeNumber sum = num1.add(num2);
        System.out.println("Sum: " + sum);

        LargeNumber difference = num1.subtract(num2);
        System.out.println("Difference: " + difference);
    }
}