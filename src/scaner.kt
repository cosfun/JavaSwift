/**
 * Created by Administrator on 2017/4/30.
 */
/*
+  1
-  2
*  3
/  4
=  5



 */
var start=0;
val systemDef= arrayOf("val","var","int","String","boolean")
fun analysis() {
    var ch=target[start]
    var token=""
    while(ch==' '){
        ch=target[++start]
    }
    if(ch>='a'&&ch<='z'||ch>='A'&&ch<='Z'){
        while (ch>='a'&&ch<='z'||ch>='A'&&ch<='Z'||ch>'0'&&ch<'9'){
            token+=ch
            ch=target[++start]
        }
        systemDef.forEachIndexed { index, it ->
            if(token==it) {
                println("token is "+token+",id="+(index+10))
                return
            }
        }
        println("token is "+token+",id=0")
        return
    }else if(ch>='0'&&ch<='9'){
        while(ch>='0'&&ch<='9'){
            token+=ch
            ch=target[++start]
        }
        println("token is "+token+",id=0")
        return
    }else when(ch){
        '='-> {
            start++
            println("token is =,id=5")
            return
        }
    }
}
