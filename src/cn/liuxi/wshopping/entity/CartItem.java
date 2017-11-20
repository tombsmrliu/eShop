package cn.liuxi.wshopping.entity;

public class CartItem {

    private Product product;
    private int buyNum;
    private double subtotal;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public double getSubtotal() {

        return this.subtotal;
    }

    public void setSubtotal() {

        this.subtotal = this.product.getShop_price()*this.buyNum;

    }
}
