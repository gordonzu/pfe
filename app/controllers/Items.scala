package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import play.api.libs.json.Writes
import play.api.libs.json.{__, Reads, JsSuccess, JsError}
import play.api.libs.functional.syntax._
import models.Item

case class CreateItem(name: String, price: Double)

object Items extends Controller {

  val shop = models.Shop

  implicit val readsCreateItem = (
    (__ \ "name").read(Reads.minLength[String](1)) and
    (__ \ "price").read(Reads.min[Double](0))
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

	val create = Action(parse.json) { implicit request =>
    request.body.validate[CreateItem] match {
      case JsSuccess(createItem, _) => 
        shop.create(createItem.name, createItem.price) match {
          case Some(item) => Ok(Json.toJson(item))
          case None => InternalServerError
        }
        case JsError(errors) =>
          BadRequest
    } 
  }
  
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



