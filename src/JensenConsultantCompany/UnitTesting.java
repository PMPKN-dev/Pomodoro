package JensenConsultantCompany;

import JensenConsultantCompany.Foundation.DB;
import JensenConsultantCompany.Foundation.SQLHandler;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;


public class UnitTesting {
    @BeforeClass
    public static void startTesting(){
        System.out.println("Start testing ...");
    }

    @AfterClass
    public static void endTesting(){
        System.out.print("... Done testing");
    }

    @Before
    public void beforeTest() throws Exception {
        System.out.println("... Next test");
    }

    @After
    public void afterTest() throws Exception{
        System.out.println("... No unexpected errors");
    }

    @Test
    public void testCreateConsultant() throws SQLException {
        System.out.println("Testing if we can create new consultant");
        Connection con = DB.getCon();
        SQLHandler.createConsultant(con,"TestGuy03","Test Guy",60,10,40);

        String expected = "TestGuy03";
        String actual = SQLHandler.checkName("TestGuy03");
        assertEquals(expected,actual);

    }


}
