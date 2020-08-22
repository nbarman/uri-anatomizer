package com.uri.anatomizer.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.uri.anatomizer.test.controller.URIControllerTest;
import com.uri.anatomizer.test.service.URIServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	URIControllerTest.class,
	URIServiceTest.class
})
public class URIAnatomizerTestSuite {

}
