package machine;

import java.util.Scanner;

public class CoffeeMachine {
    static class Machine {
        public int water = 400;
        public int milk = 540;
        public int coffeeBeans = 120;
        public int cups = 9;
        public int money = 550;

        private Scanner scanner = new Scanner(System.in);

        public boolean machine(State state) {
            switch (state) {
                case buy:
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                    String choice = scanner.nextLine();
                    CoffeeRecipe recipe;
                    switch (choice) {
                        case "1":
                            recipe = CoffeeRecipe.valueOf("espresso");
                            break;
                        case "2":
                            recipe = CoffeeRecipe.valueOf("latte");
                            break;
                        case "3":
                            recipe = CoffeeRecipe.valueOf("cappuccino");
                            break;
                        case "back":
                        default:
                            recipe = CoffeeRecipe.valueOf("unknown");
                    }
                    this.makeCoffee(recipe);
                    break;
                case fill:
                    this.fill();
                    break;
                case take:
                    this.takeMoney();
                    break;
                case remaining:
                    this.showRemaining();
                    break;
                case exit:
                    return false;
            }
            return true;
        }

        public void setMaterial(int water, int milk, int coffeeBeans, int cups, int money) {
            this.water = water;
            this.coffeeBeans = coffeeBeans;
            this.cups = cups;
            this.milk = milk;
            this.money = money;
        }

        public void takeMoney() {
            System.out.println("I gave you $" + money + "\n");
            this.money = 0;
        }

        public void showRemaining() {
            System.out.println("The coffee machine has:");
            System.out.println(water + " of water");
            System.out.println(milk + " of milk");
            System.out.println(coffeeBeans + " of coffee beans");
            System.out.println(cups + " of disposable cups");
            System.out.println(money + " of money\n");
        }

        public void fill() {
            System.out.println("Write how many ml of water do you want to add:");
            int addedWater = scanner.nextInt();
            water += addedWater;
            System.out.println("Write how many ml of milk do you want to add:");
            int addedMilk = scanner.nextInt();
            milk += addedMilk;
            System.out.println("Write how many grams of coffee beans do you want to add:");
            int addedCoffeeBeans = scanner.nextInt();
            coffeeBeans += addedCoffeeBeans;
            System.out.println("Write how many disposable cups of coffee do you want to add:");
            int addedCups = scanner.nextInt();
            scanner.nextLine();
            cups += addedCups;
        }

        private boolean makeCoffee(CoffeeRecipe recipe) {

            int[] requireMaterial = recipe.getRecipe();
            if (water < requireMaterial[0]) {
                System.out.println("Sorry, not enough water!\n");
                return false;
            }

            if (milk < requireMaterial[1]) {
                System.out.println("Sorry, not enough milk!\n");
                return false;
            }

            if (coffeeBeans < requireMaterial[2]) {
                System.out.println("Sorry, not enough coffee beans!\n");
                return false;
            }

            if (cups < requireMaterial[3]) {
                System.out.println("Sorry, not enough cups!\n");
                return false;
            }
            System.out.println("I have enough resources, making you a coffee!\n");
            this.setMaterial(water - requireMaterial[0], milk - requireMaterial[1], coffeeBeans - requireMaterial[2], cups - requireMaterial[3], money + requireMaterial[4]);
            return true;
        }

    }

    enum State {buy, fill, take, remaining, exit;}

    enum CoffeeRecipe {
        espresso(250, 0, 16, 1, 4),
        latte(350, 75, 20, 1, 7),
        cappuccino(200, 100, 12, 1, 6),
        unknown(0, 0, 0, 0, 0);

        private final int water;
        private final int milk;
        private final int coffeeBeans;
        private final int cups;
        private final int money;

        CoffeeRecipe(final int water, final int milk, final int coffeeBeans, final int cups, final int money) {
            this.coffeeBeans = coffeeBeans;
            this.cups = cups;
            this.milk = milk;
            this.water = water;
            this.money = money;
        }

        public int[] getRecipe() {
            int[] recipe = new int[]{water, milk, coffeeBeans, cups, money};
            return recipe;
        }

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Machine coffeeMachine = new Machine();
        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String choice = in.nextLine();
            State state = State.valueOf(choice);
            if(!coffeeMachine.machine(state))
            {
                break;
            }
        }
    }
}
