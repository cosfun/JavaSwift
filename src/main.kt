/**
 * Created by Administrator on 2017/4/29.
 */

var target=CharArray(0)
fun main(arg:Array<String>){
    val input="val a=3"
    target=(input+" ").toCharArray()
    while (start<target.size-1) {
        analysis()
    }
}


fun analysisPrint(text:List<String>){
   var anser= "[";
    text.forEachIndexed { index, s ->
            anser += "'" + s + "'"
            if (index < text.size - 1)
                anser += ","
    }
    anser+="]"
   println(anser)
}
