import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class GetFromHTMLTest {

    static WebDriver driver;
    private static WebElement table;


    @BeforeAll
    public static void getWebElement(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://www.w3schools.com/html/html_tables.asp");
        table = driver.findElement(By.className("ws-table-all"));
    }

    public static Stream<Object[]> dataFromXML() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File("src/main/data.xml"));
        NodeList list = doc.getElementsByTagName("test");
        Object[][] data = new Object[10][4];
        for(int i=0; i<list.getLength();i++){
            for(int j=0;j<4;j++){
                data[i][j] = list.item(i).getChildNodes().item(1+(j*2)).getTextContent();
            }
        }
        return Arrays.stream(data);
    }

    @ParameterizedTest
    @MethodSource("dataFromXML")
    public void test1( int columIndex, String ValueForSearch,int returnColumIndex,String expectedValue) throws Exception {
        GetFromHTML getFromHTML = new GetFromHTML();
        String actualResult = getFromHTML.getTableCellText(table,columIndex,ValueForSearch,returnColumIndex);
        Assert.assertEquals(actualResult,expectedValue);
        Boolean expected = getFromHTML.verifyTableCellText(table,columIndex,ValueForSearch,returnColumIndex,expectedValue);
        Assert.assertEquals(expected,true);
        String actualResultXpath = getFromHTML.getTableCellTextByXpath(table,columIndex,ValueForSearch,returnColumIndex);
        Assert.assertEquals(actualResultXpath,expectedValue);
    }

    @AfterTest
    public static void AfterTest(){
        driver.quit();
    }
}
