package com.wayne;

/**
 * @author wayne
 */
public class RiskAmount {
    private String risk_code;
    private String risk_amount_code;
    private String parameter_1;
    private String formula;
    private String customer;

    public String getRisk_code() {
        return risk_code;
    }

    public void setRisk_code(String risk_code) {
        this.risk_code = risk_code;
    }

    public String getRisk_amount_code() {
        return risk_amount_code;
    }

    public void setRisk_amount_code(String risk_amount_code) {
        this.risk_amount_code = risk_amount_code;
    }

    public String getParameter_1() {
        return parameter_1;
    }

    public void setParameter_1(String parameter_1) {
        this.parameter_1 = parameter_1;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "RiskAmount{" +
                "risk_code='" + risk_code + '\'' +
                ", risk_amount_code='" + risk_amount_code + '\'' +
                ", parameter_1='" + parameter_1 + '\'' +
                ", formula='" + formula + '\'' +
                ", customer='" + customer + '\'' +
                '}';
    }
}
