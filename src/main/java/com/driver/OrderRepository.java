package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Repository
public class OrderRepository {

   private static HashMap<String,Order> hmOrder;
   private static HashMap<String,DeliveryPartner> hmDeliveryPartner;
   private static HashMap<String,String> hmOrderPartnerPair;
  private static   HashMap<String,List<String>> hmPartnerListOrder;

    public OrderRepository(){
        hmOrder = new HashMap<>();
        hmDeliveryPartner = new HashMap<>();
        hmOrderPartnerPair = new HashMap<>();
        hmPartnerListOrder = new HashMap<>();
    }
    public void add_order(Order order){

        hmOrder.put(order.getId(),order);
    }
    public void add_partner(String  partnerId){

        hmDeliveryPartner.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void add_order_partner_pair(String orderId,  String partnerId){
        if(!hmOrder.containsKey(orderId) && hmDeliveryPartner.containsKey(partnerId)){
            return;
        }
        hmOrderPartnerPair.put(orderId,partnerId);
        int noOfOrders = hmDeliveryPartner.get(partnerId).getNumberOfOrders();
        hmDeliveryPartner.get(partnerId).setNumberOfOrders(noOfOrders+1);
        List<String> list = new ArrayList<>();
                hmPartnerListOrder.get(partnerId);
        if(hmPartnerListOrder.containsKey(partnerId)){
            list = hmPartnerListOrder.get(partnerId);
        }
        list.add(orderId);
        hmPartnerListOrder.put(partnerId,list);
    }
    public Order get_order_form_id(String orderId){
            return hmOrder.get(orderId);
    }

    public DeliveryPartner get_partner_form_id(String partnerId){
            return hmDeliveryPartner.get(partnerId);
    }

    public Integer get_order_count_by_partner_id(String partnerId){
         return hmDeliveryPartner.get(partnerId).getNumberOfOrders();
    }
    public List<String> get_orders_by_partner_id(String partnerId){
        List<String > orderList = hmPartnerListOrder.get(partnerId);
        return orderList;
    }

    public List<String> get_all_orders(){
        List<String> ans = new ArrayList<>(hmOrder.keySet());
        return ans;
    }

    public Integer get_count_of_unassigned_orders(){
        int assignedOrders = hmOrderPartnerPair.size();
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
        int lastDeliveryTime = 0;
        for(String orderId: orders){
            if(hmOrder.get(orderId).getDeliveryTime()>lastDeliveryTime){
                lastDeliveryTime = hmOrder.get(orderId).getDeliveryTime();
            }
        }
        String hours = String.valueOf(lastDeliveryTime/60);
        String minutes = String.valueOf(lastDeliveryTime%60);
        if(hours.length()<2){
            hours = "0"+hours;
        }
        if(minutes.length()<2){
            minutes = "0"+minutes;
        }

        String lastDelivery =hours +":"+minutes;
        return lastDelivery;
    }

    public void delete_partner_by_id(String partnerId){
        List<String > orders = hmPartnerListOrder.get(partnerId);
        hmPartnerListOrder.remove(partnerId);
        hmDeliveryPartner.remove(partnerId);
        for(String orderId: orders){
           hmOrderPartnerPair.remove(orderId);
        }
    }

    public void delete_order_by_id(String orderId){
        if(!hmOrder.containsKey(orderId)) return;
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
        hmOrder.remove(orderId);
    }


}
