package cn.liuxi.wshopping.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private String oid; // 该订单的订单号
     private Date ordertime; //下单时间
     private double total; // 订单的总金额
     private int status; //订单的支付状态 1 代表已支付 0代表未付款

     private String addr; //收货地址
     private String fullname; //收货人
     private String telephone; //收货人电话

     private User user;//该订单属于哪个用户


     //该订单中的订单项
     private List<OrderItem> orderItems = new ArrayList<OrderItem>();


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


    @Override
    public String toString() {
        return "Order{" +
                "oid='" + oid + '\'' +
                ", ordertime=" + ordertime +
                ", total=" + total +
                ", status=" + status +
                ", addr='" + addr + '\'' +
                ", fullname='" + fullname + '\'' +
                ", telephone='" + telephone + '\'' +
                ", user=" + user +
                ", orderItems=" + orderItems +
                '}';
    }


}
