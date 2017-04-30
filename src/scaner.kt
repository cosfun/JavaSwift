import org.w3c.dom.ranges.Range

/**
 * Created by Administrator on 2017/4/30.
 */
val ADD=1
val MINUS=2
val MULTIPLY=3
val DIVIDE=4
val EQUAL=5
val NUMBER_TYPE=101
val VARIABLE_TYPE=100
val SYSTEM_TYPE=1000
var start=0
val systemDef= arrayOf("val","var","int","String","boolean")
var tokens= mutableListOf<Pair<String,Int>>()
fun initScanner(){
    start=0
    tokens= mutableListOf<Pair<String,Int>>()
}
fun analysis() {
    var ch=target[start]
    var token=""
    while(ch==' '){
        ch=target[++start]
    }
    if(ch in 'a'..'z' || ch in 'A'..'Z'){
        while (ch in 'a'..'z' || ch in 'A'..'Z' || ch in '1'..'8'){
            token+=ch
            ch=target[++start]
        }
        systemDef.forEachIndexed {index, it ->
            if(token==it) {
                tokens.add(Pair(token,1000+index))
                return
            }
        }
        tokens.add(Pair(token,100))
        return
    }else if(ch in '0'..'9'){
        while(ch in '0'..'9'){
            token+=ch
            ch=target[++start]
        }
        tokens.add(Pair(token,101))
        return
    }else when(ch){
        '='-> {
            start++
            tokens.add(Pair("=",5))
            return
        }
        '+'-> {
            start++
            tokens.add(Pair("+",1))
            return
        }
        '*'-> {
            start++
            tokens.add(Pair("*",3))
            return
        }
    }
}
