package Test;


import expression.arithmetic.*;
import expression.bool.BooleanExpression;
import parser.ExpressionParser;
import org.junit.Test;
import expression.*;
import parser.exceptions.*;

import static org.junit.Assert.assertEquals;

public class ExpressionParserTest {


    ExpressionParser parser = new ExpressionParser();

    void run(String expected, String test, boolean hasException) throws SyntaxException {
       if (hasException) {
           try {
               // надо прописать, что будет на месте переменной (для возможной замены).
               parser.parse(test, new Variable("element"));
               throw new TestExceptions("You needed to catch Exception");
           } catch (TypeException | SyntaxException e) {
               System.out.println(e + " " + e.getMessage());
           }
       } else {
           assertEquals(parser.parse(test, new Variable("element")).toString(), expected);
       }
    }

    @Test
    public void parseConst() throws SyntaxException {
        run(Integer.toString(Integer.MIN_VALUE), Integer.toString(Integer.MIN_VALUE), false);
        run("1234", "1234", false);
        run("", "-123e", true);
    }

    @Test
    public void parseVariable() throws SyntaxException {
        run("element", "element", false);
        run("", "elemelem", true);
    }

    @Test
    public void arithmeticExpressions() throws SyntaxException {
        run("(((1+element)-23456)*(element*324))", "(((1+element)-23456)*(element*324))", false);
        run("((((((((1*2)+3)*4)-5)*6)-7)*8)+9)", "((((((((1*2)+3)*4)-5)*6)-7)*8)+9)", false);
        run("", "(12 element)", true);
        run("", "12++", true);
        run("", "(223+2", true);
        run("", "(1+1)+2)", true);
        run("", "99999999999999999999999999999999999999", true);
        run("", "-", true);
    }

    @Test
    public void booleanExpression() throws SyntaxException {
        run("(12345>element)", "(12345>element)", false);
        run("((element+197)=352)", "((element+197)=352)", false);
        run("(((133>element)&(332<(element*element)))|(32=(element-1)))", "(((133>element)&(332<(element*element)))|(32=(element-1)))", false);
        run("","((133+34)&(235<4))", true);
        run("","((4<5)=(4<5))", true);
        run("","((35>element)+((5*4)=(5*4)))", true);
    }

    @Test
    public void evaluate() throws SyntaxException {
        ExpressionParser helpParser = new ExpressionParser();
        Expression expression = parser.parse("((123+element)*1)", new Variable("element"));
        if (expression instanceof ArithmeticExpression) {
            run("123",Integer.toString(((ArithmeticExpression) expression).evaluate(0)), false);
        } else {
            throw new TestExceptions("Incorrect evaluate");
        }
        expression = parser.parse("(2=2)", new Variable("element"));
        if (expression instanceof BooleanExpression) {
            String answer = "";
            if (((BooleanExpression) expression).evaluate(-5)) {
                answer = "1";
            }
            run("1", answer, false);
        } else {
            throw new TestExceptions("Incorrect evaluate");
        }
    }

}



