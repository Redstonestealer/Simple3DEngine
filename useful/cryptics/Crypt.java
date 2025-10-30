package useful.cryptics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Crypt {
    private String crypt="";
    public Crypt(String crypt){
        this.crypt=crypt;
    }


    private static final int s= 64;
    private static final ArrayList<String> alph = new ArrayList<>(List.of("a b c d e V W X Y Z 1 2 f g h i j k l m I J K L q r s t u v w x y z A B n o p G H C D E F M N O P Q R S T U 3 4 5 6 7 8 9 0 - _".split(" ")));


    private static String getLtrCode(String ltr, Key key, boolean randomness){
        if (!alph.contains(ltr)){
            return null;
        }
        Long index = alph.indexOf(ltr)*key.P1;
        ArrayList<Long> it = new ArrayList<>();
        it.add(index);
        while (it.get(it.size()-1)>=s){
            Long r = it.get(it.size()-1);
            it.set(it.size()-1, r%s);
            it.add(r/s);
        }
        //i<=(Math.log(key)/Math.log(s))+1
        for (int i = 0; i<=(Math.log(key.P1*(s-1))/Math.log(s))+1; i++){
            if (it.size()<i){
                it.add(0L);
            }
        }
        String f="";
        for (Long i:it){
            if (randomness){ f+=alph.get(i.intValue())+generate((key.P2/100000)+1 + (key.P1/1000000000));}
            else{ f+=alph.get(i.intValue());}

        }

        return f;
    }
    private static String getLtrCodes(String s, Key key){
        String f="";
        for (String st: s.split("")){
            f+=getLtrCode(st, key, true);
        }
        return f;
    }
    public static Crypt encrypt(String s, Key key){
        return new Crypt(getLtrCodes(s, key));
    }


    private static String generate(Long l){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = l.intValue();
        Random random = new Random();


        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static String decrypt(Crypt code, Key key, boolean randomness){
        String f=code.crypt;
        if (randomness){
            f="";
            Long bsl=(key.P2/100000) +(key.P1/1000000000) +2;
            for (int i = 0; i<code.crypt.length(); i++){
                if (i%bsl==0){
                    Character c = code.crypt.charAt(i);
                    f+=c.toString();
                }
            }
        }

        int len=encrypt("a", key).crypt.length();
        String t="";
        for (int i = 0; i<f.length()/len; i++){
            String ss=f.substring(i*len, i*len+len);
            ArrayList<Integer> it=new ArrayList<>();
            for (int j = 0; j<ss.length(); j++){
                it.add(alph.indexOf(ss.substring(j, j+1)));
            }
            Double o=0.0;
            for (int r=0; r<it.size(); r++){
                o+=it.get(r)*Math.pow(s, r);
            }
            Long g=o.longValue()/key.P1;
            t+=alph.get(g.intValue());

        }
        return t;
    }
    public static String decrypt(Crypt code, Key key){
        return decrypt(code, key, true);
    }
}