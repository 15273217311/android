package com.example.calculator4;

public class Converst {
    public static int level(String str) {
        int le=11;
        switch(str) {
            case "cm3":
            case "cm":
                le=1;break;
            case "dm":
                le=2;break;
            case "dm3":
                le=4;break;
            case "m":
                le=3;break;
            case "m3":
                le=7;break;
            default:

        }
        return le;
    }

    public static String converst(String str,String first,String second) {
        Double result;
        String resultString=null;
        double input=Double.valueOf(str);
        int temp=level(first)-level(second);
        if (level(first)<10)
        {
            if(temp==0)
            {
                result=input;
                resultString=Double.toString(result);
            }
            else if (temp==1)
            {
                result=10*input;
                resultString=Double.toString(result);
            }
            else if (temp==2)
            {
                result=100*input;
                resultString=Double.toString(result);
            }
            else if (temp==3)
            {
                result=1000*input;
                resultString=Double.toString(result);
            }
            else if (temp==6)
            {
                result=1000000*input;
                resultString=Double.toString(result);
            }
            else if (temp==-3)
            {
                result=input/1000;
                resultString=Double.toString(result);
            }
            else if (temp==-6)
            {
                result=input/1000000;
                resultString=Double.toString(result);
            }

            else if (temp==-1)
            {
                result=input/10;
                resultString=Double.toString(result);
            }
            else if (temp==-2)
            {
                result=input/100;
                resultString=Double.toString(result);
            }
        }
        else
        {
            if(first.equals(")10") && second.equals(")2"))
            {
                int k=(int)input;
                resultString=Integer.toBinaryString(k);
            }
            else if(first.equals(")10") && second.equals(")8"))
            {
                int k=(int)input;
                resultString=Integer.toOctalString(k);
            }
            else if(first.equals(")10") && second.equals(")16"))
            {
                int k=(int)input;
                resultString=Integer.toHexString(k);
            }

        }
        return resultString;
    }

}
