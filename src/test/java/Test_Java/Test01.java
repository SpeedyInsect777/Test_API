package Test_Java;

import org.junit.jupiter.api.Test;

public class Test01 {

    int num1= 0;

    int num2 = 7;

    int num3;

    @Test
    public void test01(){

        num1=4;
        num2=5;
        num3=6;

        System.out.println("Num1:"+num1);
        System.out.println("Num2:"+num2);
        System.out.println("Num3:"+num3);
    }



    @Test
    public void test02(){
        System.out.println("Num1:"+num1);
        System.out.println("Num2:"+num2);
        System.out.println("Num3:"+num3);

    }

}
