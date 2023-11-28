import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProgramTest {
    private static final List<TestCase> TEST_CASES =
            List.of(new TestCase(1, "1 2 3", "2 1 1 1\n"), new TestCase(2, "", "1\n"),
                    new TestCase(3, "", IntStream.range(20, 1010).mapToObj(Integer::toString)
                                                 .collect(Collectors.joining("\n")) + "\n"),
                    new TestCase(4, "", "1 25 3\n"),
                    new TestCase(5, "1000", IntStream.range(2, 1000).filter(v -> {
                        for (int i = 2; i * i <= v; i++) {
                            if (v % i == 0) {
                                return false;
                            }
                        }
                        return true;
                    }).mapToObj(Integer::toString).collect(Collectors.joining("\n")) + "\n"),
                    new TestCase(6, "", "5\n"),
                    new TestCase(7, "", "5 8 0\n")
                   );
    private static boolean failed = false;

    public static void main(String[] args) {
        TEST_CASES.forEach(testCase -> {
            if (!testCase.checkTest()) {
                failed = true;
                System.err.println("Failed test №" + testCase.index());
            }
        });
        if (failed) {
            System.err.println("TESTS FAILED!");
        } else {
            System.out.println("TESTS PASSED!");
        }
    }
}
