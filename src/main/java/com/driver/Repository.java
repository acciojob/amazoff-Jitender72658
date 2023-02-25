package com.driver;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalDouble;

@org.springframework.stereotype.Repository
public class Repository {
   private HashMap<String,Order> hmOrder;
   private HashMap<String,DeliveryPartner> hmDeliveryPartner;
   private HashMap<String,String> hmOrderPartnerPair;
  private   HashMap<String,List<String >> hmPartnerListOrder;
    public void add_order(Order order){

        hmOrder.put(order.getId(),order);
    }
    public void add_partner(String  partnerId){

        hmDeliveryPartner.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void add_order_partner_pair(String orderId,  String partnerId){
        hmOrderPartnerPair.put(orderId,partnerId);
        hmDeliveryPartner.get(partnerId).setNumberOfOrders(hmDeliveryPartner.get(partnerId).getNumberOfOrders()+1);
        List<String> list = hmPartnerListOrder.get(partnerId);
        list.add(orderId);
        hmPartnerListOrder.put(partnerId,list);
    }
    public Order get_order_form_id(String orderId){
        if(hmOrder.containsKey(orderId)){
            return hmOrder.get(orderId);
        }
        return null;
    }

    public DeliveryPartner get_partner_form_id(String partnerId){
        if(hmDeliveryPartner.containsKey(partnerId)){
            return hmDeliveryPartner.get(partnerId);
        }
        return null;
    }

    public Integer get_order_count_by_partner_id(String partnerId){
         return hmDeliveryPartner.get(partnerId).getNumberOfOrders();
    }
    public List<String> get_orders_by_partner_id(String partnerId){
        List<String > orderList = new ArrayList<>(hmPartnerListOrder.keySet());
        return orderList;
    }

    public List<String> get_all_orders(){
        List<String> ans = new ArrayList<>(hmOrder.keySet());
        return ans;
    }

    public Integer get_count_of_unassigned_orders(){
        int assignedOrders = hmPartnerListOrder.size();
//        for(String partnerId: hmDeliveryPartner.keySet()){
//            assignedOrders += hmDeliveryPartner.get(partnerId).getNumberOfOrders();
//        }
        int totalOrders = hmOrder.size();
        return totalOrders-assignedOrders;
    }

    public Integer get_count_of_orders_left_after_given_time(String time, String partnerId){
        int orderLeft = 0;
        int givenTime = Integer.valueOf(time.substring(0,2))*60+Integer.valueOf(time.substring(3,time.length()));
        List<String> orders = hmPartnerListOrder.get(partnerId);
        for(String orderId: orders){
             if(hmOrder.get(orderId).getDeliveryTime()>givenTime){
                 orderLeft++;
             }
        }
        return orderLeft;
    }

    public String get_last_delivery_time(String partnerId){
        List<String > orders = hmPartnerListOrder.get(partnerId);
        int lastDelivery = 0;
        for(String orderId: orders){
            if(hmOrder.get(orderId).getDeliveryTime()>lastDelivery){
                lastDelivery = hmOrder.get(orderId).getDeliveryTime();
            }
        }
        int hour = lastDelivery/60;
        int minutes = lastDelivery%60;
        String lastDeliveryTime = hour<10?("0"+hour+":"+minutes):(""+hour+":"+minutes);
        return lastDeliveryTime;
    }

    public void delete_partner_by_id(String partnerId){
        List<String > orders = hmPartnerListOrder.get(partnerId);
        for(String orderId: orders){
           hmOrderPartnerPair.remove(orderId);
        }
        hmPartnerListOrder.remove(partnerId);
        hmDeliveryPartner.remove(partnerId);
    }

    public void delete_order_by_id(String orderId){
        int orderDeleted = 0;
        List<String> orderList = hmPartnerListOrder.get(hmOrderPartnerPair.get(orderId));
        for(int i =0;i<orderList.size();i++){
            if(orderList.get(i).equals(orderId)) {
               orderList.remove(i);
               orderDeleted++;
            }
        }
        int noOfOrders = hmDeliveryPartner.get(hmOrderPartnerPair.get(orderId)).getNumberOfOrders();
        hmDeliveryPartner.get(hmOrderPartnerPair.get(orderId)).setNumberOfOrders(noOfOrders-orderDeleted);
        hmOrderPartnerPair.remove(orderId);
    }


}
