package com.example.calculator4;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;

public class Calculator {
    public static int level(String operator) {
        int lev=0;
        switch(operator) {
            case "+":
            case "-":
                lev=1;
                break;
            case "*":
            case "/":
                lev=2;
                break;
            case "sin":
            case "cos":
            case "tan":
                lev=3;
                break;
            default:
        }
        return lev;
    }

    public static double count1(double v1,double v2,String operator) {//双目运算
        double temp = 0;
        switch(operator) {
            case "+":temp=v1+v2;break;
            case "-":temp=v1-v2;break;
            case "*":temp=v1*v2;break;
            case "/":temp=v1/v2;break;
            default :System.out.print(false);

        }
        return temp;
    }
    public static double count2(double v1,String flag) {//单目运算
        double temp = 0;
        switch(flag) {
            case "sin":temp=Math.sin(v1);break;
            case "cos":temp=Math.cos(v1);break;
            case "tan":temp=Math.tan(v1);break;
            default:
        }
        return temp;
    }
    public static boolean isOperator(String str) {
        boolean flag=false;
        if("#".equals(str)||"sin".equals(str)||"cos".equals(str)||"tan".equals(str)||
                "+".equals(str)||"-".equals(str)||"*".equals(str)||"/".equals(str)||
                "(".equals(str)||")".equals(str)) {
            flag= true;
        }
        return flag;
    }
    public static String compare(String a,String b) {
        String str=null;
        if(("(".equals(a)&&")".equals(b))) {
            str="=";
        }
        else if("(".equals(b)||level(a)<level(b)||")".equals(a)||"#".equals(a)) {
            str="<";
        }
        else
            str=">";
        return str;

    }
    public static String operate(String input) {
        String result=null;
        LinkedList<Double> numberList=new LinkedList<Double>();//数字栈
        LinkedList<String> operatorList=new LinkedList<String>();//运算符栈
        String[] str=input.split(" ");
        LinkedList<String> inputList=new LinkedList<String>();
        for(int i=0;i<str.length;i++) {
            inputList.add(str[i]);
        }
        operatorList.add("#");//使opratorList不为空，进入循环
        inputList.add("#");//为了进行最后一步计算，如果没有，最后一个运算符无法比较
        String temp=inputList.poll();
        while(!operatorList.isEmpty()) {

            if(isOperator(temp)==false) {
                Double num=Double.valueOf(temp);
                numberList.add(num);
                temp=inputList.poll();
            }
            else {
                if(compare(operatorList.getLast(),temp).equals("<")) {
                    operatorList.add(temp);
                    temp=inputList.poll();
                }
                else if(compare(operatorList.getLast(),temp).equals("=")) {
                    operatorList.pollLast();
                    temp=inputList.poll();
                }
                else {
                    String op=operatorList.pollLast();
                    System.out.println(op);
                    double c;
                    if("sin".equals(op)||"cos".equals(op)||"tan".equals(op)) {
                        double a=numberList.pollLast();
                        c=count2(a,op);

                    }
                    else {

                        double a=numberList.pollLast();
                        double b=numberList.pollLast();
                        c=count1(b,a,op);
                    }

                    numberList.add(c);
                    if("#".equals(temp)&&operatorList.size()==1) {
                        break;
                    }
                }
            }
        }
        result=numberList.get(0).toString();
        return result;
    }

}