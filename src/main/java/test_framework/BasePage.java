package test_framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class BasePage {
    private List<PageObjectModel> pages=new ArrayList<>();
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

            //page级别的自动化测试框架
            if(map.containsKey("page")){
                String action=map.get("action").toString();
                String pageName=map.get("page").toString();
                pages.forEach(pom -> System.out.println(pom.name));
                pages.stream()
                        .filter(pom -> pom.name.equals(pageName))
                        .findFirst()
                        .get()
                        .methods.get(action).forEach(step -> {
                            action(step);
                });
            }else {
                //自动化级别
                if(map.containsKey("click")){
                    HashMap<String,Object> by= (HashMap<String, Object>) map.get("click");
                    click(by);
                }
                if(map.containsKey("sendKeys")){
                    sendKeys(map);
                }
            }
    }

    public void find(){
        System.out.println("basePage find");
    }

    public void getText(){
        System.out.println("basePage getText");
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

    public PageObjectModel loadPage(String path){
        ObjectMapper mapper=new ObjectMapper(new YAMLFactory());
        PageObjectModel pom=null;
        try{
            pom=mapper.readValue(new File(path),PageObjectModel.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return pom;
    }

    public void loadPages(String dir){
        Stream.of(new File(dir).list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains("_page");
            }
        })).forEach(path ->{
            path=dir+"/"+path;
            System.out.println(path);
            pages.add(loadPage(path));
        });
    }
}
