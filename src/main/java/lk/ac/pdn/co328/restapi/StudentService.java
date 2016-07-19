/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;
 
@Path("rest")
public class StudentService
{
    private StudentRegister studentlist;

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = studentlist.findStudent(id) ;
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        Student st = studentlist.findStudent(id);
        String message;
        if(st!=null){
            st.setFirstName(input);
            message = "{message:'Updated student successfully'}";
        }else{
            message = "{message:'Enter valid student ID'}";  // Ideally this should be machine readable format Json or XML
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        Student st = studentlist.findStudent(id);
        String message;
        if(st!=null){
            studentlist.removeStudent(id);
            message = "{message:'Student removed successfully'}";
        }else{
            message = "{message:'Enter valid student ID'}";  // Ideally this should be machine readable format Json or XML
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
        String message;
        try {
            studentlist.addStudent(new Student(id,input,input));
            message = "{message:'Student added'}";
        } catch (Exception e) {
            e.printStackTrace();
            message = "{message:'Student already in the system'}";
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


