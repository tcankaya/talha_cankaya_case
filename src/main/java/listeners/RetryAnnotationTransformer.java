package listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import utils.RetryAnalyzer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryAnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {

        if (testMethod == null) return;

        Class<?> declaring = testMethod.getDeclaringClass();

        // Apply to only HomePageTest
        if (declaring.getName().equals("tests.HomePageTest")) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }
}