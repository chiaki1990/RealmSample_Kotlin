/////////////////////////////////////////////////////////////////////////

//create(普通のバージョン)

//Realmインスタンスの取得
realm = Realm.getDefaultInstance()

realm.beginTransaction()
//realmインスタンスを使ってmodelの呼び出し
//create
val MyModel = realm.createObject(MyModel::class.java)
MyModel.myField1 = "フィールド１"
MyModel.myField2 = "フィールド２"

realm.commitTransaction()
//Realmインスタンスを開放する
realm.close()


/////////////////////////////////////////////////////////////////////////

//create(主キーを設定している場合)

//Realmインスタンスの取得
realm = Realm.getDefaultInstance()

realm.beginTransaction()
//realmインスタンスを使ってmodelの呼び出し
//create(主キー値はcreateObjectメソッドの2引数にセットする)
val MyModel = realm.createObject(MyModel::class.java, "hoge")
MyModel.myField1 = "フィールド１"
MyModel.myField2 = "フィールド２"

realm.commitTransaction()
//Realmインスタンスを開放する
realm.close()


//////////////////////////////////////////////////////////////////////////

//read(クエリ)

//Realmインスタンスの取得
val realm = Realm.getDefaultInstance()

//read
val results:RealmResults<MyModel> = realm.where(MyModel::class.java).findAll().sort(MyModel::myField1.name)

//Realmインスタンスを開放する
realm.close()

/*
クエリの中身を参照するにはfor構文を用いたり、リストのインデックス参照するような感じで中身を表示する。
println(results[2].myField1)
*/


//distinctを使った場合のクエリ生成方法
val results:RealmResults<MyModel> = realm.where(MyModel::class.java)
                                         .distinct(MyModel::myField1.name)
                                         .findAll().sort(MyModel.myField1.name)


/*
注意点は、findAll()メソッド後にdistinct()メソッドをつなぐとエラーが出るから、distinct().findAll()とつなげるようにする。
*/

/////////////////////////////////////////////////////////////////////////////

//read(オブジェクト)

//Realmインスタンスの取得
val realm = Realm.getDefaultInstance()

//read
val myModelObj = realm.where(MyModel::class.java).equalTo(MyModel::myField1.name, "フィールド１").findfirst()

//Realmインスタンスを開放する
realm.close()


//////////////////////////////////////////////////////////////////////////////

//delete(クエリから削除するパターン)

//Realmインスタンスの取得
val realm = Realm.getDefaultInstance()

realm.beginTransaction()
val results = realm.where(MyModel::class.java).findAll()
//クエリからオブジェクトを取り出す
val data = results[3]

//delete
data.deleteFromRealm()
realm.commitTransaction()

//Realmインスタンスを開放する
realm.close()


//////////////////////////////////////////////////////////////////////////////////

//delete(オブジェクトを指定して削除するパターン)

//Realmインスタンスの取得
val realm = Realm.getDefaultInstance()

realm.beginTransaction()

//オブジェクトを直接取り出す
val myModelObj = realm.where(MyModel::class.java).equalTo(MyModel::myField1.name, "フィールド１").findfirst()

//delete
myModelObj.deleteFromRealm()
realm.commitTransaction()

//Realmインスタンスを開放する
realm.close()
