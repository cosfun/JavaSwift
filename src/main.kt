import java.util.*

/**
* Created by Administrator on 2017/4/29.
*/

var target=CharArray(0)
val runtime_root=Runtime()
fun main(arg:Array<String>){
    val scanner = Scanner(System.`in`)
    do {
        print("Java++ >")
        exeCute(scanner.nextLine())
    }while (true)


}
fun exeCute(input: String){
    init()
    target=(input+" ").toCharArray()
    while (start<target.size-1) {
        analysis()
    }
    createAst(runtime_root,tokens)
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
