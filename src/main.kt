import java.util.*

/**
* Created by Administrator on 2017/4/29.
*/

var target=CharArray(0)
fun main(arg:Array<String>){
    val scanner = Scanner(System.`in`)
    do {
        print("scanner >")
        exeCute(scanner.next())
    }while (true)


}
fun exeCute(input: String){
    init()
    target=(input+" ").toCharArray()
    while (start<target.size-1) {
        analysis()
    }
    createAst()
}
fun init(){
    initScanner()
    initAst()
}
fun analysisPrint(text:List<String>){
   var answer= "["
    text.forEachIndexed { index, s ->
            answer += "'$s'"
            if (index < text.size - 1)
                answer += ","
    }
    answer+="]"
   println(answer)
}
