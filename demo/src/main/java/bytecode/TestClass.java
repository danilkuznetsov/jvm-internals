package bytecode;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */

// inner and static classes
// enum
// lambda
// lombok

// stack machine

// registry base evaluation
// but java stack based evaluation

public class TestClass {

    // how look at sysinfo about
    int g;

    public int m(int x) {

        int y = 2;
//        int i = 2;
        int z = y + x + g;
        return z;
    }

    public int ms() {
        return 1;
    }

    // Function<String,Integer> func = str -> str.length();
//
//    int m(){
//        Function<String,Integer> func = str -> str.length();
//        return func.apply("abc");
//    }

//    int x =10;
//
//    class  Node{
//        int p = 10;
//    }
}
//
//
//enum Month{
//   MONTH(1);
//
//    int i;
//
//   Month(int i){
//       this.i = i;
//   }
// }