package com.github.satr.aws;

import com.amazonaws.regions.Regions;
import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.aws.regions.RegionsValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegionsValidatorTest {
    @Test
    public void valid() {
        assertTrue(RegionsValidator.valid("us-east-1"));
        assertTrue(RegionsValidator.valid("us-gov-west-1"));
        assertTrue(RegionsValidator.valid("ap-northeast-2"));
        assertTrue(RegionsValidator.valid(Regions.US_EAST_1.getName()));
    }

    @Test
    public void invalid() {
        assertFalse(RegionsValidator.valid(Regions.US_EAST_1.toString()));
        assertFalse(RegionsValidator.valid(Regions.US_EAST_1.name()));
        assertFalse(RegionsValidator.valid(String.format("%s", Regions.US_EAST_1)));
        assertFalse(RegionsValidator.valid("US_EAST_1"));
        assertFalse(RegionsValidator.valid("text"));
        assertFalse(RegionsValidator.valid(""));
        assertFalse(RegionsValidator.valid("   "));
        assertFalse(RegionsValidator.valid(null));
        assertFalse(RegionsValidator.valid("us-east-1   "));
        assertFalse(RegionsValidator.valid("Us-east-1"));
        assertFalse(RegionsValidator.valid("US-EAST-1"));
    }

    @Test
    public void getValidated() throws InvalidRegionNameException {
        assertEquals("us-east-1", RegionsValidator.getValidated("us-east-1"));
        assertEquals("us-gov-west-1", RegionsValidator.getValidated("us-gov-west-1"));
        assertEquals("ap-northeast-2", RegionsValidator.getValidated("ap-northeast-2"));
        assertEquals("us-east-1", RegionsValidator.getValidated(Regions.US_EAST_1.getName()));
    }

    @Test(expected = InvalidRegionNameException.class)
    public void getValidatedFailedWithToString() throws InvalidRegionNameException {
        RegionsValidator.getValidated(Regions.US_EAST_1.toString());
    }

    @Test(expected = InvalidRegionNameException.class)
    public void getValidatedFailedWithEnumsName() throws InvalidRegionNameException {
        RegionsValidator.getValidated(Regions.US_EAST_1.name());
    }

    @Test(expected = InvalidRegionNameException.class)
    public void getValidatedFailedWithCapitalLettersAndUndersore() throws InvalidRegionNameException {
        RegionsValidator.getValidated("US_EAST_1");
    }

    @Test(expected = InvalidRegionNameException.class)
    public void getValidatedFailedWithArbitraryString() throws InvalidRegionNameException {
        RegionsValidator.getValidated("text");
    }

    @Test(expected = InvalidRegionNameException.class)
    public void getValidatedFailedWithEmptyString() throws InvalidRegionNameException {
        RegionsValidator.getValidated("");
    }

    @Test(expected = InvalidRegionNameException.class)
    public void getValidatedFailedWithSpaces() throws InvalidRegionNameException {
        RegionsValidator.getValidated("   ");
    }

    @Test(expected = InvalidRegionNameException.class)
    public void getValidatedFailedWithNull() throws InvalidRegionNameException {
        RegionsValidator.getValidated(null);
    }

    @Test(expected = InvalidRegionNameException.class)
    public void getValidatedFailedWithExtraSpaces() throws InvalidRegionNameException {
        RegionsValidator.getValidated("us-east-1   ");
    }

    @Test(expected = InvalidRegionNameException.class)
    public void getValidatedFailedWithCapitalChar() throws InvalidRegionNameException {
        RegionsValidator.getValidated("Us-east-1");
    }

    @Test(expected = InvalidRegionNameException.class)
    public void getValidatedFailedWithAllCapital() throws InvalidRegionNameException {
        RegionsValidator.getValidated("US-EAST-1");
    }
}