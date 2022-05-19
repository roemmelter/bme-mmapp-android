package org.mmapp.labs;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mmapp.R;
import org.mmapp.util.ConfigActionBar;

import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class CalculatorActivity extends AppCompatActivity {

    private final int NR_OF_COLUMNS = 4;
    private final String[] btnLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "(", "0", ")", "+",
            "C", "DEL", "^", "=",
    };
    private TextView tv;
    private int width = 0;
    private int height = 0;
    private ConfigActionBar configActionBar;
    private boolean isResultCalculated = false;
    private boolean isError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        tv = findViewById(R.id.textViewDisplay);

        getScreenDimensions();
        final int NR_OF_ROWS = 5;

        GridLayout gl = findViewById(R.id.gridLayoutButtons);
        gl.removeAllViews();
        gl.setColumnCount(NR_OF_COLUMNS);
        gl.setRowCount(NR_OF_ROWS);

        Button[] btns = new Button[btnLabels.length];
        for (int i = 0; i < btnLabels.length; i++) {
            btns[i] = new Button(this);
            btns[i].setText(btnLabels[i]);
            btns[i].setTransformationMethod(null);
            btns[i].setLayoutParams( getLayoutParams(width, i) );
            btns[i].setOnClickListener(new BtnOnClickListener());
            gl.addView(btns[i]);
        }
        configActionBar = new ConfigActionBar(LabsOverviewActivity.class, this, true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        configActionBar.setActionBackButton(item);
        return super.onOptionsItemSelected(item);
    }

    private GridLayout.LayoutParams getLayoutParams(int width, int i) {
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.width = width / NR_OF_COLUMNS;
        param.setGravity(Gravity.CENTER);
        param.columnSpec = GridLayout.spec(i % NR_OF_COLUMNS);
        param.rowSpec = GridLayout.spec(i / NR_OF_COLUMNS);
        return param;
    }
    private void getScreenDimensions() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }

    /**
     * Convert infix to postfix expression
     * Process infix string
     * - if digit or letter add to postfix
     * - else if '(' push to stack
     * - else if ')' pop and
     *      - populate stack entries until '(' occurs
     *      - add entries to postfix
     *      - populate and discard matching parenthesis
     * - else operator push to stack
     *      - populate stack entries until operator with higher precedence occurs
     *      - add to postfix
     * Process stack leftover entries
     *      - pop all operators and add to postfix
     *      - if '(' occurs => invalid expression
     * @param infix expression as String
     * @return postfix expression as String
     */
    private String convertInfixToPostfix( String infix ) {
        String postfix = "";
        Stack<Character> stack = new Stack<>();
        int infixLength = infix.length();

        if (infixLength == 0) {
            Log.w("M_", "Infix expression (String) is empty.");
        }
        // process infix expression char by char
        for (int i = 0; i < infixLength; i++ ) {
            char currentChar = infix.charAt(i);
            if (Character.isDigit(currentChar))
            {
                postfix += currentChar;
            } else if (currentChar == '(') {
                stack.push(currentChar);
            } else if (currentChar == ')') {
                // populate stack until '(' occurs
                while (!stack.isEmpty() &&
                       stack.peek() != '(') {
                    postfix += stack.pop();
                }
                // discard matching parenthesis
                try {
                    stack.pop();
                } catch (Exception err) {
                    Log.e("M_", err.getMessage() + ": " + err.getStackTrace());
                    isError = true;
                }
            } else {
                // operator occurs
                postfix += " ";
                while (!stack.isEmpty() &&
                       getPrecedence(currentChar) <= getPrecedence(stack.peek())) {
                    postfix += stack.pop();
                }
                // add operator to stack
                stack.push(currentChar);
                postfix += " ";
            }
        }
        // process stack leftovers
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                Log.e("M_", "Infix expression (String) is invalid." +
                             "Each opening parenthesis must be closed.");
                return "Invalid Expression";
            }
            postfix += " " + stack.pop();
        }
        return postfix.trim();
    }
    private int getPrecedence(char operator) {
        // Higher precedence results in higher returned value
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    private double evaluatePostfix( String postfix ) {
        Stack<Integer> stack = new Stack<>();
        double result = 0;

        StringTokenizer tokenizer = new StringTokenizer(postfix);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if ("0123456789".contains(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                try {
                    int num2 = stack.pop();
                    int num1 = stack.pop();

                    switch (token.charAt(0)) {
                        case '+':
                            result = num1 + num2;
                            break;
                        case '-':
                            result = num1 - num2;
                            break;
                        case '*':
                            result = num1 * num2;
                            break;
                        case '/':
                            try {
                                result = num1 / num2;
                            } catch (Exception err) {
                                Log.e("M_", "Invalid Operation: Division by zero.");
                                isError = true;
                            }
                            break;
                        case '^':
                            result = Math.pow(num1, num2);
                            break;
                    }
                } catch (Exception err) {
                    Log.e("M_", err.getMessage() + ": " + Arrays.toString(err.getStackTrace()));
                    isError = true;
                }
            }
        }
        return result;
    }

    class BtnOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String btnLabelInput = ((Button) v).getText().toString();
            String infix = tv.getText().toString();
            if ("0123456789()+-*/^".contains(btnLabelInput)) {
                if (infix.equals("0") || isResultCalculated) {
                    tv.setText("");
                    isResultCalculated = false;
                }
                tv.append(btnLabelInput);
            } else if (btnLabelInput.equals("C")) {
                tv.setText("");
                isError = false;
            } else if (btnLabelInput.equals("=")) {
                Log.i("M_", "Infix: " + infix);
                String postfix = convertInfixToPostfix(infix);
                Log.i("M_", "Postfix: "+ postfix);
                double result = evaluatePostfix(postfix);
                Log.i("M_", "Result: "+ result);
                String printResult = isError ? "An error occured. Press \"C\" to continue." :
                                     "" + result;
                tv.setText(printResult);
                isResultCalculated = true;
            } else if (btnLabelInput.equals("DEL")) {
                String txt = tv.getText().toString();
                if (txt.length() > 0) {
                    tv.setText(txt.substring(0, txt.length() - 1));
                }
                isError = false;
            }
        }
    }
}
