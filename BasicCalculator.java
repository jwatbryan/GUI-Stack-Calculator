
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Stack;

public class BasicCalculator extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        

        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                System.out.println(calculateExpression("2 + 4"));
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static int calculateExpression(String expression) {

        Stack<Integer> numberStack = new Stack<>();
        Stack<Character> signStack = new Stack<>();

        expression = separateExpression(expression);

        String[] expressionArray = expression.split(" ");

        for (String element : expressionArray) {

            if (element.length() == 0) {
                continue;
            } else if (element.charAt(0) == '+' || element.charAt(0) == '-') {
                while (!signStack.isEmpty()
                        && (signStack.peek() == '+' || signStack.peek() == '-'
                        || signStack.peek() == '*' || signStack.peek() == '/')) {
                    crunchExpression(numberStack, signStack);
                }
                signStack.push(element.charAt(0));
            } else if (element.charAt(0) == '*' || element.charAt(0) == '/') {

                while (!signStack.isEmpty()
                        && signStack.peek() == '*' || signStack.peek() == '/') {
                    crunchExpression(numberStack, signStack);
                }
                signStack.push(element.charAt(0));
            } else if (element.charAt(0) == '(') {
                signStack.push('(');
            } else if (element.charAt(0) == ')') {
                while (signStack.peek() != '(') {
                    crunchExpression(numberStack, signStack);
                }
                signStack.pop();
            } else {
                numberStack.push(new Integer(element));
            }
        }
        while (!signStack.isEmpty()) {
            crunchExpression(numberStack, signStack);
        }
        return numberStack.pop();

    }

    public static void crunchExpression(Stack<Integer> numberStack, Stack<Character> signStack) {

        char sign = signStack.pop();
        int num1 = numberStack.pop();
        int num2 = numberStack.pop();

        if (sign == '+') {
            numberStack.push(num2 + num1);
            
        } else if (sign == '-') {
            numberStack.push(num2 - num1);
        } else if (sign == '*') {
            numberStack.push(num2 * num1);
        } else if (sign == '/') {
            numberStack.push(num2 / num1);
        } else if (sign == '%') {
            numberStack.push(num2 % num1);
        } else if (sign == '^') {
            //numberStack.push(num2 + num1);
        }
    }

    public static String separateExpression(String expression) {

        String result = "";

        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(' || expression.charAt(i) == ')'
                    || expression.charAt(i) == '+' || expression.charAt(i) == '-'
                    || expression.charAt(i) == '*' || expression.charAt(i) == '/'
                    || expression.charAt(i) == '%' || expression.charAt(i) == '^') {
                result += " " + expression.charAt(i) + " ";
            } else {
                result += expression.charAt(i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
