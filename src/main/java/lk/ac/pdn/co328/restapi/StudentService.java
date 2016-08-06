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
    StudentRegister newReg = new StudentRegister();
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = newReg.findStudent(id);
        if(st == null){
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("ID is invalid").build();
        }
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }

    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        // Ideally this should be machine readable format Json or XML
        Student st = newReg.findStudent(id);
        if(st == null){
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("ID is invalid").build();
        }

        newReg.removeStudent(id);
        String msg = null;
        try{
            String stu[] = input.split(" ");
            Student stnew = new Student(id, stu[0], stu[1]);
            newReg.addStudent(stnew);
            msg = "Student added successfully";
        }
        catch (Exception e){
            msg = e.toString();
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(msg).build();
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(msg).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        // Ideally this should be machine readable format Json or XML
        Student st = newReg.findStudent(id);
        if(st == null){
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("ID is invalid").build();
        }
        newReg.removeStudent(id);
        return Response.status(HttpResponseCodes.SC_OK).entity("Delete Success").build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
        // Ideally this should be machine readable format Json or XML
        Student st = newReg.findStudent(id);
        String message = null;
        if(st == null) {
            try {
                String stu[] = input.split(" ");
                Student st1 = new Student(id, stu[0], stu[1]);
                newReg.addStudent(st1);
                message = "Student added successfully";
            }
            catch (Exception e) {
                message = e.toString();
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
            }
        }
        else{
            message = "Student ID already exists";
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


