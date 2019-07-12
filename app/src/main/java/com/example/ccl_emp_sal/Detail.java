package com.example.ccl_emp_sal;

public class Detail {

    public String mEmpCode;
    public String mName;
    public String mDesignation;
    public String mUnitCode;
    public String mYear;
    public String mMonth;
    public String mBasicSalary;
    public String mVda;
    public String mSda;
    public String mGrossAmount;
    public String mTotalDeduction;
    public String mNetPaid;


    // it will be used to create detail object which will hold fields like basic salary, transport etc

    public Detail(String vEmpCode, String vName, String vDesignation, String vUnitCode, String vYear, String vMonth, String vBasicSalary, String vVda, String vSda, String vGrossAmount, String vTotalDeduction, String vNetPaid) {
        mEmpCode = vEmpCode;
        mName = vName;
        mDesignation = vDesignation;
        mUnitCode = vUnitCode;
        mYear = vYear;
        mMonth = vMonth;
        mBasicSalary = vBasicSalary;
        mVda = vVda;
        mSda = vSda;
        mGrossAmount = vGrossAmount;
        mTotalDeduction = vTotalDeduction;
        mNetPaid = vNetPaid;
    }

}
