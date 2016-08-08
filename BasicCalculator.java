
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Stack;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BasicCalculator extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        // textfield to read in  & display expressions
        TextField myTF = new TextField();
        
        // 0 - 9 buttons for calculator GUI & their functionalities
        Button oneButton = new Button("1");
        oneButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "1");
        });
        Button twoButton = new Button("2");
        twoButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "2");
        });
        Button threeButton = new Button("3");
        threeButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "3");
        });
        Button fourButton = new Button("4");
        fourButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "4");
        });
        Button fiveButton = new Button("5");
        fiveButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "5");
        });
        Button sixButton = new Button("6");
        sixButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "6");
        });
        Button sevenButton = new Button("7");
        sevenButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "7");
        });
        Button eightButton = new Button("8");
        eightButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "8");
        });
        Button nineButton = new Button("9");
        nineButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "9");
        });
        Button zeroButton = new Button("0");
        zeroButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "0");
        });
        
        // operator buttons for Calculator GUI & their functionalities
        Button addButton = new Button("+");
        addButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "+");
        });
        Button subButton = new Button("-");
        subButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "-");
        });
        Button multButton = new Button("*");
        multButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "*");
        });
        Button divButton = new Button("/");
        divButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "/");
        });
        Button modButton = new Button("%");
        modButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "%");
        });
        Button expButton = new Button("^");
        expButton.setOnAction((ActionEvent event) -> {
            myTF.setText(myTF.getText() + "^");
        });
        
        // answer button & functionality
        Button calcIT = new Button("Calc!");
        calcIT.setOnAction((ActionEvent event) -> {
            System.out.println(calculateExpression(myTF.getText()));
        });
        
        // place numeric buttons into rows for GUI
        HBox nums123HBox = new HBox();
        nums123HBox.getChildren().add(oneButton);
        nums123HBox.getChildren().add(twoButton);
        nums123HBox.getChildren().add(threeButton);
        HBox nums456HBox = new HBox();
        nums456HBox.getChildren().add(fourButton);
        nums456HBox.getChildren().add(fiveButton);
        nums456HBox.getChildren().add(sixButton);
        HBox nums789HBox = new HBox();
        nums789HBox.getChildren().add(sevenButton);
        nums789HBox.getChildren().add(eightButton);
        nums789HBox.getChildren().add(nineButton);
        HBox nums0HBox = new HBox();
        nums0HBox.getChildren().add(zeroButton);
        
        // operator buttons for GUI
        VBox signs = new VBox();
        signs.getChildren().add(addButton);
        signs.getChildren().add(subButton);
        signs.getChildren().add(multButton);
        signs.getChildren().add(divButton);
        signs.getChildren().add(modButton);
        signs.getChildren().add(expButton);
        
        // numeric buttons for GUI 
        VBox nums = new VBox();
        nums.getChildren().add(nums123HBox);
        nums.getChildren().add(nums456HBox);
        nums.getChildren().add(nums789HBox);
        nums.getChildren().add(nums0HBox);
        
        // borderpane to arrange buttons on GUI
        BorderPane myPane = new BorderPane();
        myPane.setTop(myTF);
        myPane.setLeft(nums);
        myPane.setCenter(signs);
        myPane.setRight(calcIT);

        Scene scene = new Scene(myPane, 250, 250);
        primaryStage.setTitle("My Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // this method uses stacks to separate operands & operators, preparing them
    // to be processed by the 'crunchExpression' method
    public static int calculateExpression(String expression) {

        Stack<Integer> numberStack = new Stack<>();
        Stack<Character> signStack = new Stack<>();

        expression = separateExpression(expression);
        String[] expressionArray = expression.split(" ");
        
        // this loop allows for proper order of operations to take place. 
        // while 'examining' a given operator,if the loop encounters an 
        // operator with higher priority, it will process that operator
        // and its operands before processing operands joined by a lower 
        // priority operator.
        for (String element : expressionArray) {
            if (element.length() == 0) {
                continue;
            } else if (element.charAt(0) == '+' || element.charAt(0) == '-') {
                while (!signStack.isEmpty()
                        && (signStack.peek() == '+' || signStack.peek() == '-'
                        || signStack.peek() == '*' || signStack.peek() == '/'
                        || signStack.peek() == '%' || signStack.peek() == '^')) {
                    crunchExpression(numberStack, signStack);
                }
                signStack.push(element.charAt(0));
            } else if (element.charAt(0) == '*' || element.charAt(0) == '/'
                    || element.charAt(0) == '%') {

                while (!signStack.isEmpty()
                        && (signStack.peek() == '*' || signStack.peek() == '/'
                        || signStack.peek() == '%' || signStack.peek() == '^')) {
                    crunchExpression(numberStack, signStack);
                }
                signStack.push(element.charAt(0));
            } else if (element.charAt(0) == '^') {
                while (!signStack.isEmpty()
                        && (signStack.peek() == '^')) {
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
    // this method does the math!
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
            numberStack.push((int) Math.pow(num2, num1));
        }
    }
    // this method inserts blank spaces between operators & operands
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
