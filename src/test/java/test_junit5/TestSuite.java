package test_junit5;


import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("JUnit Platform Sutie Demo Test suite")
//@SelectPackages("test_junit5")
@SelectClasses({
        CalcTest.class,TestSimple.class
})
public class TestSuite {

}
