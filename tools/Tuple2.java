package tools;

public class Tuple2 <T, F>{
    private T a;
    private F b;

    public Tuple2(T a, F b){
        this.a=a; this.b=b;
    }
    public T getA() {
        return a;
    }

    public F getB() {
        return b;
    }
}
