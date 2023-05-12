import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExampleClass {

    public static void main(String[] args) {
        ExampleClass exampleClass = new ExampleClass();
        exampleClass.someMethod();
    }

    public void someMethod() {
        log.info("This is an information message.");
        log.warn("This is a warning message.");
        log.error("This is an error message.");
    }
}

