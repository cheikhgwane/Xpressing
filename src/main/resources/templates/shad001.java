import java.util.HashMap;
import java.util.Map;
import java.sql.* ;
import spark.ModelAndView;
import java.util.ArrayList;
import spark.template.velocity.VelocityTemplateEngine;



import static spark.Spark.*;


public class xPressing
 {
     private static final String SESSION_NAME = "idGerant";
 
	  public static void main(String[] args)
	     {
 
         //Ne pas modifier    
	       staticFileLocation("/public");
         SqlQuery q = new SqlQuery();
 

        ///////////////Vue principale/////////////////
   	     get("/", (request, response) -> 
 
	       {
            //Ne pas modifier    
            Map map = new HashMap();
          

            // Ici on aura nos variables, requetes (q.xxx) et remplacement de nos $variable


            //Ne pas modifier    
            map.put("base", "templates/css.vtl");
	        	return new ModelAndView(map , "templates/home.vtl");
	       }
	       ,new VelocityTemplateEngine());


      ///////////////////////////////////////////////


 

        ///////////////Vue Inscription/////////////////
         get("/signin", (request, response) -> 
 
           {
            //Ne pas modifier    
            Map map = new HashMap();
          

            // Ici on aura nos variables, requetes (q.xxx) et remplacement de nos $variable


            //Ne pas modifier    
            map.put("base", "templates/css.vtl");
                return new ModelAndView(map , "templates/signin.vtl");
           }
           ,new VelocityTemplateEngine());


      ///////////////////////////////////////////////
 
 

        ///////////////Vue Connexion/////////////////
         get("/login", (request, response) -> 
 
           {
            //Ne pas modifier    
            Map map = new HashMap();
          

            
            // Ici on aura nos variables, requetes (q.xxx) et remplacement de nos $variable
             

            //Ne pas modifier    
            map.put("base", "templates/css.vtl");
                return new ModelAndView(map , "templates/login.vtl");
           }
           ,new VelocityTemplateEngine());


      ///////////////////////////////////////////////


        ///////////////Vue RedirectLog /////////////////
         post("/redirect", (request, response) -> 
 
           {
            //Ne pas modifier    
            Map map = new HashMap();
          

            // Ici on aura nos variables, requetes (q.xxx) et remplacement de nos $variable
             
            String uname = request.queryParams("username");
            String pword = request.queryParams("pword");

            int[] userType = q.login(uname, pword);

            if (userType[0] == 1)
             {  

               String idGerant = Integer.toString(userType[1]);
               //Setting the g id val
               response.cookie(SESSION_NAME, idGerant);
               int isActice = userType[2];
               if (isActice==1)
                {
                  response.redirect("/geranthome");
                } 
                else
                {
                  response.redirect("/inactive");
                }

            

             }
            if (userType[0] == 2)
             {

               String idEmp = Integer.toString(userType[1]);
               //Setting the g id val
               response.cookie(SESSION_NAME, idEmp);
               response.redirect("/mainempl"); 

               int isActice = userType[2];
               if (isActice==1)
                {
                  response.redirect("/geranthome");
                } 
                else
                {
                  response.redirect("/inactive");
                }

             }
            if (userType[0] == 3)
             {
               response.redirect("/admin");   
             }

            if (userType[0] == -1)
             {
               response.redirect("/login");   
             }


            //Ne pas modifier    
            // map.put("base", "templates/css.vtl");
                return new ModelAndView(map , "templates/redirect.vtl");
           }
           ,new VelocityTemplateEngine());


        ///////////////////////////////////////////////

        /////////////// Inactive view ////////////////

         get("/inactive", (request, response) -> 
 
           {
            //Ne pas modifier    
            Map map = new HashMap();
          

            
            // Ici on aura nos variables, requetes (q.xxx) et remplacement de nos $variable
             

            //Ne pas modifier    
            map.put("base", "templates/css.vtl");
                return new ModelAndView(map , "templates/inactive.vtl");
           }
           ,new VelocityTemplateEngine());

        ////////////////////////////////////////////////
 

        ///////////////Vue Gerant/////////////////
         get("/empl", (request, response) -> 
 
           {
               //Ne pas modifier    
               Map map = new HashMap();
          

            // Ici on aura nos variables, requetes (q.xxx) et remplacement de nos $variable
            
                String empId = request.cookie(SESSION_NAME);

                 HashMap depots =  q.depotsAll(Integer.parseInt(empId));

                 map.put("depots" , depots);

                //Ne pas modifier    
                map.put("base", "templates/css.vtl");
                return new ModelAndView(map , "templates/empdeps.vtl");
           }
           ,new VelocityTemplateEngine());


        ///////////////////////////////////////////////
 
        ///////////////Vue Movedep/////////////////
         get("/movedep", (request, response) -> 
 
           {
               //Ne pas modifier    
                Map map = new HashMap();
          

                 // Ici on aura nos variables, requetes (q.xxx) et remplacement de nos $variable
            
                 String depId = request.queryParams("iddep");
                 String status = request.queryParams("status");

                 q.moveDep(Integer.parseInt(depId) , Integer.parseInt(status));
                 response.redirect("/empl");
                //Ne pas modifier    
                map.put("base", "templates/css.vtl");
                return new ModelAndView(map , "templates/movedep.vtl");
           }
           ,new VelocityTemplateEngine());


      ///////////////////////////////////////////////





        ///////////////Vue Admin/////////////////
         get("/admin", (request, response) -> 
 
           {
            //Ne pas modifier    
            Map map = new HashMap();
          

            // Ici on aura nos variables, requetes (q.xxx) et remplacement de nos $variable
            HashMap presss =  q.pressingAll();

                map.put("pressings" , presss);

                //Ne pas modifier    
                map.put("base", "templates/css.vtl");
                return new ModelAndView(map , "templates/adminhome.vtl");
           }
           ,new VelocityTemplateEngine());


      ///////////////////////////////////////////////

     ///////////////Vue switchStatus/////////////////
         get("/switcher", (request, response) -> 
 
           {
            //Ne pas modifier    
            Map map = new HashMap();
          

            // Ici on aura nos variables, requetes (q.xxx) et remplacement de nos $variable
           
            int idPress = Integer.parseInt(request.queryParams("idPress"));
            int newStatus = Integer.parseInt(request.queryParams("status"));

            q.switchStatus(idPress, newStatus);

                //Ne pas modifier    
                response.redirect("/admin");
                map.put("base", "templates/css.vtl");
                return new ModelAndView(map , "templates/switcher.vtl");
           }
           ,new VelocityTemplateEngine());


      ///////////////////////////////////////////////



    
        }
 
 }