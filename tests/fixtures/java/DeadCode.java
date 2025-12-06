// Test fixture: Various dead code patterns in Java
package com.example.fixtures;

// DC001: Unreferenced class
class UnusedJavaClass {
    public void doSomething() {}
}

// DC001: Unreferenced method in used class
public class DeadCode {
    public void usedMethod() {
        System.out.println("used");
    }

    // This method is never called
    private void unusedPrivateMethod() {
        System.out.println("never called");
    }

    // DC001: Unreferenced property
    private String unusedField = "dead";
    public String usedField = "alive";

    // DC002: Write-only variable
    private int writeOnlyCounter = 0;

    public void increment() {
        writeOnlyCounter++;
    }

    // DC003: Unused parameter
    public String processData(String data, int unusedParam) {
        return data.toUpperCase();
    }

    public static void main(String[] args) {
        DeadCode instance = new DeadCode();
        instance.usedMethod();
        System.out.println(instance.usedField);
        instance.increment();
        instance.processData("test", 42);
    }
}

// DC005: Unused enum case
enum JavaStatus {
    ACTIVE,
    INACTIVE,
    DEPRECATED,  // Never referenced
    ARCHIVED     // Never referenced
}

class StatusChecker {
    public static boolean checkStatus(JavaStatus status) {
        return status == JavaStatus.ACTIVE || status == JavaStatus.INACTIVE;
    }

    public static void main(String[] args) {
        checkStatus(JavaStatus.ACTIVE);
    }
}
