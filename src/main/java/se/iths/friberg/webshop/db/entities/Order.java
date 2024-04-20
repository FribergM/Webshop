package se.iths.friberg.webshop.db.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItems;
    private boolean shipped;

    public Order(){

    }

    public Order(User user){
        this.user = user;
        this.orderItems = new HashSet<>();
        this.shipped = false;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Set<OrderItem> getOrderItems(){
        return orderItems;
    }
    public List<OrderItem> getOrderItemsAsList(){
        List<OrderItem> orderItemList = new ArrayList<>(orderItems);

        orderItemList.sort(Comparator.comparingLong(OrderItem::getId));

        return orderItemList;
    }

    public void setOrderItems(Set<OrderItem> orderItems){
        this.orderItems = orderItems;
    }

    public boolean isShipped(){
        return shipped;
    }

    public void setShipped(boolean shipped){
        this.shipped = shipped;
    }
}
