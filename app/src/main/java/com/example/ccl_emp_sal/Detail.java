package com.example.ccl_emp_sal;

public class Detail {

    public String mPis;
    public String mBasicSalary;
    public String mTransportAllowance;
    public String mMiscellaneous;
    public String mProfessionalTax;

    // it will be used to create detail object which will hold fields like basic salary, transport etc

    public Detail(String vPis, String vSalary, String vTransportAllowance, String vMiscellaneous, String vProfessionalTax) {
        mPis = vPis;
        mBasicSalary = vSalary;
        mTransportAllowance = vTransportAllowance;
        mMiscellaneous =vMiscellaneous;
        mProfessionalTax = vProfessionalTax;
    }

}
