package com.example.ccl_emp_sal;

class Detail {

    String mName;
    String mDesignation;
    String mUnitCode;
    String mBasicSalary;
    String mVda;
    String mSda;
    String mGrossAmount;
    String mTotalDeduction;
    String mNetPaid;


    // it will be used to create detail object which will hold fields like basic salary, transport etc

    Detail(String vEmpCode, String vName, String vDesignation, String vUnitCode, String vYear, String vMonth, String vBasicSalary, String vVda, String vSda, String vGrossAmount, String vTotalDeduction, String vNetPaid) {
        mName = vName;
        mDesignation = vDesignation;
        mUnitCode = vUnitCode;
        mBasicSalary = vBasicSalary;
        mVda = vVda;
        mSda = vSda;
        mGrossAmount = vGrossAmount;
        mTotalDeduction = vTotalDeduction;
        mNetPaid = vNetPaid;
    }

}
