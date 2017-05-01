/**
 * Created by Administrator on 2017/5/1.
 */
open class IObject{
    open val type=-1
}
class IInt( var value:Int=0): IObject() {
    override val type=NUMBER_TYPE

}
