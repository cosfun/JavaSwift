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

fun initAst() {
   /* operatorStack = Stack<Pair<String, Int>>()
    dataStack = Stack<Pair<String, Int>>()
    sysStack = Stack<Pair<String, Int>>()*/
}

fun createAst(runtime:Runtime,tokens: MutableList<Pair<String,Int>>) {
    var operatorStack = Stack<Pair<String, Int>>()
    var dataStack = Stack<Pair<String, Int>>()
    var sysStack = Stack<Pair<String, Int>>()
    //var defaultParent=Node(ArrayList<Node>(),"value",0)
    tokens.forEachIndexed { index, it ->
        when (it.second) {
            VAR,VAL,IF,ELSE,FUN,INT -> {
                sysStack.add(it)
            }
            VARIABLE_TYPE, NUMBER_TYPE -> {//data bum
                dataStack.add(it)
            }
            ADD, MINUS-> {//+ - operator
                if(sysStack.size>0&&sysStack[sysStack.size-1].second in listOf(FUN)) {
                    dataStack.add(it)
                }else{
                    if (operatorStack.size > 0 && operatorStack[operatorStack.size - 1].second in ADD..MINUS) {
                        popOperator(sysStack,operatorStack,dataStack,runtime)
                    }
                    operatorStack.add(it)
                }
            }
            MULTIPLY, DIVIDE -> {
                if(sysStack.size>0&&sysStack[sysStack.size-1].second in listOf(FUN)) {
                    dataStack.add(it)
                }else{
                    if (operatorStack.size > 0 && operatorStack[operatorStack.size - 1].second in MULTIPLY..DIVIDE) {
                        popOperator(sysStack,operatorStack,dataStack,runtime)
                    }
                    operatorStack.add(it)
                }
            }
            GREATER, LESS -> {
                operatorStack.add(it)
            }
            LEFT,RIGHT->{
                if(it.second==LEFT) {
                    if(sysStack.size>0&&sysStack[sysStack.size-1].second  in listOf(FUN)) {

                    }else{
                        operatorStack.add(it)
                    }
                }else{
                    if(sysStack.size>0&&sysStack[sysStack.size-1].second in listOf(FUN)) {
                        dataStack.add(it)
                    }else{
                        while (operatorStack[operatorStack.size - 1].second != LEFT) {
                            popOperator(sysStack,operatorStack,dataStack,runtime)
                        }
                        operatorStack.pop()
                    }
                }
            }
            EQUAL -> {
                operatorStack.add(it)
            }
            else -> {
                println(it.second)
            }
        }
    }
    while (dataStack.size > 1) {
        popOperator(sysStack,operatorStack,dataStack,runtime)
    }
    if (dataStack[0].second != 9999) {
        val pair = getPopValue(sysStack,operatorStack,dataStack,runtime)
        when (pair.second) {
            ERROR, NUMBER_TYPE, BOOLEAN_TYPE -> println(pair.first)
            else -> {
            }
        }

    }
}

