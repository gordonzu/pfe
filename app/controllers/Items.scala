package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import play.api.libs.json.Json.arr

object Items extends Controller {

  val shop = models.Shop

  val theMap = shop.list.map(item => Json.obj(
    "id" -> item.id,
    "name" -> item.name,
    "price" -> item.price
  ))

	val list = Action(Ok(Json.arr(theMap)))

	val create = Action { NotImplemented }
	
	//def details(id: Long) = Action { NotImplemented }

/*

  val list = Action {
    Ok(Json.arr(shop.list.map(item => JsObject(
      "id" -> item.id,
      "name" -> item.name,
      "price" -> item.price
    )): _*))
 }
*/

  def details(id: Long) = Action {
    shop.get(id) match {
      case Some(item) =>
        Ok(Json.obj(
          "id" -> item.id,
          "name" -> item.name,
          "price" -> item.price
        ))
      case None => NotFound
    }
  }

  def update(id: Long) = Action { NotImplemented }

  def delete(id: Long) = Action { NotImplemented }
}


                






