import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.technicalkeeda.app.CheckingMails;

public class CheckingMailsTest {

    @BeforeClass
    public static void befor(){

    }

    @Test
    public void test1() throws Exception {
        CheckingMails checkingMails = new CheckingMails("catz45774@gmail.com","sara1qaz@#123","aaa");
        checkingMails.findMail();
    }

    @AfterClass
    public static void after(){

    }
}
