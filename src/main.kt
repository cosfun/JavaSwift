/**
 * Created by Administrator on 2017/4/29.
 */


fun main(arg:Array<String>){
      analysisPrint(analysis("(begin (def a 3) (* a a))"))
}

fun analysis(text: String): ArrayList<String> {
    val tokens = text.replace("("," ( ").replace(")"," ) ").split("\\s+|\t|\r|\n".toRegex())
    val anser= ArrayList<String>()
    tokens.forEach {
        if(it!="")
            anser.add(it)
    }
    return anser
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
fun scaner(tokens:ArrayList<String>){

}
