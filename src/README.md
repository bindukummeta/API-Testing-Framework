This is the  Restassured Test framework for testing one of the free apis' available. This can be reused/extended for any REST API

API used for test       :  https://restful-booker.herokuapp.com/ 
Technologies used       :  Java
                           Testng
                           RestAssured
                           Jackson
                           JsonPath
                           Log4J
                           Intellij IDE

Setup of Tests           :  This testframework is built using Java as a programming language REST Assured for making 
                            all CRUD operations, Testng as a runner, Jackson for building json objects for request 
                            specification of domain objects, Jsonpath to traverse through json to validate responses/
                            extract any test data and Log4j for logging purposes.
                            
                            
Test Organization        :  BaseSteps file consists of the basic requestSpecification, responseSpecification which has 
                              BaseUri, Content-type etc which are genric for all tests is defined here.  Logger 
                              is initiated in the file to log any actions performed as part of the tests. All the tests
                              extend this class.
                              
                              Test classes are split based on the end resource. Each class consists of happypath, unhappy 
                              paths related to that particular end point which are tagged using Testng annotation @Test. 
                              Tests have validations included using Testng assertions.  Jsonpath is used to traverse through
                              the response json and to retrieve any key value pairs.
                              
                              For any operations that need response body, java classes are defined with field names, getters and
                              setters .  Jsonpojo is used to convert class domains for serialisation and deserialisation purposes.
                              
Test Execution            :  Testng is used as a runner for this framework.  Run configuration needs to be set up in intellij 
                                to run the tests. Either tests can be run at individual method level/ class level or all tests
                                with @Test annotation.
                              
                              
                                                        
                            
                           