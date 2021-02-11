package com.example.billing.domains;

import java.util.Date;

public class Application {
    private int id;
    private Date date;
    private int billing;
    private String application;
    private String result;
    private String remarks;

    public Application(String application){
        if(application.matches("\\d*")){ 
            this.billing = Integer.parseInt(application);
        }else{
            this.application = application;
        }
    }

    public Application(int id, Date date, int billing, String application){
        this.id = id;
        this. date = date;
        this.billing = billing;
        this.application = application;
    }

    public Application(Date date, int billing, String application, String result, String remarks){
        this. date = date;
        this.billing = billing;
        this.application = application;
        this.result = result;
        this.remarks = remarks;
    }

    public Application(int id, Date date, int billing, String application, String result, String remarks){
        this.id = id;
        this. date = date;
        this.billing = billing;
        this.application = application;
        this.result = result;
        this.remarks = remarks;
    }

    public int getId(){ return this.id; }
    public void setId(int id){ this.id = id; }
    public Date getDate(){ return this.date; }
    public void setDate(Date date){ this.date = date; }
    public int getBilling(){ return this.billing; }
    public void setBilling(int billing){ this.billing = billing; }
    public String getApplication(){ return this.application; }
    public void setApplication(String application){ this.application = application; }
    public String getResult(){ return this.result; }
    public void setResult(String result){ this.result = result; }
    public String getRemarks(){ return this.remarks; }
    public void setRemarks(String remarks){ this.remarks = remarks; }
}
