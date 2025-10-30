package errors;
public class VectorsCantBeIdentical extends Exception{
    public VectorsCantBeIdentical(){

    }

    @Override
    public String getMessage() {
        return "Vector a can't be a multiple of vector b";
    }
}