fun popOperator(sysStack:Stack<Pair<String,Int>>,operatorStack:Stack<Pair<String, Int>>,dataStack:Stack<Pair<String, Int>>,runtime: Runtime) {
    val type =
            if(sysStack.size>0&&sysStack[sysStack.size-1].second in listOf(ELSE,FUN)) {
                sysStack.pop().second
            }else if(operatorStack.size>0){
                operatorStack.pop().second
            }else{
                "useFUN"
            }
    when (type) {
        ADD -> {
            val pair2 = getPopValue(sysStack,operatorStack,dataStack,runtime)
            val num2 = if (pair2.second == NUMBER_TYPE) pair2.first as Int else 0
            val pair1 = getPopValue(sysStack,operatorStack,dataStack,runtime)
            val num1 = if (pair1.second == NUMBER_TYPE) pair1.first as Int else 0
            dataStack.push(Pair((num1 + num2).toString(), NUMBER_TYPE))
        }
        MINUS -> {
            val pair2 = getPopValue(sysStack,operatorStack,dataStack,runtime)
            val num2 = if (pair2.second == NUMBER_TYPE) pair2.first as Int else 0
            val pair1 = getPopValue(sysStack,operatorStack,dataStack,runtime)
            val num1 = if (pair1.second == NUMBER_TYPE) pair1.first as Int else 0
            dataStack.push(Pair((num1 - num2).toString(), NUMBER_TYPE))
        }
        DIVIDE -> {
            val pair2 = getPopValue(sysStack,operatorStack,dataStack,runtime)
            val num2 = if (pair2.second == NUMBER_TYPE) pair2.first as Int else 0
            val pair1 = getPopValue(sysStack,operatorStack,dataStack,runtime)
            val num1 = if (pair1.second == NUMBER_TYPE) pair1.first as Int else 0
            dataStack.push(Pair((num1 / num2).toString(), NUMBER_TYPE))
        }
        MULTIPLY -> {
            val pair2 = getPopValue(sysStack,operatorStack,dataStack,runtime)
            val num2 = if (pair2.second == NUMBER_TYPE) pair2.first as Int else 0
            val pair1 = getPopValue(sysStack,operatorStack,dataStack,runtime)
            val num1 = if (pair1.second == NUMBER_TYPE) pair1.first as Int else 0
            dataStack.push(Pair((num1 * num2).toString(), NUMBER_TYPE))
        }
        EQUAL -> {
            if (sysStack.size > 0 && sysStack[0].second in listOf(VAL,VAR,INT)) {
                sysStack.pop()
                val popData = dataStack.pop()
                var num2: Any = ""
                when (popData.second) {
                    NUMBER_TYPE -> num2 = popData.first.toInt()
                }
                val num1 = dataStack.pop().first

                runtime.variable.put(num1, IInt(num2 as Int))
                dataStack.push(Pair("success", 9999))
            } else if (sysStack.size == 0) {
                val pair2 = getPopValue(sysStack,operatorStack,dataStack,runtime)
                val num2 = if (pair2.second == NUMBER_TYPE) pair2.first as Int else 0
                val pop = dataStack.pop()

                runtime.variable.put(pop.first, IInt(num2))
                dataStack.push(Pair("success", 9999))
            }
        }
        GREATER,LESS -> {
            val pair2 = getPopValue(sysStack,operatorStack,dataStack,runtime)
            val num2 = when (pair2.second) {
                NUMBER_TYPE -> pair2.first as Int
                else -> {
                    pair2.first.toString()
                }
            }
            val pair1 = getPopValue(sysStack,operatorStack,dataStack,runtime)
            val num1 = when (pair1.second) {
                NUMBER_TYPE -> pair1.first as Int
                else -> {
                    pair1.first.toString()
                }
            }
            if (num1 is Int && num2 is Int) {
                if (type == GREATER)
                    dataStack.push(Pair(if (num1 > num2) "T" else "F", BOOLEAN_TYPE))
                else
                    dataStack.push(Pair(if (num1 < num2) "T" else "F", BOOLEAN_TYPE))
            } else if (num2 is String) {
                dataStack.push(Pair(pair2.first.toString(), ERROR))
            } else if (num1 is String) {
                dataStack.push(Pair(pair1.first.toString(), ERROR))
            }
        }
        ELSE->{
            sysStack.pop()
            val numFalse=dataStack.pop()
            val numTrue=dataStack.pop()
            val condition=dataStack.pop()
            if(condition.first.equals("T")) {
                dataStack.push(numTrue)
            }else{
                dataStack.push(numFalse)
            }
        }
        FUN->{
            val funRuntime=Runtime()
            dataStack.pop()
            while (dataStack[dataStack.size-1].second!=RIGHT) {
                val pop=dataStack.pop()
                funRuntime.funTokens.add(0,pop)
            }
            dataStack.pop()
            while(dataStack.size>1){
                val pop=dataStack.pop()
                funRuntime.variable.put(pop.first,IObject())
                funRuntime.factionName.add(0,pop.first)
            }
            val pop=dataStack.pop()
            runtime_root.faction.put(pop.first,funRuntime)
            dataStack.push(Pair("success", 9999))
        }
        "useFUN"->{
            val popList=ArrayList<Pair<String,Int>>()
            while (dataStack.size>1){
                val pop=dataStack.pop()
                popList.add(0,pop)
            }
            val pop=dataStack.pop()
            val funRuntime= runtime_root.faction[pop.first]
            funRuntime?.factionName?.forEachIndexed { index, it ->
               when(popList[index].second) {
                   NUMBER_TYPE->
                    funRuntime?.variable?.put(it, IInt(popList[index].first.toInt()))
                   VARIABLE_TYPE-> {
                       funRuntime?.variable?.put(it,runtime.variable[popList[index].first] as IInt)
                   }

                }
            }
            createAst(funRuntime!!,funRuntime!!.funTokens)
            dataStack.push(Pair("success", 9999))
        }
    }
}

fun getPopValue(sysStack:Stack<Pair<String,Int>>,operatorStack:Stack<Pair<String, Int>>,dataStack:Stack<Pair<String, Int>>,runtime: Runtime): Pair<Any, Int> {
    val pop = dataStack.pop()
    when (pop.second) {
        NUMBER_TYPE -> {
            return Pair(pop.first.toInt(), NUMBER_TYPE)
        }
        BOOLEAN_TYPE, STRING,ERROR -> {
            return Pair(pop.first, pop.second)
        }
        VARIABLE_TYPE -> {
            val variable = runtime.variable[pop.first] ?: return Pair("Error: Java++ Unresolved reference:"+pop.first, ERROR)
            when (variable?.type) {
                NUMBER_TYPE -> return Pair((variable as IInt).value, NUMBER_TYPE)
            }
        }

    }
    return Pair("", STRING_TYPE)
}

interface INode {
    var value: Int
    var token: String
    var count: Int
}

class Node(val son: MutableList<Node>, override var token: String, override var value: Int, var parent: Node? = null) : INode {
    override var count: Int
        get() = son.size
        set(value) {}

}