import eliseevh.grammar.goodlang.Main;

import java.nio.charset.StandardCharsets;

public record TestCase(int index, String input, String expectedOutput) {
    private static final String FILE_PATH_FORMAT = "test-cases/case%d/input%s";

    public boolean checkTest() {
        String filePath = String.format(FILE_PATH_FORMAT, index, ".bl");
        try {
            Main.main(filePath);
            new ProcessBuilder("gcc", "-o", String.format(FILE_PATH_FORMAT, index, ""),
                               String.format(FILE_PATH_FORMAT, index, ".c")).start().waitFor();
            Process process =
                    new ProcessBuilder(String.format(FILE_PATH_FORMAT, index, "")).start();
            process.getOutputStream().write(input.getBytes(StandardCharsets.UTF_8));
            process.getOutputStream().flush();
            process.getOutputStream().close();
            String output =
                    new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            return output.equals(expectedOutput);
        } catch (Exception e) {
            System.err.println("Run failed: " + e.getMessage() + ", on file: " + filePath);
            e.printStackTrace();
            return false;
        }

    }
}
