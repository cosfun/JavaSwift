import org.w3c.dom.ranges.Range

/**
 * Created by Administrator on 2017/4/30.
 */
val ADD=1
val MINUS=2
val MULTIPLY=3
val DIVIDE=4
val EQUAL=5
val LEFT=6
val RIGHT=7
val GREATER=8
val LESS=9
val NUMBER_TYPE=101
val STRING_TYPE=102
val BOOLEAN_TYPE=102
val VARIABLE_TYPE=100
val SYSTEM_TYPE=1000
val VAL=1001
val VAR=1002
val INT=1003
val STRING=1004
val boolean=1005
val IF=1006
val ELSE=1007
val FUN=1008
val ERROR=10000
var start=0
val systemFlag= arrayOf("+","-","*","/","=","(",")",">","<")
val systemDef= arrayOf("val","var","int","String","boolean","if","else","fun")
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
                tokens.add(Pair(token,1001+index))
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
    }else  {
            start++
            systemFlag.forEachIndexed { index, s ->
                if(s==ch.toString()) {
                    tokens.add(Pair(s, index+1))
                    return
                }
            }
    }
}
