/**
 * Created by Administrator on 2017/5/1.
 */
class Runtime{
        val variable = mutableMapOf<String, IObject>()
        val son=ArrayList<Runtime>()
        val parent=ArrayList<Runtime>()
        val faction= mutableMapOf<String,Runtime>()
        val factionName=ArrayList<String>()
        val funTokens= mutableListOf<Pair<String,Int>>()
}
