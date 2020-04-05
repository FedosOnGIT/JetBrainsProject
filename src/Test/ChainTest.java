package Test;

import parser.ChainParser;
import org.junit.Test;
import parser.exceptions.*;

import static org.junit.Assert.assertEquals;

public class ChainTest {

    ChainParser parser = new ChainParser();

    // У меня было мало времени, поэтому я сделаю через evaluate
    void run(Integer expected, String test, boolean hasException) throws SyntaxException {
        if (hasException) {
            try {
                parser.simplify(test);
                throw new TestExceptions("You needed to catch Exception");
            } catch (TypeException | SyntaxException e) {
                System.out.println(e + " " + e.getMessage());
            }
        } else {
            assertEquals(expected, parser.simplify(test).evaluate(0));
        }
    }

    @Test
    public void easy() throws SyntaxException {
        run(null,"filter{(element>12)}%>%map{element}", false);
        run(7, "map{(element+7)}%>%filter{(element=7)}%>%map{((element+1)-1)}", false);
        run(0, "filter{(3+4)}", true);
        run(0, "map{((5=3)|(element>4))}", true);
        run(0, "map{(4+4)", true);
        run(0, "mama{5}", true);
    }

    @Test
    public void hard() throws SyntaxException {
        run(100,"map{(element+10)}%>%filter{(element>9)}%>%map{(element*element)}", false);
        run(null, "filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}", false);
        run(20, "map{(((element+4)*5)-5)}%>%filter{((element>0)&(element<100))}%>%map{((element-10)--15)}", false);
    }
}
