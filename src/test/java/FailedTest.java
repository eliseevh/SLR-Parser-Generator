import eliseevh.grammar.goodlang.GoodLangToCGrammar;
import eliseevh.grammar.goodlang.Main;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class FailedTest {
    @Test
    public void failed1() {
        assertThrows(GoodLangToCGrammar.IncorrectAssignmentException.class, () -> Main.main("test-cases/caseFailed/input1.bl"));
    }
    @Test
    public void failed2() {
        assertThrows(GoodLangToCGrammar.IncorrectAssignmentException.class, () -> Main.main("test-cases/caseFailed/input2.bl"));
    }
}
