package org.smart4j.framework.test;

import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.annotation.Transaction;

@Service
public class TransactionTest {

       @Transaction
    public void trans(){
        System.out.println("trans....................................");
    }

}
