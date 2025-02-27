import com.paypal.dal.heramockclient.HERAMockAction;
import com.paypal.dal.heramockclient.HERAMockHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class HERAMockImplHelperTest {

    @Test
    public void testListMock() {
        Map<String, String> map = HERAMockHelper.listMock();
        for (String key : map.keySet())
            System.out.println(key + ": " + map.get(key));
        Assert.assertTrue("Mock Size cannot be empty", map.size() > 0);
    }

    @Test
    public void testSetServerIp() {
        String previousIP = HERAMockHelper.getServerIp();
        Assert.assertTrue("Failed to set server ip for mock", HERAMockHelper.setServerIp("test"));
        Assert.assertEquals("test", HERAMockHelper.getServerIp());
        Assert.assertTrue("Failed to revert the Server IP", HERAMockHelper.revertServerIp());
        Assert.assertEquals(previousIP, HERAMockHelper.getServerIp());
    }

    @Test
    public void testSetMock() {
        Assert.assertTrue("Failed to set Mock", HERAMockHelper.addMock("test", "value"));
        Assert.assertEquals("value", HERAMockHelper.getMock("test"));
        Assert.assertTrue("Failed to remove Mock", HERAMockHelper.removeMock("test"));
        Assert.assertNotEquals("value", HERAMockHelper.getMock("test"));
        HERAMockHelper.setMockIP("127.0.0.1");
        Assert.assertEquals("127.0.0.1", HERAMockHelper.getMockIP());
    }

    @Test
    public void testReloadMock() {
        Assert.assertTrue("Failed to Reload Mock", HERAMockHelper.reloadMock());
    }

    @Test
    public void testConnectionMock() {
        Assert.assertTrue("Failed to set custom auth failure",
                HERAMockHelper.simulateCustomAuthConnectionFailure());
        Assert.assertEquals("1005 simulating auth failure", HERAMockHelper.getMock("accept"));
        Assert.assertTrue("Failed to set connect timeout failure",
                HERAMockHelper.simulateConnectionTimeout());
        Assert.assertEquals(HERAMockAction.CONNECTION_TIMEOUT, HERAMockHelper.getMock("accept"));
        Assert.assertTrue("Failed to set clock skew failure",
                HERAMockHelper.simulateConnectionClockSkew());
        Assert.assertEquals("1010 simulating clockskew", HERAMockHelper.getMock("accept"));
        Assert.assertTrue("Failed to set custom auth failure", HERAMockHelper.removeConnectionMock());
        Assert.assertNotEquals("1010 simulating clockskew", HERAMockHelper.getMock("accept"));
    }
}
