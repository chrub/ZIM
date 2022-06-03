import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import javax.script.ScriptException;
import java.util.List;

public class GetFromHTML {


    public static String getTableCellText(WebElement table, int searchColumn, String searchText, int returnColumnText) throws ScriptException, NoSuchMethodException {

        List<WebElement> trs = table.findElements(By.tagName("tr"));
        for(WebElement tr : trs){
            List<WebElement> tds = tr.findElements(By.tagName("td"));
            String save = null;
            int i=1;
            Boolean flag = false;
            for(WebElement td : tds){
                if(returnColumnText == i){
                    save = td.getText();
                }
                if(searchColumn == i){
                    if(td.getText().equals(searchText)) flag = true;
                }
                i++;
            }
            i=1;
            if(flag){
                return save;
            }
        }
        return null;
    }

    public boolean verifyTableCellText(WebElement table, int searchColumn, String searchText, int returnColumnText, String expectedText) throws ScriptException, NoSuchMethodException {
        return  getTableCellText(table,searchColumn,searchText,returnColumnText).equals(expectedText) ? true : false;
    }

    public String getTableCellTextByXpath(WebElement table, int searchColumn, String searchText, int returnColumnText) throws Exception {
        return table.findElement(By.xpath("//tr//td[text()=\'" + searchText + "\']//parent::*/td["+returnColumnText+"]")).getText();
    }

}
