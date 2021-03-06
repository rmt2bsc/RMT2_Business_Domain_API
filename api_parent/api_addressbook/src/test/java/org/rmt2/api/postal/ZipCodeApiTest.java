package org.rmt2.api.postal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.dao.lookup.LookupDaoException;
import org.dao.mapping.orm.rmt2.Zipcode;
import org.dao.postal.ZipcodeDaoException;
import org.dto.ZipcodeDto;
import org.dto.adapter.orm.Rmt2AddressBookDtoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modules.AddressBookConstants;
import org.modules.postal.PostalApi;
import org.modules.postal.PostalApiException;
import org.modules.postal.PostalApiFactory;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.api.BaseAddressBookDaoTest;

import com.api.persistence.AbstractDaoClientImpl;
import com.api.persistence.DatabaseException;
import com.api.persistence.db.orm.Rmt2OrmClientFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AbstractDaoClientImpl.class, Rmt2OrmClientFactory.class })
public class ZipCodeApiTest extends BaseAddressBookDaoTest {
    private List<Zipcode> mockSingleFetchResponse;
    private List<Zipcode> mockCriteriaFetchResponse;
    private List<Zipcode> mockFetchAllResponse;
    private List<Zipcode> mockNotFoundFetchResponse;
    private Zipcode mockNotFoundUidFetchResponse;
    private Zipcode mockSingleUidFetchResponse;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.mockSingleFetchResponse = this.createMockSingleFetchResponse();
        this.mockCriteriaFetchResponse = this.createMockFetchUsingCriteriaResponse();
        this.mockFetchAllResponse = this.createMockFetchAllResponse();
        this.mockNotFoundFetchResponse = this.createMockNotFoundSearchResultsResponse();
        this.mockNotFoundUidFetchResponse = this.createMockNotFoundUidSearchResultsResponse();
        this.mockSingleUidFetchResponse = this.createMockSingleUidFetchResponse();
    }

    @After
    public void tearDown() throws Exception {
    }

    private List<Zipcode> createMockNotFoundSearchResultsResponse() {
        List<Zipcode> list = null;
        return list;
    }
    
    private Zipcode createMockNotFoundUidSearchResultsResponse() {
        Zipcode rec = null;
        return rec;
    }

    private List<Zipcode> createMockSingleFetchResponse() {
        List<Zipcode> list = new ArrayList<Zipcode>();
        Zipcode p = this.createMockOrm(71106,71106, "LA", "Shreveport", "318", "Caddo", 6);
        list.add(p);
        return list;
    }
    
    private Zipcode createMockSingleUidFetchResponse() {
        Zipcode p = this.createMockOrm(71106,71106, "LA", "Shreveport", "318", "Caddo", 6);
        return p;
    }

    /**
     * Use for the following selection criteria: where last name begins with 'C'
     * 
     * @return
     */
    private List<Zipcode> createMockFetchUsingCriteriaResponse() {
        List<Zipcode> list = new ArrayList<Zipcode>();
        Zipcode p = this.createMockOrm(75232,75232, "TX", "Dallas", "214", "Dallas", 6);
        list.add(p);

        p = this.createMockOrm(75028,75028, "TX", "Flower Mound", "972", "Denton", 6);
        list.add(p);

        return list;
    }

    private List<Zipcode> createMockFetchAllResponse() {
        List<Zipcode> list = new ArrayList<Zipcode>();
        Zipcode p = this.createMockOrm(75231,75231, "State1", "City1", "AreaCode1", "County1", 6);
        list.add(p);

        p = this.createMockOrm(75232,75232, "State2", "City2", "AreaCode2", "County2", 6);
        list.add(p);

        p = this.createMockOrm(75233,75233, "State3", "City3", "AreaCode3", "County3", 6);
        list.add(p);

        p = this.createMockOrm(75234,75234, "State4", "City4", "AreaCode4", "County4", 6);
        list.add(p);
        
        p = this.createMockOrm(75235,75235, "State5", "City5", "AreaCode5", "County5", 6);
        list.add(p);
        
        return list;
    }

    private ZipcodeDto createMockDto(int zipId, int zipCode, String state, String city, String areaCode, String countyName, int timeZoneId) {
        ZipcodeDto dto = Rmt2AddressBookDtoFactory.getNewZipCodeInstance();
        dto.setId(zipId);
        dto.setZip(zipCode);
        dto.setStateCode(state);
        dto.setCity(city);
        dto.setAreaCode(areaCode);
        dto.setCountyName(countyName);
        dto.setTimeZoneId(timeZoneId);
        dto.setLatitude(382372382323.3883828);
        dto.setLongitude(48484848.4843949);
        dto.setBlackPopulation(239000);
        dto.setWhitePopulation(10000000);
        dto.setHispanicPopulation(30000);
        dto.setCityAliasName(city + "_Alias");
        dto.setAverageHouseValue(87674.84);
        dto.setCbsa(123.88);
        dto.setCbsaDiv(23.88);
        dto.setCityAliasAbbr("AAB");
        dto.setCityTypeId("City_type");
        dto.setCountiesArea(333);
        dto.setCountyFips("Counties FIPS");
        dto.setDayLightSaving("TRUE");
        dto.setElevation(2345.89);
        dto.setHouseholdsPerZipcode(600);
        dto.setIncomePerHousehold(569.76);
        dto.setMsa(757575);
        dto.setPersonsPerHousehold(4);
        dto.setPmsa(123);
        dto.setZipPopulation(25000);
        return dto;
    }
    
    private Zipcode createMockOrm(int zipId, int zipCode, String state, String city, String areaCode, String countyName, int timeZoneId) {
        Zipcode dto = new Zipcode();
        dto.setZipId(zipId);
        dto.setZip(zipCode);
        dto.setState(state);
        dto.setCity(city);
        dto.setAreaCode(areaCode);
        dto.setCountyName(countyName);
        dto.setTimeZoneId(timeZoneId);
        dto.setLatitude(382372382323.3883828);
        dto.setLongitude(48484848.4843949);
        dto.setBlackPopulation(239000);
        dto.setWhitePopulation(10000000);
        dto.setHispanicPopulation(30000);
        dto.setCityAliasName(city + "_Alias");
        dto.setAverageHouseValue(87674.84);
        dto.setCbsa(123.88);
        dto.setCbsaDiv(23.88);
        dto.setCityAliasAbbr("AAB");
        dto.setCityTypeId("City_type");
        dto.setCountiesArea(333);
        dto.setCountyFips("Counties FIPS");
        dto.setDayLightSaving("TRUE");
        dto.setElevation(2345.89);
        dto.setHouseholdsPerZipcode(600);
        dto.setIncomePerHousehold(569.76);
        dto.setMsa(757575);
        dto.setPersonsPerHousehold(4);
        dto.setPmsa(123);
        dto.setZipcodePopulation(25000);
        return dto;
    }
    @Test
    public void testFetchAll() {
        ZipcodeDto criteria = Rmt2AddressBookDtoFactory.getNewZipCodeInstance();
        criteria.setTimeZoneId(6);
        try {
            when(this.mockPersistenceClient.retrieveList(any(Zipcode.class))).thenReturn(this.mockFetchAllResponse);
        } catch (LookupDaoException e) {
            e.printStackTrace();
            Assert.fail("All zipcode fetch test case failed");
        }
        PostalApiFactory f = new PostalApiFactory();
        PostalApi api = f.createApi(AddressBookConstants.APP_NAME);
        List<ZipcodeDto> results = null;
        try {
            results = api.getZipCode(criteria);
        } catch (PostalApiException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(results);
        Assert.assertEquals(5, results.size());
        
        for (int ndx = 0; ndx < results.size(); ndx++) {
            ZipcodeDto obj = results.get(ndx);
            Assert.assertEquals(obj.getZip(), (75231 + ndx));
            Assert.assertEquals(obj.getId(), (75231 + ndx));
            Assert.assertEquals(obj.getStateCode(), "State" + (ndx + 1));
            Assert.assertEquals(obj.getCity(), "City" + (ndx + 1));
            Assert.assertEquals(obj.getAreaCode(), "AreaCode" + (ndx + 1));
            Assert.assertEquals(obj.getCountyName(), "County" + (ndx + 1));
            Assert.assertEquals(obj.getTimeZoneId(), 6);
        }
    }
    
    @Test
    public void testFetchNotFoundWithCriteria() {
        ZipcodeDto criteria = Rmt2AddressBookDtoFactory.getNewZipCodeInstance();
        criteria.setTimeZoneId(-847384);
        try {
            when(this.mockPersistenceClient.retrieveList(any(Zipcode.class))).thenReturn(this.mockNotFoundFetchResponse);
        } catch (LookupDaoException e) {
            e.printStackTrace();
            Assert.fail("zipcode not found fetch test case failed");
        }
        PostalApiFactory f = new PostalApiFactory();
        PostalApi api = f.createApi(AddressBookConstants.APP_NAME);
        List<ZipcodeDto> results = null;
        try {
            results = api.getZipCode(criteria);
        } catch (PostalApiException e) {
            e.printStackTrace();
        }
        Assert.assertNull(results);
    }
    
    @Test
    public void testFetchWithEmptyCriteria() {
        ZipcodeDto criteria = Rmt2AddressBookDtoFactory.getNewZipCodeInstance();
        try {
            when(this.mockPersistenceClient.retrieveList(any(Zipcode.class))).thenReturn(this.mockFetchAllResponse);
        } catch (LookupDaoException e) {
            e.printStackTrace();
            Assert.fail("zipcode fetch with empty criteria test case failed");
        }
        PostalApiFactory f = new PostalApiFactory();
        PostalApi api = f.createApi(AddressBookConstants.APP_NAME);
        try {
            api.getZipCode(criteria);
            Assert.fail("Expected exception to be thrown due to empty criteria");
        } catch (PostalApiException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFetchWithNullCriteria() {
        ZipcodeDto criteria = null;
        try {
            when(this.mockPersistenceClient.retrieveList(any(Zipcode.class))).thenReturn(this.mockFetchAllResponse);
        } catch (LookupDaoException e) {
            e.printStackTrace();
            Assert.fail("zipcode fetch with null criteria test case failed");
        }
        PostalApiFactory f = new PostalApiFactory();
        PostalApi api = f.createApi(AddressBookConstants.APP_NAME);
        try {
            api.getZipCode(criteria);
            Assert.fail("Expected exception to be thrown due to null criteria");
        } catch (PostalApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFetchSingle() {
        ZipcodeDto criteria = Rmt2AddressBookDtoFactory.getNewZipCodeInstance();
        criteria.setZip(71106);
        try {
            when(this.mockPersistenceClient.retrieveList(any(Zipcode.class))).thenReturn(this.mockSingleFetchResponse);
        } catch (LookupDaoException e) {
            e.printStackTrace();
            Assert.fail("Single zipcode fetch test case failed");
        }
        PostalApiFactory f = new PostalApiFactory();
        PostalApi api = f.createApi(AddressBookConstants.APP_NAME);
        List<ZipcodeDto> results = null;
        try {
            results = api.getZipCode(criteria);
        } catch (PostalApiException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        ZipcodeDto rec = results.get(0);
        Assert.assertEquals(71106, rec.getId());
        Assert.assertEquals(71106, rec.getZip());
        Assert.assertEquals("Shreveport", rec.getCity());
        Assert.assertEquals("LA", rec.getStateCode());
        Assert.assertEquals("318", rec.getAreaCode());
    }
    
    @Test
    public void testFetchByUid() {
        try {
            when(this.mockPersistenceClient.retrieveObject(any(Zipcode.class))).thenReturn(this.mockSingleUidFetchResponse);
        } catch (LookupDaoException e) {
            e.printStackTrace();
            Assert.fail("UID zipcode fetch test case failed");
        }
        PostalApiFactory f = new PostalApiFactory();
        PostalApi api = f.createApi(AddressBookConstants.APP_NAME);
        ZipcodeDto rec = null;
        try {
            rec = api.getZipCode(71106);
        } catch (PostalApiException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(rec);
        Assert.assertEquals(71106, rec.getId());
        Assert.assertEquals(71106, rec.getZip());
        Assert.assertEquals("Shreveport", rec.getCity());
        Assert.assertEquals("LA", rec.getStateCode());
        Assert.assertEquals("318", rec.getAreaCode());
    }
    
    @Test
    public void testFetchNotFoundByUid() {
        try {
            when(this.mockPersistenceClient.retrieveObject(any(Zipcode.class))).thenReturn(this.mockNotFoundUidFetchResponse);
        } catch (LookupDaoException e) {
            e.printStackTrace();
            Assert.fail("UID zipcode not found fetch test case failed");
        }
        PostalApiFactory f = new PostalApiFactory();
        PostalApi api = f.createApi(AddressBookConstants.APP_NAME);
        ZipcodeDto rec = null;
        try {
            rec = api.getZipCode(99999);
        } catch (PostalApiException e) {
            e.printStackTrace();
        }
        Assert.assertNull(rec);
    }
    
    @Test
    public void testFetchAllDaoException() {
        ZipcodeDto criteria = Rmt2AddressBookDtoFactory.getNewZipCodeInstance();
        criteria.setTimeZoneId(6);
        try {
            when(this.mockPersistenceClient.retrieveList(any(Zipcode.class)))
            .thenThrow(DatabaseException.class);
        } catch (LookupDaoException e) {
            e.printStackTrace();
            Assert.fail("All zipcode fetch test case failed");
        }
        PostalApiFactory f = new PostalApiFactory();
        PostalApi api = f.createApi(AddressBookConstants.APP_NAME);
        List<ZipcodeDto> results = null;
        try {
            results = api.getZipCode(criteria);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof PostalApiException);
            Assert.assertTrue(e.getCause() instanceof ZipcodeDaoException);
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFetchByUidDaoException() {
        try {
            when(this.mockPersistenceClient.retrieveObject(any(Zipcode.class)))
            .thenThrow(DatabaseException.class);
        } catch (LookupDaoException e) {
            e.printStackTrace();
            Assert.fail("UID zipcode fetch test case failed");
        }
        PostalApiFactory f = new PostalApiFactory();
        PostalApi api = f.createApi(AddressBookConstants.APP_NAME);
        ZipcodeDto rec = null;
        try {
            rec = api.getZipCode(71106);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof PostalApiException);
            Assert.assertTrue(e.getCause() instanceof ZipcodeDaoException);
            e.printStackTrace();
        }
    }
}
