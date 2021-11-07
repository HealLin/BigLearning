package corelin.web.bigstudent.index;

import lombok.Getter;

public class MainPlayerOption {

    public String id;

    @Getter
    private String name;

    public String value;

    public MainPlayerOption(String id , String name){
        this.id = id;
        this.name = name;
        this.value = id + name;
    }
}
