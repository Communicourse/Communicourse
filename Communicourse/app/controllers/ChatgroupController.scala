package controllers

import model.{Chatgroup, ChatgroupForm}
import model.{User, UserForm}
import play.api.mvc._
import scala.concurrent.Future
import service.ChatgroupService
import service.UserService
import scala.concurrent.ExecutionContext.Implicits.global

class ChatgroupController extends Controller {

  

    def addChatgroup() = Action.async { 
     /* var now = new Date()
    var  dateFormat:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var hehe = dateFormat.format( now )*/
      
       
      implicit request =>
      ChatgroupForm.form.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => Future.successful(Ok(("Fail"))),
      data => {
        val newChatgroup = Chatgroup(0, data.group_name, "," , data.owner,"time","description")
        //UserService.update_chatgroup(data.owner, UserService.getUser(data.owner).chat_grupe +data.owner+"," )
        ChatgroupService.addChatgroup(newChatgroup).map(res =>
         Ok("File uploaded")//.withSession("chatgroup" ->request.session.get("chatgroup").toString.slice(5,request.session.get("chatgroup").toString.length-1)+data.owner+",")
        )
      })
  }

  def deleteChatgroup(id: Long) = Action.async { implicit request =>
    var userid=request.session.get("userid").toString.slice(5,request.session.get("userid").toString.length-1)
   // UserService.update_chatgroup(userid,
     //                         request.session.get("chatgroup").toString.slice(5,request.session.get("chatgroup").toString.length-1).replace(","+id+"," , ",") )
    ChatgroupService.deleteChatgroup(id) map { res =>
      Redirect(routes.ChatgroupController.list_owner())
    }
  }


    def listAll = Action.async { implicit request =>
    ChatgroupService.listAllChatgroups map { chatgroups => 
       Ok("List_All_Group")
    }
    }
    
    def list_owner = Action.async { implicit request =>
    ChatgroupService.listAllChatgroups map { chatgroups => 
    val userid  =request.session.get("userid").toString.slice(5,request.session.get("userid").toString.length-1)
    val new_chatgroups= chatgroups.filter(_.owner ==  userid)
    
       Ok(views.html.list_owner(ChatgroupForm.form, new_chatgroups,userid))
    
    }        
    }
    
    def list_members = Action.async { implicit request =>
    ChatgroupService.listAllChatgroups map { chatgroups => 
    val userid  =request.session.get("userid").toString.slice(5,request.session.get("userid").toString.length-1)
    val new_chatgroups= chatgroups.filter(_.members.contains(","+userid+","))
    
       Ok(views.html.list_members(ChatgroupForm.form, new_chatgroups,userid))
    
    }
    }
    
     
    
    
    def exitChatgroup(content:String) = Action.async { implicit request =>
    var members=content.split(':')(1)
    var userid=content.split(':')(2)
    ChatgroupService.update_chatgroup(content.split(':')(0), members.replace(","+userid+","  , ",")) map { chatgroups => 
       Redirect(routes.ChatgroupController.list_members())
    
}
    }
    
    
  
}

