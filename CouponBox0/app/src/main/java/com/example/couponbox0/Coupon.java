
package com.example.couponbox0;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coupon{
    public String sender="";
    public String content="";
    public String discount="";
    public String duedate="";
    public String couponcode="";
    public String category = "";

    public void setDiscount() {
        Pattern pattern = Pattern.compile("(flat|Flat)( )?((Inr|INR|inr|Rs|rs|RS)(\\.|\\-)?( )?[0-9]+)|([0-9]+( )?%)( )?(OFF|off|Off|Cashback|cashback)");
        Matcher matcher = pattern.matcher(content);
        if(matcher.find())
            discount = matcher.group(0).replaceAll("flat|Flat|OFF|off|Off|Cashback|cashback","") ;
        else {
            pattern = Pattern.compile("[0-9]+\\/\\-( )?(OFF|off|Off|Cashback|cashback)");
            matcher = pattern.matcher(content);
            if (matcher.find())
                discount = matcher.group(0).replaceAll("flat|Flat|OFF|off|Off|Cashback|cashback", "");
        }
    }

    public void setCouponcode() {
        Pattern pattern = Pattern.compile("(Use|use|apply|Apply|apply promo|Apply Promo|with code)( )?(code|Code)?(:)?( )?(([a-zA-Z]+|[0-9]+)([a-zA-Z]+)?([0-9]+)?([a-zA-Z]+)?([0-9]+)?)");
        Matcher matcher = pattern.matcher(content);
        if(matcher.find())
            couponcode = matcher.group(0).replaceAll("(Use|use|apply|Apply|apply promo|Apply Promo|with code)( )?(code|Code)?(:)?( )?","") ;
        if(couponcode==couponcode.toLowerCase())
            couponcode = "";
    }

    public boolean isValidCoupon(){
        return couponcode.length()>0 || discount.length()>0;
    }

    public String getCategory(){
        if(isValidCoupon()) {
            if(sender.contains("MDLIFE")||sender.contains("MEDLAB")||sender.contains("MEDLFE"))
                return "health";
            if(sender.contains("HAPYGO")||sender.contains("TRAVEL"))
                return "travel";
            return "coupon";
        }
        return "other";
    }
}
