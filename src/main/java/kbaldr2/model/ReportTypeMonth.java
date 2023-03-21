package kbaldr2.model;

public class ReportTypeMonth {
    
    String type;
    int month;
    int qty;
    
    public ReportTypeMonth(String type, int month, int qty) {
        
        this.type = type;
        this.month = month;
        this.qty = qty;
    }
    
    public String getType() {
        
        return type;
    }
    
    public void setType(String type) {
        
        this.type = type;
    }
    
    public int getMonth() {
        
        return month;
    }
    
    public void setMonth(int month) {
        
        this.month = month;
    }
    
    public int getQty() {
        
        return qty;
    }
    
    public void setQty(int qty) {
        
        this.qty = qty;
    }
    
}
