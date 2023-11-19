package com.thelocalmarketplace.software.test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleGold;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.software.Session;
import org.junit.Before;

public class SessionTest {
    public class myItem extends Item {
        Mass mass;
        public myItem(Mass mass) {
            super(mass);
        }
    }
    @Before
    public void setUpSession() {
        ElectronicScaleGold myScale = new ElectronicScaleGold();
        Session mySession = new Session(myScale);
        myItem item1 = new myItem(new Mass(BigDecimal.valueOf(100)));

    }
}
