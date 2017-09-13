package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import play.api.libs.json.Writes
import play.api.libs.json.{__, Reads}
import play.api.libs.functional.syntax._
import models.Item

case class CreateItem(name: String, price: Double)

object Items extends Controller {

  val shop = models.Shop

  implicit val readsCreateItem: Reads[CreateItem] = (
    ((__ \ "name").read[String]) and
    ((__ \ "price").read[Double])
  )(CreateItem.apply _)

  implicit val writesItem = Writes[Item] {
    case Item(id, name, price) => 
      Json.obj(
        "id" -> id,
        "name" -> name,
        "price" -> price  
      )
  }

  val list = Action {
    Ok(Json.toJson(shop.list))
  }

  def details(id: Long) = Action {
    shop.get(id) match {
      case Some(item) => Ok(Json.toJson(item))
      case None => NotFound
    }
  }

	val create = Action { NotImplemented }
  
  def update(id: Long) = Action { NotImplemented }

  def delete(id: Long) = Action { NotImplemented }
}


                



/*	val list = Action {
    Ok(Json.arr(
      shop.list.map(item => Json.obj(
        "id" -> item.id,
        "name" -> item.name,
        "price" -> item.price
      ))
    ))}
   
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
*/



