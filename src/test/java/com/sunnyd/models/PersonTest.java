package com.sunnyd.models;

import com.sunnyd.Base;
import com.sunnyd.IModel;
import com.sunnyd.annotations.*;
import com.sunnyd.database.fixtures.Prep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class PersonTest extends Base implements IModel {

    public static final String tableName = "persons";

    @tableAttr
    private String firstName;

    @tableAttr
    private String lastName;

    private String status;

    public PersonTest() {
        super();
    }

    public PersonTest(HashMap<String, Object> HM) {
        super(HM);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setUpdateFlag(true);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setUpdateFlag(true);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean save() {
        if (firstName != null && firstName.isEmpty())
            return false;
        return super.save();
    }

    /****************************** TEST ********************************************************/

    private static final boolean purgeExistingRecord = true;

    @BeforeClass
    public void init() throws SQLException {
        Prep.init(tableName);
    }
    
    public void prepTable() throws SQLException {
        Prep.purgeAllRecord(tableName);
        Prep.resetPrimaryKey(tableName);
    }

    @Test
    public void TestSave() {
        try {
            prepTable();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PersonTest a = new PersonTest();
        Assert.assertNull(a.getId());
        Assert.assertNull(a.getCreationDate());
        Assert.assertNull(a.getLastModifiedDate());
        Assert.assertNull(a.getFirstName());
        Assert.assertNull(a.getLastName());
        Assert.assertNull(a.getStatus());
        Assert.assertFalse(a.getUpdateFlag());
        a.setStatus("aoidjaoidja");
        a.setFirstName("a");
        a.setLastName("b");
        Assert.assertTrue(a.save());
        Integer id = 1;
        Assert.assertEquals(id.intValue(), a.getId().intValue());
       
    }
    
    
    //Depends on TestSave()
    @Test
    public static void TestFind(){
        PersonTest a = PersonTest.find(1);
        Date today = new Date();
        Assert.assertNull(a.getStatus());
        Assert.assertEquals("a", a.getFirstName());
        Assert.assertEquals("b", a.getLastName());
        Integer id = 1;
        Assert.assertEquals(id.intValue(), a.getId().intValue());

    }

}