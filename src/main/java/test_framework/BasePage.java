package test_framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.HashMap;

public class BasePage {
    public void click(HashMap<String,Object> map){
          System.out.println("basePage click");
          System.out.println(map);
//        run调用后， 交给子类重写（覆盖）此方法，实际操作

    }
    public void sendKeys(HashMap<String,Object> map){
           System.out.println("basePage sendKeys");
           System.out.println(map);
    }
    public void action(HashMap<String,Object> map){
            System.out.println("basePage action");
            System.out.println(map) ;
    }

    public void find(){

    }

    public void getText(){

    }
    public void run(UIAuto uiAuto){
        uiAuto.steps.stream().forEach(m ->{

            if(m.containsKey("click")){
                HashMap<String,Object> by= (HashMap<String, Object>) m.get("click");
                  click(by);
            }
            if(m.containsKey("sendKeys")){
                  sendKeys(m);
            }
            if(m.containsKey("action")){
                  action(m);
            }
        });
    }
    public UIAuto load(String path){
        ObjectMapper mapper=new ObjectMapper(new YAMLFactory());  //对一个yaml文件的初始化
        UIAuto uiAuto=null;
        try {
            uiAuto=mapper.readValue(BasePage.class.getResourceAsStream(path),UIAuto.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return uiAuto;
    }
}
