import com.home.TestClass2;
import ru.clevertec.annotations.LogThisMethod;
import ru.clevertec.beans.TestClass;

public class TestRunner {
    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        testClass.printA();
        System.out.println(testClass.getInt());
//
//
//        TestClass2 testClass2 = new TestClass2();
//        testClass2.printZ();
//        System.out.println(testClass2.getString());
    }

}
