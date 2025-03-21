package com.challenge.api.customer.management.util;

public class Constants {

    public static final String CUSTOMER_NOT_FOUND = "Customer not found";

    public static final String CUSTOMER_DELETED = """
            Customer deleted, with identification: [{}]:""";

    public static final String INIT_METHOD = """
            |--> Start Method: [{}]""";
    
    public static final String INIT_METHOD_PARAMETERS = """
            |--> Start Method: [{}], Parameters: [{}: {}]""";

    public static final String ERROR_METHOD_PARAMETERS = """
            |--> Error Method: [{}], Message: {}""";
}
