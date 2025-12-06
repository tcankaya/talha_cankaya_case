package utils;

import org.testng.ITestResult;
import org.testng.IRetryAnalyzer;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private static final int maxRetry = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxRetry) {
            count++;

            result.setAttribute("retrying", true);

            System.out.println("RETRYING: " + result.getName() + " (" + count + "/" + maxRetry + ")");            return true;
        }
        return false;
    }
}