package com.globaltravelrule.tools.matching.test;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.junit.Test;

public class PhoneticTest {

    @Test
    public void testPhoneticEncoding() {
        DoubleMetaphone metaphone = new DoubleMetaphone();

        System.out.println("Yonlonfoun: " + metaphone.doubleMetaphone("Yonlonfoun"));
        System.out.println("GNONLONFOUN: " + metaphone.doubleMetaphone("GNONLONFOUN"));

        System.out.println("Rebeca: " + metaphone.doubleMetaphone("Rebeca"));
        System.out.println("REBECCA: " + metaphone.doubleMetaphone("REBECCA"));

        System.out.println("senami: " + metaphone.doubleMetaphone("senami"));
        System.out.println("SENAMI: " + metaphone.doubleMetaphone("SENAMI"));
    }
}
