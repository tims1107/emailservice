package com.spectra.daily;

public class RESULTS_QUERY {
    private String order_number;
    private String order_test_code;
    private String result_test_code;
    private String eid;

    public RESULTS_QUERY(String order_number, String order_test_code, String result_test_code, String eid) {
        this.order_number = order_number;
        this.order_test_code = order_test_code;
        this.result_test_code = result_test_code;
        this.eid = eid;
    }

    public RESULTS_QUERY() {
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_test_code() {
        return order_test_code;
    }

    public void setOrder_test_code(String order_test_code) {
        this.order_test_code = order_test_code;
    }

    public String getResult_test_code() {
        return result_test_code;
    }

    public void setResult_test_code(String result_test_code) {
        this.result_test_code = result_test_code;
    }

    @Override
    public String toString() {
        return "RESULTS_QUERY{" +
                "order_number='" + order_number + '\'' +
                ", order_test_code='" + order_test_code + '\'' +
                ", result_test_code='" + result_test_code + '\'' +
                ", eid='" + eid + '\'' +
                '}';
    }
}
