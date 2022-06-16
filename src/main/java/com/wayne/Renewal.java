package com.wayne;

/**
 * 续保费率对象
 * @author wayne
 */
public class Renewal {
    /**
     * 年龄
     */
    private String age;
    /**
     * 是否有医保
     */
    private String isMedicalInsurance;

    /**
     * 保额
     */
    private String amountInsured;

    /**
     * 计划类型
     */
    private String dutyType;

    /**
     * 保费
     */
    private String premium;


    public Renewal() {
    }



    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIsMedicalInsurance() {
        return isMedicalInsurance;
    }

    public void setIsMedicalInsurance(String isMedicalInsurance) {
        this.isMedicalInsurance = isMedicalInsurance;
    }

    public String getAmountInsured() {
        return amountInsured;
    }

    public void setAmountInsured(String amountInsured) {
        this.amountInsured = amountInsured;
    }

    public String getDutyType() {
        return dutyType;
    }

    public void setDutyType(String dutyType) {
        this.dutyType = dutyType;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }


    @Override
    public String toString() {
        return "Renewal{" +
                "age='" + age + '\'' +
                ", isMedicalInsurance='" + isMedicalInsurance + '\'' +
                ", amountInsured='" + amountInsured + '\'' +
                ", dutyType='" + dutyType + '\'' +
                ", premium='" + premium + '\'' +
                '}';
    }
}
