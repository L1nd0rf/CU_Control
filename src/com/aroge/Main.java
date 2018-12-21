package com.aroge;
import java.util.Scanner;
import java.util.Arrays;

public class Main {

    private byte[] doubleToS7Real(double value)
    {
        byte[] result=new byte[4];

        double f, tmp;
        int i, expo, mant, signe;

        if (value<0)
        {
            value = value*-1;
            signe = 0x80;
        }
        else
            signe =0x00;

        expo = (int)Math.floor(Math.log(value)/Math.log(2))+127;

        f=Math.pow(2,Math.log(value)/Math.log(2)+127-expo)-1;

        mant=0;
        tmp=f;
        for(i=1;i<=24;i++)
        {
            if (tmp/Math.pow(2,-i)>=1.0)
            {
                mant = mant+(0x01<<(24-i));
                tmp = tmp-Math.pow(2,-i);
            }
        }
        result[0] = (byte)((expo>>1)&0x7F|signe);
        result[1] = (byte)((((mant>>1)&(0xFF0000))>>16)+(expo&0x01)*0x80);
        result[2] = (byte)((mant>>1)&(0x00FF00));
        result[3] = (byte)((mant >>1)&(0x0000FF));//0;

//		ByteBuffer.wrap(result).putFloat((float)value);

        return result;
    }

    public static void main(String[] args) {
        byte[] resultTest=new byte[4];
        int j;
        double number = 1.0;

        Arrays.fill(resultTest, (byte)0);
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        do {
            System.out.println("Please enter a value: ");
            number = reader.nextDouble();
            //reader.close();
            System.out.println(number);
            Main converter = new Main();

            resultTest = converter.doubleToS7Real(number);

            for (j = 0; j <= resultTest.length - 1; j++) {
                System.out.println(Integer.toBinaryString(resultTest[j] & 0xFF).replace(" ", "0"));
            }
        } while (number != 0.0);

        reader.close();

    }
}
