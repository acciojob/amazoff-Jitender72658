package com.driver;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    Repository repository;
    public void add_order(Order order){
        repository.add_order(order);
    }
    public void add_partner(String partnerId){
        repository.add_partner(partnerId);
    }

    public void add_order_partner_pair(String orderId,  String partnerId){
        repository.add_order_partner_pair(orderId,partnerId);
    }
    public Order get_order_form_id(String orderId){
       return repository.get_order_form_id(orderId);
    }

    public DeliveryPartner get_partner_form_id(String partnerId){
        return repository.get_partner_form_id(partnerId);
    }

    public Integer get_order_count_by_partner_id(String partnerId){
         return repository.get_order_count_by_partner_id(partnerId);
    }

    public List<String> get_orders_by_partner_id(String partnerId){
        return repository.get_orders_by_partner_id(partnerId);
    }

    public List<String> get_all_orders(){
        return repository.get_all_orders();
    }

    public Integer get_count_of_unassigned_orders(){
        return repository.get_count_of_unassigned_orders();
    }

    public Integer get_count_of_orders_left_after_given_time(String time, String partnerId){
        return repository.get_count_of_orders_left_after_given_time(time,partnerId);
    }

    public String get_last_delivery_time(String partnerId){
        return repository.get_last_delivery_time(partnerId);
    }

    public void delete_partner_by_id(String partnerId){
        repository.delete_partner_by_id(partnerId);
    }

    public void delete_order_by_id(String orderId){
           repository.delete_partner_by_id(orderId);
    }

}
