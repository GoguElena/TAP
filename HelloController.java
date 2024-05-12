//HelloController.java
package org.example.tap_lab7;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField set1Input;

    @FXML
    private TextField set2Input;

    @FXML
    private Label unionResultLabel;

    @FXML
    private Label intersectionResultLabel;

    @FXML
    protected void calculateUnion() {
        String set1String = set1Input.getText();
        String set2String = set2Input.getText();

        // Convertirea șirurilor de caractere în seturi de numere întregi
        IntegerSet set1 = convertToIntegerSet(set1String);
        IntegerSet set2 = convertToIntegerSet(set2String);

        // Calcularea și afișarea uniunii
        IntegerSet unionSet = set1.union(set2);
        unionResultLabel.setText("Union: " + unionSet.getStringRepresentation());
    }

    @FXML
    protected void calculateIntersection() {
        String set1String = set1Input.getText();
        String set2String = set2Input.getText();

        // Convertirea șirurilor de caractere în seturi de numere întregi
        IntegerSet set1 = convertToIntegerSet(set1String);
        IntegerSet set2 = convertToIntegerSet(set2String);

        // Calcularea și afișarea intersecției
        IntegerSet intersectionSet = set1.intersection(set2);
        intersectionResultLabel.setText("Intersection: " + intersectionSet.getStringRepresentation());
    }


    private IntegerSet convertToIntegerSet(String inputString) {
        IntegerSet set = new IntegerSet();
        String[] numbers = inputString.split(",");
        for (String number : numbers) {
            try {
                int num = Integer.parseInt(number.trim());
                set.add(num);
            } catch (NumberFormatException e) {
                // Ignoră numerele nevalide
            }
        }
        return set;
    }
}
