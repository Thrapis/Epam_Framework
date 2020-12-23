package service;

import model.CustomerOrderData;

public class CustomerOrderDataCreator {

    public static final String TESTDATA_ORDERDATA_FULLNAME = "test.data.orderData.first.fullName";
    public static final String TESTDATA_ORDERDATA_EMAIL = "test.data.orderData.first.email";
    public static final String TESTDATA_ORDERDATA_PHONENUMBER = "test.data.orderData.first.phoneNumber";
    public static final String TESTDATA_ORDERDATA_ADDRESS = "test.data.orderData.first.address";
    public static final String TESTDATA_ORDERDATA_COMMENT = "test.data.orderData.first.comment";

    public static CustomerOrderData withCredentialsFromProperty(){
        return new CustomerOrderData(TestDataReader.getTestData(TESTDATA_ORDERDATA_FULLNAME),
                TestDataReader.getTestData(TESTDATA_ORDERDATA_EMAIL),
                TestDataReader.getTestData(TESTDATA_ORDERDATA_PHONENUMBER),
                TestDataReader.getTestData(TESTDATA_ORDERDATA_ADDRESS),
                TestDataReader.getTestData(TESTDATA_ORDERDATA_COMMENT)
                );
    }

    public static CustomerOrderData withEmptyCredentials(){
        return new CustomerOrderData("", "", "", "", "");
    }
}