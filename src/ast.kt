import org.w3c.dom.ranges.Range
import java.lang.reflect.Array.get
import java.util.*
import kotlin.collections.ArrayList

/*
+  1
-  2
*  3
/  4
=  5
variable 100
num 101
systemDef 1000
 */
var operatorStack=Stack<Pair<String,Int>>()
var dataStack= Stack<Pair<String,Int>>()
fun initAst(){
     operatorStack=Stack<Pair<String,Int>>()
     dataStack= Stack<Pair<String,Int>>()
}
fun createAst(){
    var rootNode=Node(ArrayList<Node>(),0,"root")
    var currentNode=rootNode
    tokens.forEachIndexed{index,it->
         when(it.second){
             101->{//data bum
                 dataStack.add(it)
                /* if(currentNode.count>0&&currentNode.son[currentNode.count-1].value in 0..10){
                     currentNode.son.add(Node(ArrayList<Node>(),0,"value",currentNode))
                     currentNode=currentNode.son[currentNode.count-1]
                 }
                  currentNode.son.add(Node(ArrayList<Node>(), it.second, it.first,currentNode))*/
             }
             ADD,MINUS->{//+ - operator
                 if(operatorStack.size>0 && operatorStack[operatorStack.size-1].second in ADD..MINUS) {
                    //val
                     popOperator()
                 }
                     operatorStack.add(it)


               /*  if(currentNode.count>0&&currentNode.son[currentNode.count-1].value in 0..10){
                     currentNode.son.add(Node(ArrayList<Node>(),0,"value",currentNode))
                     currentNode=currentNode.son[currentNode.count-1]
                 }
                 currentNode.son.add(Node(ArrayList<Node>(), it.second, it.first,currentNode))*/
             }
             MULTIPLY,DIVIDE->{
                 if(operatorStack.size>0 && operatorStack[operatorStack.size-1].second in MULTIPLY..DIVIDE) {
                     popOperator()
                 }
                 operatorStack.add(it)

             }
             else->{
                 println(it.second)
             }
         }
    }
    popOperator()
    println(dataStack[dataStack.size-1])
}
fun popOperator(){
    val type=operatorStack.pop().second
    when(type) {
      ADD-> {
          val num2 = dataStack.pop().first.toInt()
          val num1 = dataStack.pop().first.toInt()
          dataStack.push(Pair((num1 + num2).toString(), NUMBER_TYPE))
      }
        MINUS->{
            val num2 = dataStack.pop().first.toInt()
            val num1 = dataStack.pop().first.toInt()
            dataStack.push(Pair((num1 - num2).toString(), NUMBER_TYPE))
        }
        DIVIDE->{
            val num2 = dataStack.pop().first.toInt()
            val num1 = dataStack.pop().first.toInt()
            dataStack.push(Pair((num1 / num2).toString(), NUMBER_TYPE))
        }
        MULTIPLY->{
            val num2 = dataStack.pop().first.toInt()
            val num1 = dataStack.pop().first.toInt()
            dataStack.push(Pair((num1 * num2).toString(), NUMBER_TYPE))
        }
    }
}
interface INode{
    var value:Int
    var token:String
    var count:Int
}
class Node(val son:MutableList<Node>, override var value: Int, override var token: String,val parent:Node?=null):INode {
    override var count: Int
        get() = son.size
        set(value) {}

}