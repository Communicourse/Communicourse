package controllers

import model.{Message, MessageForm}
import model.{User, UserForm}
import java.io.File
import play.api.mvc._
import scala.concurrent.Future
import service.MessageService
import service.UserService
import scala.concurrent.ExecutionContext.Implicits.global

class MessageController extends Controller {



  def addMessage_text() = Action.async { 
     /* var now = new Date()
    var  dateFormat:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var time = dateFormat.format( now )*/
      
      
      implicit request =>
    MessageForm.form.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => Future.successful(Ok("Fail")),
      data => {
        val newMessage = Message(0, "text",data.content, data.chat_group, data.user,"time")
        //UserService.update_chatgroup(data.owner, UserService.getUser(data.owner).chat_grupe +data.owner+"," )
        MessageService.addMessage(newMessage).map(res =>
          Ok("success")//.withSession("chatgroup" ->request.session.get("chatgroup").toString.slice(5,request.session.get("chatgroup").toString.length-1)+data.owner+",")
        )
      })
  }
  
  /*
  def addMessage_file(chat_group:String ) = Action(parse.multipartFormData) { request =>
        // val chat_group=MessageForm.form.bindFromRequest.get.chat_group
        request.body.file("document").map { doc =>

            val filename = doc.filename
            val contentType = doc.contentType
            doc.ref.moveTo(new File(s"public/documents/$filename"))
            var userid=request.session.get("userid").toString.slice(5,request.session.get("userid").toString.length-1)
             val newMessage = Message(0, "doc",filename, chat_group.toInt, userid,"sdfgfdg")
             MessageService.addMessage(newMessage)
             
            Ok("File uploaded")
        }.getOrElse {
           Redirect(routes.Application.index).flashing(
              "error" -> "Missing file")
        }
    }
    
    def download_file(file_name:String ) = Action { 
        Ok.sendFile(new java.io.File(s"public/documents/$file_name"))
    }


  
  


    def listAll = Action.async { implicit request =>
    MessageService.listAllMessages map { messages => 
       Ok(views.html.exitchatgroup(MessagegroupForm.form, messages,request.session.get("userid").toString))
    
       // Ok(views.html.createchatgroup(ChatgroupForm.form,Seq.empty[Chatgroup],"users.head.id.toString"))
    }
    }*/
    
    
  
}

