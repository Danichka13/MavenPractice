package stage2.partTwo.models;

import java.util.Objects;

public class BaseModel {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BaseModel(){}

    public BaseModel(long id){
        this.id = id;
    }

    @Override
    public boolean equals(Object temp){
        if(this == temp){
            return true;
        }
        if(temp == null || getClass() != temp.getClass()){
            return false;
        }
        BaseModel bM = (BaseModel) temp;
        return id == bM.id;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
